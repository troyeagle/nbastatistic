package njuse.ffff.presenter.teamController;

import java.util.ArrayList;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.teamService.TeamCompareService;
import njuse.ffff.ui.TeamComparePanel;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;

public class TeamCompareController implements TeamCompareService{
	private DataReaderService dataService;
	private static TeamCompareController teamCompareController = null;
	private static TotalUIController totalController = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
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
	public void setTeamComparePanel() {
		//获取所有球队信息
		ArrayList<TeamPO> data = dataService.getTeamInfoAll(emptyFilter);
		
		String[] propertices_total = {"球队名称","比赛场数","投篮命中数","投篮出手数"
				,"三分命中数","三分出手数","罚球命中数","罚球出手数","进攻篮板数","防守篮板数","篮板数"
				,"助攻数","抢断数","盖帽数","失误数","犯规数","比赛得分","投篮命中率","三分命中率"
				,"罚球命中率","胜率","进攻回合","进攻效率","防守效率","进攻篮板效率","防守篮板效率"
				,"抢断效率","助攻率"};
		String[] propertices_average = {"球队名称","比赛场数","投篮命中数","投篮出手数"
				,"三分命中数","三分出手数","罚球命中数","罚球出手数","进攻篮板数","防守篮板数","篮板数"
				,"助攻数","抢断数","盖帽数","失误数","犯规数","比赛得分","投篮命中率","三分命中率"
				,"罚球命中率","进攻回合","进攻效率","防守效率","进攻篮板效率","防守篮板效率"
				,"抢断效率","助攻率"};
		Object[][] values_total = new Object[data.size()][];
		Object[][] values_average = new Object[data.size()][];
		for(int i = 0;i <data.size(); i++){
			TeamPO team = data.get(i);
			TeamInAverage teamAvg = dataService.getTeamAverage(team.getName(), emptyFilter);
			if(teamAvg==null){
				values_total[i] = new Object[] {team.getName(),"","","","","","","","","","","","","","","",""
						,"","","","","","","","","","",""};
				values_average[i] = new Object[] {team.getName(),"","","","","","","","","","","","","","","",""
						,"","","","","","","","","",""};
				continue;
			}
			
			int GP = teamAvg.getNumOfMatches();
			values_total[i] = new Object[] {team.getName(),teamAvg.getNumOfMatches(),teamAvg.getFieldGoalMade()*GP
					,teamAvg.getFieldGoalAttempted()*GP,teamAvg.getThreePointerMade()*GP,teamAvg.getThreePointerAttempted()*GP
					,teamAvg.getFreeThrowMade()*GP,teamAvg.getFieldGoalAttempted()*GP,teamAvg.getOffensiveRebound()*GP
					,teamAvg.getDefensiveRebound()*GP,teamAvg.getRebound()*GP,teamAvg.getAssist()*GP,teamAvg.getSteal()*GP
					,teamAvg.getBlock()*GP,teamAvg.getTurnover()*GP,teamAvg.getFoul()*GP,teamAvg.getScores()*GP
					,DealDecimal.formatChange(teamAvg.getFieldGoalRatio(), 3),DealDecimal.formatChange(teamAvg.getThreePointerRatio(), 3)
					,DealDecimal.formatChange(teamAvg.getFreeThrowRatio(), 3),DealDecimal.formatChange(teamAvg.getWinningRatio(), 3)
					,DealDecimal.formatChange(teamAvg.getMyRounds()*GP, 3),DealDecimal.formatChange(teamAvg.getOffensiveEf(), 3)
					,DealDecimal.formatChange(teamAvg.getDefensiveEf(), 3)
					,DealDecimal.formatChange(teamAvg.getOffensiveReboundEf(), 3),DealDecimal.formatChange(teamAvg.getDefensiveReboundEf(), 3)
					,DealDecimal.formatChange(teamAvg.getStealEf(), 3),DealDecimal.formatChange(teamAvg.getAssistEf(), 3)
			};
			values_average[i] = new Object[] {team.getName(),teamAvg.getNumOfMatches(),DealDecimal.formatChange(teamAvg.getFieldGoalMade(), 3)
					,DealDecimal.formatChange(teamAvg.getFieldGoalAttempted(), 3),DealDecimal.formatChange(teamAvg.getThreePointerMade(), 3)
					,DealDecimal.formatChange(teamAvg.getThreePointerAttempted(), 3)
					,DealDecimal.formatChange(teamAvg.getFreeThrowMade(), 3)
					,DealDecimal.formatChange(teamAvg.getFieldGoalAttempted(), 3)
					,DealDecimal.formatChange(teamAvg.getOffensiveRebound(), 3)
					,DealDecimal.formatChange(teamAvg.getDefensiveRebound(), 3)
					,DealDecimal.formatChange(teamAvg.getRebound(), 3)
					,DealDecimal.formatChange(teamAvg.getAssist(), 3),DealDecimal.formatChange(teamAvg.getSteal(), 3)
					,DealDecimal.formatChange(teamAvg.getBlock(), 3),DealDecimal.formatChange(teamAvg.getTurnover(), 3)
					,DealDecimal.formatChange(teamAvg.getFoul(), 3),DealDecimal.formatChange(teamAvg.getScores(), 3)
					,DealDecimal.formatChange(teamAvg.getFieldGoalRatio(), 3)
					,DealDecimal.formatChange(teamAvg.getThreePointerRatio(), 3)
					,DealDecimal.formatChange(teamAvg.getFreeThrowRatio(), 3)
					,DealDecimal.formatChange(teamAvg.getMyRounds(), 3)
					,DealDecimal.formatChange(teamAvg.getOffensiveEf(), 3),DealDecimal.formatChange(teamAvg.getDefensiveEf(), 3)
					,DealDecimal.formatChange(teamAvg.getOffensiveReboundEf(), 3)
					,DealDecimal.formatChange(teamAvg.getDefensiveReboundEf(), 3)
					,DealDecimal.formatChange(teamAvg.getStealEf(), 3)
					,DealDecimal.formatChange(teamAvg.getAssistEf(), 3)
			};
		}
		TeamComparePanel teamComparePanel = new TeamComparePanel();
		teamComparePanel.setTeamsInfo(propertices_total,values_total,propertices_average,values_average);
		
		totalController.addCurrentPanel(teamComparePanel);
		totalController.switchToPanel(teamComparePanel);
	}
}
