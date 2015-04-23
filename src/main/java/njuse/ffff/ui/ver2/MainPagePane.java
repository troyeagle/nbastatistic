package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Timer;

import njuse.ffff.presenter.hotEventController.HotEventController;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchButton;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.ui.ver2.component.SwitchEvent;
import njuse.ffff.ui.ver2.component.SwitchListener;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.ui.ver2.component.WrapLayout;
import njuse.ffff.uiservice.SpecialViewService;

public class MainPagePane extends PanelEx implements SpecialViewService {

	private static final long serialVersionUID = 1L;

	private static final String[][] headers = {
			{ "球员名称", "所属球队", "球员位置", "" },
			{ "球员名称", "所属球队", "球员位置", "" },
			{ "球员姓名", "所属球队", "", "近5场提升率" },
			{ "球队名称", "所属联盟", "" }
	};

	private static final String[] switchStr = {
			"球员详情:", "球员详情:", "球员详情:", "球队详情:"
	};

	private static final int HotToday = 0;
	private static final int HotSeason = 1;
	private static final int Progress = 2;
	private static final int HotTeam = 3;

	private SwitchButtonGroup[] hotGroup;

	private PanelEx[] tableView;
	private PanelEx[] tabPanel;

	private Map<String, TableView>[] tableMap;
	private MouseListener[] listeners;

	@SuppressWarnings("unchecked")
	public MainPagePane() {
		super(new GridLayout(2, 2, 60, 60));
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

		tableMap = new Map[4];
		hotGroup = new SwitchButtonGroup[4];
		tableView = new PanelEx[4];
		tabPanel = new PanelEx[4];

		String[] titles = { "今日热点球员 Top 5", "赛季热点球员 Top 5",
				"进步最快球员 Top 5", "赛季热点球队 Top 5" };
		for (int i = 0; i < 4; i++) {
			tableMap[i] = new HashMap<>();
			hotGroup[i] = new SwitchButtonGroup();
			tableView[i] = new PanelEx(new CardLayout());
			tableView[i].setOpaque(false);

			int index = i;
			hotGroup[i].addSwitchListener(new SwitchListener() {
				@Override
				public void actionPerformed(SwitchEvent e) {
					((CardLayout) tableView[index].getLayout()).show(tableView[index],
							hotGroup[index].getActiveButton().getName());
				}
			});

			LabelEx labelTitle = new LabelEx(titles[i]);
			labelTitle.setOpaque(false);
			labelTitle.setFont(UIConfig.TitleFont);
			labelTitle.setForeground(Color.WHITE);

			tabPanel[i] = new PanelEx(new WrapLayout(WrapLayout.LEFT));
			tabPanel[i].setOpaque(false);

			PanelEx tablePanel = new PanelEx(new BorderLayout());
			tablePanel.setOpaque(false);
			tablePanel.add(tabPanel[i], BorderLayout.NORTH);
			tablePanel.add(tableView[i]);

			PanelEx dataPanel = new PanelEx(new BorderLayout());
			dataPanel.setOpaque(false);
			dataPanel.add(labelTitle, BorderLayout.NORTH);
			dataPanel.add(tablePanel);
			add(dataPanel);
		}

		initListener();
		initData();
	}

	private void initData() {
		Timer t = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HotEventController hec = HotEventController.getInstance();
				hec.setBestPlayerForDayPanel(MainPagePane.this);
				hec.setBestPlayerForSeasonPanel(MainPagePane.this);
				hec.setBestTeamForSeasonPanel(MainPagePane.this);
				hec.setProgressedPlayerPanel(MainPagePane.this);
			}
		});
		t.setRepeats(false);
		t.start();
	}

	private void initListener() {
		listeners = new MouseListener[4];
		listeners[0] = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		};
	}

	@Override
	public void setHotPlayerToday(String type, Object[][] data) {
		String[] header = headers[HotToday].clone();
		header[3] = type;
		setTable(type, data, header, HotToday);
	}

	@Override
	public void setHotPlayerSeason(String type, Object[][] data) {
		String[] header = headers[HotSeason].clone();
		header[3] = type;
		setTable(type, data, header, HotSeason);
	}

	@Override
	public void setHotTeamSeason(String type, Object[][] data) {
		String[] header = headers[HotTeam].clone();
		header[2] = type;
		setTable(type, data, header, HotTeam);
	}

	@Override
	public void setProgressPlayer(String type, Object[][] data) {
		String[] header = headers[Progress].clone();
		header[2] = type;
		setTable(type, data, header, Progress);
	}

	private TableView setTable(String type, Object[][] data, String[] header, int tableType) {
		if (!tableMap[tableType].containsKey(type)) {
			TableView table = new TableView(data, header);
			setTableUIConfig(table);
			table.getTable().setCursor(new Cursor(Cursor.HAND_CURSOR));
			MouseListener l = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int[] p = table.getSelectedCellLocation();
					if (p[0] >= 0) {
						Object v = table.getValueAt(p[0], 0);
						UIEventManager.notify(UIEventType.SWITCH, switchStr[tableType] + v);
					}
				}
			};
			table.getTable().addMouseListener(l);
			tableView[tableType].add(type, table);
			tableMap[tableType].put(type, table);

			SwitchButton btn = new SwitchButton(type);
			btn.setName(type);
			btn.setFont(UIConfig.SubTitleFont);
			btn.setBackground(new Color(255, 255, 255, 64));
			btn.setForeground(Color.WHITE);

			tabPanel[tableType].add(btn);
			hotGroup[tableType].addButton(btn);
			if (hotGroup[tableType].getActiveIndex() == -1)
				hotGroup[tableType].switchTo(0);
		} else {
			tableMap[tableType].get(type).setTable(data);
		}

		return null;
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
