package njuse.ffff.presenter;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import njuse.ffff.data.DataReadController;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamPO;
import njuse.ffff.ui.MainFrame;
import njuse.ffff.ui.PlayerComparePanel;
import njuse.ffff.ui.PlayerDataPanel;
import njuse.ffff.ui.PlayerProfilePanel;
import njuse.ffff.ui.SearchPanel;
import njuse.ffff.ui.TeamComparePanel;
import njuse.ffff.ui.TeamProfilePanel;
import njuse.ffff.util.Filter;

public class UIController implements ControllerService {
	private MainFrame frame = null;
	private static UIController controller = null;
	private DataReaderService dataService;

	private JPanel currentPanel;

	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}

	private UIController() {
		bindDataService();
	}

	public static UIController getInstance() {
		if (controller == null) {
			controller = new UIController();
		}
		return controller;
	}

	public void createFrame() {
		if (frame == null) {
			frame = new MainFrame();
			//			setTeamProfilePanel("Hawks");
		}
		frame.setVisible(true);
	}

	/**
	 * 绑定数据层
	 */
	private void bindDataService() {
		dataService = new DataReadController();
	}

	/**
	 * 界面切换准备(?)工作
	 * 
	 * @param panel
	 */
	private void switchToPanel(JPanel panel) {
		frame.switchToPanel(panel);
		currentPanel = panel;
	}

	/**
	 * 设置查询界面
	 */
	public void setSearchPanel() {
		SearchPanel searchPanel = new SearchPanel();
		switchToPanel(searchPanel);
	}

	/**
	 * 设置球队信息横向比较界面
	 */
	public void setTeamComparePanel() {
		// TODO 获取所有球队信息

		TeamComparePanel teamComparePanel = new TeamComparePanel();
		switchToPanel(teamComparePanel);
	}

	/**
	 * 设置球员信息一览界面
	 */
	public void setPlayerComparePanel() {
		//TODO 获取所有球员信息
		ArrayList<PlayerPO> data = dataService.getPlayerInfoAll(emptyFilter);

		String[] properties = { "球员名称", "比赛场数", "投篮命中数",
				"投篮出手数", "三分命中数", "三分出手数", "罚球命中数",
				"罚球出手数","进攻篮板数", "防守篮板数", "篮板数",
				"助攻数", "抢断数", "盖帽数", "失误数", "犯规数", "" };
		String[][] values = new String[data.size()][];
		for (int i = 0; i < data.size(); i++) {
			PlayerPO player = data.get(i);
			PlayerInAverage playerAvg = dataService.getPlayerAverage(player.getName(),
					emptyFilter);
			values[i] = new String[] {
					playerAvg.getName(),
			};
		}

		PlayerComparePanel playerComparePanel = new PlayerComparePanel();
		playerComparePanel.setPlayersInfo(properties, values);

		switchToPanel(playerComparePanel);

	}

	/**
	 * 设置球员简介界面
	 */
	public void setPlayerProfilePanel(String playerName) {
		//TODO 获取指定的球员信息
		PlayerPO playerInfo = dataService.getPlayerInfo(playerName, emptyFilter);
		String position = null;
		switch (Character.toUpperCase(playerInfo.getPosition())) {
		case 'F':
			position = "前锋";
			break;
		case 'C':
			position = "中锋";
			break;
		case 'G':
			position = "后卫";
			break;
		}
		String[][] properties = {
				{ "身高", playerInfo.getHeight() },
				{ "体重", playerInfo.getWeight() },
				{ "生日", playerInfo.getBirth() },
				{ "年龄", String.valueOf(playerInfo.getAge()) },
				{ "联赛球龄", playerInfo.getNumber() },
				{ "编号", playerInfo.getNumber() },
				{ "学校", playerInfo.getSchool() }
		};

		// 球员头像
		ImageIcon photo = new ImageIcon(playerInfo.getPathOfPortrait());

		PlayerProfilePanel playerPanel = new PlayerProfilePanel();
		playerPanel.setProfile(playerInfo.getName(), position, properties);
		playerPanel.setPhoto(photo.getImage());

		switchToPanel(playerPanel);
	}

	/**
	 * 设置球员数据信息界面
	 */
	public void setPlayerDataPanel(int number) {
		PlayerDataPanel playerDataPanel = new PlayerDataPanel(number);
		switchToPanel(playerDataPanel);
	}

	/**
	 * 设置球队简介界面
	 */
	public void setTeamProfilePanel(String teamName) {
		//TODO 获取指定球队的信息

		TeamPO teamInfo = dataService.getTeamInfo(teamName, emptyFilter);

		// 球队简介
		String[][] properties = {
				{ "所在地", teamInfo.getState() },
				{ "联盟", teamInfo.getLeague() },
				{ "次级联盟", teamInfo.getSubLeague() },
				{ "主场", teamInfo.getHomeCourt() },
				{ "成立时间", teamInfo.getTimeOfFoundation() }
		};

		//		dataService.getTeamAverage(teamName, emptyFilter);

		// 球队队徽
//		ImageIcon icon = new ImageIcon(teamInfo.getPathOfLogo());
		// TODO 处理svg文件

		TeamProfilePanel teamProfilePanel = new TeamProfilePanel();
		teamProfilePanel.setProfile(teamInfo.getName(), properties);
//		teamProfilePanel.setIcon(icon.getImage());
//		teamProfilePanel.putTable(2014, "赛季总数据", null, null);
//		teamProfilePanel.putTable(2014, "场均数据", null, null);

		switchToPanel(teamProfilePanel);
	}

	/**
	 * 设置球队数据信息界面
	 */
	public void setTeamDataPanel(String teamName, String tableName) {

	}
}
