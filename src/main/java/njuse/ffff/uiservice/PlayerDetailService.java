package njuse.ffff.uiservice;

import java.awt.Image;

/**
 * 提供球员简介的访问和修改方法
 * 
 * @author Li
 *
 */
public interface PlayerDetailService {
	/**
	 * 设置球员简介
	 * 
	 * @param name
	 *            球员名
	 * @param position
	 *            球员的位置
	 * @param properties
	 *            球员的属性
	 */
	void setProfile(String name, String position, String[][] properties);

	/**
	 * 设置球员的照片
	 * 
	 * @param photo
	 */
	void setPhoto(Image photo);

}
