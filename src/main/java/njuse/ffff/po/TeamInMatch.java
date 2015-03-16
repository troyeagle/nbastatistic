package njuse.ffff.po;

import java.util.ArrayList;

public class TeamInMatch {
	boolean win;
	String name;
	int secondInTotal;
	int fieldGoalMade = 0;
	int fieldGoalAttempted = 0;
	int threePointerMade = 0;
	int threePointerAttempted = 0;
	int freeThrowMade = 0;
	int freeThrowAttempted = 0;
	int offensiveRebound = 0;
	int defensiveRebound = 0;
	int rebound = 0;
	int assist = 0;
	int steal = 0;
	int block = 0;
	int turnover = 0;
	int foul = 0;
	int scores = 0;

	int tempScores = 0;

	TeamInMatch rival;
	double fieldGoalRatio;
	double threePointerRatio;
	double freeThrowRatio;
	double myRounds;
	double offensiveEf;
	double defensiveEf;
	double offensiveReboundEf;
	double defensiveReboundEf;
	double stealEf;
	double assistEf;

	public TeamInMatch(String name, ArrayList<PlayerInMatch> players,
			TeamInMatch rival, ArrayList<Integer> score,
			ArrayList<Integer> rivalScore) {
		this.name = name;
		if(score.get(0)>rivalScore.get(0)){
			win = true;
		}
		for (PlayerInMatch p : players) {
			secondInTotal += p.second;
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
			tempScores += p.points;
			this.rival = rival;
		}
	}

	public void calAll() {
		System.out.println("Team " + name + " calculating");
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

	void calOffensiveRounds() {
		myRounds = 0.4
				* freeThrowAttempted
				+ fieldGoalAttempted
				- 1.07
				* ((double) offensiveRebound
						/ (offensiveRebound + rival.defensiveRebound) * (freeThrowAttempted - freeThrowMade))
				+ 1.07 * turnover;
	}

	void calOffensiveEf() {
		offensiveEf = (double) scores / 100 * myRounds;
	}

	void calDefensiveEf() {
		defensiveEf = (double) rival.scores / 100 * rival.myRounds;
	}

	void calReboundEf() {
		offensiveReboundEf = (double) offensiveRebound
				/ (offensiveRebound + rival.defensiveRebound);
		defensiveReboundEf = (double) defensiveRebound
				/ (defensiveRebound + rival.offensiveRebound);
	}

	void setPoints(ArrayList<PlayerInMatch> players) {
		for (PlayerInMatch p : players) {
			if (p.getDirty().contains(17)) {
				int points = scores - tempScores;
				p.setPoints(points);
			}
		}
	}

	void calStealEf() {
		stealEf = (double) steal / rival.myRounds;
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

}