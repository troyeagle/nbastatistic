package njuse.ffff.dataservice;

import java.io.IOException;
import java.util.ArrayList;

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
	public ArrayList<PlayerInAverage> getPlayerInAverage();
	public PlayerInAverage getPlayerAverage(String name,Filter filter);
	public ArrayList<PlayerInMatchExtended> getPlayerStatistics(String name,Filter filter);
	public PlayerPO getPlayerInfo(String name,Filter filter);
	public ArrayList<PlayerPO> getPlayerInfoAll(Filter filter);
	public ArrayList<TeamPO> getTeamInfoAll(Filter filter);
	public TeamInAverage getTeamAverage(String name,Filter filter);
	public ArrayList<TeamInMatch> getTeamStatistics(String name,Filter filter);
	public TeamPO getTeamInfo(String name,Filter filter);
	public void initialize() throws IOException;
	public void load() throws ClassNotFoundException, IOException;
}
