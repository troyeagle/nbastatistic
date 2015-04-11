package njuse.ffff.presenter.playerController;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.playerService.PlayerInfoService;
import njuse.ffff.ui.PlayerPanel;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;

public class PlayerInfoController implements PlayerInfoService{
	private DataReaderService dataService;
	private static PlayerInfoController playerInfoController = null;
	private static TotalUIController totalController = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private PlayerInfoController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
		}

	public static PlayerInfoController getInstance() {
		if (playerInfoController == null) {
			playerInfoController = new PlayerInfoController();
		}
		return playerInfoController;
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
		//退休球员
		String experience = playerInfo.getExp();
		if(experience.equals("R")){
			experience = "Retired";
		}
		String[][] properties = {
				{ "身高", playerInfo.getHeight() },
				{ "体重", playerInfo.getWeight() },
				{ "生日", playerInfo.getBirth() },
				{ "年龄", String.valueOf(playerInfo.getAge()) },
				{ "联赛球龄", experience },
				{ "编号", playerInfo.getNumber() },
				{ "毕业学校", playerInfo.getSchool() }
		};
		
		PlayerInAverage data = dataService.getPlayerAverage(playerName, emptyFilter);
		
		if(data!=null){
			String[] properties1 = {"投篮命中数","投篮出手数","三分命中数","三分出手数"
					,"罚球命中数","罚球出手数"};
			String[] properties1_ratio = {"投篮命中率","三分命中率","罚球命中率","真实命中率"};
			String[] properties2 = {"篮板数","进攻篮板数","防守篮板数","助攻数","抢断数","盖帽数","失误数","犯规数"};
			String[] properties2_ratio = {"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率"};
			String[] properties3_total = {"在场时间","得分","效率"};//出场次数，先发次数
			String[] properties3_average = {"在场时间","使用率","得分","效率","GmSc效率值"};
			
			double[] total = data.getStatsTotal();
			double[] average = data.getStatsAverage();
			//TODO
			Object[][] values_total1 = new Object[1][];
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
					DealDecimal.formatChange(data.getSecond()/60,3)//properties4
						,DealDecimal.formatChange(total[14], 3),DealDecimal.formatChange(total[15], 3)//GmSc
					};
			
			Object[][] values_average1 = new Object[1][];
			values_average1[0] = new Object[]{
					DealDecimal.formatChange(average[0], 3),DealDecimal.formatChange(average[1], 3)//properties1
					,DealDecimal.formatChange(average[2], 3),DealDecimal.formatChange(average[3], 3)
					,DealDecimal.formatChange(average[4], 3),DealDecimal.formatChange(average[5], 3)
			};
			
			Object[][] values_average2 = new Object[1][];
			values_average2[0] = new Object[]{
					DealDecimal.formatChange(average[8], 3),DealDecimal.formatChange(average[6], 3)
					,DealDecimal.formatChange(average[7], 3),DealDecimal.formatChange(average[9], 3)//propertices2
					,DealDecimal.formatChange(average[10], 3),DealDecimal.formatChange(average[11], 3)
					,DealDecimal.formatChange(average[12], 3),DealDecimal.formatChange(average[13], 3)
				};
			
			Object[][] values_average3 = new Object[1][];
			values_average3[0] = new Object[]{
					DealDecimal.formatChange(data.getSecond()/(60*data.getEffective()), 3)
					,DealDecimal.formatChange(average[26], 3)//properties3
					,DealDecimal.formatChange(average[14], 3),DealDecimal.formatChange(average[15], 3)
					,DealDecimal.formatChange(average[16], 3)
				};
			
			Object[][] values_ratio1 = new Object[1][];
			values_ratio1[0] = new Object[]{
					DealDecimal.formatChange(average[29], 3)
					,DealDecimal.formatChange(average[28], 3)
					,DealDecimal.formatChange(average[27], 3)
					,DealDecimal.formatChange(average[17], 3)
					};
			
			Object[][] values_ratio2 = new Object[1][];
			values_ratio2[0] = new Object[]{DealDecimal.formatChange(average[19], 3)
					,DealDecimal.formatChange(average[20], 3),DealDecimal.formatChange(average[21], 3)//properties3
					,DealDecimal.formatChange(average[22], 3),DealDecimal.formatChange(average[23], 3)
					,DealDecimal.formatChange(average[24], 3),DealDecimal.formatChange(average[25], 3)
				};
			
			PlayerPanel playerPanel = new PlayerPanel();
			ImageIcon img_icon = new ImageIcon(playerInfo.getPathOfPortrait());
			Image image = new ImageIcon(playerInfo.getPathOfAction()).getImage();
			playerPanel.setProfile(playerInfo.getName(), position, img_icon, properties);
			playerPanel.setData(playerInfo.getName(), image
							,properties1,properties1_ratio
							,properties2,properties2_ratio,
							properties3_total,properties3_average
							,values_total1,values_average1,values_ratio1
							,values_total2,values_average2,values_ratio2
							,values_total3,values_average3);
	
			totalController.addCurrentPanel(playerPanel);
			totalController.switchToPanel(playerPanel);
		}
	}

	/**
	 * 球员简介界面切换为球员数据界面
	 */
	public void changeToPlayerDataPanel(int number) {
		((PlayerPanel)totalController.getCurrentPanel()).displayData(number);
	}

	/**
	 * 球员数据界面切换为球员简介界面
	 */
	public void changeToPlayerProfilePanel() {
		((PlayerPanel)totalController.getCurrentPanel()).displayProfile();
	}
	
	/**
	 * 球员参与的比赛
	 */
	public void arrangeMatchForPlayer(String season,String playerName){
		List<MatchPO> matchList = dataService.getMatchForPlayer(playerName);
		String[] properties = {"比赛日期","比赛对阵"};
		Object[][] values = new Object[matchList.size()][];
		for(int i=0;i<matchList.size();i++){
			MatchPO match = matchList.get(i);
			Date date = match.getDate();
			StringBuffer dateBuffer = new StringBuffer(date.getYear()+"-"+date.getMinutes()+"-"+date.getDay());
			StringBuffer participentsBuffer = new StringBuffer(match.getTeamA()+"  VS  "+match.getTeamB());
			values[i] = new Object[]{dateBuffer.toString(),participentsBuffer.toString()};
		}
	}
}
