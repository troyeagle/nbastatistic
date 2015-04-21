package njuse.ffff.presenter.teamController;

import java.util.ArrayList;

import njuse.ffff.data.SeasonStatProcessor;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.teamService.TeamCompareService;
import njuse.ffff.uiservice.TeamsOverviewService;
import njuse.ffff.util.DealDecimal;

public class TeamCompareController implements TeamCompareService{
	private DataReaderService dataService;
	private static TeamCompareController teamCompareController = null;
	private static TotalUIController totalController = null;
	
	private String[] seasonList = {"14-15","13-14","12-13"};
	
//	private static final Filter emptyFilter;
//
//	static {
//		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
//	}
	
	private TeamCompareController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}

	public static TeamCompareController getInstance() {
		if (teamCompareController == null) {
			teamCompareController = new TeamCompareController();
		}
		return teamCompareController;
	}

	/**
	 * 设置球队信息横向比较界面
	 */
	public void setTeamCompareInfoForSeason(TeamsOverviewService panel) {
		//获取所有球队信息
		for(String season:seasonList){
			SeasonStatProcessor seasonStatProcessor = dataService.getSeasonStatProcessor(season);
			if(seasonStatProcessor==null){
				continue;
			}
//			ArrayList<TeamInAverage> teams = seasonStatProcessor.getTeamInAverage();
			ArrayList<TeamPO> teamPOs = dataService.getTeamInfoAll(null);
			ArrayList<TeamInAverage> teams = new ArrayList<TeamInAverage>();
			for(TeamPO team:teamPOs){
				TeamInAverage t = dataService.getTeamAverage(team.getName(), null);
				teams.add(t);
			}
			
	//		String[] propertices_total = {"球队名称","缩写","比赛场数","投篮命中数","投篮出手数"
	//				,"三分命中数","三分出手数","罚球命中数","罚球出手数","进攻篮板数","防守篮板数","篮板数"
	//				,"助攻数","抢断数","盖帽数","失误数","犯规数","比赛得分"};
	//		String[] propertices_average = {"球队名称","缩写","比赛场数","投篮命中数","投篮出手数"
	//				,"三分命中数","三分出手数","罚球命中数","罚球出手数","进攻篮板数","防守篮板数","篮板数"
	//				,"助攻数","抢断数","盖帽数","失误数","犯规数","比赛得分","投篮命中率","三分命中率"
	//				,"罚球命中率","进攻回合","进攻效率","防守效率","进攻篮板效率","防守篮板效率"
	//				,"抢断效率","助攻效率"};
			Object[][] values_total = new Object[teams.size()][];
			Object[][] values_average = new Object[teams.size()][];
			for(int i = 0;i <teams.size(); i++){
				TeamInAverage teamAvg = teams.get(i);
				if(teamAvg==null){
					continue;
				}
				
				double[] total = teamAvg.getStatsTotal();
				double[] average = teamAvg.getStatsAverage();
				values_total[i] = new Object[] {
						teamAvg.getName(),teamAvg.getAbbr(),teamAvg.getNumOfMatches()		//球队名，球队比赛场数
						,DealDecimal.formatChange(total[0])				//投篮命中数
						,DealDecimal.formatChange(total[1])				//投篮出手数
						,DealDecimal.formatChange(total[2])				//三分命中数
						,DealDecimal.formatChange(total[3])				//三分出手数
						,DealDecimal.formatChange(total[4])				//罚球命中数
						,DealDecimal.formatChange(total[5])				//罚球出手数
						,DealDecimal.formatChange(total[6])				//进攻篮板数
						,DealDecimal.formatChange(total[7])				//防守篮板数
						,DealDecimal.formatChange(total[8])				//篮板数
						,DealDecimal.formatChange(total[9])				//助攻数
						,DealDecimal.formatChange(total[10])			//抢断数
						,DealDecimal.formatChange(total[11])			//盖帽数
						,DealDecimal.formatChange(total[12])			//失误数
						,DealDecimal.formatChange(total[13])			//犯规数
						,DealDecimal.formatChange(total[14])			//得分
				};
				values_average[i] = new Object[] {
						teamAvg.getName(),teamAvg.getAbbr(),teamAvg.getNumOfMatches()							//球队名称，球队比赛场数
						,DealDecimal.formatChange(average[0], 1)			//投篮命中数
						,DealDecimal.formatChange(average[1], 1)		//投篮出手数
						,DealDecimal.formatChange(average[2], 1)			//三分命中数
						,DealDecimal.formatChange(average[3], 1)	//三分出手数
						,DealDecimal.formatChange(average[4], 1)			//罚球命中数
						,DealDecimal.formatChange(average[5], 1)		//罚球出手数
						,DealDecimal.formatChange(average[6], 1)			//进攻篮板数
						,DealDecimal.formatChange(average[7], 1)			//防守篮板数
						,DealDecimal.formatChange(average[8], 1)					//篮板数
						,DealDecimal.formatChange(average[9], 1)					//助攻数
						,DealDecimal.formatChange(average[10], 1)					//抢断数
						,DealDecimal.formatChange(average[11], 1)					//盖帽数
						,DealDecimal.formatChange(average[12], 1)					//失误数
						,DealDecimal.formatChange(average[13], 1)						//犯规数
						,DealDecimal.formatChange(average[14], 1)					//得分
						,DealDecimal.formatChangeToPercentage(average[21])			//投篮命中率
						,DealDecimal.formatChangeToPercentage(average[22])		//三分命中率
						,DealDecimal.formatChangeToPercentage(average[23])			//罚球命中率
						,DealDecimal.formatChange(average[24], 1)					//进攻回合
						,DealDecimal.formatChange(average[25], 1)				//进攻效率
						,DealDecimal.formatChange(average[26], 1)				//防守效率
						,DealDecimal.formatChange(average[27], 1)		//进攻篮板效率
						,DealDecimal.formatChange(average[28], 1)		//防守篮板效率
						,DealDecimal.formatChange(average[29], 1)					//抢断效率
						,DealDecimal.formatChange(average[30], 1)					//助攻效率
				};
			}
			
			panel.setTeamsTotalInfo(values_total,season);
			panel.setTeamsAvgInfo(values_average,season);
			
			totalController.setTeamsOverviewService(panel);
		}
//		TeamComparePanel teamComparePanel = new TeamComparePanel();
//		teamComparePanel.setTeamsInfo(propertices_total,values_total,propertices_average,values_average);
		
//		totalController.addCurrentPanel(teamComparePanel);
//		totalController.switchToPanel(teamComparePanel);
	}
}
