package njuse.ffff.presenter;

import java.util.ArrayList;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenterService.SearchService;
import njuse.ffff.ui.SearchPanel;
import njuse.ffff.ui.SearchResultPanel;
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
	 * 设置查询界面
	 */
	public void setSearchPanel() {
		SearchPanel searchPanel = new SearchPanel();
		totalController.addCurrentPanel(searchPanel);
		totalController.switchToPanel(searchPanel);
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
		String[] properties = new String[]{"编号","分类","详细名称"};
		Object[][] values = new Object[search_team.size()+search_player.size()][3];
		for(int i=0;i<search_team.size();i++){
			values[i][0] = i+1;
			values[i][1] = "球队";
			values[i][2] = search_team.get(i).getName();
		}
		for(int j=search_team.size();j<search_team.size()+search_player.size();j++){
			values[j][0] = j+1;
			values[j][1] = "球员";
			values[j][2] = search_player.get(j-search_team.size()).getName();
		}
		SearchResultPanel panel_searchResult = new SearchResultPanel();
		panel_searchResult.setSearchResult(properties, values);
		
		totalController.addCurrentPanel(panel_searchResult);
		totalController.switchToPanel(panel_searchResult);
	}
	
	/**
	 * 设置高级所示结果界面
	 */
	public void setAdvancedSearchPanel(SearchService searchPanel,SearchResultService searchResultPanel){
		
	}

}
