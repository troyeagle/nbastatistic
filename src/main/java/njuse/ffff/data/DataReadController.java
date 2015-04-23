package njuse.ffff.data;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamInMatch;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenter.UpdateController;
import njuse.ffff.presenterService.UpdateService;
import njuse.ffff.util.FileListener;
import njuse.ffff.util.Filter;
import njuse.ffff.util.Sort;
/**
 * 数据层读取处理入口和中心控制
 * 
 * @author Mebleyev.G.Longinus
 *
 */
public class DataReadController implements DataReaderService {
	PlayersDataProcessor player = new PlayersDataProcessor();
	MatchDataProcessor match = new MatchDataProcessor();
	TeamDataProcessor team = new TeamDataProcessor();
	ArrayList<TeamInAverage> teamInAverage;
	ArrayList<PlayerInAverage> playerInAverage;
	ArrayList<SeasonStatProcessor> seasons = new ArrayList<SeasonStatProcessor>();
	ExecutorService exe = Executors.newCachedThreadPool();
	
	String path;
	
	Date currentDate;
	String currentSeason;
	
	Queue<String> eventQ = new LinkedList<String>();
	public ArrayList<PlayerInAverage> getPlayerInAverage() {
		return playerInAverage;
	}

	public PlayerInAverage getPlayerAverage(String name, Filter filter) {
		if(filter==null){filter = new Filter();}
		for (PlayerInAverage p : playerInAverage) {
			if (p.getName().equals(name) && filter.filt(p)) {
				return p;

			}
		}
		return null;
	}

	public PlayerPO getPlayerInfo(String name, Filter filter) {
		if(filter==null){filter = new Filter();}
		for (PlayerPO p : PlayersDataProcessor.players) {
			if (p.getName().equals(name) && filter.filt(p)) {
				return p;
			}
		}
		return null;
	}

	public TeamInAverage getTeamAverage(String name, Filter filter) {
		if(filter==null){filter = new Filter();}
		for (TeamInAverage t : teamInAverage) {
			if ((t.getName().equals(name)||t.getAbbr().equals(name)) && filter.filt(t)) {
				return t;
			}
		}
		return null;
	}

	public TeamPO getTeamInfo(String name, Filter filter) {
		if(filter==null){filter = new Filter();}
		for (TeamPO t : TeamDataProcessor.teams) {
			if ((t.getName().equals(name)||t.getAbbr().equals(name)) && filter.filt(t)) {
				return t;
			}
		}
		return null;

	}

	public ArrayList<PlayerInMatchExtended> getPlayerStatistics(String name,
			Filter filter) {
		if(filter==null){filter = new Filter();}
		for (PlayerInAverage p : playerInAverage) {
			if (p.getName().equals(name) && filter.filt(p)) {
				return p.getPlayerStats();

			}
		}
		return null;
	}

	public ArrayList<TeamInMatch> getTeamStatistics(String name, Filter filter) {
		if(filter==null){filter = new Filter();}
		for (TeamInAverage t : teamInAverage) {
			if ((t.getName().equals(name)||t.getAbbr().equals(name)) && filter.filt(t)) {
				return t.getTeamStats();
			}
		}
		return null;
	}
	/**
	 * 数据首次读取入口,程序核心方法
	 */
	
