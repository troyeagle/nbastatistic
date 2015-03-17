package njuse.ffff.uiservice;

/**
 * 球员数据界面接口
 * 
 * @author Li
 *
 */
public interface PlayerDataService {

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
