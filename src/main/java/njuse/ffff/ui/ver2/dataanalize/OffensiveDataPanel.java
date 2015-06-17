package njuse.ffff.ui.ver2.dataanalize;

import java.awt.event.ItemEvent;

import njuse.ffff.presenter.analysisController.AnalysisController;
import njuse.ffff.ui.ver2.dataanalize.offensive.AccPanel;
import njuse.ffff.ui.ver2.dataanalize.offensive.AttackPanel;
import njuse.ffff.ui.ver2.dataanalize.offensive.PassPanel;
import njuse.ffff.ui.ver2.dataanalize.offensive.ReboundPanel;
import njuse.ffff.ui.ver2.dataanalize.offensive.ShotPanel;
import njuse.ffff.util.BasicPlayerInfo;
import njuse.ffff.vo.OffendFactor;

public class OffensiveDataPanel extends DataPanel {

	private static final long serialVersionUID = 1L;

	AccPanel accPanel;
	ShotPanel shotPanel;
	ReboundPanel reboundPanel;

	PassPanel passPanel;
	AttackPanel attackPanel;

	public OffensiveDataPanel() {
		super("命中率", "得分分布", "前场篮板", "传球意识", "进攻贡献");

		accPanel = new AccPanel();
		dataPanel.add("命中率", accPanel);
		shotPanel = new ShotPanel();
		dataPanel.add("得分分布", shotPanel);
		reboundPanel = new ReboundPanel(ReboundPanel.OFFENSIVE);
		dataPanel.add("前场篮板", reboundPanel);
		passPanel = new PassPanel();
		dataPanel.add("传球意识", passPanel);
		attackPanel = new AttackPanel();
		dataPanel.add("进攻贡献", attackPanel);


		ans = AnalysisController.getInstance();

		seasonList.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				switchSeason();
			}
		});
	}

	private AnalysisController ans;

	@Override
	protected void switchToPlayerData(BasicPlayerInfo info) {
		String id = info.getID();
		String[] seasons = ans.getOffendInvolvedSeason(id);
		setSeasons(seasons);

		super.switchToPlayerData(info);
	}

	private void switchSeason() {
		OffendFactor of = ans.getPlayerOffendAnalysis(getPlayerInfo().getID(),
				getSelectedSeason());

		loc.setText(of.getPosition());

		accPanel.setData(arr1to2(of.getFG_RatioByDistance()));
		accPanel.setDescription(of.getAnalysisOfFG_Ratio());

		shotPanel.setData(arr1to2(of.getFGA_PercentageByDistance()));
		shotPanel.setDescription(of.getAnalysisOfFGA_Percentage());

		reboundPanel.setData(of.getORB_Ratio(), of.getORB_rank(),
				of.getORBperGame_Percentage());
		reboundPanel.setDescription(of.getAnalysisOfORB());

		passPanel.setData(new Object[][] { { of.getAssistRatio(), of.getAssistperGame(),
				of.getAssistper36Minutes(), of.getAssistRatio_rank(),
				of.getAssistperGame_league(), of.getAssistper36Minutes_league() } });
		passPanel.setDescription(of.getAnalysisOfAssist());

		attackPanel.setData(new Object[][] { { of.getTrueShootingPercentage(),
				of.getTSPercentage_rank(), of.getOWS(), of.getOWS_rank() } });
		attackPanel.setDescription(of.getAnalysisOfFG_Choice());
	}

	private Object[][] arr1to2(double[] data) {
		Object[][] res = new Object[1][data.length];
		for (int i = 0; i < data.length; i++) {
			res[0][i] = data[i];
		}
		return res;
	}
}
