package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

import njuse.ffff.presenter.teamController.TeamCompareController;
import njuse.ffff.ui.component.ButtonEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchButton;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.uiservice.TeamsOverviewService;

public class TeamsOverViewPanel extends OverviewPanel implements TeamsOverviewService {

	private static final long serialVersionUID = 1L;

	private static String[] totalTableHeader = new String[] { "队名", "胜场", "负场", "命中",
			"出手", "三分", "出手", "罚球", "出手", "进攻板",
			"防守板", "篮板", "助攻", "抢断", "盖帽", "失误", "犯规", "得分"
	};
	private static String[] avgTableHeader = new String[] { "队名", "胜场", "负场",
			"总命中数", "总出手", "命中率", "三分", "出手", "命中率", "罚球", "出手", "命中率",
			"进攻篮板", "进攻篮板率", "防守篮板", "防守篮板率", "篮板", "助攻", "助攻率",
			"抢断", "抢断率", "盖帽", "失误", "犯规", "得分", "进攻回合", "进攻效率", "防守效率"
	};

	private PanelEx iconPanel;

	private SwitchButton picView;

	private TeamCompareController controller;

	public TeamsOverViewPanel() {
		super(avgTableHeader, totalTableHeader);

		seasonList.addItemListener(e -> {
//			new Thread(() -> {
				UIEventManager.notify(UIEventType.BUSY);
				controller.setTeamCompareInfoForSeason(this, getSelectedSeason());
				UIEventManager.notify(UIEventType.FINISH);
//			}).start();
		});

		picView = new SwitchButton("球队一览", new ImageIcon("./img/btn/picview.png"));
		picView.setName("picView");
		setButtonUI(picView);

		dataGroup.addButton(picView);
		switchPanel.add(picView);

		iconPanel = new PanelEx(new BorderLayout());
		iconPanel = new PanelEx(new BorderLayout());
		iconPanel.setBackground(new Color(255, 255, 255, 32));
		dataPanel.add("picView", iconPanel);

		Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		avgTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] loc = avgTable.getSelectedCellLocation();
				if (loc[0] >= 0 && loc[1] >= 0) {
					Object v = avgTable.getValueAt(loc[0], 0);
					UIEventManager.notify(UIEventType.SWITCH, "球队详情:" + v);
				}
			}
		});
		avgTable.getTable().setCursor(handCursor);

		totalTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] loc = totalTable.getSelectedCellLocation();
				if (loc[0] >= 0 && loc[1] >= 0) {
					Object v = totalTable.getValueAt(loc[0], 0);
					UIEventManager.notify(UIEventType.SWITCH, "球队详情:" + v);
				}
			}
		});
		totalTable.getTable().setCursor(handCursor);

		initData();
	}

	private void initData() {
		new Thread(() -> {
			UIEventManager.notify(UIEventType.BUSY, this);
			controller = TeamCompareController.getInstance();
			String[] seasons = controller.getAllSeasons();
			this.setSeasons(seasons);
			UIEventManager.notify(UIEventType.FINISH, this);
		}).start();
	}

	@Override
	public void setTeamsAvgInfo(Object[][] values, String season) {
		if (values.length > 0) {
			setAvgInfo(values);

			String[] teamName = new String[values.length];
			String[] teamAbbr = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				teamName[i] = values[i][0].toString();
				teamAbbr[i] = values[i][0].toString();
			}

			setTeamsIcon(teamName, teamAbbr, season);
		}
	}

	//	private void handleData(Object[][] data) {
	//		for (int i = 0; i < data.length; i++) {
	////			data[i][3] 
	//		}
	//	}

	@Override
	public void setTeamsTotalInfo(Object[][] values, String season) {
		if (values.length > 0) {
			setTotalInfo(values);
		}
	}

	private void setTeamsIcon(String[] teamName, String[] teamAbbr, String season) {
		iconPanel.removeAll();

		PanelEx pagePanel = new PanelEx(new CardLayout());
		pagePanel.setOpaque(false);

		iconPanel.add(pagePanel);

		final int r = 2;
		final int c = 5;
		int pageCount;
		for (pageCount = 0; pageCount * r * c < teamAbbr.length; pageCount++) {
			PanelEx iconPage = new PanelEx(new GridLayout(r, c));
			iconPage.setOpaque(false);
			pagePanel.add(String.valueOf(pageCount + 1), iconPage);

			for (int i = 0; i < r * c; i++) {
				int index = pageCount * r * c + i;
				if (index < teamAbbr.length) {
					ImageIcon portrait = ImageUtilsEx.getTeamIcon(teamAbbr[index],
							ImageUtilsEx.L);
					ButtonEx teamBtn = new ButtonEx(teamName[index], portrait);
					teamBtn.setName(teamName[index]);
					teamBtn.setOpaque(false);
					teamBtn.setBackground(new Color(255, 255, 255, 64));
					teamBtn.setForeground(Color.BLACK);
					teamBtn.setFont(UIConfig.SubTitleFont);
					teamBtn.setVerticalTextPosition(ButtonEx.BOTTOM);
					teamBtn.setHorizontalTextPosition(ButtonEx.CENTER);
					teamBtn.setIconTextGap(10);
					iconPage.add(teamBtn);

					teamBtn.addActionListener(e -> {
						UIEventManager.notify(UIEventType.SWITCH,
								"球队详情:" + teamBtn.getName());
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
			group.addSwitchListener(e ->
					((CardLayout) pagePanel.getLayout()).show(pagePanel, e.getSource()
							.getName())
					);
			for (int i = 0; i < pageCount; i++) {
				SwitchButton pageIndex = new SwitchButton(String.valueOf(i + 1));
				pageIndex.setName(String.valueOf(i + 1));
				setButtonUI(pageIndex);
				pageIndex.setFont(UIConfig.SmallFont);
				switchPanel.add(pageIndex);
				group.addButton(pageIndex);
			}
			group.switchTo(0);
		}
		iconPanel.validate();
	}

	private List<RowSorter.SortKey> totalSorter = Arrays.asList(
			new RowSorter.SortKey(Arrays.asList(totalTableHeader).indexOf("得分"),
					SortOrder.DESCENDING));

	@Override
	public void setTotalInfo(Object[][] data) {
		super.setTotalInfo(data);
		totalTable.getTable().getRowSorter().setSortKeys(totalSorter);
	}

	private List<RowSorter.SortKey> avgSorter = Arrays.asList(
			new RowSorter.SortKey(Arrays.asList(avgTableHeader).indexOf("得分"),
					SortOrder.DESCENDING));

	@Override
	public void setAvgInfo(Object[][] data) {
		super.setAvgInfo(data);
		avgTable.getTable().getRowSorter().setSortKeys(avgSorter);
	}
}
