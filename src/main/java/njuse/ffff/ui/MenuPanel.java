package njuse.ffff.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel{
	private final int titlePanel_width = 150;
	private final int titlePanel_height = 200;
	
	private Color background = new Color(170,200,228);
	private String img_search_URL = "picture/menuItem/search.jpg";
//	private String img_detailSearch_URL = "picture/menuItem/detailSearch.jpg";
	private String img_teamCompare_URL = "picture/menuItem/teamCompare.jpg";
	private String img_playerCompare_URL = "picture/menuItem/playerCompare.jpg";
	private String img_playerFilter_URL = "picture/menuItem/playerFilter.jpg";
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
		
		//搜索界面按钮
		label_searchPanel = new JLabel();
		label_searchPanel.setOpaque(true);
		ImageIcon icon = new ImageIcon(img_search_URL);
		label_searchPanel.setIcon(icon);
		label_searchPanel.setBounds(0, 0, 150, 40);
		
		//球队横向对比界面按钮
		label_teamComparePanel = new JLabel();
		label_teamComparePanel.setOpaque(true);
		icon = new ImageIcon(img_teamCompare_URL);
		label_teamComparePanel.setIcon(icon);
		label_teamComparePanel.setBounds(0, 40, 150, 40);
		
		//球员横向对比界面按钮
		label_playerComparePanel = new JLabel();
		label_playerComparePanel.setOpaque(true);
		icon = new ImageIcon(img_playerCompare_URL);
		label_playerComparePanel.setIcon(icon);
		label_playerComparePanel.setBounds(0, 80, 150, 40);
		
		//球员筛选界面按钮
		label_playerFilterPanel = new JLabel();
		label_playerFilterPanel.setOpaque(true);
		icon = new ImageIcon(img_playerFilter_URL);
		label_playerFilterPanel.setIcon(icon);
		label_playerFilterPanel.setBounds(0, 120, 150, 40);
		
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
				// TODO 退出系统
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
