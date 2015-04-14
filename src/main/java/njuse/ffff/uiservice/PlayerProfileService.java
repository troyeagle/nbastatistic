package njuse.ffff.uiservice;

/**
 * 提供球员简介的访问和修改方法
 * 
 * @author Li
 *
 */
public interface PlayerProfileService {
	/**
	 * 设置球员简介
	 * 
	 * @param name
	 *            球员名
	 * @param position
	 *            球员的位置
	 */
	void setProfile(String name, String position, String number, String height,
			String weight, String birthday, String age, String exp, String school, String team);

}
