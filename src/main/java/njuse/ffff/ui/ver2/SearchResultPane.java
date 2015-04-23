package njuse.ffff.ui.ver2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;

import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.SearchResultService;

public class SearchResultPane extends PanelEx implements SearchResultService {

	private static final long serialVersionUID = 1L;

	private TableView teams;
	private TableView players;
	private LabelEx noResult;

	public SearchResultPane() {
		super(new GridLayout(1, 0, 40, 40));
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

		teams = new TableView(new Object[0][], new String[] { "球队名" });
		teams.getTable().setCursor(new Cursor(Cursor.HAND_CURSOR));
		setTableUIConfig(teams);
		teams.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] p = teams.getSelectedCellLocation();
				if (p[0] >= 0) {
					Object v = teams.getValueAt(p[0], 0);
					UIEventManager.notify(UIEventType.SWITCH, "球队详情:" + v);
				}
			}
		});
		players = new TableView(new Object[0][], new String[] { "球员名" });
		players.getTable().setCursor(new Cursor(Cursor.HAND_CURSOR));
		setTableUIConfig(players);
		teams.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] p = players.getSelectedCellLocation();
				if (p[0] >= 0) {
					Object v = players.getValueAt(p[0], 0);
					UIEventManager.notify(UIEventType.SWITCH, "球员详情:" + v);
				}
			}
		});

		noResult = new LabelEx("没有符合条件的搜索结果", LabelEx.CENTER);
		noResult.setOpaque(true);
		noResult.setFont(UIConfig.TitleFont);
		noResult.setBackground(new Color(255, 255, 255, 192));
		noResult.setForeground(Color.BLACK);
		add(teams);
		add(noResult);
		add(players);
	}

	@Override
	public void setSearchResult(String[] teamNames, String[] playerNames) {
		int teamCount = teamNames.length;
		int playerCount = playerNames.length;
		if (teamCount == 0) {
			remove(teams);
		} else {
			Object[][] teamData = new Object[teamCount][1];
			for (int i = 0; i < teamCount; i++) {
				teamData[i][0] = teamNames[i];
			}
			teams.setTable(teamData);
			add(teams);
		}

		if (playerCount == 0) {
			remove(players);
		} else {
			Object[][] playerData = new Object[playerCount][1];
			for (int i = 0; i < playerCount; i++) {
				playerData[i][0] = playerNames[i];
			}
			players.setTable(playerData);
			add(players);
		}

		if (teamCount + playerCount == 0) {
			add(noResult);
		} else {
			remove(noResult);
		}
		validate();
		repaint();
	}

	private void setTableUIConfig(TableView table) {
		table.setTableFont(UIConfig.ContentFont);
		table.setHeaderFont(UIConfig.ContentFont);
		table.setRowHeight(UIConfig.ContentFont.getSize() + 5);
		table.setForeground(Color.WHITE);
		table.setSelectionBgColor(UIConfig.TableSelectionBgColor);
		table.setSelectionFgColor(UIConfig.TableSelectionFgColor);
		table.setTableFgColor(UIConfig.TableFgColor);
		table.setHeaderBgColor(UIConfig.TableHeaderBgColor);
		table.setHeaderFgColor(UIConfig.TableHeaderFgColor);
	}
}
