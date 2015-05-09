package njuse.ffff.dataservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import njuse.ffff.data.SeasonStatProcessor;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamInMatch;
import njuse.ffff.po.TeamPO;
import njuse.ffff.util.Filter;
/**
 * 数据逻辑层供接口列表，其中有一些是暂时用不到或者设计出问题的
 * @author Mebleyev.G.Longinus
 *
 */
public interface DataReaderService {
	//获得所有球员平均比赛信息
	public ArrayList<PlayerInAverage> getPlayerInAverage();
	//获得某一球员的平均信息？
	public PlayerInAverage getPlayerAverage(String name,Filter filter);
//	/获得某一球员的全部比赛数据,未被使用
	public ArrayList<PlayerInMatchExtended> getPlayerStatistics(String name,Filter filter);
	//获得某一球员信息
	public PlayerPO getPlayerInfo(String name,Filter filter);
	//获得所有球员信息
	public ArrayList<PlayerPO> getPlayerInfoAll(Filter filter);
	//获得所有球队信息
	public ArrayList<TeamPO> getTeamInfoAll(Filter filter);
	//获得队伍平均比赛信息
	public TeamInAverage getTeamAverage(String name,Filter filter);
	//获得队伍所有比赛信息未被使用
	public ArrayList<TeamInMatch> getTeamStatistics(String name,Filter filter);
	//获得某一个球队信息
	
	//获得某一球队参与的比赛
	public List<MatchPO> getMatchForTeam(String team);

	//获得某一球员参与的比赛
	public List<MatchPO> getMatchForPlayer(String player);
	
	public TeamPO getTeamInfo(String name,Filter filter);
	
	

	//获得指定时间段内的比赛
	public List<MatchPO> getMatchInPeriod(Date date1,Date date2);

	//获得指定时间、某个球队打的的比赛，
	public MatchPO getMatch(Date date,String teamA);
	//初始化
	public void advancedInitialize(String path, int cacheLength)throws IOException;
	public void initialize() throws IOException;
	public void load() throws ClassNotFoundException, IOException;
	
	
	//获得某一天的某个条件筛选出来的前5名球员
	public List<PlayerInMatchExtended> getLeadPlayerForDay(Date date,int condition);

	//获得某一赛季的某个条件筛选出来的前5名球员
	public List<PlayerInAverage> getLeadPlayerForSeason(String season,int condition);

	//获得某一赛季的某个条件筛选出来的前5支球队
	public List<TeamInAverage> getLeadTeamForSeason(String season,int condition);

	//获得按某一条件的提升率筛选出来的最近5场进步最快的5名球员
	public List<PlayerInAverage> getImprovePlayer(String season,int condition);
	
	public Date getCurrentDate();
	
	public String getCurrentSeason();
	
	public SeasonStatProcessor getSeasonStatProcessor(String season);
}
