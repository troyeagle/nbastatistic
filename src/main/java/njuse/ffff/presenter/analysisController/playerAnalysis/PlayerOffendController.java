package njuse.ffff.presenter.analysisController.playerAnalysis;

import java.util.ArrayList;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.uiservice.PlayerDataService;
import njuse.ffff.vo.OffendFactor;

public class PlayerOffendController{
	private static PlayerOffendController playerOffendController = null;
	private static TotalUIController totalController = null;
	private DataReaderService dataService;
	
	private PlayerOffendController(){
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}
	
	public static PlayerOffendController getInstance(){
		if(playerOffendController==null){
			playerOffendController = new PlayerOffendController();
		}
		return playerOffendController;
	}
	
	/**
	 * 进攻分析
	 */
	public void analyseOffend(PlayerDataService panel,String playerID,String season){
		PlayerInAverage player = dataService.getPlayerAverage(playerID, null);
		//
		double[] FG_RatioByDistance = new double[6];
		
		StringBuffer analysisOfFG_Ratio = new StringBuffer();
		if(FG_RatioByDistance[1]>=0.6){
			analysisOfFG_Ratio.append("篮下命中率高于60%，finish能力较强。");
		}
		if(FG_RatioByDistance[5]>=0.4){
			analysisOfFG_Ratio.append("\n");
			analysisOfFG_Ratio.append("三分命中率高于40%，三分较准。");
		}
		
		//
		double[] FGA_PercentByDistance = new double[5];
		
		StringBuffer analysisOfFGA_Percent = new StringBuffer();
		if(FGA_PercentByDistance[4]>=0.4){
			analysisOfFGA_Percent.append("三分比例高于40%，射手型球员。");
		}
		if((FGA_PercentByDistance[0]+FGA_PercentByDistance[1])>=0.5
				&&player.getPosition().toString().contains("PG、SG、SF")){
			analysisOfFGA_Percent.append("\n");
			analysisOfFGA_Percent.append("内线出手比例高于50%的外线球员，偏爱突破。");
		}
		if((FGA_PercentByDistance[0]+FGA_PercentByDistance[1])<=0.3
				&&player.getPosition().toString().contains("PF、C")){
			analysisOfFGA_Percent.append("\n");
			analysisOfFGA_Percent.append("内线出手比例低于30%的内线球员，大个子投手");
		}
		
		//
		double ORB_Ratio = player.getStatsAverage()[20];
		double ORBperGame_Percentage = player.getStatsAverage()[6]/player.getStatsAverage()[8];
		int ORB_rank = 0;
		ArrayList<PlayerInAverage> playerList = dataService.getPlayerInAverage();
		StringBuffer analysisOfORB = new StringBuffer();
		if(ORB_rank<=30){
			analysisOfORB.append("擅长拼抢前场篮板。");
		}
		
		//
		double assistRatio = player.getStatsAverage()[22];
		int assistRatio_rank = 0;
		double assistperGame = player.getStatsAverage()[9];
		double assistperGame_league = 0;
		double assistper36Minutes = 0;
		double assistper36Minutes_league = 0;
		int assistper36Minutes_rank = 0;
		StringBuffer analysisOfAssist = new StringBuffer();
		if(assistper36Minutes_rank<=30){
			analysisOfAssist.append("传球意识较佳。");
		}
		
		//
		double trueShootingPercentage = 0;
		int TSPercentage_rank = 0;
		double OWS = 0;
		int OWS_rank = 0;
		StringBuffer analysisOfFG_Choice= new StringBuffer();
		if(TSPercentage_rank<=30&&OWS_rank<=30){
			analysisOfFG_Choice.append("进攻高效。");
		}
		
		OffendFactor playerOffend = new OffendFactor(playerID, season,
				FG_RatioByDistance, analysisOfFG_Ratio.toString()
				, FGA_PercentByDistance, analysisOfFGA_Percent.toString(), ORB_Ratio, ORBperGame_Percentage
				, ORB_rank, analysisOfORB.toString(), assistRatio, assistRatio_rank, assistperGame
				, assistperGame_league, assistper36Minutes, assistper36Minutes_league, analysisOfAssist.toString()
				, trueShootingPercentage, TSPercentage_rank, OWS, OWS_rank, analysisOfFG_Choice.toString());
		//TODO
	}
	
	
	/**
	 * 
	 * @param playerName
	 */
	public void analysePoints(String playerName){
		ArrayList<PlayerInAverage> playerList = new ArrayList<PlayerInAverage>();
		PlayerInAverage player = null;
		for(PlayerInAverage p:playerList){
			if(p.getName().equals(playerName)){
				player = p;
			}
		}
		if(player!=null){
			ArrayList<PlayerInMatchExtended> playerStats = player.getPlayerStats();
			if(playerStats.size()>0){
				//划分区间，需要计算最大值、最小值、平均值等
				double[] list = new double[playerStats.size()];
				double max = 0.0;
				double min = 1000.0;
				double total = player.getStatsTotal()[14];
				double average = player.getStatsAverage()[14];
				for(int i=0;i<playerStats.size();i++){
					PlayerInMatchExtended playerMatches = playerStats.get(i);
					double temp = playerMatches.getArray()[14];
					list[i] = temp;
					if(max<temp)
						max = temp;
					if(min>temp)
						min = temp;
				}
				double[] interval_point;
				if(list.length<10){
//					interval_point = 
				}
			}
		}
	}
	
}
