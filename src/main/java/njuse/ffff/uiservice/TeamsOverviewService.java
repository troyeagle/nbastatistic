package njuse.ffff.uiservice;

/**
 * 提供球队一览界面访问和修改方法
 * 
 * @author Li
 *
 */
public interface TeamsOverviewService extends OverviewService {

	/**
	 * 设置所有球队的信息
	 * 
	 * @param values
	 */
	void setTeamsAvgInfo(Object[][] values, String season);

	void setTeamsTotalInfo(Object[][] values, String season);
}
