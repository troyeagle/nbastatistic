package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import njuse.ffff.presenter.matchController.MatchInfoController;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.TableUtils;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.MatchViewService;
import njuse.ffff.util.TeamNameAndAbbr;

public class MatchViewPane extends PanelEx implements MatchViewService {

	private static final long serialVersionUID = 1L;

	private static final String[] tableHeader = { "球员", "首发", "时间", "投篮",
			"命中", "出手", "三分", "命中", "出手", "罚球", "命中", "出手", "真实命中",
			"篮板", "前场", "后场", "助攻", "抢断", "盖帽", "失误", "犯规", "得分"
	};

	private LabelEx[] labelIcon;
	private LabelEx[] labelName;
	private TableView tableScore;
	private LabelEx[] labelFinal;

	private TableView[] tableData;
	private LabelEx[] tableName;

	public MatchViewPane() {
		super(new BorderLayout());
		setOpaque(false);

		PanelEx matchInfoPanel = new PanelEx(null);
		matchInfoPanel.setPreferredSize(new Dimension(0, 210));
		matchInfoPanel.setBackground(UIConfig.HeadPanelBgColor);
		add(matchInfoPanel, BorderLayout.NORTH);

		PanelEx scorePanel = createScorePanel();
		matchInfoPanel.add(scorePanel);

		labelIcon = new LabelEx[2];
		labelName = new LabelEx[2];
		labelFinal = new LabelEx[2];
		tableData = new TableView[2];
		tableName = new LabelEx[2];

		PanelEx panelData = new PanelEx(new GridLayout(2, 1, 0, 5));
		panelData.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		panelData.setOpaque(false);

		// 滚~(
		JViewport dataView = new JViewport();
		dataView.setOpaque(false);
		dataView.add(panelData);
		JScrollPane dataPane = new JScrollPane();
		dataPane.getVerticalScrollBar().setUnitIncrement(20);
		dataPane.setOpaque(false);
		dataPane.setBorder(BorderFactory.createEmptyBorder());
		dataPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dataPane.setViewport(dataView);
		add(dataPane);

		// ：）
		LabelEx maohao = new LabelEx("：");
		maohao.setHorizontalAlignment(LabelEx.CENTER);
		maohao.setBounds(600, 40, 80, 40);
		maohao.setFont(UIConfig.TitleFont);
		maohao.setForeground(Color.BLACK);
		matchInfoPanel.add(maohao);

		for (int i = 0; i < 2; i++) {
			int align = i == 0 ? LabelEx.RIGHT : LabelEx.LEFT;

			labelIcon[i] = new LabelEx();
			labelIcon[i].setBounds(50 + 1020 * i, 40, 160, 160);

			LabelEx name = new LabelEx();
			labelName[i] = name;
			name.setBounds(250 + i * 580, 40, 200, 40);
			name.setCursor(new Cursor(Cursor.HAND_CURSOR));
			name.setFont(UIConfig.TitleFont);
			name.setForeground(Color.BLACK);
			name.setHorizontalAlignment(i == 0 ? LabelEx.LEFT : LabelEx.RIGHT);
			name.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					UIEventManager.notify(UIEventType.SWITCH, "球队详情:" + name.getText());
				}
			});

			LabelEx labelType = new LabelEx(i == 0 ? "客队" : "主队");
			labelType.setBounds(260 + i * 650, 80, 110, 20);
			labelType.setHorizontalAlignment(i == 0 ? LabelEx.LEFT : LabelEx.RIGHT);
			labelType.setFont(UIConfig.SmallFont);
			labelType.setForeground(Color.BLACK);

			labelFinal[i] = new LabelEx();
			labelFinal[i].setBounds(480 + 260 * i, 40, 50, 40);
			labelFinal[i].setHorizontalAlignment(align);
			labelFinal[i].setFont(UIConfig.TitleFont);
			labelFinal[i].setForeground(Color.BLACK);

			matchInfoPanel.add(labelIcon[i]);
			matchInfoPanel.add(name);
			matchInfoPanel.add(labelType);
			matchInfoPanel.add(labelFinal[i]);

			Object[][] emptyValue = new Object[0][0];
			tableData[i] = new TableView(emptyValue, tableHeader);
			tableData[i].setHeaderFont(UIConfig.SmallFont);
			TableView t = tableData[i];
			t.getTable().setCursor(new Cursor(Cursor.HAND_CURSOR));
			t.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int[] p = t.getSelectedCellLocation();
					if (p[0] >= 0) {
						Object v = t.getValueAt(p[0], 0);
						UIEventManager.notify(UIEventType.SWITCH, "球员详情:" + v);
					}
				}
			});

			tableName[i] = new LabelEx("Team" + (2 - i));
			tableName[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
			tableName[i].setOpaque(true);
			tableName[i].setBackground(Color.DARK_GRAY.brighter().brighter());
			tableName[i].setForeground(Color.WHITE);
			tableName[i].setFont(UIConfig.SubTitleFont);

			PanelEx tableArea = new PanelEx(new BorderLayout());
			tableArea.setOpaque(false);
			tableArea.add(tableData[i].getTable().getTableHeader(), BorderLayout.NORTH);
			tableArea.add(tableData[i].getTable());
			PanelEx tablePanel = new PanelEx(new BorderLayout());
			tablePanel.setOpaque(false);
			tablePanel.add(tableName[i], BorderLayout.NORTH);
			tablePanel.add(tableArea);
			panelData.add(tablePanel);
		}
	}

	private PanelEx createScorePanel() {
		PanelEx scorePanel = new PanelEx(new BorderLayout());
		scorePanel.setOpaque(false);

		String[] header = new String[16];
		header[0] = "";
		header[15] = "总分";
		for (int i = 1; i <= 4; i++)
			header[i] = "第" + i + "节";
		for (int i = 1; i <= 10; i++)
			header[i + 4] = "加时" + i;

		String[][] teams = new String[2][16];
		for (int i = 0; i < 2; i++) {
			teams[i][0] = "team" + (i + 1);
			for (int j = 1; j <= 15; j++)
				teams[i][j] = "0";
		}

		tableScore = new TableView(teams, header);
		JTable table = tableScore.getTable();
		table.setRowSelectionAllowed(false);
		table.setIntercellSpacing(new Dimension());
		table.getTableHeader().setBackground(Color.DARK_GRAY);
		table.getTableHeader().setForeground(Color.WHITE);
		table.setBackground(Color.GRAY.brighter());
		table.setForeground(Color.BLACK);
		table.setRowSorter(null);
		scorePanel.add(tableScore);

		int height = (int) (table.getRowHeight() * 2 + table.getTableHeader()
				.getPreferredSize().getHeight() + 2);
		scorePanel.setBounds(230, 120, 820, height);

		return scorePanel;
	}

	public void setMatch(String date, String team) {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		Date time;
		try {
			time = df.parse(date);
		} catch (Exception e1) {
			time = new Date();
		}
		MatchInfoController.getInstance().setMatchInfoPanel(MatchViewPane.this, time, team);
	}

	@Override
	public void setGuestTeamInfo(String name, int[] score, Object[][] data, int[][] dirty) {
		setTeamInfo(name, score, data, dirty, true);
	}

	@Override
	public void setHostTeamInfo(String name, int[] score, Object[][] data, int[][] dirty) {
		setTeamInfo(name, score, data, dirty, false);
	}

	private void setTeamInfo(String name, int[] score, Object[][] data, int[][] dirty,
			boolean isGuest) {
		int index = isGuest ? 0 : 1;
		String abbr = TeamNameAndAbbr.getInstance().getAbbr(name);

		ImageIcon icon = ImageUtilsEx.getTeamIcon(name, ImageUtilsEx.L);
		labelIcon[index].setIcon(icon);

		labelName[index].setText(name);
		labelFinal[index].setText(String.valueOf(score[0]));

		JTable scoreTable = tableScore.getTable();
		int c = scoreTable.getColumnCount();
		scoreTable.setValueAt(abbr, index, 0);
		scoreTable.setValueAt(String.valueOf(score[0]), index, c - 1);

		// 处理加时
		if (c - 1 > score.length) {
			for (int i = score.length - 4; i <= c - 6; i++)
				tableScore.hide("加时" + i);
		} else {
			for (int i = score.length - 5; i > c - 6; i--)
				tableScore.show("加时" + i);
		}

		for (int i = 1; i <= score.length - 1; i++)
			scoreTable.setValueAt(String.valueOf(score[i]), index, i);

		tableName[index].setText(name);
		icon = ImageUtilsEx.getTeamIcon(abbr, ImageUtilsEx.S);
		tableName[index].setIcon(icon);
		tableData[index].setTable(data);

		// autoResize
		JTable table = tableData[index].getTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableUtils.FitTableColumns(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}
}
