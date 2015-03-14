package njuse.ffff.po;

import java.util.ArrayList;

public class TeamInAverage{
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
		
	}
	
	
}
