package njuse.ffff.sqlpo;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlayerInMatchFull {
	String name;
	String position;
	String minute;
	String playerId;
	Date date;

	String attribute;
	String season;
	String age;
	String team;
	String league;
	int gamesPlayed;
	String idMatchInfo;

	protected double fieldGoalMade;
	protected double fieldGoalAttempted;
	protected double threePointerMade;
	protected double threePointerAttempted;
	protected double freeThrowMade;
	protected double freeThrowAttempted;
	protected double offensiveRebound;
	protected double defensiveRebound;
	protected double rebound;
	protected double assist;
	protected double steal;
	protected double block;
	protected double turnover;
	protected double foul;
	protected double points;
	double plusminus;

	int firstOnMatch;

	double playerEfficiencyRate;
	double fieldGoalPercentage;
	double threePointerPercentage;
	double freeThrowRate;
	double effFGPercentage;
	double trueShootingPercentage;
	double threePAr;
	double reboundRatio;
	double offensiveReboundRatio;
	double defensiveReboundRatio;
	double assistRatio;
	double stealRatio;
	double blockRatio;
	double turnoverRatio;
	double usingRatio;
	double GmSc;
	double offensiveRating;
	double defensiveRating;


	double ows;
	double dws;
	double ws;
	double wsper48;
	double obpm;
	double dbpm;
	double bpm;
	double vorp;

	public PlayerInMatchFull(String name, String playerId) {
		this.name = name;
		this.playerId = playerId;
	}

	public PlayerInMatchFull(Map<String, Object> basicMap) {
		name = (String) basicMap.get("name");
		position = (String) basicMap.get("position");
		minute = (String) basicMap.get("minute");
		playerId = (String) basicMap.get("idplayerinfo");
		date = (Date) basicMap.get("date");

		attribute = (String) basicMap.get("attribute");
		season = (String) basicMap.get("season");
		age = (String) basicMap.get("age");
		team = (String) basicMap.get("team");
		league = (String) basicMap.get("league");
		gamesPlayed = (int) basicMap.get("gamesPlayed");
		idMatchInfo = (String) basicMap.get("idMatchInfo");

		fieldGoalMade = myParse(basicMap.get("fieldGoalMade"));
		fieldGoalAttempted = myParse(basicMap.get("fieldGoalAttempted"));
		threePointerMade = myParse(basicMap.get("threePointerMade"));
		threePointerAttempted = myParse(basicMap.get("threePointerAttempted"));
		freeThrowMade = myParse(basicMap.get("freeThrowMade"));
		freeThrowAttempted = myParse(basicMap.get("freeThrowAttempted"));
		offensiveRebound = myParse(basicMap.get("offensiveRebound"));
		defensiveRebound = myParse(basicMap.get("defensiveRebound"));
		rebound = myParse(basicMap.get("rebound"));
		assist = myParse(basicMap.get("assist"));
		steal = myParse(basicMap.get("steal"));
		block = myParse(basicMap.get("block"));
		turnover = myParse(basicMap.get("turnover"));
		foul = myParse(basicMap.get("foul"));
		points = myParse(basicMap.get("points"));
		plusminus = myParse(basicMap.get("plusminus"));

		firstOnMatch = (int) basicMap.get("firstOnMatch");

			playerEfficiencyRate = myParse(basicMap.get("playerEfficiencyRate"));

		
		fieldGoalPercentage = myParse(basicMap.get("fieldGoalPercentage"));
		threePointerPercentage = myParse( basicMap
				.get("threePointerPercentage"));
		freeThrowRate = myParse(basicMap.get("freeThrowRate"));
		effFGPercentage = myParse(basicMap.get("effFGPercentage"));
		trueShootingPercentage = myParse( basicMap
				.get("trueShootingPercentage"));
		threePAr = myParse(basicMap.get("threePAr"));
		reboundRatio = myParse(basicMap.get("reboundRatio"));
		offensiveReboundRatio = myParse(basicMap.get("offensiveReboundRatio"));
		defensiveReboundRatio = myParse(basicMap.get("defensiveReboundRatio"));
		assistRatio = myParse(basicMap.get("assistRatio"));
		stealRatio = myParse(basicMap.get("stealRatio"));
		blockRatio = myParse(basicMap.get("blockRatio"));
		turnoverRatio = myParse(basicMap.get("turnoverRatio"));
		usingRatio = myParse(basicMap.get("usingRatio"));
		GmSc = myParse(basicMap.get("GmSc"));
	

			offensiveRating = myParse(basicMap.get("offensiveRating"));
			defensiveRating = myParse(basicMap.get("defensiveRating"));
			ows = myParse(basicMap.get("ows"));
			dws = myParse(basicMap.get("dws"));
			ws = myParse(basicMap.get("ws"));
			wsper48 = myParse(basicMap.get("wsper48"));
			obpm = myParse(basicMap.get("obpm"));
			dbpm = myParse(basicMap.get("dbpm"));
			bpm = myParse(basicMap.get("bpm"));
			vorp = myParse(basicMap.get("vorp"));
		
		
	}

	private double myParse(Object object) {
		try{
			return ((Float)object).doubleValue();
		}catch(Exception e){
			return -1;
		}
		
	}

	public void setBasic(String[] split) {
		minute = split[0];
		fieldGoalMade = parseInt(split[1]);
		fieldGoalAttempted = parseInt(split[2]);
		fieldGoalPercentage = parseDouble(split[3]);
		threePointerMade = parseInt(split[4]);
		threePointerAttempted = parseInt(split[5]);
		threePointerPercentage = parseDouble(split[6]);
		freeThrowMade = parseInt(split[7]);
		freeThrowAttempted = parseInt(split[8]);
		freeThrowRate = parseDouble(split[9]);
		offensiveRebound = parseInt(split[10]);
		defensiveRebound = parseInt(split[11]);
		rebound = parseInt(split[12]);
		assist = parseInt(split[13]);
		steal = parseInt(split[14]);
		block = parseInt(split[15]);
		turnover = parseInt(split[16]);
		foul = parseInt(split[17]);
		points = parseInt(split[18]);
		if(split.length>19){
			plusminus = parseInt(split[19]);
		}
		
	}

	public void setAdvanced(String[] split) {
		trueShootingPercentage = parseDouble(split[0]);
		effFGPercentage = parseDouble(split[1]);
		threePAr = parseDouble(split[2]);
		offensiveReboundRatio = parseDouble(split[3]);

		defensiveReboundRatio = parseDouble(split[4]);
		reboundRatio = parseDouble(split[5]);
		assistRatio = parseDouble(split[6]);
		stealRatio = parseDouble(split[7]);
		blockRatio = parseDouble(split[8]);
		turnoverRatio = parseDouble(split[9]);
		usingRatio = parseDouble(split[10]);
		offensiveRating = parseDouble(split[11]);
		defensiveRating = parseDouble(split[12]);

	}

	public int parseInt(String str) {
		try {
			return Integer.parseInt(str.trim());
		} catch (NumberFormatException | NullPointerException e1) {
			return -1;
		}
	}

	public double parseDouble(String str) {
		try {
			return Double.parseDouble(str.trim());
		} catch (NumberFormatException | NullPointerException e1) {
			return -1;
		}
	}

	public int isFirstOnMatch() {
		return firstOnMatch;
	}

	public void setFirstOnMatch(int firstOnMatch) {
		this.firstOnMatch = firstOnMatch;
	}

	public String getName() {
		return name;
	}

	public void setBasicByArray(ArrayList<String> arr) {
		Iterator<String> it = arr.iterator();
		attribute = it.next();
		season = it.next();
		if (attribute.contains("playoff")) {
			season += "po";
		}
		age = it.next();
		team = it.next();
		league = it.next();
		position = it.next();
		gamesPlayed = parseInt(it.next());
		firstOnMatch = parseInt(it.next());
		minute = it.next();
		fieldGoalMade = parseDouble(it.next());
		fieldGoalAttempted = parseDouble(it.next());
		fieldGoalPercentage = parseDouble(it.next());
		threePointerMade = parseDouble(it.next());
		threePointerAttempted = parseDouble(it.next());
		threePointerPercentage = parseDouble(it.next());
		if (!attribute.contains("star")) {
			it.next();
			it.next();
			it.next();
		}

		if (attribute.contains("per_game") || attribute.contains("total")) {
			effFGPercentage = parseDouble(it.next());
		}
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
		if (attribute.contains("poss")) {
			it.next();
			offensiveRating = parseDouble(it.next());
			defensiveRating = parseDouble(it.next());
		}
	}

	public void setAdvancedByArray(ArrayList<String> arr) {
		try{
			Iterator<String> it = arr.iterator();
			attribute = it.next();
			season = it.next();
			age = it.next();
			team = it.next();
			league = it.next();
			position = it.next();
			gamesPlayed = parseInt(it.next());
			minute = it.next();
			playerEfficiencyRate = parseDouble(it.next());
			trueShootingPercentage = parseDouble(it.next());
			threePointerPercentage = parseDouble(it.next());
			freeThrowRate = parseDouble(it.next());
			offensiveReboundRatio = parseDouble(it.next());
			defensiveReboundRatio = parseDouble(it.next());
			reboundRatio = parseDouble(it.next());
			assistRatio = parseDouble(it.next());
			stealRatio = parseDouble(it.next());
			blockRatio = parseDouble(it.next());
			turnoverRatio = parseDouble(it.next());
			usingRatio = parseDouble(it.next());
			it.next();
			ows = parseDouble(it.next());
			dws = parseDouble(it.next());
			ws = parseDouble(it.next());
			wsper48 = parseDouble(it.next());
			it.next();
			obpm = parseDouble(it.next());
			dbpm = parseDouble(it.next());
			bpm = parseDouble(it.next());
			vorp = parseDouble(it.next());
		}catch(Exception e){
			System.out.println(arr.toString());
		}
		

	}

	public Map<String, Object> generateBasicMap() {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("attribute", attribute);
		inputMap.put("name", name);
		inputMap.put("position", position);
		inputMap.put("minute", minute);
		inputMap.put("idPlayerInfo", playerId);
		inputMap.put("season", season);
		inputMap.put("firstOnMatch", firstOnMatch);
		inputMap.put("age", age);
		inputMap.put("team", team);
		inputMap.put("league", league);
		inputMap.put("gamesPlayed", gamesPlayed);
		inputMap.put("fieldGoalMade", fieldGoalMade);
		inputMap.put("fieldGoalAttempted", fieldGoalAttempted);
		inputMap.put("threePointerMade", threePointerMade);
		inputMap.put("threePointerAttempted", threePointerAttempted);
		inputMap.put("freeThrowMade", freeThrowMade);
		inputMap.put("threePointerPercentage", threePointerPercentage);
		inputMap.put("freeThrowRate", freeThrowRate);
		inputMap.put("fieldGoalPercentage", fieldGoalPercentage);
		inputMap.put("effFGPercentage", effFGPercentage);
		inputMap.put("freeThrowAttempted", freeThrowAttempted);
		inputMap.put("offensiveRebound", offensiveRebound);
		inputMap.put("defensiveRebound", defensiveRebound);
		inputMap.put("rebound", rebound);
		inputMap.put("assist", assist);
		inputMap.put("steal", steal);
		inputMap.put("block", block);
		inputMap.put("turnover", turnover);
		inputMap.put("foul", foul);
		inputMap.put("points", points);
		inputMap.put("plusminus", plusminus);
		return inputMap;
	}

	public Map<String, Object> generateAdvancedMap() {
		HashMap<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("name", name);
		inputMap.put("idPlayerInfo", playerId);
		inputMap.put("attribute", attribute);
		inputMap.put("season", season);
		inputMap.put("age", age);
		inputMap.put("team", team);
		inputMap.put("league", league);
		inputMap.put("position", position);
		inputMap.put("gamesPlayed", gamesPlayed);
		inputMap.put("minute", minute);
		inputMap.put("playerEfficiencyRate", playerEfficiencyRate);
		inputMap.put("trueShootingPercentage", trueShootingPercentage);
		inputMap.put("threePAr", threePAr);
		inputMap.put("freeThrowRatio", freeThrowRate);
		inputMap.put("offensiveReboundRatio", offensiveReboundRatio);
		inputMap.put("defensiveReboundRatio", defensiveReboundRatio);
		inputMap.put("reboundRatio", reboundRatio);
		inputMap.put("assistRatio", assistRatio);
		inputMap.put("stealRatio", stealRatio);
		inputMap.put("blockRatio", blockRatio);
		inputMap.put("turnoverRatio", turnoverRatio);
		inputMap.put("usingRatio", usingRatio);
		inputMap.put("ows", ows);
		inputMap.put("dws", dws);
		inputMap.put("ws", ws);
		inputMap.put("wsper48", wsper48);
		inputMap.put("obpm", obpm);
		inputMap.put("dbpm", dbpm);
		inputMap.put("bpm", bpm);
		inputMap.put("vorp", vorp);
		return inputMap;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getIdMatchInfo() {
		return idMatchInfo;
	}

	public void setIdMatchInfo(String idMatchInfo) {
		this.idMatchInfo = idMatchInfo;
	}

	public String getPosition() {
		return position;
	}

	public String getMinute() {
		return minute;
	}

	public Date getDate() {
		return date;
	}

	public String getAttribute() {
		return attribute;
	}

	public String getSeason() {
		return season;
	}

	public String getAge() {
		return age;
	}

	public String getTeam() {
		return team;
	}

	public String getLeague() {
		return league;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public double getFieldGoalMade() {
		return fieldGoalMade;
	}

	public double getFieldGoalAttempted() {
		return fieldGoalAttempted;
	}

	public double getThreePointerMade() {
		return threePointerMade;
	}

	public double getThreePointerAttempted() {
		return threePointerAttempted;
	}

	public double getFreeThrowMade() {
		return freeThrowMade;
	}

	public double getFreeThrowAttempted() {
		return freeThrowAttempted;
	}

	public double getOffensiveRebound() {
		return offensiveRebound;
	}

	public double getDefensiveRebound() {
		return defensiveRebound;
	}

	public double getRebound() {
		return rebound;
	}

	public double getAssist() {
		return assist;
	}

	public double getSteal() {
		return steal;
	}

	public double getBlock() {
		return block;
	}

	public double getTurnover() {
		return turnover;
	}

	public double getFoul() {
		return foul;
	}

	public double getPoints() {
		return points;
	}

	public int getFirstOnMatch() {
		return firstOnMatch;
	}

	public double getPlayerEfficiencyRate() {
		return playerEfficiencyRate;
	}

	public double getFieldGoalPercentage() {
		return fieldGoalPercentage;
	}

	public double getThreePointerPercentage() {
		return threePointerPercentage;
	}

	public double getFreeThrowRate() {
		return freeThrowRate;
	}

	public double getEffFGPercentage() {
		return effFGPercentage;
	}

	public double getTrueShootingPercentage() {
		return trueShootingPercentage;
	}

	public double getThreePAr() {
		return threePAr;
	}

	public double getReboundRatio() {
		return reboundRatio;
	}

	public double getOffensiveReboundRatio() {
		return offensiveReboundRatio;
	}

	public double getDefensiveReboundRatio() {
		return defensiveReboundRatio;
	}

	public double getAssistRatio() {
		return assistRatio;
	}

	public double getStealRatio() {
		return stealRatio;
	}

	public double getBlockRatio() {
		return blockRatio;
	}

	public double getTurnoverRatio() {
		return turnoverRatio;
	}

	public double getUsingRatio() {
		return usingRatio;
	}

	public double getGmSc() {
		return GmSc;
	}

	public double getOffensiveRating() {
		return offensiveRating;
	}

	public double getDefensiveRating() {
		return defensiveRating;
	}

	public double getOws() {
		return ows;
	}

	public double getDws() {
		return dws;
	}

	public double getWs() {
		return ws;
	}

	public double getWsper48() {
		return wsper48;
	}

	public double getObpm() {
		return obpm;
	}

	public double getDbpm() {
		return dbpm;
	}

	public double getBpm() {
		return bpm;
	}

	public double getVorp() {
		return vorp;
	}

}
