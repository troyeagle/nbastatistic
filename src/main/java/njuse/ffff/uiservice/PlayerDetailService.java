package njuse.ffff.uiservice;

import java.awt.Image;

/**
 * 提供球员详情的访问和修改方法
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
	 * @param properties
	 *            球员的属性
	 */
	void setProfile(String name, String[][] properties);

	/**
	 * 设置球员的照片
	 * 
	 * @param photo
	 */
	void setPhoto(Image photo);

	/**
	 * 设置一个数据组（常以表格出现？）
	 * 
	 * @param season
	 *            赛季
	 * @param tableName
	 *            数据组的名称
	 * @param properties
	 *            数据组的属性（表头（列））
	 * @param value
	 *            具体数据
	 */
	void putTable(int season, String tableName, String[] properties, String[][] value);
}
