package njuse.ffff.uiservice;

/**
 * 提供访问球队简介界面数据的接口
 * 
 * @author Li
 *
 */
public interface TeamProfileService {

	/**
	 * 设置球队简介
	 * 
	 * @param name
	 *            球队名
	 * @param properties
	 *            球队的属性
	 */
	void setProfile(String name, String abbr, String location,
			String league, String subleague, String homeCourt, String foundYear);

}
