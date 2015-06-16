package njuse.ffff.presenter.hotEventController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.hotEventService.HotEventService;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.sqlpo.TeamAverage;
import njuse.ffff.uiservice.SpecialViewService;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;

public class HotEventController implements HotEventService{
//	private DataReaderService dataService;
	private NewDataReaderService dataReader;
	private static HotEventController hotEventController = null;
	private static TotalUIController totalController = null;
	
	@SuppressWarnings("unused")
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private HotEventController() {
		totalController = TotalUIController.getInstance();
//		dataService = totalController.getDataReadController();
		dataReader = totalController.getDataReader();
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
		Object[][] player_points = formPlayerValuesForDay("points");
		panel.setHotPlayerToday("得分", player_points);
		//篮板
		Object[][] player_rebound = formPlayerValuesForDay("rebound");
		panel.setHotPlayerToday("篮板", player_rebound);
		//助攻
		Object[][] player_assist = formPlayerValuesForDay("assist");
		panel.setHotPlayerToday("助攻", player_assist);
		//盖帽
		Object[][] player_block = formPlayerValuesForDay("block");
		panel.setHotPlayerToday("盖帽", player_block);
		//抢断
		Object[][] player_steal = formPlayerValuesForDay("steal");
		panel.setHotPlayerToday("抢断", player_steal);
		
		totalController.setSpecialViewService(panel);
	}

	/**
	 * 设置赛季热点球员界面
	 */
	public void setBestPlayerForSeasonPanel(SpecialViewService panel) {
//		String[] properties = {"球员名称","所属球队","球员位置","该项数据"};
		//场均得分
		Object[][] player_points = formPlayerValuesForSeason("points");
		panel.setHotPlayerSeason("场均得分", player_points);
		//场均篮板
		Object[][] player_rebound = formPlayerValuesForSeason("rebound");
		panel.setHotPlayerSeason("场均篮板", player_rebound);
		//场均助攻
		Object[][] player_assist = formPlayerValuesForSeason("assist");
		panel.setHotPlayerSeason("场均助攻", player_assist);
		//场均盖帽
		Object[][] player_block = formPlayerValuesForSeason("block");
		panel.setHotPlayerSeason("场均盖帽", player_block);
		//场均抢断
		Object[][] player_steal = formPlayerValuesForSeason("steal");
		panel.setHotPlayerSeason("场均抢断", player_steal);
		//投篮命中率
		Object[][] player_fieldGoalRatio = formPlayerValuesForSeason("fieldGoalPercentage");
		panel.setHotPlayerSeason("投篮命中率", player_fieldGoalRatio);
		//三分命中率
		Object[][] player_threePointerRatio = formPlayerValuesForSeason("threePointerPercentage");
		panel.setHotPlayerSeason("三分命中率", player_threePointerRatio);
		//罚球命中率
		Object[][] player_freeThrowRatio = formPlayerValuesForSeason("freeThrowMade");
		panel.setHotPlayerSeason("罚球命中率", player_freeThrowRatio);
	}

	/**
	 * 设置赛季热点球队界面
	 */
	public void setBestTeamForSeasonPanel(SpecialViewService panel) {
//		String[] properties = {"球队名称","所属联盟","该项数据"};
		//场均得分
		Object[][] team_score = formTeamValuesForSeason("points");
		panel.setHotTeamSeason("场均得分", team_score);
		//场均篮板
		Object[][] team_rebound = formTeamValuesForSeason("rebound");
		panel.setHotTeamSeason("场均篮板", team_rebound);
		//场均助攻
		Object[][] team_assist= formTeamValuesForSeason("assist");
		panel.setHotTeamSeason("场均助攻", team_assist);
		//场均盖帽
		Object[][] team_block = formTeamValuesForSeason("rebound");
		panel.setHotTeamSeason("场均盖帽", team_block);
		//场均抢断
		Object[][] team_steal = formTeamValuesForSeason("steal");
		panel.setHotTeamSeason("场均抢断", team_steal);
		//投篮命中率
		Object[][] team_fieldGoalRatio = formTeamValuesForSeason("fieldGoalPercentage");
		panel.setHotTeamSeason("投篮命中率", team_fieldGoalRatio);
		//三分命中率
		Object[][] team_threePointerRatio = formTeamValuesForSeason("threePointerPercentage");
		panel.setHotTeamSeason("三分命中率", team_threePointerRatio);
		//罚球命中率
		Object[][] team_freeThrowRatio = formTeamValuesForSeason("freeThrowMade");
		panel.setHotTeamSeason("罚球命中率", team_freeThrowRatio);
	}

