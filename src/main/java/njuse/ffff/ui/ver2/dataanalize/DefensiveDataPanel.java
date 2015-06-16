package njuse.ffff.ui.ver2.dataanalize;

import njuse.ffff.presenter.analysisController.AnalysisController;
import njuse.ffff.ui.ver2.dataanalize.defensive.BlockPanel;
import njuse.ffff.ui.ver2.dataanalize.defensive.DefPanel;
import njuse.ffff.ui.ver2.dataanalize.defensive.StealPanel;
import njuse.ffff.ui.ver2.dataanalize.offensive.ReboundPanel;
import njuse.ffff.util.BasicPlayerInfo;
import njuse.ffff.vo.DefendFactor;

public class DefensiveDataPanel extends DataPanel {

	private static final long serialVersionUID = 1L;

	private StealPanel stealPanel;
	private BlockPanel blockPanel;
	private ReboundPanel reboundPanel;
	private DefPanel defPanel;

	public DefensiveDataPanel() {
		super("防守篮板", "抢断能力", "封堵能力", "防守贡献");

		reboundPanel = new ReboundPanel(ReboundPanel.DEFENSIVE);
		stealPanel = new StealPanel();
		blockPanel = new BlockPanel();
		defPanel = new DefPanel();

		dataPanel.add("防守篮板", reboundPanel);
		dataPanel.add("抢断能力", stealPanel);
		dataPanel.add("封堵能力", blockPanel);
		dataPanel.add("防守贡献", defPanel);

		ans = AnalysisController.getInstance();

		seasonList.addItemListener(e -> {
			switchSeason();
		});
	}

	private AnalysisController ans;

	protected void switchToPlayerData(BasicPlayerInfo info) {
		String id = info.getID();
		String[] seasons = ans.getOffendInvolvedSeason(id);
		setSeasons(seasons);

		super.switchToPlayerData(info);
	}

	private void switchSeason() {
		DefendFactor df = AnalysisController.getInstance().getPlayerDefendAnalysis(
				getPlayerInfo().getID(), getSelectedSeason());

		loc.setText(df.getPosition());

		stealPanel.setData(new Object[][] { { df.getStealRatio(), df.getStealperGame(),
				df.getStealper36Minutes(), df.getStealRatio_rank(),
				df.getStealperGame_league(), df.getStealper36Minutes_league() } });
		stealPanel.setDescription(df.getAnalysisOfSteal());

		blockPanel.setData(new Object[][] { { df.getBlockRatio(), df.getBlockperGame(),
				df.getBlockper36Minutes(), df.getBlockRatio_rank(),
				df.getBlockperGame_league(), df.getBlockper36Minutes_league() } });
		blockPanel.setDescription(df.getAnalysisOfBlock());

		reboundPanel.setData(df.getDRB_ratio(), df.getDRB_rank(),
				df.getDRBperGame_percentage());
		reboundPanel.setDescription(df.getAnalysisOfDRB());

		defPanel.setDWS(df.getDWS());
		defPanel.setRank(df.getDWS_rank());
		defPanel.setDescription(df.getAnalysisOfDWS());
	}
}
