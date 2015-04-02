package njuse.ffff.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
/**
 * 储存和处理一场比赛的诸多数据
 * @author Mebleyev.G.Longinus
 *
 */
public class MatchPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	Date date;
	String teamA,teamB;
	ArrayList<String> members;
	ArrayList<Integer> scoreA,scoreB;
	ArrayList<PlayerInMatch> playerInTeamA;
	ArrayList<PlayerInMatch> playerInTeamB;
	TeamInMatch teamStatA=null;
	TeamInMatch teamStatB=null;
	ArrayList<PlayerInMatchExtended> playerInAEx = new ArrayList<PlayerInMatchExtended>();
	ArrayList<PlayerInMatchExtended> playerInBEx = new ArrayList<PlayerInMatchExtended>();
	public MatchPO(String name,Date date, String teamA, String teamB,
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
	
	public String getTeamA() {
		return teamA;
	}
	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}
	public String getTeamB() {
		return teamB;
	}
	public void setTeamB(String teamB) {
		this.teamB = teamB;
	}
	public ArrayList<PlayerInMatchExtended> getPlayerInAEx() {
		return playerInAEx;
	}
	public ArrayList<PlayerInMatchExtended> getPlayerInBEx() {
		return playerInBEx;
	}
	public void teamProcess(){
		//System.out.println("Match "+name+" calculating");

		teamStatA = new TeamInMatch(this.teamA,playerInTeamA,teamStatB,scoreA,scoreB);
		teamStatB = new TeamInMatch(this.teamB,playerInTeamB,teamStatA,scoreA,scoreB);
		teamStatA.rival=teamStatB;
		teamStatA.calRounds();
		teamStatB.calRounds();
		teamStatA.calAll();//计算球队的进阶数据
		teamStatB.calAll();

		for(PlayerInMatch p : playerInTeamA){
			PlayerInMatchExtended playerEx = new PlayerInMatchExtended(p,teamStatA);
			playerEx.calAll(teamStatA, teamStatB);
			playerInAEx.add(playerEx);
		}
		for(PlayerInMatch p : playerInTeamB){
			PlayerInMatchExtended playerEx = new PlayerInMatchExtended(p,teamStatA);
			playerEx.calAll(teamStatB, teamStatA);
			playerInBEx.add(playerEx);
		}
		
		
	}

	public void dirtyProcess(){
		
	}
	public TeamInMatch getTeamStatA() {
		return teamStatA;
	}
	public TeamInMatch getTeamStatB() {
		return teamStatB;
	}
	
}
