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
	 * 设置热点界面
	 */
	public void setHotEventPanel(SpecialViewService panel) {
		//设置当天热点球员
		setBestPlayerForDayPanel(panel);
		//设置赛季热点球员
		setBestPlayerForSeasonPanel(panel);
		//设置赛季热点球队
		setBestTeamForSeasonPanel(panel);
		//设置5佳进步球员
		setProgressedPlayerPanel(panel);
	}
	
	/**
	 * 设置当天热点球员界面
	 */
	public void setBestPlayerForDayPanel(SpecialViewService panel) {
//		String[] properties = {"球员名称","所属球队","球员位置","该项数据"};
		//得分
		Object[][] player_points = formPlayerValuesForDay(14);
		panel.setHotPlayerToday("得分", player_points);
		//篮板
		Object[][] player_rebound = formPlayerValuesForDay(8);
		panel.setHotPlayerToday("篮板", player_rebound);
		//助攻
		Object[][] player_assist = formPlayerValuesForDay(9);
		panel.setHotPlayerToday("助攻", player_assist);
		//盖帽
		Object[][] player_block = formPlayerValuesForDay(11);
		panel.setHotPlayerToday("盖帽", player_block);
		//抢断
		Object[][] player_steal = formPlayerValuesForDay(10);
		panel.setHotPlayerToday("抢断", player_steal);
		
		totalController.setSpecialViewService(panel);
	}

	/**
	 * 设置赛季热点球员界面
	 */
	public void setBestPlayerForSeasonPanel(SpecialViewService panel) {
//		String[] properties = {"球员名称","所属球队","球员位置","该项数据"};
		//场均得分
		Object[][] player_points = formPlayerValuesForSeason(14);
		panel.setHotPlayerSeason("场均得分", player_points);
		//场均篮板
		Object[][] player_rebound = formPlayerValuesForSeason(8);
		panel.setHotPlayerSeason("场均篮板", player_rebound);
		//场均助攻
		Object[][] player_assist = formPlayerValuesForSeason(9);
		panel.setHotPlayerSeason("场均助攻", player_assist);
		//场均盖帽
		Object[][] player_block = formPlayerValuesForSeason(11);
		panel.setHotPlayerSeason("场均盖帽", player_block);
		//场均抢断
		Object[][] player_steal = formPlayerValuesForSeason(10);
		panel.setHotPlayerSeason("场均抢断", player_steal);
		//投篮命中率
		Object[][] player_fieldGoalRatio = formPlayerValuesForSeason(29);
		panel.setHotPlayerSeason("投篮命中率", player_fieldGoalRatio);
		//三分命中率
		Object[][] player_threePointerRatio = formPlayerValuesForSeason(28);
		panel.setHotPlayerSeason("三分命中率", player_threePointerRatio);
		//罚球命中率
		Object[][] player_freeThrowRatio = formPlayerValuesForSeason(27);
		panel.setHotPlayerSeason("罚球命中率", player_freeThrowRatio);
	}

	/**
	 * 设置赛季热点球队界面
	 */
	public void setBestTeamForSeasonPanel(SpecialViewService panel) {
//		String[] properties = {"球队名称","所属联盟","该项数据"};
		//场均得分
		Object[][] team_score = formTeamValuesForSeason(14);
		panel.setHotTeamSeason("场均得分", team_score);
		//场均篮板
		Object[][] team_rebound = formTeamValuesForSeason(8);
		panel.setHotTeamSeason("场均篮板", team_rebound);
		//场均助攻
		Object[][] team_assist= formTeamValuesForSeason(9);
		panel.setHotTeamSeason("场均助攻", team_assist);
		//场均盖帽
		Object[][] team_block = formTeamValuesForSeason(11);
		panel.setHotTeamSeason("场均盖帽", team_block);
		//场均抢断
		Object[][] team_steal = formTeamValuesForSeason(10);
		panel.setHotTeamSeason("场均抢断", team_steal);
		//投篮命中率
		Object[][] team_fieldGoalRatio = formTeamValuesForSeason(21);
		panel.setHotTeamSeason("投篮命中率", team_fieldGoalRatio);
		//三分命中率
		Object[][] team_threePointerRatio = formTeamValuesForSeason(22);
		panel.setHotTeamSeason("三分命中率", team_threePointerRatio);
		//罚球命中率
		Object[][] team_freeThrowRatio = formTeamValuesForSeason(23);
		panel.setHotTeamSeason("罚球命中率", team_freeThrowRatio);
	}

	/**
	 * 设置5佳进步球员界面
	 */
	public void setProgressedPlayerPanel(SpecialViewService panel) {
//		String[] properties = {"球员姓名","所属球队","该项数据","近5场提升率"};
		//场均得分
		Object[][] values_score = formPlayerValuesForImprove(21);
		panel.setProgressPlayer("场均得分", values_score);
		//场均篮板
		Object[][] values_rebound = formPlayerValuesForImprove(22);
		panel.setProgressPlayer("场均篮板", values_rebound);
		//场均助攻
		Object[][] values_assist = formPlayerValuesForImprove(23);
		panel.setProgressPlayer("场均助攻", values_assist);
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
		return player_condition;
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
				player_condition[i] = new Object[]{player.getName(),player.getTeamName(),position,data};
				break;
			case 29:
			case 28:
			case 27:
				String data_percent = DealDecimal.formatChangeToPercentage(player.getStatsAverage()[condition]);
				player_condition[i] = new Object[]{player.getName(),player.getTeamName(),position,data_percent};
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
			int dataLoc = -1;
			switch(condition){
			case 21:
				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5ScoreAdv());
				dataLoc = 14;
				break;
			case 22:
				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5BlockAdv());
				dataLoc = 8;
				break;
			case 23:
				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5AssistAdv());
				dataLoc = 9;
			}
			player_condition[i] = new Object[]{player.getName(),player.getTeamName()
					,DealDecimal.formatChange(player.getStatsAverage()[dataLoc], 1),improvement};
		}
		return player_condition;
	}

}
