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
		TeamInMatch teamA=null;
		TeamInMatch teamB=null;
		teamA = new TeamInMatch(this.teamA,playerInTeamA,teamB);
		teamB = new TeamInMatch(this.teamB,playerInTeamB,teamA);
		teamA.rival=teamB;
		teamA.calAll();
		teamB.calAll();
		ArrayList<PlayerInMatchExtended> playerInAEx = new ArrayList<PlayerInMatchExtended>();
		ArrayList<PlayerInMatchExtended> playerInBEx = new ArrayList<PlayerInMatchExtended>();
		for(PlayerInMatch p : playerInTeamA){
			PlayerInMatchExtended playerEx = new PlayerInMatchExtended(p);
			playerEx.calAll(teamA, teamB);
			playerInAEx.add(playerEx);
		}
		for(PlayerInMatch p : playerInTeamB){
			PlayerInMatchExtended playerEx = new PlayerInMatchExtended(p);
			playerEx.calAll(teamB, teamA);
			playerInBEx.add(playerEx);
		}
		
		
	}
	
}
