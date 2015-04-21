package njuse.ffff.presenter;

import java.util.ArrayList;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenterService.SearchService;
import njuse.ffff.uiservice.SearchResultService;
import njuse.ffff.util.Filter;

public class SearchController implements SearchService{
	private DataReaderService dataService;
	private static SearchController searchController = null;
	private static TotalUIController totalController = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private SearchController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}

	public static SearchController getInstance() {
		if (searchController == null) {
			searchController = new SearchController();
		}
		return searchController;
	}

	/**
	 * 查找搜索的球队或者球员
	 */
	public void search(SearchResultService searchResultPanel,String input) {
		ArrayList<TeamPO> search_team = new ArrayList<TeamPO>();
		ArrayList<PlayerPO> search_player = new ArrayList<PlayerPO>();
		//获取所有球队信息
		ArrayList<TeamPO> data_team = dataService.getTeamInfoAll(emptyFilter);
		//获取所有球员信息
		ArrayList<PlayerPO> data_player = dataService.getPlayerInfoAll(emptyFilter);
		//查找
		for(TeamPO team:data_team){
			if(team.getName().contains(input)||team.getAbbr().contains(input)){
				search_team.add(team);
			}
		}
		for(PlayerPO player:data_player){
			if(player.getName().contains(input)){
				search_player.add(player);
			}
		}
		//设置搜索结果界面
		String[] teamNames = new String[search_team.size()];
		String[] playerNames = new String[search_player.size()];
		for(int i=0;i<search_team.size();i++){
			teamNames[i] = search_team.get(i).getName();
		}
		for(int j=search_team.size();j<search_team.size()+search_player.size();j++){
			playerNames[j-search_team.size()] = search_player.get(j-search_team.size()).getName();
		}
		
		searchResultPanel.setSearchResult(teamNames, playerNames);
	}
	
	/**
	 * 设置高级所示结果界面
	 */
	public void setAdvancedSearchPanel(njuse.ffff.uiservice.SearchService searchPanel,SearchResultService searchResultPanel){
		String[][] filters = searchPanel.getFilters();
		for(int i=0;i<filters.length;i++){
			switch(filters[i][0]){
			//TODO
			case "":
				break;
				
			}
		}
	}

}
