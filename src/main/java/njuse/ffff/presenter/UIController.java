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
import njuse.ffff.ui.PlayerFilterPanel;
import njuse.ffff.ui.PlayerPanel;
import njuse.ffff.ui.SearchPanel;
import njuse.ffff.ui.TeamComparePanel;
import njuse.ffff.ui.TeamProfilePanel;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;
import njuse.ffff.util.Sort;

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
					,DealDecimal.formatChange(teamAvg.getFieldGoalRatio(), 3),DealDecimal.formatChange(teamAvg.getThreePointerRatio(), 3)
					,DealDecimal.formatChange(teamAvg.getFreeThrowRatio(), 3),DealDecimal.formatChange(teamAvg.getWinningRatio(), 3)
					,DealDecimal.formatChange(teamAvg.getMyRounds()*GP, 3),DealDecimal.formatChange(teamAvg.getOffensiveEf(), 3)
					,DealDecimal.formatChange(teamAvg.getDefensiveEf(), 3)
					,DealDecimal.formatChange(teamAvg.getOffensiveReboundEf(), 3),DealDecimal.formatChange(teamAvg.getDefensiveReboundEf(), 3)
					,DealDecimal.formatChange(teamAvg.getStealEf(), 3),DealDecimal.formatChange(teamAvg.getAssistEf(), 3)
			};
			values_average[i] = new Object[] {team.getName(),teamAvg.getNumOfMatches(),DealDecimal.formatChange(teamAvg.getFieldGoalMade(), 3)
					,DealDecimal.formatChange(teamAvg.getFieldGoalAttempted(), 3),DealDecimal.formatChange(teamAvg.getThreePointerMade(), 3)
					,DealDecimal.formatChange(teamAvg.getThreePointerAttempted(), 3)
					,DealDecimal.formatChange(teamAvg.getFreeThrowMade(), 3)
					,DealDecimal.formatChange(teamAvg.getFieldGoalAttempted(), 3)
					,DealDecimal.formatChange(teamAvg.getOffensiveRebound(), 3)
					,DealDecimal.formatChange(teamAvg.getDefensiveRebound(), 3)
					,DealDecimal.formatChange(teamAvg.getRebound(), 3)
					,DealDecimal.formatChange(teamAvg.getAssist(), 3),DealDecimal.formatChange(teamAvg.getSteal(), 3)
					,DealDecimal.formatChange(teamAvg.getBlock(), 3),DealDecimal.formatChange(teamAvg.getTurnover(), 3)
					,DealDecimal.formatChange(teamAvg.getFoul(), 3),DealDecimal.formatChange(teamAvg.getScores(), 3)
					,DealDecimal.formatChange(teamAvg.getFieldGoalRatio(), 3)
					,DealDecimal.formatChange(teamAvg.getThreePointerRatio(), 3)
					,DealDecimal.formatChange(teamAvg.getFreeThrowRatio(), 3)
					,DealDecimal.formatChange(teamAvg.getMyRounds(), 3)
					,DealDecimal.formatChange(teamAvg.getOffensiveEf(), 3),DealDecimal.formatChange(teamAvg.getDefensiveEf(), 3)
					,DealDecimal.formatChange(teamAvg.getOffensiveReboundEf(), 3)
					,DealDecimal.formatChange(teamAvg.getDefensiveReboundEf(), 3)
					,DealDecimal.formatChange(teamAvg.getStealEf(), 3)
					,DealDecimal.formatChange(teamAvg.getAssistEf(), 3)
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
					playerAvg.getName(),playerAvg.getLeague(),playerAvg.getEffective(),playerAvg.getFirstOnMatch()//球队
					,DealDecimal.formatChange(total[8], 3),DealDecimal.formatChange(total[9], 3),playerAvg.getMinute()
					,DealDecimal.formatChange(total[16], 3),DealDecimal.formatChange(total[17], 3)
					,DealDecimal.formatChange(total[18], 3),"","",DealDecimal.formatChange(total[10], 3)//进攻数,防守数
					,DealDecimal.formatChange(total[11], 3),DealDecimal.formatChange(total[12], 3)
					,DealDecimal.formatChange(total[13], 3),DealDecimal.formatChange(total[14], 3)
					,DealDecimal.formatChange(total[15], 3),DealDecimal.formatChange(total[29], 3)
					,DealDecimal.formatChange(total[20], 3),DealDecimal.formatChange(total[19], 3)
					,DealDecimal.formatChange(total[21], 3),DealDecimal.formatChange(total[22], 3)
					,DealDecimal.formatChange(total[23], 3),DealDecimal.formatChange(total[24], 3)
					,DealDecimal.formatChange(total[25], 3),DealDecimal.formatChange(total[26], 3)
					//,total[27],total[28]
					,DealDecimal.formatChange(total[27], 3),DealDecimal.formatChange(total[28], 3)
			};
			values_average[i] = new Object[]{
					playerAvg.getName(),"",DealDecimal.formatChange(average[8], 3)//,average[8]//球队
					,DealDecimal.formatChange(average[9], 3)//,average[9]//
					,DealDecimal.formatChange(playerAvg.getSecond()/playerAvg.getEffective(), 3)//,playerAvg.getSecond()/playerAvg.getEffective()//
					,DealDecimal.formatChange(average[16], 3),DealDecimal.formatChange(average[17], 3)//,average[16],average[17]//
					,DealDecimal.formatChange(average[18], 3),"",""//,进攻数,防守数,average[18]
					,DealDecimal.formatChange(average[10], 3),DealDecimal.formatChange(average[11], 3)//,average[10],average[11]//
					,DealDecimal.formatChange(average[12], 3),DealDecimal.formatChange(average[13], 3)//,average[12],average[13]//
					,DealDecimal.formatChange(average[14], 3),DealDecimal.formatChange(average[15], 3)//,average[14],average[15]//
					,DealDecimal.formatChange(average[29], 3),DealDecimal.formatChange(average[20], 3)//,average[29],average[20]//
					,DealDecimal.formatChange(average[19], 3),DealDecimal.formatChange(average[21], 3)//,average[19],average[21]//
					,DealDecimal.formatChange(average[22], 3),DealDecimal.formatChange(average[23], 3)//,average[22],average[23]//
					,DealDecimal.formatChange(average[24], 3),DealDecimal.formatChange(average[25], 3)//,average[24],average[25]//
					,DealDecimal.formatChange(average[26], 3),DealDecimal.formatChange(average[27], 3)//,average[26],average[27]//
							,DealDecimal.formatChange(average[28], 3)//,average[28]//
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
	public void setPlayerFilterResult(PlayerFilterPanel panel,String position,String league,String sort){
		char pos = 0;
		String leagueInEnglish = null;
		if(position.equals("前锋")){
			pos = 'F';
		}
		else if(position.equals("中锋")){
			pos = 'C';
		}
		else if(position.equals("后卫")){
			pos = 'G';
		}
		
		if(league.equals("东部")){
			leagueInEnglish = "E";
		}
		else if(league.equals("西部")){
			leagueInEnglish = "W";
		}
		
		//TODO
		ArrayList<PlayerInAverage> data_to_filter = dataService.getPlayerInAverage();
		ArrayList<PlayerInAverage> data_filtered = new ArrayList<PlayerInAverage>();
		for(PlayerInAverage player:data_to_filter){
			PlayerPO playerPO = dataService.getPlayerInfo(player.getName(), emptyFilter);
			if(playerPO==null){
				continue;
			}
			if(pos!=0&&playerPO.getPosition()==pos){
				if(leagueInEnglish!=null&&leagueInEnglish.equals(player.getLeague())){
					data_filtered.add(player);
				}
			}
		}
		
		//排序条件
		String[] conditions = sort.split(";");
		
		String[] properties = {"编号","球员姓名","得分","篮板","助攻","得分/篮板/助攻","盖帽","抢断","犯规"
				,"失误","分钟","效率","投篮","三分","罚球","两双"};
		
		int[] conditionsOfSort = new int[conditions.length];
		for(int i=0;i<conditions.length;i++){
			int location = 0;
			switch(conditions[i]){
			case "得分":
				location = 14;
				break;
			case "篮板":
				location = 8;
				break;
			case "助攻":
				location = 9;
				break;
			case "得分/篮板/助攻":
				location = 30;
				break;
			case "盖帽":
				location = 11;
				break;
			case "抢断":
				location = 10;
				break;
			case "犯规":
				location = 13;
				break;
			case "失误":
				location = 12;
				break;
			case "分钟":
//				location = 8;
				break;
			case "效率":
				location = 15;
				break;
			case "投篮":
				location = 0;
				break;
			case "三分":
				location = 2;
				break;
			case "罚球":
				location = 4;
				break;
			case "两双":
				//location = -1;TODO
				break;
			}
			conditionsOfSort[i] = location;
		}

		//TODO 排序
		Sort sortConductor = new Sort();
		sortConductor.sortPlayer(data_filtered, conditionsOfSort);
		
		Object[][] values_average = new Object[50][];
		
		for(int i=0;i<Math.min(50, data_filtered.size());i++){
			PlayerInAverage playerAvg = data_filtered.get(i);
			
			if(playerAvg!=null){
				double[] average = playerAvg.getStatsAverage();
				values_average[i] = new Object[]{i+1,playerAvg.getName(),DealDecimal.formatChange(average[14], 3)
						,DealDecimal.formatChange(average[8], 3),DealDecimal.formatChange(average[9], 3)
						,DealDecimal.formatChange(average[30], 3),DealDecimal.formatChange(average[11], 3)
						,DealDecimal.formatChange(average[10], 3),DealDecimal.formatChange(average[12], 3)
						,DealDecimal.formatChange(average[13], 3),DealDecimal.formatChange(playerAvg.getSecond()/60, 3)
						,DealDecimal.formatChange(average[15], 3),DealDecimal.formatChange(average[0], 3)
						,DealDecimal.formatChange(average[2], 3),DealDecimal.formatChange(average[4], 3),""};//两双
			}
		}
		
		panel.setFilterInfo(properties, values_average);
		
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
		
		if(data!=null){
			String[] properties1 = {"投篮命中数","投篮出手数","投篮命中率","三分命中数","三分出手数","三分命中率"
					,"罚球命中数","罚球出手数","罚球命中率","真实命中率"};
			String[] properties2 = {"篮板数","进攻篮板数","防守篮板数","助攻数","抢断数","盖帽数","失误数","犯规数"};
			String[] properties3 = {"进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率"};
			String[] properties4 = {"在场时间","使用率","得分","效率","GmSc效率值"};
			
			double[] total = data.getStatsTotal();
			double[] average = data.getStatsAverage();
			//TODO
			Object[][] values_total1 = new Object[1][];
			values_total1[0] = new Object[]{total[0],total[1],DealDecimal.formatChange(total[16], 3)//properties1
						,total[2],total[3],DealDecimal.formatChange(total[17], 3)
						,total[4],total[5],DealDecimal.formatChange(total[18], 3)
						,DealDecimal.formatChange(total[20], 3)
					};
			Object[][] values_total2 = new Object[1][];
			values_total2[0] = new Object[]{
						total[8],total[6],total[7],total[9],total[10],total[11],total[12],total[13]//properties2
					};
			Object[][] values_total3 = new Object[1][];
			values_total3[0] = new Object[]{
						DealDecimal.formatChange(total[22], 3),DealDecimal.formatChange(total[23], 3)//properties3
						,DealDecimal.formatChange(total[24], 3),DealDecimal.formatChange(total[25], 3)
						,DealDecimal.formatChange(total[26], 3),DealDecimal.formatChange(total[27], 3)
					};
			Object[][] values_total4 = new Object[1][];
			values_total4[0] = new Object[]{
						data.getSecond(),DealDecimal.formatChange(total[28], 3)//properties4
						,DealDecimal.formatChange(total[14], 3),DealDecimal.formatChange(total[15], 3)
						,DealDecimal.formatChange(total[29], 3)
					};
			Object[][] values_average1 = new Object[1][];
			values_average1[0] = new Object[]{average[0],average[1],DealDecimal.formatChange(average[16], 3)//properties1
					,average[2],average[3],DealDecimal.formatChange(average[17], 3)
					,average[4],average[5],DealDecimal.formatChange(average[18], 3)
					,DealDecimal.formatChange(average[20], 3)
				};
			Object[][] values_average2 = new Object[1][];
			values_average2[0] = new Object[]{
					average[8],average[6],average[7],average[9]//propertices2
					,average[10],average[11],average[12],average[13]
				};
			Object[][] values_average3 = new Object[1][];
			values_average3[0] = new Object[]{
					DealDecimal.formatChange(average[22], 3),DealDecimal.formatChange(average[23], 3)//properties3
					,DealDecimal.formatChange(average[24], 3),DealDecimal.formatChange(average[25], 3)
					,DealDecimal.formatChange(average[26], 3),DealDecimal.formatChange(average[27], 3)
				};
			Object[][] values_average4 = new Object[1][];
			values_average4[0] = new Object[]{
					data.getSecond(),DealDecimal.formatChange(average[28], 3)//properties4
					,DealDecimal.formatChange(average[14], 3),DealDecimal.formatChange(average[15], 3)
					,DealDecimal.formatChange(average[29], 3)
				};
			
			PlayerPanel playerPanel = new PlayerPanel();
			playerPanel.setProfile(playerInfo.getName(), position,playerInfo.getPathOfPortrait(), properties);
			playerPanel.setData(playerInfo.getPathOfAction(),properties1,properties2,properties3,properties4
							,values_total1,values_total2,values_total3,values_total4
							,values_average1,values_average2,values_average3,values_average4);
	
			switchToPanel(playerPanel);
		}
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