	/**
	 * 设置5佳进步球员界面
	 */
	public void setProgressedPlayerPanel(SpecialViewService panel) {
//		String[] properties = {"球员姓名","所属球队","该项数据","近5场提升率"};
		//场均得分
		Object[][] values_score = formPlayerValuesForImprove("points");
		panel.setProgressPlayer("场均得分", values_score);
		//场均篮板
		Object[][] values_rebound = formPlayerValuesForImprove("rebound");
		panel.setProgressPlayer("场均篮板", values_rebound);
		//场均助攻
		Object[][] values_assist = formPlayerValuesForImprove("assist");
		panel.setProgressPlayer("场均助攻", values_assist);
	}
	
	/**
	 * 判断球员位置
	 * @param pos
	 * @return
	 */
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unused")
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
//	private Object[][] formPlayerValuesForDay(int condition){
//		List<PlayerInMatchExtended> list = dataService.getLeadPlayerForDay(dataService.getCurrentDate(), condition);
//		Object[][] player_condition = new Object[5][];
//		for(int i=0;i<Math.min(5, list.size());i++){
//			PlayerInMatchExtended player = list.get(list.size()-i-1);
//			PlayerInAverage playerAvg = dataService.getPlayerAverage(player.getName(), emptyFilter);
//			String team = "N/A";
//			if(playerAvg!=null){
//				team = playerAvg.getTeamName();
//			}
//			PlayerPO playerPO = dataService.getPlayerInfo(player.getName(), emptyFilter);
//			String position = "N/A";
//			if(playerPO!=null){
//				position = judgePlayerPosition(playerPO.getPosition()[0]);
//			}
//			player_condition[i] = new Object[]{player.getName(),team,position
//					,DealDecimal.formatChange(player.getArray()[condition])};
//		}
//		return player_condition;
//	}
	
	private Object[][] formPlayerValuesForDay(String condition){
		Date a = totalController.getCurrentDay();
		List<PlayerInMatchFull> list = dataReader.getLeadPlayerForDay(totalController.getCurrentDay(), condition);//dataService.getCurrentDate().getTime()
		Object[][] player_condition = new Object[5][];
		for(int i=0;i<Math.min(5, list.size());i++){
			PlayerInMatchFull player = list.get(i);
			Map<String,Object> map = player.generateBasicMap();
			player_condition[i] = new Object[]{player.getName(),map.get("team"),map.get("position")
					,map.get("condition")};
		}
		return player_condition;
	}
	
	/**
	 * 形成赛季热点球员表格数据
	 * @param condition
	 * @return
	 */
//	private Object[][] formPlayerValuesForSeason(int condition){
//		List<PlayerInAverage> list = dataService.getLeadPlayerForSeason(dataService.getCurrentSeason(), condition);
//		Object[][] player_condition = new Object[5][];
//		int num = list.size()-1;
//		for(int i=0;i<Math.min(5, list.size());i++){
//			PlayerInAverage player = list.get(num);
//			if(player.getEffective()>=58){
//				PlayerPO playerPO = dataService.getPlayerInfo(player.getName(), emptyFilter);
//				String position = "N/A";
//				if(playerPO!=null){
//					position = judgePlayerPosition(playerPO.getPosition()[0]);
//				}
//				switch(condition){
//				case 14:	//场均得分
//				case 8:		//场均篮板
//				case 9:		//场均助攻
//				case 11:	//场均盖帽
//				case 10:	//场均抢断
//					double data = DealDecimal.formatChange(player.getStatsAverage()[condition], 1);
//					player_condition[i] = new Object[]{player.getName(),player.getTeamName(),position,data};
//					break;
//				case 29:	//投篮命中率
//				case 28:	//三分命中率
//				case 27:	//罚球命中率
//					String data_percent = DealDecimal.formatChangeToPercentage(player.getStatsAverage()[condition]);
//					player_condition[i] = new Object[]{player.getName(),player.getTeamName(),position,data_percent};
//				}
//			}
//			else{
//				i--;
//			}
//			num--;
//		}
//		return player_condition;
//	}
	
	private Object[][] formPlayerValuesForSeason(String condition){
		List<PlayerInMatchFull> list = dataReader.getLeadPlayerForSeason(totalController.getCurrentSeason().substring(2,7), condition);
		Object[][] player_condition = new Object[5][];
		int num = 0;
		for(int i=0;i<Math.min(5, list.size());i++){
			PlayerInMatchFull player = list.get(num);
			Map<String,Object> map = player.generateBasicMap();
			if(Integer.parseInt(String.valueOf(map.get("gamesPlayed")))>=58){
				switch(condition){
				case "points":	//场均得分
				case "rebound":		//场均篮板
				case "assist":		//场均助攻
				case "block":	//场均盖帽
				case "steal":	//场均抢断
					player_condition[i] = new Object[]{player.getName(),map.get("team"),map.get("position"),map.get("condition")};
					break;
				case "fieldGoalPercentage":	//投篮命中率
				case "threePointerPercentage":	//三分命中率
				case "freeThrowMade":	//罚球命中率
					player_condition[i] = new Object[]{player.getName(),map.get("team"),map.get("position"),
							DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map.get("condition"))))};
				}
			}
			else{
				i--;
			}
			num++;
		}
		return player_condition;
	}
	
	/**
	 * 形成赛季热点球队数据表格
	 * @param condition
	 * @return
	 */
