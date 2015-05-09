package njuse.ffff.presenter.playerController;

import java.util.ArrayList;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.playerService.PlayerFilterService;
import njuse.ffff.uiservice.PlayerFilterViewService;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;
import njuse.ffff.util.Sort;

public class PlayerFilterController implements PlayerFilterService{
	private DataReaderService dataService;
	private static PlayerFilterController playerFilterController = null;
	private static TotalUIController totalController = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private PlayerFilterController(){
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}
	
	public static PlayerFilterController getInstance(){
		if(playerFilterController==null){
			playerFilterController = new PlayerFilterController();
		}
		return playerFilterController;
	}

	/**
	 * 将球员筛选结果显示到界面
	 */
	public void setPlayerFilterResult(PlayerFilterViewService panel) {
		char pos = 0;
		String leagueInEnglish = null;
		String sortCondition = null;
		String[] filters = panel.getFilters();
		String position = null;
		String league = null;
		for(int i=0;i<filters.length;i++){
			String[] temp = filters[i].split(":");
			if(temp.length==2){
				if(temp[0].equals("位置")){
					position = temp[1];
				}
				else if(temp[0].equals("联盟")){
					league = temp[1];
				}
			}
			else{
				sortCondition = filters[i];
			}
		}
		
		if(position.equals("前锋")){
			pos = 'F';
		}
		else if(position.equals("中锋")){
			pos = 'C';
		}
		else if(position.equals("后卫")){
			pos = 'G';
		}
		
		if(league.equals("东部")){
			leagueInEnglish = "E";
		}
		else if(league.equals("西部")){
			leagueInEnglish = "W";
		}
		
		ArrayList<PlayerInAverage> data_to_filter = dataService.getPlayerInAverage();
		ArrayList<PlayerInAverage> data_filtered = new ArrayList<PlayerInAverage>();
		ArrayList<PlayerInAverage> data_display = new ArrayList<PlayerInAverage>();
		
		for(PlayerInAverage player:data_to_filter){
			PlayerPO playerPO = dataService.getPlayerInfo(player.getName(), emptyFilter);
			if(playerPO==null){
				continue;
			}
			if(pos==0){
				if(leagueInEnglish==null){
					data_filtered.add(player);
				}
				else if(leagueInEnglish!=null&&leagueInEnglish.equals(player.getLeague())){
					data_filtered.add(player);
				}
			}
			if(pos!=0&&playerPO.getPosition()[0]==pos){
				if(leagueInEnglish==null){
					data_filtered.add(player);
				}
				if(leagueInEnglish!=null&&leagueInEnglish.equals(player.getLeague())){
					data_filtered.add(player);
				}
			}
		}
		
//		String[] properties = {"编号","球员姓名","场均得分","场均篮板数","场均助攻数","得分/篮板/助攻"
//				,"场均盖帽数","场均抢断数","场均犯规数","场均失误数","分钟","效率"
//				,"投篮命中率","三分命中率","罚球命中率","两双"};
		//排序条件
		int[] conditionsOfSort = new int[1];

		if(sortCondition.equals("两双")){
			ArrayList<PlayerInAverage> player_withDD = new ArrayList<PlayerInAverage>();
			ArrayList<PlayerInAverage> player_withoutDD = new ArrayList<PlayerInAverage>();
			for(PlayerInAverage player:data_filtered){
				if(player.getDoubledouble()==true){
					player_withDD.add(player);
				}
				else {
					player_withoutDD.add(player);
				}
			}
			conditionsOfSort[0] = 30;
			Sort sortConductor = new Sort();
			sortConductor.sortPlayer(player_withDD, conditionsOfSort,new boolean[2]);
			
			if(player_withDD.size()>=50){
				for(int i=0;i<50;i++){
					data_display.add(player_withDD.get(i));
				}
			}
			else{
				for(int i=0;i<player_withDD.size();i++){
					data_display.add(player_withDD.get(i));
				}
				Sort sortConductor2 = new Sort();
				sortConductor2.sortPlayer(player_withoutDD, conditionsOfSort,new boolean[2]);
				for(int j=0;j<Math.min(50-player_withDD.size(),player_withoutDD.size());j++){
					data_display.add(player_withoutDD.get(j));
				}
			}
		}
		else{
			int location = 0;
			switch(sortCondition){
			case "得分":
				location = 14;
				break;
			case "篮板":
				location = 8;
				break;
			case "助攻":
				location = 9;
				break;
			case "得分/篮板/助攻":
				location = 30;
				break;
			case "盖帽":
				location = 11;
				break;
			case "抢断":
				location = 10;
				break;
			case "犯规":
				location = 12;
				break;
			case "失误":
				location = 13;
				break;
			case "分钟":
				location = 15;
				break;
			case "效率":
				location = 15;
				break;
			case "投篮":
				location = 0;
				break;
			case "三分":
				location = 2;
				break;
			case "罚球":
				location = 4;
				break;
			}
			conditionsOfSort[0] = location;
			
			//排序
			Sort sortConductor = new Sort();
			sortConductor.sortPlayer(data_filtered, conditionsOfSort,new boolean[2]);
			
			for(int i=0;i<Math.min(50, data_filtered.size());i++){
				data_display.add(data_filtered.get(i));
			}
		}
		
		Object[][] values_average = new Object[50][];
		
		for(int i=0;i<data_display.size();i++){
			PlayerInAverage playerAvg = data_display.get(i);
			
			if(playerAvg!=null){
				double[] average = playerAvg.getStatsAverage();
				values_average[i] = new Object[]{
						i+1,playerAvg.getName()						//编号，球员名称
						,DealDecimal.formatChange(average[14], 1)	//得分
						,DealDecimal.formatChange(average[8], 1)	//篮板数
						,DealDecimal.formatChange(average[9], 1)	//助攻数
						,DealDecimal.formatChange(average[30], 1)	//得分/篮板/助攻
						,DealDecimal.formatChange(average[11], 1)	//盖帽数
						,DealDecimal.formatChange(average[10], 1)	//抢断数
						,DealDecimal.formatChange(average[12], 1)	//犯规数
						,DealDecimal.formatChange(average[13], 1)	//失误数
						,DealDecimal.formatChange(playerAvg.getSecond()/60, 1)	//平均上场时间
						,DealDecimal.formatChange(average[15], 1)	//效率
						,DealDecimal.formatChangeToPercentage(average[29])	//投篮命中率
						,DealDecimal.formatChangeToPercentage(average[28])	//三分命中率
						,DealDecimal.formatChangeToPercentage(average[27])	//罚球命中率
						,playerAvg.getDoubledouble()				//两双
						};
			}
		}
		//设置界面显示
		panel.setResult(values_average);
		totalController.setPlayerFilterViewService(panel);
	}
}
