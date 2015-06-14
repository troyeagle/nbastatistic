package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import njuse.ffff.presenter.SearchController;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.TabBar;
import njuse.ffff.ui.ver2.component.TitleBar;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static MainFrame frame;

	private TitleBar titleBar;
	private TabBar tabBar;

	private PanelEx viewPanel;
	private PanelEx loadingPanel;

	private PlayerDetailPane playerPane;
	private TeamDetailPane teamPane;
	private MatchViewPane matchPane;
	private SearchResultPane searchResPane;

	private MainFrame() {
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setTitle("NBA数据查询系统");
		ImageIcon icon = new ImageIcon("./img/nba.png");
		setIconImage(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

		getContentPane().setBackground(new Color(210, 210, 210));
		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder());

		setLoadingPanel();

		viewPanel = new PanelEx(new CardLayout());
		viewPanel.setOpaque(false);

		UIEventManager.addListener(new UIEventHandler(), UIEventType.ALL);

		setVisible(true);

		initView();
	}

	private void initView() {
		UIEventManager.notify(UIEventType.BUSY, this);

		initTitleArea();
		setTitle("主页");

		playerPane = new PlayerDetailPane();
		teamPane = new TeamDetailPane();
		matchPane = new MatchViewPane();
		searchResPane = new SearchResultPane();

		viewPanel.setBorder(BorderFactory.createLineBorder(UIConfig.TitleBgColor, 1));

		viewPanel.add("主页", new MainPagePane());
		viewPanel.add("比赛直播", new GameLivePanel());
		viewPanel.add("球队一览", new TeamsOverViewPanel());
		viewPanel.add("球员一览", new PlayersOverViewPane());
		viewPanel.add("球员筛选", new PlayerFilterPane());
		viewPanel.add("搜索结果", searchResPane);
		viewPanel.add("球员详情", playerPane);
		viewPanel.add("球队详情", teamPane);
		viewPanel.add("比赛详情", matchPane);

		add(viewPanel);

		getContentPane().repaint();

		UIEventManager.notify(UIEventType.FINISH, this);
	}

	private void initTitleArea() {
		titleBar = new TitleBar(this);
		titleBar.setOpaque(false);
		titleBar.setTitle("NBA数据查询系统");

		tabBar = new TabBar("主页", "比赛直播", "球队一览", "球员一览", "球员筛选");
		tabBar.setOpaque(false);
		tabBar.addSwitchListener(e -> {
			String name = e.getSource().getName();
			((CardLayout) viewPanel.getLayout()).show(viewPanel, name);
			// TODO
		});

		PanelEx titleArea = new PanelEx(new BorderLayout(0, 0));
		titleArea.setBackground(UIConfig.TitleBgColor);
		titleArea.add(titleBar, BorderLayout.NORTH);
		titleArea.add(tabBar, BorderLayout.SOUTH);

		MouseAdapter l = initListener();
		titleArea.addMouseListener(l);
		titleArea.addMouseMotionListener(l);

		add(titleArea, BorderLayout.NORTH);
	}

	private MouseAdapter initListener() {
		MouseAdapter l = new MouseAdapter() {
			Point p;
			boolean moved = false;

			public void mousePressed(MouseEvent e) {
				p = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				moved = false;
				if (getY() < -1)
					setLocation(getX(), -1);
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
		return l;
	}

	public static MainFrame getInstance() {
		if (frame == null)
			frame = new MainFrame();
		return frame;
	}

	private class UIEventHandler implements UIEventListener {

		int busyCount;

		@Override
		public synchronized void actionPerformed(UIEvent e) {
			switch (e.getType()) {
			case BUSY:
				handleBusy();
				break;
			case FINISH:
				handleFinish();
				break;
			case SWITCH:
				handleSwitch(e.getMessage());
				break;
			case SEARCH:
				handleSearch(e.getMessage());
				break;
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
				if (!mes[1].equals("N/A") && !mes[1].isEmpty())
					setTeamPane(mes[1]);
				break;
			case "比赛详情":
				setMatchPane(mes[1], mes[2]);
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
			if (busyCount <= 0)
				t.setInitialDelay(100);
			if (busyCount < 0)
				busyCount = 0;
			t.setRepeats(false);
			t.start();
			if (busyCount < 0)
				busyCount = 0;
		}
	}

	private void setLoadingPanel() {
		loadingPanel = new LoadingPane();
		add(loadingPanel, 0);
		loadingPanel.setVisible(false);
	}

	private void handleSearch(String message) {
		SearchController.getInstance().search(searchResPane, message);
		tabBar.addTab("搜索结果");
		tabBar.switchTo("搜索结果");
	}

	public void setPlayerPane(String playerName) {
		playerPane.setPlayer(playerName);
		tabBar.addTab("球员详情");
		tabBar.switchTo("球员详情");
	}

	public void setTeamPane(String teamName) {
		teamPane.setTeam(teamName);
		tabBar.addTab("球队详情");
		tabBar.switchTo("球队详情");
	}

	public void setMatchPane(String date, String team) {
		matchPane.setMatch(date, team);
		tabBar.addTab("比赛详情");
		tabBar.switchTo("比赛详情");
	}

	@Override
	public void dispose() {
		super.dispose();
		System.exit(0);
	}

	public static void main(String[] args) {
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		TotalUIController.getInstance().initSystem();
		MainFrame.getInstance();
	}

}