//	private Object[][] formTeamValuesForSeason(int condition){
//		List<TeamInAverage> list = dataService.getLeadTeamForSeason(dataService.getCurrentSeason(), condition);
//		Object[][] team_condition = new Object[5][];
//		for(int i=0;i<Math.min(5, list.size());i++){
//			TeamInAverage team = list.get(list.size()-i-1);
//			TeamPO teamPO = dataService.getTeamInfo(team.getName(), emptyFilter);
//			String league = "N/A";
//			if(teamPO!=null){
//				league = judgeTeamLeague(teamPO.getLeague());
//			}
//			switch(condition){
//			case 14:	//场均得分
//			case 8:		//场均篮板
//			case 9:		//场均助攻
//			case 11:	//场均盖帽
//			case 10:	//场均抢断
//				double data = DealDecimal.formatChange(team.getStatsAverage()[condition], 1);
//				team_condition[i] = new Object[]{team.getName(),league,data};
//				break;
//			case 21:	//投篮命中率
//			case 22:	//三分命中率
//			case 23:	//罚球命中率
//				String data_percent = DealDecimal.formatChangeToPercentage(team.getStatsAverage()[condition]);
//				team_condition[i] = new Object[]{team.getName(),league,data_percent};
//			}
//			
//		}
//		return team_condition;
//	}
	
	private Object[][] formTeamValuesForSeason(String condition){
		List<TeamAverage> list = dataReader.getLeadTeamForSeason(totalController.getCurrentSeason().substring(2,7), condition);
		Object[][] team_condition = new Object[5][];
		for(int i=0;i<Math.min(5, list.size());i++){
			TeamAverage team = list.get(i);
			Map<String,Object> map = team.generateMap();
			switch(condition){
			case "points":	//场均得分
			case "rebound":		//场均篮板
			case "assist":		//场均助攻
			case "block":	//场均盖帽
			case "steal":	//场均抢断
				team_condition[i] = new Object[]{map.get("team"),map.get("league"),map.get(condition)};
				break;
			case "fieldGoalPercentage":	//
			case "threePointerPercentage":	//
			case "freeThrowMade":	//
				team_condition[i] = new Object[]{map.get("team"),map.get("league"),
						DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map.get(condition))))};
			}
			
		}
		return team_condition;
	}
	
	/**
	 * 形成进步最快球员数据表格
	 * @param condition
	 * @return
	 */
//	private Object[][] formPlayerValuesForImprove(int condition){
//		List<PlayerInAverage> list = dataService.getImprovePlayer(dataService.getCurrentSeason(), condition);
//		Object[][] player_condition = new Object[5][];
//		for(int i=0;i<Math.min(5, list.size());i++){
//			PlayerInAverage player = list.get(list.size()-i-1);
//			String improvement = null;
//			int dataLoc = -1;
//			switch(condition){
//			case 21:
//				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5ScoreAdv());
//				dataLoc = 14;
//				break;
//			case 22:
//				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5BlockAdv());
//				dataLoc = 8;
//				break;
//			case 23:
//				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5AssistAdv());
//				dataLoc = 9;
//			}
//			player_condition[i] = new Object[]{player.getName(),player.getTeamName()
//					,DealDecimal.formatChange(player.getStatsAverage()[dataLoc], 1),improvement};
//		}
//		return player_condition;
//	}

	private Object[][] formPlayerValuesForImprove(String condition){
		List<PlayerInMatchFull> list = dataReader.getImprovePlayer(totalController.getCurrentSeason().substring(2,7), condition);
		Object[][] player_condition = new Object[5][];
		if(list!=null){
			for(int i=0;i<Math.min(5, list.size());i++){
				PlayerInMatchFull player = list.get(i);
				Map<String,Object> map = player.generateBasicMap();
	//			switch(condition){
	//			case "points": 	//场均得分
	//				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5ScoreAdv());
	//				dataLoc = 14;
	//				break;
	//			case "rebound":	//场均篮板
	//				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5BlockAdv());
	//				dataLoc = 8;
	//				break;
	//			case "assist":	//场均助攻
	//				improvement = DealDecimal.formatChangeToPercentage(player.getRecent5AssistAdv());
	//				dataLoc = 9;
	//			}
				player_condition[i] = new Object[]{player.getName(),map.get("team")
						/**,DealDecimal.formatChange(player.getStatsAverage()[dataLoc], 1)*/,map.get("condition")};
			}
		}
		return player_condition;
	}
	
}
