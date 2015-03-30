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
	private int fieldGoalMade = 0;
	private int fieldGoalAttempted = 0;
	private int threePointerMade = 0;
	private int threePointerAttempted = 0;
	private int freeThrowMade = 0;
	private int freeThrowAttempted = 0;
	private int offensiveRebound = 0;
	private int defensiveRebound = 0;
	private int rebound = 0;
	private int assist = 0;
	private int steal = 0;
	private int block = 0;
	private int turnover = 0;
	private int foul = 0;
	private int scores = 0;
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

	private int rivalScores;
	private double rivalRounds;
	private int rivalOffensiveRebound;
	private int rivalDefensiveRebound;
	private int rivalRebounds;
	private int rivalFieldGoalAttempted;
	private int rivalThreePointerAttempted;

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

	public void addOneMatchToAll(TeamInMatch p) {
		numOfMatches++;
		if (p.win) {
			numOfWins++;
		}
		fieldGoalMade += p.fieldGoalMade;
		fieldGoalAttempted += p.fieldGoalAttempted;
		threePointerMade += p.threePointerMade;
		threePointerAttempted += p.threePointerAttempted;
		freeThrowMade += p.freeThrowMade;
		freeThrowAttempted += p.freeThrowAttempted;
		offensiveRebound += p.offensiveRebound;
		defensiveRebound += p.defensiveRebound;
		rebound += p.rebound;
		assist += p.assist;
		steal += p.steal;
		block += p.block;
		turnover += p.turnover;
		foul += p.foul;
		scores += p.scores;
		rivalScores += p.rival.scores;
		rivalOffensiveRebound += p.rival.offensiveRebound;
		rivalDefensiveRebound += p.rival.defensiveRebound;
		rivalRebounds += p.rival.rebound;
		rivalFieldGoalAttempted += p.rival.fieldGoalAttempted;
		rivalThreePointerAttempted += p.rival.threePointerAttempted;
		rivalRounds += p.myRounds;
		secondInTotal += p.secondInTotal;
	}

	public void calAverage() {
		for (TeamInMatch p : teamStats) {
			addOneMatchToAll(p);
		}
		averageProcess();
	}

	private void averageProcess() {
		makeTotalArray();
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
		calAll();
		winningRatio = (double) numOfWins / numOfMatches;
		makeArray();
	}

	// Iteration 2
	public void calAverageWithNew(TeamInMatch p) {
		teamStats.add(p);
		addOneMatchToAll(p);
		averageProcess();

	}

	public void makeTotalArray() {
		statsTotal = new double[22];
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
		statsAverage = new double[31];
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

	public int getFieldGoalMade() {
		return fieldGoalMade;
	}

	public int getFieldGoalAttempted() {
		return fieldGoalAttempted;
	}

	public int getThreePointerMade() {
		return threePointerMade;
	}

	public int getThreePointerAttempted() {
		return threePointerAttempted;
	}

	public int getFreeThrowMade() {
		return freeThrowMade;
	}

	public int getFreeThrowAttempted() {
		return freeThrowAttempted;
	}

	public int getOffensiveRebound() {
		return offensiveRebound;
	}

	public int getDefensiveRebound() {
		return defensiveRebound;
	}

	public int getRebound() {
		return rebound;
	}

	public int getAssist() {
		return assist;
	}

	public int getSteal() {
		return steal;
	}

	public int getBlock() {
		return block;
	}

	public int getTurnover() {
		return turnover;
	}

	public int getFoul() {
		return foul;
	}

	public int getScores() {
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

	public int getRivalScores() {
		return rivalScores;
	}

	public double getRivalRounds() {
		return rivalRounds;
	}

	public int getRivalOffensiveRebound() {
		return rivalOffensiveRebound;
	}

	public int getRivalDefensiveRebound() {
		return rivalDefensiveRebound;
	}

	public int getRivalRebounds() {
		return rivalRebounds;
	}

	public int getRivalFieldGoalAttempted() {
		return rivalFieldGoalAttempted;
	}

	public int getRivalThreePointerAttempted() {
		return rivalThreePointerAttempted;
	}

	public double[] getStatsAverage() {
		return statsAverage;
	}

	public double[] getStatsTotal() {
		return statsTotal;
	}

}
