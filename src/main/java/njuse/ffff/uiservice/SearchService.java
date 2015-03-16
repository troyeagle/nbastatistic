package njuse.ffff.uiservice;

/**
 * 搜索界面的访问和修改方法
 * 
 * @author Li
 *
 */
public interface SearchService {

	/**
	 * 提供搜索时非空的限定条件
	 * 
	 * @return <code>arr[i][0]</code>: 条件名<br>
	 *         <code>arr[i][1]</code>: 限定条件1<br>
	 *         <code>arr[i][2]</code>: 限定条件2（若没有则为<code>null</code>）
	 */
	String[][] getFilters();

	/**
	 * 添加一个搜索的过滤项
	 * 
	 * @param type
	 *            过滤项的分类，若不存在则新建
	 * @param name
	 *            过滤项的名称
	 * @param isRangeValue
	 *            过滤项是否为范围值（限定条件为2个）
	 */
	void addFilter(String type, String name, boolean isRangeValue);

	/**
	 * 设置主搜索项
	 * 
	 * @param name
	 *            搜索项的名称
	 * @param tip
	 *            搜索提示
	 */
	void setPrimaryFilter(String name, String tip);
}
