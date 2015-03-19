package njuse.ffff.po;

public class PlayerInMatchExtended extends PlayerInMatch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int playerEfficiencyRate;
	double fieldGoalRatio;
	double threePointerRatio;
	double freeThrowRatio;
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
	double GmSc;
	
	TeamInMatch team;

	public PlayerInMatchExtended(PlayerInMatch p,TeamInMatch team) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	public PlayerInMatchExtended(PlayerInMatch p, int playerEfficiencyRate,
			double fieldGoalRatio, double threePointerRatio,
			double freeThrowRatio, double efficiencyGoalPercentage,
			double trueShootingPercentage, double reboundRatio,
			double offensiveReboundRatio, double defensiveReboundRatio,
			double assistRatio, double stealRatio, double blockRatio,
			double turnoverRatio, double usingRatio, double gmSc,TeamInMatch team) {
		super(p);
		this.playerEfficiencyRate = playerEfficiencyRate;
		this.fieldGoalRatio = fieldGoalRatio;
		this.threePointerRatio = threePointerRatio;
		this.freeThrowRatio = freeThrowRatio;
		this.efficiencyGoalPercentage = efficiencyGoalPercentage;
		this.trueShootingPercentage = trueShootingPercentage;
		this.reboundRatio = reboundRatio;
		this.offensiveReboundRatio = offensiveReboundRatio;
		this.defensiveReboundRatio = defensiveReboundRatio;
		this.assistRatio = assistRatio;
		this.stealRatio = stealRatio;
		this.blockRatio = blockRatio;
		this.turnoverRatio = turnoverRatio;
		this.usingRatio = usingRatio;
		GmSc = gmSc;
		this.team = team;
	}

	public PlayerInMatchExtended(PlayerInMatchExtended p) {

		this(p, p.playerEfficiencyRate, p.fieldGoalRatio, p.threePointerRatio,
				p.freeThrowRatio, p.efficiencyGoalPercentage,
				p.trueShootingPercentage, p.reboundRatio,
				p.offensiveReboundRatio, p.defensiveReboundRatio,
				p.assistRatio, p.stealRatio, p.blockRatio, p.turnoverRatio,
				p.usingRatio, p.GmSc,p.team);
	}
	/**
	 * calAll<br>
	 * Calculate all advanced statistics of a player.<br>
	 * Based on all dirty statistics are properly dealed with;<br>
	 * @param team
	 * @param rival
	 */
	public void calAll(TeamInMatch team, TeamInMatch rival) {
//		System.out.println("Player " + name
//				+ " Calculating Advanced Statistics");
		calPlayerEfficiencyRate();
		calEfficiencyGoalPercentage();
		calTrueShootingPercentage();
		calFreeThrowRatio();
		calThreePointerRatio();
		calFieldGoalRatio();
		calGmSc();
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

	void calThreePointerRatio() {
		threePointerRatio = (double) threePointerMade / threePointerAttempted;
	}

	void calFieldGoalRatio() {
		fieldGoalRatio = (double) fieldGoalMade / fieldGoalAttempted;
	}

	void calFreeThrowRatio() {
		freeThrowRatio = (double) freeThrowMade / freeThrowAttempted;
	}

	void calGmSc() {
		GmSc = 0.4 * fieldGoalMade + points - 0.7 * fieldGoalAttempted - 0.4
				* (freeThrowAttempted - freeThrowMade) + 0.7 * offensiveRebound
				+ 0.3 * defensiveRebound + steal + 0.7 * assist + 0.7 * block
				- 0.4 * foul - turnover;
	}

	public int getPlayerEfficiencyRate() {
		return playerEfficiencyRate;
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

	public double getEfficiencyGoalPercentage() {
		return efficiencyGoalPercentage;
	}

	public double getTrueShootingPercentage() {
		return trueShootingPercentage;
	}

	public double getReboundRatio() {
		return reboundRatio;
	}

	public double getOffensiveReboundRatio() {
		return offensiveReboundRatio;
	}

	public double getDefensiveReboundRatio() {
		return defensiveReboundRatio;
	}

	public double getAssistRatio() {
		return assistRatio;
	}

	public double getStealRatio() {
		return stealRatio;
	}

	public double getBlockRatio() {
		return blockRatio;
	}

	public double getTurnoverRatio() {
		return turnoverRatio;
	}

	public double getUsingRatio() {
		return usingRatio;
	}

	public double getGmSc() {
		return GmSc;
	}

	public TeamInMatch getTeam() {
		return team;
	}

}
