package njuse.ffff.po;

import java.util.ArrayList;

import sun.misc.Queue;

public class PlayerInAverage {
	double[] statsAverage;
	int[] statsDirty;
	double[] statsTotal;
	
	String name;
	char position;
	String minute;
	double second;// Advanced

	double fieldGoalMade;
	double fieldGoalAttempted;
	double threePointerMade;
	double threePointerAttempted;
	double freeThrowMade;
	double freeThrowAttempted;
	double offensiveRebound;
	double defensiveRebound;
	double rebound;
	double assist;
	double steal;
	double block;
	double turnover;
	double foul;
	double points;

	double playerEfficiencyRate;
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
	public PlayerInAverage(String name, ArrayList<MatchPO> matches) {
		this.name = name;
		E: for (MatchPO m : matches) {
			for (PlayerInMatchExtended p : m.playerInAEx) {
				if (name.equals(p.name)) {
					playerStats.add(p);
					break E;
				}
			}
			for (PlayerInMatchExtended p : m.playerInBEx) {
				if (name.equals(p.name)) {
					playerStats.add(p);
					break E;
				}
			}
		}
		calAverage();
	}

	public void addOneMatchStat(PlayerInMatchExtended p) {

		playerStats.add(p);
	}

	public void calAverageAsArray() {
		int effective = 0;
		statsAverage = new double[31];
		statsTotal = new double[31];
		statsDirty = new int[31];

		for (PlayerInMatchExtended p : playerStats) {
			if (p.second != 0) {
				effective++;
				Queue<Double> queue = new Queue<Double>();
				/**
				 * Number 3: for the dirty number starts from number 3
				 * @see PlayerInMatch
				 * 
				 */
				for (int j : p.dirty) {
					System.out.println(p.getName());
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
				statsTotal[16] += p.fieldGoalRatio;
				statsTotal[17] += p.threePointerRatio;
				statsTotal[18] += p.freeThrowRatio;
				statsTotal[19] += p.efficiencyGoalPercentage;
				statsTotal[20] += p.trueShootingPercentage;
				statsTotal[21] += p.reboundRatio;
				statsTotal[22] += p.offensiveReboundRatio;
				statsTotal[23] += p.defensiveReboundRatio;
				statsTotal[24] += p.assistRatio;
				statsTotal[25] += p.stealRatio;
				statsTotal[26] += p.blockRatio;
				statsTotal[27] += p.turnoverRatio;
				statsTotal[28] += p.usingRatio;
				statsTotal[29] += p.GmSc;
				for (int j : p.dirty) {

					try {
						statsTotal[j - 3] = queue.dequeue();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		for(int i = 0;i<30;i++){
			statsAverage[i]=statsTotal[i]/(effective-statsDirty[i]);
		}
	}

	public void calAverage() {
		int effective = 0;
		for (PlayerInMatchExtended p : playerStats) {
			if (p.second != 0) {
				effective++;

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
				points += p.points;

				playerEfficiencyRate += p.playerEfficiencyRate;
				fieldGoalRatio += p.fieldGoalRatio;
				threePointerRatio += p.threePointerRatio;
				freeThrowRatio += p.freeThrowRatio;
				efficiencyGoalPercentage += p.efficiencyGoalPercentage;
				trueShootingPercentage += p.trueShootingPercentage;
				reboundRatio += p.reboundRatio;
				offensiveReboundRatio += p.offensiveReboundRatio;
				defensiveReboundRatio += p.defensiveReboundRatio;
				assistRatio += p.assistRatio;
				stealRatio += p.stealRatio;
				blockRatio += p.blockRatio;
				turnoverRatio += p.turnoverRatio;
				usingRatio += p.usingRatio;
				GmSc += p.GmSc;
			}

		}

		second /= effective;
		fieldGoalMade /= effective;
		fieldGoalAttempted /= effective;
		threePointerMade /= effective;
		threePointerAttempted /= effective;
		freeThrowMade /= effective;
		freeThrowAttempted /= effective;
		offensiveRebound /= effective;
		defensiveRebound /= effective;
		rebound /= effective;
		assist /= effective;
		steal /= effective;
		block /= effective;
		turnover /= effective;
		foul /= effective;
		points /= effective;

		playerEfficiencyRate /= effective;
		fieldGoalRatio /= effective;
		threePointerRatio /= effective;
		freeThrowRatio /= effective;
		efficiencyGoalPercentage /= effective;
		trueShootingPercentage /= effective;
		reboundRatio /= effective;
		offensiveReboundRatio /= effective;
		defensiveReboundRatio /= effective;
		assistRatio /= effective;
		stealRatio /= effective;
		blockRatio /= effective;
		turnoverRatio /= effective;
		usingRatio /= effective;
		GmSc /= effective;
	}

	public String getName() {
		return name;
	}

	public ArrayList<PlayerInMatchExtended> getPlayerStats() {
		return playerStats;
	}

}
