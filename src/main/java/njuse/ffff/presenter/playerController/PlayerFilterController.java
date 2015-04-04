package njuse.ffff.presenter.playerController;

import java.util.ArrayList;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.playerService.PlayerFilterService;
import njuse.ffff.ui.PlayerFilterPanel;
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
	 * 设置球员筛选界面
	 */
	public void setPlayerFilterPanel() {
		PlayerFilterPanel playerFilterPanel = new PlayerFilterPanel();
		totalController.addCurrentPanel(playerFilterPanel);
		totalController.switchToPanel(playerFilterPanel);
	}

	/**
	 * 将球员筛选结果显示到界面
	 */
	public void setPlayerFilterResult(PlayerFilterPanel panel, String position,
			String league, String sort) {
		char pos = 0;
		String leagueInEnglish = null;
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
			if(pos!=0&&playerPO.getPosition()==pos){
				if(leagueInEnglish!=null&&leagueInEnglish.equals(player.getLeague())){
					data_filtered.add(player);
				}
			}
		}
		
		//排序条件
		String[] conditions = sort.split(";");
		
		String[] properties = {"编号","球员姓名","得分","篮板","助攻","得分/篮板/助攻","盖帽","抢断","犯规"
				,"失误","分钟","效率","投篮","三分","罚球","两双"};
		
		int[] conditionsOfSort = new int[conditions.length];

		if(conditions[0].equals("两双")){
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
			sortConductor.sortPlayer(player_withDD, conditionsOfSort);
			
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
				sortConductor2.sortPlayer(player_withoutDD, conditionsOfSort);
				for(int j=0;j<Math.min(50-player_withDD.size(),player_withoutDD.size());j++){
					data_display.add(player_withoutDD.get(j));
				}
			}
		}
		else{
			for(int i=0;i<conditions.length;i++){
				int location = 0;
				switch(conditions[i]){
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
	//			case "两双":
	//				location = 30;
	//				break;
				}
				conditionsOfSort[i] = location;
			}

			//排序
			Sort sortConductor = new Sort();
			sortConductor.sortPlayer(data_filtered, conditionsOfSort);
			
			for(int i=0;i<Math.min(50, data_filtered.size());i++){
				data_display.add(data_filtered.get(i));
			}
		}
		
		Object[][] values_average = new Object[50][];
		
		for(int i=0;i<data_display.size();i++){
			PlayerInAverage playerAvg = data_display.get(i);
			
			if(playerAvg!=null){
				double[] average = playerAvg.getStatsAverage();
				values_average[i] = new Object[]{i+1,playerAvg.getName(),DealDecimal.formatChange(average[14], 3)
						,DealDecimal.formatChange(average[8], 3),DealDecimal.formatChange(average[9], 3)
						,DealDecimal.formatChange(average[30], 3),DealDecimal.formatChange(average[11], 3)
						,DealDecimal.formatChange(average[10], 3),DealDecimal.formatChange(average[12], 3)
						,DealDecimal.formatChange(average[13], 3),DealDecimal.formatChange(playerAvg.getSecond()/60, 3)
						,DealDecimal.formatChange(average[15], 3),DealDecimal.formatChange(average[0], 3)
						,DealDecimal.formatChange(average[2], 3),DealDecimal.formatChange(average[4], 3)
						,playerAvg.getDoubledouble()};//两双
			}
		}
		
		panel.setFilterInfo(properties, values_average);
		
	}
}
