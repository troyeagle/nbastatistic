package njuse.ffff.sqlpo;

import java.util.HashMap;
import java.util.Map;

public class TeamAverageAdv {
	String team;
	String season;
	double winRate;
	double myRounds;
	double offensiveEf;
	double defensiveEf;
	double offensiveReboundEf;
	double defensiveReboundEf;
	double stealEf;
	double assistEf;

	public TeamAverageAdv(TeamAverage myteam, TeamAverage rival) {
		team = myteam.team;
		season = myteam.season;
		winRate = (double) myteam.win / (myteam.win + myteam.lose);
		myRounds = 0.4
				* myteam.freeThrowAttempted
				+ myteam.fieldGoalAttempted
				- 1.07
				* ((double) myteam.offensiveRebound
						/ (myteam.offensiveRebound + rival.defensiveRebound) * (myteam.freeThrowAttempted - myteam.freeThrowMade))
				+ 1.07 * myteam.turnover;
		
		double rivalRounds = 0.4
				* rival.freeThrowAttempted
				+ rival.fieldGoalAttempted
				- 1.07
				* ((double) rival.offensiveRebound
						/ (rival.offensiveRebound + myteam.defensiveRebound) * (rival.freeThrowAttempted - rival.freeThrowMade))
				+ 1.07 * rival.turnover;
		offensiveEf = (double) myteam.points / 100 * myRounds;
		defensiveEf = (double) rival.points / 100 * rivalRounds;
		offensiveReboundEf = (double) myteam.offensiveRebound
				/ (myteam.offensiveRebound + rival.defensiveRebound);
		defensiveReboundEf = (double) myteam.defensiveRebound
				/ (myteam.defensiveRebound + rival.offensiveRebound);
		stealEf = (double) myteam.steal / rivalRounds;
		assistEf = (double) myteam.assist / myRounds;
	}

	public Map<String, Object> generateMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("team", team);
		map.put("season", season);
		map.put("winRate", winRate);
		map.put("myRounds", myRounds);
		map.put("offensiveEf", offensiveEf);
		map.put("defensiveEf", defensiveEf);
		map.put("offensiveReboundEf", offensiveReboundEf);
		map.put("defensiveReboundEf", defensiveReboundEf);
		map.put("stealEf", stealEf);
		map.put("assistEf", assistEf);
		return map;
	}

}
