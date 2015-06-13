package njuse.ffff.presenter.playerController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import njuse.ffff.data.SeasonStatProcessor;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatch;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamInMatch;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.playerService.PlayerInfoService;
import njuse.ffff.uiservice.PlayerDataService;
import njuse.ffff.uiservice.PlayerProfileService;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;

public class PlayerInfoController implements PlayerInfoService{
	private DataReaderService dataService;
	private static PlayerInfoController playerInfoController = null;
	private static TotalUIController totalController = null;
	
	private String[] seasonList = {"14-15","13-14","12-13"};
	
	private String playerProfile = null;
	private String playerTotalData = null;
	private String playerAvgData = null;
	private String playerAdvancedData = null;
	private String playerGameLog = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private PlayerInfoController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
		}

	public static PlayerInfoController getInstance() {
		if (playerInfoController == null) {
			playerInfoController = new PlayerInfoController();
		}
		return playerInfoController;
	}

	public String[] getPresentPlayer(){
		return new String[]{playerProfile,playerTotalData,
				playerAvgData,playerAdvancedData,playerGameLog};
	}
	
	/**
	 * 获得球员参与的赛季 TODO
	 */
	public String[] getInvolvedSeason(String playerName){
		return null;
	}

	/**
	 * 设置球员简介
	 */
	public void setPlayerProfilePanel(PlayerProfileService panel, String playerName) {
		//获取指定的球员信息
		PlayerPO playerInfo = dataService.getPlayerInfo(playerName, emptyFilter);
		if(playerInfo!=null){
			String position = null;
			switch (Character.toUpperCase(playerInfo.getPosition()[0])) {
			case 'F':
				position = "前锋";
				break;
			case 'C':
				position = "中锋";
				break;
			case 'G':
				position = "后卫";
				break;
			}
			//退休球员
			String experience = playerInfo.getExp();
			if(experience.equals("R")){
				experience = "Retired";
			}
			//格式化身高
//			String[] parts = playerInfo.getWeight().split("-");
//			String weight = null;
//			if(parts[1].equals("0")){
//				weight = parts[0];
//			}
//			else{
//				weight = playerInfo.getWeight();
//			}
			PlayerInAverage data = dataService.getPlayerAverage(playerName, emptyFilter);
			panel.setProfile(playerName, position, playerInfo.getNumber(), 
					playerInfo.getHeight(), playerInfo.getWeight(),
					playerInfo.getBirth(), String.valueOf(playerInfo.getAge()),
					experience, playerInfo.getSchool(), data.getTeamName());
		}	
		else{
			PlayerInAverage data = dataService.getPlayerAverage(playerName, emptyFilter);
			String team = "不存在";
			if(data!=null){
				team = data.getTeamName();
			}
			panel.setProfile(playerName, "不存在", "不存在", 
					"不存在", "不存在",
					"不存在", "不存在",
					"不存在", "不存在", team);
		}
		playerProfile = playerName;
		totalController.setPlayerProfileService(panel);
	}

	/**
	 * 设置球员总基础数据
	 */
	public void setPlayerTotalData(PlayerDataService panel, String playerName) {
		ArrayList<String> valid_season = new ArrayList<String>();
		for(String s:seasonList){
			if(dataService.getSeasonStatProcessor(s)!=null){
				valid_season.add(s);
			}
		}
//		while(seasonNum[0]>1940){
//			StringBuffer season = new StringBuffer();
//			season.append(seasonNum[0]);
//			season.append("-");
//			season.append(seasonNum[1]);
//			if(dataService.getSeasonStatProcessor(season.toString())!=null){
//				valid_season.add(season.toString());
//			}
//			seasonNum[0]--;
//			seasonNum[1]--;
//		}
//		seasonNum[0] = 2014;
//		seasonNum[1] = 2015;
		Object[][] totalData = new Object[valid_season.size()][];
		for(int i=0;i<valid_season.size();i++){
			SeasonStatProcessor seasonProcess = dataService.getSeasonStatProcessor(valid_season.get(i));
			PlayerInAverage player = seasonProcess.getPlayerAverage(playerName, emptyFilter);
			if(player==null)
				continue;
			double[] total = player.getStatsTotal();
			double[] average = player.getStatsAverage();
			totalData[i] = new Object[]{
					valid_season.get(i),			//赛季
					player.getTeamName(),			//球队
					player.getFirstOnMatch(),		//首发场数
					player.getEffective(),			//比赛场数
					DealDecimal.formatChangeToPercentage(average[29]),	//投篮命中率
					DealDecimal.formatChange(total[0]),					//投篮命中数
					DealDecimal.formatChange(total[1]),					//投篮出手数
					DealDecimal.formatChangeToPercentage(average[28]),	//三分命中率
					DealDecimal.formatChange(total[2]),					//三分命中数
					DealDecimal.formatChange(total[3]),					//三分出手数
					DealDecimal.formatChangeToPercentage(average[27]),	//罚球命中率
					DealDecimal.formatChange(total[4]),					//罚球命中数
					DealDecimal.formatChange(total[5]),					//罚球出手数
					DealDecimal.formatChange(total[8]),					//篮板
					DealDecimal.formatChange(total[6]),					//前场篮板
					DealDecimal.formatChange(total[7]),					//后场篮板
					DealDecimal.formatChange(total[9]),					//助攻
					DealDecimal.formatChange(total[10]),				//抢断
					DealDecimal.formatChange(total[11]),				//盖帽
					DealDecimal.formatChange(total[12]),				//失误
					DealDecimal.formatChange(total[13]),				//犯规
					DealDecimal.formatChange(total[14]),				//得分
			};
		}
		panel.setTotalData(totalData);
		playerTotalData = playerName;
		totalController.setPlayerDataService(panel);
	}

	/**
	 * 设置球员平均基础数据
	 */
	public void setPlayerAvgData(PlayerDataService panel, String playerName) {
		ArrayList<String> valid_season = new ArrayList<String>();
		for(String s:seasonList){
			if(dataService.getSeasonStatProcessor(s)!=null){
				valid_season.add(s);
			}
		}
//		while(seasonNum[0]>1940){
//			StringBuffer season = new StringBuffer();
//			season.append(seasonNum[0]);
//			season.append("-");
//			season.append(seasonNum[1]);
//			if(dataService.getSeasonStatProcessor(season.toString())!=null){
//			valid_season.add(season.toString());
//			}
//			seasonNum[0]--;
//			seasonNum[1]--;
//		}
//		seasonNum[0] = 2014;
//		seasonNum[1] = 2015;
		Object[][] averageData = new Object[valid_season.size()][];
		for(int i=0;i<valid_season.size();i++){
			SeasonStatProcessor seasonProcess = dataService.getSeasonStatProcessor(valid_season.get(i));
			PlayerInAverage player = seasonProcess.getPlayerAverage(playerName, emptyFilter);
			if(player==null)
				continue;
			double[] average = player.getStatsAverage();
			averageData[i] = new Object[]{
					valid_season.get(i),			//赛季
					player.getTeamName(),			//球队
					player.getFirstOnMatch(),		//首发场数
					DealDecimal.formatChangeTime(average[31]),		//出场时间
					DealDecimal.formatChangeToPercentage(average[29]),		//投篮命中率
					DealDecimal.formatChange(average[0],1),					//投篮命中数
					DealDecimal.formatChange(average[1],1),					//投篮出手数
					DealDecimal.formatChangeToPercentage(average[28]),		//三分命中率
					DealDecimal.formatChange(average[2],1),					//三分命中数
					DealDecimal.formatChange(average[3],1),					//三分出手数
					DealDecimal.formatChangeToPercentage(average[27]),		//罚球命中率
					DealDecimal.formatChange(average[4],1),					//罚球命中数
					DealDecimal.formatChange(average[5],1),					//罚球出手数
					DealDecimal.formatChange(average[8],1),					//篮板
					DealDecimal.formatChange(average[6],1),					//前场篮板
					DealDecimal.formatChange(average[7],1),					//后场篮板
					DealDecimal.formatChange(average[9],1),					//助攻
					DealDecimal.formatChange(average[10],1),				//抢断
					DealDecimal.formatChange(average[11],1),				//盖帽
					DealDecimal.formatChange(average[12],1),				//失误
					DealDecimal.formatChange(average[13],1),				//犯规
					DealDecimal.formatChange(average[14],1),				//得分
			};
		}
		panel.setAvgData(averageData);
		playerAvgData = playerName;
		totalController.setPlayerDataService(panel);
	}

	/**
	 * 设置球员进阶数据
	 */
	public void setPlayerAdvancedData(PlayerDataService panel, String playerName) {
		ArrayList<String> valid_season = new ArrayList<String>();
		for(String s:seasonList){
			if(dataService.getSeasonStatProcessor(s)!=null){
				valid_season.add(s);
			}
		}
//		while(seasonNum[0]>1940){
//			StringBuffer season = new StringBuffer();
//			season.append(seasonNum[0]);
//			season.append("-");
//			season.append(seasonNum[1]);
//			if(dataService.getSeasonStatProcessor(season.toString())!=null){
//				valid_season.add(season.toString());
//			}
//			seasonNum[0]--;
//			seasonNum[1]--;
//		}
//		seasonNum[0] = 2014;
//		seasonNum[1] = 2015;
		Object[][] advancedData = new Object[valid_season.size()][];
		for(int i=0;i<valid_season.size();i++){
			SeasonStatProcessor seasonProcess = dataService.getSeasonStatProcessor(valid_season.get(i));
			PlayerInAverage player = seasonProcess.getPlayerAverage(playerName, emptyFilter);
			if(player==null)
				continue;
			double[] average = player.getStatsAverage();
			advancedData[i] = new Object[]{
					valid_season.get(i),			//赛季
					player.getTeamName(),			//球队
					DealDecimal.formatChange(average[19], 1),		//篮板率
					DealDecimal.formatChange(average[20], 1),		//进攻板
					DealDecimal.formatChange(average[21], 1),		//防守板
					DealDecimal.formatChange(average[22], 1),		//助攻率
					DealDecimal.formatChange(average[23], 1),		//抢断率
					DealDecimal.formatChange(average[24], 1),		//盖帽率
					DealDecimal.formatChange(average[25], 1),		//失误率
					DealDecimal.formatChange(average[26], 1),		//使用率
					DealDecimal.formatChange(average[15], 1),		//效率
					DealDecimal.formatChange(average[16], 1),		//GmSc效率值
					DealDecimal.formatChange(average[17], 1),		//真实命中
					DealDecimal.formatChange(average[18], 1),		//投篮效率
			};
		}
		panel.setAdvancedData(advancedData);
		playerAdvancedData = playerName;
		totalController.setPlayerDataService(panel);
	}
	
	/**
	 * 设置季后赛球员数据界面--总基础数据表格
	 */
	public void setPlayOffTotalData(PlayerDataService panel,String playerName){
		
	}
	
	/**
	 * 设置季后赛球员数据界面--平均基础数据表格
	 */
	public void setPlayOffAvgData(PlayerDataService panel,String playerName){
		
	}
	
	/**
	 * 设置季后赛球员数据界面--进阶数据表格
	 */
	public void setPlayOffAdvancedData(PlayerDataService panel,String playerName){
		
	}
		

	/**
	 * 设置球员参加的比赛
	 */
	public void setPlayerGameLog(PlayerDataService panel,String season ,String playerName) {
		List<MatchPO> matchList = dataService.getMatchForPlayer(playerName);
//		String[] properties = {"比赛日期","比赛对阵"};
		if(matchList.size()>0){
			//比赛排序
			for(int i=0;i<matchList.size()-1;i++){
				for(int j=0;j<matchList.size()-i-1;j++){
					if(matchList.get(j).getDate().before(matchList.get(j+1).getDate())){
						MatchPO temp = matchList.get(j);
						matchList.set(j, matchList.get(j+1));
						matchList.set(j+1, temp);
					}
				}
			}
			//设置数据
			Object[][] values = new Object[matchList.size()][];
			for(int i=0;i<matchList.size();i++){
				MatchPO match = matchList.get(i);
				Calendar date = new GregorianCalendar();
				date.setTime(match.getDate());
				int year = date.get(Calendar.YEAR);//减去差值
				int month = date.get(Calendar.MONTH)+1;
				int day = date.get(Calendar.DAY_OF_MONTH);
				StringBuffer dateBuffer = new StringBuffer(year+"-"+month+"-"+day);
				StringBuffer participentsBuffer = new StringBuffer(match.getTeamA()+"  VS  "+match.getTeamB());
				values[i] = new Object[]{dateBuffer.toString(),participentsBuffer.toString()};
			}
			panel.setGameLog(values, null);
		}
		else{
			panel.setGameLog(new Object[0][2], null);
		}
		playerGameLog = playerName;
		totalController.setPlayerDataService(panel);
	}
	
	/**
	 * 球员和联盟平均水平对比
	 */
	public void playerByLeagueCompare(PlayerDataService panel,String season,String playerName){
		ArrayList<TeamPO> teamList = new ArrayList<TeamPO>();
		String league = null;
		Object[][] list = new Object[8][2];//平均得分，平均篮板，平均抢断，平均助攻，平均盖帽，投篮命中率，三分命中率，罚球命中率
		ArrayList<TeamInAverage> teamDataList = new ArrayList<TeamInAverage>();//TODO
		int score_total = 0;
		int rebound_total = 0;
		int steal_total = 0;
		int assist_total = 0;
		int block_total = 0;
		int fieldGoalMade_total = 0;
		int fieldGoalAttempted_total = 0;
		int threePointerMade_total = 0;
		int threePointerAttempted_total = 0;
		int freeThrowMade_total = 0;
		int freeThrowAttempted_total = 0;
		int player_number = 0;
		for(TeamPO team:teamList){
			if(team.getLeague().equals(league)){
				TeamInAverage teamData = dataService.getSeasonStatProcessor(season).getTeamAverage(team.getName(), emptyFilter);
				teamDataList.add(teamData);
			}
		}
		for(TeamInAverage team:teamDataList){
			double[] total = team.getStatsTotal();
			score_total += total[14];
			rebound_total += total[8];
			steal_total += total[10];
			assist_total += total[9];
			block_total += total[11];
			fieldGoalMade_total += total[0];
			fieldGoalAttempted_total += total[1];
			threePointerMade_total += total[2];
			threePointerAttempted_total += total[3];
			freeThrowMade_total += total[4];
			freeThrowAttempted_total += total[5];
			for(TeamInMatch match:team.getTeamStats()){
				for(PlayerInMatch playerInMatch:match.getPlayers()){
					if(playerInMatch.getSecond()>0){
						player_number++;
					}
				}
			}
		}
		SeasonStatProcessor seasonProcess = dataService.getSeasonStatProcessor(season);
		PlayerInAverage player = seasonProcess.getPlayerAverage(playerName, emptyFilter);
		double[] player_data = player.getStatsAverage();
		list[0][0] = DealDecimal.formatChange(player_data[14],1);
		list[0][1] = DealDecimal.formatChange(player_data[8],1);
		list[0][2] = DealDecimal.formatChange(player_data[9],1);
		list[0][3] = DealDecimal.formatChange(player_data[10],1);
		list[0][4] = DealDecimal.formatChange(player_data[11],1);
		list[0][5] = DealDecimal.formatChangeToPercentage(player_data[29]);
		list[0][6] = DealDecimal.formatChangeToPercentage(player_data[28]);
		list[0][7] = DealDecimal.formatChangeToPercentage(player_data[27]);
		list[1][0] = DealDecimal.formatChange(((double)score_total)/((double)player_number),1);
		list[1][1] = DealDecimal.formatChange(((double)rebound_total)/((double)player_number),1);
		list[1][2] = DealDecimal.formatChange(((double)assist_total)/((double)player_number),1);
		list[1][3] = DealDecimal.formatChange(((double)steal_total)/((double)player_number),1);
		list[1][4] = DealDecimal.formatChange(((double)block_total)/((double)player_number),1);
		list[1][5] = DealDecimal.formatChangeToPercentage(((double)fieldGoalMade_total)/((double)fieldGoalAttempted_total));
		list[1][6] = DealDecimal.formatChangeToPercentage(((double)threePointerMade_total)/((double)threePointerAttempted_total));
		list[1][7] = DealDecimal.formatChangeToPercentage(((double)freeThrowMade_total)/((double)freeThrowAttempted_total));
		//设置界面 TODO
	}
	
}
