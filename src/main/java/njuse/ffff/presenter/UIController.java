package njuse.ffff.presenter;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import njuse.ffff.data.DataReadController;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamPO;
import njuse.ffff.ui.MainFrame;
import njuse.ffff.ui.PlayerComparePanel;
import njuse.ffff.ui.PlayerDataPanel;
import njuse.ffff.ui.PlayerFilterPanel;
import njuse.ffff.ui.PlayerPanel;
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
	
	public void initSystem(){
		try {
			dataService.initialize();
			createFrame();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		ArrayList<TeamPO> data = dataService.getTeamInfoAll(emptyFilter);
		
		String[] propertices_total = {"球队名称","比赛场数","投篮命中数","投篮出手数"
				,"三分命中数","三分出手数","罚球命中数","罚球出手数","进攻篮板数","防守篮板数","篮板数"
				,"助攻数","抢断数","盖帽数","失误数","犯规数","比赛得分","投篮命中率","三分命中率"
				,"罚球命中率","胜率","进攻回合","进攻效率","防守效率","进攻篮板效率","防守篮板效率"
				,"抢断效率","助攻率"};
		String[] propertices_average = {"球队名称","比赛场数","投篮命中数","投篮出手数"
				,"三分命中数","三分出手数","罚球命中数","罚球出手数","进攻篮板数","防守篮板数","篮板数"
				,"助攻数","抢断数","盖帽数","失误数","犯规数","比赛得分","投篮命中率","三分命中率"
				,"罚球命中率","进攻回合","进攻效率","防守效率","进攻篮板效率","防守篮板效率"
				,"抢断效率","助攻率"};
		Object[][] values_total = new Object[data.size()][];
		Object[][] values_average = new Object[data.size()][];
		for(int i = 0;i <data.size(); i++){
			TeamPO team = data.get(i);
			TeamInAverage teamAvg = dataService.getTeamAverage(team.getName(), emptyFilter);
			if(teamAvg==null){
				values_total[i] = new Object[] {team.getName(),"","","","","","","","","","","","","","","",""
						,"","","","","","","","","","",""};
				values_average[i] = new Object[] {team.getName(),"","","","","","","","","","","","","","","",""
						,"","","","","","","","","",""};
				continue;
			}
			//TODO
			int GP = teamAvg.getNumOfMatches();
			values_total[i] = new Object[] {team.getName(),teamAvg.getNumOfMatches(),teamAvg.getFieldGoalMade()*GP
					,teamAvg.getFieldGoalAttempted()*GP,teamAvg.getThreePointerMade()*GP,teamAvg.getThreePointerAttempted()*GP
					,teamAvg.getFreeThrowMade()*GP,teamAvg.getFieldGoalAttempted()*GP,teamAvg.getOffensiveRebound()*GP
					,teamAvg.getDefensiveRebound()*GP,teamAvg.getRebound()*GP,teamAvg.getAssist()*GP,teamAvg.getSteal()*GP
					,teamAvg.getBlock()*GP,teamAvg.getTurnover()*GP,teamAvg.getFoul()*GP,teamAvg.getScores()*GP
					,teamAvg.getFieldGoalRatio(),teamAvg.getThreePointerRatio(),teamAvg.getFreeThrowRatio()
					,teamAvg.getWinningRatio(),teamAvg.getMyRounds()*GP,teamAvg.getOffensiveEf(),teamAvg.getDefensiveEf()
					,teamAvg.getOffensiveReboundEf(),teamAvg.getDefensiveReboundEf(),teamAvg.getStealEf()
					,teamAvg.getAssistEf()
			};
			values_average[i] = new Object[] {team.getName(),teamAvg.getNumOfMatches(),teamAvg.getFieldGoalMade()
					,teamAvg.getFieldGoalAttempted(),teamAvg.getThreePointerMade(),teamAvg.getThreePointerAttempted()
					,teamAvg.getFreeThrowMade(),teamAvg.getFieldGoalAttempted(),teamAvg.getOffensiveRebound()
					,teamAvg.getDefensiveRebound(),teamAvg.getRebound(),teamAvg.getAssist(),teamAvg.getSteal()
					,teamAvg.getBlock(),teamAvg.getTurnover(),teamAvg.getFoul(),teamAvg.getScores()
					,teamAvg.getFieldGoalRatio(),teamAvg.getThreePointerRatio(),teamAvg.getFreeThrowRatio()
					,teamAvg.getMyRounds(),teamAvg.getOffensiveEf(),teamAvg.getDefensiveEf()
					,teamAvg.getOffensiveReboundEf(),teamAvg.getDefensiveReboundEf(),teamAvg.getStealEf()
					,teamAvg.getAssistEf()
			};
		}
		TeamComparePanel teamComparePanel = new TeamComparePanel();
		teamComparePanel.setTeamsInfo(propertices_total,values_total,propertices_average,values_average);
		
		switchToPanel(teamComparePanel);
	}

	/**
	 * 设置球员信息一览界面
	 */
	public void setPlayerComparePanel() {
		//TODO 获取所有球员信息
		ArrayList<PlayerPO> data = dataService.getPlayerInfoAll(emptyFilter);

		String[] properties_total = { "球员名称","所属球队","参赛场数","先发场数"
				,"篮板数","助攻数","在场时间","投篮命中率","三分命中率","罚球命中率","进攻数","防守数"
				,"抢断数","盖帽数","失误数","犯规数","得分","效率","GmSc效率值","真实命中率","投篮效率"
				,"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率" };
		String[] properties_average = { "球员名称","所属球队"
				,"篮板数","助攻数","在场时间","投篮命中率","三分命中率","罚球命中率","进攻数","防守数"
				,"抢断数","盖帽数","失误数","犯规数","得分","效率","GmSc效率值","真实命中率","投篮效率"
				,"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率" };
		Object[][] values_total = new Object[data.size()][];
		Object[][] values_average = new Object[data.size()][];
		for (int i = 0; i < data.size(); i++) {
			PlayerPO player = data.get(i);
			PlayerInAverage playerAvg = dataService.getPlayerAverage(player.getName(),
					emptyFilter);
			if(playerAvg==null){
				values_total[i] = new Object[] {player.getName(),"","","","","","","","","","","","","","","",""
						,"","","","","","","","","","","","","","","",""};
				values_average[i] = new Object[] {player.getName(),"","","","","","","","","","","","","","",""
						,"","","","","","","","","","",""};
				continue;
			}
			double[] total = playerAvg.getStatsTotal();
			double[] average = playerAvg.getStatsAverage();
			// TODO
			values_total[i] = new Object[] {
					playerAvg.getName(),"球队",playerAvg.getEffective(),playerAvg.getFirstOnMatch(),total[8],total[9],playerAvg.getMinute()
					,total[16],total[17],total[18],"进攻数","防守数",total[10],total[11],total[12]
					,total[13],total[14],total[15],total[29],total[20],total[19],total[21]
					,total[22],total[23],total[24],total[25],total[26],total[27],total[28]
			};
			values_average[i] = new Object[]{
					playerAvg.getName(),"球队",average[8],average[9],playerAvg.getSecond()/playerAvg.getEffective()
					,average[16],average[17],average[18],"进攻数","防守数",average[10],average[11],average[12]
					,average[13],average[14],average[15],average[29],average[20],average[19],average[21]
					,average[22],average[23],average[24],average[25],average[26],average[27],average[28]
			};
		}

		PlayerComparePanel playerComparePanel = new PlayerComparePanel();
		playerComparePanel.setPlayersInfo(properties_total, values_total, properties_average, values_average);

		switchToPanel(playerComparePanel);
	}
	
	/**
	 * 设置球员筛选界面
	 */
	public void setPlayerFilterPanel(){
		PlayerFilterPanel playerFilterPanel = new PlayerFilterPanel();
		switchToPanel(playerFilterPanel);
	}
	
	/**
	 * 将球员筛选结果显示到界面
	 */
	public void setPlayerFilterResult(String position,String league,String sort){
		ArrayList<String> attributes = new ArrayList<String>();
		ArrayList<String> condition = new ArrayList<String>();
		attributes.add("position");
		if(position.equals("前锋")){
			condition.add("F");
		}
		else if(position.equals("中锋")){
			condition.add("C");
		}
		else if(position.equals("后卫")){
			condition.add("G");
		}
		
		attributes.add("league");
		condition.add(league);
		//TODO

		Filter filter = new Filter(attributes,condition);
		ArrayList<PlayerPO> data = dataService.getPlayerInfoAll(filter);
		
		ArrayList<PlayerInAverage> data_to_sort = new ArrayList<PlayerInAverage>();
		for(PlayerPO p:data){
			data_to_sort.add(dataService.getPlayerAverage(p.getName(), emptyFilter));
		}
		//排序条件
		String[] conditions = sort.split(" ");
		
		String[] properties = {"得分","篮板","助攻","得分/篮板/助攻","盖帽","抢断","犯规"
				,"失误","分钟","效率","投篮","三分","罚球","两双"};
		
		//TODO 排序
		ArrayList<PlayerInAverage> data_sorted = new ArrayList<PlayerInAverage>();
		
		Object[][] values = new Object[data.size()][];
		
		for(int i=0;i<50;i++){
			PlayerInAverage player = data_sorted.get(i);
			values[i] = new Object[]{player};
		}
		
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
		
		PlayerInAverage data = dataService.getPlayerAverage(playerName, emptyFilter);
		
		String[] properties1 = {"投篮命中数","投篮出手数","投篮命中率","三分命中数","三分出手数","三分命中率"
				,"罚球命中数","罚球出手数","罚球命中率","真实命中率","投篮效率"};
		String[] properties2 = {"篮板数","进攻篮板数","防守篮板数","助攻数","抢断数","盖帽数","失误数","犯规"};
		String[] properties3 = {"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率"};
		String[] properties4 = {"在场时间","使用率","得分","效率","GmSc效率值"}; 
		
		//TODO
		Object[] values = {};
		
		PlayerPanel playerPanel = new PlayerPanel();
		playerPanel.setProfile(playerInfo.getName(), position,playerInfo.getPathOfPortrait(), properties);

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
		ImageIcon icon = new ImageIcon(teamInfo.getPathOfLogo());
		// TODO 处理svg文件

		TeamProfilePanel teamProfilePanel = new TeamProfilePanel();
		teamProfilePanel.setProfile(teamInfo.getName(), properties);
		teamProfilePanel.setIcon(icon);
//		teamProfilePanel.putTable(2014, "赛季总数据", null, null);
//		teamProfilePanel.putTable(2014, "场均数据", null, null);

		switchToPanel(teamProfilePanel);
	}

	/**
	 * 设置球队数据信息界面
	 */
	public void setTeamDataPanel(String teamName, String tableName) {

	}

	public void setTeamDataPanel() {
		// TODO 自动生成的方法存根
		
	}
}
