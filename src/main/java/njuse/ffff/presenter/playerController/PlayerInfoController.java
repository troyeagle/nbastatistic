package njuse.ffff.presenter.playerController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import njuse.ffff.data.SeasonStatProcessor;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerPO;
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
	 * 设置球员简介
	 */
	public void setPlayerProfilePanel(PlayerProfileService panel, String playerName) {
		//获取指定的球员信息
		PlayerPO playerInfo = dataService.getPlayerInfo(playerName, emptyFilter);
		if(playerInfo!=null){
			String position = null;
			switch (Character.toUpperCase(playerInfo.getPosition())) {
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
					player.getMinute(),				//出场时间
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
					DealDecimal.formatChange(player.getSecond(), 1),		//出场时间
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
	 * 设置球员参加的比赛
	 */
	public void setPlayerGameLog(PlayerDataService panel,String playerName) {
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
		playerGameLog = playerName;
		totalController.setPlayerDataService(panel);
	}

}
