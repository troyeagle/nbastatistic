package njuse.ffff.presenter.teamController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import njuse.ffff.data.SeasonStatProcessor;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInMatch;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamInMatch;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.teamService.TeamInfoService;
import njuse.ffff.uiservice.TeamDataService;
import njuse.ffff.uiservice.TeamProfileService;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;
import njuse.ffff.util.TeamNameAndAbbr;

public class TeamInfoController implements TeamInfoService{
	private DataReaderService dataService;
	private static TeamInfoController teamInfoController = null;
	private static TotalUIController totalController = null;
	
	private String[] seasonList = {"14-15","13-14","12-13"};//赛季列表
	
	private String teamProfile = null;
	private String teamPlayer = null;
	private String teamTotalData = null;
	private String teamAvgData = null;
	private String teamAdvancedData = null;
	private String teamGameLog = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private TeamInfoController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
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
	 * 设置球队简介界面
	 */
	public void setTeamProfilePanel(TeamProfileService panel, String teamName) {
		//获取指定球队的信息
		TeamPO teamInfo = dataService.getTeamInfo(teamName, emptyFilter);
		//设置球队简介
		panel.setProfile(teamInfo.getName(), teamInfo.getAbbr(), teamInfo.getState()
						, teamInfo.getLeague(), teamInfo.getSubLeague()
						, teamInfo.getHomeCourt(), teamInfo.getTimeOfFoundation());
		teamProfile = teamInfo.getName();
		totalController.setTeamProfileService(panel);
	}
	
	/**
	 * 设置球队的球员
	 */
	public void setPlayerForTeam(TeamDataService panel, String teamName) {
		SeasonStatProcessor seasonProcess = dataService.getSeasonStatProcessor(dataService.getCurrentSeason());
		ArrayList<TeamInMatch> matches = seasonProcess.getTeamStatistics(teamName, emptyFilter);
		Object[][] playerForTeam = null;
		if(matches.size()>0){
			TeamInMatch match = matches.get(0);
			ArrayList<PlayerInMatch> players = match.getPlayers();
			playerForTeam = new Object[players.size()][];
			for(int i=0;i<players.size();i++){
				PlayerInMatch player = players.get(i);
				playerForTeam[i] = new Object[]{i,player.getName()};
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
			SeasonStatProcessor seasonProcess = dataService.getSeasonStatProcessor(valid_season.get(i));
			TeamInAverage team = seasonProcess.getTeamAverage(teamName, emptyFilter);
			double[] total = team.getStatsTotal();
			double[] average = team.getStatsAverage();
			totalData[i] = new Object[]{
					valid_season.get(i),		//赛季
//					team.getNumOfMatches(),		//比赛数
					DealDecimal.formatChangeToPercentage(average[21]),	//投篮命中率
					DealDecimal.formatChange(total[0]),					//投篮命中数
					DealDecimal.formatChange(total[1]),					//投篮出手数
					DealDecimal.formatChangeToPercentage(average[22]),	//三分命中率
					DealDecimal.formatChange(total[2]),					//三分命中数
					DealDecimal.formatChange(total[3]),					//三分出手数
					DealDecimal.formatChangeToPercentage(average[23]),	//罚球命中率
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
			SeasonStatProcessor seasonProcess = dataService.getSeasonStatProcessor(valid_season.get(i));
			TeamInAverage team = seasonProcess.getTeamAverage(teamName, emptyFilter);
			double[] average = team.getStatsAverage();
			averageData[i] = new Object[]{
					valid_season.get(i),		//赛季
					team.getNumOfMatches(),		//比赛数
					DealDecimal.formatChangeToPercentage(average[21]),		//投篮命中率
					DealDecimal.formatChange(average[0],1),					//投篮命中数
					DealDecimal.formatChange(average[1],1),					//投篮出手数
					DealDecimal.formatChangeToPercentage(average[22]),		//三分命中率
					DealDecimal.formatChange(average[2],1),					//三分命中数
					DealDecimal.formatChange(average[3],1),					//三分出手数
					DealDecimal.formatChangeToPercentage(average[23]),		//罚球命中率
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
					DealDecimal.formatChange(average[14],1)					//得分
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
			SeasonStatProcessor seasonProcess = dataService.getSeasonStatProcessor(valid_season.get(i));
			TeamInAverage team = seasonProcess.getTeamAverage(teamName, emptyFilter);
			double[] average = team.getStatsAverage();
			advancedData[i] = new Object[]{
					valid_season.get(i),		//赛季
					DealDecimal.formatChange(average[24],1),	//进攻回合
					DealDecimal.formatChange(average[25],1),				//进攻效率
					DealDecimal.formatChange(average[26],1),				//防守效率
					DealDecimal.formatChange(average[27],1),	//进攻篮板效率
					DealDecimal.formatChange(average[28],1),				//防守篮板效率
					DealDecimal.formatChange(average[29],1),				//抢断效率
					DealDecimal.formatChange(average[30],1)			//助攻效率
			};
		}
		panel.setAdvancedDataTable(advancedData);
		teamAdvancedData = teamName;
		totalController.setTeamDataService(panel);
	}

	/**
	 * 设置球队参与的比赛
	 */
	public void setTeamGameLog(TeamDataService panel, String teamName) {
		String teamAbbr = TeamNameAndAbbr.getInstance().getAbbr(teamName);
		if(teamAbbr==null){
			teamAbbr = teamName;
		}
		List<MatchPO> matchList = dataService.getMatchForTeam(teamAbbr);
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
			panel.setGameLog(new Object[0][2],null);
		}
		teamGameLog = teamName;
		totalController.setTeamDataService(panel);
	}

}
