package njuse.ffff.presenter;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.batik.transcoder.TranscoderException;

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
import njuse.ffff.ui.SearchResultPanel;
import njuse.ffff.ui.TeamComparePanel;
import njuse.ffff.ui.TeamPanel;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;
import njuse.ffff.util.Sort;
import njuse.ffff.util.SvgConverter;

public class UIController implements ControllerService {
	private MainFrame frame = null;
	private static UIController controller = null;
	private DataReaderService dataService;

	private JPanel lastPanel;
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
	 * 查找搜索的球队或者球员
	 */
	public void search(String input){
		ArrayList<TeamPO> search_team = new ArrayList<TeamPO>();
		ArrayList<PlayerPO> search_player = new ArrayList<PlayerPO>();
		//获取所有球队信息
		ArrayList<TeamPO> data_team = dataService.getTeamInfoAll(emptyFilter);
		//获取所有球员信息
		ArrayList<PlayerPO> data_player = dataService.getPlayerInfoAll(emptyFilter);
		//查找
		for(TeamPO team:data_team){
			if(team.getName().contains(input)||team.getAbbr().contains(input)){
				search_team.add(team);
			}
		}
		for(PlayerPO player:data_player){
			if(player.getName().contains(input)){
				search_player.add(player);
			}
		}
		//设置搜索结果界面
		String[] properties = new String[]{"编号","分类","详细名称"};
		Object[][] values = new Object[search_team.size()+search_player.size()][3];
		for(int i=0;i<search_team.size();i++){
			values[i][0] = i+1;
			values[i][1] = "球队";
			values[i][2] = search_team.get(i).getName();
		}
		for(int j=search_team.size();j<search_team.size()+search_player.size();j++){
			values[j][0] = j+1;
			values[j][1] = "球员";
			values[j][2] = search_player.get(j-search_team.size()).getName();
		}
		SearchResultPanel panel_searchResult = new SearchResultPanel();
		panel_searchResult.setSearchResult(properties, values);
		currentPanel = panel_searchResult;
		switchToPanel(panel_searchResult);
	}

	/**
	 * 设置球队信息横向比较界面
	 */
	public void setTeamComparePanel() {
		//获取所有球队信息
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
		
		currentPanel = teamComparePanel;
		switchToPanel(teamComparePanel);
	}

