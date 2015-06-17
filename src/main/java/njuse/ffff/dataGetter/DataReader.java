package njuse.ffff.dataGetter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.sqlpo.MatchInfo;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.sqlpo.PlayerInfo;
import njuse.ffff.sqlpo.PlayerShooting;
import njuse.ffff.sqlpo.TeamAverage;
import njuse.ffff.sqlpo.TeamAverageAdv;
import njuse.ffff.sqlpo.TeamInfo;
import njuse.ffff.util.DatabaseUtility;
import njuse.ffff.util.Mapper;

import org.apache.ibatis.session.SqlSession;

public class DataReader implements NewDataReaderService {
	String currentDate;
	SqlSession sqlSession;
	Mapper mapper;

	public DataReader() {
		initialize();
	}

	public void initialize() {
		DatabaseUtility.init();
		sqlSession = DatabaseUtility.getSqlSession();
		mapper = sqlSession.getMapper(Mapper.class);
	}

	@Override
	public List<String> selectSeasonsByPlayer(String playerid) {
		List<String> target = new ArrayList<String>();
		playerid = "'" + playerid + "'";
		target.add("season");
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("idplayerinfo", playerid);
		List<Map<String, Object>> result = mapper
				.selectFree("distinct season from playermatchinfo where idplayerinfo = "
						+ playerid);
		List<String> ret = new ArrayList<String>();
		for (Map<String, Object> m : result) {
			ret.add((String) m.get("season"));
		}
		return ret;
	}

	@Override
	public List<PlayerInMatchFull> getPlayersStatsAll(String season,
			String attribute) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("season", season);
		attribute += "%";

