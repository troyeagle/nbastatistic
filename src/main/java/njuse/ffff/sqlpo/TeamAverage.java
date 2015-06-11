package njuse.ffff.sqlpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TeamAverage {
	String season;
	String league;
	String team;
	int win;
	int lose;
	int finish;
	double aveAge;
	String aveHeight;
	double aveWeight;
	int games;
	double minutesPlayed;
	double fieldGoalMade;
	double fieldGoalAttempted;
	double fieldGoalPercentage;
	double threePointerMade;
	double threePointerAttempted;
	double threePointerPercentage;
	double freeThrowMade;
	double freeThrowAttempted;
	double freeThrowRate;
	double offensiveRebound;
	double defensiveRebound;
	double rebound;
	double assist;
	double steal;
	double block;
	double turnover;
	double foul;
	double points;

	String attribute;

	public TeamAverage(String attribute, ArrayList<String> str) {
		this.attribute = attribute;
		Iterator<String> it = str.iterator();
		season = it.next();
		league = it.next();
		team = it.next();
		win = (int) parseDouble(it.next());
		lose = (int) parseDouble(it.next());
		finish = (int) parseDouble(it.next());
		it.next();
		if(!attribute.contains("opp")){
			aveAge = parseDouble(it.next());
			aveHeight = it.next();
			aveWeight = parseDouble(it.next());
			it.next();
		}		
		games = (int) parseDouble(it.next());
		minutesPlayed = parseDouble(it.next());
		fieldGoalMade = parseDouble(it.next());
		fieldGoalAttempted = parseDouble(it.next());
		fieldGoalPercentage = parseDouble(it.next());
		threePointerMade = parseDouble(it.next());
		threePointerAttempted = parseDouble(it.next());
		threePointerPercentage = parseDouble(it.next());
		it.next();it.next();it.next();
		freeThrowMade = parseDouble(it.next());
		freeThrowAttempted = parseDouble(it.next());
		freeThrowRate = parseDouble(it.next());
		offensiveRebound = parseDouble(it.next());
		defensiveRebound = parseDouble(it.next());
		rebound = parseDouble(it.next());
		assist = parseDouble(it.next());
		steal = parseDouble(it.next());
		block = parseDouble(it.next());
		turnover = parseDouble(it.next());
		foul = parseDouble(it.next());
		points = parseDouble(it.next());
	}

	public double parseDouble(String str) {
		try {
			return Double.parseDouble(str.trim());
		} catch (NumberFormatException | NullPointerException e1) {
			return -1;
		}
	}

	public Map<String, Object> generateMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("attribute", attribute);
		map.put("season", season);
		map.put("league", league);
		map.put("team", team);
		map.put("win", win);
		map.put("lose", lose);
		map.put("finish", finish);
		map.put("aveAge", aveAge);
		map.put("aveHeight", aveHeight);
		map.put("aveWeight", aveWeight);
		map.put("games", games);
		map.put("minutesPlayed", minutesPlayed);
		map.put("fieldGoalMade", fieldGoalMade);
		map.put("fieldGoalAttempted", fieldGoalAttempted);
		map.put("fieldGoalPercentage", fieldGoalPercentage);
		map.put("threePointerMade", threePointerMade);
		map.put("threePointerAttempted", threePointerAttempted);
		map.put("threePointerPercentage", threePointerPercentage);
		map.put("freeThrowMade", freeThrowMade);
		map.put("freeThrowAttempted", freeThrowAttempted);
		map.put("freeThrowRate", freeThrowRate);
		map.put("offensiveRebound", offensiveRebound);
		map.put("defensiveRebound", defensiveRebound);
		map.put("rebound", rebound);
		map.put("assist", assist);
		map.put("steal", steal);
		map.put("block", block);
		map.put("turnover", turnover);
		map.put("foul", foul);
		map.put("points", points);
		return map;
	}
}
