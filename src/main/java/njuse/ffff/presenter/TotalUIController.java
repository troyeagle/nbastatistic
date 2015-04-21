package njuse.ffff.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;

import njuse.ffff.data.DataReadController;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.presenter.hotEventController.HotEventController;
import njuse.ffff.presenter.matchController.MatchInfoController;
import njuse.ffff.presenter.matchController.MatchsViewController;
import njuse.ffff.presenter.playerController.PlayerCompareController;
import njuse.ffff.presenter.playerController.PlayerFilterController;
import njuse.ffff.presenter.playerController.PlayerInfoController;
import njuse.ffff.presenter.teamController.TeamCompareController;
import njuse.ffff.presenter.teamController.TeamInfoController;
import njuse.ffff.presenterService.TotalControlService;
import njuse.ffff.presenterService.hotEventService.HotEventService;
import njuse.ffff.presenterService.matchService.MatchInfoService;
import njuse.ffff.presenterService.matchService.MatchsViewService;
import njuse.ffff.presenterService.playerService.PlayerCompareService;
import njuse.ffff.presenterService.playerService.PlayerFilterService;
import njuse.ffff.presenterService.playerService.PlayerInfoService;
import njuse.ffff.presenterService.teamService.TeamCompareService;
import njuse.ffff.presenterService.teamService.TeamInfoService;
import njuse.ffff.ui.ver1.MainFrame;
import njuse.ffff.uiservice.MatchViewService;
import njuse.ffff.uiservice.MatchesLogOverviewService;
import njuse.ffff.uiservice.PlayerDataService;
import njuse.ffff.uiservice.PlayerFilterViewService;
import njuse.ffff.uiservice.PlayerProfileService;
import njuse.ffff.uiservice.PlayersOverviewService;
import njuse.ffff.uiservice.SearchResultService;
import njuse.ffff.uiservice.SearchService;
import njuse.ffff.uiservice.SpecialViewService;
import njuse.ffff.uiservice.TeamDataService;
import njuse.ffff.uiservice.TeamProfileService;
import njuse.ffff.uiservice.TeamsOverviewService;

public class TotalUIController implements TotalControlService{
	private MainFrame frame = null;
	private ArrayList<JPanel> panelList = new ArrayList<JPanel>();//存储点击界面先后顺序的列表

	private DataReaderService dataService;
	private static TotalUIController totalController = null;
	private static UpdateController updateController = null;
	
	//所有的界面接口
	private MatchViewService matchViewService = null;
	private MatchesLogOverviewService matchsLogService = null;
	private PlayersOverviewService playersOverviewService = null;
	private PlayerFilterViewService playerFilterViewService = null;
	private PlayerProfileService playerProfileService = null;
	private PlayerDataService playerDataService = null;
	private TeamsOverviewService teamsOverviewService = null;
	private TeamProfileService teamProfileService = null;
	private TeamDataService teamDataService = null;
	private SpecialViewService specialViewService = null;
	private SearchResultService searchResultService = null;
	private SearchService searchService = null;
	
	private TotalUIController() {
		bindDataService();
	}

	public static TotalUIController getInstance() {
		if (totalController == null) {
			totalController = new TotalUIController();
		}
		return totalController;
	}
	
	public void setMatchViewService(MatchViewService matchViewService) {
		this.matchViewService = matchViewService;
	}

	public void setMatchsLogService(MatchesLogOverviewService matchsLogService) {
		this.matchsLogService = matchsLogService;
	}

	public void setPlayersOverviewService(
			PlayersOverviewService playersOverviewService) {
		this.playersOverviewService = playersOverviewService;
	}

	public void setPlayerFilterViewService(
			PlayerFilterViewService playerFilterViewService) {
		this.playerFilterViewService = playerFilterViewService;
	}

	public void setPlayerProfileService(PlayerProfileService playerProfileService) {
		this.playerProfileService = playerProfileService;
	}

	public void setPlayerDataService(PlayerDataService playerDataService) {
		this.playerDataService = playerDataService;
	}

	public void setTeamsOverviewService(TeamsOverviewService teamsOverviewService) {
		this.teamsOverviewService = teamsOverviewService;
	}

	public void setTeamProfileService(TeamProfileService teamProfileService) {
		this.teamProfileService = teamProfileService;
	}

	public void setTeamDataService(TeamDataService teamDataService) {
		this.teamDataService = teamDataService;
	}

	public void setSpecialViewService(SpecialViewService specialViewService) {
		this.specialViewService = specialViewService;
	}