		filter.put("attribute", attribute);
		List<Map<String, Object>> result = mapper.selectList("playerstatinfo",
				null, filter, null);
		List<PlayerInMatchFull> players = new ArrayList<PlayerInMatchFull>();
		for (Map<String, Object> m : result) {
			Map<String, Object> advfilter = new HashMap<String, Object>();
			advfilter.put("season", season);
			advfilter.put("idplayerinfo", m.get("idplayerinfo"));
			filter.remove("attribute");
			Map<String, Object> adv = mapper.selectOne("playerstatinfoadv",
					null, filter);
			adv.putAll(m);
			players.add(new PlayerInMatchFull(adv));
		}
		return players;
	}

	@Override
	public PlayerInMatchFull getPlayerStatsSingle(String idPlayer,
			String season, String attribute) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("season", season);
		filter.put("idplayerinfo", idPlayer);
		attribute += "%";

		filter.put("attribute", attribute);
		Map<String, Object> result = mapper.selectOne("playerstatinfo", null,
				filter);
		if (result == null) {
			return null;
		}
		PlayerInMatchFull p = new PlayerInMatchFull(result);
		return p;
	}

	@Override
	public List<PlayerInMatchFull> getPlayerStats(String idPlayer, String season) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("season", season);
		filter.put("idplayerinfo", idPlayer);

		List<Map<String, Object>> result = mapper.selectList("playerstatinfo",
				null, filter, null);
		List<PlayerInMatchFull> players = new ArrayList<PlayerInMatchFull>();
		for (Map<String, Object> m : result) {
			PlayerInMatchFull p = new PlayerInMatchFull(m);
			players.add(p);
		}
		return players;
	}

	@Override
	public PlayerInfo getPlayerInfo(String name) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("plName", name);
		List<Map<String, Object>> result = mapper
				.selectFree("* from playerinfo where plName = " + "'" + name
						+ "'");
		// Map<String, Object> result = mapper.selectOne("playerinfo", null,
		// filter);
		if (!result.isEmpty()) {
			PlayerInfo p = new PlayerInfo(result.get(0));
			return p;
		}
		return null;
	}

	@Override
	public List<PlayerInfo> getPlayerInfoAll(String attribute) {
		// Map<String,Object> filter = new HashMap<String,Object>();
		List<Map<String, Object>> result = mapper.selectListFree("playerinfo",
				null, "experience >0");
		List<PlayerInfo> players = new ArrayList<PlayerInfo>();
		for (Map<String, Object> m : result) {
			PlayerInfo p = new PlayerInfo(m);
			players.add(p);
		}
		return players;
	}

	@Override
	public PlayerShooting getPlayerShooting(String playerId, String season) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("idPlayerInfo", playerId);
		filter.put("season", season);
		Map<String, Object> result = mapper.selectOne("playershooting", null,
				filter);
		PlayerShooting ps = new PlayerShooting(result);

		return ps;
	}

	@Override
	public List<TeamAverage> getTeamAverages(String season) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("season", season);
		List<Map<String, Object>> result = mapper.selectList("teamaverage",
				null, filter, null);
		List<TeamAverage> teams = new ArrayList<TeamAverage>();
		for (Map<String, Object> m : result) {
			teams.add(new TeamAverage(m));
		}
		return teams;
	}

	@Override
	public TeamAverage getTeamAverageSingle(String name, String season,
			String attribute) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("team", name);
		filter.put("season", season);
		if (attribute != null) {
			filter.put("attribute", "% " + attribute + "");
		}
		Map<String, Object> result = mapper.selectOne("teamaverage", null,
				filter);
		TeamAverage m = new TeamAverage(result);
		return m;
	}

	@Override
	public TeamAverageAdv getTeamAverageAdv(String name, String season) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("team", name);
		filter.put("season", season);

		Map<String, Object> result = mapper.selectOne("teamaverageadv", null,
				filter);
		TeamAverageAdv t = new TeamAverageAdv(result);
		return t;
	}

	@Override
	public MatchInfo getTeamStatSingle(String idTeam, Date date) {
		// Map<String, Object> filter = new HashMap<String, Object>();
		// filter.put("teamA", idTeam);
		// filter.put("date", date);
		// List<String> list = new ArrayList<String>();
		// list.add("idmatchinfo");
		// Map<String, Object> resultA = mapper.selectOne("matchinfo", list,
		// filter);
		// filter.clear();
		// filter.put("teamB", idTeam);
		// filter.put("date", date);
		// Map<String, Object> resultB = mapper.selectOne("matchinfo", list,
		// filter);
		// Map<String, Object> playerfilter = new HashMap<String, Object>();
		// if (!resultA.isEmpty()) {
		// playerfilter.put("idmatchinfo", resultA.get("idmatchinfo"));
		// Map<String, Object> resultAtA = mapper.selectOne("playermatchinfo",
		// null, playerfilter);
		// return new PlayerInMatchFull(resultAtA);
		// } else if (!resultB.isEmpty()) {
		// playerfilter.put("idmatchinfo", resultB.get("idmatchinfo"));
		// Map<String, Object> resultAtB = mapper.selectOne("playermatchinfo",
		// null, playerfilter);
		// return new PlayerInMatchFull(resultAtB);
		// } else {
		// return null;
		// }
		String dt = date.toString().replaceAll("-", "");
		dt = "'" + dt + "%'";
		idTeam = "'" + idTeam + "'";
		List<Map<String, Object>> result = mapper
				.selectFree("* from matchinfo where idmatchinfo in (select idmatchinfo from playermatchinfo where idmatchinfo like "
						+ dt + " and team =" + idTeam + ")");
		if (result.isEmpty()) {
			return null;
		}
		MatchInfo a = new MatchInfo(result.get(0));
		try {
			fullfil(a);
		} catch (Exception e) {

		}

		return a;
	}

	private void fullfil(MatchInfo match) {
		String idmatchinfo = "'" + match.getIdmatchinfo() + "'";
		String teamA = "'" + match.getTeamA() + "'";
		String teamB = "'" + match.getTeamB() + "'";

		List<Map<String, Object>> resultA = mapper
				.selectFree("* from playermatchinfo where idmatchinfo = "
						+ idmatchinfo + "and team = " + teamA
						+ "and idplayerinfo is not null");
		for (Map<String, Object> m : resultA) {
			match.getPlayersA().add(new PlayerInMatchFull(m));
		}
		List<Map<String, Object>> resultB = mapper
				.selectFree("* from playermatchinfo where idmatchinfo = "
						+ idmatchinfo + "and team = " + teamA
						+ "and idplayerinfo is not null");
		for (Map<String, Object> m : resultB) {
			match.getPlayersB().add(new PlayerInMatchFull(m));
		}
		List<Map<String, Object>> astat = mapper
				.selectFree("* from playermatchinfo where idmatchinfo = "
						+ idmatchinfo + "and team = " + teamA
						+ "and idplayerinfo is null");
		match.setTeamAStats(new PlayerInMatchFull(astat.get(0)));
		List<Map<String, Object>> bstat = mapper
				.selectFree("* from playermatchinfo where idmatchinfo = "
						+ idmatchinfo + "and team = " + teamB
						+ "and idplayerinfo is null");
		match.setTeamBStats(new PlayerInMatchFull(bstat.get(0)));
	}

	public List<MatchInfo> getTeamStatBySeason(String idTeam, String season) {
		// Map<String, Object> filter = new HashMap<String, Object>();
		// filter.put("teamA", idTeam);
		// filter.put("season", season);
		// List<String> list = new ArrayList<String>();
		// list.add("idmatchinfo");
		// List<Map<String, Object>> resultA = mapper.selectList("matchinfo",
		// list,
		// filter,null);
		// filter.clear();
		// filter.put("teamB", idTeam);
		// filter.put("season", season);
		// List<Map<String, Object>> resultB = mapper.selectList("matchinfo",
		// list,
		// filter,null);
		// Map<String, Object> playerfilter = new HashMap<String, Object>();
		// for(resultA.)
		// List<Map<String, Object>> result = mapper
		// .selectFree(
		// "* from playermatchinfo where team ="
		// + "'"+idTeam+"'" + " AND season=" + "'"+season+"'"
		// +" and team like 'Team%'");
		// List<PlayerInMatchFull> players = new ArrayList<PlayerInMatchFull>();
		// for (Map<String, Object> m : result) {
		// players.add(new PlayerInMatchFull(m));
		// }
		List<Map<String, Object>> result = mapper
				.selectFree("* from matchinfo where idmatchinfo in (select idmatchinfo from playermatchinfo where season = '"
						+ season + "' and team ='" + idTeam + "')");

		List<MatchInfo> list = new ArrayList<MatchInfo>();
		for (Map<String, Object> m : result) {
			MatchInfo a = new MatchInfo(m);
			fullfil(a);
			list.add(a);
		}
		return list;
	}

	@Override
	public List<MatchInfo> getMatchInPeriod(Date start, Date end) {
		String st = "'" + start + "'";
		String ed = "'" + end + "'";
		List<Map<String, Object>> result = mapper.selectListFree("matchinfo",
				null, "date >" + st + "AND date<" + ed);
		List<MatchInfo> list = new ArrayList<MatchInfo>();
		for (Map<String, Object> m : result) {
			MatchInfo a = new MatchInfo(m);
			fullfil(a);
			list.add(a);

		}
		return list;
	}

	@Override
	public List<PlayerInMatchFull> getLeadPlayerForDay(Date date,
			String condition) {
		Map<String, Object> filter = new HashMap<String, Object>();
		String dt = date.toString().replaceAll("-", "");
		dt = "" + dt + "";
		filter.put("idmatchinfo", dt);
		List<Map<String, Object>> result = mapper
				.selectFree("* from playermatchinfo where idmatchinfo = " + dt
						+ " and idplayerinfo is not null order by " + condition
						+ " desc");
		List<PlayerInMatchFull> play = new ArrayList<PlayerInMatchFull>();
		for (Map<String, Object> m : result) {
			play.add(new PlayerInMatchFull(m));
		}

		return play;
	}

	@Override
	public List<PlayerInMatchFull> getLeadPlayerForSeason(String season,
			String condition) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("season", season);
		filter.put("attribute", "per_game%");
		List<Map<String, Object>> result = mapper
				.selectFree("* from playermatchinfo where season = " + season
						+ " and attribute like " + "'per_game%' "
						+ "and idplayerinfo is not null order by " + condition
						+ " desc");
		List<PlayerInMatchFull> play = new ArrayList<PlayerInMatchFull>();
		for (Map<String, Object> m : result) {
			play.add(new PlayerInMatchFull(m));
		}
		return play;
	}

	@Override
	public List<TeamAverage> getLeadTeamForSeason(String season,
			String condition) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("season", season);
		filter.put("attribute", "%perGame");
		List<Map<String, Object>> result = mapper.selectList("teamaverage",
				null, filter, "ordered by " + condition + " desc");
		List<TeamAverage> teams = new ArrayList<TeamAverage>();
		for (Map<String, Object> m : result) {
			teams.add(new TeamAverage(m));

		}
		return teams;

	}

	@Override
	public List<PlayerInMatchFull> getImprovePlayer(String season,
			String condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamInfo> getTeamInfoAll() {
		List<Map<String, Object>> result = mapper.selectList("teaminfo", null,
				null, null);
		List<TeamInfo> teams = new ArrayList<TeamInfo>();
		for (Map<String, Object> m : result) {
			teams.add(new TeamInfo(m));
		}
		return teams;
	}

	@Override
	public TeamInfo getTeamInfo(String name) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("name", name);
		Map<String, Object> result = mapper.selectOne("teaminfo", null, filter);
		return new TeamInfo(result);
	}

	@Override
	public List<PlayerInMatchFull> getPlayerInMatch(String idmatchinfo) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("idmatchinfo", idmatchinfo);
		List<Map<String, Object>> result = mapper.selectList("playermatchinfo",
				null, filter, null);
		List<PlayerInMatchFull> players = new ArrayList<PlayerInMatchFull>();
		for (Map<String, Object> m : result) {
			players.add(new PlayerInMatchFull(m));
		}
		return players;
	}

	@Override
	public List<PlayerInfo> getPlayersInTeam(String teamName, String season) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("team", teamName);
		filter.put("season", season);
		filter.put("attribute", "totals.%");
		List<Map<String, Object>> result = mapper.selectList("playermatchinfo",
				null, filter, null);
		List<PlayerInfo> players = new ArrayList<PlayerInfo>();
		for (Map<String, Object> m : result) {
			players.add(new PlayerInfo(m));
		}
		return players;
	}

	@Override
	public List<MatchInfo> getMatchByPlayer(String idplayerinfo, String season) {
		List<Map<String, Object>> result = mapper
				.selectFree("* from matchinfo where season = '" + season
						+ "' and idplayerinfo ='" + idplayerinfo + "'");
		List<MatchInfo> list = new ArrayList<MatchInfo>();
		for (Map<String, Object> m : result) {
			MatchInfo a = new MatchInfo(m);
			fullfil(a);
			list.add(a);
		}
		return list;
	}

}
