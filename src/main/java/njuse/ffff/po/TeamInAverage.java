package njuse.ffff.po;

import java.io.Serializable;
import java.util.ArrayList;

public class TeamInAverage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	double secondInTotal;
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
	
	int numOfMatches =0;
	int numOfWins=0;
	double winningRatio;
	
	ArrayList<TeamInMatch> teamStats;
	
	public TeamInAverage(String name,ArrayList<MatchPO> matches){

		for(MatchPO m:matches){
			if(m.teamA.equals(name)){
				teamStats.add(m.teamStatA);
			}else if(m.teamB.equals(name)){
				teamStats.add(m.teamStatB);
			}
		}
		calAverage();
	}
	public void calAverage(){
		for(TeamInMatch p:teamStats){
			numOfMatches++;
			if(p.win){numOfWins++;}
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
			fieldGoalRatio +=p.fieldGoalRatio;
			threePointerRatio +=p.threePointerRatio;
			freeThrowRatio +=p.freeThrowRatio;
			myRounds +=p.myRounds;
			offensiveEf +=p.offensiveEf;
			defensiveEf +=p.defensiveEf;
			offensiveReboundEf +=p.offensiveReboundEf;
			defensiveReboundEf +=p.defensiveReboundEf;
			stealEf +=p.stealEf;
			assistEf +=p.assistEf;
		}
		
		fieldGoalMade /=teamStats.size();
		fieldGoalAttempted /=teamStats.size();
		threePointerMade /=teamStats.size();
		threePointerAttempted /=teamStats.size();
		freeThrowMade /=teamStats.size();
		freeThrowAttempted /=teamStats.size();
		offensiveRebound /=teamStats.size();
		defensiveRebound /=teamStats.size();
		rebound /=teamStats.size();
		assist /=teamStats.size();
		steal /=teamStats.size();
		block /=teamStats.size();
		turnover /=teamStats.size();
		foul /=teamStats.size();
		scores /=teamStats.size();
		fieldGoalRatio/=teamStats.size();
		threePointerRatio/=teamStats.size();
		freeThrowRatio/=teamStats.size();
		myRounds/=teamStats.size();
		offensiveEf/=teamStats.size();
		defensiveEf/=teamStats.size();
		offensiveReboundEf/=teamStats.size();
		defensiveReboundEf/=teamStats.size();
		stealEf/=teamStats.size();
		assistEf/=teamStats.size();
		winningRatio = (double)numOfWins/numOfMatches;
		
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
	
}
