package njuse.ffff.uiservice;

import java.awt.Image;

/**
 * 提供球员一览界面访问和修改方法
 * 
 * @author Li
 *
 */
public interface PlayersViewService {

	/**
	 * 设置所有球员的属性及数据
	 * 
	 * @param properties
	 * @param values
	 */
	void setPlayersInfo(String[] properties, String[][] values);

	/**
	 * 设置球员的头像
	 * 
	 * @param names
	 *            球员姓名数组
	 * @param photos
	 *            头像数组
	 */
	void setPlayersPhoto(String[] names, Image[] photos);
	
	/**
	 * 获得当前排序条件
	 * @return
	 */
	String getFilter();
}
