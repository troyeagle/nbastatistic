package njuse.ffff.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.presenterService.SearchService;
import njuse.ffff.sqlpo.PlayerInfo;
import njuse.ffff.sqlpo.TeamInfo;
import njuse.ffff.uiservice.SearchResultService;

public class SearchController implements SearchService {
	private NewDataReaderService dataReader;
	private static SearchController searchController = null;
	private static TotalUIController totalController = null;

//	private static final Filter emptyFilter;
//
//	static {
//		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
//	}

	private SearchController() {
		totalController = TotalUIController.getInstance();
		dataReader = totalController.getDataReader();
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
		List<PlayerInfo> data_player = dataReader.getPlayerInfoAll("");
		for (PlayerInfo player : data_player) {
			if (player.getPlName().toUpperCase().contains(input)) {
				playersName.add(player.getPlName());
			}
		}
		return playersName.toArray(new String[0]);
	}

	public String[] searchTeams(String input) {
		input = input.toUpperCase();
		ArrayList<String> search_team = new ArrayList<>();
		//获取所有球队信息
		List<TeamInfo> data_team = dataReader.getTeamInfoAll();
		//查找
		for (TeamInfo team : data_team) {
			Map<String,Object> map = team.generateMap();
			if (String.valueOf(map.get("name")).toUpperCase().contains(input)
					) {//|| String.valueOf(map.get("teamNames")).toUpperCase().contains(input)
				search_team.add(String.valueOf(map.get("name")));
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
