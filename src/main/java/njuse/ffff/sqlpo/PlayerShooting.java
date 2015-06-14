package njuse.ffff.sqlpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlayerShooting {
	String name;
	String idPlayerInfo;

	String attribute;
	String season;
	int age;
	String team;
	String league;
	String position;
	int games;
	String minutes;
	double fieldGoalPercentage;
	double averageDistance;

	double twoPOfFGA;
	double twoPOfFGA0_3;
	double twoPOfFGA3_10;
	double twoPOfFGA10_16;
	double twoPOfFGA16plus;
	double threePOfFGA;

	double FGtwoPOfFGA;
	double FGtwoPOfFGA0_3;
	double FGtwoPOfFGA3_10;
	double FGtwoPOfFGA10_16;
	double FGtwoPOfFGA16plus;
	double FGthreePOfFGA;

	double twoPAssisted;
	double dunks;
	double dunkThrowdowns;

	double threePAssisted;
	double FGAthreePFromCorner;
	double FGthreePFromCorner;
	double heaveAttempts;
	double heaveMade;

	public PlayerShooting(Map<String, Object> map) {
		name = (String) map.get("name");
		idPlayerInfo = (String) map.get("idPlayerInfo");

		attribute = (String) map.get("attribute");
		season = (String) map.get("season");
		age = (int) map.get("age");
		team = (String) map.get("team");
		league = (String) map.get("league");
		position = (String) map.get("position");
		games = (int) map.get("games");
		minutes = (String) map.get("minutes");
		fieldGoalPercentage = (double) map.get("fieldGoalPercentage");
		averageDistance = (double) map.get("averageDistance");

		twoPOfFGA = (double) map.get("twoPOfFGA");
		twoPOfFGA0_3 = (double) map.get("twoPOfFGA0_3");
		twoPOfFGA3_10 = (double) map.get("twoPOfFGA3_10");
		twoPOfFGA10_16 = (double) map.get("twoPOfFGA10_16");
		twoPOfFGA16plus = (double) map.get("twoPOfFGA16plus");
		threePOfFGA = (double) map.get("threePOfFGA");

		FGtwoPOfFGA = (double) map.get("FGtwoPOfFGA");
		FGtwoPOfFGA0_3 = (double) map.get("FGtwoPOfFGA0_3");
		FGtwoPOfFGA3_10 = (double) map.get("FGtwoPOfFGA3_10");
		FGtwoPOfFGA10_16 = (double) map.get("FGtwoPOfFGA10_16");
		FGtwoPOfFGA16plus = (double) map.get("FGtwoPOfFGA16plus");
		FGthreePOfFGA = (double) map.get("FGthreePOfFGA");

		twoPAssisted = (double) map.get("twoPAssisted");
		dunks = (double) map.get("dunks");
		dunkThrowdowns = (double) map.get("dunkThrowdowns");

		threePAssisted = (double) map.get("threePAssisted");
		FGAthreePFromCorner = (double) map.get("FGAthreePFromCorner");
		FGthreePFromCorner = (double) map.get("FGthreePFromCorner");
		heaveAttempts = (double) map.get("heaveAttempts");
		heaveMade = (double) map.get("heaveMade");
	}

	public PlayerShooting(String name, String idPlayerInfo) {
		this.name = name;
		this.idPlayerInfo = idPlayerInfo;
	}

	public void setAttributes(ArrayList<String> arr) {
		Iterator<String> it = arr.iterator();
		this.attribute = it.next();
		this.season = it.next();
		this.age = (int) parseDouble(it.next());
		this.team = it.next();
		this.league = it.next();
		this.position = it.next();
		this.games = (int) parseDouble(it.next());
		this.minutes = it.next();
		this.fieldGoalPercentage = parseDouble(it.next());
		this.averageDistance = parseDouble(it.next());

		this.twoPOfFGA = parseDouble(it.next());
		this.twoPOfFGA0_3 = parseDouble(it.next());
		this.twoPOfFGA3_10 = parseDouble(it.next());
		this.twoPOfFGA10_16 = parseDouble(it.next());
		this.twoPOfFGA16plus = parseDouble(it.next());
		this.threePOfFGA = parseDouble(it.next());

		this.FGtwoPOfFGA = parseDouble(it.next());
		this.FGtwoPOfFGA0_3 = parseDouble(it.next());
		this.FGtwoPOfFGA3_10 = parseDouble(it.next());
		this.FGtwoPOfFGA10_16 = parseDouble(it.next());
		this.FGtwoPOfFGA16plus = parseDouble(it.next());
		this.FGthreePOfFGA = parseDouble(it.next());

		this.twoPAssisted = parseDouble(it.next());
		this.dunks = parseDouble(it.next());
		this.dunkThrowdowns = parseDouble(it.next());

		this.threePAssisted = parseDouble(it.next());
		this.FGAthreePFromCorner = parseDouble(it.next());
		this.FGthreePFromCorner = parseDouble(it.next());
		this.heaveAttempts = parseDouble(it.next());
		this.heaveMade = parseDouble(it.next());
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
		map.put("name", name);
		map.put("idPlayerInfo", idPlayerInfo);

		map.put("attribute", attribute);
		map.put("season", season);
		map.put("age", age);
		map.put("team", team);
		map.put("league", league);
		map.put("position", position);
		map.put("games", games);
		map.put("minutes", minutes);
		map.put("fieldGoalPercentage", fieldGoalPercentage);
		map.put("averageDistance", averageDistance);

		map.put("twoPOfFGA", twoPOfFGA);
		map.put("twoPOfFGA0_3", twoPOfFGA0_3);
		map.put("twoPOfFGA3_10", twoPOfFGA3_10);
		map.put("twoPOfFGA10_16", twoPOfFGA10_16);
		map.put("twoPOfFGA16plus", twoPOfFGA16plus);
		map.put("threePOfFGA", threePOfFGA);

		map.put("FGtwoPOfFGA", FGtwoPOfFGA);
		map.put("FGtwoPOfFGA0_3", FGtwoPOfFGA0_3);
		map.put("FGtwoPOfFGA3_10", FGtwoPOfFGA3_10);
		map.put("FGtwoPOfFGA10_16", FGtwoPOfFGA10_16);
		map.put("FGtwoPOfFGA16plus", FGtwoPOfFGA16plus);
		map.put("FGthreePOfFGA", FGthreePOfFGA);

		map.put("twoPAssisted", twoPAssisted);
		map.put("dunks", dunks);
		map.put("dunkThrowdowns", dunkThrowdowns);

		map.put("threePAssisted", threePAssisted);
		map.put("FGAthreePFromCorner", FGAthreePFromCorner);
		map.put("FGthreePFromCorner", FGthreePFromCorner);
		map.put("heaveAttempts", heaveAttempts);
		map.put("heaveMade", heaveMade);

		return map;
	}

}
