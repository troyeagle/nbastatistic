package njuse.ffff.po;

import java.util.ArrayList;

public class PlayerInAverage {

	String name;
	char position;
	String minute;
	double second;
	double fieldGoalMade;
	double fieldGoalAttempted;
	double threePodoubleerMade;
	double threePodoubleerAttempted;
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
	public PlayerInAverage(String name){
		this.name = name;
		
		playerStats = new ArrayList<PlayerInMatchExtended>();
	}
	/**
	 * This Constructor is used as follows:
	 * For each player,find its statistics in every match.
	 * Because of too many matches, this method is very stupid.
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
	
	public void addOneMatchStat(PlayerInMatchExtended p){
		
		playerStats.add(p);
	}

	public void calAverage() {
		int effective = 0;
		for (PlayerInMatchExtended p : playerStats) {
			if(p.second!=0){
				effective++;

				fieldGoalMade += p.fieldGoalMade;
				fieldGoalAttempted += p.fieldGoalAttempted;
				threePodoubleerMade += p.threePointerMade;
				threePodoubleerAttempted += p.threePointerAttempted;
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
		
		second/=effective;
		fieldGoalMade/=effective;
		fieldGoalAttempted/=effective;
		threePodoubleerMade/=effective;
		threePodoubleerAttempted/=effective;
		freeThrowMade/=effective;
		freeThrowAttempted/=effective;
		offensiveRebound/=effective;
		defensiveRebound/=effective;
		rebound/=effective;
		assist/=effective;
		steal/=effective;
		block/=effective;
		turnover/=effective;
		foul/=effective;
		points/=effective;

		playerEfficiencyRate/=effective;
		fieldGoalRatio/=effective;
		threePointerRatio/=effective;
		freeThrowRatio/=effective;
		efficiencyGoalPercentage/=effective;
		trueShootingPercentage/=effective;
		reboundRatio/=effective;
		offensiveReboundRatio/=effective;
		defensiveReboundRatio/=effective;
		assistRatio/=effective;
		stealRatio/=effective;
		blockRatio/=effective;
		turnoverRatio/=effective;
		usingRatio/=effective;
		GmSc/=effective;
	}

	public String getName() {
		return name;
	}

	public ArrayList<PlayerInMatchExtended> getPlayerStats() {
		return playerStats;
	}

	
}
