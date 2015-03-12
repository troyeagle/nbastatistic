package njuse.ffff.dataservice;

import njuse.ffff.po.*;
import njuse.ffff.util.Filter;

public interface DataReaderService {
	public PlayerInAverage getPlayerAverage(String str,Filter filter);
	public PlayerInMatchExtended[] getPlayerStatistics(String str,Filter filter);
	public PlayerPO getPlayerInfo(String str,Filter filter);
	
	public TeamInAverage getTeamAverage(String str,Filter filter);
	public MatchPO[] getTeamStatistics(String str,Filter filter);
	public TeamPO getTeamInfo(String str,Filter filter);
}
