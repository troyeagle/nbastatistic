package njuse.ffff.ui;

import java.awt.Color;

import javax.swing.JPanel;

import njuse.ffff.presenter.ControllerService;
import njuse.ffff.presenter.UIController;

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel{
	private final int playerPanel_width = 1100;
	private final int playerPanel_height = 700;
	
	private ControllerService uiController;
	private PlayerProfilePanel playerProfile;
	private PlayerDataPanel playerData;
	
	private Color background = new Color(99,43,142);
	
	public PlayerPanel(){
		this.setSize(playerPanel_width, playerPanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		uiController = UIController.getInstance();
		playerProfile = new PlayerProfilePanel();
		playerData = new PlayerDataPanel(1);//TODO
		
		this.setLayout(null);
	}
	
	public void setProfile(String name,String position,String img_portrait_URL,String[][] properties){
		playerProfile.setProfile(name, position, properties);
		playerProfile.setPhoto(img_portrait_URL);
	}
	
	public void setData(String img_action_URL,String[] properties1,String[] properties2,String[] properties3,String[] properties4
			,Object[][] values_total1,Object[][] values_total2,Object[][] values_total3,Object[][] values_total4
			,Object[][] values_average1,Object[][] values_average2,Object[][] values_average3,Object[][] values_average4){
		playerData.setAction(img_action_URL);
		playerData.setData(properties1, properties2, properties3, properties4, values_total1, values_total2
				, values_total3, values_total4, values_average1, values_average2, values_average3, values_average4);
	}
}
