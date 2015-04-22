package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import njuse.ffff.presenter.teamController.TeamInfoController;
import njuse.ffff.ui.component.ButtonEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchButton;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.ui.ver2.component.SwitchEvent;
import njuse.ffff.ui.ver2.component.SwitchListener;
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
	private static final String[] gameHeader = { "比赛日期", "比赛对阵" };

	private String name;

	private TabBar tabBar;
	private PanelEx viewPanel;

	private TeamProfilePane profilePane;

	private PanelEx iconPanel;
	//	private TableView playerTable;
	private TableView avgTable;
	private TableView totalTable;
	private TableView advTable;
	private TableView gamesTable;

	public TeamDetailPane() {
		super(new BorderLayout());
		setOpaque(false);

		profilePane = new TeamProfilePane();
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

	public void setTeam(String teamName) {
		Timer t = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isChanged = name == null || !teamName.equals(name);
				name = teamName;

				TeamDetailPane pane = TeamDetailPane.this;
				profilePane.setTeam(teamName);
				TeamInfoController tic = TeamInfoController.getInstance();
				tic.setPlayerForTeam(pane, teamName);
				tic.setTeamAvgData(pane, teamName);
				tic.setTeamTotalData(pane, teamName);
				tic.setTeamAdvancedlData(pane, teamName);
				tic.setTeamGameLog(pane, teamName);

				if (isChanged) {
					tabBar.switchTo(0);
				}
			}
		});
		t.setRepeats(false);
		t.start();
	}

	@Override
	public void setPlayers(Object[][] data) {

		if (iconPanel != null) {
			iconPanel.removeAll();
		} else {
			iconPanel = new PanelEx(new BorderLayout());
			iconPanel.setBackground(new Color(255, 255, 255, 32));
			tabBar.addTab("球员一览");
			viewPanel.add("球员一览", iconPanel);
		}

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
					ImageIcon portrait = ImageUtils.getPlayerImg(playersName[index]);
					if (portrait == null)
						portrait = new ImageIcon("./img/no_image.png");
					ButtonEx player = new ButtonEx(playersName[index], portrait);
					player.setName(playersName[index]);
					player.setOpaque(false);
					player.setBackground(new Color(255, 255, 255, 64));
					player.setForeground(Color.WHITE);
					player.setFont(UIConfig.SmallFont);
					player.setVerticalTextPosition(ButtonEx.BOTTOM);
					player.setHorizontalTextPosition(ButtonEx.CENTER);
					player.setIconTextGap(10);
					iconPage.add(player);

					player.addComponentListener(new ComponentAdapter() {
						public void componentResized(ComponentEvent e) {
							ImageIcon icon = (ImageIcon) player.getIcon();
							double icoRatio = icon.getIconWidth()
									/ (double) icon.getIconHeight();
							double btnRaito = (player.getWidth() - 20)
									/ (double) (player.getHeight() - player.getFont()
											.getSize() - player.getIconTextGap() - 10);
							int icoWidth;
							int icoHeight;
							if (icoRatio > btnRaito) { // 图片“过高”
								icoWidth = player.getWidth() - 20;
								icoHeight = (int) (icoWidth / icoRatio);
							} else {
								icoHeight = player.getHeight() - player.getFont().getSize()
										- player.getIconTextGap() - 10;
								icoWidth = (int) (icoHeight * icoRatio);
							}
							Image temp = icon.getImage().getScaledInstance(icoWidth,
									icoHeight, Image.SCALE_SMOOTH);
							icon = new ImageIcon(temp);
							player.setIcon(icon);
						}
					});

					player.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							UIEventManager.notify(UIEventType.SWITCH,
									"球员详情:" + player.getName());
						}
					});
				} else {
					PanelEx p = new PanelEx();
					p.setOpaque(false);
					iconPage.add(p);
				}
			}

			if (pageCount > 1) {
				PanelEx switchPanel = new PanelEx();
				switchPanel.setOpaque(false);
				iconPanel.add(switchPanel, BorderLayout.SOUTH);

				SwitchButtonGroup group = new SwitchButtonGroup();
				group.addSwitchListener(new SwitchListener() {
					@Override
					public void actionPerformed(SwitchEvent e) {
						((CardLayout) pagePanel.getLayout()).show(pagePanel, e.getSource()
								.getName());
					}
				});
				for (int i = 0; i < pageCount; i++) {
					SwitchButton pageIndex = new SwitchButton(String.valueOf(i + 1));
					pageIndex.setName(String.valueOf(i + 1));
					pageIndex.setBackground(new Color(255, 255, 255, 64));
					pageIndex.setForeground(Color.WHITE);
					pageIndex.setFont(UIConfig.SmallFont);
					switchPanel.add(pageIndex);
					group.addButton(pageIndex);
				}
				group.switchTo(0);
			}
		}

		if (pageCount > 1) {
			PanelEx switchPanel = new PanelEx();
			switchPanel.setOpaque(false);
			iconPanel.add(switchPanel, BorderLayout.SOUTH);

			SwitchButtonGroup group = new SwitchButtonGroup();
			group.addSwitchListener(new SwitchListener() {
				@Override
				public void actionPerformed(SwitchEvent e) {
					((CardLayout) pagePanel.getLayout()).show(pagePanel, e.getSource()
							.getName());
				}
			});
			for (int i = 0; i < pageCount; i++) {
				SwitchButton pageIndex = new SwitchButton(String.valueOf(i + 1));
				pageIndex.setName(String.valueOf(i + 1));
				pageIndex.setBackground(new Color(255, 255, 255, 64));
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
	public void setTotalDataTable(Object[][] data) {
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
	public void setAdvancedDataTable(Object[][] data) {
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
			tabBar.addTab("比赛数据");
			viewPanel.add("比赛数据", gamesTable);
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
