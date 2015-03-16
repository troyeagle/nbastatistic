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

public interface DataReaderService {
	public PlayerInAverage getPlayerAverage(String name,Filter filter);
	public ArrayList<PlayerInMatchExtended> getPlayerStatistics(String name,Filter filter);
	public PlayerPO getPlayerInfo(String name,Filter filter);
	
	public TeamInAverage getTeamAverage(String name,Filter filter);
	public ArrayList<TeamInMatch> getTeamStatistics(String name,Filter filter);
	public TeamPO getTeamInfo(String name,Filter filter);
	public void initialize() throws IOException;
	public void load() throws ClassNotFoundException, IOException;
}
