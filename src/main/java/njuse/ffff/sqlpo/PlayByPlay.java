package njuse.ffff.sqlpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlayByPlay {
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

	double MPatPG;
	double MPatSG;
	double MPatSF;
	double MPatPF;
	double MPatC;

	double oncourtPer100Pos;
	double onoffPer100Pos;

	double badpass;
	double lostball;
	double otherTurnover;

	double shootFoul;
	double blockFoul;
	double offensiveFoul;
	double takeFoul;

	double pointByAssists;
	double shootingFoulsDrawn;
	double fieldGoalOnFoul;
	double attemptsBlocked;

	public PlayByPlay(String name, String idPlayerInfo) {
		this.name = name;
		this.idPlayerInfo = idPlayerInfo;
	}

	public void setAttributes(ArrayList<String> arr) {
		Iterator<String> it = arr.iterator();
		//name = it.next();
		//idPlayerInfo = it.next();

		attribute = it.next();
		season = it.next();
		age = (int) parseDouble(it.next());
		team = it.next();
		league = it.next();
		position = it.next();
		games = (int) parseDouble(it.next());
		minutes = it.next();

		MPatPG = parseDouble(it.next());
		MPatSG = parseDouble(it.next());
		MPatSF = parseDouble(it.next());
		MPatPF = parseDouble(it.next());
		MPatC = parseDouble(it.next());

		oncourtPer100Pos = parseDouble(it.next());
		onoffPer100Pos = parseDouble(it.next());

		badpass = parseDouble(it.next());
		lostball = parseDouble(it.next());
		otherTurnover = parseDouble(it.next());

		shootFoul = parseDouble(it.next());
		blockFoul = parseDouble(it.next());
		offensiveFoul = parseDouble(it.next());
		takeFoul = parseDouble(it.next());

		pointByAssists = parseDouble(it.next());
		shootingFoulsDrawn = parseDouble(it.next());
		fieldGoalOnFoul = parseDouble(it.next());
		attemptsBlocked = parseDouble(it.next());
	}

	public double parseDouble(String str) {
		str = str.trim();
		if(str.endsWith("%")){
			str = str.substring(0, str.length()-1);
		}
		try {
			return Double.parseDouble(str);
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

		map.put("MPatPG", MPatPG);
		map.put("MPatSG", MPatSG);
		map.put("MPatSF", MPatSF);
		map.put("MPatPF", MPatPF);
		map.put("MPatC", MPatC);

		map.put("oncourtPer100Pos", oncourtPer100Pos);
		map.put("onoffPer100Pos", onoffPer100Pos);

		map.put("badpass", badpass);
		map.put("lostball", lostball);
		map.put("otherTurnover", otherTurnover);

		map.put("shootFoul", shootFoul);
		map.put("blockFoul", blockFoul);
		map.put("offensiveFoul", offensiveFoul);
		map.put("takeFoul", takeFoul);

		map.put("pointByAssists", pointByAssists);
		map.put("shootingFoulsDrawn", shootingFoulsDrawn);
		map.put("fieldGoalOnFoul", fieldGoalOnFoul);
		map.put("attemptsBlocked", attemptsBlocked);

		return map;
	}

}
