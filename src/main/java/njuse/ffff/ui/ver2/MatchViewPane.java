package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import njuse.ffff.presenter.matchController.MatchInfoController;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.MatchViewService;

public class MatchViewPane extends PanelEx implements MatchViewService {

	private static final long serialVersionUID = 1L;

	private static final String[] tableHeader = { "球员", "首发", "时间", "投篮",
			"命中", "出手", "三分", "命中", "出手", "罚球", "命中", "出手", "真实命中",
			"篮板", "前场", "后场", "助攻", "抢断", "盖帽", "失误", "犯规", "得分"
	};

	private LabelEx[] labelIcon;
	private LabelEx[] labelName;
	private LabelEx[][] labelScore;

	private TableView[] table;

	public MatchViewPane() {
		super(new BorderLayout(10, 20));
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

		labelIcon = new LabelEx[2];
		labelName = new LabelEx[2];
		labelScore = new LabelEx[2][5];
		table = new TableView[2];

		PanelEx panelData = new PanelEx();
		panelData.setOpaque(false);
		add(panelData, BorderLayout.NORTH);
		PanelEx panelTable = new PanelEx(new GridLayout(1, 2, 50, 0));
		panelTable.setOpaque(false);
		add(panelTable);
		for (int i = 0; i < 2; i++) {
			int align = i == 0 ? LabelEx.RIGHT : LabelEx.LEFT;
			PanelEx teamPanel = new PanelEx(new BorderLayout());
			teamPanel.setOpaque(false);
			panelData.add(teamPanel);

			labelIcon[i] = new LabelEx();
			labelIcon[i].setOpaque(false);
			labelIcon[i].setHorizontalAlignment(LabelEx.CENTER);
			teamPanel.add(labelIcon[i]);

			PanelEx panelText = new PanelEx(new BorderLayout(0, 10));
			panelText.setOpaque(false);
			teamPanel.add(panelText, BorderLayout.SOUTH);
			PanelEx panelInfo = new PanelEx(new GridLayout(5, 1));
			panelInfo.setOpaque(false);
			panelText.add(panelInfo);

			labelName[i] = new LabelEx();
			LabelEx name = labelName[i];
			name.setOpaque(false);
			name.setCursor(new Cursor(Cursor.HAND_CURSOR));
			name.setHorizontalAlignment(align);
			name.setFont(UIConfig.TitleFont);
			name.setForeground(Color.WHITE);
			name.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					UIEventManager.notify(UIEventType.SWITCH, "球队详情:" + name.getText());
				}
			});
			panelText.add(name, BorderLayout.NORTH);

			for (int j = 0; j < 5; j++) {
				labelScore[i][j] = new LabelEx();
				labelScore[i][j].setOpaque(false);
				labelScore[i][j].setHorizontalAlignment(align);
				labelScore[i][j].setFont(UIConfig.SmallFont);
				labelScore[i][j].setForeground(Color.WHITE);
				panelInfo.add(labelScore[i][j]);
			}

			Object[][] emptyValue = new Object[0][0];
			table[i] = new TableView(emptyValue, tableHeader);
			TableView t = table[i];
			setTableUIConfig(t);
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
			panelTable.add(table[i]);
		}
		PanelEx centerPanel = new PanelEx(new BorderLayout());
		centerPanel.setOpaque(false);
		panelData.add(centerPanel, 1);

		PanelEx empty = new PanelEx(new BorderLayout());
		empty.setOpaque(false);
		empty.setPreferredSize(new Dimension(200, 200));
		centerPanel.add(empty);
		PanelEx panelText = new PanelEx(new BorderLayout(0, 10));
		panelText.setOpaque(false);
		centerPanel.add(panelText, BorderLayout.SOUTH);
		PanelEx charPanel = new PanelEx(new GridLayout(5, 1));
		charPanel.setOpaque(false);
		panelText.add(charPanel);

		LabelEx vs = new LabelEx("VS");
		vs.setOpaque(false);
		vs.setHorizontalAlignment(LabelEx.CENTER);
		vs.setFont(UIConfig.TitleFont);
		vs.setForeground(Color.WHITE);
		panelText.add(vs, BorderLayout.NORTH);

		String[] labels = { "总分", "第一节", "第二节", "第三节", "第四节" };
		for (int i = 0; i < 5; i++) {
			LabelEx quarter = new LabelEx(labels[i]);
			quarter.setOpaque(false);
			quarter.setHorizontalAlignment(LabelEx.CENTER);
			quarter.setFont(UIConfig.SmallFont);
			quarter.setForeground(Color.WHITE);
			charPanel.add(quarter);
		}

	}

	public void setMatch(String date, String team) {
		Timer t = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] d = date.split("-");
				int year = Integer.parseInt(d[0]);
				int month = Integer.parseInt(d[1]);
				int day = Integer.parseInt(d[2]);
				if (year < 1900) {
					year += 1900;
					day++;
				} else {
					month--;
				}
				Calendar c = Calendar.getInstance();
				c.set(year, month, day, 0, 0, 0);
				Date time = c.getTime();
				MatchInfoController.getInstance().setMatchInfoPanel(MatchViewPane.this, time,
						team);
			}
		});
		t.setRepeats(false);
		t.start();
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

		ImageIcon icon = ImageUtils.getTeamIcon(name);
		if (icon == null)
			icon = new ImageIcon("./img/no_image.png");
		Image img = icon.getImage();
		double ratio = icon.getIconWidth() / (double) icon.getIconHeight();
		int width = 200;
		int height = (int) (width / ratio);
		img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		labelIcon[index].setIcon(icon);

		labelName[index].setText(name);
		for (int i = 0; i < score.length; i++) {
			labelScore[index][i].setText(String.valueOf(score[i]));
		}
		table[index].setTable(data);
	}

	private void setTableUIConfig(TableView table) {
		table.setTableFont(UIConfig.SmallFont);
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
