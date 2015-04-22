package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchEvent;
import njuse.ffff.ui.ver2.component.SwitchListener;
import njuse.ffff.ui.ver2.component.TabBar;
import njuse.ffff.ui.ver2.component.TitleBar;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static MainFrame frame;

	private TitleBar titleBar;
	private TabBar tabBar;

	private PanelEx viewPanel;

	private PlayerDetailPane playerPane;
	private PanelEx loadingPanel;

	private MainFrame() {
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(960, 720);
		this.setLocationRelativeTo(null);

		getContentPane().setBackground(Color.DARK_GRAY);

		setLoadingPanel();

		viewPanel = new PanelEx(new CardLayout());
		viewPanel.setOpaque(false);

		UIEventManager.addListener(new UIEventHandler(), UIEventType.ALL);

		this.setVisible(true);

		initView();
	}

	private void initView() {
		UIEventManager.notify(UIEventType.BUSY, this);

		initTitleArea();
		this.setTitle("主页");

		playerPane = new PlayerDetailPane();

		viewPanel.add("球队一览", new TeamsOverViewPanel());
		viewPanel.add("球员一览", new PlayersOverViewPane());
		viewPanel.add("球员详情", playerPane);

		add(viewPanel);

		getContentPane().repaint();

		UIEventManager.notify(UIEventType.FINISH, this);
	}

	private void initTitleArea() {
		titleBar = new TitleBar(this);
		titleBar.setOpaque(false);

		tabBar = new TabBar("主页", "球队一览", "球员一览", "球员筛选");
		tabBar.setOpaque(false);
		tabBar.addSwitchListener(new SwitchListener() {

			@Override
			public void actionPerformed(SwitchEvent e) {
				String name = e.getSource().getName();
				setTitle(name);
				((CardLayout) viewPanel.getLayout()).show(viewPanel, name);
				// TODO
			}
		});

		PanelEx titleArea = new PanelEx(new BorderLayout(0, 0));
		titleArea.setBackground(UIConfig.TitleBgColor);
		titleArea.add(titleBar, BorderLayout.NORTH);
		titleArea.add(tabBar, BorderLayout.SOUTH);

		MouseAdapter l = new MouseAdapter() {
			Point p;
			boolean moved = false;

			public void mousePressed(MouseEvent e) {
				p = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				moved = false;
				if (getY() < 0)
					setLocation(getX(), 0);
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

		add(titleArea, BorderLayout.NORTH);
	}

	public static MainFrame getInstance() {
		if (frame == null)
			frame = new MainFrame();
		return frame;
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		if (titleBar != null)
			titleBar.setTitle(title);
	}

	private class UIEventHandler implements UIEventListener {

		int busyCount;

		@Override
		public void actionPerformed(UIEvent e) {
			switch (e.getType()) {
			case BUSY:
				handleBusy();
				break;
			case FINISH:
				handleFinish();
				break;
			case SWITCH:
				handleSwitch(e.getMessage());
			default:
				break;
			}
		}

		private void handleSwitch(String message) {
			String[] mes = message.split(":");
			switch (mes[0]) {
			case "球员详情":
				setPlayerPane(mes[1]);
				break;
			case "球队详情":
				setTeamPane(mes[1]);
				break;
			}
		}

		private void handleBusy() {
			busyCount++;
			handleStatus();
		}

		private void handleFinish() {
			busyCount--;
			handleStatus();
		}

		private void handleStatus() {
			Timer t = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO
					loadingPanel.setVisible(busyCount > 0);
				}
			});
			if (busyCount < 0)
				t.setInitialDelay(100);
			t.setRepeats(false);
			t.start();
			if (busyCount < 0)
				busyCount = 0;
		}
	}

	private void setLoadingPanel() {
		// TODO
		loadingPanel = new LoadingPane();
		add(loadingPanel, 0);
		loadingPanel.setVisible(false);
//		setGlassPane(new LoadingPane());
//		getGlassPane().addMouseListener(new MouseAdapter() {
//		});	// loading时不能鼠标操作
	}

	public void setTeamPane(String teamName) {
		tabBar.addTab("球队详情");
		tabBar.switchTo("球队详情");
	}

	public void setPlayerPane(String playerName) {
		playerPane.setPlayer(playerName);
		tabBar.addTab("球员详情");
		tabBar.switchTo("球员详情");
	}
	
	@Override
	public void dispose() {
		super.dispose();
		System.exit(0);
	}

	public static void main(String[] args) {
		TotalUIController.getInstance().initSystem();
		UIConfig.initialize();
		MainFrame.getInstance();
	}

}
