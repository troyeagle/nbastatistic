package njuse.ffff.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import njuse.ffff.presenter.ControllerService;
import njuse.ffff.presenter.UIController;

@SuppressWarnings("serial")
public class TeamProfilePanel extends JPanel{
	private final int teamProfilePanel_width = 1100;
	private final int teamProfilePanel_height = 700;
	
	private ControllerService uiController;
	
	private Color background = new Color(99,43,142);
	private Color blue_light = new Color(46,117,182);
	private Color blue_light_changed = new Color(93,169,238);
	private String img_arrow_left_URL = "picture/arrow/arrow_left.jpg";
	private String img_arrow_left_changed_URL = "picture/arrow/arrow_left_changed.jpg";
	private String img_label_search_URL = "picture/transfer/label_search_small.jpg";
	private String img_label_search_changed_URL = "picture/transfer/label_search_changed_small.jpg";
	
	private JLabel label_arrow_left;
	private JTextField text_searchInfo;
	private JLabel label_search;
	private JLabel label_team_name;
	private JLabel label_team_data1;
	private JLabel label_team_data2;
	private JLabel label_team_data3;
	
	private JLabel label_team_protrait;
	private ImageIcon icon_protrait;
	private JLabel label_abbr_field;
	private JLabel label_state_field;
	private JLabel label_league_field;
	private JLabel label_subLeague_field;
	private JLabel label_homeCourt_field;
	private JLabel label_timeOfFoundation_field;
	
	public TeamProfilePanel(){
		this.setSize(teamProfilePanel_width, teamProfilePanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		uiController = UIController.getInstance();
		
		//左指向箭头，回退
		label_arrow_left = new JLabel();
		label_arrow_left.setOpaque(true);
		label_arrow_left.setBounds(44, 44, 40, 40);
		ImageIcon icon_left = new ImageIcon(img_arrow_left_URL);
		label_arrow_left.setIcon(icon_left);
		label_arrow_left.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon_left = new ImageIcon(img_arrow_left_URL);
				label_arrow_left.setIcon(icon_left);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_changed = new ImageIcon(img_arrow_left_changed_URL);
				label_arrow_left.setIcon(icon_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//回退到上个界面
				uiController.backToLastPanel();
			}
		});
		
		//球队名称
		label_team_name = new JLabel();
		label_team_name.setOpaque(true);
		label_team_name.setBackground(background);
		label_team_name.setForeground(Color.WHITE);
		label_team_name.setFont(new FontUIResource("DialogInput", Font.BOLD, 50));
		label_team_name.setBounds(308, 62, 400, 44);
		
		//便捷搜索框
		JLabel label_searchDialog = new JLabel();
		label_searchDialog.setOpaque(true);
		label_searchDialog.setBackground(Color.gray);
		label_searchDialog.setBounds(850, 40, 210, 32);
		
