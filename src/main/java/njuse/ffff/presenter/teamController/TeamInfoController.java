package njuse.ffff.presenter.teamController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.teamService.TeamInfoService;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.sqlpo.PlayerInfo;
import njuse.ffff.sqlpo.TeamAverage;
import njuse.ffff.sqlpo.TeamAverageAdv;
import njuse.ffff.sqlpo.TeamInfo;
import njuse.ffff.uiservice.TeamDataService;
import njuse.ffff.uiservice.TeamProfileService;
import njuse.ffff.util.DealDecimal;

public class TeamInfoController implements TeamInfoService{
	private DataReaderService dataService;
	private NewDataReaderService dataReader;
	private static TeamInfoController teamInfoController = null;
	private static TotalUIController totalController = null;
	
	private String[] seasonList;//赛季列表
	
	private String teamProfile = null;
	private String teamPlayer = null;
	private String teamTotalData = null;
	private String teamAvgData = null;
	private String teamAdvancedData = null;
	private String teamGameLog = null;
	
//	private static final Filter emptyFilter;
//
//	static {
//		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
//	}
	
	private TeamInfoController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
		dataReader = totalController.getDataReader();
	}

	public static TeamInfoController getInstance() {
		if (teamInfoController == null) {
			teamInfoController = new TeamInfoController();
		}
		return teamInfoController;
	}

	public String[] getPresentTeam(){
		return new String[]{teamProfile,teamPlayer,teamTotalData
				,teamAvgData,teamAdvancedData,teamGameLog};
	}
	
	/**
	 * 获得球队参与的赛季
	 */
	public String[] getInvolvedSeason(String teamName){
		TeamInfo teamInfo = dataReader.getTeamInfo(teamName);
		String seasons = String.valueOf(teamInfo.generateMap().get("seasons")).replace(" ", "");
		String[] temp = seasons.split("[;；]")[1].split("to");
		int start = Integer.parseInt(temp[0].split("-")[0]);
		int end = Integer.parseInt(temp[1].split("-")[0]);
		seasonList = new String[end-start];
		for(int i=start;i<=end-1;i++){
			String temp2 = String.valueOf(start+1);
			StringBuffer bf = new StringBuffer(start+"-"+temp2.substring(2,temp2.length()));
			seasonList[i-start] = bf.toString();
		}
		return seasonList;
	}
	
	/**
	 * 设置球队简介界面
	 */
	public void setTeamProfilePanel(TeamProfileService panel, String teamName) {
		//获取指定球队的信息
		TeamInfo teamInfo = dataReader.getTeamInfo(teamName);
		Map<String,Object> map = teamInfo.generateMap();
		//设置球队简介
		panel.setProfile(teamName, String.valueOf(map.get("teamNames")), String.valueOf(map.get("location"))
						, String.valueOf(map.get("teamNames")), ""
						, "", "");
		teamProfile = teamName;
		totalController.setTeamProfileService(panel);
	}
	
	/**
	 * 设置球队的球员
	 */
	public void setPlayerForTeam(TeamDataService panel, String teamName) {
		List<PlayerInfo> players = dataReader.getPlayersInTeam(teamName, totalController.getCurrentSeason());
		if(players.size()>0){
			Object[][] playerForTeam = new Object[players.size()][];
			for(int i=0;i<players.size();i++){
				playerForTeam[i] = new Object[]{i,players.get(i).getPlName()};//TODO
			}
			panel.setPlayers(playerForTeam);
		}
		teamPlayer = teamName;
		totalController.setTeamDataService(panel);
	}

	/**
	 * 设置球队总基础数据
	 */
	public void setTeamTotalData(TeamDataService panel, String teamName) {
		ArrayList<String> valid_season = new ArrayList<String>();
		for(String s:seasonList){
			if(dataService.getSeasonStatProcessor(s) != null){
				valid_season.add(s);
			}
		}
		Object[][] totalData = new Object[valid_season.size()][];
		for(int i=0;i<valid_season.size();i++){
			TeamAverage team = dataReader.getTeamAverageSingle(teamName, valid_season.get(i),"total");
			Map<String,Object> map = team.generateMap();
			int numberOfMatch = Integer.parseInt(String.valueOf(map.get("win")))+Integer.parseInt(String.valueOf(map.get("lose")));
			totalData[i] = new Object[]{
					valid_season.get(i),		//赛季
					numberOfMatch,		//比赛数
					DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map.get("fieldGoalPercentage")))),	//投篮命中率
					map.get("fieldGoalMade"),					//投篮命中数
					map.get("fieldGoalAttempted"),					//投篮出手数
					DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map.get("threePointerPercentage")))),	//三分命中率
					map.get("threePointerMade"),					//三分命中数
					map.get("threePointerAttempted"),					//三分出手数
					DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map.get("freeThrowRate")))),	//罚球命中率
					map.get("freeThrowMade"),					//罚球命中数
					map.get("freeThrowAttempted"),					//罚球出手数
					map.get("rebound"),					//篮板
					map.get("offensiveRebound"),					//前场篮板
					map.get("defensiveRebound"),					//后场篮板
					map.get("assist"),					//助攻
					map.get("steal"),				//抢断
					map.get("block"),				//盖帽
					map.get("turnover"),				//失误
					map.get("foul"),				//犯规
					map.get("points"),				//得分
			};
		}
		panel.setTotalDataTable(totalData);
		teamTotalData = teamName;
		totalController.setTeamDataService(panel);
	}

	/**
	 * 设置球队平均基础数据
	 */
	public void setTeamAvgData(TeamDataService panel, String teamName) {
		ArrayList<String> valid_season = new ArrayList<String>();
		for(String s:seasonList){
			if(dataService.getSeasonStatProcessor(s) != null){
				valid_season.add(s);
			}
		}
		Object[][] averageData = new Object[valid_season.size()][];
		for(int i=0;i<valid_season.size();i++){
			TeamAverage team = dataReader.getTeamAverageSingle(teamName, valid_season.get(i),"perGame");
			Map<String,Object> map = team.generateMap();
			averageData[i] = new Object[]{
					valid_season.get(i),		//赛季
					map.get("games"),		//比赛数
					DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map.get("fieldGoalPercentage")))),		//投篮命中率
					map.get("fieldGoalMade"),					//投篮命中数
					map.get("fieldGoalAttempted"),					//投篮出手数
					DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map.get("threePointerPercentage")))),		//三分命中率
					map.get("threePointerMade"),					//三分命中数
					map.get("threePointerAttempted"),					//三分出手数
					DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map.get("freeThrowRate")))),		//罚球命中率
					map.get("freeThrowMade"),					//罚球命中数
					map.get("freeThrowAttempted"),					//罚球出手数
					map.get("rebound"),					//篮板
					map.get("offensiveRebound"),					//前场篮板
					map.get("defensiveRebound"),					//后场篮板
					map.get("assist"),					//助攻
					map.get("steal"),				//抢断
					map.get("block"),				//盖帽
					map.get("turnover"),				//失误
					map.get("foul"),				//犯规
					map.get("points")					//得分
			};
		}
		panel.setAvgDataTable(averageData);
		teamAvgData = teamName;
		totalController.setTeamDataService(panel);
	}

	/**
	 * 设置球队进阶数据
	 */
	public void setTeamAdvancedlData(TeamDataService panel, String teamName) {
		ArrayList<String> valid_season = new ArrayList<String>();
		for(String s:seasonList){
			if(dataService.getSeasonStatProcessor(s) != null){
				valid_season.add(s);
			}
		}
		Object[][] advancedData = new Object[valid_season.size()][];
		for(int i=0;i<valid_season.size();i++){
			TeamAverageAdv team = dataReader.getTeamAverageAdv(teamName, valid_season.get(i));
			Map<String,Object> map = team.generateMap();
			advancedData[i] = new Object[]{
					valid_season.get(i),		//赛季
					map.get("myRounds"),	//进攻回合
					map.get("offensiveEf"),				//进攻效率
					map.get("defensiveEf"),				//防守效率
					map.get("offensiveReboundEf"),	//进攻篮板效率
					map.get("defensiveReboundEf"),				//防守篮板效率
					map.get("stealEf"),				//抢断效率
					map.get("assistEf")			//助攻效率
			};
		}
		panel.setAdvancedDataTable(advancedData);
		teamAdvancedData = teamName;
		totalController.setTeamDataService(panel);
	}

	/**
	 * 设置球队参与的比赛
	 */
	public void setTeamGameLog(TeamDataService panel, String season, String teamName) {
		List<PlayerInMatchFull> matchList = dataReader.getTeamStatBySeason(teamName, season);
//		String[] properties = {"比赛日期","比赛对阵"};
		if(matchList!=null&&matchList.size()>0){
			//比赛排序
			for(int i=0;i<matchList.size()-1;i++){
				for(int j=0;j<matchList.size()-i-1;j++){
					if(matchList.get(j).getDate().before(matchList.get(j+1).getDate())){
						PlayerInMatchFull temp = matchList.get(j);
						matchList.set(j, matchList.get(j+1));
						matchList.set(j+1, temp);
					}
				}
			}
			//设置数据
			Object[][] values = new Object[matchList.size()][];
			for(int i=0;i<matchList.size();i++){
				PlayerInMatchFull match = matchList.get(i);
				Calendar date = new GregorianCalendar();
				date.setTime(match.getDate());
				int year = date.get(Calendar.YEAR);//减去差值
				int month = date.get(Calendar.MONTH)+1;
				int day = date.get(Calendar.DAY_OF_MONTH);
				StringBuffer dateBuffer = new StringBuffer(year+"-"+month+"-"+day);
				StringBuffer participentsBuffer = new StringBuffer(match.getTeam()+"  VS  "+match.getAttribute());
				values[i] = new Object[]{dateBuffer.toString(),participentsBuffer.toString()};
			}
			panel.setGameLog(values, null);
		}
		else{
			panel.setGameLog(new Object[0][2],null);
		}
		teamGameLog = teamName;
		totalController.setTeamDataService(panel);
	}

	/**
	 * 球队和联盟平均水平对比
	 */
	public void setTeamByLeagueCompare(TeamDataService panel,String season,String teamName){
		List<TeamAverage> teamList = dataReader.getTeamAverages(season);
		TeamAverage currentTeam = dataReader.getTeamAverageSingle(teamName, season,"total");
		Object[][] list = new Object[8][2];//平均得分，平均篮板，平均抢断，平均助攻，平均盖帽，投篮命中率，三分命中率，罚球命中率
		ArrayList<TeamAverage> teamDataList = new ArrayList<TeamAverage>();
		double score_total = 0;
		double rebound_total = 0;
		double steal_total = 0;
		double assist_total = 0;
		double block_total = 0;
		double fieldGoalMade_total = 0;
		double fieldGoalAttempted_total = 0;
		double threePointerMade_total = 0;
		double threePointerAttempted_total = 0;
		double freeThrowMade_total = 0;
		double freeThrowAttempted_total = 0;
		int team_number = 0;
		for(TeamAverage team:teamList){
			if(String.valueOf(team.generateMap().get("league")).equals(String.valueOf(currentTeam.generateMap().get("league")))){
				teamDataList.add(team);
			}
		}
		for(TeamAverage team:teamDataList){
			if(!String.valueOf(team.generateMap().get("attribute")).equals("totals")){
				continue;
			}
			Map<String,Object> map = team.generateMap();
			score_total += Double.parseDouble(String.valueOf(map.get("points")));
			rebound_total += Double.parseDouble(String.valueOf(map.get("rebound")));
			steal_total += Double.parseDouble(String.valueOf(map.get("steal")));
			assist_total += Double.parseDouble(String.valueOf(map.get("assist")));
			block_total += Double.parseDouble(String.valueOf(map.get("block")));
			fieldGoalMade_total += Double.parseDouble(String.valueOf(map.get("fieldGoalMade")));
			fieldGoalAttempted_total += Double.parseDouble(String.valueOf(map.get("fieldGoalAttempted")));
			threePointerMade_total += Double.parseDouble(String.valueOf(map.get("threePointerMade")));
			threePointerAttempted_total += Double.parseDouble(String.valueOf(map.get("threePointerAttempted")));
			freeThrowMade_total += Double.parseDouble(String.valueOf(map.get("freeThrowMade")));
			freeThrowAttempted_total += Double.parseDouble(String.valueOf(map.get("freeThrowAttempted")));
			team_number += Integer.parseInt(String.valueOf(map.get("lose")))+Integer.parseInt(String.valueOf(map.get("win")));
		}
		Map<String,Object> map_ct = currentTeam.generateMap();
		list[0][0] = map_ct.get("points");
		list[0][1] = map_ct.get("rebound");
		list[0][2] = map_ct.get("assist");
		list[0][3] = map_ct.get("steal");
		list[0][4] = map_ct.get("block");
		list[0][5] = map_ct.get("fieldGoalPercentage");
		list[0][6] = map_ct.get("threePointerPercentage");
		list[0][7] = map_ct.get("freeThrowRate");
		list[1][0] = DealDecimal.formatChange((score_total)/((double)team_number),1);
		list[1][1] = DealDecimal.formatChange((rebound_total)/((double)team_number),1);
		list[1][2] = DealDecimal.formatChange((assist_total)/((double)team_number),1);
		list[1][3] = DealDecimal.formatChange((steal_total)/((double)team_number),1);
		list[1][4] = DealDecimal.formatChange((block_total)/((double)team_number),1);
		list[1][5] = DealDecimal.formatChangeToPercentage((fieldGoalMade_total)/(fieldGoalAttempted_total));
		list[1][6] = DealDecimal.formatChangeToPercentage((threePointerMade_total)/(threePointerAttempted_total));
		list[1][7] = DealDecimal.formatChangeToPercentage((freeThrowMade_total)/(freeThrowAttempted_total));
		//设置界面 TODO
	}
}
