package njuse.ffff.po;

import java.util.ArrayList;
import java.util.Collections;

import sun.misc.Queue;

@SuppressWarnings("unused")
/**
 * 负责储存球员所有比赛的平均数据
 * @author Mebleyev.G.Longinus
 *
 */
public class PlayerInAverage {
	double[] statsAverage;// 记录并处理
	int[] statsDirty;// 记录每一项数据属性，在遍历所有球员单场数据时，有几个是脏数据
	double[] statsTotal;
	int effective; // 有效记录数，即出场数
	String name;
	char position;
	String minute;
	double second;// Advanced
	int firstOnMatch;
	String league;

	// 这些并不作为处理的数据，只是处理完之后保存而已
	private double fieldGoalMade;
	private double fieldGoalAttempted;
	private double threePointerMade;
	private double threePointerAttempted;
	private double freeThrowMade;
	private double freeThrowAttempted;
	private double offensiveRebound;
	private double defensiveRebound;
	private double rebound;
	private double assist;
	private double steal;
	private double block;
	private double turnover;
	private double foul;
	private double points;

	private double playerEfficiencyRate;
	private double fieldGoalRatio;
	private double threePointerRatio;
	private double freeThrowRatio;
	private double efficiencyGoalPercentage;
	private double trueShootingPercentage;
	private double reboundRatio;
	private double offensiveReboundRatio;
	private double defensiveReboundRatio;
	private double assistRatio;
	private double stealRatio;
	private double blockRatio;
	private double turnoverRatio;
	private double usingRatio;
	private double GmSc;

	private double teamSecondInTotal;
	private int teamRebound;
	private int rivalRebound;
	private int teamFieldGoalAttempted;
	private int teamThreePointerAttempted;
	private int teamTurnover;
	private int teamScores;
	private int teamFreeThrowAttempted;
	private int rivalFieldGoalAttempted;
	private int rivalThreePointerAttempted;
	private double rivalRounds;

	private boolean doubledouble;
	// 关键变量。用于存放该球员所有比赛数据
	ArrayList<PlayerInMatchExtended> playerStats;

	public PlayerInAverage(String name) {
		this.name = name;

		playerStats = new ArrayList<PlayerInMatchExtended>();
	}

	/**
	 * This Constructor is used as follows: For each player,find its statistics
	 * in every match. Because of too many matches, this method is very stupid.
	 * 
	 * @param name
	 * @param matches
	 */
	/*
	 * public PlayerInAverage(String name, ArrayList<MatchPO> matches) {
	 * this.name = name; E: for (MatchPO m : matches) { for
	 * (PlayerInMatchExtended p : m.playerInAEx) { if (name.equals(p.name)) {
	 * playerStats.add(p); break E; } } for (PlayerInMatchExtended p :
	 * m.playerInBEx) { if (name.equals(p.name)) { playerStats.add(p); break E;
	 * } } } calAverage(); }
	 */
	/**
	 * Used by DataReadController
	 * 
	 * @param p
	 */
	public void addOneMatchStat(PlayerInMatchExtended p) {

		playerStats.add(p);

	}

