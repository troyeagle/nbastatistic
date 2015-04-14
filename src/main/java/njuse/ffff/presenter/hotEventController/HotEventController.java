package njuse.ffff.presenter.hotEventController;

import java.util.ArrayList;
import java.util.List;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.hotEventService.HotEventService;
import njuse.ffff.uiservice.SpecialViewService;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;

public class HotEventController implements HotEventService{
	private DataReaderService dataService;
	private static HotEventController hotEventController = null;
	private static TotalUIController totalController = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private HotEventController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}

	public static HotEventController getInstance() {
		if (hotEventController == null) {
			hotEventController = new HotEventController();
		}
		return hotEventController;
	}
	
	/**
	 * 设置当天热点球员界面
	 */
	public void setBestPlayerForDayPanel() {
		String[] properties = {"球员名称","所属球队","球员位置","该项数据"};
		//得分
		Object[][] player_points = formPlayerValuesForDay(14);
		//篮板
		Object[][] player_rebound = formPlayerValuesForDay(8);
		//助攻
		Object[][] player_assist = formPlayerValuesForDay(9);
		//盖帽
		Object[][] player_block = formPlayerValuesForDay(11);
		//抢断
		Object[][] player_steal = formPlayerValuesForDay(10);
		
	}

	/**
	 * 设置赛季热点球员界面
	 */
	public void setBestPlayerForSeasonPanel() {
		String[] properties = {"球员名称","所属球队","球员位置","该项数据"};
		//场均得分
		Object[][] player_points = formPlayerValuesForSeason(14);
		//场均篮板
		Object[][] player_rebound = formPlayerValuesForSeason(8);
		//场均助攻
		Object[][] player_assist = formPlayerValuesForSeason(9);
		//场均盖帽
		Object[][] player_block = formPlayerValuesForSeason(11);
		//场均抢断
		Object[][] player_steal = formPlayerValuesForSeason(10);
		//投篮命中率
		Object[][] player_fieldGoalRatio = formPlayerValuesForSeason(29);
		//三分命中率
		Object[][] player_threePointerRatio = formPlayerValuesForSeason(28);
		//罚球命中率
		Object[][] player_freeThrowRatio = formPlayerValuesForSeason(27);
		
		
	}

	/**
	 * 设置赛季热点球队界面
	 */
	public void setBestTeamForSeasonPanel() {
		String[] properties = {"球队名称","所属联盟","该项数据"};
		//场均得分
		Object[][] team_score = formTeamValuesForSeason(14);
		//场均篮板
		Object[][] team_rebound = formTeamValuesForSeason(8);
		//场均助攻
		Object[][] team_assist= formTeamValuesForSeason(9);
		//场均盖帽
		Object[][] team_block = formTeamValuesForSeason(11);
		//场均抢断
		Object[][] team_steal = formTeamValuesForSeason(10);
		//投篮命中率
		Object[][] team_fieldGoalRatio = formTeamValuesForSeason(21);
		//三分命中率
		Object[][] team_threePointerRatio = formTeamValuesForSeason(22);
		//罚球命中率
		Object[][] team_freeThrowRatio = formTeamValuesForSeason(23);
		
	}

	/**
	 * 设置5佳进步球员界面
	 */
	public void setProgressedPlayerPanel() {
		String[] properties = {"球员姓名","所属球队","该项数据","近5场提升率"};
		//场均得分
		Object[][] values_score = formPlayerValuesForImprove(14);
		//场均篮板
		Object[][] values_rebound = formPlayerValuesForImprove(8);
		//场均助攻
		Object[][] values_assist = formPlayerValuesForImprove(9);
	}
	
	/**
	 * 判断球员位置
	 * @param pos
	 * @return
	 */
	private String judgePlayerPosition(char pos){
		String position = null;
		switch(pos){
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
		return position;
	}
	
	/**
	 * 判断球队联盟
	 * @param league
	 * @return
	 */
	private String judgeTeamLeague(String league){
		if(league.equals("W")){
			return "西部";
		}
		else{
			return "东部";
		}
	}
	
	/**
	 * 形成当天热点球员数据表格
	 * @param condition
	 * @return
	 */
	private Object[][] formPlayerValuesForDay(int condition){
		List<PlayerInMatchExtended> list = dataService.getLeadPlayerForDay(dataService.getCurrentDate(), condition);
		Object[][] player_condition = new Object[5][];
		for(int i=0;i<5;i++){
			PlayerInMatchExtended player = list.get(i);
			PlayerInAverage playerAvg = dataService.getPlayerAverage(player.getName(), emptyFilter);
			String team = playerAvg.getTeamName();
			PlayerPO playerPO = dataService.getPlayerInfo(player.getName(), emptyFilter);
			String position = judgePlayerPosition(playerPO.getPosition());
			player_condition[i] = new Object[]{player.getName(),team,position
					,DealDecimal.formatChange(player.getArray()[condition])};
		}
		return null;
	}
	
	/**
	 * 形成赛季热点球员表格数据
	 * @param condition
	 * @return
	 */
	private Object[][] formPlayerValuesForSeason(int condition){
		List<PlayerInAverage> list = dataService.getLeadPlayerForSeason(dataService.getCurrentSeason(), condition);
		Object[][] player_condition = new Object[5][];
		for(int i=0;i<5;i++){
			PlayerInAverage player = list.get(i);
			PlayerPO playerPO = dataService.getPlayerInfo(player.getName(), emptyFilter);
			String position = judgePlayerPosition(playerPO.getPosition());
			switch(condition){
			case 14:
			case 8:
			case 9:
			case 11:
			case 10:
				double data = DealDecimal.formatChange(player.getStatsAverage()[condition], 1);
				player_condition[i] = new Object[]{i,player.getName(),player.getTeamName(),position,data};
				break;
			case 29:
			case 28:
			case 27:
				String data_percent = DealDecimal.formatChangeToPercentage(player.getStatsAverage()[condition]);
				player_condition[i] = new Object[]{i,player.getName(),player.getTeamName(),position,data_percent};
			}
		}
		return player_condition;
	}
	
	/**
	 * 形成赛季热点球队数据表格
	 * @param condition
	 * @return
	 */
	private Object[][] formTeamValuesForSeason(int condition){
		List<TeamInAverage> list = dataService.getLeadTeamForSeason(dataService.getCurrentSeason(), condition);
		Object[][] team_condition = new Object[5][];
		for(int i=0;i<5;i++){
			TeamInAverage team = list.get(i);
			TeamPO teamPO = dataService.getTeamInfo(team.getName(), emptyFilter);
			String league = judgeTeamLeague(teamPO.getLeague());
			switch(condition){
			case 14:
			case 8:
			case 9:
			case 11:
			case 10:
				double data = DealDecimal.formatChange(team.getStatsAverage()[condition], 1);
				team_condition[i] = new Object[]{team.getName(),league,data};
				break;
			case 21:
			case 22:
			case 23:
				String data_percent = DealDecimal.formatChangeToPercentage(team.getStatsAverage()[condition]);
				team_condition[i] = new Object[]{team.getName(),league,data_percent};
			}
			
		}
		return team_condition;
	}
	
	/**
	 * 形成进步最快球员数据表格
	 * @param condition
	 * @return
	 */
	private Object[][] formPlayerValuesForImprove(int condition){
		List<PlayerInAverage> list = dataService.getImprovePlayer(dataService.getCurrentSeason(), condition);
		Object[][] player_condition = new Object[5][];
		for(int i=0;i<5;i++){
			PlayerInAverage player = list.get(i);
			String improvement = null;
			switch(condition){
			case 14:
				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5ScoreAdv());
				break;
			case 8:
				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5BlockAdv());
				break;
			case 9:
				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5AssistAdv());
			}
			player_condition[i] = new Object[]{player.getName(),player.getTeamName()
					,DealDecimal.formatChange(player.getStatsAverage()[condition], 1),improvement};
		}
		return player_condition;
	}

	@Override
	public void setHotEventPanel(SpecialViewService panel) {
		// TODO 自动生成的方法存根
		
	}

}
