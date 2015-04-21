package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.BorderFactory;

import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.TabBar;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.PlayerDataService;

public class PlayerDetailPane extends PanelEx implements PlayerDataService {

	private static final long serialVersionUID = 1L;

	private static String[] simpleHeader = { "赛季", "球队", "首发", "时间", "投篮", "命中",
			"出手", "三分", "命中", "出手", "罚球", "命中", "出手", "篮板", "前场", "后场",
			"助攻", "抢断", "盖帽", "失误", "犯规", "得分"
	};
	
	private static String[] advHeader = {};

	private TabBar tabBar;
	private PanelEx viewPanel;

	private PlayerProfilePane profilePane;

	private TableView avgTable;
	private TableView totalTable;
	private TableView advTable;
	private TableView gamesTable;

	public PlayerDetailPane() {
		super(new BorderLayout());
		// TODO
		setOpaque(false);

		profilePane = new PlayerProfilePane();
		add(profilePane, BorderLayout.NORTH);

		tabBar = new TabBar();
		tabBar.setAlignment(TabBar.CENTER);
		tabBar.setOpaque(true);
		tabBar.setBackground(UIConfig.TitleBgColor);

		viewPanel = new PanelEx(new CardLayout());
		viewPanel.setOpaque(false);

		PanelEx tabPanel = new PanelEx(new BorderLayout());
		tabPanel.setOpaque(false);
		tabPanel.add(tabBar, BorderLayout.NORTH);
		add(tabPanel);

		viewPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

	}

	public void setPlayer(String playerName) {
		profilePane.setPlayer(playerName);
	}

	@Override
	public void setAvgData(Object[][] data) {
		if (avgTable == null) {

		}
	}

	@Override
	public void setTotalData(Object[][] data) {

	}

	@Override
	public void setAdvancedData(Object[][] data) {

	}

	@Override
	public void setGameLog(Object[][] data, int[][] dirty) {

	}
}
