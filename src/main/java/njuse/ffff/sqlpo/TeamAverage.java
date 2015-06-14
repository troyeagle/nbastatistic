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

	public TeamAverage(Map<String, Object> map) {
		season = (String) map.get("season");
		league = (String) map.get("league");
		team = (String) map.get("team");
		win = (int) map.get("win");
		lose = (int) map.get("lose");
		finish = (int) map.get("finish");
		aveAge = (double) map.get("aveAge");
		aveHeight = (String) map.get("aveHeight");
		aveWeight = (double) map.get("aveWeight");
		games = (int) map.get("games");
		minutesPlayed = (double) map.get("minutesPlayed");
		fieldGoalMade = (double) map.get("fieldGoalMade");
		fieldGoalAttempted = (double) map.get("fieldGoalAttempted");
		fieldGoalPercentage = (double) map.get("fieldGoalPercentage");
		threePointerMade = (double) map.get("threePointerMade");
		threePointerAttempted = (double) map.get("threePointerAttempted");
		threePointerPercentage = (double) map.get("threePointerPercentage");
		freeThrowMade = (double) map.get("freeThrowMade");
		freeThrowAttempted = (double) map.get("freeThrowAttempted");
		freeThrowRate = (double) map.get("freeThrowRate");
		offensiveRebound = (double) map.get("offensiveRebound");
		defensiveRebound = (double) map.get("defensiveRebound");
		rebound = (double) map.get("rebound");
		assist = (double) map.get("assist");
		steal = (double) map.get("steal");
		block = (double) map.get("block");
		turnover = (double) map.get("turnover");
		foul = (double) map.get("foul");
		points = (double) map.get("points");

		attribute = (String) map.get("attribute");
	}

	public TeamAverage(String attribute, ArrayList<String> str) {
		this.attribute = attribute;
		Iterator<String> it = str.iterator();
		season = it.next().trim();
		league = it.next().trim();
		team = it.next().trim();
		win = (int) parseDouble(it.next().trim());
		lose = (int) parseDouble(it.next().trim());
		finish = (int) parseDouble(it.next().trim());
		it.next().trim();
		if (!attribute.contains("opp")) {
			aveAge = parseDouble(it.next().trim());
			aveHeight = it.next().trim();
			aveWeight = parseDouble(it.next().trim());
			it.next().trim();
		}
		games = (int) parseDouble(it.next().trim());
		minutesPlayed = parseDouble(it.next().trim());
		fieldGoalMade = parseDouble(it.next().trim());
		fieldGoalAttempted = parseDouble(it.next().trim());
		fieldGoalPercentage = parseDouble(it.next().trim());
		threePointerMade = parseDouble(it.next().trim());
		threePointerAttempted = parseDouble(it.next().trim());
		threePointerPercentage = parseDouble(it.next().trim());
		it.next().trim();
		it.next().trim();
		it.next().trim();
		freeThrowMade = parseDouble(it.next().trim());
		freeThrowAttempted = parseDouble(it.next().trim());
		freeThrowRate = parseDouble(it.next().trim());
		offensiveRebound = parseDouble(it.next().trim());
		defensiveRebound = parseDouble(it.next().trim());
		rebound = parseDouble(it.next().trim());
		assist = parseDouble(it.next().trim());
		steal = parseDouble(it.next().trim());
		block = parseDouble(it.next().trim());
		turnover = parseDouble(it.next().trim());
		foul = parseDouble(it.next().trim());
		points = parseDouble(it.next().trim());
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
