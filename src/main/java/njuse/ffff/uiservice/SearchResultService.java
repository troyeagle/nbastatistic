package njuse.ffff.uiservice;

import njuse.ffff.util.BasicPlayerInfo;

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
	 * @param teamNames
	 * @param playerNames
	 */
	void setSearchResult(String[] teamNames, BasicPlayerInfo[] playerNames);
}
