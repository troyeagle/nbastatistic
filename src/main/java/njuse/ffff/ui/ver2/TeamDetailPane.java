package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import njuse.ffff.presenter.teamController.TeamInfoController;
import njuse.ffff.ui.component.ButtonEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchButton;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.ui.ver2.component.TabBar;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.TeamDataService;

public class TeamDetailPane extends PanelEx implements TeamDataService {

	private static final long serialVersionUID = 1L;

	private static final String[] avgHeader = { "赛季", "比赛数", "投篮", "命中", "出手",
			"三分", "命中", "出手", "罚球", "命中", "出手", "篮板", "前场", "后场", "助攻",
			"抢断", "盖帽", "失误", "犯规", "得分"
	};
	private static final String[] totalHeader = { "赛季", "投篮", "命中", "出手", "三分",
			"命中", "出手", "罚球", "命中", "出手", "篮板", "前场", "后场", "助攻", "抢断",
			"盖帽", "失误", "犯规", "得分"
	};
	private static final String[] advHeader = { "赛季", "进攻回合", "进攻效率", "防守效率",
			"进攻篮板效率", "防守篮板效率", "抢断效率", "助攻效率"
	};

	private String name;

	private TabBar tabBar;
	private PanelEx viewPanel;

	private TeamProfilePane profilePane;

	private PanelEx iconPanel;
	private TableView avgTable;
	private TableView totalTable;
	private TableView advTable;
	private GameLogPanel gamesTable;

	public TeamDetailPane() {
		super(new BorderLayout());
		setOpaque(false);

		profilePane = new TeamProfilePane();
		add(profilePane, BorderLayout.NORTH);

		tabBar = new TabBar();
		tabBar.setAlignment(TabBar.CENTER);
		tabBar.setOpaque(true);
		tabBar.setBackground(UIConfig.TitleBgColor);
		tabBar.addSwitchListener(e -> {
			((CardLayout) viewPanel.getLayout()).show(viewPanel,
					tabBar.getActiveTabTitle());
		});

		viewPanel = new PanelEx(new CardLayout());
		viewPanel.setOpaque(false);

		PanelEx showPanel = new PanelEx(new BorderLayout());
		showPanel.setOpaque(false);
		showPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
		showPanel.add(viewPanel);

		PanelEx tabPanel = new PanelEx(new BorderLayout());
		tabPanel.setOpaque(false);
		tabPanel.add(tabBar, BorderLayout.NORTH);
		tabPanel.add(showPanel);
		add(tabPanel);

		iconPanel = new PanelEx(new BorderLayout());
		iconPanel.setBackground(new Color(0, 0, 0, 32));
		tabBar.addTab("球员一览");
		viewPanel.add("球员一览", iconPanel);

		avgTable = new TableView(null, avgHeader);
		tabBar.addTab("平均数据");
		viewPanel.add("平均数据", avgTable);

		totalTable = new TableView(null, totalHeader);
		tabBar.addTab("总数据");
		viewPanel.add("总数据", totalTable);

		advTable = new TableView(null, advHeader);
		tabBar.addTab("进阶数据");
		viewPanel.add("进阶数据", advTable);

		gamesTable = new GameLogPanel();
		tabBar.addTab("比赛数据");
		viewPanel.add("比赛数据", gamesTable);

		gamesTable.getSeasonList().addItemListener(e -> {
			TeamInfoController.getInstance().setTeamGameLog(this,
					gamesTable.getSelectedSeason(), name);
		});
	}
	
	public void updateData() {
		setTeam(name);
	}

	public void setTeam(String teamName) {
		boolean isChanged = name == null || !teamName.equals(name);
		name = teamName;

		profilePane.setTeam(teamName);
		TeamInfoController tic = TeamInfoController.getInstance();
		tic.setPlayerForTeam(this, teamName);
		tic.setTeamAvgData(this, teamName);
		tic.setTeamTotalData(this, teamName);
		tic.setTeamAdvancedlData(this, teamName);
		String[] seasons = tic.getInvolvedSeason(teamName);
		setGameSeasons(seasons);

		tic.setTeamGameLog(this, null, name);

		if (isChanged) {
			tabBar.switchTo(0);
		}
	}

	@Override
	public void setPlayers(Object[][] data) {
		iconPanel.removeAll();

		PanelEx pagePanel = new PanelEx(new CardLayout());
		pagePanel.setOpaque(false);

		iconPanel.add(pagePanel);

		String[] playersName = new String[data.length];
		for (int i = 0; i < playersName.length; i++) {
			playersName[i] = data[i][1].toString();
		}
		final int r = 2;
		final int c = 5;
		int pageCount;
		for (pageCount = 0; pageCount * r * c < playersName.length; pageCount++) {
			PanelEx iconPage = new PanelEx(new GridLayout(r, c));
			iconPage.setOpaque(false);
			pagePanel.add(String.valueOf(pageCount + 1), iconPage);

			for (int i = 0; i < r * c; i++) {
				int index = pageCount * r * c + i;
				if (index < playersName.length) {
					ImageIcon portrait = ImageUtilsEx.getPlayerImg(playersName[index],
							ImageUtilsEx.M);
					if (portrait == null)
						portrait = new ImageIcon("./img/no_image.png");
					ButtonEx player = new ButtonEx(playersName[index], portrait);
					player.setName(playersName[index]);
					player.setOpaque(false);
					player.setForeground(Color.BLACK);
					player.setFont(UIConfig.SmallFont);
					player.setVerticalTextPosition(ButtonEx.BOTTOM);
					player.setHorizontalTextPosition(ButtonEx.CENTER);
					player.setIconTextGap(10);
					iconPage.add(player);

					player.addActionListener(e ->
							UIEventManager.notify(UIEventType.SWITCH,
									"球员详情:" + player.getName())
							);
				} else {
					PanelEx p = new PanelEx();
					p.setOpaque(false);
					iconPage.add(p);
				}
			}
		}

		if (pageCount > 1) {
			PanelEx switchPanel = new PanelEx();
			switchPanel.setOpaque(false);
			iconPanel.add(switchPanel, BorderLayout.SOUTH);

			SwitchButtonGroup group = new SwitchButtonGroup();
			group.addSwitchListener(e ->
					((CardLayout) pagePanel.getLayout()).show(pagePanel, e.getSource()
							.getName())
					);
			for (int i = 0; i < pageCount; i++) {
				SwitchButton pageIndex = new SwitchButton(String.valueOf(i + 1));
				pageIndex.setName(String.valueOf(i + 1));
				pageIndex.setBackground(UIConfig.ThemeColor);
				pageIndex.setForeground(Color.WHITE);
				pageIndex.setFont(UIConfig.SmallFont);
				switchPanel.add(pageIndex);
				group.addButton(pageIndex);
			}
			group.switchTo(0);
		}
	}

	@Override
	public void setAvgDataTable(Object[][] data) {
		data = removeEmptyData(data);
		avgTable.setTable(data);
	}

	@Override
	public void setTotalDataTable(Object[][] data) {
		data = removeEmptyData(data);
		totalTable.setTable(data);
	}

	@Override
	public void setAdvancedDataTable(Object[][] data) {
		data = removeEmptyData(data);
		advTable.setTable(data);
	}

	@Override
	public void setGameLog(Object[][] data, int[][] dirty) {
		setGameLog(data);
		System.out.println("setted");
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
					if (cell == null || cell.equals("")) {
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
