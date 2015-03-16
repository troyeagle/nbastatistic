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
public class PlayerDataPanel extends JPanel{
	private final int playerDataPanel_width = 1100;
	private final int playerDataPanel_height = 700;
	
	private ControllerService uiController;
	
	private Color background = new Color(99,43,142);
	private Color blue_light = new Color(46,117,182);
	private Color blue_light_changed = new Color(93,169,238);
	private String img_arrow_left_URL = "picture/arrow/arrow_left.jpg";
	private String img_arrow_left_changed_URL = "picture/arrow/arrow_left_changed.jpg";
	private String img_label_search_URL = "picture/transfer/label_search_small.jpg";
	private String img_label_search_changed_URL = "picture/transfer/label_search_changed_small.jpg";
	private String img_data_arrow_URL = "picture/arrow/data_arrow.jpg";
	
	private JLabel label_arrow_left;
	private JTextField text_searchInfo;
	private JLabel label_search;
	private JLabel label_player_data1;
	private JLabel label_player_data2;
	private JLabel label_player_data3;
	private JLabel label_player_data4;
	private JLabel label_player_return;
	
	public PlayerDataPanel(int number){
		this.setSize(playerDataPanel_width, playerDataPanel_height);
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
				// TODO 跳转到“球员一览”界面
			}
		});
		
		//球员姓名
		JLabel label_player_name = new JLabel("Aaron Brooks");
		label_player_name.setOpaque(true);
		label_player_name.setBackground(background);
		label_player_name.setForeground(Color.WHITE);
		label_player_name.setFont(new FontUIResource("DialogInput", Font.BOLD, 50));
		label_player_name.setBounds(308, 62, 400, 44);
		
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
        
        //左侧数据引导框1
        label_player_data1 = new JLabel(" 各种数据1");
        label_player_data1.setOpaque(true);
        label_player_data1.setBackground(blue_light);
        label_player_data1.setForeground(Color.WHITE);
        label_player_data1.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data1.setBounds(0, 206, 138, 46);
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
				// TODO 跳转到高级搜索界面
			}
		});
        
        //左侧数据引导框2
        label_player_data2 = new JLabel(" 各种数据2");
        label_player_data2.setOpaque(true);
        label_player_data2.setBackground(blue_light);
        label_player_data2.setForeground(Color.WHITE);
        label_player_data2.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data2.setBounds(0, 282, 138, 46);
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
				// TODO 跳转到高级搜索界面
			}
		});
        
        //左侧数据引导框3
        label_player_data3 = new JLabel(" 各种数据3");
        label_player_data3.setOpaque(true);
        label_player_data3.setBackground(blue_light);
        label_player_data3.setForeground(Color.WHITE);
        label_player_data3.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data3.setBounds(0, 358, 138, 46);
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
				// TODO 跳转到高级搜索界面
			}
		});
        
        //左侧数据引导框4
        label_player_data4 = new JLabel(" 各种数据4");
        label_player_data4.setOpaque(true);
        label_player_data4.setBackground(blue_light);
        label_player_data4.setForeground(Color.WHITE);
        label_player_data4.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data4.setBounds(0, 434, 138, 46);
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
				// TODO 跳转到高级搜索界面
			}
		});
        
        //左侧返回球员简介界面框
        label_player_return = new JLabel(" 返回");
        label_player_return.setOpaque(true);
        label_player_return.setBackground(blue_light);
        label_player_return.setForeground(Color.WHITE);
        label_player_return.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_return.setBounds(0, 510, 90, 46);
        label_player_return.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_player_return.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_player_return.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到球员介绍界面
				uiController.setPlayerProfilePanel();
			}
		});
        
        //数据显示指向箭头
        JLabel label_data_arrow = new JLabel();
        label_data_arrow.setOpaque(true);
        label_data_arrow.setBounds(138, 76*number+130, 23, 46);
        ImageIcon icon_data_arrow = new ImageIcon(img_data_arrow_URL);
        label_data_arrow.setIcon(icon_data_arrow);
        
        //表格显示数据
        
		
		this.setLayout(null);
		this.add(label_arrow_left);
		this.add(label_player_name);
		this.add(label_searchDialog);
		this.add(label_player_data1);
		this.add(label_player_data2);
		this.add(label_player_data3);
		this.add(label_player_data4);
		this.add(label_player_return);
		this.add(label_data_arrow);
	}
}
