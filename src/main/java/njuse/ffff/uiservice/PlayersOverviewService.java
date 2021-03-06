package njuse.ffff.uiservice;

/**
 * 提供球员一览界面访问和修改方法
 * 
 * @author Li
 *
 */
public interface PlayersOverviewService extends OverviewService {

	/**
	 * 设置所有球员的数据
	 * 
	 * @param values
	 */
	void setPlayersAvgInfo(Object[][] values, String season);
	
	void setPlayersTotalInfo(Object[][] values, String season);
}
