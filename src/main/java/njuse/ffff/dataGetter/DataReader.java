package njuse.ffff.dataGetter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.sqlpo.PlayerInfo;
import njuse.ffff.sqlpo.PlayerShooting;
import njuse.ffff.sqlpo.TeamAverage;
import njuse.ffff.sqlpo.TeamInfo;
import njuse.ffff.util.DatabaseUtility;
import njuse.ffff.util.Mapper;

import org.apache.ibatis.session.SqlSession;

public class DataReader implements NewDataReaderService{
	String currentDate;
	SqlSession sqlSession;
	Mapper mapper;
	public void initialize(){
		DatabaseUtility.init();
		sqlSession = DatabaseUtility.getSqlSession();
		mapper = sqlSession.getMapper(Mapper.class);
	}

	@Override
	public List<String> selectSeasonsByPlayer(String playerid) {
		List<String> target = new ArrayList<String>();
		target.add("season");
		Map<String,Object> filter = new HashMap<String,Object>();
		filter.put("playerid", playerid);
		List<Map<String,Object>> result = mapper.selectList("playermatchinfo", target, filter, null);
		List<String> ret = new ArrayList<String>();
		for(Map<String,Object> m:result){
			ret.add((String)m.get("season"));
		}
		return ret;
	}

	@Override
	public List<PlayerInMatchFull> getPlayersStatsAll(String season,
			String attribute) {
		Map<String,Object> filter = new HashMap<String,Object>();
		filter.put("season", season);
		attribute+="%";
		
		filter.put("attribute", attribute);
		List<Map<String,Object>> result = mapper.selectList("playermatchinfo", null, filter, null);
		List<PlayerInMatchFull> players = new ArrayList<PlayerInMatchFull>();
		for(Map<String,Object> m:result){
			Map<String,Object> advfilter = new HashMap<String,Object>();
			advfilter.put("season", season);
			advfilter.put("idplayerinfo", m.get("idplayerinfo"));
			Map<String,Object> adv = mapper.selectOne("playermatchinfoadv", null, filter);
			adv.putAll(m);
			players.add(new PlayerInMatchFull(adv));
		}		
		return players;
	}

	@Override
	public PlayerInMatchFull getPlayerStatsSingle(String idPlayer,
			String season, String attribute) {
		Map<String,Object> filter = new HashMap<String,Object>();
		filter.put("season", season);
		filter.put("idplayerinfo", idPlayer);
		attribute+="%";
		
		filter.put("attribute", attribute);
		return null;
	}

	@Override
	public List<PlayerInMatchFull> getPlayerStats(String idPlayer, String season) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerInfo getPlayerInfo(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlayerInfo> getPlayerInfoAll(String attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerShooting getPlayerShooting(String name, String season) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamAverage> getTeamAverages(String season) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamAverage getTeamAverageSingle(String name, String season) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerInMatchFull getTeamStatSingle(String idTeam, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlayerInMatchFull> getMatchInPeriod(Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlayerInMatchFull> getLeadPlayerForDay(Date date,
			String condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlayerInMatchFull> getLeadPlayerForSeason(String season,
			String condition) {
		// TODO Auto-generated method stub
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
	 
}
