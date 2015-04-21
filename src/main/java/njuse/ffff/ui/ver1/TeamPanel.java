package njuse.ffff.ui.ver1;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TeamPanel extends JPanel{
	private final int teamPanel_width = 1100;
	private final int teamPanel_height = 700;
	
	private TeamProfilePanel teamProfile;
	private TeamDataPanel teamData;
	
	private Color background = new Color(99,43,142);
	
	public TeamPanel(){
		this.setSize(teamPanel_width, teamPanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		teamProfile = new TeamProfilePanel();
		teamData = new TeamDataPanel();
		
		this.setLayout(null);
		this.add(teamProfile);
	}
	
	public void setProfile(String name,ImageIcon icon,String[][] properties){
		teamProfile.setProfile(name, properties);
		teamProfile.setIcon(icon);
	}
	
	public void setData(String name,String[] properties1,String[] properties1_2,String[] properties2
			,String[] properties2_2,String[] properties3,String[] properties3_2
			,Object[][] values_total1,Object[][] values_average1,Object[][] values_ratio1
			,Object[][] values_total2,Object[][] values_average2,Object[][] values_ratio2
			,Object[][] values3_1,Object[][] values3_2){
		teamData.setName(name);
		teamData.setData(properties1, properties1_2, properties2, properties2_2, properties3, properties3_2
				, values_total1, values_average1, values_ratio1, values_total2, values_average2, values_ratio2
				, values3_1, values3_2);
	}
	
	public void displayProfile(){
		this.remove(teamData);
		this.add(teamProfile);
		this.repaint();
	}
	
	public void displayData(int number){
		this.remove(teamProfile);
		teamData.displayTables(number);
		this.add(teamData);
		this.repaint();
	}
}
