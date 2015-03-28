package njuse.ffff.presenter;

import java.util.ArrayList;

import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.ui.PlayerFilterPanel;

public interface ControllerService {
	public void initSystem();
	//设置搜索界面
	public void setSearchPanel();
	//查找搜索的球队或者球员
	public void search(String input);
	//设置球队信息横向比较界面
	public void setTeamComparePanel();
	//设置球员信息横向比较界面
	public void setPlayerComparePanel();
	//按照选择的表头排序
	public void resetPlayerList(ArrayList<PlayerInAverage> players,int column);
	//设置界面为球员筛选界面
	public void setPlayerFilterPanel();
	//传送球员筛选条件
	public void setPlayerFilterResult(PlayerFilterPanel panel,String position,String league,String sort);
	//设置界面为球员简介界面
	public void setPlayerProfilePanel(String playerName);
	//球员简介界面切换为球员数据界面
	public void changeToPlayerDataPanel(int number);
	//球员数据界面切换为球员简介界面
	public void changeToPlayerProfilePanel();
	//设置界面为球队简介界面
	public void setTeamProfilePanel(String teamName);
	//球队简介界面切换为球队数据界面
	public void changeToTeamDataPanel(int number);
	//球队数据界面切换为球队简介界面
	public void changeToTeamProfilePanel();
}