	/**
	 * 计算平均数据，包括脏数据处理
	 */
	// 核心方法
	public void calAverageAsArray() {
		statsAverage = new double[32];
		statsTotal = new double[32];
		statsDirty = new int[31];
		// For each PlayerInMatchExtended, add basic statistics.
		for (PlayerInMatchExtended p : playerStats) {
			if (p.second != 0) {
				effective++;
				if (p.firstOnMatch) {
					this.firstOnMatch++;
				}
				Queue<Double> queue = new Queue<Double>();
				/**
				 * Number 3: for the dirty number starts from number 3
				 * 
				 * @see PlayerInMatch
				 * 
				 */
				for (int j : p.dirty) {
					// FIXME
					// System.out.println(p.getName());
					statsDirty[j - 3]++;
					queue.enqueue(statsTotal[j - 3]);
				}

				statsTotal[0] += p.fieldGoalMade;
				statsTotal[1] += p.fieldGoalAttempted;
				statsTotal[2] += p.threePointerMade;
				statsTotal[3] += p.threePointerAttempted;
				statsTotal[4] += p.freeThrowMade;
				statsTotal[5] += p.freeThrowAttempted;
				statsTotal[6] += p.offensiveRebound;
				statsTotal[7] += p.defensiveRebound;
				statsTotal[8] += p.rebound;
				statsTotal[9] += p.assist;
				statsTotal[10] += p.steal;
				statsTotal[11] += p.block;
				statsTotal[12] += p.turnover;
				statsTotal[13] += p.foul;
				statsTotal[14] += p.points;

				statsTotal[15] += p.playerEfficiencyRate;
				
				teamSecondInTotal+=p.getTeam().secondInTotal;
				teamRebound+=p.getTeam().rebound;
				rivalRebound+=p.getTeam().rival.rebound;
				teamFieldGoalAttempted+=p.getTeam().fieldGoalAttempted;
				teamThreePointerAttempted+=p.getTeam().threePointerAttempted;
				teamTurnover+=p.getTeam().turnover;
				teamScores+=p.getTeam().scores;
				teamFreeThrowAttempted+=p.getTeam().freeThrowAttempted;
				rivalFieldGoalAttempted+=p.getTeam().rival.fieldGoalAttempted;
				rivalThreePointerAttempted+=p.getTeam().rival.threePointerAttempted;
				rivalRounds+=p.getTeam().rival.myRounds;
				
				statsTotal[31] += p.second;
				// Get off dirty statistics
				for (int j : p.dirty) {

					try {
						statsTotal[j - 3] = queue.dequeue();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		for (int i = 0; i < 30; i++) {
			statsAverage[i] = statsTotal[i] / (effective - statsDirty[i]);
		}

		 fieldGoalMade=statsAverage[0];
		 fieldGoalAttempted= statsAverage[1];
		 threePointerMade=statsAverage[2];
		 threePointerAttempted=statsAverage[3];
		 freeThrowMade=statsAverage[4];
		 freeThrowAttempted=statsAverage[5];
		 offensiveRebound=statsAverage[6];
		 defensiveRebound=statsAverage[7];
		 rebound=statsAverage[8];
		 assist=statsAverage[9];
		 steal=statsAverage[10];
		 block=statsAverage[11];
		 turnover=statsAverage[12];
		 foul=statsAverage[13];
		 points=statsAverage[14];
		 second = statsAverage[15];
		
		calAllAverage();
		//TODO 关键区域，@张怡	关于队员平均信息在数组中保存的顺序。		
		statsAverage[15]= playerEfficiencyRate;
		statsAverage[16]= GmSc;
		statsAverage[17]= trueShootingPercentage;
		statsAverage[18]= efficiencyGoalPercentage;
		statsAverage[19]= reboundRatio;
		statsAverage[20]= offensiveReboundRatio;
		statsAverage[21]= defensiveReboundRatio;
		statsAverage[22]= assistRatio;
		statsAverage[23]= stealRatio;
		statsAverage[24]=blockRatio;
		statsAverage[25]=turnoverRatio;
		statsAverage[26]=usingRatio;
		statsAverage[27]=freeThrowRatio;
		statsAverage[28]=threePointerRatio;
		statsAverage[29]=fieldGoalRatio;
		


		
//		playerEfficiencyRate = statsTotal[15];
		second = statsTotal[31];

		statsAverage[30] = statsAverage[14] + statsAverage[8] + statsAverage[9];// 得分+篮板+助攻


		

	}

	/*
	 * public void calAverage() { effective = 0; for (PlayerInMatchExtended p :
	 * playerStats) { if (p.second != 0) { effective++;
	 * 
	 * fieldGoalMade += p.fieldGoalMade; fieldGoalAttempted +=
	 * p.fieldGoalAttempted; threePointerMade += p.threePointerMade;
	 * threePointerAttempted += p.threePointerAttempted; freeThrowMade +=
	 * p.freeThrowMade; freeThrowAttempted += p.freeThrowAttempted;
	 * offensiveRebound += p.offensiveRebound; defensiveRebound +=
	 * p.defensiveRebound; rebound += p.rebound; assist += p.assist; steal +=
	 * p.steal; block += p.block; turnover += p.turnover; foul += p.foul; points
	 * += p.points;
	 * 
	 * playerEfficiencyRate += p.playerEfficiencyRate; fieldGoalRatio +=
	 * p.fieldGoalRatio; threePointerRatio += p.threePointerRatio;
	 * freeThrowRatio += p.freeThrowRatio; efficiencyGoalPercentage +=
	 * p.efficiencyGoalPercentage; trueShootingPercentage +=
	 * p.trueShootingPercentage; reboundRatio += p.reboundRatio;
	 * offensiveReboundRatio += p.offensiveReboundRatio; defensiveReboundRatio
	 * += p.defensiveReboundRatio; assistRatio += p.assistRatio; stealRatio +=
	 * p.stealRatio; blockRatio += p.blockRatio; turnoverRatio +=
	 * p.turnoverRatio; usingRatio += p.usingRatio; GmSc += p.GmSc; }
	 * 
	 * }
	 * 
	 * second /= effective; fieldGoalMade /= effective; fieldGoalAttempted /=
	 * effective; threePointerMade /= effective; threePointerAttempted /=
	 * effective; freeThrowMade /= effective; freeThrowAttempted /= effective;
	 * offensiveRebound /= effective; defensiveRebound /= effective; rebound /=
	 * effective; assist /= effective; steal /= effective; block /= effective;
	 * turnover /= effective; foul /= effective; points /= effective;
	 * 
	 * playerEfficiencyRate /= effective; fieldGoalRatio /= effective;
	 * threePointerRatio /= effective; freeThrowRatio /= effective;
	 * efficiencyGoalPercentage /= effective; trueShootingPercentage /=
	 * effective; reboundRatio /= effective; offensiveReboundRatio /= effective;
	 * defensiveReboundRatio /= effective; assistRatio /= effective; stealRatio
	 * /= effective; blockRatio /= effective; turnoverRatio /= effective;
	 * usingRatio /= effective; GmSc /= effective; }
	 */

	public String getName() {
		return name;
	}

	public ArrayList<PlayerInMatchExtended> getPlayerStats() {
		return playerStats;
	}

	public double[] getStatsAverage() {
		return statsAverage;
	}

	public double[] getStatsTotal() {
		return statsTotal;
	}

	public int getEffective() {
		return effective;
	}

	public String getMinute() {
		return minute;
	}

	public double getSecond() {
		return second;
	}

	public int getFirstOnMatch() {
		return firstOnMatch;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public String getLeague() {
		return league;
	}

	public void calAllAverage() {

		calPlayerEfficiencyRate();
		calEfficiencyGoalPercentage();
		calTrueShootingPercentage();
		calFreeThrowRatio();
		calThreePointerRatio();
		calFieldGoalRatio();
		calGmSc();
		calReboundRatio(teamSecondInTotal, teamRebound, rivalRebound);
		calStealRatio(teamSecondInTotal, rivalRounds);
		calBlockRatio(teamSecondInTotal, rivalFieldGoalAttempted
				- rivalThreePointerAttempted);
		calTurnoverRatio();
		calUsingRatio(teamSecondInTotal, teamFreeThrowAttempted,
				teamFieldGoalAttempted, teamTurnover);
		calDoubledouble();
		calAssistRatio(teamSecondInTotal, teamScores);
	}

	void calAssistRatio(double secondInTotal, double totalScores) {
		assistRatio = (double) assist
				/ ((double) second / (secondInTotal / 5) * totalScores - points);

	}

	void calDoubledouble() {
		ArrayList<Double> arr = new ArrayList<Double>();
		arr.add(points);
		arr.add(assist);
		arr.add(rebound);
		arr.add(steal);
		arr.add(block);
		Collections.sort(arr);
		if (arr.get(3) >= 10 && arr.get(2) < 10) {
			doubledouble = true;
		} else {
			doubledouble = false;
		}

	}

	void calPlayerEfficiencyRate() {
		playerEfficiencyRate = (points + rebound + assist + steal + block)
				- (fieldGoalAttempted - fieldGoalMade)
				- (freeThrowAttempted - freeThrowMade) - turnover;
	}

	void calEfficiencyGoalPercentage() {
		efficiencyGoalPercentage = ((double) fieldGoalMade)
				/ ((double) fieldGoalAttempted);
		if (Double.valueOf(efficiencyGoalPercentage).isNaN()) {
			(efficiencyGoalPercentage) = 0;
		}
	}

	void calTrueShootingPercentage() {
		trueShootingPercentage = (double) points
				/ ((double) 2 * fieldGoalAttempted + (double) 0.44
						* freeThrowAttempted);
		if (Double.valueOf(trueShootingPercentage).isNaN()) {
			(trueShootingPercentage) = 0;
		}
	}

	void calReboundRatio(double secondTotal, int teamReboundsTotal,
			int rivalReboundsTotal) {
		reboundRatio = (double) rebound * secondTotal / 5 / (second)
				/ (teamReboundsTotal + rivalReboundsTotal);
		if (Double.valueOf(reboundRatio).isNaN()) {
			(reboundRatio) = 0;
		}
		offensiveReboundRatio = (double) offensiveRebound * (secondTotal / 5)
				/ (second) / (teamReboundsTotal + rivalReboundsTotal);
		if (Double.valueOf(offensiveReboundRatio).isNaN()) {
			(offensiveReboundRatio) = 0;
		}
		defensiveReboundRatio = (double) defensiveRebound * (secondTotal / 5)
				/ (second) / (teamReboundsTotal + rivalReboundsTotal);
		if (Double.valueOf(defensiveReboundRatio).isNaN()) {
			(defensiveReboundRatio) = 0;
		}

	}

	void calStealRatio(double secondTotal, double rivalRound) {
		stealRatio = (double) steal * secondTotal / 5 / (second) / rivalRound;
		if (Double.valueOf(stealRatio).isNaN()) {
			(stealRatio) = 0;
		}
	}

	void calBlockRatio(double secondTotal, int twoPointAttempts) {
		blockRatio = (double) block * secondTotal / 5 / (second)
				/ twoPointAttempts;
		if (Double.valueOf(blockRatio).isNaN()) {
			(blockRatio) = 0;
		}
	}

	void calTurnoverRatio() {
		turnoverRatio = (double) turnover
				/ (0.44 * freeThrowAttempted + fieldGoalAttempted + turnover);
		if (Double.valueOf(turnoverRatio).isNaN()) {
			(turnoverRatio) = 0;
		}
	}

	void calUsingRatio(double secondTotal, double freeThrowTotal,
			double fieldGoalTotal, double turnoverTotal) {
		usingRatio = (0.44 * freeThrowAttempted + fieldGoalAttempted + turnover)
				* secondTotal
				/ 5
				/ (second)
				/ (0.44 * freeThrowTotal + fieldGoalTotal + turnoverTotal);
		if (Double.valueOf(usingRatio).isNaN()) {
			(usingRatio) = 0;
		}
	}

	void calThreePointerRatio() {
		threePointerRatio = (double) threePointerMade / threePointerAttempted;
		if (Double.valueOf(threePointerRatio).isNaN()) {
			(threePointerRatio) = 0;
		}
	}

	void calFieldGoalRatio() {
		fieldGoalRatio = (double) fieldGoalMade / fieldGoalAttempted;
		if (Double.valueOf(fieldGoalRatio).isNaN()) {
			(fieldGoalRatio) = 0;
		}
	}

	void calFreeThrowRatio() {
		freeThrowRatio = (double) freeThrowMade / freeThrowAttempted;
		if (Double.valueOf(freeThrowRatio).isNaN()) {
			(freeThrowRatio) = 0;
		}

	}

	void calGmSc() {
		GmSc = 0.4 * (double)fieldGoalMade + points - 0.7 * (double)fieldGoalAttempted - 0.4
				* ((double)freeThrowAttempted - (double)freeThrowMade) + 0.7 * (double)offensiveRebound
				+ 0.3 * (double)defensiveRebound + (double)steal + 0.7 * assist + 0.7 * block
				- 0.4 * (double)foul - turnover;
		if ((GmSc) == Double.NaN) {
			(GmSc) = 0;
		}
	}
	public String getTeamName(){
		if(playerStats.size()==0){
			return "N/A";
		}
		return playerStats.get(playerStats.size()-1).getTeam().nameAbbr;
	}
}
