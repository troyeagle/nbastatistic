package njuse.ffff.ui.ver2;

import static java.lang.String.valueOf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import njuse.ffff.live.LiveTest;
import njuse.ffff.live.PlayByPlayMessages;
import njuse.ffff.live.PlayerLiveInfo;
import njuse.ffff.live.TeamLiveInfo;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.util.TeamNameAndAbbr;

public class GameLivePanel extends PanelEx {

	private static final long serialVersionUID = 1L;

	private static final String[][] tableHeader = {
			{ "首发球员", "位置", "出场时间", "投篮(中-投)", "三分(中-投)", "罚球(中-投)",
					"前篮板", "后篮板", "总篮板", "助攻", "抢断", "盖帽", "失误", "犯规",
					"得分" },
			{ "替补球员", "位置", "出场时间", "投篮(中-投)", "三分(中-投)", "罚球(中-投)",
					"前篮板", "后篮板", "总篮板", "助攻", "抢断", "盖帽", "失误", "犯规",
					"得分" }
	};

	private LabelEx[] labelIcon;
	private LabelEx[] labelName;
	private TableView tableScore;
	private LabelEx[] labelScore;
	private LabelEx finished;

	private TableView[][] tableData;
	private LabelEx[] tableName;

	private String[] teamsDataNames = { "得分", "助攻", "篮板", "前-后篮板", "抢断",
			"盖帽", "投篮(中-投)", "三分(中-投)", "罚球(中-投)", "失误", "犯规",
			"快攻得分", "内线得分", "对方失误得分", "最大领先得分", "技术犯规",
			"恶意犯规", "六犯离场", "被逐离场" };
	private LabelEx[][] teamsData;
	private LabelEx[] teamsName;

	private TableView liveTable;

