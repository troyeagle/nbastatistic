package njuse.ffff.uiservice;

import java.awt.Image;

/**
 * 提供访问球队简介界面数据的接口
 * 
 * @author Li
 *
 */
public interface TeamDetailService {

	/**
	 * 设置球队简介
	 * 
	 * @param name
	 *            球队名
	 * @param properties
	 *            球队的属性
	 */
	void setProfile(String name, String[][] properties);

	/**
	 * 设置球队的队徽
	 * 
	 * @param icon
	 */
	void setIcon(Image icon);

	/**
	 * 设置球队的照片
	 * 
	 * @param photo
	 */
	void setPhoto(Image photo);

}
