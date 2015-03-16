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

	public void setSearchPanel() {
		// TODO 自动生成的方法存根
		SearchPanel searchPanel = new SearchPanel();
		frame.changePanel(searchPanel);
	}
	
	public void setTeamComparePanel() {
		// TODO 自动生成的方法存根
		TeamComparePanel teamComparePanell = new TeamComparePanel();
		frame.changePanel(teamComparePanell);
	}
	
	public void setPlayerProfilePanel() {
		PlayerProfilePanel playerPanel = new PlayerProfilePanel();
		frame.changePanel(playerPanel);
	}

	public void setPlayerDataPanel(int number) {
		PlayerDataPanel playerDataPanel = new PlayerDataPanel(number);
		frame.changePanel(playerDataPanel);
	}

	public void setPlayerComparePanel() {
		PlayerComparePanel playerComparePanel = new PlayerComparePanel();
		frame.changePanel(playerComparePanel);
	}

	public void setTeamProfilePanel() {
		TeamProfilePanel teamProfilePanel = new TeamProfilePanel();
		frame.changePanel(teamProfilePanel);
	}

	public void setTeamDataPanel() {
		// TODO 自动生成的方法存根
		
	}
}
