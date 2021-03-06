package njuse.ffff.po;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 计算并储存一支球队的赛季总数据和平均数据 由DataReadController构建
 * 
 * @author Mebleyev.G.Longinus
 * @see DataReadController
 */
public class TeamInAverage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String abbr;
	private double secondInTotal;
	private double fieldGoalMade = 0;
	private double fieldGoalAttempted = 0;
	private double threePointerMade = 0;
	private double threePointerAttempted = 0;
	private double freeThrowMade = 0;
	private double freeThrowAttempted = 0;
	private double offensiveRebound = 0;
	private double defensiveRebound = 0;
	private double rebound = 0;
	private double assist = 0;
	private double steal = 0;
	private double block = 0;
	private double turnover = 0;
	private double foul = 0;
	private double scores = 0;
	private double fieldGoalRatio;
	private double threePointerRatio;
	private double freeThrowRatio;
	private double myRounds;
	private double offensiveEf;
	private double defensiveEf;
	private double offensiveReboundEf;
	private double defensiveReboundEf;
	private double stealEf;
	private double assistEf;

	private double rivalScores;
	private double rivalRounds;
	private double rivalOffensiveRebound;
	private double rivalDefensiveRebound;
	private double rivalRebounds;
	private double rivalFieldGoalAttempted;
	private double rivalThreePointerAttempted;

	private double[] statsAverage;
	private double[] statsTotal;

	private int numOfMatches = 0;
	private int numOfWins = 0;
	private double winningRatio;

	ArrayList<TeamInMatch> teamStats;

	/*
	 * public TeamInAverage(String name,ArrayList<MatchPO> matches){
	 * 
	 * for(MatchPO m:matches){ if(m.teamA.equals(name)){
	 * teamStats.add(m.teamStatA); }else if(m.teamB.equals(name)){
	 * teamStats.add(m.teamStatB); } } calAverage();
	 * 
	 * }
	 */
	public TeamInAverage(String name2, String abbr) {
		teamStats = new ArrayList<TeamInMatch>();
		statsAverage = new double[32];
		statsTotal = new double[22];
		this.name = name2;
		this.abbr = abbr;
	}

	public void addMatch(TeamInMatch p) {
		teamStats.add(p);
	}

	private void calAll() {
		calOffensiveRounds();
		calFreeThrowRatio();
		calThreePointerRatio();
		calFieldGoalRatio();
		calOffensiveEf();
		calDefensiveEf();
		calReboundEf();
		calStealEf();
		calAssistEf();
	}

	public void addOneMatchToAll(TeamInMatch t) {
		numOfMatches++;
		if (t.win) {
			numOfWins++;
		}
		fieldGoalMade += t.fieldGoalMade;
		fieldGoalAttempted += t.fieldGoalAttempted;
		threePointerMade += t.threePointerMade;
		threePointerAttempted += t.threePointerAttempted;
		freeThrowMade += t.freeThrowMade;
		freeThrowAttempted += t.freeThrowAttempted;
		offensiveRebound += t.offensiveRebound;
		defensiveRebound += t.defensiveRebound;
		rebound += t.rebound;
		assist += t.assist;
		steal += t.steal;
		block += t.block;
		turnover += t.turnover;
		foul += t.foul;
		scores += t.scores;
		rivalScores += t.rival.scores;
		rivalOffensiveRebound += t.rival.offensiveRebound;
		rivalDefensiveRebound += t.rival.defensiveRebound;
		rivalRebounds += t.rival.rebound;
		rivalFieldGoalAttempted += t.rival.fieldGoalAttempted;
		rivalThreePointerAttempted += t.rival.threePointerAttempted;
		rivalRounds += t.myRounds;
		secondInTotal += t.secondInTotal;
	}

	public void calAverage() {
		for (TeamInMatch p : teamStats) {
			addOneMatchToAll(p);
		}
		averageProcess();
	}

	private void averageProcess() {
		makeTotalArray();
		if(teamStats.size()==0){
			return;
		}
		
		fieldGoalMade /= teamStats.size();
		fieldGoalAttempted /= teamStats.size();
		threePointerMade /= teamStats.size();
		threePointerAttempted /= teamStats.size();
		freeThrowMade /= teamStats.size();
		freeThrowAttempted /= teamStats.size();
		offensiveRebound /= teamStats.size();
		defensiveRebound /= teamStats.size();
		rebound /= teamStats.size();
		assist /= teamStats.size();
		steal /= teamStats.size();
		block /= teamStats.size();
		turnover /= teamStats.size();
		foul /= teamStats.size();
		scores /= teamStats.size();
		rivalScores /= teamStats.size();
		rivalRounds/= teamStats.size();
		rivalOffensiveRebound/=teamStats.size();
		rivalDefensiveRebound/=teamStats.size();
		rivalRebounds/=teamStats.size();
		rivalFieldGoalAttempted/=teamStats.size();
		rivalThreePointerAttempted/=teamStats.size();
		calAll();
		winningRatio = (double) numOfWins / numOfMatches;
		makeArray();
		restoreVariables();
	}

	// Iteration 2
	public void calAverageWithNew(TeamInMatch p) {
		teamStats.add(p);
		addOneMatchToAll(p);
		averageProcess();

	}

	public void makeTotalArray() {
		statsTotal = new double[32];
		statsTotal[0] = fieldGoalMade;
		statsTotal[1] = fieldGoalAttempted;
		statsTotal[2] = threePointerMade;
		statsTotal[3] = threePointerAttempted;
		statsTotal[4] = freeThrowMade;
		statsTotal[5] = freeThrowAttempted;
		statsTotal[6] = offensiveRebound;
		statsTotal[7] = defensiveRebound;
		statsTotal[8] = rebound;
		statsTotal[9] = assist;
		statsTotal[10] = steal;
		statsTotal[11] = block;
		statsTotal[12] = turnover;
		statsTotal[13] = foul;
		statsTotal[14] = scores;
		statsTotal[15] = rivalScores;
		statsTotal[16] = rivalOffensiveRebound;
		statsTotal[17] = rivalDefensiveRebound;
		statsTotal[18] = rivalRebounds;
		statsTotal[19] = rivalFieldGoalAttempted;
		statsTotal[20] = rivalThreePointerAttempted;
		statsTotal[21] = secondInTotal;
	}

	public void makeArray() {
		
		statsAverage = new double[32];
		statsAverage[0] = fieldGoalMade;
		statsAverage[1] = fieldGoalAttempted;
		statsAverage[2] = threePointerMade;
		statsAverage[3] = threePointerAttempted;
		statsAverage[4] = freeThrowMade;
		statsAverage[5] = freeThrowAttempted;
		statsAverage[6] = offensiveRebound;
		statsAverage[7] = defensiveRebound;
		statsAverage[8] = rebound;
		statsAverage[9] = assist;
		statsAverage[10] = steal;
		statsAverage[11] = block;
		statsAverage[12] = turnover;
		statsAverage[13] = foul;
		statsAverage[14] = scores;
		statsAverage[15] = rivalScores;
		statsAverage[16] = rivalOffensiveRebound;
		statsAverage[17] = rivalDefensiveRebound;
		statsAverage[18] = rivalRebounds;
		statsAverage[19] = rivalFieldGoalAttempted;
		statsAverage[20] = rivalThreePointerAttempted;
		statsAverage[21] = fieldGoalRatio;
		statsAverage[22] = threePointerRatio;
		statsAverage[23] = freeThrowRatio;
		statsAverage[24] = myRounds;
		statsAverage[25] = offensiveEf;
		statsAverage[26] = defensiveEf;
		statsAverage[27] = offensiveReboundEf;
		statsAverage[28] = defensiveReboundEf;
		statsAverage[29] = stealEf;
		statsAverage[30] = assistEf;
		statsAverage[31]=winningRatio;
	}
	
	public void restoreVariables(){
		fieldGoalMade = statsTotal[0];
		fieldGoalAttempted = statsTotal[1];
		threePointerMade = statsTotal[2];
		threePointerAttempted = statsTotal[3];
		freeThrowMade = statsTotal[4];
		freeThrowAttempted = statsTotal[5];
		offensiveRebound = statsTotal[6];
		defensiveRebound = statsTotal[7];
		rebound = statsTotal[8];
		assist = statsTotal[9];
		steal = statsTotal[10];
		block = statsTotal[11];
		turnover = statsTotal[12];
		foul = statsTotal[13];
		scores = statsTotal[14];
		rivalScores = statsTotal[15];
		rivalOffensiveRebound = statsTotal[16];
		rivalDefensiveRebound = statsTotal[17];
		rivalRebounds = statsTotal[18];
		rivalFieldGoalAttempted = statsTotal[19];
		rivalThreePointerAttempted = statsTotal[20];
		secondInTotal = statsTotal[21];
	}

	public String getName() {
		return name;
	}

	public ArrayList<TeamInMatch> getTeamStats() {
		return teamStats;
	}

	public double getSecondInTotal() {
		return secondInTotal;
	}

	public double getFieldGoalMade() {
		return fieldGoalMade;
	}

	public double getFieldGoalAttempted() {
		return fieldGoalAttempted;
	}

	public double getThreePointerMade() {
		return threePointerMade;
	}

	public double getThreePointerAttempted() {
		return threePointerAttempted;
	}

	public double getFreeThrowMade() {
		return freeThrowMade;
	}

	public double getFreeThrowAttempted() {
		return freeThrowAttempted;
	}

	public double getOffensiveRebound() {
		return offensiveRebound;
	}

	public double getDefensiveRebound() {
		return defensiveRebound;
	}

	public double getRebound() {
		return rebound;
	}

	public double getAssist() {
		return assist;
	}

	public double getSteal() {
		return steal;
	}

	public double getBlock() {
		return block;
	}

	public double getTurnover() {
		return turnover;
	}

	public double getFoul() {
		return foul;
	}

	public double getScores() {
		return scores;
	}

	public double getFieldGoalRatio() {
		return fieldGoalRatio;
	}

	public double getThreePointerRatio() {
		return threePointerRatio;
	}

	public double getFreeThrowRatio() {
		return freeThrowRatio;
	}

	public double getMyRounds() {
		return myRounds;
	}

	public double getOffensiveEf() {
		return offensiveEf;
	}

	public double getDefensiveEf() {
		return defensiveEf;
	}

	public double getOffensiveReboundEf() {
		return offensiveReboundEf;
	}

	public double getDefensiveReboundEf() {
		return defensiveReboundEf;
	}

	public double getStealEf() {
		return stealEf;
	}

	public double getAssistEf() {
		return assistEf;
	}

	public int getNumOfMatches() {
		return numOfMatches;
	}

	public int getNumOfWins() {
		return numOfWins;
	}

	public double getWinningRatio() {
		return winningRatio;
	}

	public String getAbbr() {
		return abbr;
	}

	@Override
	public String toString() {
		return "TeamInAverage [name=" + name + ", secondInTotal="
				+ secondInTotal + ", fieldGoalMade=" + fieldGoalMade
				+ ", fieldGoalAttempted=" + fieldGoalAttempted
				+ ", threePointerMade=" + threePointerMade
				+ ", threePointerAttempted=" + threePointerAttempted
				+ ", freeThrowMade=" + freeThrowMade + ", freeThrowAttempted="
				+ freeThrowAttempted + ", offensiveRebound=" + offensiveRebound
				+ ", defensiveRebound=" + defensiveRebound + ", rebound="
				+ rebound + ", assist=" + assist + ", steal=" + steal
				+ ", block=" + block + ", turnover=" + turnover + ", foul="
				+ foul + ", scores=" + scores + ", fieldGoalRatio="
				+ fieldGoalRatio + ", threePointerRatio=" + threePointerRatio
				+ ", freeThrowRatio=" + freeThrowRatio + ", myRounds="
				+ myRounds + ", offensiveEf=" + offensiveEf + ", defensiveEf="
				+ defensiveEf + ", offensiveReboundEf=" + offensiveReboundEf
				+ ", defensiveReboundEf=" + defensiveReboundEf + ", stealEf="
				+ stealEf + ", assistEf=" + assistEf + ", numOfMatches="
				+ numOfMatches + ", numOfWins=" + numOfWins + ", winningRatio="
				+ winningRatio + "]";
	}

	void calOffensiveRounds() {
		myRounds = 0.4
				* freeThrowAttempted
				+ fieldGoalAttempted
				- 1.07
				* ((double) offensiveRebound
						/ (offensiveRebound + rivalDefensiveRebound) * (freeThrowAttempted - freeThrowMade))
				+ 1.07 * turnover;
	}

	void calOffensiveEf() {
		offensiveEf = (double) scores / 100 * myRounds;
	}

	void calDefensiveEf() {
		defensiveEf = (double) rivalScores / 100 * rivalRounds;
	}

	void calReboundEf() {
		offensiveReboundEf = (double) offensiveRebound
				/ (offensiveRebound + rivalDefensiveRebound);
		defensiveReboundEf = (double) defensiveRebound
				/ (defensiveRebound + rivalOffensiveRebound);
	}

	void calStealEf() {
		stealEf = (double) steal / rivalRounds;
	}

	void calAssistEf() {
		assistEf = (double) assist / myRounds;
	}

	void calThreePointerRatio() {
		threePointerRatio = (double) threePointerMade / threePointerAttempted;
	}

	void calFieldGoalRatio() {
		fieldGoalRatio = (double) fieldGoalMade / fieldGoalAttempted;
	}

	void calFreeThrowRatio() {
		freeThrowRatio = (double) freeThrowMade / freeThrowAttempted;
	}

	public double getRivalScores() {
		return rivalScores;
	}

	public double getRivalRounds() {
		return rivalRounds;
	}

	public double getRivalOffensiveRebound() {
		return rivalOffensiveRebound;
	}

	public double getRivalDefensiveRebound() {
		return rivalDefensiveRebound;
	}

	public double getRivalRebounds() {
		return rivalRebounds;
	}

	public double getRivalFieldGoalAttempted() {
		return rivalFieldGoalAttempted;
	}

	public double getRivalThreePointerAttempted() {
		return rivalThreePointerAttempted;
	}

	public double[] getStatsAverage() {
		return statsAverage;
	}

	public double[] getStatsTotal() {
		return statsTotal;
	}
	/**
	 * 新增功能。通过计算某球员上场和不上场的球队分别情况，衡量该球员的贡献。
	 * @param playerName
	 * @return
	 */
	public ArrayList<TeamInAverage> calPlayerContribution(String playerName){
		TeamInAverage with = new TeamInAverage(this.name,this.abbr);
		TeamInAverage without = new TeamInAverage(this.name,this.abbr);
		for(TeamInMatch t:teamStats){
			boolean in = false;
			for(PlayerInMatch p:t.players){
				if(p.name.equals(playerName)){
					with.addMatch(t);
					in = true;
					break;
				}
			}
			if(!in){
				without.addMatch(t);
			}
		}
		with.calAll();
		without.calAll();
		ArrayList<TeamInAverage> two = new ArrayList<TeamInAverage>();
		two.add(with);
		two.add(without);
		return two;
	}
	
	
}
