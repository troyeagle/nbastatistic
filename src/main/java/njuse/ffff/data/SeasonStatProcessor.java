package njuse.ffff.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamInMatch;
import njuse.ffff.po.TeamPO;
import njuse.ffff.util.Filter;
import njuse.ffff.util.Sort;

public class SeasonStatProcessor {
	String season;
	ArrayList<TeamInAverage> teamInAverage;
	ArrayList<PlayerInAverage> playerInAverage;
	Queue<String> eventQ = new LinkedList<String>();
	public SeasonStatProcessor(String season){
		this.season = season;
		averageArrayIni();
	}
	public ArrayList<PlayerInAverage> getPlayerInAverage() {
		return playerInAverage;
	}
	public ArrayList<TeamInAverage> getTeamInAverage(){
		return teamInAverage;
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

	public TeamInAverage getTeamAverage(String name, Filter filter) {
		if(filter==null){filter = new Filter();}
		for (TeamInAverage t : teamInAverage) {
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

	public void average() {
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
			if(m.getName().startsWith(season)){
				addMatch(m);
			}

		}
		for (PlayerInAverage pa : playerInAverage) {
			pa.calAverageAsArray();
		}
		for (TeamInAverage pa : teamInAverage) {
			pa.calAverage();
		}
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
	private void addMatch(MatchPO m){
		for(TeamInAverage ta:teamInAverage){
			if(ta.getAbbr().equals(m.getTeamA())){
				ta.addMatch(m.getTeamStatA());
			}else if(ta.getAbbr().equals(m.getTeamB())){
				ta.addMatch(m.getTeamStatB());
			}
		}
		
		for (PlayerInMatchExtended p : m.getPlayerInAEx()) {
			for (PlayerInAverage pa : playerInAverage) {
				boolean in = false;
				if (pa.getName().equals(p.getName())) {
					pa.addOneMatchStat(p);
					for(TeamPO tpo:TeamDataProcessor.teams){
						if(tpo.getAbbr().equals(p.getTeam().getNameAbbr())){
							pa.setLeague(tpo.getLeague());
						}
					}
					in = true;
					
				}
				if(!in){
					PlayerInAverage newplayer = new PlayerInAverage(p.getName());
					playerInAverage.add(newplayer);
					newplayer.addOneMatchStat(p);
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
	 * Call by DataReadController
	 * @param m
	 */
	void averageProcessForNewMatch(MatchPO m){
		
		for(TeamInAverage ta:teamInAverage){
//			if(ta.getAbbr().equals(m.getName())){
//				ta.calAverageWithNew(m.getTeamStatA());
//			}else if(ta.getAbbr().equals(m.getTeamB())){
//				ta.calAverageWithNew(m.getTeamStatB());
//			}
			if(isEqualTeam(ta.getAbbr(),m.getTeamA())){
				ta.calAverageWithNew(m.getTeamStatA());
			}else if(isEqualTeam(ta.getAbbr(),m.getTeamB())){
				ta.calAverageWithNew(m.getTeamStatB());
			}
		}
		
		for (PlayerInMatchExtended p : m.getPlayerInAEx()) {
			boolean in = false;
			for (PlayerInAverage pa : playerInAverage) {
				if (pa.getName().equals(p.getName())) {
					pa.calAverageAsArrayNew(p);	
					in = true;
				}
			}
			if(!in){
				PlayerInAverage newplayer = new PlayerInAverage(p.getName());
				playerInAverage.add(newplayer);
				newplayer.calAverageAsArrayNew(p);
			}
		}
		for (PlayerInMatchExtended p : m.getPlayerInBEx()) {
			boolean in = false;
			for (PlayerInAverage pa : playerInAverage) {
				if (pa.getName().equals(p.getName())) {
					pa.calAverageAsArrayNew(p);
					in = true;
				}
			}
			if(!in){
				PlayerInAverage newplayer = new PlayerInAverage(p.getName());
				playerInAverage.add(newplayer);
				newplayer.calAverageAsArrayNew(p);
			}
		}
	}
	
	public List<PlayerInMatchExtended> getLeadPlayerForDay(Date date, String condition) {
		List<PlayerInMatchExtended> players = new ArrayList<PlayerInMatchExtended>();
		for(MatchPO m:MatchDataProcessor.matches){
			if(m.getDate().equals(date)){
				players.addAll(m.getPlayerInAEx());
				players.addAll(m.getPlayerInBEx());
			}
		}
		int[] attributes = new int[2];
		new Sort().sortPlayerSingle(players, attributes);
		
		return players;
	}
	
	public List<PlayerInAverage> getLeadPlayerForSeason(int condition) {
		List<PlayerInAverage> players = new ArrayList<PlayerInAverage>();
		players.addAll(playerInAverage);
		int[] attributes = new int[2];
		attributes[0]=condition;
		new Sort().sortPlayer(players, attributes);
		return players;
	}
	
	public List<TeamInAverage> getLeadTeamForSeason(int condition) {
		List<TeamInAverage> teams = new ArrayList<TeamInAverage>();
		teams.addAll(teamInAverage);
		int[] attributes = new int[2];
		attributes[0]=condition;
		new Sort().sortTeam(teams, attributes);
		return teams;
	}
	/**
	 * Condition:
	 * 21 得分
	 * 22 篮板
	 * 23 助攻
	 * @param condition
	 * @return
	 */
	public List<PlayerInAverage> getImprovePlayer(int condition) {
		List<PlayerInAverage> players = new ArrayList<PlayerInAverage>();
		players.addAll(playerInAverage);
		int[] attributes = new int[2];
		attributes[0]=condition;
		new Sort().sortPlayerTotal(players, attributes);
		return players;
	}
	public String getSeason() {
		return season;
	}
	public boolean isEqualTeam(String abbrInTeam,String abbrInMatch){
		if(abbrInTeam.equals(abbrInMatch)){
			return true;
		}
		else if(this.season.equals("12-13")&&abbrInTeam.equals("NOP")&&abbrInMatch.equals("NOH")){
			return true;
		}
		else{
			return false;
		}
	}
}
