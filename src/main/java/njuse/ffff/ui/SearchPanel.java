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
public class SearchPanel extends JPanel{
	private final int searchPanel_width = 1100;
	private final int searchPanel_height = 700;
	
	private ControllerService uiController;
	
	private Color background = new Color(99,43,142);
	private Color gray = new Color(127,127,127);
//	private Color light_purple = new Color(190,131,234);
//	private Color light_purple_changed = new Color(211,175,239);
	private String img_sign_URL = "picture/nba_sign.jpg";
	private String img_label_search_URL = "picture/transfer/label_search.jpg";
	private String img_label_search_changed_URL = "picture/transfer/label_search_changed.jpg";
	private String img_arrow_down_URL = "picture/arrow/arrow_down.jpg";
	private String img_arrow_down_changed_URL = "picture/arrow/arrow_down_changed.jpg";
	private String img_menu_URL = "picture/menu.jpg";
	private String img_menu_changed_URL = "picture/menu_changed.jpg";
	
	private JTextField text_searchInfo;
	private JLabel label_search;
	private JLabel label_arrow_down_left;
	private JLabel label_arrow_down_middle;
	private JLabel label_menu;
	private MenuPanel menuPanel;
	private int menuDisplay;
	
	public SearchPanel(){
		this.setSize(searchPanel_width, searchPanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		uiController = UIController.getInstance();
		menuPanel = new MenuPanel();
		menuPanel.setLocation(920, 400);
		menuDisplay = 0;
		
		//搜索框
		JLabel label_searchDialog = new JLabel();
		label_searchDialog.setOpaque(true);
		label_searchDialog.setBackground(gray);
		label_searchDialog.setBounds(145, 289, 818, 66);
		
		text_searchInfo = new JTextField("请输入球队名/球员名进行搜索");
		text_searchInfo.setBounds(6, 6, 752, 54);
		text_searchInfo.setBorder(new EmptyBorder(0,0,0,0));
		text_searchInfo.setForeground(Color.gray);
		text_searchInfo.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
		text_searchInfo.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) {
				//当输入信息为空
				if(text_searchInfo.getText().equals("")){
					text_searchInfo.setText("请输入球队名/球员名进行搜索");
				}
			}
			public void focusGained(FocusEvent arg0) {
				//消去默认初始的提示文字
				if(text_searchInfo.getText().equals("请输入球队名/球员名进行搜索")){
					text_searchInfo.setText("");
				}
			}
		});
		
		label_search = new JLabel();
		label_search.setOpaque(true);
		label_search.setBounds(764, 10, 46, 46);
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
				//获得搜索的项
				String search = text_searchInfo.getText();
				//TODO 调用查找
				
				
			}
		});
		
		label_searchDialog.setLayout(null);
		label_searchDialog.add(text_searchInfo);
		label_searchDialog.add(label_search);
		
		//向下翻页标志
		label_arrow_down_left = new JLabel();
		label_arrow_down_left.setOpaque(true);
		label_arrow_down_left.setBounds(95, 617, 40, 40);
		ImageIcon icon_arrow = new ImageIcon(img_arrow_down_URL);
		label_arrow_down_left.setIcon(icon_arrow);
		label_arrow_down_left.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon_arrow = new ImageIcon(img_arrow_down_URL);
				label_arrow_down_left.setIcon(icon_arrow);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_changed = new ImageIcon(img_arrow_down_changed_URL);
				label_arrow_down_left.setIcon(icon_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//跳转到球队信息横向比较界面
				uiController.setTeamComparePanel();
			}
		});
		
		//“球队信息横向比较”提示文字
		JLabel label_info = new JLabel("球队信息横向比较");
		label_info.setOpaque(true);
		label_info.setBackground(background);
		label_info.setForeground(Color.WHITE);
		label_info.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_info.setBounds(144, 629, 220, 24);
		
		//向下翻页标志————转换到高级搜索界面
		label_arrow_down_middle = new JLabel();
		label_arrow_down_middle.setOpaque(true);
		label_arrow_down_middle.setBounds(500, 617, 40, 40);
		icon_arrow = new ImageIcon(img_arrow_down_URL);
		label_arrow_down_middle.setIcon(icon_arrow);
		label_arrow_down_middle.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon_arrow = new ImageIcon(img_arrow_down_URL);
				label_arrow_down_middle.setIcon(icon_arrow);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_changed = new ImageIcon(img_arrow_down_changed_URL);
				label_arrow_down_middle.setIcon(icon_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到"高级搜索"界面
				
				
			}
		});
		
		//高级搜索链接
		JLabel label_detailSearch = new JLabel("高级搜索");
		label_detailSearch.setOpaque(true);
		label_detailSearch.setBackground(background);
		label_detailSearch.setForeground(Color.WHITE);
		label_detailSearch.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_detailSearch.setBounds(550, 629, 220, 24);
		
		//菜单按钮
		label_menu = new JLabel();
		label_menu.setOpaque(true);
		ImageIcon icon_menu = new ImageIcon(img_menu_URL);
		label_menu.setIcon(icon_menu);
		label_menu.setBounds(968, 601, 70, 70);
		label_menu.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon_menu = new ImageIcon(img_menu_URL);
				label_menu.setIcon(icon_menu);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_menu_changed = new ImageIcon(img_menu_changed_URL);
				label_menu.setIcon(icon_menu_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//显示菜单
				if(menuDisplay==0){
					displayMenu();
					menuDisplay = 1;
				}
				else{
					removeMenu();
					menuDisplay = 0;
				}
			}
		});
		
		this.setLayout(null);
		this.add(label_searchDialog);
		this.add(label_detailSearch);
		this.add(label_arrow_down_left);
		this.add(label_info);
		this.add(label_arrow_down_middle);
		this.add(label_detailSearch);
		this.add(label_menu);
		this.repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		//主标志
		ImageIcon icon_sign = new ImageIcon(img_sign_URL);
		Image img_sign = icon_sign.getImage();
		g.drawImage(img_sign, 370, 142, null);
	}
	
	public void displayMenu(){
		this.add(menuPanel);
		this.repaint();
	}
	
	public void removeMenu(){
		this.remove(menuPanel);
		this.repaint();
	}
}
