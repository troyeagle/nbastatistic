package njuse.ffff.ui.ver2;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.RowSorter;
import javax.swing.SortOrder;

import njuse.ffff.presenter.playerController.PlayerCompareController;
import njuse.ffff.uiservice.PlayersOverviewService;

public class PlayersOverViewPane extends OverviewPanel implements PlayersOverviewService {

	private static final long serialVersionUID = 1L;

	private static String[] totalTableHeader = new String[] { "姓名", "球队", "出场",
			"首发", "投篮", "出手", "三分", "出手", "罚球", "出手",
			"篮板", "助攻", "抢断", "盖帽", "失误", "犯规", "得分", "效率"
	};
	private static String[] avgTableHeader = new String[] { "姓名", "球队", "篮板", "助攻",
			"时间", "投篮", "三分", "罚球", "抢断", "盖帽", "失误",
			"犯规", "得分", "效率", "GmSc", "真实命中率", "投篮效率", "篮板率",
			"进攻板", "防守板", "助攻率", "抢断率", "盖帽率", "失误率", "使用率"
	};

	public PlayersOverViewPane() {
		super(avgTableHeader, totalTableHeader);

		seasonList.addItemListener(e -> {
//			new Thread(() -> {
				UIEventManager.notify(UIEventType.BUSY);
				controller.setPlayerCompareInfoForSeason(this, getSelectedSeason());
				UIEventManager.notify(UIEventType.FINISH);
//			}).start();
		});

		avgTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] loc = avgTable.getSelectedCellLocation();
				if (loc[0] >= 0 && loc[1] >= 0) {
					if (loc[1] == 1) {
						Object v = avgTable.getValueAt(loc[0], 1);
						UIEventManager.notify(UIEventType.SWITCH, "球队详情:" + v);
					} else {
						Object v = avgTable.getValueAt(loc[0], 0);
						UIEventManager.notify(UIEventType.SWITCH, "球员详情:", v);
					}
				}
			}
		});
		avgTable.getTable().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		totalTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] loc = totalTable.getSelectedCellLocation();
				if (loc[0] >= 0 && loc[1] >= 0) {
					if (loc[1] == 1) {
						Object v = totalTable.getValueAt(loc[0], 1);
						UIEventManager.notify(UIEventType.SWITCH, "球队详情:" + v);
					} else {
						Object v = totalTable.getValueAt(loc[0], 0);
						UIEventManager.notify(UIEventType.SWITCH, "球员详情", v);
					}
				}
			}
		});
		totalTable.getTable().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		initData();
	}

	private PlayerCompareController controller;

	private void initData() {
		new Thread(() -> {
			UIEventManager.notify(UIEventType.BUSY, this);
			controller = PlayerCompareController.getInstance();
			String[] seasons = controller.getAllSeasons();
			setSeasons(seasons);
			UIEventManager.notify(UIEventType.FINISH, this);
		}).start();
	}

	@Override
	public void setPlayersAvgInfo(Object[][] values, String season) {
		handleName(values);
		if (values.length > 0)
			setAvgInfo(values);
	}

	@Override
	public void setPlayersTotalInfo(Object[][] values, String season) {
		handleName(values);
		if (values.length > 0)
			setTotalInfo(values);
	}

	private void handleName(Object[][] data) {
		//		for (int i = 0; i < data.length; i++) {
		//			data[i][0] = new BasicPlayerInfo((String) data[i][0]);
		//		}
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
