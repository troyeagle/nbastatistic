package njuse.ffff.presenter.playerController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.playerService.PlayerCompareService;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.uiservice.PlayersOverviewService;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;

public class PlayerCompareController implements PlayerCompareService{
//	private DataReaderService dataService;
	private NewDataReaderService dataReader;
	private static PlayerCompareController playerCompareController = null;
	private static TotalUIController totalController = null;
	
	private String[] seasonList;
	
	@SuppressWarnings("unused")
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private PlayerCompareController() {
		totalController = TotalUIController.getInstance();
//		dataService = totalController.getDataReadController();
		dataReader = totalController.getDataReader();
		seasonList = totalController.getAllSeasons();
	}

	public static PlayerCompareController getInstance() {
		if (playerCompareController == null) {
			playerCompareController = new PlayerCompareController();
		}
		return playerCompareController;
	}
	
	//获得所有的赛季
	public String[] getAllSeasons(){
		return seasonList;
	}

	/**
	 * 设置球员信息一览界面
	 */
//	public void setPlayerCompareInfoForSeason(PlayersOverviewService playerViewPanel) {
//		//获取所有球员信息
//		for(String season:seasonList){
//			SeasonStatProcessor seasonStatProcessor = dataService.getSeasonStatProcessor(season);
//			if(seasonStatProcessor==null){
//				continue;
//			}
//			ArrayList<PlayerInAverage> players = seasonStatProcessor.getPlayerInAverage();
//	
//	//		String[] properties_total = { "球员名称","所属球队","参赛场数","先发场数"
//	//				,"投篮命中数","投篮出手数","三分命中数","三分出手数","罚球命中数"
//	//				,"罚球出手数","篮板数","助攻数"/**,"在场时间","进攻数","防守数"*/
//	//				,"抢断数","盖帽数","失误数","犯规数","得分","效率"};
//	//		String[] properties_average = { "球员名称","所属球队"
//	//				,"篮板数","助攻数","在场时间","投篮命中率","三分命中率","罚球命中率"/**,"进攻数","防守数"*/
//	//				,"抢断数","盖帽数","失误数","犯规数","得分","效率","GmSc效率值","真实命中率","投篮效率"
//	//				,"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率" };
//	
//			Object[][] values_total = new Object[players.size()][];
//			Object[][] values_average = new Object[players.size()][];
//			for (int i = 0; i < players.size(); i++) {
//				PlayerInAverage playerAvg = players.get(i);
//				if(playerAvg==null){
//					continue;
//				}
//				double[] total = playerAvg.getStatsTotal();
//				double[] average = playerAvg.getStatsAverage();
//				// TODO
//				values_total[i] = new Object[] {
//						playerAvg.getName(),playerAvg.getTeamName() 		//球员姓名，球员所属球队名
//						,playerAvg.getEffective(),playerAvg.getFirstOnMatch()//球员比赛场数，球员首发场数
//						,DealDecimal.formatChange(total[0])					//投篮命中数
//						,DealDecimal.formatChange(total[1])					//投篮出手数
//						,DealDecimal.formatChange(total[2])					//三分命中数
//						,DealDecimal.formatChange(total[3])					//三分出手数
//						,DealDecimal.formatChange(total[4])					//罚球命中数
//						,DealDecimal.formatChange(total[5])					//罚球命中数
//						,DealDecimal.formatChange(total[6])					//进攻篮板数
//						,DealDecimal.formatChange(total[7])					//防守篮板数
//						,DealDecimal.formatChange(total[8])					//篮板
//						,DealDecimal.formatChange(total[9])					//助攻
//						/**,"",""*/											//上场总时间，进攻数，防守数
//						,DealDecimal.formatChange(total[10])				//抢断
//						,DealDecimal.formatChange(total[11])				//盖帽
//						,DealDecimal.formatChange(total[12])				//失误
//						,DealDecimal.formatChange(total[13])				//犯规
//						,DealDecimal.formatChange(total[14])				//得分
//						,DealDecimal.formatChange(total[15])				//效率
//						//playerID
//				};
//				values_average[i] = new Object[]{
//						playerAvg.getName(),playerAvg.getTeamName()
//						,DealDecimal.formatChange(average[8], 1)//篮板
//						,DealDecimal.formatChange(average[9], 1)//助攻
//						,DealDecimal.formatChange(playerAvg.getSecond()/60, 1)//平均在场时间
//						,DealDecimal.formatChangeToPercentage(average[29])//投篮命中率
//						,DealDecimal.formatChangeToPercentage(average[28])//三分命中率
//						,DealDecimal.formatChangeToPercentage(average[27])//罚球命中率
//						/**,"",""*///,进攻数,防守数,
//						,DealDecimal.formatChange(average[10], 1)//抢断数
//						,DealDecimal.formatChange(average[11], 1)//盖帽数
//						,DealDecimal.formatChange(average[12], 1)//失误数
//						,DealDecimal.formatChange(average[13], 1)//犯规
//						,DealDecimal.formatChange(average[14], 1)//得分
//						,DealDecimal.formatChange(average[15], 1)//效率
//						,DealDecimal.formatChange(average[16], 1)//GmSc效率值
//						,DealDecimal.formatChangeToPercentage(average[17])//真实命中率
//						,DealDecimal.formatChangeToPercentage(average[18])//投篮效率
//						,DealDecimal.formatChangeToPercentage(average[19])//篮板率
//						,DealDecimal.formatChangeToPercentage(average[20])//进攻篮板率
//						,DealDecimal.formatChangeToPercentage(average[21])//防守篮板率
//						,DealDecimal.formatChangeToPercentage(average[22])//助攻率
//						,DealDecimal.formatChangeToPercentage(average[23])//抢断率
//						,DealDecimal.formatChangeToPercentage(average[24])//盖帽率
//						,DealDecimal.formatChangeToPercentage(average[25])//失误率
//						,DealDecimal.formatChangeToPercentage(average[26])//使用率
//						//playerID
//				};
//			}
//
//			playerViewPanel.setPlayersTotalInfo(values_total,season);
//			playerViewPanel.setPlayersAvgInfo(values_average,season);
//		
//			totalController.setPlayersOverviewService(playerViewPanel);
//		}
//	}
	
