package njuse.ffff.po;

import java.io.Serializable;
import java.util.ArrayList;

public class MatchPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String date;
	String teamA,teamB;
	ArrayList<String> members;
	ArrayList<Integer> scoreA,scoreB;
	ArrayList<PlayerInMatch> playerInTeamA;
	ArrayList<PlayerInMatch> playerInTeamB;
	TeamInMatch teamStatA=null;
	TeamInMatch teamStatB=null;
	ArrayList<PlayerInMatchExtended> playerInAEx = new ArrayList<PlayerInMatchExtended>();
	ArrayList<PlayerInMatchExtended> playerInBEx = new ArrayList<PlayerInMatchExtended>();
	public MatchPO(String name,String date, String teamA, String teamB,
			ArrayList<String> members, ArrayList<Integer> scoreA,
			ArrayList<Integer> scoreB, ArrayList<PlayerInMatch> playerInTeamA,
			ArrayList<PlayerInMatch> playerInTeamB) {
		super();
		this.name = name;
		this.date = date;
		this.teamA = teamA;
		this.teamB = teamB;
		this.members = members;
		this.scoreA = scoreA;
		this.scoreB = scoreB;
		this.playerInTeamA = playerInTeamA;
		this.playerInTeamB = playerInTeamB;
	}
	public String getName() {
		return name;
	}
	
	public void teamProcess(){
		System.out.println("Match "+name+" calculating");

		teamStatA = new TeamInMatch(this.teamA,playerInTeamA,teamStatB);
		teamStatB = new TeamInMatch(this.teamB,playerInTeamB,teamStatA);
		teamStatA.rival=teamStatB;
		teamStatA.calAll();
		teamStatB.calAll();

		for(PlayerInMatch p : playerInTeamA){
			PlayerInMatchExtended playerEx = new PlayerInMatchExtended(p);
			playerEx.calAll(teamStatA, teamStatB);
			playerInAEx.add(playerEx);
		}
		for(PlayerInMatch p : playerInTeamB){
			PlayerInMatchExtended playerEx = new PlayerInMatchExtended(p);
			playerEx.calAll(teamStatB, teamStatA);
			playerInBEx.add(playerEx);
		}
		
		
	}
	
}
