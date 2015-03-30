package njuse.ffff.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamInMatch;
import njuse.ffff.po.TeamPO;
import njuse.ffff.util.Filter;
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
		// TODO Auto-generated method stub
		if(filter==null){filter = new Filter();}
		for (TeamInAverage t : teamInAverage) {
			if (t.getName().equals(name) && filter.filt(t)) {
				return t;
			}
		}
		return null;
	}

	public TeamPO getTeamInfo(String name, Filter filter) {
		if(filter==null){filter = new Filter();}
		for (TeamPO t : TeamDataProcessor.teams) {
			if (t.getName().equals(name) && filter.filt(t)) {
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
			if (t.getName().equals(name) && filter.filt(t)) {
				return t.getTeamStats();
			}
		}
		return null;
	}
	/**
	 * 数据首次读取入口
	 */
	public void initialize() throws IOException {
		
		long a = System.currentTimeMillis();
		player.readAndAnalysisPlayer();

		team.readAndAnalysisTeam();

		match.readAndAnalysisMatch();
		
		long b = System.currentTimeMillis();
		System.out.println(b - a);
		match.processAll();
		long c = System.currentTimeMillis();
		System.out.println(c - b);

		average();
		new Thread() {
			public void run() {

				try {
					player.saveAsSerial();
					team.saveAsSerial();
					match.saveAsSerial();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}.start();
	}

	public void load() throws ClassNotFoundException, IOException {
		player.loadSerial();
		team.loadSerial();
		match.loadSerial();
		match.processAll();

		average();

	}
	/**
	 * 所有平均值计算
	 */
	public void average() {
		playerInAverage = new ArrayList<PlayerInAverage>();
		teamInAverage = new ArrayList<TeamInAverage>();

		/*
		 * for (PlayerPO p : PlayersDataProcessor.players) {
		 * playerInAverage.add(new PlayerInAverage(p.getName(),
		 * MatchDataProcessor.matches)); }
		 */// This method is too stupid.

		for (PlayerPO p : PlayersDataProcessor.players) {
			playerInAverage.add(new PlayerInAverage(p.getName()));
		}
		for(TeamPO p:TeamDataProcessor.teams){
			teamInAverage.add(new TeamInAverage(p.getName(),p.getAbbr()));
		}
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
			if(ta.getAbbr().equals(m.getName())){
				ta.addMatch(m.getTeamStatA());
			}else if(ta.getAbbr().equals(m.getTeamB())){
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

	public void processNewMatch(){
		Queue<String> eventQ = new LinkedList<String>();
		new Thread(){
			public void run(){
				while(true){
					if(!eventQ.isEmpty()){
						String[] name = eventQ.poll().split(";");
						if(name[1].equals("EVENT_CREATE"));
						MatchPO oneMatch = match.readAndAnalyzeNew(name[0]);
						oneMatch.teamProcess();
						averageProcessForNewMatch(oneMatch);
					}
				}
			}
		}.start();
	}
}
