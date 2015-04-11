package njuse.ffff.presenter.teamController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.teamService.TeamInfoService;
import njuse.ffff.ui.TeamPanel;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;
import njuse.ffff.util.SvgConverter;

import org.apache.batik.transcoder.TranscoderException;

public class TeamInfoController implements TeamInfoService{
	private DataReaderService dataService;
	private static TeamInfoController teamInfoController = null;
	private static TotalUIController totalController = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private TeamInfoController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}

	public static TeamInfoController getInstance() {
		if (teamInfoController == null) {
			teamInfoController = new TeamInfoController();
		}
		return teamInfoController;
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
			
			totalController.addCurrentPanel(teamPanel);
			totalController.switchToPanel(teamPanel);
		}
	}

	/**
	 * 球队简介界面切换为球队数据界面
	 */
	public void changeToTeamDataPanel(int number){
		((TeamPanel)totalController.getCurrentPanel()).displayData(number);
	}

	/**
	 * 球队数据界面切换为球队简介界面
	 */
	public void changeToTeamProfilePanel(){
		((TeamPanel)totalController.getCurrentPanel()).displayProfile();
	}
	
	/**
	 * 球队参与的比赛
	 */
	public void arrangeMatchForTeam(String season,String teamName){
		List<MatchPO> matchList = dataService.getMatchForTeam(teamName);
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
