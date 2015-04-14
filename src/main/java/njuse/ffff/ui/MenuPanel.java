package njuse.ffff.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import njuse.ffff.presenter.SearchController;
import njuse.ffff.presenter.playerController.PlayerCompareController;
import njuse.ffff.presenter.playerController.PlayerFilterController;
import njuse.ffff.presenter.teamController.TeamCompareController;
import njuse.ffff.presenterService.SearchService;
import njuse.ffff.presenterService.playerService.PlayerCompareService;
import njuse.ffff.presenterService.playerService.PlayerFilterService;
import njuse.ffff.presenterService.teamService.TeamCompareService;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel{
	private final int titlePanel_width = 150;
	private final int titlePanel_height = 200;
	
//	private ControllerService uiController;
	private PlayerCompareService playerCompareController;
	private TeamCompareService teamCompareController;
	private PlayerFilterService playerFilterController;
	private SearchService searchController;
	
	private Color background = new Color(170,200,228);
	private String img_search_URL = "picture/menuItem/search.jpg";
	private String img_search_changed_URL = "picture/menuItem/search_changed.jpg";
//	private String img_detailSearch_URL = "picture/menuItem/detailSearch.jpg";
	private String img_teamCompare_URL = "picture/menuItem/teamCompare.jpg";
	private String img_teamCompare_changed_URL = "picture/menuItem/teamCompare_changed.jpg";
	private String img_playerCompare_URL = "picture/menuItem/playerCompare.jpg";
	private String img_playerCompare_changed_URL = "picture/menuItem/playerCompare_changed.jpg";
	private String img_playerFilter_URL = "picture/menuItem/playerFilter.jpg";
	private String img_playerFilter_changed_URL = "picture/menuItem/playerFilter_changed.jpg";
//	private String img_teamHandBook_URL = "picture/menuItem/teamHandBook.jpg";
//	private String img_playerHandBook_URL = "picture/menuItem/playerHandBook.jpg";
	private String img_exit_URL = "picture/menuItem/exit.jpg";
	private String img_exit_changed_URL = "picture/menuItem/exit_changed.jpg";
	
	private JLabel label_searchPanel;
//	private JLabel label_detailSearchPanel;
	private JLabel label_teamComparePanel;
	private JLabel label_playerComparePanel;
	private JLabel label_playerFilterPanel;
//	private JLabel label_teamHandBookPanel;
//	private JLabel label_playerHandBookPanel;
	private JLabel label_exit;
	
	public MenuPanel(){
		this.setSize(titlePanel_width, titlePanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
//		uiController = UIController.getInstance();
		playerCompareController = PlayerCompareController.getInstance();
		teamCompareController = TeamCompareController.getInstance();
		playerFilterController = PlayerFilterController.getInstance();
		searchController = SearchController.getInstance();
		
		//搜索界面按钮
		label_searchPanel = new JLabel();
		label_searchPanel.setOpaque(true);
		ImageIcon icon = new ImageIcon(img_search_URL);
		label_searchPanel.setIcon(icon);
		label_searchPanel.setBounds(0, 0, 150, 40);
		label_searchPanel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon = new ImageIcon(img_search_URL);
				label_searchPanel.setIcon(icon);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_changed = new ImageIcon(img_search_changed_URL);
				label_searchPanel.setIcon(icon_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//设置球队横向比较界面
//				uiController.setTeamComparePanel();
				searchController.setSearchPanel();
			}
		});
		
		//球队横向对比界面按钮
		label_teamComparePanel = new JLabel();
		label_teamComparePanel.setOpaque(true);
		icon = new ImageIcon(img_teamCompare_URL);
		label_teamComparePanel.setIcon(icon);
		label_teamComparePanel.setBounds(0, 40, 150, 40);
		label_teamComparePanel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon = new ImageIcon(img_teamCompare_URL);
				label_teamComparePanel.setIcon(icon);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_changed = new ImageIcon(img_teamCompare_changed_URL);
				label_teamComparePanel.setIcon(icon_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//设置球队横向比较界面
//				uiController.setTeamComparePanel();
				teamCompareController.setTeamCompareInfoForSeason(null,"");
			}
		});
		
		//球员横向对比界面按钮
		label_playerComparePanel = new JLabel();
		label_playerComparePanel.setOpaque(true);
		icon = new ImageIcon(img_playerCompare_URL);
		label_playerComparePanel.setIcon(icon);
		label_playerComparePanel.setBounds(0, 80, 150, 40);
		label_playerComparePanel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon = new ImageIcon(img_playerCompare_URL);
				label_playerComparePanel.setIcon(icon);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_changed = new ImageIcon(img_playerCompare_changed_URL);
				label_playerComparePanel.setIcon(icon_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//设置球员横向比较界面
//				uiController.setPlayerComparePanel();
				playerCompareController.setPlayerCompareInfoForSeason(null,"");
			}
		});
		
		//球员筛选界面按钮
		label_playerFilterPanel = new JLabel();
		label_playerFilterPanel.setOpaque(true);
		icon = new ImageIcon(img_playerFilter_URL);
		label_playerFilterPanel.setIcon(icon);
		label_playerFilterPanel.setBounds(0, 120, 150, 40);
		label_playerFilterPanel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon = new ImageIcon(img_playerFilter_URL);
				label_playerFilterPanel.setIcon(icon);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_changed = new ImageIcon(img_playerFilter_changed_URL);
				label_playerFilterPanel.setIcon(icon_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//设置球员筛选界面
//				uiController.setPlayerFilterPanel();
				playerFilterController.setPlayerFilterPanel();
			}
		});
		
		//退出系统按钮
		label_exit = new JLabel();
		label_exit.setOpaque(true);
		icon = new ImageIcon(img_exit_URL);
		label_exit.setIcon(icon);
		label_exit.setBounds(0, 160, 150, 40);
		label_exit.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon_exit = new ImageIcon(img_exit_URL);
				label_exit.setIcon(icon_exit);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_exitChanged = new ImageIcon(img_exit_changed_URL);
				label_exit.setIcon(icon_exitChanged);
			}
			public void mouseClicked(MouseEvent arg0) {
				//退出系统
				System.exit(0);
			}
		});
		
		this.setLayout(null);
		this.add(label_searchPanel);
		this.add(label_teamComparePanel);
		this.add(label_playerComparePanel);
		this.add(label_playerFilterPanel);
		this.add(label_exit);
	}
}