		text_searchInfo = new JTextField("输入球队名/球员名");
		text_searchInfo.setBounds(2, 2, 176, 28);
		text_searchInfo.setBorder(new EmptyBorder(0,0,0,0));
		text_searchInfo.setForeground(Color.gray);
		text_searchInfo.setFont(new FontUIResource("DialogInput", Font.BOLD, 18));
		text_searchInfo.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) {
				//当输入信息为空
				if(text_searchInfo.getText().equals("")){
					text_searchInfo.setText("输入球队名/球员名");
				}
			}
			public void focusGained(FocusEvent arg0) {
				//消去默认初始的提示文字
				if(text_searchInfo.getText().equals("输入球队名/球员名")){
					text_searchInfo.setText("");
				}
			}
		});
		
		label_search = new JLabel();
		label_search.setOpaque(true);
		label_search.setBounds(180, 4, 24, 24);
		ImageIcon image = new ImageIcon(img_label_search_URL);
        label_search.setIcon(image);
        label_search.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon image = new ImageIcon(img_label_search_URL);
				label_search.setIcon(image);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon image_changed = new ImageIcon(img_label_search_changed_URL);
				label_search.setIcon(image_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到搜索结果界面
			}
		});
        
        label_searchDialog.setLayout(null);
        label_searchDialog.add(text_searchInfo);
        label_searchDialog.add(label_search);
        
        //左侧球队标志
        label_team_protrait = new JLabel();
        label_team_protrait.setOpaque(true);
        label_team_protrait.setBounds(64, 252, 214, 174);
        
        //中间球队信息
        JLabel label_abbr = new JLabel("简称");
        label_abbr.setOpaque(true);
        label_abbr.setBackground(background);
        label_abbr.setForeground(Color.WHITE);
        label_abbr.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_abbr.setBounds(418, 265, 88, 35);
        
        label_abbr_field = new JLabel();
        label_abbr_field.setOpaque(true);
        label_abbr_field.setBackground(background);
        label_abbr_field.setForeground(Color.WHITE);
        label_abbr_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_abbr_field.setBounds(605, 265, 275, 35);
        
        JLabel label_state = new JLabel("所在地");
        label_state.setOpaque(true);
        label_state.setBackground(background);
        label_state.setForeground(Color.WHITE);
        label_state.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_state.setBounds(418, 315, 170, 35);
        
        label_state_field = new JLabel();
        label_state_field.setOpaque(true);
        label_state_field.setBackground(background);
        label_state_field.setForeground(Color.WHITE);
        label_state_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_state_field.setBounds(605, 315, 275, 35);
        
        JLabel label_league = new JLabel("联盟");
        label_league.setOpaque(true);
        label_league.setBackground(background);
        label_league.setForeground(Color.WHITE);
        label_league.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_league.setBounds(418, 365, 88, 35);
        
        label_league_field = new JLabel();
        label_league_field.setOpaque(true);
        label_league_field.setBackground(background);
        label_league_field.setForeground(Color.WHITE);
        label_league_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_league_field.setBounds(605, 365, 275, 35);

        JLabel label_subLeague = new JLabel("次级联盟");
        label_subLeague.setOpaque(true);
        label_subLeague.setBackground(background);
        label_subLeague.setForeground(Color.WHITE);
        label_subLeague.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_subLeague.setBounds(418, 415, 170, 35);
        
        label_subLeague_field = new JLabel();
        label_subLeague_field.setOpaque(true);
        label_subLeague_field.setBackground(background);
        label_subLeague_field.setForeground(Color.WHITE);
        label_subLeague_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_subLeague_field.setBounds(605, 415, 275, 35);
        
        JLabel label_homeCourt = new JLabel("主场");
        label_homeCourt.setOpaque(true);
        label_homeCourt.setBackground(background);
        label_homeCourt.setForeground(Color.WHITE);
        label_homeCourt.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_homeCourt.setBounds(418, 465, 88, 35);
        
        label_homeCourt_field = new JLabel();
        label_homeCourt_field.setOpaque(true);
        label_homeCourt_field.setBackground(background);
        label_homeCourt_field.setForeground(Color.WHITE);
        label_homeCourt_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_homeCourt_field.setBounds(605, 465, 475, 35);

        JLabel label_timeOfFoundation = new JLabel("成立时间");
        label_timeOfFoundation.setOpaque(true);
        label_timeOfFoundation.setBackground(background);
        label_timeOfFoundation.setForeground(Color.WHITE);
        label_timeOfFoundation.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_timeOfFoundation.setBounds(418, 515, 170, 35);

        label_timeOfFoundation_field = new JLabel();
        label_timeOfFoundation_field.setOpaque(true);
        label_timeOfFoundation_field.setBackground(background);
        label_timeOfFoundation_field.setForeground(Color.WHITE);
        label_timeOfFoundation_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_timeOfFoundation_field.setBounds(605, 515, 275, 35);
        
        //右侧数据引导框1
        label_team_data1 = new JLabel(" 投篮数据");
        label_team_data1.setOpaque(true);
        label_team_data1.setBackground(blue_light);
        label_team_data1.setForeground(Color.WHITE);
        label_team_data1.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_team_data1.setBounds(962, 206, 138, 46);
        label_team_data1.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_team_data1.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_team_data1.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				uiController.changeToTeamDataPanel(1);
			}
		});
        
        //右侧数据引导框2
        label_team_data2 = new JLabel(" 各项数据");
        label_team_data2.setOpaque(true);
        label_team_data2.setBackground(blue_light);
        label_team_data2.setForeground(Color.WHITE);
        label_team_data2.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_team_data2.setBounds(962, 282, 138, 46);
        label_team_data2.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_team_data2.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_team_data2.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				uiController.changeToTeamDataPanel(2);
			}
		});
        
        //右侧数据引导框3
        label_team_data3 = new JLabel(" 其他数据");
        label_team_data3.setOpaque(true);
        label_team_data3.setBackground(blue_light);
        label_team_data3.setForeground(Color.WHITE);
        label_team_data3.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_team_data3.setBounds(962, 358, 138, 46);
        label_team_data3.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_team_data3.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_team_data3.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				uiController.changeToTeamDataPanel(3);
			}
		});
        
		
		this.setLayout(null);
		this.add(label_arrow_left);
		this.add(label_team_name);
		this.add(label_searchDialog);
//		this.add(label_team_protrait);
		this.add(label_abbr);
		this.add(label_abbr_field);
		this.add(label_state);
		this.add(label_state_field);
		this.add(label_league);
		this.add(label_league_field);
		this.add(label_subLeague);
		this.add(label_subLeague_field);
		this.add(label_homeCourt);
		this.add(label_homeCourt_field);
		this.add(label_timeOfFoundation);
		this.add(label_timeOfFoundation_field);
		this.add(label_team_data1);
		this.add(label_team_data2);
		this.add(label_team_data3);
	}
	
	public void setProfile(String name,String[][] properties){
		label_team_name.setText(name);
		for(int i=0;i<properties.length;i++){
			String property = properties[i][0];
			String value = properties[i][1];
			if(property.equals("简称")){
				label_abbr_field.setText(value);
			}
			else if(property.equals("所在地")){
				label_state_field.setText(value);
			}
			else if(property.equals("联盟")){
				label_league_field.setText(value);
			}
			else if(property.equals("次级联盟")){
				label_subLeague_field.setText(value);
			}
			else if(property.equals("主场")){
				label_homeCourt_field.setText(value);
			}
			else if(property.equals("成立时间")){
				label_timeOfFoundation_field.setText(value);
			}
		}
		this.repaint();
	}
	
	public void setIcon(ImageIcon icon){
		icon_protrait = icon;
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Image img = icon_protrait.getImage();
		g.drawImage(img, 64, 232, 250, 250, null);
	}
}
