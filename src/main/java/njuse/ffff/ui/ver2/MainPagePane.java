package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import njuse.ffff.presenter.hotEventController.HotEventController;
import njuse.ffff.ui.component.ButtonEx;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchButton;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.ui.ver2.component.WrapLayout;
import njuse.ffff.uiservice.SpecialViewService;

public class MainPagePane extends PanelEx implements SpecialViewService {

	private static final long serialVersionUID = 1L;

	private static final String[][] headers = {
			{ "球员名", "球队", "位置", "" },
			{ "球员名", "球队", "位置", "" },
//			{ "球员名", "球队", "", "近5场提升率" },
			{ "球队名", "联盟", "" }
	};

	private static final String[] switchStr = {
			"球员详情:", "球员详情:", "球员详情:", "球队详情:"
	};

	private static final int HotToday = 0;
	private static final int HotSeason = 1;
	//	private static final int Progress = 2;
	private static final int HotTeam = 2;

	private SwitchButtonGroup[] hotGroup;

	private PanelEx[] tableView;
	private PanelEx[] tabPanel;

	private Map<String, TableView>[] tableMap;
	private MouseListener[] listeners;

	@SuppressWarnings("unchecked")
	public MainPagePane() {
		super(new BorderLayout());
		setOpaque(false);

		PanelEx searchPanel = new PanelEx(new BorderLayout());
		searchPanel.setPreferredSize(new Dimension(960, 210));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(70, 0, 70, 0));
		searchPanel.add(createSearchField());
		searchPanel.setBackground(UIConfig.HeadPanelBgColor);
		add(searchPanel, BorderLayout.NORTH);

		tableMap = new Map[4];
		hotGroup = new SwitchButtonGroup[4];
		tableView = new PanelEx[4];
		tabPanel = new PanelEx[4];

		PanelEx topInfoPanel = new PanelEx(new GridLayout(2, 2, 20, 10));
		topInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
		topInfoPanel.setOpaque(false);
		add(topInfoPanel);

		String[] titles = { "今日热点球员 Top 5", "赛季热点球员 Top 5",
				"赛季热点球队 Top 5" };
		for (int i = 0; i < titles.length; i++) {
			tableMap[i] = new HashMap<>();
			hotGroup[i] = new SwitchButtonGroup();
			tableView[i] = new PanelEx(new CardLayout());
			tableView[i].setOpaque(false);

			int index = i;
			hotGroup[i].addSwitchListener(e ->
					((CardLayout) tableView[index].getLayout()).show(tableView[index],
							hotGroup[index].getActiveButton().getName())
					);

			LabelEx labelTitle = new LabelEx(titles[i]);
			labelTitle.setOpaque(false);
			labelTitle.setFont(UIConfig.TitleFont);
			labelTitle.setForeground(Color.BLACK);

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
			topInfoPanel.add(dataPanel);
		}

		initListener();
		initData();
	}

	private void initData() {
		HotEventController hec = HotEventController.getInstance();
		new Thread(() -> {
			hec.setBestPlayerForDayPanel(this);
		}).start();
		new Thread(() -> {
			hec.setBestPlayerForSeasonPanel(this);
		}).start();
		new Thread(() -> {
			hec.setBestTeamForSeasonPanel(this);
		}).start();
		//			hec.setProgressedPlayerPanel(this);
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
		//		String[] header = headers[Progress].clone();
		//		header[2] = type;
		//		setTable(type, data, header, Progress);
	}

	private void setTable(String type, Object[][] data, String[] header, int tableType) {
		type = type.replaceAll("场均", "").replaceAll("命中率", "");
		if (!tableMap[tableType].containsKey(type)) {
			TableView table = new TableView(data, header);
			table.getTable().setRowSorter(null);
			table.getTable().setCursor(new Cursor(Cursor.HAND_CURSOR));
			MouseListener l = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int[] p = table.getSelectedCellLocation();
					if (p[0] >= 0) {
						Object v = table.getValueAt(p[0], 0);
						UIEventManager
								.notify(UIEventType.SWITCH, switchStr[tableType] + v, v);
					}
				}
			};
			table.getTable().addMouseListener(l);
			tableView[tableType].add(type, table);
			tableMap[tableType].put(type, table);

			SwitchButton btn = new SwitchButton(type);
			btn.setName(type);
			btn.setFont(UIConfig.SubTitleFont);
			btn.setBackground(UIConfig.ThemeColor);
			btn.setForeground(Color.WHITE);

			tabPanel[tableType].add(btn);
			hotGroup[tableType].addButton(btn);
			if (hotGroup[tableType].getActiveIndex() == -1)
				hotGroup[tableType].switchTo(0);
		} else {
			tableMap[tableType].get(type).setTable(data);
		}
	}

	private boolean textFieldNotEdited = true;
	private JTextField textField;

	private PanelEx createSearchField() {
		PanelEx searchArea = new PanelEx();
		searchArea.setOpaque(false);
		searchArea.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		PanelEx fieldPanel = new PanelEx(new BorderLayout(0, 0));
		fieldPanel.setBackground(Color.WHITE);
		fieldPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));

		ButtonEx searchButton = new ButtonEx("搜索");
		searchButton.setFont(UIConfig.TitleFont);
		searchButton.setForeground(Color.WHITE);
		searchButton.setBackground(Color.GRAY);
		searchButton.setBorder(BorderFactory.createEmptyBorder(5, 40, 5, 40));
		fieldPanel.add(searchButton, BorderLayout.EAST);
		searchButton.addActionListener(e -> {
			String text = textField.getText().trim();
			if (!text.isEmpty() && !(text.equals("请输入球员名") && textFieldNotEdited)) {
				searchButton.requestFocusInWindow();
				searchAction();
			}
		});

		textField = new JTextField("请输入球员名");
		textField.setColumns(20);
		textField.setForeground(Color.LIGHT_GRAY);
		textField.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		textField.setFont(UIConfig.TitleFont);
		fieldPanel.add(textField);

		textField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				textFieldNotEdited = textField.getText().isEmpty();
				if (textFieldNotEdited) {
					textField.setText("请输入球员名");
					textField.setForeground(Color.LIGHT_GRAY);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().equals("请输入球员名") && textFieldNotEdited) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchButton.doClick();
				}
			}
		});

		searchArea.add(fieldPanel);

		return searchArea;
	}

	private void searchAction() {
		String text = textField.getText().trim();
		if (!text.isEmpty()) {
			UIEventManager.notify(UIEventType.SEARCH, text);
		}
	}
}
