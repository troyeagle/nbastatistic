package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import njuse.ffff.presenter.playerController.PlayerInfoController;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchButton;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.ui.ver2.component.TabBar;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.PlayerDataService;

public class PlayerDetailPane extends PanelEx implements PlayerDataService {

	private static final long serialVersionUID = 1L;

	private static final String[] avgHeader = { "赛季", "球队", "首发", "时间", "总命中数",
			"出手", "命中%", "三分", "出手", "命中%", "罚球", "出手", "命中%", "篮板", "前场",
			"后场", "助攻", "抢断", "盖帽", "失误", "犯规", "得分"
	};
	private static final String[] totalHeader = { "赛季", "球队", "首发", "上场数", "总命中数",
			"出手", "命中%", "三分", "出手", "命中%", "罚球", "出手", "命中%", "篮板", "前场",
			"后场", "助攻", "抢断", "盖帽", "失误", "犯规", "得分"
	};

	private static final String[] advHeader = { "赛季", "球队", "篮板率", "进攻板", "防守板",
			"助攻率", "抢断率", "盖帽率", "失误率", "使用率", "效率", "真实命中", "进攻贡献值",
			"防守贡献值", "球员贡献值"
	};

	private static final String[] shotHeader = { "赛季", "球队", "主打位置", "出场数", "时间",
			"命中率", "平均出手距离", "两分", "0-3尺", "3-10尺", "10-16尺", "16尺–篮下", "三分",
			"两分", "0-3尺", "3-10尺", "10-16尺", "16尺-篮下", "三分" };

	private String name;

	private TabBar tabBar;
	private PanelEx viewPanel;

	private PlayerProfilePane profilePane;

	private TableView avgTable;
	private TableView totalTable;
	private TableView advTable;
	private TableView shotTable;
	private GameLogPanel gamesTable;

	private SwitchButton regularBtn;
	private SwitchButton playoffBtn;
	private SwitchButtonGroup group;

	public PlayerDetailPane() {
		super(new BorderLayout());
		setOpaque(false);

		profilePane = new PlayerProfilePane();
		add(profilePane, BorderLayout.NORTH);

		tabBar = new TabBar();
		tabBar.setAlignment(TabBar.CENTER);
		tabBar.setOpaque(true);
		tabBar.setBackground(UIConfig.TitleBgColor);
		tabBar.addSwitchListener(e -> {
			((CardLayout) viewPanel.getLayout()).show(viewPanel,
					tabBar.getActiveTabTitle());
			boolean isVisible = tabBar.getActiveTabIndex() < 3;
			regularBtn.setVisible(isVisible);
			playoffBtn.setVisible(isVisible);
		});

		regularBtn = new SwitchButton("常规赛");
		regularBtn.setBackground(UIConfig.ThemeColor);
		regularBtn.setForeground(Color.WHITE);
		regularBtn.setFont(UIConfig.SubTitleFont);
		playoffBtn = new SwitchButton("季后赛");
		playoffBtn.setBackground(UIConfig.ThemeColor);
		playoffBtn.setForeground(Color.WHITE);
		playoffBtn.setFont(UIConfig.SubTitleFont);

		group = new SwitchButtonGroup();
		group.addButton(regularBtn);
		group.addButton(playoffBtn);
		group.switchTo(0);
		group.addSwitchListener(e -> {
			// TODO
		});

		PanelEx buttonPanel = new PanelEx(new FlowLayout(FlowLayout.LEFT, 10, 5));
		buttonPanel.setOpaque(false);
		buttonPanel.add(regularBtn);
		buttonPanel.add(playoffBtn);

		viewPanel = new PanelEx(new CardLayout());
		viewPanel.setOpaque(false);

		PanelEx showPanel = new PanelEx(new BorderLayout());
		showPanel.setOpaque(false);
		showPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
		showPanel.add(buttonPanel, BorderLayout.NORTH);
		showPanel.add(viewPanel);

		PanelEx tabPanel = new PanelEx(new BorderLayout());
		tabPanel.setOpaque(false);
		tabPanel.add(tabBar, BorderLayout.NORTH);
		tabPanel.add(showPanel);
		add(tabPanel);

		avgTable = new TableView(null, avgHeader);
		tabBar.addTab("平均数据");
		viewPanel.add("平均数据", avgTable);

		totalTable = new TableView(null, totalHeader);
		tabBar.addTab("总数据");
		viewPanel.add("总数据", totalTable);

		advTable = new TableView(null, advHeader);
		tabBar.addTab("进阶数据");
		viewPanel.add("进阶数据", advTable);

		shotTable = new TableView(null, shotHeader);
		tabBar.addTab("投篮分布");
		viewPanel.add("投篮分布", shotTable);

		gamesTable = new GameLogPanel();
		tabBar.addTab("比赛数据");
		viewPanel.add("比赛数据", gamesTable);
		// TODO 特殊表头
		//			((DefaultTableCellRenderer) gamesTable.getTable()
		//					.getDefaultRenderer(Object.class))
		//					.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

		gamesTable.getSeasonList().addItemListener(e -> {
			// TODO
				PlayerInfoController.getInstance().setPlayerGameLog(this,
						gamesTable.getSelectedSeason(), name);
			});
	}

	public void setPlayer(String playerName) {
		boolean isChanged = name == null || !playerName.equals(name);
		name = playerName;

		profilePane.setPlayer(playerName);
		PlayerInfoController pic = PlayerInfoController.getInstance();
		pic.setPlayerAvgData(this, playerName);
		pic.setPlayerTotalData(this, playerName);
		pic.setPlayerAdvancedData(this, playerName);
		pic.setPlayerGameLog(this, null, playerName);	// TODO delete

		if (isChanged)
			tabBar.switchTo(0);
	}

	@Override
	public void setAvgData(Object[][] data) {
		data = removeEmptyData(data);
		avgTable.setTable(data);
	}

	@Override
	public void setTotalData(Object[][] data) {
		data = removeEmptyData(data);
		totalTable.setTable(data);
	}

	@Override
	public void setAdvancedData(Object[][] data) {
		data = removeEmptyData(data);
		advTable.setTable(data);
	}

	@Override
	public void setShotData(Object[][] data) {
		data = removeEmptyData(data);
		shotTable.setTable(data);
	}

	@Override
	public void setGameLog(Object[][] data, int[][] dirty) {
		setGameLog(data);
	}

	private Object[][] removeEmptyData(Object[][] data) {
		List<Object[]> resList = new ArrayList<Object[]>();
		int rowLen = data.length;
		for (int i = 0; i < rowLen; i++) {
			boolean isEmpty = false;
			if (data[i] != null) {
				int columnLen = data[i].length;
				boolean allZero = true;
				for (int j = 0; j < columnLen; j++) {
					Object cell = data[i][j];
					if (cell == null || cell.equals("") || cell.equals("N/A")) {
						isEmpty = true;
						break;
					}
					if (j >= 1 && !cell.equals(0) && !cell.equals(0.0)
							&& !cell.equals("0:00") && !cell.equals("0%")) {
						allZero = false;
						break;
					}
				}
				if (!isEmpty && !allZero) {
					resList.add(data[i]);
				}
			}
		}
		return resList.toArray(new Object[0][0]);
	}

	@Override
	public void setGameSeasons(String[] seasons) {
		gamesTable.setSeasons(seasons);
	}

	@Override
	public String getSelectedSeason() {
		return gamesTable.getSelectedSeason();
	}

	@Override
	public void setGameLog(Object[][] data) {
		gamesTable.setData(data);
	}
}
