package njuse.ffff.presenter;

public interface ControllerService {
	//设置搜索界面
	public void setSearchPanel();
	//设置球队信息横向比较界面
	public void setTeamComparePanel();
	//设置球员信息横向比较界面
	public void setPlayerComparePanel();
	//设置界面为球员简介界面
	public void setPlayerProfilePanel();
	//设置界面为球员数据界面
	public void setPlayerDataPanel(int number);
	//设置界面为球队简介界面
	public void setTeamProfilePanel();
	//设置界面未球队数据界面
	public void setTeamDataPanel();
}