	/**
	 * 设置球员信息一览界面
	 */
	public void setPlayerComparePanel() {
		//获取所有球员信息
		ArrayList<PlayerPO> data = dataService.getPlayerInfoAll(emptyFilter);

		String[] properties_total = { "球员名称","所属球队","参赛场数","先发场数"
				,"篮板数","助攻数"/**,"在场时间","进攻数","防守数"*/
				,"抢断数","盖帽数","失误数","犯规数","得分","效率"/**,"GmSc效率值""真实命中率",,"投篮效率" */};
//				,"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率"
		String[] properties_average = { "球员名称","所属球队"
				,"篮板数","助攻数","在场时间","投篮命中率","三分命中率","罚球命中率"/**,"进攻数","防守数"*/
				,"抢断数","盖帽数","失误数","犯规数","得分","效率","GmSc效率值","真实命中率","投篮效率"
				,"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率" };
		ArrayList<PlayerInAverage> players = new ArrayList<PlayerInAverage>();
		Object[][] values_total = new Object[data.size()][];
		Object[][] values_average = new Object[data.size()][];
		for (int i = 0; i < data.size(); i++) {
			PlayerPO player = data.get(i);
			PlayerInAverage playerAvg = dataService.getPlayerAverage(player.getName(),emptyFilter);
			if(playerAvg==null){
				values_total[i] = new Object[] {player.getName(),"","","","","","","","","","","","","","","",""
						,"","","","","","","","","","","","","","","",""};
				values_average[i] = new Object[] {player.getName(),"","","","","","","","","","","","","","",""
						,"","","","","","","","","","",""};
				continue;
			}
			players.add(playerAvg);
			double[] total = playerAvg.getStatsTotal();
			double[] average = playerAvg.getStatsAverage();
			// TODO
			values_total[i] = new Object[] {
					playerAvg.getName(),playerAvg.getTeamName(),playerAvg.getEffective(),playerAvg.getFirstOnMatch()//球队
					,DealDecimal.formatChange(total[8], 3),DealDecimal.formatChange(total[9], 3)//,"playerAvg.getMinute()"
					/**,"",""*/,DealDecimal.formatChange(total[10], 3)//进攻数,防守数
					,DealDecimal.formatChange(total[11], 3),DealDecimal.formatChange(total[12], 3)
					,DealDecimal.formatChange(total[13], 3),DealDecimal.formatChange(total[14], 3)
					,DealDecimal.formatChange(total[15], 3)//,DealDecimal.formatChange(total[29], 3)
					//,DealDecimal.formatChange(total[19], 3),DealDecimal.formatChange(total[20], 3)
//					,DealDecimal.formatChange(total[21], 3),DealDecimal.formatChange(total[22], 3)
//					,DealDecimal.formatChange(total[23], 3),DealDecimal.formatChange(total[24], 3)
//					,DealDecimal.formatChange(total[25], 3),DealDecimal.formatChange(total[26], 3)
					//,total[27],total[28]
//					,DealDecimal.formatChange(total[27], 3),DealDecimal.formatChange(total[28], 3)
			};
			values_average[i] = new Object[]{
					playerAvg.getName(),playerAvg.getTeamName(),DealDecimal.formatChange(average[8], 3)//,average[8]//球队
					,DealDecimal.formatChange(average[9], 3)//,average[9]//
					,DealDecimal.formatChange(playerAvg.getSecond()/60, 3)//,playerAvg.getSecond()/playerAvg.getEffective()//
					,DealDecimal.formatChange(average[29], 3),DealDecimal.formatChange(average[28], 3)//,average[16],average[17]//
					,DealDecimal.formatChange(average[27], 3)/**,"",""*///,进攻数,防守数,average[18] TODO
					,DealDecimal.formatChange(average[10], 3),DealDecimal.formatChange(average[11], 3)//,average[10],average[11]//
					,DealDecimal.formatChange(average[12], 3),DealDecimal.formatChange(average[13], 3)//,average[12],average[13]//
					,DealDecimal.formatChange(average[14], 3),DealDecimal.formatChange(average[15], 3)//,average[14],average[15]//
					,DealDecimal.formatChange(average[16], 3),DealDecimal.formatChange(average[17], 3)//,average[29],average[20]//
					,DealDecimal.formatChange(average[18], 3),DealDecimal.formatChange(average[19], 3)//,average[19],average[21]//
					,DealDecimal.formatChange(average[20], 3),DealDecimal.formatChange(average[21], 3)//,average[22],average[23]//
					,DealDecimal.formatChange(average[22], 3),DealDecimal.formatChange(average[23], 3)//,average[24],average[25]//
					,DealDecimal.formatChange(average[24], 3),DealDecimal.formatChange(average[25], 3)//,average[26],average[27]//
							,DealDecimal.formatChange(average[26], 3)//,average[28]//
			};
		}

		PlayerComparePanel playerComparePanel = new PlayerComparePanel();
		playerComparePanel.setPlayersTotalInfo(properties_total, values_total,players);
		playerComparePanel.setPlayerAverageInfo( properties_average, values_average);

		currentPanel = playerComparePanel;
		switchToPanel(playerComparePanel);
	}
	
	
	/**
	 * 设置球员筛选界面
	 */
	public void setPlayerFilterPanel(){
		PlayerFilterPanel playerFilterPanel = new PlayerFilterPanel();
		currentPanel = playerFilterPanel;
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
				location = 12;
				break;
			case "失误":
				location = 13;
				break;
			case "分钟":
				location = 15;
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
				location = 30;
				break;
			}
			conditionsOfSort[i] = location;
		}

		//排序
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
		//获取指定的球员信息
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
				{ "毕业学校", playerInfo.getSchool() }
		};
		
		PlayerInAverage data = dataService.getPlayerAverage(playerName, emptyFilter);
		
		if(data!=null){
			String[] properties1_total = {"投篮命中数","投篮出手数","三分命中数","三分出手数"
					,"罚球命中数","罚球出手数"};
			String[] properties1_average = {"投篮命中数","投篮出手数","投篮命中率","三分命中数","三分出手数","三分命中率"
					,"罚球命中数","罚球出手数","罚球命中率","真实命中率"};
			String[] properties2 = {"篮板数","进攻篮板数","防守篮板数","助攻数","抢断数","盖帽数","失误数","犯规数"};
			String[] properties3 = {"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率"};
			String[] properties4 = {"在场时间","使用率","得分","效率","GmSc效率值"};
			
			double[] total = data.getStatsTotal();
			double[] average = data.getStatsAverage();
			//TODO
			Object[][] values_total1 = new Object[1][];//DealDecimal.formatChange(total[2])TODO
			values_total1[0] = new Object[]{DealDecimal.formatChange(total[0]),
				DealDecimal.formatChange(total[1])//properties1
						,DealDecimal.formatChange(total[2]),DealDecimal.formatChange(total[3])
						,DealDecimal.formatChange(total[4]),DealDecimal.formatChange(total[5])
					};
			Object[][] values_total2 = new Object[1][];
			values_total2[0] = new Object[]{
					DealDecimal.formatChange(total[8]),DealDecimal.formatChange(total[6])
					,DealDecimal.formatChange(total[7]),DealDecimal.formatChange(total[9])
					,DealDecimal.formatChange(total[10]),DealDecimal.formatChange(total[11])
					,DealDecimal.formatChange(total[12]),DealDecimal.formatChange(total[13])//properties2
					};
			Object[][] values_total3 = new Object[1][];
			values_total3[0] = new Object[]{
						DealDecimal.formatChange(total[22], 3),DealDecimal.formatChange(total[23], 3)//properties3
						,DealDecimal.formatChange(total[24], 3),DealDecimal.formatChange(total[25], 3)
						,DealDecimal.formatChange(total[26], 3),DealDecimal.formatChange(total[27], 3)
					};
			Object[][] values_total4 = new Object[1][];
			values_total4[0] = new Object[]{
					DealDecimal.formatChange(data.getSecond()/60),DealDecimal.formatChange(total[28], 3)//properties4
						,DealDecimal.formatChange(total[14], 3),DealDecimal.formatChange(total[15], 3)
						,DealDecimal.formatChange(total[29], 3)
					};
			Object[][] values_average1 = new Object[1][];
			values_average1[0] = new Object[]{
					DealDecimal.formatChange(average[0], 3),DealDecimal.formatChange(average[1], 3)
					,DealDecimal.formatChange(average[29], 3)//properties1
					,DealDecimal.formatChange(average[2], 3),DealDecimal.formatChange(average[3], 3)
					,DealDecimal.formatChange(average[28], 3)
					,DealDecimal.formatChange(average[4], 3),DealDecimal.formatChange(average[5], 3)
					,DealDecimal.formatChange(average[27], 3)
					,DealDecimal.formatChange(average[20], 3)
				};
			Object[][] values_average2 = new Object[1][];
			values_average2[0] = new Object[]{
					DealDecimal.formatChange(average[8], 3),DealDecimal.formatChange(average[6], 3)
					,DealDecimal.formatChange(average[7], 3),DealDecimal.formatChange(average[9], 3)//propertices2
					,DealDecimal.formatChange(average[10], 3),DealDecimal.formatChange(average[11], 3)
					,DealDecimal.formatChange(average[12], 3),DealDecimal.formatChange(average[13], 3)
				};
			Object[][] values_average3 = new Object[1][];
			values_average3[0] = new Object[]{DealDecimal.formatChange(average[19], 3)
					,DealDecimal.formatChange(average[20], 3),DealDecimal.formatChange(average[21], 3)//properties3
					,DealDecimal.formatChange(average[22], 3),DealDecimal.formatChange(average[23], 3)
					,DealDecimal.formatChange(average[24], 3),DealDecimal.formatChange(average[25], 3)
				};
			Object[][] values_average4 = new Object[1][];
			values_average4[0] = new Object[]{
					DealDecimal.formatChange(data.getSecond()/(60*data.getEffective()), 3)
					,DealDecimal.formatChange(average[28], 3)//properties4
					,DealDecimal.formatChange(average[14], 3),DealDecimal.formatChange(average[15], 3)
					,DealDecimal.formatChange(average[29], 3)
				};
			
			PlayerPanel playerPanel = new PlayerPanel();
			ImageIcon img_icon = new ImageIcon(playerInfo.getPathOfPortrait());
			Image image = new ImageIcon(playerInfo.getPathOfAction()).getImage();
			playerPanel.setProfile(playerInfo.getName(), position, img_icon, properties);
			playerPanel.setData(playerInfo.getName(), image,properties1_total,properties1_average,properties2,properties3,properties4
							,values_total1,values_total2,values_total3,values_total4
							,values_average1,values_average2,values_average3,values_average4);
	
			lastPanel = currentPanel;
			currentPanel = playerPanel;
			switchToPanel(playerPanel);
		}
	}
	
	/**
	 * 球员简介界面切换为球员数据界面
	 */
	public void changeToPlayerDataPanel(int number){
		((PlayerPanel)currentPanel).displayData(number);
	}
	
	/**
	 * 球员数据界面切换为球员简介界面
	 */
	public void changeToPlayerProfilePanel(){
		((PlayerPanel)currentPanel).displayProfile();
	}

	/**
	 * 设置球队简介界面
	 */
	public void setTeamProfilePanel(String teamName) {
		//TODO 获取指定球队的信息
		TeamPO teamInfo = dataService.getTeamInfo(teamName, emptyFilter);

		// 球队简介
		String[][] properties = {
				{ "简称", teamInfo.getAbbr() },
				{ "所在地", teamInfo.getState() },
				{ "联盟", teamInfo.getLeague() },
				{ "次级联盟", teamInfo.getSubLeague() },
				{ "主场", teamInfo.getHomeCourt() },
				{ "成立时间", teamInfo.getTimeOfFoundation() }
		};

		//		dataService.getTeamAverage(teamName, emptyFilter);

		// 球队队徽
		//处理svg文件
		File flie_svg = new File(teamInfo.getPathOfLogo());
		String f = teamInfo.getPathOfLogo();
		String pathOfLogo = f.substring(0, f.length()-4).concat(".png");
		File flie_png = new File(pathOfLogo);
		try {
			SvgConverter.convertToPng(flie_svg, flie_png);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TranscoderException e) {
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(pathOfLogo);
		
		
		TeamInAverage data = dataService.getTeamAverage(teamName, emptyFilter);
		if(data!=null){
			String[] properties1 = {"投篮命中数","投篮出手数","三分命中数","三分出手数","罚球命中数","罚球出手数"};
			String[] properties1_2 = {"投篮命中率","三分命中率","罚球命中率"};
			String[] properties2 = {"篮板数","进攻篮板数","防守篮板数","助攻数","抢断数","盖帽数","失误数","犯规数"};
			String[] properties2_2 = {"进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率"};
			String[] properties3 = {"比赛场数","胜场数","胜率"};
			String[] properties3_2 = {"进攻回合","进攻效率","防守效率"};
			
			//TODO
			double[] total = data.getStatsTotal();
			Object[][] values_total1 = new Object[1][];
			values_total1[0] = new Object[]{
					DealDecimal.formatChange(total[0])//properties1
						,DealDecimal.formatChange(total[1])
						,DealDecimal.formatChange(total[2])
						,DealDecimal.formatChange(total[3])
						,DealDecimal.formatChange(total[4])
						,DealDecimal.formatChange(total[5])
					};
			Object[][] values_average1 = new Object[1][];
			values_average1[0] = new Object[]{
					DealDecimal.formatChange(data.getFieldGoalMade(),3)//properties1
					,DealDecimal.formatChange(data.getFieldGoalAttempted(),3)
					,DealDecimal.formatChange(data.getThreePointerMade(),3)
					,DealDecimal.formatChange(data.getThreePointerAttempted(),3)
					,DealDecimal.formatChange(data.getFreeThrowMade(),3)
					,DealDecimal.formatChange(data.getFreeThrowAttempted(),3)
			};
			Object[][] values_ratio1 = new Object[1][];
			values_ratio1[0] = new Object[]{
					DealDecimal.formatChange(data.getFieldGoalRatio(),3)//properties1_2
					,DealDecimal.formatChange(data.getThreePointerRatio(), 3)
					,DealDecimal.formatChange(data.getFreeThrowRatio(), 3)
			};
			
			Object[][] values_total2 = new Object[1][];
			values_total2[0] = new Object[]{
					DealDecimal.formatChange(total[8])//properties2
					,DealDecimal.formatChange(total[6])
					,DealDecimal.formatChange(total[7])
					,DealDecimal.formatChange(total[9])
					,DealDecimal.formatChange(total[10])
					,DealDecimal.formatChange(total[11])
					,DealDecimal.formatChange(total[12])
					,DealDecimal.formatChange(total[13])
			};
			Object[][] values_average2 = new Object[1][];
			values_average2[0] = new Object[]{
					DealDecimal.formatChange(data.getRebound(),3)//properties2
					,DealDecimal.formatChange(data.getOffensiveRebound(), 3)
					,DealDecimal.formatChange(data.getDefensiveRebound(), 3)
					,DealDecimal.formatChange(data.getAssist(), 3)
					,DealDecimal.formatChange(data.getSteal(), 3)
					,DealDecimal.formatChange(data.getBlock(), 3)
					,DealDecimal.formatChange(data.getTurnover(), 3)
					,DealDecimal.formatChange(data.getFoul(), 3)
			};
			Object[][] values_ratio2 = new Object[1][];
			values_ratio2[0] = new Object[]{
					DealDecimal.formatChange(data.getOffensiveReboundEf(),3)//properties2_2
					,DealDecimal.formatChange(data.getDefensiveReboundEf(), 3)
					,DealDecimal.formatChange(data.getAssistEf(), 3)
					,DealDecimal.formatChange(data.getStealEf(),3)
					,"",""//盖帽率,失误率
			};
			
			Object[][] values3_1 = new Object[1][];
			values3_1[0] = new Object[]{
					DealDecimal.formatChange(data.getNumOfMatches())//properties3_1
					,DealDecimal.formatChange(data.getNumOfWins())
					,DealDecimal.formatChange(data.getWinningRatio(), 3)
			};
			Object[][] values3_2 = new Object[1][];
			values3_2[0] = new Object[]{
					DealDecimal.formatChange(data.getMyRounds(),3)//properties3_2
					,DealDecimal.formatChange(data.getOffensiveEf(), 3)
					,DealDecimal.formatChange(data.getDefensiveEf(), 3)
			};
			
			TeamPanel teamPanel = new TeamPanel();
			teamPanel.setProfile(teamInfo.getName(),icon,properties);
			teamPanel.setData(teamInfo.getName(),properties1,properties1_2,properties2,properties2_2,properties3,properties3_2
					,values_total1,values_average1,values_ratio1,values_total2,values_average2,values_ratio2
					,values3_1,values3_2);
			
			lastPanel = currentPanel;
			currentPanel = teamPanel;
			switchToPanel(teamPanel);
		}
	}

	/**
	 * 球队简介界面切换为球队数据界面
	 */
	public void changeToTeamDataPanel(int number){
		((TeamPanel)currentPanel).displayData(number);
	}
	/**
	 * 球队数据界面切换为球队简介界面
	 */
	public void changeToTeamProfilePanel(){
		((TeamPanel)currentPanel).displayProfile();
	}
	
	/**
	 * 回退到上个界面
	 */
	public void backToLastPanel(){
		switchToPanel(lastPanel);
		currentPanel = lastPanel;
	}
}
