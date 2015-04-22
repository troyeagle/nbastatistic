package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.Timer;

import njuse.ffff.presenter.playerController.PlayerCompareController;
import njuse.ffff.ui.component.ButtonEx;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchButton;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.ui.ver2.component.SwitchEvent;
import njuse.ffff.ui.ver2.component.SwitchListener;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.PlayersOverviewService;

public class PlayersOverViewPane extends PanelEx implements PlayersOverviewService,
		UIConfigNotifier {

	private static final long serialVersionUID = 1L;

	private PanelEx dataPanel;
	private PanelEx controlPanel;
	private PanelEx contentPanel;

	private PanelEx seasonPanel;
	private SwitchButtonGroup seasonGroup;

	private Map<String, TableView> avgTableMap;
	private Map<String, TableView> totalTableMap;
	private Map<String, PanelEx> iconPanelMap;

	private String[] totalTableHeader = new String[] { "姓名", "球队", "参赛场数",
			"先发场数", "投篮命中数", "出手数", "三分命中数", "三分出手数", "罚球命中数",
			"罚球出手数", "篮板", "助攻", "抢断", "盖帽", "失误", "犯规", "得分",
			"效率"
	};
	private String[] avgTableHeader = new String[] { "姓名", "球队", "篮板", "助攻",
			"在场时间", "投篮命中率", "三分命中率", "罚球命中率", "抢断", "盖帽", "失误",
			"犯规", "得分", "效率", "GmSc效率值", "真实命中率", "投篮效率", "篮板率",
			"进攻篮板率", "防守篮板率", "助攻率", "抢断率", "盖帽率", "失误率", "使用率"
	};
	// private final boolean[] totalIsHidden = new boolean[totalTableHeader.length];
	// private final boolean[] avgIsHidden = new boolean[avgTableHeader.length];

	private SwitchButton avgView;
	private SwitchButton totalView;
	private SwitchButton picView;
	private SwitchButtonGroup dataGroup;

	private SwitchButton settings;
	private PanelEx settingPanel;
	private PanelEx settingContent;

	public PlayersOverViewPane() {
		super(new BorderLayout());
		setOpaque(false);

		System.out.println(totalTableHeader.length);
		System.out.println(avgTableHeader.length);

		avgTableMap = new HashMap<>();
		totalTableMap = new HashMap<>();
		iconPanelMap = new HashMap<>();

		dataPanel = new PanelEx(new CardLayout());
		dataPanel.setOpaque(false);
		dataPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		avgView = new SwitchButton("平均数据", new ImageIcon("./img/btn/tableview.png"));
		avgView.setName("avgView");
		avgView.setVisible(false);
		avgView.setIconTextGap(0);
		setButtonUI(avgView);

		totalView = new SwitchButton("总数据", new ImageIcon("./img/btn/tableview.png"));
		totalView.setName("totalView");
		totalView.setVisible(false);
		totalView.setIconTextGap(2);
		setButtonUI(totalView);

		picView = new SwitchButton("球员一览", new ImageIcon("./img/btn/picview.png"));
		picView.setName("picView");
		picView.setVisible(false);
		picView.setIconTextGap(2);
		setButtonUI(picView);

		settings = new SwitchButton(new ImageIcon("./img/btn/settings.png"));
		settings.setVisible(false);
		settings.setClickCancelEnable(true);
		settings.setBackground(new Color(255, 255, 255, 64));
		PanelEx settingBtn = new PanelEx(new FlowLayout(FlowLayout.LEFT, 10, 5));
		settingBtn.setOpaque(false);
		settingBtn.add(settings);

		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (settings.isActive()) {
					picView.setVisible(true);
					contentPanel.remove(settingPanel);
					//					contentPanel.validate();
					contentPanel.repaint();
				} else {
					picView.setVisible(false);
					contentPanel.add(settingPanel, 0);
					//					contentPanel.validate();
					contentPanel.repaint();
				}
			}
		});

		settingPanel = new PanelEx(new BorderLayout(30, 0));
		settingPanel.setBackground(new Color(255, 255, 255, 200));
		settingPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		settingPanel.addMouseListener(new MouseAdapter() {
		});
		ButtonEx ok = new ButtonEx("确定");
		ok.setForeground(Color.WHITE);
		ok.setFont(UIConfig.TitleFont);
		ok.setBackground(new Color(0, 140, 230));
		ok.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.doClick();
			}
		});
		settingPanel.add(ok, BorderLayout.SOUTH);

		settingContent = new PanelEx(new CardLayout());
		settingContent.setOpaque(false);
		settingPanel.add(settingContent);

		LabelEx settingTitle = new LabelEx("设置表格列显示", LabelEx.CENTER);
		settingTitle.setFont(UIConfig.TitleFont);
		settingTitle.setOpaque(false);
		settingTitle.setForeground(Color.BLACK);
		settingPanel.add(settingTitle, BorderLayout.NORTH);

		ImageIcon checked = new ImageIcon("./img/btn/checked.png");
		ImageIcon unchecked = new ImageIcon("./img/btn/unchecked.png");
		PanelEx avgSetting = new PanelEx(new GridLayout(0, 4, 20, 10));
		avgSetting.setOpaque(false);
		for (int i = 1; i < avgTableHeader.length; i++) {
			SwitchButton btn = new SwitchButton(avgTableHeader[i], unchecked);
			btn.setName(avgTableHeader[i]);
			btn.setBackground(new Color(0, 0, 0, 160));
			btn.setHorizontalAlignment(SwitchButton.LEFT);
			btn.setForeground(Color.WHITE);
			btn.setFont(UIConfig.SubTitleFont);
			btn.setClickCancelEnable(true);
			btn.setActiveIcon(checked);
			btn.setActive(true);
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					avgTableMap.forEach((season, table) -> {
						table.setDisplay(btn.getName(), !btn.isActive());
					});
				}
			});
			avgSetting.add(btn);
		}
		PanelEx p1 = new PanelEx();
		p1.setOpaque(false);
		p1.add(avgSetting);
		settingContent.add("avgView", p1);
		PanelEx totalSetting = new PanelEx(new GridLayout(0, 4, 20, 10));
		totalSetting.setOpaque(false);
		for (int i = 1; i < totalTableHeader.length; i++) {
			SwitchButton btn = new SwitchButton(totalTableHeader[i], unchecked);
			btn.setName(totalTableHeader[i]);
			btn.setBackground(new Color(0, 0, 0, 160));
			btn.setHorizontalAlignment(SwitchButton.LEFT);
			btn.setForeground(Color.WHITE);
			btn.setFont(UIConfig.SubTitleFont);
			btn.setClickCancelEnable(true);
			btn.setActiveIcon(checked);
			btn.setActive(true);
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					totalTableMap.forEach((season, table) -> {
						table.setDisplay(btn.getName(), !btn.isActive());
					});
				}
			});
			totalSetting.add(btn);
		}
		PanelEx p2 = new PanelEx();
		p2.setOpaque(false);
		p2.add(totalSetting);
		settingContent.add("totalView", p2);

		seasonPanel = new PanelEx(new FlowLayout(FlowLayout.LEFT, 10, 5));
		seasonPanel.setOpaque(false);

		SwitchListener l = new SwitchListener() {
			@Override
			public void actionPerformed(SwitchEvent e) {
				SwitchButton type = dataGroup.getActiveButton();
				SwitchButton season = seasonGroup.getActiveButton();
				if (type != null && season != null) {
					((CardLayout) dataPanel.getLayout()).show(dataPanel, type.getName()
							+ season.getName());
					((CardLayout) settingContent.getLayout()).show(settingContent,
							type.getName());
				}
				settings.setVisible(type != picView);
			}
		};

		seasonGroup = new SwitchButtonGroup();
		seasonGroup.addSwitchListener(l);

		dataGroup = new SwitchButtonGroup();
		dataGroup.addButton(avgView);
		dataGroup.addButton(totalView);
		dataGroup.addButton(picView);
		dataGroup.addSwitchListener(l);

		PanelEx switchPanel = new PanelEx(new FlowLayout(FlowLayout.LEFT, 10, 5));
		switchPanel.setOpaque(false);
		switchPanel.add(avgView);
		switchPanel.add(totalView);
		switchPanel.add(picView);

		controlPanel = new PanelEx(new BorderLayout());
		controlPanel.setOpaque(false);
		controlPanel.add(switchPanel, BorderLayout.WEST);
		controlPanel.add(settingBtn, BorderLayout.EAST);

		contentPanel = new PanelEx(new BorderLayout());
		contentPanel.setOpaque(false);
		contentPanel.add(dataPanel);
		contentPanel.add(controlPanel, BorderLayout.NORTH);

		add(contentPanel);
		add(seasonPanel, BorderLayout.NORTH);

		setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		initData();
	}

	private void initData() {
		// TODO 获取数据？
//		PlayerCompareController.getInstance().setPlayerCompareInfoForSeason(this);

				avgTableHeader = new String[] { "c1", "c2", "c3" };
		Object[][] values = new Object[460][3];
				for (int i = 0; i < 460; i++) {
					values[i][0] = "Aaron Brooks";
					values[i][1] = 1;
					values[i][2] = "aaa";
				}
		setPlayersAvgInfo(values, "2014");
				totalTableHeader = new String[] { "c1", "c2", "c3" };
		setPlayersTotalInfo(values, "2014");
	}

	private void addSeason(String season) {
		if (seasonGroup.contains(season))
			return;

		SwitchButton seasonBtn = new SwitchButton(season + "赛季");
		seasonBtn.setOpaque(false);
		seasonBtn.setName(season);
		seasonBtn.setForeground(Color.WHITE);
		seasonBtn.setFont(UIConfig.TitleFont);
		seasonGroup.addButton(seasonBtn);
		seasonPanel.add(seasonBtn);

		if (seasonGroup.getActiveIndex() == -1)
			seasonGroup.switchTo(0);
	}

	@Override
	public void setPlayersAvgInfo(Object[][] values, String season) {
		Timer t = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIEventManager.notify(UIEventType.BUSY, this);	// 通知状态

				if (!avgTableMap.containsKey(season)) {
					TableView avgTable = new TableView(values, avgTableHeader);
					setTableUIConfig(avgTable);
					avgTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int[] loc = avgTable.getSelectedCellLocation();
							if (loc[0] >= 0 && loc[1] >= 0) {
								Object v = avgTable.getValueAt(loc[0], loc[1]);
								if (loc[1] == 1) {
									UIEventManager.notify(UIEventType.SWITCH, "球队详情:" + v);
								} else {
									UIEventManager.notify(UIEventType.SWITCH, "球员详情:" + v);
								}
							}
						}
					});

					dataPanel.add("avgView" + season, avgTable);
					avgTableMap.put(season, avgTable);

					avgView.setVisible(true);

					addSeason(season);
					if (dataGroup.getActiveIndex() == -1) {
						avgView.doClick();
					}
				} else {
					avgTableMap.get(season).setTable(values);
				}

				String[] playersName = new String[values.length];
				for (int i = 0; i < values.length; i++) {
					playersName[i] = (String) values[i][0];
				}

				setPlayersPortrait(playersName, season);

				UIEventManager.notify(UIEventType.FINISH, this);	// 完成
			}
		});
		t.setRepeats(false);
		t.start();
	}

	@Override
	public void setPlayersTotalInfo(Object[][] values, String season) {
		Timer t = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIEventManager.notify(UIEventType.BUSY, this);	// 通知状态

				if (!totalTableMap.containsKey(season)) {
					addSeason(season);

					TableView totalTable = new TableView(values, totalTableHeader);
					setTableUIConfig(totalTable);
					totalTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int[] loc = totalTable.getSelectedCellLocation();
							if (loc[0] >= 0 && loc[1] >= 0) {
								Object v = totalTable.getValueAt(loc[0], loc[1]);
								if (loc[1] == 1) {
									UIEventManager.notify(UIEventType.SWITCH, "球队详情:" + v);
								} else {
									UIEventManager.notify(UIEventType.SWITCH, "球员详情:" + v);
								}
							}
						}
					});

					dataPanel.add("totalView" + season, totalTable);
					totalTableMap.put(season, totalTable);

					totalView.setVisible(true);
					if (dataGroup.getActiveIndex() == -1) {
						totalView.doClick();
					}
				} else {
					totalTableMap.get(season).setTable(values);
				}

				UIEventManager.notify(UIEventType.FINISH, this);	// 完成
			}
		});
		t.setRepeats(false);
		t.start();
	}

	private void setPlayersPortrait(String[] playersName, String season) {

		if (iconPanelMap.containsKey(season)) {
			iconPanelMap.get(season).removeAll();
		} else {
			PanelEx iconPanel = new PanelEx(new BorderLayout());
			iconPanel.setBackground(new Color(255, 255, 255, 32));

			dataPanel.add("picView" + season, iconPanel);
			picView.setVisible(true);

			iconPanelMap.put(season, iconPanel);
			addSeason(season);
		}

		PanelEx iconPanel = iconPanelMap.get(season);

		PanelEx pagePanel = new PanelEx(new CardLayout());
		pagePanel.setOpaque(false);

		iconPanel.add(pagePanel);

		final int r = 4;
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
	public void notifyChange() {
		setUIConfig();
	}

	private void setUIConfig() {
		avgTableMap.forEach((season, table) -> {
			setTableUIConfig(table);
		});

		totalTableMap.forEach((season, table) -> {
			setTableUIConfig(table);
		});
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

	private void setButtonUI(ButtonEx button) {
		button.setBackground(new Color(255, 255, 255, 64));
		button.setForeground(Color.WHITE);
		button.setFont(UIConfig.SubTitleFont);
	}

	@Override
	public void paint(Graphics g) {
		avgTableMap
				.forEach((season, tableView) -> {
					JTable table = tableView.getTable();
					if (table != null) {
						int mode = table.getPreferredSize().getWidth() <= tableView
								.getWidth() ? JTable.AUTO_RESIZE_ALL_COLUMNS
								: JTable.AUTO_RESIZE_OFF;
						if (table.getAutoResizeMode() != mode) {
							table.setAutoResizeMode(mode);
						}
					}
				});

		totalTableMap
				.forEach((season, tableView) -> {
					JTable table = tableView.getTable();
					if (table != null) {
						int mode = table.getPreferredSize().getWidth() <= tableView
								.getWidth() ? JTable.AUTO_RESIZE_ALL_COLUMNS
								: JTable.AUTO_RESIZE_OFF;
						if (table.getAutoResizeMode() != mode) {
							table.setAutoResizeMode(mode);
						}
					}
				});
		super.paint(g);
	}
}
