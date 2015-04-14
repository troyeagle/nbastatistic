package njuse.ffff.presenterService;

import njuse.ffff.uiservice.SearchResultService;

public interface SearchService {
	//设置查询界面
	public void setSearchPanel();
	//查找搜索的球队或者球员
	public void search(SearchResultService searchResultPanel,String input);
	//设置高级所示结果界面
	public void setAdvancedSearchPanel(SearchService searchPanel,SearchResultService searchResultPanel);
}
