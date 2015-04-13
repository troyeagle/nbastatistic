package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static MainFrame frame;

	TitleArea titleArea;

	private MainFrame() {
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);

		titleArea = new TitleArea();
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
				if (moved || (moved = p1.distance(p) >= 10)) {
					Point p2 = getLocation(null);
					p2.x += p1.x - p.x;
					p2.y += p1.y - p.y;
					setLocation(p2);
				}
			}
		};
		titleArea.addMouseListener(l);
		titleArea.addMouseMotionListener(l);

		add(new JPanel());

		this.setVisible(true);
	}

	public static MainFrame getInstance() {
		if (frame == null)
			frame = new MainFrame();
		return frame;
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		titleArea.setTitle(title);
	}

	public static void main(String[] args) {
		UIConfig.initialize();
		MainFrame.getInstance();
	}

}
