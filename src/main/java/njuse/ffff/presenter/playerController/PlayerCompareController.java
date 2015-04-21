package njuse.ffff.presenter.playerController;

import java.util.ArrayList;

import njuse.ffff.data.SeasonStatProcessor;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.playerService.PlayerCompareService;
import njuse.ffff.uiservice.PlayersOverviewService;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;

public class PlayerCompareController implements PlayerCompareService{
	private DataReaderService dataService;
	private static PlayerCompareController playerCompareController = null;
	private static TotalUIController totalController = null;
	
	private String presentSeason = null;
	
	@SuppressWarnings("unused")
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private PlayerCompareController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}

	public static PlayerCompareController getInstance() {
		if (playerCompareController == null) {
			playerCompareController = new PlayerCompareController();
		}
		return playerCompareController;
	}

	public String getPresentSeason() {
		return presentSeason;
	}

	/**
	 * 设置球员信息一览界面
	 */
	public void setPlayerCompareInfoForSeason(PlayersOverviewService playerViewPanel,String season) {
		//获取所有球员信息
		SeasonStatProcessor seasonStatProcessor = dataService.getSeasonStatProcessor(season);
		ArrayList<PlayerInAverage> players = seasonStatProcessor.getPlayerInAverage();

//		String[] properties_total = { "球员名称","所属球队","参赛场数","先发场数"
//				,"投篮命中数","投篮出手数","三分命中数","三分出手数","罚球命中数"
//				,"罚球出手数","篮板数","助攻数"/**,"在场时间","进攻数","防守数"*/
//				,"抢断数","盖帽数","失误数","犯规数","得分","效率"};
//		String[] properties_average = { "球员名称","所属球队"
//				,"篮板数","助攻数","在场时间","投篮命中率","三分命中率","罚球命中率"/**,"进攻数","防守数"*/
//				,"抢断数","盖帽数","失误数","犯规数","得分","效率","GmSc效率值","真实命中率","投篮效率"
//				,"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率" };

		Object[][] values_total = new Object[players.size()][];
		Object[][] values_average = new Object[players.size()][];
		for (int i = 0; i < players.size(); i++) {
			PlayerInAverage playerAvg = players.get(i);
			if(playerAvg==null){
				continue;
			}
			double[] total = playerAvg.getStatsTotal();
			double[] average = playerAvg.getStatsAverage();
			// TODO
			values_total[i] = new Object[] {
					playerAvg.getName(),playerAvg.getTeamName() 		//球员姓名，球员所属球队名
					,playerAvg.getEffective(),playerAvg.getFirstOnMatch()//球员比赛场数，球员首发场数
					,DealDecimal.formatChange(total[0])					//投篮命中数
					,DealDecimal.formatChange(total[1])					//投篮出手数
					,DealDecimal.formatChange(total[2])					//三分命中数
					,DealDecimal.formatChange(total[3])					//三分出手数
					,DealDecimal.formatChange(total[4])					//罚球命中数
					,DealDecimal.formatChange(total[5])					//罚球命中数
					,DealDecimal.formatChange(total[6])					//进攻篮板数
					,DealDecimal.formatChange(total[7])					//防守篮板数
					,DealDecimal.formatChange(total[8])					//篮板
					,DealDecimal.formatChange(total[9])					//助攻
					/**,"",""*/											//上场总时间，进攻数，防守数
					,DealDecimal.formatChange(total[10])				//抢断
					,DealDecimal.formatChange(total[11])				//盖帽
					,DealDecimal.formatChange(total[12])				//失误
					,DealDecimal.formatChange(total[13])				//犯规
					,DealDecimal.formatChange(total[14])				//得分
					,DealDecimal.formatChange(total[15])				//效率
			};
			values_average[i] = new Object[]{
					playerAvg.getName(),playerAvg.getTeamName()
					,DealDecimal.formatChange(average[8], 1)//篮板
					,DealDecimal.formatChange(average[9], 1)//助攻
					,DealDecimal.formatChange(playerAvg.getSecond()/60, 1)//平均在场时间
					,DealDecimal.formatChangeToPercentage(average[29])//投篮命中率
					,DealDecimal.formatChangeToPercentage(average[28])//三分命中率
					,DealDecimal.formatChangeToPercentage(average[27])//罚球命中率
					/**,"",""*///,进攻数,防守数,
					,DealDecimal.formatChange(average[10], 1)//抢断数
					,DealDecimal.formatChange(average[11], 1)//盖帽数
					,DealDecimal.formatChange(average[12], 1)//失误数
					,DealDecimal.formatChange(average[13], 1)//犯规
					,DealDecimal.formatChange(average[14], 1)//得分
					,DealDecimal.formatChange(average[15], 1)//效率
					,DealDecimal.formatChange(average[16], 1)//GmSc效率值
					,DealDecimal.formatChangeToPercentage(average[17])//真实命中率
					,DealDecimal.formatChangeToPercentage(average[18])//投篮效率
					,DealDecimal.formatChangeToPercentage(average[19])//篮板率
					,DealDecimal.formatChangeToPercentage(average[20])//进攻篮板率
					,DealDecimal.formatChangeToPercentage(average[21])//防守篮板率
					,DealDecimal.formatChangeToPercentage(average[22])//助攻率
					,DealDecimal.formatChangeToPercentage(average[23])//抢断率
					,DealDecimal.formatChangeToPercentage(average[24])//盖帽率
					,DealDecimal.formatChangeToPercentage(average[25])//失误率
					,DealDecimal.formatChangeToPercentage(average[26])//使用率
			};
		}

		playerViewPanel.setPlayersTotalInfo(values_total);
		playerViewPanel.setPlayersAvgInfo(values_average);
		
		totalController.setPlayersOverviewService(playerViewPanel);
		presentSeason = season;
	}
}
