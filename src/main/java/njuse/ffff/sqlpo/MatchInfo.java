package njuse.ffff.sqlpo;

import java.sql.Date;
import java.util.Map;

public class MatchInfo {
	String idmatchinfo;
	String season;
	Date date;
	String teamA;
	String teamB;
	String host;
	String scoreInSectionA;
	String scoreInSectionB;
	String fourFactorsA;
	String fourFactorsB;

	public MatchInfo(Map<String, Object> map) {
		idmatchinfo = (String) map.get("idmatchinfo");
		season = (String) map.get("season");
		date = (Date) map.get("date");
		teamA = (String) map.get("teamA");
		teamB = (String) map.get("teamB");
		host = (String) map.get("host");
		scoreInSectionA = (String) map.get("scoreInSectionA");
		scoreInSectionB = (String) map.get("scoreInSectionB");
		fourFactorsA = (String) map.get("fourFactorsA");
		fourFactorsB = (String) map.get("fourFactorsB");
	}

	public String getIdmatchinfo() {
		return idmatchinfo;
	}

	public String getSeason() {
		return season;
	}

	public Date getDate() {
		return date;
	}

	public String getTeamA() {
		return teamA;
	}

	public String getTeamB() {
		return teamB;
	}

	public String getHost() {
		return host;
	}

	public String getScoreInSectionA() {
		return scoreInSectionA;
	}

	public String getScoreInSectionB() {
		return scoreInSectionB;
	}

	public String getFourFactorsA() {
		return fourFactorsA;
	}

	public String getFourFactorsB() {
		return fourFactorsB;
	}
}
