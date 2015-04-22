package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;

import njuse.ffff.presenter.playerController.PlayerInfoController;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchEvent;
import njuse.ffff.ui.ver2.component.SwitchListener;
import njuse.ffff.ui.ver2.component.TabBar;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.PlayerDataService;

public class PlayerDetailPane extends PanelEx implements PlayerDataService {

	private static final long serialVersionUID = 1L;

	private static final String[] avgHeader = { "赛季", "球队", "首发", "时间",
			"命中", "出手", "三分", "命中", "出手", "罚球", "命中", "出手", "篮板", "前场",
			"后场", "助攻", "抢断", "盖帽", "失误", "犯规", "得分"
	};
	private static final String[] totalHeader = avgHeader;

	private static final String[] advHeader = { "赛季", "球队", "篮板率", "进攻板", "防守板",
			"助攻率", "抢断率", "盖帽率", "失误率", "使用率", "效率", "GmSc效率值",
			"真实命中", "投篮效率"
	};

	private static final String[] gameHeader = { "比赛日期", "比赛对阵" };

	private String name;

	private TabBar tabBar;
	private PanelEx viewPanel;

	private PlayerProfilePane profilePane;

	private TableView avgTable;
	private TableView totalTable;
	private TableView advTable;
	private TableView gamesTable;

	public PlayerDetailPane() {
		super(new BorderLayout());
		setOpaque(false);

		profilePane = new PlayerProfilePane();
		add(profilePane, BorderLayout.NORTH);

		tabBar = new TabBar();
		tabBar.setAlignment(TabBar.CENTER);
		tabBar.setOpaque(true);
		tabBar.setBackground(UIConfig.TitleBgColor);
		tabBar.addSwitchListener(new SwitchListener() {
			@Override
			public void actionPerformed(SwitchEvent e) {
				((CardLayout) viewPanel.getLayout()).show(viewPanel,
						tabBar.getActiveTabTitle());
			}
		});

		viewPanel = new PanelEx(new CardLayout());
		viewPanel.setOpaque(false);
		viewPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

		PanelEx tabPanel = new PanelEx(new BorderLayout());
		tabPanel.setOpaque(false);
		tabPanel.add(tabBar, BorderLayout.NORTH);
		tabPanel.add(viewPanel);
		add(tabPanel);
	}

	public void setPlayer(String playerName) {

		Timer t = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isChanged = name == null || !playerName.equals(name);
				name = playerName;

				PlayerDetailPane p = PlayerDetailPane.this;
				profilePane.setPlayer(playerName);
				PlayerInfoController pic = PlayerInfoController.getInstance();
				pic.setPlayerAvgData(p, playerName);
				pic.setPlayerTotalData(p, playerName);
				pic.setPlayerAdvancedData(p, playerName);
				pic.setPlayerGameLog(p, playerName);

				if (isChanged) {
					tabBar.switchTo(0);
				}
			}
		});
		t.setRepeats(false);
		t.start();
	}

	@Override
	public void setAvgData(Object[][] data) {
		if (avgTable == null) {
			avgTable = new TableView(data, avgHeader);
			setTableUIConfig(avgTable);
			tabBar.addTab("平均数据");
			viewPanel.add("平均数据", avgTable);
		} else {
			avgTable.setTable(data);
		}
	}

	@Override
	public void setTotalData(Object[][] data) {
		if (totalTable == null) {
			totalTable = new TableView(data, totalHeader);
			setTableUIConfig(totalTable);
			tabBar.addTab("总数据");
			viewPanel.add("总数据", totalTable);
		} else {
			totalTable.setTable(data);
		}
	}

	@Override
	public void setAdvancedData(Object[][] data) {
		if (advTable == null) {
			advTable = new TableView(data, advHeader);
			setTableUIConfig(advTable);
			tabBar.addTab("进阶数据");
			viewPanel.add("进阶数据", advTable);
		} else {
			advTable.setTable(data);
		}
	}

	@Override
	public void setGameLog(Object[][] data, int[][] dirty) {
		if (gamesTable == null) {
			gamesTable = new TableView(data, gameHeader);
			setTableUIConfig(gamesTable);
			gamesTable.getTable().setCursor(new Cursor(Cursor.HAND_CURSOR));
			gamesTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int[] point = gamesTable.getSelectedCellLocation();
					if (point[0] >= 0) {
						Object date = gamesTable.getValueAt(point[0], 0);
						String name = gamesTable.getValueAt(point[0], 1).toString()
								.split("VS")[0].trim();
						UIEventManager
								.notify(UIEventType.SWITCH, "比赛详情:" + date + ":" + name);
					}
				}
			});

			tabBar.addTab("比赛数据");
			viewPanel.add("比赛数据", gamesTable);
			((DefaultTableCellRenderer) gamesTable.getTable()
					.getDefaultRenderer(Object.class))
					.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		} else {
			gamesTable.setTable(data);
		}
	}

	private void setTableUIConfig(TableView table) {
		table.setTableFont(UIConfig.ContentFont);
		table.setHeaderFont(UIConfig.SmallFont);
		table.setRowHeight(UIConfig.ContentFont.getSize() + 5);
		table.setForeground(Color.WHITE);
		table.setSelectionBgColor(UIConfig.TableSelectionBgColor);
		table.setSelectionFgColor(UIConfig.TableSelectionFgColor);
		table.setTableFgColor(UIConfig.TableFgColor);
		table.setHeaderBgColor(UIConfig.TableHeaderBgColor);
		table.setHeaderFgColor(UIConfig.TableHeaderFgColor);
	}
}
