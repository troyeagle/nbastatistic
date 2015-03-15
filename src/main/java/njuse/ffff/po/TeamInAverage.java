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
	void calAverage(){
		for(TeamInMatch p:teamStats){
			
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
		
	}
	public String getName() {
		return name;
	}
	public ArrayList<TeamInMatch> getTeamStats() {
		return teamStats;
	}
	
}
