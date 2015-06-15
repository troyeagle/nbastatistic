package njuse.ffff.presenter;

import java.util.ArrayList;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenterService.SearchService;
import njuse.ffff.uiservice.SearchResultService;
import njuse.ffff.util.Filter;

public class SearchController implements SearchService {
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
	public void search(SearchResultService searchResultPanel, String input) {
		String[] teamNames = searchTeams(input);
		String[] playerNames = searchPlayers(input);

		searchResultPanel.setSearchResult(teamNames, playerNames);
	}

	public String[] searchPlayers(String input) {
		input = input.toUpperCase();
		ArrayList<String> playersName = new ArrayList<>();
		ArrayList<PlayerPO> data_player = dataService.getPlayerInfoAll(emptyFilter);
		for (PlayerPO player : data_player) {
			if (player.getName().toUpperCase().contains(input)) {
				playersName.add(player.getName());
			}
		}
		return playersName.toArray(new String[0]);
	}

	public String[] searchTeams(String input) {
		input = input.toUpperCase();
		ArrayList<String> search_team = new ArrayList<>();
		//获取所有球队信息
		ArrayList<TeamPO> data_team = dataService.getTeamInfoAll(emptyFilter);
		//查找
		for (TeamPO team : data_team) {
			if (team.getName().toUpperCase().contains(input)
					|| team.getAbbr().contains(input)) {
				search_team.add(team.getName());
			}
		}
		return search_team.toArray(new String[0]);
	}

	/**
	 * 设置高级所示结果界面
	 */
	public void setAdvancedSearchPanel(njuse.ffff.uiservice.SearchService searchPanel,
			SearchResultService searchResultPanel) {
		String[][] filters = searchPanel.getFilters();
		for (int i = 0; i < filters.length; i++) {
			switch (filters[i][0]) {
			//TODO
			case "":
				break;

			}
		}
	}

}
