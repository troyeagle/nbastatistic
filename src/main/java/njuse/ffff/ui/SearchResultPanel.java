package njuse.ffff.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import njuse.ffff.presenter.SearchController;
import njuse.ffff.presenter.playerController.PlayerInfoController;
import njuse.ffff.presenter.teamController.TeamInfoController;
import njuse.ffff.presenterService.SearchService;
import njuse.ffff.presenterService.playerService.PlayerInfoService;
import njuse.ffff.presenterService.teamService.TeamInfoService;

@SuppressWarnings("serial")
public class SearchResultPanel extends JPanel{
	private final int searchResultPanel_width = 1100;
	private final int searchResultPanel_height = 700;
	
//	private ControllerService uiController;
	private PlayerInfoService playerInfoController;
	private TeamInfoService teamInfoController;
	private SearchService searchController;
	private JLabel label_menu;
	private MenuPanel menuPanel;
	private int menuDisplay;
	
	private Color background = new Color(99,43,142);

	private String img_menu_URL = "picture/menu.jpg";
	private String img_menu_changed_URL = "picture/menu_changed.jpg";
	private String img_arrow_left_URL = "picture/arrow/arrow_left.jpg";
	private String img_arrow_left_changed_URL = "picture/arrow/arrow_left_changed.jpg";
	
	private DefaultTableModel tableModel_searchResult;
	private JTable table_searchResult;
	private JScrollPane scrollPane_searchResult;
	
	private JLabel label_arrow_left;
	
	public SearchResultPanel(){
		this.setSize(searchResultPanel_width, searchResultPanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
//		uiController = UIController.getInstance();
		playerInfoController = PlayerInfoController.getInstance();
		teamInfoController = TeamInfoController.getInstance();
		searchController = SearchController.getInstance();
		menuPanel = new MenuPanel();
		menuPanel.setLocation(930, 110);
		menuDisplay = 0;
		
		//向左翻页标志
		label_arrow_left = new JLabel();
		label_arrow_left.setOpaque(true);
		label_arrow_left.setBounds(946, 62, 40, 40);
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
				//跳转到"搜索界面"
//				uiController.setSearchPanel();
				searchController.setSearchPanel();
			}
		});
		
		//向左翻页提示
		JLabel label_left_info = new JLabel("搜索");
		label_left_info.setOpaque(true);
		label_left_info.setBackground(background);
		label_left_info.setForeground(Color.WHITE);
		label_left_info.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_left_info.setBounds(890, 68, 76, 28);
		
		//上方标题栏
		JLabel label_title = new JLabel("搜索结果");
		label_title.setOpaque(true);
		label_title.setBackground(getBackground());
		label_title.setForeground(Color.WHITE);
		label_title.setFont(new FontUIResource("DialogInput", Font.BOLD, 40));
		label_title.setBounds(176, 42, 400, 44);
		
		//菜单按钮
		label_menu = new JLabel();
		label_menu.setOpaque(true);
		ImageIcon icon_menu = new ImageIcon(img_menu_URL);
		label_menu.setIcon(icon_menu);
		label_menu.setBounds(996, 30, 70, 70);
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
		
		//显示结果
		
		
		this.setLayout(null);
		this.add(label_title);
		this.add(label_arrow_left);
		this.add(label_left_info);
		this.add(label_menu);
	}
	
	public void setSearchResult(String[] properties,Object[][] values){
		tableModel_searchResult = new DefaultTableModel(values,properties){
			public boolean isCellEditable(int row, int column)
            {
                return false;
            }
		};
		table_searchResult = new JTable(tableModel_searchResult){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c = super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
//				if (row == r && column == c) {
//					c.setBackground(Color.gray);
//				}
				return c;
			}
		};
		table_searchResult.setOpaque(false);
		table_searchResult.setForeground(Color.WHITE);
		table_searchResult.setFont(new FontUIResource("DialogInput", Font.PLAIN, 12));
		table_searchResult.setSelectionForeground(Color.CYAN);
		
		//第1列
		TableColumn firstColumn = table_searchResult.getColumnModel().getColumn(0);
		firstColumn.setPreferredWidth(50);
		firstColumn.setMaxWidth(50);
		firstColumn.setMinWidth(50);
		//第3列
		TableColumn secondColumn = table_searchResult.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(100);
		secondColumn.setMaxWidth(100);
		secondColumn.setMinWidth(100);
		//第3列
		TableColumn thirdColumn = table_searchResult.getColumnModel().getColumn(2);
		thirdColumn.setPreferredWidth(250);
		thirdColumn.setMaxWidth(250);
		thirdColumn.setMinWidth(250);
		//表格显示
		table_searchResult.getTableHeader().setBackground(background);
		table_searchResult.getTableHeader().setForeground(Color.WHITE);
		//监听
		table_searchResult.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			public void mouseEntered(MouseEvent e) {
				//鼠标进入第三列的某一个单元格，该单元格高亮
				
			}
			public void mouseClicked(MouseEvent e) {
				// 点击某个球员或者球队，跳转到该球员或球队界面
				int column = table_searchResult.getSelectedColumn();
				if(column==2){
					int row = table_searchResult.getSelectedRow();
					String type = (String)table_searchResult.getValueAt(row, column-1);
					String content = (String)table_searchResult.getValueAt(row, column);
					if(type.equals("球队")){
//						uiController.setTeamProfilePanel(content);
						teamInfoController.setTeamProfilePanel(content);
					}
					else if(type.equals("球员")){
//						uiController.setPlayerProfilePanel(content);
						playerInfoController.setPlayerProfilePanel(content);
					}
				}
			}
		});
		
		scrollPane_searchResult = new JScrollPane(table_searchResult);
		scrollPane_searchResult.setOpaque(false);
		scrollPane_searchResult.getViewport().setOpaque(false);
		scrollPane_searchResult.setBounds(100, 160, 400, 515);
		
		this.add(scrollPane_searchResult);
	}
	
	/**
	 * 显示菜单
	 */
	public void displayMenu(){
		this.add(menuPanel,0);
		this.repaint();
	}
	
	/**
	 * 隐藏菜单
	 */
	public void removeMenu(){
		this.remove(menuPanel);
		this.repaint();
	}
}
