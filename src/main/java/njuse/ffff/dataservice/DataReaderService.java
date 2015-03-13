package njuse.ffff.dataservice;

import njuse.ffff.po.*;
import njuse.ffff.util.Filter;

public interface DataReaderService {
	public PlayerInAverage getPlayerAverage(String name,Filter filter);
	public PlayerInMatchExtended[] getPlayerStatistics(String name,Filter filter);
	public PlayerPO getPlayerInfo(String name,Filter filter);
	
	public TeamInAverage getTeamAverage(String name,Filter filter);
	public MatchPO[] getTeamStatistics(String name,Filter filter);
	public TeamPO getTeamInfo(String name,Filter filter);
}