	public GameLivePanel() {
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
		labelScore = new LabelEx[2];
		tableData = new TableView[2][2];
		tableName = new LabelEx[2];

		teamsData = new LabelEx[2][19];
		teamsName = new LabelEx[2];

		liveTable = new TableView(null, new String[] { "", "消息", "比分" });
		//		liveTable.getTable().setBackground(new Color(232, 242, 254));
		liveTable.getTable().getColumnModel().getColumn(0).setMaxWidth(70);
		liveTable.getTable().getColumnModel().getColumn(0).setMinWidth(70);
		liveTable.getTable().getColumnModel().getColumn(2).setMaxWidth(100);
		liveTable.getTable().getColumnModel().getColumn(2).setMinWidth(100);
		liveTable.getTable().setRowSorter(null);
		liveTable.getTable().setRowHeight(30);
		liveTable.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		PanelEx panelData = new PanelEx(new BorderLayout(0, 10));
		panelData.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelData.setOpaque(false);

		PanelEx liveAreaPanel = new PanelEx(new BorderLayout());
		liveAreaPanel.setOpaque(false);
		liveAreaPanel.add(liveTable);
		panelData.add(liveAreaPanel, BorderLayout.NORTH);

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

		LabelEx maohao = new LabelEx("：");
		maohao.setHorizontalAlignment(LabelEx.CENTER);
		maohao.setBounds(600, 40, 80, 40);
		maohao.setFont(UIConfig.TitleFont);
		maohao.setForeground(Color.BLACK);
		matchInfoPanel.add(maohao);

		for (int i = 0; i < 2; i++) {
			// 球队总数据
			teamsName[i] = new LabelEx("team" + (2 - i));
			teamsName[i].setFont(UIConfig.ContentFont);
			teamsName[i].setBorder(BorderFactory.createEmptyBorder(2, 5, 3, 5));
			PanelEx labelsPanel = new PanelEx(new GridLayout(19, 2));
			labelsPanel.setOpaque(false);
			for (int index = 0; index < 19; index++) {
				LabelEx label = new LabelEx(teamsDataNames[index]);
				label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
				label.setFont(UIConfig.SmallFont);
				labelsPanel.add(label);
				LabelEx data = new LabelEx("0");
				data.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
				data.setFont(UIConfig.SmallFont);
				data.setHorizontalAlignment(LabelEx.RIGHT);
				labelsPanel.add(data);
				teamsData[i][index] = data;
			}
			PanelEx teamDataArea = new PanelEx(new BorderLayout());
			teamDataArea.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
			teamDataArea.add(teamsName[i], BorderLayout.NORTH);
			teamDataArea.add(labelsPanel);
			liveAreaPanel.add(teamDataArea, i == 0 ? BorderLayout.WEST : BorderLayout.EAST);

			// 表格区
			int align = i == 0 ? LabelEx.RIGHT : LabelEx.LEFT;

			labelIcon[i] = new LabelEx();
			labelIcon[i].setBounds(50 + 1020 * i, 40, 160, 160);

			LabelEx name = new LabelEx("Team" + (2 - i));
			labelName[i] = name;
			name.setBounds(250 + i * 670, 40, 110, 40);
			name.setFont(UIConfig.TitleFont);
			name.setForeground(Color.BLACK);
			name.setHorizontalAlignment(i == 0 ? LabelEx.LEFT : LabelEx.RIGHT);

			LabelEx labelType = new LabelEx(i == 0 ? "客队" : "主队");
			labelType.setBounds(260 + i * 650, 80, 110, 20);
			labelType.setHorizontalAlignment(i == 0 ? LabelEx.LEFT : LabelEx.RIGHT);
			labelType.setFont(UIConfig.SmallFont);
			labelType.setForeground(Color.BLACK);

			labelScore[i] = new LabelEx("0");
			labelScore[i].setBounds(450 + 320 * i, 40, 50, 40);
			labelScore[i].setHorizontalAlignment(align);
			labelScore[i].setFont(UIConfig.TitleFont);
			labelScore[i].setForeground(Color.BLACK);

			matchInfoPanel.add(labelIcon[i]);
			matchInfoPanel.add(name);
			matchInfoPanel.add(labelType);
			matchInfoPanel.add(labelScore[i]);

			PanelEx tablePanel = new PanelEx(new BorderLayout());
			tablePanel.setOpaque(false);
			for (int j = 0; j < 2; j++) {
				TableView tableView = new TableView(null, tableHeader[j]);
				tableData[i][j] = tableView;

				JTable table = tableView.getTable();
				table.setRowSorter(null);
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

				PanelEx tableArea = new PanelEx(new BorderLayout());
				tableArea.setOpaque(false);
				tableArea.add(tableView.getTable().getTableHeader(), BorderLayout.NORTH);
				tableArea.add(tableView.getTable());
				tablePanel.add(tableArea, j == 0 ? BorderLayout.NORTH : BorderLayout.CENTER);
			}
			finished = new LabelEx("已结束");
			finished.setBounds(600, 80, 80, 20);
			finished.setHorizontalAlignment(LabelEx.CENTER);
			finished.setFont(UIConfig.SmallFont);
			//			matchInfoPanel.add(finished);

			tableName[i] = new LabelEx("Team" + (2 - i));
			tableName[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
			tableName[i].setOpaque(true);
			tableName[i].setBackground(Color.DARK_GRAY.brighter().brighter());
			tableName[i].setForeground(Color.WHITE);
			tableName[i].setFont(UIConfig.SubTitleFont);

			PanelEx teamPanel = new PanelEx(new BorderLayout());
			teamPanel.setOpaque(false);
			teamPanel.add(tableName[i], BorderLayout.NORTH);
			teamPanel.add(tablePanel);

			panelData.add(teamPanel, i == 0 ? BorderLayout.CENTER : BorderLayout.SOUTH);
		}

		// 刷新
		Timer timer = initTimer();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				if (!timer.isRunning())
					timer.start();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				timer.stop();
			}
		});
		timer.start();
	}

	private PanelEx createScorePanel() {
		PanelEx scorePanel = new PanelEx(new BorderLayout());
		scorePanel.setOpaque(false);

		String[] header = new String[15];
		header[0] = "";
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

	//	private void setTeamInfo(String name, int[] score, Object[][] data, boolean isGuest) {
	//
	//		int index = isGuest ? 0 : 1;
	//		String abbr = TeamNameAndAbbr.getInstance().getAbbr(name);
	//
	//		ImageIcon icon = ImageUtilsEx.getTeamIcon(name, ImageUtilsEx.L);
	//		labelIcon[index].setIcon(icon);
	//
	//		labelName[index].setText(name);
	//		labelFinal[index].setText(String.valueOf(score[0]));
	//
	//		JTable scoreTable = tableScore.getTable();
	//		int c = scoreTable.getColumnCount();
	//		scoreTable.setValueAt(abbr, index, 0);
	//		scoreTable.setValueAt(String.valueOf(score[0]), index, c - 1);
	//
	//		// 处理加时
	//		if (c - 1 > score.length) {
	//			for (int i = score.length - 4; i <= c - 6; i++)
	//				tableScore.hide("加时" + i);
	//		} else {
	//			for (int i = score.length - 5; i > c - 6; i--)
	//				tableScore.show("加时" + i);
	//		}
	//
	//		for (int i = 1; i <= score.length - 1; i++)
	//			scoreTable.setValueAt(String.valueOf(score[i]), index, i);
	//
	//		tableName[index].setText(name);
	//		icon = ImageUtilsEx.getTeamIcon(abbr, ImageUtilsEx.S);
	//		tableName[index].setIcon(icon);
	//
	//		setTable(index, 0, data);
	//	}

	private void setTable(int index1, int index2, Object[][] data) {
		JTable table = tableData[index1][index2].getTable();
		int rowCount = data.length;
		if (table.getRowCount() < rowCount) {
			((DefaultTableModel) tableData[index1][index2].getTable().getModel())
					.setDataVector(data, tableHeader[index2]);
		} else {
			for (int row = 0; row < rowCount; row++) {
				int columnCount = data[row].length;
				for (int column = 0; column < columnCount; column++) {
					table.setValueAt(data[row][column], row, column);
				}
			}
		}
	}

	private Icon getTeamIcon(String name, char size) {
		String enName = "";
		switch (name) {
		case "勇士":
			enName = "Warriors";
			break;
		case "骑士":
			enName = "Cavaliers";
			break;
		}
		if (!enName.isEmpty()) {
			return ImageUtilsEx.getTeamIcon(enName, size);
		} else {
			return null;
		}
	}

	private void setTeamInfo(int index, String name, int totalScore, Object[] data,
			int[] scores) {
		labelName[index].setText(name);
		tableName[index].setText(name);
		teamsName[index].setText(name);

		Icon icon = getTeamIcon(name, ImageUtilsEx.L);
		labelIcon[index].setIcon(icon);
		icon = getTeamIcon(name, ImageUtilsEx.S);
		tableName[index].setIcon(icon);
		teamsName[index].setIcon(icon);

		labelScore[index].setText(String.valueOf(totalScore));

		for (int i = 0; i < 19; i++) {
			teamsData[index][i].setText(data[i].toString());
		}

		// TODO scores
	}

	private boolean firstRun = true;
	private boolean isUpdating;

	private Timer initTimer() {
		Timer t = new Timer(4000, e -> {
			// 保证只有一个线程在更新(?
				if (!isUpdating) {
					isUpdating = true;

					new Thread(() -> {
						if (firstRun) {
							UIEventManager.notify(UIEventType.BUSY, this);
						}

						refreshData();

						if (firstRun) {
							UIEventManager.notify(UIEventType.FINISH, this);
							firstRun = false;
						}
						isUpdating = false;
					}).start();
				}
			});
		t.setInitialDelay(0);

		return t;
	}

	private void refreshData() {
		LiveTest liveData = new LiveTest();
		List<TeamLiveInfo> gameInfo = liveData.AnalyseSmatchData();
		List<PlayByPlayMessages> liveInfo = liveData.AnalyseReSauto();

		boolean updated = updateMessage(liveInfo);

		if (updated && gameInfo != null) {
			int teamIndex = 1;	// 0:主队	1:客队
			for (TeamLiveInfo teamInfo : gameInfo) {
				String teamName = teamInfo.getTeam_name();
				// TODO
				int score = teamInfo.getPoints();

				Object[] data = getTeamData(teamInfo);

				setTeamInfo(teamIndex, teamName, score, data, null);

				setPlayersInfos(teamIndex, teamInfo);

				teamIndex--;
			}
		}
	}

	private boolean updateMessage(List<PlayByPlayMessages> liveInfo) {
		JTable table = liveTable.getTable();

		int originalMsgCount = table.getRowCount();
		boolean updated = originalMsgCount < liveInfo.size();
		if (updated) {
			for (int i = originalMsgCount; i < liveInfo.size(); i++) {
				PlayByPlayMessages msg = liveInfo.get(i);

				Object[] rowData = new Object[3];
				rowData[0] = getTeamIcon(msg.getTeam_name(), ImageUtilsEx.MS);
				rowData[1] = msg.getDescription().replaceAll("<[^>]*>", " ");
				rowData[2] = msg.getVisitor_score() + "-" + msg.getHome_score();

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(rowData);
				model.moveRow(i, i, 0);
			}
		}

		return updated;
	}

	private Object[] getTeamData(TeamLiveInfo teamInfo) {
		Object[] data = new Object[19];
		int index = 0;
		data[index++] = teamInfo.getPoints();
		data[index++] = teamInfo.getAss();
		data[index++] = teamInfo.getOff() + teamInfo.getDef();
		data[index++] = teamInfo.getOff() + "-" + teamInfo.getDef();
		data[index++] = teamInfo.getSte();
		data[index++] = teamInfo.getBlo();
		data[index++] = teamInfo.getField();
		data[index++] = teamInfo.getThree();
		data[index++] = teamInfo.getFree();
		data[index++] = teamInfo.getTurn();
		data[index++] = teamInfo.getFouls();
		data[index++] = teamInfo.getFast_points();
		data[index++] = teamInfo.getPoints_paint();
		data[index++] = teamInfo.getOff_turnovers();
		data[index++] = teamInfo.getBiggest();
		data[index++] = teamInfo.getTec_fouls();
		data[index++] = teamInfo.getFlag();
		data[index++] = teamInfo.getDisqualifications();
		data[index++] = teamInfo.getEjections();

		return data;
	}

	@SuppressWarnings({ "unchecked" })
	private void setPlayersInfos(int teamIndex, TeamLiveInfo teamInfo) {
		List<PlayerLiveInfo>[] playersInfos = new List[2];
		playersInfos[0] = teamInfo.getOnPlayers();
		playersInfos[1] = teamInfo.getOffPlayers();
		for (int infoType = 0; infoType < 2; infoType++) {
			// 移动位置
			if (infoType == 0) {
				for (int i = playersInfos[0].size() - 1; i >= 5; i--) {
					PlayerLiveInfo playerInfo = playersInfos[0].remove(i);
					playersInfos[1].add(0, playerInfo);
				}
			}

			List<PlayerLiveInfo> playersInfo = playersInfos[infoType];

			Object[][] playerData = new Object[playersInfo.size()][15];

			int playerIndex = 0;	// 球员编号
			for (PlayerLiveInfo playerInfo : playersInfo) {
				int index = 0;
				playerData[playerIndex][index++] = playerInfo.getPlayer_name();
				playerData[playerIndex][index++] = playerInfo.getPosition();
				// 某些数据设为空
				if (teamIndex == 0 || (!playerInfo.getPosition().equals("没有上场")
						&& !playerInfo.getPosition().equals("未被激活"))) {
					playerData[playerIndex][index++] = playerInfo.getMinutes();
					playerData[playerIndex][index++] = playerInfo.getField();
					playerData[playerIndex][index++] = playerInfo.getThree();
					playerData[playerIndex][index++] = playerInfo.getFree();
					playerData[playerIndex][index++] = valueOf(playerInfo.getOff());
					playerData[playerIndex][index++] = valueOf(playerInfo.getDef());
					playerData[playerIndex][index++] = valueOf(playerInfo.getOff()
							+ playerInfo.getDef());
					playerData[playerIndex][index++] = valueOf(playerInfo.getAss());
					playerData[playerIndex][index++] = valueOf(playerInfo.getSte());
					playerData[playerIndex][index++] = valueOf(playerInfo.getBlo());
					playerData[playerIndex][index++] = valueOf(playerInfo.getTurn());
					playerData[playerIndex][index++] = valueOf(playerInfo.getFouls());
					playerData[playerIndex][index++] = valueOf(playerInfo.getPoints());
				} else {
					while (index < 15) {
						playerData[playerIndex][index++] = "";
					}
				}
				playerIndex++;
			}

			setTable(teamIndex, infoType, playerData);
		}
	}
}
