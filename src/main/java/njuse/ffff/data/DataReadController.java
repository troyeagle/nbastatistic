package njuse.ffff.data;

import java.io.IOException;
import java.util.ArrayList;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamInMatch;
import njuse.ffff.po.TeamPO;
import njuse.ffff.util.Filter;

public class DataReadController implements DataReaderService {
	PlayersDataProcessor player = new PlayersDataProcessor();
	MatchDataProcessor match = new MatchDataProcessor();
	TeamDataProcessor team = new TeamDataProcessor();
	ArrayList<TeamInAverage> teamInAverage;
	ArrayList<PlayerInAverage> playerInAverage;

	public PlayerInAverage getPlayerAverage(String name, Filter filter) {
		// TODO Auto-generated method stub
		for (PlayerInAverage p : playerInAverage) {
			if (p.getName().equals(name) || filter.filt(p)) {
				return p;

			}
		}
		return null;
	}

	public PlayerPO getPlayerInfo(String name, Filter filter) {
		// TODO Auto-generated method stub
		for (PlayerPO p : PlayersDataProcessor.players) {
			if (p.getName().equals(name) || filter.filt(p)) {
				return p;
			}
		}
		return null;
	}

	public TeamInAverage getTeamAverage(String name, Filter filter) {
		// TODO Auto-generated method stub
		for (TeamInAverage t : teamInAverage) {
			if (t.getName().equals(name) || filter.filt(t)) {
				return t;
			}
		}
		return null;
	}

	public TeamPO getTeamInfo(String name, Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerInMatchExtended> getPlayerStatistics(String name,
			Filter filter) {
		for (PlayerInAverage p : playerInAverage) {
			if (p.getName().equals(name) || filter.filt(p)) {
				return p.getPlayerStats();

			}
		}
		return null;
	}

	public ArrayList<TeamInMatch> getTeamStatistics(String name, Filter filter) {
		// TODO Auto-generated method stub
		for (TeamInAverage t : teamInAverage) {
			if (t.getName().equals(name) || filter.filt(t)) {
				return t.getTeamStats();
			}
		}
		return null;
	}

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
		for (MatchPO m : MatchDataProcessor.matches) {
			for (PlayerInMatchExtended p : m.getPlayerInAEx()) {
				for (PlayerInAverage pa : playerInAverage) {
					if (pa.getName().equals(p.getName())) {
						pa.addOneMatchStat(p);
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
		for (PlayerInAverage pa : playerInAverage) {
			pa.calAverageAsArray();
		}
		for (TeamInAverage pa : teamInAverage) {
			pa.calAverage();
		}
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		new DataReadController().initialize();
	}

}
