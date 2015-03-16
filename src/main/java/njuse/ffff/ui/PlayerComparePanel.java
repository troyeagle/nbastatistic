package njuse.ffff.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

import njuse.ffff.presenter.ControllerService;
import njuse.ffff.presenter.UIController;

@SuppressWarnings("serial")
public class PlayerComparePanel extends JPanel{
	private final int teamComparePanel_width = 1100;
	private final int teamComparePanel_height = 700;
	
	private ControllerService uiController;

	private Color background = new Color(99,43,142);
	private String img_arrow_up_URL = "picture/arrow/arrow_up.jpg";
	private String img_arrow_up_changed_URL = "picture/arrow/arrow_up_changed.jpg";
	private String img_arrow_left_URL = "picture/arrow/arrow_left.jpg";
	private String img_arrow_left_changed_URL = "picture/arrow/arrow_left_changed.jpg";
	
	private String[] teamCompareTable_title = {"球队名称","比赛场数","投篮命中数","投篮出手数"
			,"三分命中数","三分出手数","罚球命中数","罚球出手数","进攻篮板数","防守篮板数","篮板数"
			,"助攻数","抢断数","盖帽数","失误数","犯规数",""};
	
	private JLabel label_arrow_up;
	private JLabel label_arrow_left;
	
	public PlayerComparePanel(){
		this.setSize(teamComparePanel_width, teamComparePanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		uiController = UIController.getInstance();
		
		//向左翻页标志
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
				ImageIcon icon_left_changed = new ImageIcon(img_arrow_left_changed_URL);
				label_arrow_left.setIcon(icon_left_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到"球队信息横向比较"界面
				uiController.setTeamComparePanel();
			}
		});
		
		//向左翻页提示
		JLabel label_left_info = new JLabel("球队信息横向比较");
		label_left_info.setOpaque(true);
		label_left_info.setBackground(background);
		label_left_info.setForeground(Color.WHITE);
		label_left_info.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_left_info.setBounds(88, 48, 176, 28);
		
		//上方标题栏
		JLabel label_title = new JLabel("球员信息横向比较");
		label_title.setOpaque(true);
		label_title.setBackground(getBackground());
		label_title.setForeground(Color.WHITE);
		label_title.setFont(new FontUIResource("DialogInput", Font.BOLD, 40));
		label_title.setBounds(358, 42, 400, 44);
		
		//向上翻页提示
		JLabel label_up_info = new JLabel("搜索界面");
		label_up_info.setOpaque(true);
		label_up_info.setBackground(background);
		label_up_info.setForeground(Color.WHITE);
		label_up_info.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_up_info.setBounds(938, 48, 90, 28);
		
		//上指向箭头，返回搜索界面
		label_arrow_up = new JLabel();
		label_arrow_up.setOpaque(true);
		label_arrow_up.setBounds(1026, 42, 40, 40);
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
		
		//球队信息比较表格
		
		
		this.setLayout(null);
		this.add(label_left_info);
		this.add(label_arrow_left);
		this.add(label_title);
		this.add(label_arrow_up);
		this.add(label_up_info);
	}
}
