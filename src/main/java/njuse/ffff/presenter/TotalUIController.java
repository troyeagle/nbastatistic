package njuse.ffff.presenter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import njuse.ffff.data.DataReadController;
import njuse.ffff.dataGetter.DataReader;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.dataservice.NewDataReaderService;
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
import njuse.ffff.sqlpo.MatchInfo;
import njuse.ffff.sqlpo.TeamAverage;
import njuse.ffff.sqlpo.TeamInfo;
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
import njuse.ffff.util.TeamNameAndAbbr;

//球员在一场比赛里的信息和球队在一场比赛里的信息存储在一个表里
public class TotalUIController implements TotalControlService{
	private ArrayList<JPanel> panelList = new ArrayList<JPanel>();//存储点击界面先后顺序的列表

	private DataReaderService dataService;
	private NewDataReaderService dataReader;
	private DataReader dataR;
	private static TotalUIController totalController = null;
	private static UpdateController updateController = null;
	
	private String[] seasonList;
	private String currentSeason;
	private Date currentDay;
	private Map<String,ArrayList<TeamAverage>> teamsInSeasons;
	
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
//		if (frame == null) {
//			frame = new MainFrame();
//		}
//		frame.setVisible(true);
	}

	/**
	 * 绑定数据层
	 */
	private void bindDataService() {
//		dataService = new DataReadController();
		dataR = new DataReader();
		dataReader = dataR;
	}
	
	/**
	 * 返回数据服务
	 */
	public DataReadController getDataReadController(){
		return (DataReadController) dataService;
	}
	
	/**
	 * 返回数据库服务
	 */
	public DataReader getDataReader(){
		return dataR;
	}
	
	/**
	 * 返回所有赛季
	 */
	public String[] getAllSeasons(){
		if(seasonList==null){
			initSeason();
		}
		return seasonList;
	}
	
	/**
	 * 返回当前赛季
	 */
	public String getCurrentSeason(){
		if(currentSeason==null){
			initSeason();
		}
		return currentSeason;
	}
	
	/**
	 * 返回当天
	 */
	public Date getCurrentDay(){
		if(currentDay==null){
			initCurrentDay();
		}
		return currentDay;
	}

	/**
	 * 界面切换准备工作
	 * @param panel
	 */
	public void switchToPanel(JPanel panel) {
//		frame.switchToPanel(panel);
	}
	
	/**
	 * 初始化系统
	 */
	public void initSystem() {
//		dataR.initialize();
		//计算所有赛季
		initSeason();
//		try {
//			dataService.initialize();
////			createFrame();
////			updateController = UpdateController.getInstance();
////			updateController.checkForUpdate();
////			initTeamAndAbbr();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public void initSeason(){
		List<TeamInfo> teamList = dataReader.getTeamInfoAll();
		int startSeason = 3000;
		int endSeason = 0;
		if(teamList!=null&&teamList.size()!=0){
			for(int i=0;i<teamList.size();i++){
				String seasons = String.valueOf(teamList.get(i).generateMap().get("seasons")).replace(" ", "");
				String[] list = seasons.split("[;；]");
				String[] temp = list[list.length-1].split("to");
				int start = Integer.parseInt(temp[0].split("-")[0]);
				int end = Integer.parseInt(temp[1].split("-")[0]);
				if(start<startSeason)
					startSeason = start;
				if(end>endSeason)
					endSeason = end;
			}
			seasonList = new String[endSeason-startSeason+1];
			for(int i=startSeason;i<=endSeason;i++){
				String temp2 = String.valueOf(i+1);
				StringBuffer bf = new StringBuffer(i+"-"+temp2.substring(2,temp2.length()));
				seasonList[i-startSeason] = bf.toString();
				if(i==endSeason-1){
					currentSeason = bf.toString();
				}
			}
			initCurrentDay();
		}
	}
	
	public void initCurrentDay(){
		if(currentSeason==null){
			initSeason();
		}
		String[] s = currentSeason.split("-");
		int year = Integer.parseInt(s[0])+1;
		boolean getCurrent = false;
		for(int i=6;i>0;i--){
			Date date_start = formDate(year, i, 1);
			Date date_end = formDate(year, i, getDayOfMonth(year, i));
			List<MatchInfo> matches = dataReader.getMatchInPeriod(date_start, date_end);
			if(matches==null||matches.size()==0)
				continue;
			int latest = 1;
			for(MatchInfo match:matches){
				Calendar date = new GregorianCalendar();
				date.setTime(match.getDate());
				int day = date.get(Calendar.DAY_OF_MONTH);
				if(day>latest)
					latest = day;
			}
			currentDay = formDate(year, i, latest);
			getCurrent = true;
			break;
		}
		if(!getCurrent){
			year = year-1;
			//TODO
		}
	}
	
	public void addTeamsInSeasons(String season,ArrayList<TeamAverage> t){
		if(teamsInSeasons==null){
			teamsInSeasons = new HashMap<String,ArrayList<TeamAverage>>();
		}
		if(teamsInSeasons.get(season)==null){
			teamsInSeasons.put(season, t);
		}
	}
	
	public ArrayList<TeamAverage> getTeamsInSeason(String season){
		if(teamsInSeasons!=null){
			return teamsInSeasons.get(season);
		}
		return null;
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
		TeamNameAndAbbr.getInstance().updateTeams();
		if(matchViewService!=null){
			MatchInfoService service = MatchInfoController.getInstance();
			java.util.Date date = service.getPresentDate();
			String team = service.getPresentTeam();
			if(date!=null&&team!=null){
				service.setMatchInfoPanel(matchViewService, date, team);
			}
		}
		if(matchsLogService!=null){
			MatchsViewService service = MatchsViewController.getInstance();
			java.util.Date currentDate = dataService.getCurrentDate();
			String year = service.getPresentYear();
			String month = service.getPresentMonth();
			if(currentDate.getYear()==Integer.parseInt(year)
					&&currentDate.getMonth()==Integer.parseInt(month)){
				service.setMatchsViewPanel(matchsLogService, year, month);
			}
		}
		if(playersOverviewService!=null){
			@SuppressWarnings("unused")
			PlayerCompareService service = PlayerCompareController.getInstance();
//			service.setPlayerCompareInfoForSeason(playersOverviewService);
		}
		if(playerFilterViewService!=null){
			PlayerFilterService service = PlayerFilterController.getInstance();
			service.setPlayerFilterResult(playerFilterViewService);
		}
		if(playerProfileService!=null){
			PlayerInfoService service = PlayerInfoController.getInstance();
			String[] playerList = service.getPresentPlayer();
			if(playerList[0]!=null){
//				service.setPlayerProfilePanel(playerProfileService, playerList[0]);
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
				service.setPlayerGameLog(playerDataService, playerList[4] ,null);
			}
		}
		if(teamsOverviewService!=null){
			@SuppressWarnings("unused")
			TeamCompareService service = TeamCompareController.getInstance();
//			service.setTeamCompareInfoForSeason(teamsOverviewService);
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
				service.setTeamGameLog(teamDataService, teamList[5], null);
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
	
	/**
	 * 获得某年某月有几天
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDayOfMonth(int year,int month){
		int day = 0;
		if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
			day = 31;
		}
		else if(month==4||month==6||month==9||month==11){
			day = 30;
		}
		else if(month==2){
			//闰年
			if(year%4==0||year%400==0){
				day = 29;
			}
			//平年
			else{
				day = 28;
			}
		}
		return day;
	}
	
	public Date formDate(int year,int month,int day){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer date = new StringBuffer();
		date.append(year+"-"+month+"-"+day);
		Date sample = null;
        try {
			sample = new Date(format.parse(date.toString()).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sample;
	}
	
}
