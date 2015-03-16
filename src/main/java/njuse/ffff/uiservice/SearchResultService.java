package njuse.ffff.uiservice;

/**
 * 提供搜索界面数据的访问和修改方法
 * 
 * @author Li
 *
 */
public interface SearchResultService {

	/**
	 * 设置搜索结果
	 * 
	 * @param teamsProperties
	 * @param teamsValues
	 * @param playersProperties
	 * @param playerValues
	 */
	void setSearchResult(String[] teamsProperties, String[][] teamsValues,
			String[] playersProperties, String[][] playerValues);
}
