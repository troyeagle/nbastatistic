package njuse.ffff.ui.ver1;

import njuse.ffff.presenter.SearchController;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenter.hotEventController.HotEventController;
import njuse.ffff.presenter.matchController.MatchInfoController;
import njuse.ffff.presenter.matchController.MatchsViewController;
import njuse.ffff.presenter.playerController.PlayerCompareController;
import njuse.ffff.presenter.playerController.PlayerFilterController;
import njuse.ffff.presenter.playerController.PlayerInfoController;
import njuse.ffff.presenter.teamController.TeamCompareController;
import njuse.ffff.presenter.teamController.TeamInfoController;
import njuse.ffff.presenterService.SearchService;
import njuse.ffff.presenterService.TotalControlService;
import njuse.ffff.presenterService.hotEventService.HotEventService;
import njuse.ffff.presenterService.matchService.MatchInfoService;
import njuse.ffff.presenterService.matchService.MatchsViewService;
import njuse.ffff.presenterService.playerService.PlayerCompareService;
import njuse.ffff.presenterService.playerService.PlayerFilterService;
import njuse.ffff.presenterService.playerService.PlayerInfoService;
import njuse.ffff.presenterService.teamService.TeamCompareService;
import njuse.ffff.presenterService.teamService.TeamInfoService;

public class PanelFactory {
	private TotalControlService totalController = null;
	private HotEventService hotEventController = null;
	private MatchInfoService matchInfoController = null;
	private MatchsViewService matchsViewController = null;
	private PlayerCompareService playerCompareController = null;
	private PlayerFilterService playerFilterController = null;
	private PlayerInfoService playerInfoController = null;
	private TeamCompareService teamCompareController = null;
	private TeamInfoService teamInfoController = null;
	private SearchService searchController = null;
	
	private PlayerComparePanel playerComparePanel = null;
	private PlayerFilterPanel playerFilterPanel = null;
	private PlayerPanel playerPanel = null;
	private TeamComparePanel teamComparePanel = null;
	private TeamPanel teamPanel = null;
	private SearchPanel searchPanel = null;
	private SearchResultPanel searchResultPanel = null;
	
	private static PanelFactory panelFactory = null;
	
	private PanelFactory() {
		totalController = TotalUIController.getInstance();
		hotEventController = HotEventController.getInstance();
		matchInfoController = MatchInfoController.getInstance();
		matchsViewController = MatchsViewController.getInstance();
		playerCompareController = PlayerCompareController.getInstance();
		playerFilterController = PlayerFilterController.getInstance();
		playerInfoController = PlayerInfoController.getInstance();
		teamCompareController = TeamCompareController.getInstance();
		teamInfoController = TeamInfoController.getInstance();
		searchController = SearchController.getInstance();
	}

	public static PanelFactory getInstance() {
		if (panelFactory == null) {
			panelFactory = new PanelFactory();
		}
		return panelFactory;
	}

	public void createTeamComparePanel(String season){
		if(teamComparePanel==null){
			teamComparePanel = new TeamComparePanel();
		}
		teamCompareController.setTeamCompareInfoForSeason(teamComparePanel, season);
		totalController.switchToPanel(teamComparePanel);
	}
	
	public void createPlayerComparePanel(String season){
		if(playerComparePanel==null){
			playerComparePanel = new PlayerComparePanel();
		}
		playerCompareController.setPlayerCompareInfoForSeason(playerComparePanel, season);
		totalController.switchToPanel(playerComparePanel);
	}
	
	public void createPlayerFilterPanel(){
		if(playerFilterPanel==null){
			playerFilterPanel = new PlayerFilterPanel();
		}
		totalController.switchToPanel(playerFilterPanel);
	}
	
	public void searchResultForPlayerFilter(){
		if(playerFilterPanel!=null){
			playerFilterController.setPlayerFilterResult(playerFilterPanel);
			totalController.switchToPanel(playerFilterPanel);
		}
	}
	
	public void createPlayerPanel(){
		if(playerPanel==null){
			playerPanel = new PlayerPanel();
		}
		
	}
	
	public void createTeamPanel(){
		if(teamPanel==null){
			teamPanel = new TeamPanel();
		}
		
	}
	
	public void createSearchPanel(){
		if(searchPanel==null){
			searchPanel = new SearchPanel();
		}
		totalController.switchToPanel(searchPanel);
	}
	
	public void createResultForSimpleSearch(String input){
		if(searchResultPanel==null){
			searchResultPanel = new SearchResultPanel();
		}
		searchController.search(searchResultPanel, input);
		totalController.switchToPanel(searchResultPanel);
	}
}