	public void advancedInitialize(String path,int cacheLength){
		//写着玩的
	}
	public void initialize() throws IOException {
		/**
		 * FileListener应该是最高优先级？
		 */
		path="./CSEIII data/plus/matches";
		currentSeason = "12-13";
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		
		Thread fileListener = new Thread(){
			public void run(){
				FileListener fl = new FileListener(path);
				try {
					fl.startNewWatch(eventQ);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		exe.execute(fileListener);
		
		player.readAndAnalysisPlayer();

		team.readAndAnalysisTeam();
		seasons.add(new SeasonStatProcessor("12-13"));
		MatchDataProcessor.setPath("./CSEIII data/plus/matches");
		MatchDataProcessor.matches = new ArrayList<MatchPO>();
		match.readAndAnalysisMatch();				
		match.processAll();
		averageArrayIni();
		for(MatchPO m:MatchDataProcessor.matches){
			for(SeasonStatProcessor ss:seasons){
				if(m.getName().startsWith(ss.getSeason())){
					ss.averageProcessForNewMatch(m);
					break;
				}
			}
		}
		currentDate = MatchDataProcessor.matches.get(MatchDataProcessor.matches.size()-1).getDate();
		average();
		Thread main = new Thread() {//初始化以及处理初始数据
			public void run() {
				
				try {
					player.saveAsSerial();
					team.saveAsSerial();
					match.saveAsSerial();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		};
		Thread season = new Thread(){//增添赛季数据
			public void run(){
				//seasons.add(new SeasonStatProcessor("12-13"));
				//seasons.add(new SeasonStatProcessor("13-14"));
				//seasons.add(new SeasonStatProcessor("14-15"));
			}
		};
		exe.execute(main);
		exe.execute(season);
		processNewMatch();
	}

	private void averageArrayIni(){
		playerInAverage = new ArrayList<PlayerInAverage>();
		teamInAverage = new ArrayList<TeamInAverage>();
		for (PlayerPO p : PlayersDataProcessor.players) {
			playerInAverage.add(new PlayerInAverage(p.getName()));
		}
		for(TeamPO p:TeamDataProcessor.teams){
			teamInAverage.add(new TeamInAverage(p.getName(),p.getAbbr()));
		}
	}
	public void load() throws ClassNotFoundException, IOException {
		player.loadSerial();
		team.loadSerial();
		match.loadSerial();
		match.processAll();
		averageArrayIni();
		average();

	}
	/**
	 * 所有平均值计算,必须确保averageIni已调用，及各平均数容器已经初始化
	 */
	public void average() {


		/*
		 * for (PlayerPO p : PlayersDataProcessor.players) {
		 * playerInAverage.add(new PlayerInAverage(p.getName(),
		 * MatchDataProcessor.matches)); }
		 */// This method is too stupid.


		for (MatchPO m : MatchDataProcessor.matches) {
			averageProcessForMatch(m);
		}
		for (PlayerInAverage pa : playerInAverage) {
			pa.calAverageAsArray();
		}
		for (TeamInAverage pa : teamInAverage) {
			pa.calAverage();
		}
	}

	private void averageProcessForMatch(MatchPO m){
		for(TeamInAverage ta:teamInAverage){
//			if(ta.getAbbr().equals(m.getTeamA())){
//				ta.addMatch(m.getTeamStatA());
//			}else if(ta.getAbbr().equals(m.getTeamB())){
//				ta.addMatch(m.getTeamStatB());
//			}
			if(isEqualTeam(ta.getAbbr(),m.getTeamA())){
				ta.addMatch(m.getTeamStatA());
			}else if(isEqualTeam(ta.getAbbr(),m.getTeamB())){
				ta.addMatch(m.getTeamStatB());
			}
		}
		
		for (PlayerInMatchExtended p : m.getPlayerInAEx()) {
			for (PlayerInAverage pa : playerInAverage) {
				if (pa.getName().equals(p.getName())) {
					pa.addOneMatchStat(p);
					for(TeamPO tpo:TeamDataProcessor.teams){
						if(tpo.getAbbr().equals(p.getTeam().getNameAbbr())){
							pa.setLeague(tpo.getLeague());
						}
					}
					
				}
			}
		}
		for (PlayerInMatchExtended p : m.getPlayerInBEx()) {
			for (PlayerInAverage pa : playerInAverage) {
				if (pa.getName().equals(p.getName())) {
					pa.addOneMatchStat(p);
				}
			}
		}
	}
	/**
	 * Iteration 2
	 * Process a new Match with its average
	 * @param m
	 */
	private void averageProcessForNewMatch(MatchPO m){
		for(TeamInAverage ta:teamInAverage){
			if(ta.getAbbr().equals(m.getName())){
				ta.calAverageWithNew(m.getTeamStatA());
			}else if(ta.getAbbr().equals(m.getTeamB())){
				ta.calAverageWithNew(m.getTeamStatB());
			}
		}
		
		for (PlayerInMatchExtended p : m.getPlayerInAEx()) {
			for (PlayerInAverage pa : playerInAverage) {
				if (pa.getName().equals(p.getName())) {
					pa.calAverageAsArrayNew(p);				
				}
			}
		}
		for (PlayerInMatchExtended p : m.getPlayerInBEx()) {
			for (PlayerInAverage pa : playerInAverage) {
				if (pa.getName().equals(p.getName())) {
					pa.calAverageAsArrayNew(p);
				}
			}
		}
	}
		
	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		new DataReadController().initialize();
	}

	public ArrayList<PlayerPO> getPlayerInfoAll(Filter filter) {
		if(filter==null){filter = new Filter();}
		ArrayList<PlayerPO> arr = new ArrayList<PlayerPO>();
		for (PlayerPO p : PlayersDataProcessor.players) {
			if (filter.filt(p)) {
				arr.add(p);
			}
		}
		return arr;
	}

	public ArrayList<TeamPO> getTeamInfoAll(Filter filter) {
		if(filter==null){filter = new Filter();}
		ArrayList<TeamPO> arr = new ArrayList<TeamPO>();
		for (TeamPO t : TeamDataProcessor.teams) {
			if (filter.filt(t)) {
				arr.add(t);
			}
		}
		return arr;
	}
	/**
	 * Iteration 2 main method
	 */
	public void processNewMatch(){
		
		Thread watch = new Thread(){
			public void run(){

				while(true){
					synchronized(this){
						if(eventQ.size()>2){
							String[] name = eventQ.poll().split(";");
							
							if(name[0].equals("ENTRY_CREATE")){
								
									System.out.println(name[0]+"start:"+name[1]);
									MatchPO oneMatch = MatchDataProcessor.readAndAnalyzeNew(path,name[1]);
									oneMatch.teamProcess();
									averageProcessForNewMatch(oneMatch);
									currentDate = oneMatch.getDate();
									currentSeason = oneMatch.getName().substring(0, 5);
									UpdateService up = UpdateController.getInstance();//更新
									up.informUpdate();
									/**
									 * Iteration 2 select season and process
									 */
									for(SeasonStatProcessor ss:seasons){
										if(oneMatch.getName().startsWith(ss.getSeason())){
											ss.averageProcessForNewMatch(oneMatch);
											break;
										}
									}
								
								
							}
							
						}
					}
					
				}
			}
		};
		exe.execute(watch);
	}

	@Override
	public List<MatchPO> getMatchForTeam(String team) {
		List<MatchPO> matchForTeam = new ArrayList<MatchPO>();
		for(MatchPO m:MatchDataProcessor.matches){
			if(m.getTeamA().equals(team)||m.getTeamB().equals(team)){
				matchForTeam.add(m);
			}
		}
		return matchForTeam;
	}

	@Override
	public List<MatchPO> getMatchForPlayer(String player) {
		List<MatchPO> matchForTeam = new ArrayList<MatchPO>();
		for(MatchPO m:MatchDataProcessor.matches){
			if(m.getMembers().contains(player)){
				matchForTeam.add(m);
			}
		}
		return matchForTeam;
	}

	@Override
	public List<MatchPO> getMatchInPeriod(Date date1, Date date2) {
		List<MatchPO> matchForTeam = new ArrayList<MatchPO>();
		for(MatchPO m:MatchDataProcessor.matches){
			if(m.getDate().compareTo(date1)>=0&&m.getDate().compareTo(date2)>=0){
				matchForTeam.add(m);
			}
		}
		return matchForTeam;
	}

	@Override
	public MatchPO getMatch(Date date, String teamA) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(MatchPO m:MatchDataProcessor.matches){
		    String ds1 = sdf.format(m.getDate());
		    String ds2 = sdf.format(date);
			if(ds1.equals(ds2)&&(m.getTeamA().equals(teamA)||m.getTeamB().equals(teamA))){
				return m;
			}
		}
		return null;
	}

	@Override
	public List<PlayerInMatchExtended> getLeadPlayerForDay(Date date, int condition) {
		List<PlayerInMatchExtended> players = new ArrayList<PlayerInMatchExtended>();
		for(MatchPO m:MatchDataProcessor.matches){
			if(m.getDate().equals(date)){
				players.addAll(m.getPlayerInAEx());
				players.addAll(m.getPlayerInBEx());
			}
		}
		int[] attributes = new int[2];
		attributes[0]=condition;
		new Sort().sortPlayerSingle(players, attributes);
		
		return players;
	}

	@Override
	public List<PlayerInAverage> getLeadPlayerForSeason(String season, int condition) {
		for(SeasonStatProcessor ss:seasons){
			if(ss.season.equals(season)){
				return ss.getLeadPlayerForSeason(condition);
			}
		}
		return null;
	}

	@Override
	public List<TeamInAverage> getLeadTeamForSeason(String season, int condition) {
		for(SeasonStatProcessor ss:seasons){
			if(ss.season.equals(season)){
				return ss.getLeadTeamForSeason(condition);
			}
		}
		return null;
	}

	@Override
	public List<PlayerInAverage> getImprovePlayer(String season, int condition) {
		for(SeasonStatProcessor ss:seasons){
			if(ss.season.equals(season)){
				return ss.getImprovePlayer(condition);
			}
		}
		return null;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public String getCurrentSeason() {
		return currentSeason;
	}
	
	public SeasonStatProcessor getSeasonStatProcessor(String season){
		for(SeasonStatProcessor ss:seasons){
			if(season.equals(ss.season)){
				return ss;
			}
		}
		return null;
	}

	public boolean isEqualTeam(String abbrInTeam,String abbrInMatch){
		if(abbrInTeam.equals(abbrInMatch)){
			return true;
		}
		else if(this.currentSeason.equals("12-13")&&abbrInTeam.equals("NOP")&&abbrInMatch.equals("NOH")){
			return true;
		}
		else{
			return false;
		}
	}
}