	public void setSearchResultService(SearchResultService searchResultService) {
		this.searchResultService = searchResultService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	/**
	 * 新建主界面框架
	 */
	public void createFrame() {
		if (frame == null) {
			frame = new MainFrame();
		}
		frame.setVisible(true);
	}

	/**
	 * 绑定数据层
	 */
	private void bindDataService() {
		dataService = new DataReadController();
	}
	
	/**
	 * 返回数据服务
	 */
	public DataReadController getDataReadController(){
		return (DataReadController) dataService;
	}

	/**
	 * 界面切换准备工作
	 * @param panel
	 */
	public void switchToPanel(JPanel panel) {
		frame.switchToPanel(panel);
	}
	
	/**
	 * 初始化系统
	 */
	public void initSystem() {
		try {
			dataService.initialize();
			createFrame();
			updateController = UpdateController.getInstance();
			updateController.checkForUpdate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向界面列表添加当前界面
	 */
	public void addCurrentPanel(JPanel panel){
		panelList.add(panel);
	}
	
	/**
	 * 获得当前显示界面
	 */
	public JPanel getCurrentPanel(){
		return panelList.get(panelList.size()-1);
	}
	
	/**
	 * 返回到上个界面
	 */
	public void backToLastPanel() {
		JPanel panel = panelList.get(panelList.size()-2);
		panelList.remove(panelList.size()-1);
		switchToPanel(panel);
	}
	
	
	
	/**
	 * 更新界面显示
	 */
	@SuppressWarnings("deprecation")
	public void refreshView(){
		if(matchViewService!=null){
			MatchInfoService service = MatchInfoController.getInstance();
			Date date = service.getPresentDate();
			String team = service.getPresentTeam();
			if(date!=null&&team!=null){
				service.setMatchInfoPanel(matchViewService, date, team);
			}
		}
		if(matchsLogService!=null){
			MatchsViewService service = MatchsViewController.getInstance();
			Date currentDate = dataService.getCurrentDate();
			String year = service.getPresentYear();
			String month = service.getPresentMonth();
			if(currentDate.getYear()==Integer.parseInt(year)
					&&currentDate.getMonth()==Integer.parseInt(month)){
				service.setMatchsViewPanel(matchsLogService, year, month);
			}
		}
		if(playersOverviewService!=null){
			PlayerCompareService service = PlayerCompareController.getInstance();
			service.setPlayerCompareInfoForSeason(playersOverviewService);
		}
		if(playerFilterViewService!=null){
			PlayerFilterService service = PlayerFilterController.getInstance();
			service.setPlayerFilterResult(playerFilterViewService);
		}
		if(playerProfileService!=null){
			PlayerInfoService service = PlayerInfoController.getInstance();
			String[] playerList = service.getPresentPlayer();
			if(playerList[0]!=null){
				service.setPlayerProfilePanel(playerProfileService, playerList[0]);
			}
		}
		if(playerDataService!=null){
			PlayerInfoService service = PlayerInfoController.getInstance();
			String[] playerList = service.getPresentPlayer();
			if(playerList[1]!=null){
				service.setPlayerTotalData(playerDataService, playerList[1]);
			}
			if(playerList[2]!=null){
				service.setPlayerAvgData(playerDataService, playerList[2]);
			}
			if(playerList[3]!=null){
				service.setPlayerAdvancedData(playerDataService, playerList[3]);
			}
			if(playerList[4]!=null){
				service.setPlayerGameLog(playerDataService, playerList[4]);
			}
		}
		if(teamsOverviewService!=null){
			TeamCompareService service = TeamCompareController.getInstance();
			service.setTeamCompareInfoForSeason(teamsOverviewService);
		}
		if(teamProfileService!=null){
			TeamInfoService service = TeamInfoController.getInstance();
			String[] teamList = service.getPresentTeam();
			if(teamList[0]!=null){
				service.setTeamProfilePanel(teamProfileService, teamList[0]);
			}
		}
		if(teamDataService!=null){
			TeamInfoService service = TeamInfoController.getInstance();
			String[] teamList = service.getPresentTeam();
			if(teamList[1]!=null){
				service.setPlayerForTeam(teamDataService, teamList[1]);
			}
			if(teamList[2]!=null){
				service.setTeamTotalData(teamDataService, teamList[2]);
			}
			if(teamList[3]!=null){
				service.setTeamAvgData(teamDataService, teamList[3]);
			}
			if(teamList[4]!=null){
				service.setTeamAdvancedlData(teamDataService, teamList[4]);
			}
			if(teamList[5]!=null){
				service.setTeamGameLog(teamDataService, teamList[5]);
			}
		}
		if(specialViewService!=null){
			HotEventService service = HotEventController.getInstance();
			service.setHotEventPanel(specialViewService);
		}
		if(searchService!=null){
			
		}
		if(searchResultService!=null){
			
		}
		updateController.checkForUpdate();
	}

}
