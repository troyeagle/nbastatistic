package njuse.ffff.presenter.teamController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.teamService.TeamCompareService;
import njuse.ffff.sqlpo.TeamAverage;
import njuse.ffff.sqlpo.TeamAverageAdv;
import njuse.ffff.uiservice.TeamsOverviewService;
import njuse.ffff.util.DealDecimal;

public class TeamCompareController implements TeamCompareService{
	private NewDataReaderService dataReader;
	private static TeamCompareController teamCompareController = null;
	private static TotalUIController totalController = null;
	
	private String[] seasonList;
	
//	private static final Filter emptyFilter;
//
//	static {
//		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
//	}
	
	private TeamCompareController() {
		totalController = TotalUIController.getInstance();
		dataReader = totalController.getDataReader();
		seasonList = totalController.getAllSeasons();
	}

	public static TeamCompareController getInstance() {
		if (teamCompareController == null) {
			teamCompareController = new TeamCompareController();
		}
		return teamCompareController;
	}

	public String[] getAllSeasons() {
		return seasonList;
	}

	/**
	 * 设置球队信息横向比较界面
	 */
	public void setTeamCompareInfoForSeason(TeamsOverviewService panel,String season) {
		//获取所有球队信息
		List<TeamAverage> teamList = totalController.getTeamsInSeason(season);
		if(teamList==null){
			teamList = dataReader.getTeamAverages(season);
		}
//		String[] propertices_total = {"队名", "胜场", "负场", "命中",
//				"出手", "三分", "出手", "罚球", "出手", "进攻板",
//				"防守板", "篮板", "助攻", "抢断", "盖帽", "失误", "犯规", "得分"};
//		String[] propertices_average = {"队名", "胜场", "负场",
//				"总命中数", "总出手", "命中%", "三分", "出手", "命中%", "罚球", "出手", "命中%",
//				"进攻篮板", "进攻篮板率", "防守篮板", "防守篮板率", "篮板", "助攻", "助攻率",
//				"抢断", "抢断率", "盖帽", "失误", "犯规", "得分", "进攻回合", "进攻效率", "防守效率"};
		ArrayList<TeamAverage> teams_total = new ArrayList<TeamAverage>();
		ArrayList<TeamAverage> teams_avg = new ArrayList<TeamAverage>();
		for(TeamAverage t:teamList){
			String judge = String.valueOf(t.generateMap().get("attribute"));
			if(judge.equals("total")){
				teams_total.add(t);
			}
			if(judge.equals("pergame")){
				teams_avg.add(t);
			}
		}
		Object[][] values_total = new Object[teams_total.size()][];
		Object[][] values_average = new Object[teams_avg.size()][];
		for(int i = 0;i <teams_total.size(); i++){
			TeamAverage teamTotal = teams_total.get(i);
			if(teamTotal==null){
				continue;
			}
			
			Map<String,Object> map_total = teamTotal.generateMap();
			values_total[i] = new Object[] {
					map_total.get("name"),map_total.get("win"),map_total.get("lose")	//球队名，球队比赛场数
					,map_total.get("fieldGoalMade")				//投篮命中数
					,map_total.get("fieldGoalAttempted")				//投篮出手数
					,map_total.get("threePointerMade")				//三分命中数
					,map_total.get("threePointerAttempted")				//三分出手数
					,map_total.get("freeThrowMade")			//罚球命中数
					,map_total.get("freeThrowAttempted")				//罚球出手数
					,map_total.get("offensiveRebound")			//进攻篮板数
					,map_total.get("defensiveRebound")				//防守篮板数
					,map_total.get("rebound")				//篮板数
					,map_total.get("assist")				//助攻数
					,map_total.get("steal")			//抢断数
					,map_total.get("block")			//盖帽数
					,map_total.get("turnover")			//失误数
					,map_total.get("foul")			//犯规数
					,map_total.get("points")			//得分
			};
		}
		for(int i = 0;i <teams_avg.size(); i++){
			TeamAverage teamAvg = teams_avg.get(i);
			if(teamAvg==null){
				continue;
			}
			Map<String,Object> map_avg = teamAvg.generateMap();
			String name = String.valueOf(map_avg.get("name"));
			TeamAverageAdv teamAdv = dataReader.getTeamAverageAdv(name, season, null);
			if(teamAdv==null){
				continue;
			}
			Map<String,Object> map_adv = teamAdv.generateMap();
			values_average[i] = new Object[] {
					name,map_avg.get("win"),map_avg.get("lose")							//球队名称，球队比赛场数
					,map_avg.get("fieldGoalMade")				//投篮命中数
					,map_avg.get("fieldGoalAttempted")				//投篮出手数
					,DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map_avg.get("fieldGoalPercentage"))))
					,map_avg.get("threePointerMade")				//三分命中数
					,map_avg.get("threePointerAttempted")				//三分出手数
					,DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map_avg.get("threePointerPercentage"))))
					,map_avg.get("freeThrowMade")			//罚球命中数
					,map_avg.get("freeThrowAttempted")				//罚球出手数
					,DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map_avg.get("freeThrowRate"))))
					,map_avg.get("offensiveRebound")			//进攻篮板数
					,String.valueOf(map_adv.get("offensiveReboundEf")).concat("%")		//进攻篮板效率
					,map_avg.get("defensiveRebound")			//防守篮板数
					,String.valueOf(map_adv.get("defensiveReboundEf")).concat("%")		//防守篮板效率
					,map_avg.get("rebound")					//篮板数
					,map_avg.get("assist")					//助攻数
					,String.valueOf(map_adv.get("assistEf")).concat("%")					//助攻效率
					,map_avg.get("steal")					//抢断数
					,String.valueOf(map_adv.get("stealEf")).concat("%")					//抢断效率
					,map_avg.get("block")					//盖帽数
					,map_avg.get("turnover")					//失误数
					,map_avg.get("foul")						//犯规数
					,map_avg.get("points")					//得分
					,String.valueOf(map_adv.get("myRounds")).concat("%")					//进攻回合
					,String.valueOf(map_adv.get("offensiveEf")).concat("%")					//进攻效率
					,String.valueOf(map_adv.get("defensiveEf")).concat("%")					//防守效率
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
