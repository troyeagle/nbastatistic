package njuse.ffff.ui.ver2.dataanalize;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;

import njuse.ffff.presenter.analysisController.AnalysisController;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;

public class LeagueOffenseStylePanel extends PanelEx {
	private static final long serialVersionUID = 1L;

	private static final String[] header = { "总出手数", "总命中数", "命中率", "三分出手数",
			"三分命中数", "命中率", "罚球出手数", "罚球命中数", "命中率", "得分" };

	private JComboBox<String> seasonList;

	private LabelEx[] labels;

	private AnalysisController ans;

	public LeagueOffenseStylePanel() {
		super(new BorderLayout(80, 80));
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(80, 80, 80, 80));

		PanelEx dataPanel = new PanelEx(new GridLayout(5, 2, 20, 20));
		dataPanel.setOpaque(false);
		add(dataPanel);

		ans = AnalysisController.getInstance();
		labels = new LabelEx[header.length];
		for (int i = 0; i < header.length; i++) {
			PanelEx panel = new PanelEx(new GridLayout(1, 2));
			panel.setOpaque(false);
			LabelEx label = new LabelEx(header[i]);
			label.setFont(UIConfig.ContentFont);
			label.setForeground(Color.BLACK);
			panel.add(label);

			labels[i] = new LabelEx("data");
			labels[i].setFont(UIConfig.ContentFont);
			labels[i].setForeground(Color.BLACK);
			panel.add(labels[i]);

			dataPanel.add(panel);
		}

		LabelEx season = new LabelEx("开始赛季：");
		season.setFont(UIConfig.TitleFont);
		season.setForeground(Color.BLACK);

		seasonList = new JComboBox<String>();
		seasonList.setFont(UIConfig.TitleFont);
		seasonList.setForeground(Color.BLACK);
		seasonList.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				new Thread(() -> {
					String[] res = ans.getSelfLeagueAnalysis(e.getItem().toString());
					for (int i = 0; i < res.length; i++) {
						labels[i].setText(res[i]);
					}
				}).start();
			}
		});

		PanelEx seasonPanel = new PanelEx();
		seasonPanel.setOpaque(false);
		seasonPanel.add(season);
		seasonPanel.add(seasonList);
		String[] seasons = ans.getStartSeason();
		for (String s : seasons) {
			seasonList.addItem(s);
		}
		seasonList.setSelectedIndex(0);
		add(seasonPanel, BorderLayout.NORTH);

	}
}
