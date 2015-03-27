package njuse.ffff.ui;

import java.awt.Color;
import java.awt.Font;
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
public class PlayerProfilePanel extends JPanel{
	private final int playerProfilePanel_width = 1100;
	private final int playerProfilePanel_height = 700;
	
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
	private JLabel label_player_name;
	private JLabel label_player_position;
	private JLabel label_player_protrait;
	
	private JLabel label_height_field;
	private JLabel label_weight_field;
	private JLabel label_birth_field;
	private JLabel label_age_field;
	private JLabel label_career_field;
	private JLabel label_code_field;
	private JLabel label_graduateSchool_field;
	
	private JLabel label_player_data1;
	private JLabel label_player_data2;
	private JLabel label_player_data3;
	private JLabel label_player_data4;
	
	public PlayerProfilePanel(){
		this.setSize(playerProfilePanel_width, playerProfilePanel_height);
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
				//跳转到搜索界面
				uiController.setSearchPanel();
			}
		});
		
		//球员姓名
		label_player_name = new JLabel();
		label_player_name.setOpaque(true);
		label_player_name.setBackground(background);
		label_player_name.setForeground(Color.WHITE);
		label_player_name.setFont(new FontUIResource("DialogInput", Font.BOLD, 50));
		label_player_name.setBounds(208, 62, 500, 44);
		
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
        
        //球员位置
        label_player_position = new JLabel();
        label_player_position.setOpaque(true);
        label_player_position.setBackground(background);
        label_player_position.setForeground(Color.WHITE);
        label_player_position.setFont(new FontUIResource("DialogInput", Font.BOLD, 40));
        label_player_position.setBounds(194, 146, 94, 44);
        
        //左侧球员头像
        label_player_protrait = new JLabel();
        label_player_protrait.setOpaque(true);
        label_player_protrait.setBounds(64, 252, 214, 174);
        
        //中间球员信息
        JLabel label_height = new JLabel("身高");
        label_height.setOpaque(true);
        label_height.setBackground(background);
        label_height.setForeground(Color.WHITE);
        label_height.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_height.setBounds(418, 265, 88, 35);
        
        label_height_field = new JLabel();
        label_height_field.setOpaque(true);
        label_height_field.setBackground(background);
        label_height_field.setForeground(Color.WHITE);
        label_height_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_height_field.setBounds(605, 265, 275, 35);
        
        JLabel label_weight = new JLabel("体重");
        label_weight.setOpaque(true);
        label_weight.setBackground(background);
        label_weight.setForeground(Color.WHITE);
        label_weight.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_weight.setBounds(418, 315, 88, 35);
        
        label_weight_field = new JLabel();
        label_weight_field.setOpaque(true);
        label_weight_field.setBackground(background);
        label_weight_field.setForeground(Color.WHITE);
        label_weight_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_weight_field.setBounds(605, 315, 275, 35);
        
        JLabel label_birth = new JLabel("生日");
        label_birth.setOpaque(true);
        label_birth.setBackground(background);
        label_birth.setForeground(Color.WHITE);
        label_birth.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_birth.setBounds(418, 365, 88, 35);
        
        label_birth_field = new JLabel();
        label_birth_field.setOpaque(true);
        label_birth_field.setBackground(background);
        label_birth_field.setForeground(Color.WHITE);
        label_birth_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_birth_field.setBounds(605, 365, 275, 35);

        JLabel label_age = new JLabel("年龄");
        label_age.setOpaque(true);
        label_age.setBackground(background);
        label_age.setForeground(Color.WHITE);
        label_age.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_age.setBounds(418, 415, 88, 35);
        
        label_age_field = new JLabel();
        label_age_field.setOpaque(true);
        label_age_field.setBackground(background);
        label_age_field.setForeground(Color.WHITE);
        label_age_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_age_field.setBounds(605, 415, 275, 35);
        
        JLabel label_career = new JLabel("联赛球龄");
        label_career.setOpaque(true);
        label_career.setBackground(background);
        label_career.setForeground(Color.WHITE);
        label_career.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_career.setBounds(418, 465, 170, 35);
        
        label_career_field = new JLabel();
        label_career_field.setOpaque(true);
        label_career_field.setBackground(background);
        label_career_field.setForeground(Color.WHITE);
        label_career_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_career_field.setBounds(605, 465, 275, 35);

        JLabel label_code = new JLabel("编号");
        label_code.setOpaque(true);
        label_code.setBackground(background);
        label_code.setForeground(Color.WHITE);
        label_code.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_code.setBounds(418, 515, 88, 35);

        label_code_field = new JLabel();
        label_code_field.setOpaque(true);
        label_code_field.setBackground(background);
        label_code_field.setForeground(Color.WHITE);
        label_code_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_code_field.setBounds(605, 515, 275, 35);

        JLabel label_guaduateSchool = new JLabel("毕业学校");
        label_guaduateSchool.setOpaque(true);
        label_guaduateSchool.setBackground(background);
        label_guaduateSchool.setForeground(Color.WHITE);
        label_guaduateSchool.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_guaduateSchool.setBounds(418, 565, 170, 35);

        label_graduateSchool_field = new JLabel();
        label_graduateSchool_field.setOpaque(true);
        label_graduateSchool_field.setBackground(background);
        label_graduateSchool_field.setForeground(Color.WHITE);
        label_graduateSchool_field.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
        label_graduateSchool_field.setBounds(605, 565, 275, 35);
        
        
        //右侧数据引导框1
        label_player_data1 = new JLabel(" 各种数据1");
        label_player_data1.setOpaque(true);
        label_player_data1.setBackground(blue_light);
        label_player_data1.setForeground(Color.WHITE);
        label_player_data1.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data1.setBounds(962, 206, 138, 46);
        label_player_data1.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_player_data1.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_player_data1.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到球员数据信息界面
				uiController.changeToPlayerDataPanel(1);
			}
		});
        
        //右侧数据引导框2
        label_player_data2 = new JLabel(" 各种数据2");
        label_player_data2.setOpaque(true);
        label_player_data2.setBackground(blue_light);
        label_player_data2.setForeground(Color.WHITE);
        label_player_data2.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data2.setBounds(962, 282, 138, 46);
        label_player_data2.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_player_data2.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_player_data2.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到球员数据信息界面
				uiController.changeToPlayerDataPanel(2);
			}
		});
        
        //右侧数据引导框3
        label_player_data3 = new JLabel(" 各种数据3");
        label_player_data3.setOpaque(true);
        label_player_data3.setBackground(blue_light);
        label_player_data3.setForeground(Color.WHITE);
        label_player_data3.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data3.setBounds(962, 358, 138, 46);
        label_player_data3.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_player_data3.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_player_data3.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到球员数据信息界面
				uiController.changeToPlayerDataPanel(3);
			}
		});
        
        //右侧数据引导框4
        label_player_data4 = new JLabel(" 各种数据4");
        label_player_data4.setOpaque(true);
        label_player_data4.setBackground(blue_light);
        label_player_data4.setForeground(Color.WHITE);
        label_player_data4.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data4.setBounds(962, 434, 138, 46);
        label_player_data4.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_player_data4.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_player_data4.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到球员数据信息界面
				uiController.changeToPlayerDataPanel(4);
			}
		});
        
		
		this.setLayout(null);
		this.add(label_arrow_left);
		this.add(label_player_name);
		this.add(label_searchDialog);
		this.add(label_player_position);
		this.add(label_player_protrait);
		this.add(label_height);
		this.add(label_height_field);
		this.add(label_weight);
		this.add(label_weight_field);
		this.add(label_birth);
		this.add(label_birth_field);
		this.add(label_age);
		this.add(label_age_field);
		this.add(label_career);
		this.add(label_career_field);
		this.add(label_code);
		this.add(label_code_field);
		this.add(label_guaduateSchool);
		this.add(label_graduateSchool_field);
		this.add(label_player_data1);
		this.add(label_player_data2);
		this.add(label_player_data3);
		this.add(label_player_data4);
	}
	
	//设置属性
	public void setProfile(String name,String position,String[][] properties){
		label_player_name.setText(name);
		label_player_position.setText(position);
		for(int i=0;i<properties.length;i++){
			String property = properties[i][0];
			String value = properties[i][1];
			if(property.equals("身高")){
				label_height_field.setText(value);
			}
			else if(property.equals("生日")){
				label_birth_field.setText(value);
			}
			else if(property.equals("体重")){
				label_weight_field.setText(value);
			}
			else if(property.equals("年龄")){
				label_age_field.setText(value);
			}
			else if(property.equals("联赛球龄")){
				label_career_field.setText(value);
			}
			else if(property.equals("编号")){
				label_code_field.setText(value);
			}
			else if(property.equals("毕业学校")){
				label_graduateSchool_field.setText(value);
			}
		}
		this.repaint();
	}
	
	public void setPhoto(ImageIcon img){
		label_player_protrait.setIcon(img);
		this.repaint();
	}
}
