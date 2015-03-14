package njuse.ffff.po;

public class PlayerInMatchExtended extends PlayerInMatch {

	int playerEfficiencyRate;
	double efficiencyGoalPercentage;
	double trueShootingPercentage;
	double reboundRatio;
	double offensiveReboundRatio;
	double defensiveReboundRatio;
	double assistRatio;
	double stealRatio;
	double blockRatio;
	double turnoverRatio;
	double usingRatio;

	public PlayerInMatchExtended(PlayerInMatch p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	void calPlayerEfficiencyRate() {
		playerEfficiencyRate = (points + rebound + assist + steal + block)
				- (fieldGoalAttempted - fieldGoalMade)
				- (freeThrowAttempted - freeThrowMade) - turnover;
	}

	void calEfficiencyGoalPercentage() {
		efficiencyGoalPercentage = ((double) fieldGoalMade)
				/ ((double) fieldGoalAttempted);
	}

	void caltrueShootingPercentage() {
		trueShootingPercentage = (double) points
				/ ((double) 2 * fieldGoalAttempted + (double) 0.44
						* freeThrowAttempted);
	}

	void calReboundRatio(double minuteTotal, int teamReboundsTotal,
			int rivalReboundsTotal) {
		reboundRatio = (double) rebound * (minuteTotal / 5) / minute
				/ (teamReboundsTotal + rivalReboundsTotal);
		offensiveReboundRatio = (double) offensiveRebound * (minuteTotal / 5)
				/ minute / (teamReboundsTotal + rivalReboundsTotal);
		defensiveReboundRatio = (double) defensiveRebound * (minuteTotal / 5)
				/ minute / (teamReboundsTotal + rivalReboundsTotal);
	}

	void calStealRatio(double minuteTotal, double rivalRound) {
		stealRatio = (double) steal * (minuteTotal / 5) / minute / rivalRound;
	}

	void calBlockRatio(double minuteTotal, int twoPointAttempts) {
		blockRatio = (double) block * (minuteTotal / 6) / minute
				/ twoPointAttempts;

	}

	void calTurnoverRatio() {
		turnoverRatio = (double) turnover
				/ (0.44 * freeThrowAttempted + fieldGoalAttempted + turnover);
	}

	void calUsingRatio(double minuteTotal, double freeThrowTotal,
			double fieldGoalTotal, double turnoverTotal) {
		usingRatio = (0.44 * freeThrowAttempted + fieldGoalAttempted + turnover)
				* (minuteTotal / 5)
				/ minute
				/ (0.44 * freeThrowTotal + fieldGoalTotal + turnoverTotal);
	}

}
