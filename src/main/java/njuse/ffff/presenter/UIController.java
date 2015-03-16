package njuse.ffff.presenter;

import njuse.ffff.ui.MainFrame;
import njuse.ffff.ui.PlayerComparePanel;
import njuse.ffff.ui.PlayerDataPanel;
import njuse.ffff.ui.PlayerProfilePanel;
import njuse.ffff.ui.SearchPanel;
import njuse.ffff.ui.TeamComparePanel;
import njuse.ffff.ui.TeamProfilePanel;

public class UIController implements ControllerService{
	private static MainFrame frame = null;
	private static UIController controller = null;
	private UIController(){
	}
	
	public static UIController getInstance(){
		if(controller==null){
			controller = new UIController();
		}
		return controller;
	}
	
	public static void createFrame(){
		if(frame==null){
			frame = new MainFrame();
		}
		frame.setVisible(true);
	}
	
	/**
	 * 设置查询界面
	 */
	public void setSearchPanel() {
		SearchPanel searchPanel = new SearchPanel();
		frame.changePanel(searchPanel);
	}
	
	/**
	 * 设置球队信息横向比较界面
	 */
	public void setTeamComparePanel() {
		// TODO 获取所有球队信息
		
		
		TeamComparePanel teamComparePanell = new TeamComparePanel();
		frame.changePanel(teamComparePanell);
	}
	
	/**
	 * 设置球员信息一览界面
	 */
	public void setPlayerComparePanel() {
		//TODO 获取所有球员信息
		
		
		PlayerComparePanel playerComparePanel = new PlayerComparePanel();
		frame.changePanel(playerComparePanel);
	}

	/**
	 * 设置球员简介界面
	 */
	public void setPlayerProfilePanel(String playerName) {
		//TODO 获取指定的球员信息
		
		
		PlayerProfilePanel playerPanel = new PlayerProfilePanel();
		frame.changePanel(playerPanel);
	}

	/**
	 * 设置球员数据信息界面
	 */
	public void setPlayerDataPanel(int number) {
		PlayerDataPanel playerDataPanel = new PlayerDataPanel(number);
		frame.changePanel(playerDataPanel);
	}

	/**
	 * 设置球队简介界面
	 */
	public void setTeamProfilePanel(String teamName) {
		//TODO 获取指定球队的信息
		
		
		TeamProfilePanel teamProfilePanel = new TeamProfilePanel();
		frame.changePanel(teamProfilePanel);
	}

	/**
	 * 设置球队数据信息界面
	 */
	public void setTeamDataPanel() {
		// TODO 自动生成的方法存根
		
	}
}
