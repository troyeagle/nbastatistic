package njuse.ffff.presenter.analysisController.playerAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.sqlpo.PlayerShooting;
import njuse.ffff.vo.OffendFactor;

public class PlayerOffendController{
	private static PlayerOffendController playerOffendController = null;
	private static TotalUIController totalController = null;
	private NewDataReaderService dataReader;
	
	private PlayerOffendController(){
		totalController = TotalUIController.getInstance();
		dataReader = totalController.getDataReader();
	}
	
	public static PlayerOffendController getInstance(){
		if(playerOffendController==null){
			playerOffendController = new PlayerOffendController();
		}
		return playerOffendController;
	}
	
	/**
	 * 进攻分析   只分析1980年以后的
	 */
	public OffendFactor analyseOffend(String playerID,String season){
		String temp[] = season.split("-");
		if(Integer.parseInt(temp[0])<20){
			season = "20".concat(season);
		}
		else{
			season = "19".concat(season);
		}
		String position;
		List<PlayerInMatchFull> playerList_pergame = dataReader.getPlayersStatsAll(season, "per_game");
		List<PlayerInMatchFull> playerList_36Minutes = dataReader.getPlayersStatsAll(season, "per_minute");
		
		PlayerInMatchFull player_pergame = dataReader.getPlayerStatsSingle(playerID, season, "per_game");
		PlayerInMatchFull player_36Minutes = dataReader.getPlayerStatsSingle(playerID, season, "per_minute");
		if(player_pergame!=null&&player_36Minutes!=null){
		
			Map<String,Object> map_adv = player_pergame.generateAdvancedMap();
			Map<String,Object> map_avg = player_pergame.generateBasicMap();
			Map<String,Object> map_avg_36 = player_36Minutes.generateBasicMap();
			
			position = String.valueOf(map_avg.get("position"));
			
			PlayerShooting playerShoot = dataReader.getPlayerShooting(playerID, season);
			Map<String,Object> map_shoot = playerShoot.generateMap();
			//
			double[] FG_RatioByDistance = new double[]{//6
				Double.parseDouble(String.valueOf(map_shoot.get("twoPOfFGA"))),
				Double.parseDouble(String.valueOf(map_shoot.get("twoPOfFGA0_3"))),
				Double.parseDouble(String.valueOf(map_shoot.get("twoPOfFGA3_10"))),
				Double.parseDouble(String.valueOf(map_shoot.get("twoPOfFGA10_16"))),
				Double.parseDouble(String.valueOf(map_shoot.get("twoPOfFGA16plus"))),
				Double.parseDouble(String.valueOf(map_shoot.get("threePOfFGA")))
			};
			
			StringBuffer analysisOfFG_Ratio = new StringBuffer();
			if(FG_RatioByDistance[1]>=0.6){
				analysisOfFG_Ratio.append("篮下命中率高于60%，finish能力较强。");
			}
			if(FG_RatioByDistance[5]>=0.4){
				analysisOfFG_Ratio.append("\n");
				analysisOfFG_Ratio.append("三分命中率高于40%，三分较准。");
			}
			
			//
			double[] FGA_PercentByDistance = new double[]{//6
					Double.parseDouble(String.valueOf(map_shoot.get("FGtwoPOfFGA"))),
					Double.parseDouble(String.valueOf(map_shoot.get("FGtwoPOfFGA0_3"))),
					Double.parseDouble(String.valueOf(map_shoot.get("FGtwoPOfFGA3_10"))),
					Double.parseDouble(String.valueOf(map_shoot.get("FGtwoPOfFGA10_16"))),
					Double.parseDouble(String.valueOf(map_shoot.get("FGtwoPOfFGA16plus"))),
					Double.parseDouble(String.valueOf(map_shoot.get("FGthreePOfFGA")))
			};
			
			StringBuffer analysisOfFGA_Percent = new StringBuffer();
			if(FGA_PercentByDistance[4]>=0.4){
				analysisOfFGA_Percent.append("三分比例高于40%，射手型球员。");
			}
			if((FGA_PercentByDistance[0]+FGA_PercentByDistance[1])>=0.5
					&&(player_pergame.getPosition().toString().contains("PG")
							||player_pergame.getPosition().toString().contains("SG")
							||player_pergame.getPosition().toString().contains("SF"))){
				analysisOfFGA_Percent.append("\n");
				analysisOfFGA_Percent.append("内线出手比例高于50%的外线球员，偏爱突破。");
			}
			if((FGA_PercentByDistance[0]+FGA_PercentByDistance[1])<=0.3
					&&(player_pergame.getPosition().toString().contains("PF"))
					||player_pergame.getPosition().toString().contains("C")){
				analysisOfFGA_Percent.append("\n");
				analysisOfFGA_Percent.append("内线出手比例低于30%的内线球员，大个子投手");
			}
			
			//
			double ORB_Ratio = Double.parseDouble(String.valueOf(map_adv.get("offensiveReboundRatio")));
			double ORBperGame_Percentage = Double.parseDouble(String.valueOf(map_avg.get("offensiveRebound")))
					/Double.parseDouble(String.valueOf(map_avg.get("rebound")));
			StringBuffer analysisOfORB = new StringBuffer();
			int ORB_rank = 1;
			if(ORB_Ratio==-1){
				ORB_rank = -1;
			}
			else{
				for(PlayerInMatchFull p:playerList_pergame){
					if(ORB_Ratio<p.getOffensiveReboundRatio()){
						ORB_rank++;
					}
				}
				if(ORB_rank<=30){
					analysisOfORB.append("擅长拼抢前场篮板。");
				}
			}
			
			
			
			//
			double assistRatio = Double.parseDouble(String.valueOf(map_adv.get("assistRatio")));
			int assistRatio_rank = 1;
			double assistperGame = Double.parseDouble(String.valueOf(map_avg.get("assist")));
			double assistperGame_league = 0;
			if(assistRatio==-1){
				assistRatio_rank = -1;
			}
			else{
				for(PlayerInMatchFull p:playerList_pergame){
					if(assistRatio<p.getAssistRatio()){
						assistRatio_rank++;
					}
				}
			}
			int gamePlayed_league = 0;
			for(PlayerInMatchFull p:playerList_pergame){
				if(p.getAssist()<0)
					continue;
				gamePlayed_league += p.getGamesPlayed();
				assistperGame_league+=p.getAssist()*p.getGamesPlayed();
			}
			assistperGame_league/=gamePlayed_league;
			
			double assistper36Minutes = Double.parseDouble(String.valueOf(map_avg_36.get("assist")));
			double assistper36Minutes_league = 0;
			StringBuffer analysisOfAssist = new StringBuffer();
			int assistper36Minutes_rank = 1;
			gamePlayed_league = 0;
			if(assistper36Minutes==-1){
				assistper36Minutes_rank = -1;
			}
			else{
				for(PlayerInMatchFull p:playerList_36Minutes){
					if(assistper36Minutes<p.getAssist()){
						assistper36Minutes_rank++;
					}
				}
				if(assistper36Minutes_rank<=30){
					analysisOfAssist.append("传球意识较佳。");
				}
			}
			for(PlayerInMatchFull p:playerList_36Minutes){
				if(p.getAssist()<0)
					continue;
				gamePlayed_league += p.getGamesPlayed();
				assistper36Minutes_league+=p.getAssist()*p.getGamesPlayed();
			}
			assistper36Minutes_league/=gamePlayed_league;
			
			
			
			//
			double trueShootingPercentage = Double.parseDouble(String.valueOf(map_adv.get("trueShootingPercentage")));
			int TSPercentage_rank = 1;
			double OWS = Double.parseDouble(String.valueOf(map_adv.get("ows")));
			int OWS_rank = 1;
			StringBuffer analysisOfFG_Choice= new StringBuffer();
			if(trueShootingPercentage==-1){
				TSPercentage_rank = -1;
			}
			else if(OWS==-1){
				OWS_rank = -1;
			}
			else{
				for(PlayerInMatchFull p:playerList_pergame){
					if(trueShootingPercentage<p.getTrueShootingPercentage()){
						TSPercentage_rank++;
					}
					if(OWS<p.getOws()){
						OWS_rank++;
					}
				}
				if(TSPercentage_rank<=30&&OWS_rank<=30){
					analysisOfFG_Choice.append("进攻高效。");
				}
			}
			
			
			
			OffendFactor playerOffend = new OffendFactor(playerID, season,position,
					FG_RatioByDistance, analysisOfFG_Ratio.toString()
					, FGA_PercentByDistance, analysisOfFGA_Percent.toString(), ORB_Ratio, ORBperGame_Percentage
					, ORB_rank, analysisOfORB.toString(), assistRatio, assistRatio_rank, assistperGame
					, assistperGame_league, assistper36Minutes, assistper36Minutes_league, analysisOfAssist.toString()
					, trueShootingPercentage, TSPercentage_rank, OWS, OWS_rank, analysisOfFG_Choice.toString());
			return playerOffend;
			
			
		}
		return null;
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
