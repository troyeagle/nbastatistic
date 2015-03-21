package njuse.ffff.presenter;

import njuse.ffff.ui.PlayerFilterPanel;

public interface ControllerService {
	public void initSystem();
	//设置搜索界面
	public void setSearchPanel();
	//设置球队信息横向比较界面
	public void setTeamComparePanel();
	//设置球员信息横向比较界面
	public void setPlayerComparePanel();
	//设置界面为球员筛选界面
	public void setPlayerFilterPanel();
	//传送球员筛选条件
	public void setPlayerFilterResult(PlayerFilterPanel panel,String position,String league,String sort);
	//设置界面为球员简介界面
	public void setPlayerProfilePanel(String playerName);
	//设置界面为球队简介界面
	public void setTeamProfilePanel(String teamName);
	//设置界面为球队数据界面
	public void setTeamDataPanel();
}
