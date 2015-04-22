package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;

import njuse.ffff.presenter.playerController.PlayerInfoController;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchEvent;
import njuse.ffff.ui.ver2.component.SwitchListener;
import njuse.ffff.ui.ver2.component.TabBar;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.PlayerDataService;

public class PlayerDetailPane extends PanelEx implements PlayerDataService {

	private static final long serialVersionUID = 1L;

	private static final String[] avgHeader = { "赛季", "球队", "首发", "时间", "投篮",
			"命中", "出手", "三分", "命中", "出手", "罚球", "命中", "出手", "篮板", "前场",
			"后场", "助攻", "抢断", "盖帽", "失误", "犯规", "得分"
	};
	private static final String[] totalHeader = avgHeader;

	private static final String[] advHeader = { "赛季", "球队", "篮板率", "进攻板", "防守板",
			"助攻率", "抢断率", "盖帽率", "失误率", "使用率", "效率", "GmSc效率值",
			"真实命中", "投篮效率"
	};

	private static final String[] gameHeader = { "比赛日期", "比赛对阵" };

	private TabBar tabBar;
	private PanelEx viewPanel;

	private PlayerProfilePane profilePane;

	private TableView avgTable;
	private TableView totalTable;
	private TableView advTable;
	private TableView gamesTable;

	public PlayerDetailPane() {
		super(new BorderLayout());
		// TODO
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
		viewPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		PanelEx tabPanel = new PanelEx(new BorderLayout());
		tabPanel.setOpaque(false);
		tabPanel.add(tabBar, BorderLayout.NORTH);
		tabPanel.add(viewPanel);
		add(tabPanel);
	}

	public void setPlayer(String playerName) {
		profilePane.setPlayer(playerName);
		PlayerInfoController pic = PlayerInfoController.getInstance();
		pic.setPlayerAvgData(this, playerName);
		pic.setPlayerTotalData(this, playerName);
		pic.setPlayerAdvancedData(this, playerName);
		pic.setPlayerGameLog(this, playerName);
	}

	@Override
	public void setAvgData(Object[][] data) {
		if (avgTable == null) {
			avgTable = new TableView(data, avgHeader);
			setTableUIConfig(avgTable);
			tabBar.addTab("平均数据");
			viewPanel.add("平均数据", avgTable);
			if (tabBar.getActiveTabIndex() == -1) {
				tabBar.switchTo(0);
			}
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
			if (tabBar.getActiveTabIndex() == -1) {
				tabBar.switchTo(0);
			}
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
			if (tabBar.getActiveTabIndex() == -1) {
				tabBar.switchTo(0);
			}
		} else {
			advTable.setTable(data);
		}
	}

	@Override
	public void setGameLog(Object[][] data, int[][] dirty) {
		if (gamesTable == null) {
			gamesTable = new TableView(data, gameHeader);
			setTableUIConfig(gamesTable);
			tabBar.addTab("比赛数据");
			viewPanel.add("比赛数据", gamesTable);
			if (tabBar.getActiveTabIndex() == -1) {
				tabBar.switchTo(0);
			}
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
