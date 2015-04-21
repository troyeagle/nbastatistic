package njuse.ffff.po;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("unused")
/**
 * 负责储存球员所有比赛的平均数据
 * @author Mebleyev.G.Longinus
 *
 */
public class PlayerInAverage {
	String season;
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
	private int teamFieldGoalMade;
	private int teamFreeThrowAttempted;
	private int rivalFieldGoalAttempted;
	private int rivalThreePointerAttempted;
	private double rivalRounds;

	private boolean doubledouble;

	private double recent5ScoreAdv;
	private double recent5BlockAdv;
	private double recent5AssistAdv;
	
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
	 * 将一场比赛加入总数据中，从迭代一拆分出来供迭代二使用。
	 * 
	 * @param p
	 */
	private void addOneMatchToAll(PlayerInMatchExtended p) {
		if (p.second != 0) {
			effective++;
			if (p.firstOnMatch) {
				this.firstOnMatch++;
			}
			Queue<Double> queue = new LinkedList<Double>();
			/**
			 * Number 3: for the dirty number starts from number 3
			 * 
			 * @see PlayerInMatch
			 * 
			 */
			for (int j : p.dirty) {
				// FIXME
				statsDirty[j - 3]++;

				queue.offer(statsTotal[j - 3]);
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
			teamSecondInTotal += p.getTeam().secondInTotal;
			teamRebound += p.getTeam().rebound;
			rivalRebound += p.getTeam().rival.rebound;
			teamFieldGoalAttempted += p.getTeam().fieldGoalAttempted;
			teamThreePointerAttempted += p.getTeam().threePointerAttempted;
			teamTurnover += p.getTeam().turnover;
			teamFieldGoalMade += p.getTeam().fieldGoalMade;
			teamFreeThrowAttempted += p.getTeam().freeThrowAttempted;
			rivalFieldGoalAttempted += p.getTeam().rival.fieldGoalAttempted;
			rivalThreePointerAttempted += p.getTeam().rival.threePointerAttempted;
			rivalRounds += p.getTeam().rival.myRounds;

			statsTotal[31] += p.second;
			// Get off dirty statistics
			for (int j : p.dirty) {

				statsTotal[j - 3] = queue.poll();

			}
		}
	}

	/**
	 * 对平均值的处理
	 */
	private void averageProcess() {
		for (int i = 0; i < 16; i++) {
			statsAverage[i] = statsTotal[i] / (effective - statsDirty[i]);
		}
		statsAverage[31] = statsTotal[31] / (effective - statsDirty[31]);

		fieldGoalMade = statsAverage[0];
		fieldGoalAttempted = statsAverage[1];
		threePointerMade = statsAverage[2];
		threePointerAttempted = statsAverage[3];
		freeThrowMade = statsAverage[4];
		freeThrowAttempted = statsAverage[5];
		offensiveRebound = statsAverage[6];
		defensiveRebound = statsAverage[7];
		rebound = statsAverage[8];
		assist = statsAverage[9];
		steal = statsAverage[10];
		block = statsAverage[11];
		turnover = statsAverage[12];
		foul = statsAverage[13];
		points = statsAverage[14];

		second = statsAverage[31];
		calAllAverage();
		// TODO 关键区域，@张怡 关于队员平均信息在数组中保存的顺序。
		statsAverage[15] = playerEfficiencyRate;
		statsAverage[16] = GmSc;
		statsAverage[17] = trueShootingPercentage;
		statsAverage[18] = efficiencyGoalPercentage;
		statsAverage[19] = reboundRatio;
		statsAverage[20] = offensiveReboundRatio;
		statsAverage[21] = defensiveReboundRatio;
		statsAverage[22] = assistRatio;
		statsAverage[23] = stealRatio;
		statsAverage[24] = blockRatio;
		statsAverage[25] = turnoverRatio;
		statsAverage[26] = usingRatio;
		statsAverage[27] = freeThrowRatio;
		statsAverage[28] = threePointerRatio;
		statsAverage[29] = fieldGoalRatio;

		// playerEfficiencyRate = statsTotal[15];
		// second = statsTotal[31];

		statsAverage[30] = (statsAverage[14] + statsAverage[8] + statsAverage[9]) / 3;// 得分+篮板+助攻

	}

	/**
	 * 计算平均数据，包括脏数据处理
	 */
	// 核心方法
	public void calAverageAsArray() {
		statsAverage = new double[32];
		statsTotal = new double[32];
		statsDirty = new int[32];
		// For each PlayerInMatchExtended, add basic statistics.
		for (PlayerInMatchExtended p : playerStats) {
			addOneMatchToAll(p);
		}
		averageProcess();
		statsTotal[19]=this.firstOnMatch;
	}

	/**
	 * 迭代二使用的增量读取数据 包含对最近五场提升率的计算
	 * 
	 * @param p
	 */
	public void calAverageAsArrayNew(PlayerInMatchExtended p) {
		if (statsAverage == null) {
			statsAverage = new double[32];
			statsTotal = new double[32];
			statsDirty = new int[32];
		}
		addOneMatchToAll(p);
		averageProcess();
		statsTotal[19]=this.firstOnMatch;
		calRecentFive();
		statsTotal[21] = recent5ScoreAdv;
		statsTotal[22] = recent5BlockAdv;
		statsTotal[23] = recent5AssistAdv;
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

	public boolean getDoubledouble() {
		return doubledouble;
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
		calAssistRatio(teamSecondInTotal, teamFieldGoalMade);
	}

	void calAssistRatio(double secondInTotal, double totalFieldGoal) {
		assistRatio = (double) assist
				/ ((double) second / (secondInTotal / 5) * totalFieldGoal - fieldGoalMade);

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
		GmSc = 0.4 * (double) fieldGoalMade + points - 0.7
				* (double) fieldGoalAttempted - 0.4
				* ((double) freeThrowAttempted - (double) freeThrowMade) + 0.7
				* (double) offensiveRebound + 0.3 * (double) defensiveRebound
				+ (double) steal + 0.7 * assist + 0.7 * block - 0.4
				* (double) foul - turnover;
		if ((GmSc) == Double.NaN) {
			(GmSc) = 0;
		}
	}

	/**
	 * 关键算法
	 */
	void calRecentFive() {
		if (playerStats.size() < 6) {
			return;
		}
		PlayerInAverage history = new PlayerInAverage(this.name);
		for (int i = 0; i < playerStats.size() - 5; i++) {
			history.addOneMatchStat(playerStats.get(i));
		}
		history.calAverageAsArray();

		PlayerInAverage recent5 = new PlayerInAverage(this.name);
		for (int i = playerStats.size() - 5; i < playerStats.size(); i++) {
			recent5.addOneMatchStat(playerStats.get(i));
		}
		recent5.calAverageAsArray();

		recent5ScoreAdv = recent5.statsAverage[14] / history.statsAverage[14]
				- 1;
		recent5BlockAdv = recent5.statsAverage[11] / history.statsAverage[11]
				- 1;
		recent5AssistAdv = recent5.statsAverage[9] / history.statsAverage[9]
				- 1;

	}

	void calLastMonth(Date currentDate) {
		PlayerInAverage lastmonth = new PlayerInAverage(this.name);

		for (int i = playerStats.size(); i > 0; i--) {

			Calendar see = new GregorianCalendar();
			see.setTime(playerStats.get(i).getDate());
			Calendar current = new GregorianCalendar();
			current.setTime(currentDate);
			if(see.get(Calendar.MONTH)-current.get(Calendar.MONTH)%12==1){
				lastmonth.addOneMatchStat(playerStats.get(i));
			}
		}
		lastmonth.calAverageAsArray();
	}

	public double getRecent5ScoreAdv() {
		return recent5ScoreAdv;
	}

	public double getRecent5BlockAdv() {
		return recent5BlockAdv;
	}

	public double getRecent5AssistAdv() {
		return recent5AssistAdv;
	}

	public String getTeamName() {
		if (playerStats.size() == 0) {
			return "N/A";
		}
		return playerStats.get(playerStats.size() - 1).getTeam().nameAbbr;
	}

}
