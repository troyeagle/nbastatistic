package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import njuse.ffff.ui.ver2.component.TabBar;
import njuse.ffff.ui.ver2.component.TitleBar;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static MainFrame frame;

	private TitleBar titleBar;
	private TabBar tabBar;

	private MainFrame() {
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);

		getContentPane().setBackground(Color.DARK_GRAY);

		initTitleArea();

		// TODO
		add(new TeamsOverViewPanel());

		this.setVisible(true);
	}

	private void initTitleArea() {
		titleBar = new TitleBar(this);
		titleBar.setOpaque(false);

		tabBar = new TabBar("主页", "球队一览", "球员一览", "球员筛选");
		tabBar.setOpaque(false);

		JPanel titleArea = new JPanel(new BorderLayout(0, 0));
		titleArea.setBackground(UIConfig.TitleBgColor);
		titleArea.add(titleBar, BorderLayout.NORTH);
		titleArea.add(tabBar, BorderLayout.SOUTH);

		add(titleArea, BorderLayout.NORTH);

		MouseAdapter l = new MouseAdapter() {
			Point p;
			boolean moved = false;

			public void mousePressed(MouseEvent e) {
				p = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				moved = false;
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				Point p1 = e.getPoint();
				// 模拟窗口移动
				if (moved || (moved = p1.distance(p) >= 5)) {
					Point p2 = getLocation(null);
					p2.x += p1.x - p.x;
					p2.y += p1.y - p.y;
					setLocation(p2);
				}
			}
		};
		titleArea.addMouseListener(l);
		titleArea.addMouseMotionListener(l);
	}

	public static MainFrame getInstance() {
		if (frame == null)
			frame = new MainFrame();
		return frame;
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		titleBar.setTitle(title);
	}

	public static void main(String[] args) {
		UIConfig.initialize();
		MainFrame.getInstance();
	}

}
