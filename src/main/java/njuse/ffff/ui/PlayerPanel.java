package njuse.ffff.ui;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel{
	private final int playerPanel_width = 1100;
	private final int playerPanel_height = 700;
	
	private PlayerProfilePanel playerProfile;
	private PlayerDataPanel playerData;
	
	private Color background = new Color(99,43,142);
	
	public PlayerPanel(){
		this.setSize(playerPanel_width, playerPanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		playerProfile = new PlayerProfilePanel();
		playerData = new PlayerDataPanel();
		
		this.setLayout(null);
		this.add(playerProfile);
	}
	
	public void setProfile(String name,String position,ImageIcon img_portrait,String[][] properties){
		playerProfile.setProfile(name, position, properties);
		playerProfile.setPhoto(img_portrait);
	}
	
	public void setData(String name,Image img_action
			,String[] properties1,String[] properties1_ratio
			,String[] properties2,String[] properties2_ratio
			,String[] properties3_total,String[] properties3_average
			,Object[][] values_total1,Object[][] values_average1,Object[][] values_ratio1
			,Object[][] values_total2,Object[][] values_average2,Object[][] values_ratio2
			,Object[][] values_total3,Object[][] values_average3){
		playerData.setAction(name,img_action);
		playerData.setData(properties1,properties1_ratio, properties2
				, properties2_ratio, properties3_total, properties3_average
				, values_total1, values_average1, values_ratio1
				, values_total2, values_average2, values_ratio2
				, values_total3, values_average3);
	}
	
	public void displayProfile(){
		this.remove(playerData);
		this.add(playerProfile);
		this.repaint();
	}
	
	public void displayData(int number){
		this.remove(playerProfile);
		playerData.displayTables(number);
		this.add(playerData);
		this.repaint();
	}
}
