package njuse.ffff.ui.ver2.dataanalize;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;

import njuse.ffff.presenter.analysisController.AnalysisController;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.component.TableView;

public class LeagueOffenseStylePanel extends PanelEx {
	private static final long serialVersionUID = 1L;

	private static final String[] header = { "总出手数", "总命中数", "命中率", "三分出手数",
			"三分命中数", "命中率", "罚球出手数", "罚球命中数", "命中率", "得分" };

	private JComboBox<String> seasonList;

	private TableView table;

	private AnalysisController ans;

	public LeagueOffenseStylePanel() {
		super(new BorderLayout(20, 20));
		setOpaque(false);

		ans = AnalysisController.getInstance();

		setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		LabelEx season = new LabelEx("开始赛季：");
		season.setFont(UIConfig.TitleFont);
		season.setForeground(Color.BLACK);

		seasonList = new JComboBox<String>();
		seasonList.setFont(UIConfig.TitleFont);
		seasonList.setForeground(Color.BLACK);
		seasonList.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				String[] res = ans.getSelfLeagueAnalysis(e.getItem().toString());
				Object[][] values = new Object[1][res.length];
				for (int i = 0; i < res.length; i++) {
					values[0][i] = res[i];
				}
				table.setTable(values);
			}
		});
		table = new TableView(null, header);
		add(table);

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

		//		new Thread(() -> {
		//			table.setTable(ans.getDefaultLeagueAnalysis());
		//		}).start();
	}
}
