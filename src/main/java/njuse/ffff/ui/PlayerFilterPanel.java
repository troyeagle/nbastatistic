package njuse.ffff.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import njuse.ffff.presenter.ControllerService;
import njuse.ffff.presenter.UIController;

@SuppressWarnings("serial")
public class PlayerFilterPanel extends JPanel{
	private final int teamComparePanel_width = 1100;
	private final int teamComparePanel_height = 700;
	
	private ControllerService uiController;
	
	private SelectPanel p;

	//颜色及图片引用
	private Color background = new Color(99,43,142);
	private Color blue_light_changed = new Color(93,169,238);
	private String img_arrow_up_URL = "picture/arrow/arrow_up.jpg";
	private String img_arrow_up_changed_URL = "picture/arrow/arrow_up_changed.jpg";
	private String img_arrow_left_URL = "picture/arrow/arrow_left.jpg";
	private String img_arrow_left_changed_URL = "picture/arrow/arrow_left_changed.jpg";
	private String img_searchIcon_URL = "picture/searchIcon.jpg";
	private String img_searchIcon_changed_URL = "picture/searchIcon_changed.jpg";
	
	private JLabel label_arrow_left;
	private JLabel label_arrow_up;
	private JLabel label_searchIcon;
	private JLabel label_add_item;
	private JComboBox<String> filter_position;
	private JComboBox<String> filter_alliance;
	private JLabel label_sort_condition;
	
	//表格属性
	private JTable table_filter;
	private String[] table_filter_header;
	private Object[][] table_filter_body;
	private DefaultTableModel tableModel_filter_total;
	
	public PlayerFilterPanel(){
		this.setSize(teamComparePanel_width, teamComparePanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		uiController = UIController.getInstance();
		p = new SelectPanel();
		p.setLocation(264, 168);
		
		//球员筛选标题
		JLabel label_filter_title = new JLabel("球员筛选");
		label_filter_title.setOpaque(true);
		label_filter_title.setBackground(background);
		label_filter_title.setForeground(Color.WHITE);
		label_filter_title.setFont(new FontUIResource("DialogInput", Font.BOLD, 40));
		label_filter_title.setBounds(44, 35, 188, 42);
		
		//向左翻页标志
		label_arrow_left = new JLabel();
		label_arrow_left.setOpaque(true);
		label_arrow_left.setBounds(1026, 62, 40, 40);
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
				ImageIcon icon_left_changed = new ImageIcon(img_arrow_left_changed_URL);
				label_arrow_left.setIcon(icon_left_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到"球员信息横向比较"界面
				uiController.setTeamComparePanel();
			}
		});
		
		//向左翻页提示
		JLabel label_left_info = new JLabel("球员信息横向比较");
		label_left_info.setOpaque(true);
		label_left_info.setBackground(background);
		label_left_info.setForeground(Color.WHITE);
		label_left_info.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_left_info.setBounds(850, 68, 176, 28);
		
		//向上翻页提示
		JLabel label_up_info = new JLabel("搜索界面");
		label_up_info.setOpaque(true);
		label_up_info.setBackground(background);
		label_up_info.setForeground(Color.WHITE);
		label_up_info.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_up_info.setBounds(935, 20, 100, 28);
		
		//上指向箭头，返回搜索界面
		label_arrow_up = new JLabel();
		label_arrow_up.setOpaque(true);
		label_arrow_up.setBounds(1026, 14, 40, 40);
		ImageIcon icon_up = new ImageIcon(img_arrow_up_URL);
		label_arrow_up.setIcon(icon_up);
		label_arrow_up.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon_up = new ImageIcon(img_arrow_up_URL);
				label_arrow_up.setIcon(icon_up);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_up_changed = new ImageIcon(img_arrow_up_changed_URL);
				label_arrow_up.setIcon(icon_up_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到搜索界面
				uiController.setSearchPanel();
			}
		});
		
		//搜索标志
		label_searchIcon = new JLabel();
		label_searchIcon.setOpaque(true);
		ImageIcon icon_search = new ImageIcon(img_searchIcon_URL);
		label_searchIcon.setIcon(icon_search);
		label_searchIcon.setBounds(64, 90, 80, 80);
		label_searchIcon.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon_search = new ImageIcon(img_searchIcon_URL);
				label_searchIcon.setIcon(icon_search);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_search_changed = new ImageIcon(img_searchIcon_changed_URL);
				label_searchIcon.setIcon(icon_search_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 获得搜索条件，开始搜索
				String position = filter_position.getSelectedItem().toString();
				String alliance = filter_alliance.getSelectedItem().toString();
				String sortCondition = label_sort_condition.getText();
				
			}
		});
		
		//小标题——首选项
		JLabel label_preference = new JLabel("首选项");
		label_preference.setOpaque(true);
		label_preference.setBackground(background);
		label_preference.setForeground(Color.WHITE);
		label_preference.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_preference.setBounds(164, 99, 90, 30);
		
		//球员位置
		String[] list_position = {"位置","前锋","中锋","后卫"};
		filter_position = new JComboBox<String>(list_position);
		filter_position.setOpaque(true);
		filter_position.setBackground(background);
		filter_position.setForeground(Color.CYAN);
		filter_position.setBounds(270, 99, 60, 28);
		
		//球员联盟
		String[] list_alliance = {"联盟","东部","西部"};
		filter_alliance = new JComboBox<String>(list_alliance);
		filter_alliance.setOpaque(true);
		filter_alliance.setBackground(background);
		filter_alliance.setForeground(Color.CYAN);
		filter_alliance.setBounds(370, 99, 60, 28);
		
		//小标题——排序项
		JLabel label_filter_sort = new JLabel("排序项");
		label_filter_sort.setOpaque(true);
		label_filter_sort.setBackground(background);
		label_filter_sort.setForeground(Color.WHITE);
		label_filter_sort.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_filter_sort.setBounds(164, 132, 90, 30);

		//选择的排序项
		label_sort_condition = new JLabel();
		label_sort_condition.setOpaque(true);
		label_sort_condition.setBackground(background);
		label_sort_condition.setForeground(Color.WHITE);
		label_sort_condition.setBounds(310, 132, 800, 30);
		
		//添加项
		label_add_item = new JLabel("+");
		label_add_item.setOpaque(true);
		label_add_item.setBackground(background);
		label_add_item.setForeground(Color.WHITE);
		label_add_item.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
		label_add_item.setBounds(270, 135, 20, 25);
		label_add_item.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_add_item.setBackground(background);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_add_item.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				if(label_add_item.getText().equals("+")){
					displaySelectPanel();
					label_add_item.setText("-");
				}
				else if(label_add_item.getText().equals("-")){
					removeSelectPanel();
					label_add_item.setText("+");
					ArrayList<String> l =  p.getBoxSelected();
					StringBuffer buffer = new StringBuffer();
					for(String str:l){
						buffer.append(str);
						buffer.append("  ");
					}
					label_sort_condition.setText(buffer.toString());
					repaint();
				}
			}
		});
		
		
		this.setLayout(null);
		this.add(label_filter_title);
		this.add(label_arrow_up);
		this.add(label_up_info);
		this.add(label_arrow_left);
		this.add(label_left_info);
		this.add(label_searchIcon);
		this.add(label_preference);
		this.add(filter_position);
		this.add(filter_alliance);
		this.add(label_filter_sort);
		this.add(label_add_item);
		this.add(label_sort_condition);
	}
	
	public void displaySelectPanel(){
		this.add(p);
		this.repaint();
	}
	
	public void removeSelectPanel(){
		this.remove(p);
		this.repaint();
	}
}
