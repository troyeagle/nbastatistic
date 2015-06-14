package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;

import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.TableView;

public class GameLogPanel extends PanelEx {
	private static final long serialVersionUID = 1L;

	private static final String[] gameHeader = { "比赛日期", "比赛对阵" };

	private JComboBox<String> seasonList;

	private TableView gamesTable;

	public GameLogPanel() {
		super(new BorderLayout(20, 0));
		setOpaque(false);

		seasonList = new JComboBox<String>();
		seasonList.setFont(UIConfig.ContentFont);
		LabelEx seasonLabel = new LabelEx("赛季：");
		seasonLabel.setFont(UIConfig.ContentFont);
		PanelEx seasonPanel = new PanelEx(new FlowLayout(FlowLayout.LEFT, 10, 5));
		seasonPanel.setOpaque(false);
		seasonPanel.add(seasonLabel);
		seasonPanel.add(seasonList);

		add(seasonPanel, BorderLayout.NORTH);

		gamesTable = new TableView(new Object[0][0], gameHeader);

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

		add(gamesTable);
	}

	public void setData(Object[][] data) {
		gamesTable.setTable(data);
	}

	public void setSeasons(String[] seasons) {
		String selectedSeason = getSelectedSeason();
		seasonList.removeAllItems();
		boolean existed = false;
		for (String season : seasons) {
			seasonList.addItem(season);
			if (!existed && selectedSeason != null && season.equals(selectedSeason)) {
				seasonList.setSelectedItem(season);
				existed = true;
			}
		}
		if (!existed) {
			seasonList.setSelectedIndex(0);
		}
	}

	public String getSelectedSeason() {
		return (String) seasonList.getSelectedItem();
	}

	public JComboBox<String> getSeasonList() {
		return seasonList;
	}
}
