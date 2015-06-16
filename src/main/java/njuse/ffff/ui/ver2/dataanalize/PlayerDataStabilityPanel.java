package njuse.ffff.ui.ver2.dataanalize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import njuse.ffff.presenter.analysisController.AnalysisController;
import njuse.ffff.util.BasicPlayerInfo;
import njuse.ffff.vo.PlayerSteady;

public class PlayerDataStabilityPanel extends DataPanel {
	private static final long serialVersionUID = 1L;

	private static final String[] tabs = { "得分", "篮板", "助攻", "抢断", "盖帽" };

	private Map<String, StabilityPage> map;

	AnalysisController ans;

	public PlayerDataStabilityPanel() {
		super(tabs);
		seasonPanel.setVisible(false);

		map = new HashMap<String, StabilityPage>();

		for (String tab : tabs) {
			StabilityPage page = new StabilityPage(tab);
			dataPanel.add(tab, page);
			map.put(tab, page);
		}

		ans = AnalysisController.getInstance();
	}

	@Override
	protected void switchToPlayerData(BasicPlayerInfo info) {
		ArrayList<PlayerSteady> list = ans.getPlayerSteadyAnalysis(info.getID());

		for (int i = 0; i < tabs.length; i++) {
			StabilityPage page = map.get(tabs[i]);
			PlayerSteady ps = list.get(i);
			//			page.average.setText(ps.get);
			page.var.setText(String.valueOf(ps.getVar()));
			page.varSample.setText(String.valueOf(ps.getS2()));
//			page.varSample2.setText(String.valueOf(ps.get));
			page.conclusion.setText(ps.getResult());
		}

		super.switchToPlayerData(info);
	}
}
