package njuse.ffff.po;

public class PlayerInMatchExtended extends PlayerInMatch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public void calAll(TeamInMatch team, TeamInMatch rival) {
		System.out.println("Player "+name + " Calculating Advanced Statistics");
		calPlayerEfficiencyRate();
		calEfficiencyGoalPercentage();
		calTrueShootingPercentage();
		calReboundRatio(team.secondInTotal, team.rebound, rival.rebound);
		calStealRatio(team.secondInTotal, rival.myRounds);
		calBlockRatio(team.secondInTotal, rival.fieldGoalAttempted
				- rival.threePointerAttempted);
		calTurnoverRatio();
		calUsingRatio(team.secondInTotal, team.freeThrowAttempted,
				team.fieldGoalAttempted, team.turnover);

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

	void calTrueShootingPercentage() {
		trueShootingPercentage = (double) points
				/ ((double) 2 * fieldGoalAttempted + (double) 0.44
						* freeThrowAttempted);
	}

	void calReboundRatio(double secondTotal, int teamReboundsTotal,
			int rivalReboundsTotal) {
		reboundRatio = (double) rebound * secondTotal / 5 / (second)
				/ (teamReboundsTotal + rivalReboundsTotal);
		offensiveReboundRatio = (double) offensiveRebound * (secondTotal / 5)
				/ (second) / (teamReboundsTotal + rivalReboundsTotal);
		defensiveReboundRatio = (double) defensiveRebound * (secondTotal / 5)
				/ (second) / (teamReboundsTotal + rivalReboundsTotal);
	}

	void calStealRatio(double secondTotal, double rivalRound) {
		stealRatio = (double) steal * secondTotal / 5 / (second) / rivalRound;
	}

	void calBlockRatio(double secondTotal, int twoPointAttempts) {
		blockRatio = (double) block * secondTotal / 5 / (second)
				/ twoPointAttempts;

	}

	void calTurnoverRatio() {
		turnoverRatio = (double) turnover
				/ (0.44 * freeThrowAttempted + fieldGoalAttempted + turnover);
	}

	void calUsingRatio(double secondTotal, double freeThrowTotal,
			double fieldGoalTotal, double turnoverTotal) {
		usingRatio = (0.44 * freeThrowAttempted + fieldGoalAttempted + turnover)
				* secondTotal
				/ 5
				/ (second)
				/ (0.44 * freeThrowTotal + fieldGoalTotal + turnoverTotal);
	}

}
