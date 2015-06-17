package njuse.ffff.dataservice;

import java.sql.Date;
import java.util.List;

import njuse.ffff.sqlpo.MatchInfo;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.sqlpo.PlayerInfo;
import njuse.ffff.sqlpo.PlayerShooting;
import njuse.ffff.sqlpo.TeamAverage;
import njuse.ffff.sqlpo.TeamAverageAdv;
import njuse.ffff.sqlpo.TeamInfo;

public interface NewDataReaderService {
	/** 根据球员获得他参加的所有赛季，season格式为“xx-xx” */
	List<String> selectSeasonsByPlayer(String playerId);

	/**
	 * 获得某赛季所有球员数据,其中season可包含po，attribute可为totals,per_game,per_minute,per_poss
	 * ,
	 *
	* advanced直接附赠。 */
	List<PlayerInMatchFull> getPlayersStatsAll(String season, String attribute);

	/** 获得某赛季某个球员的数据。attribute同上 */
	PlayerInMatchFull getPlayerStatsSingle(String idPlayer, String season,
			String attribute);

	/** 获得某赛季某个球员所有数据项 */
	List<PlayerInMatchFull> getPlayerStats(String idPlayer, String season);

	/** 获得某一球员信息 */
	PlayerInfo getPlayerInfo(String name);

	/** 获得所有球员信息,attribute表示是否服役中 */
	List<PlayerInfo> getPlayerInfoAll(String attribute);

	/** 获得投篮信息 */
	PlayerShooting getPlayerShooting(String name, String season);

	/** 获得某个赛季全球队数据信息 */
	List<TeamAverage> getTeamAverages(String season);

	/** 获得某球队某赛季比赛信息,对于PlayerInMatchFull不要感到奇怪。我的锅。 */
	PlayerInMatchFull getTeamStatSingle(String idTeam, Date date);

	List<MatchInfo> getTeamStatBySeason(String idTeam, String season);
	
	List<MatchInfo> getMatchByPlayer(String idplayerinfo,String season);
	/** 获得指定时间段内的比赛,都含 */
	List<MatchInfo> getMatchInPeriod(Date start, Date end);

	/** condition 改成String.一定要精准，直接会把它作为条件去数据库搜索 */
	/** 不要搜索高阶项。 */
	/** 获得某一天的某个条件筛选出来的前5名球员 */
	public List<PlayerInMatchFull> getLeadPlayerForDay(Date date,
			String condition);

	/** 获得某一赛季的某个条件筛选出来的前5名球员 */
	public List<PlayerInMatchFull> getLeadPlayerForSeason(String season,
			String condition);

	/** 获得某一赛季的某个条件筛选出来的前5支球队 */
	public List<TeamAverage> getLeadTeamForSeason(String season,
			String condition);

	/** 获得按某一条件的提升率筛选出来的最近5场进步最快的5名球员 */
	public List<PlayerInMatchFull> getImprovePlayer(String season,
			String condition);
	/**
	 * 获得所有球队信息
	 * @return
	 */
	public List<TeamInfo> getTeamInfoAll();
	/**
	 * 获得某一支球队的基本信息
	 * @param name
	 * @return
	 */
	public TeamInfo getTeamInfo(String name);
	/**
	 * 获得一场比赛当中的所有球员表现
	 * @param idmatchinfo
	 * @return
	 */
	public List<PlayerInMatchFull> getPlayerInMatch(String idmatchinfo);
	/**
	 * 获得某个赛季中一支队伍的所有球员
	 * @param teamName
	 * @param season
	 * @return
	 */
	public List<PlayerInfo> getPlayersInTeam(String teamName, String season);

	/** 获得某赛季某球队数据信息 */
	TeamAverage getTeamAverageSingle(String name, String season,
			String attribute);

	TeamAverageAdv getTeamAverageAdv(String name, String season
			);
}
