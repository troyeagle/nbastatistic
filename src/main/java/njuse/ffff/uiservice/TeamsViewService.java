package njuse.ffff.uiservice;

import java.awt.Image;

/**
 * 提供球队一览界面访问和修改方法
 * 
 * @author Li
 *
 */
public interface TeamsViewService {

	/**
	 * 设置所有球队的信息
	 * 
	 * @param properties
	 * @param values
	 */
	void setTeamsInfo(String[] properties, String[][] values);

	/**
	 * 设置球队的队徽
	 * 
	 * @param names
	 *            队名数组，对应values中的name项（如果存在）
	 * @param icons
	 *            队徽图像数组
	 */
	void setTeamsIcon(String[] names, Image[] icons);

	/**
	 * 获得当前排序条件
	 * 
	 * @return
	 */
	String getFilter();
}
