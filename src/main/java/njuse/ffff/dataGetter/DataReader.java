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
import njuse.ffff.sqlpo.TeamInfo;
import njuse.ffff.util.DatabaseUtility;
import njuse.ffff.util.Mapper;

import org.apache.ibatis.session.SqlSession;

public class DataReader implements NewDataReaderService {
	String currentDate;
	SqlSession sqlSession;
	Mapper mapper;

	public void initialize() {
		DatabaseUtility.init();
		sqlSession = DatabaseUtility.getSqlSession();
		mapper = sqlSession.getMapper(Mapper.class);
	}

	@Override
	public List<String> selectSeasonsByPlayer(String playerid) {
		List<String> target = new ArrayList<String>();
		target.add("season");
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("playerid", playerid);
		List<Map<String, Object>> result = mapper.selectList("playermatchinfo",
				target, filter, null);
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
		List<Map<String, Object>> result = mapper.selectList("playermatchinfo",
				null, filter, null);
		List<PlayerInMatchFull> players = new ArrayList<PlayerInMatchFull>();
		for (Map<String, Object> m : result) {
			Map<String, Object> advfilter = new HashMap<String, Object>();
			advfilter.put("season", season);
			advfilter.put("idplayerinfo", m.get("idplayerinfo"));
			Map<String, Object> adv = mapper.selectOne("playermatchinfoadv",
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
		Map<String, Object> result = mapper.selectOne("playermatchinfo", null,
				filter);
		PlayerInMatchFull p = new PlayerInMatchFull(result);
		return p;
	}

	@Override
	public List<PlayerInMatchFull> getPlayerStats(String idPlayer, String season) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("season", season);
		filter.put("idplayerinfo", idPlayer);

		List<Map<String, Object>> result = mapper.selectList("playermatchinfo",
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

		Map<String, Object> result = mapper.selectOne("playerinfo", null,
				filter);
		PlayerInfo p = new PlayerInfo(result);
		return p;
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
	public TeamAverage getTeamAverageSingle(String name, String season) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("team", name);
		filter.put("season", season);
		Map<String, Object> result = mapper.selectOne("teamaverage", null,
				filter);
		TeamAverage m = new TeamAverage(result);
		return m;
	}

	@Override
	public PlayerInMatchFull getTeamStatSingle(String idTeam, Date date) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("teamA", idTeam);
		filter.put("date", date);
		List<String> list = new ArrayList<String>();
		list.add("idmatchinfo");
		Map<String, Object> resultA = mapper.selectOne("matchinfo", list,
				filter);
		filter.clear();
		filter.put("teamB", idTeam);
		
		Map<String, Object> resultB = mapper.selectOne("matchinfo", list,
				filter);
		Map<String, Object> playerfilter = new HashMap<String, Object>();
		if (!resultA.isEmpty()) {
			playerfilter.put("idmatchinfo", resultA.get("idmatchinfo"));
			Map<String, Object> resultAtA = mapper.selectOne("playermatchinfo",
					null, playerfilter);
			return new PlayerInMatchFull(resultAtA);
		} else if (!resultB.isEmpty()) {
			playerfilter.put("idmatchinfo", resultB.get("idmatchinfo"));
			Map<String, Object> resultAtB = mapper.selectOne("playermatchinfo",
					null, playerfilter);
			return new PlayerInMatchFull(resultAtB);
		} else {
			return null;
		}
	}

	@Override
	public List<MatchInfo> getMatchInPeriod(Date start, Date end) {
		List<Map<String, Object>> result = mapper.selectListFree("matchinfo",
				null, "date >" + start + "AND date<" + end);
		List<MatchInfo> list = new ArrayList<MatchInfo>();
		for (Map<String, Object> m : result) {
			list.add(new MatchInfo(m));
		}
		return list;
	}

	@Override
	public List<PlayerInMatchFull> getLeadPlayerForDay(Date date,
			String condition) {
		Map<String,Object> filter = new HashMap<String,Object>();
		filter.put("date", date);
		List<Map<String,Object>> result = mapper.selectList("playermatchinfo", null, filter, "ordered by "+condition+" desc");
		List<PlayerInMatchFull> play = new ArrayList<PlayerInMatchFull>();
		for(Map<String, Object> m : result){
			play.add(new PlayerInMatchFull(m));
		}
		
		return play;
	}

	@Override
	public List<PlayerInMatchFull> getLeadPlayerForSeason(String season,
			String condition) {
		Map<String,Object> filter = new HashMap<String,Object>();
		filter.put("season", season);
		filter.put("attribute", "per_game%");
		return null;
	}

	@Override
	public List<TeamAverage> getLeadTeamForSeason(String season,
			String condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlayerInMatchFull> getImprovePlayer(String season,
			String condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamInfo> getTeamInfoAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamInfo getTeamInfo(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlayerInMatchFull> getPlayerInMatch(String idmatchinfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
