package njuse.ffff.po;

import java.util.ArrayList;

public class MatchPO {
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
	
	
	
}