	public void setPlayerCompareInfoForSeason(PlayersOverviewService playerViewPanel,String season) {
		//获取所有球员信息
		List<PlayerInMatchFull> players_total = dataReader.getPlayersStatsAll(season, "totals");
		List<PlayerInMatchFull> players_avg = dataReader.getPlayersStatsAll(season, "per_game");

//		String[] properties_total = { "球员名称","所属球队","参赛场数","先发场数"
//				,"投篮命中数","投篮出手数","三分命中数","三分出手数","罚球命中数"
//				,"罚球出手数","篮板数","助攻数"/**,"在场时间","进攻数","防守数"*/
//				,"抢断数","盖帽数","失误数","犯规数","得分","效率"};
//		String[] properties_average = { "球员名称","所属球队"
//				,"篮板数","助攻数","在场时间","投篮命中率","三分命中率","罚球命中率"/**,"进攻数","防守数"*/
//				,"抢断数","盖帽数","失误数","犯规数","得分","效率","GmSc效率值","真实命中率","投篮效率"
//				,"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率" };

		Object[][] values_total = new Object[players_total.size()][];
		Object[][] values_average = new Object[players_avg.size()][];
		for (int i = 0; i < players_total.size(); i++) {
			PlayerInMatchFull player_total = players_total.get(i);
			if(player_total==null){
				continue;
			}
			Map<String,Object> map_total = player_total.generateBasicMap();
			Map<String,Object> map_adv = player_total.generateAdvancedMap();
			// TODO
			values_total[i] = new Object[] {
					player_total.getName(),map_total.get("team")		//球员姓名，球员所属球队名
					,map_total.get("gamesPlayed"),map_total.get("firstOnMatch")//球员比赛场数，球员首发场数
					,map_total.get("fieldGoalMade")					//投篮命中数
					,map_total.get("fieldGoalAttempted")					//投篮出手数
					,map_total.get("threePointerMade")						//三分命中数
					,map_total.get("threePointerAttempted")						//三分出手数
					,map_total.get("freeThrowMade")						//罚球命中数
					,map_total.get("freeThrowAttempted")						//罚球命中数
					,map_total.get("offensiveRebound")				//进攻篮板数
					,map_total.get("defensiveRebound")				//防守篮板数
					,map_total.get("rebound")					//篮板
					,map_total.get("assist")					//助攻
					/**,"",""*/											//上场总时间，进攻数，防守数
					,map_total.get("steal")				//抢断
					,map_total.get("block")				//盖帽
					,map_total.get("turnover")				//失误
					,map_total.get("foul")				//犯规
					,map_total.get("points")				//得分
					,map_adv.get("playerEfficiencyRate")				//效率
					,map_total.get("idPlayerInfo")
			};
			
		}
		
		for(int i=0;i<players_avg.size();i++){
			PlayerInMatchFull player_avg = players_avg.get(i);
			if(player_avg==null){
				continue;
			}
			Map<String,Object> map_avg = player_avg.generateBasicMap();
			Map<String,Object> map_adv = player_avg.generateAdvancedMap();
			values_average[i] = new Object[]{
					player_avg.getName(),map_avg.get("team")
					,map_avg.get("rebound")//篮板
					,map_avg.get("assist")//助攻
					,map_avg.get("minute")//平均在场时间
					,DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map_avg.get("fieldGoalPercentage"))))//投篮命中率
					,DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map_avg.get("threePointerPercentage"))))//三分命中率
					,DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map_avg.get("freeThrowRate"))))//罚球命中率
					/**,"",""*///,进攻数,防守数,
					,map_avg.get("steal")//抢断数
					,map_avg.get("block")//盖帽数
					,map_avg.get("turnover")//失误数
					,map_avg.get("foul")//犯规
					,map_avg.get("points")//得分
					,map_adv.get("playerEfficiencyRate")//效率
					,map_avg.get("trueShootingPercentage")//					,DealDecimal.formatChangeToPercentage(Double.parseDouble(String.valueOf(map_avg.get("trueShootingPercentage"))))//真实命中率
					,String.valueOf(map_adv.get("reboundRatio")).concat("%")//篮板率
					,String.valueOf(map_adv.get("offensiveReboundRatio")).concat("%")//进攻篮板率
					,String.valueOf(map_adv.get("defensiveReboundRatio")).concat("%")//防守篮板率
					,String.valueOf(map_adv.get("assistRatio")).concat("%")//助攻率
					,String.valueOf(map_adv.get("stealRatio")).concat("%")//抢断率
					,String.valueOf(map_adv.get("blockRatio")).concat("%")//盖帽率
					,String.valueOf(map_adv.get("turnoverRatio")).concat("%")//失误率
					,String.valueOf(map_adv.get("usingRatio")).concat("%")//使用率
					,map_adv.get("ows")//进攻贡献值
					,map_adv.get("dws")//防守贡献值
					,map_adv.get("ws")//球员贡献值
					,map_avg.get("idPlayerInfo")
			};
		}

		playerViewPanel.setPlayersTotalInfo(values_total,season);
		playerViewPanel.setPlayersAvgInfo(values_average,season);
	
		totalController.setPlayersOverviewService(playerViewPanel);
	}

}
