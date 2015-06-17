package njuse.ffff.presenter.analysisController.playerAnalysis;

import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.vo.DefendFactor;

public class PlayerDefendController {
	private static PlayerDefendController playerDefendController = null;
	private static TotalUIController totalController = null;
	private NewDataReaderService dataReader;
	
	private PlayerDefendController(){
		totalController = TotalUIController.getInstance();
		dataReader = totalController.getDataReader();
	}
	
	public static PlayerDefendController getInstance(){
		if(playerDefendController==null){
			playerDefendController = new PlayerDefendController();
		}
		return playerDefendController;
	}
	
	public DefendFactor analyseDefend(String playerID,String season){
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
		Map<String,Object> map_adv = player_pergame.generateAdvancedMap();
		Map<String,Object> map_avg = player_pergame.generateBasicMap();
		Map<String,Object> map_avg_36 = player_36Minutes.generateBasicMap();
		
		position = String.valueOf(map_avg.get("position"));
		
		//
		double DRB_ratio = Double.parseDouble(String.valueOf(map_adv.get("defensiveReboundRatio")));
		double DRBperGame_percentage = Double.parseDouble(String.valueOf(map_avg.get("defensiveRebound")))
				/Double.parseDouble(String.valueOf(map_avg.get("rebound")));
		StringBuffer analysisOfDRB = new StringBuffer();
		int DRB_rank = 1;
		if(DRB_ratio==-1){
			DRB_rank = -1;
		}else{
			for(PlayerInMatchFull p:playerList_pergame){
				if(!(p.getDefensiveReboundRatio()==-1)){
					if(DRB_ratio<p.getDefensiveReboundRatio()){
						DRB_rank++;
					}
				}
			}
			if(DRB_rank<=30){
				analysisOfDRB.append("擅长保护后场篮板。");
			}
		}
		
		
		//
		double stealRatio = Double.parseDouble(String.valueOf(map_adv.get("stealRatio")));//抢断率：STL%
		int stealRatio_rank = 1;//抢断率联盟排名
		double stealperGame = Double.parseDouble(String.valueOf(map_avg.get("steal")));//场均抢断
		double stealperGame_league = 0;//联盟平均水平(场均抢断)
		int gamePlayed_league = 0;
		if(stealRatio==-1){
			stealRatio_rank=-1;
		}else{
			for(PlayerInMatchFull p:playerList_pergame){
				if(p.getAssistRatio()==-1){
					if(stealRatio<p.getAssistRatio()){
						stealRatio_rank++;
					}
				}
			}
		}
		for(PlayerInMatchFull p:playerList_pergame){
			if(p.getAssist()>0)
				continue;
			gamePlayed_league += p.getGamesPlayed();
			stealperGame_league+=p.getAssist()*p.getGamesPlayed();
		}
		stealperGame_league/=gamePlayed_league;
		
		double stealper36Minutes = Double.parseDouble(String.valueOf(map_avg_36.get("steal")));//每36分钟助攻
		double stealper36Minutes_league = 0;//联盟平均水平(每36分钟抢断)
		gamePlayed_league = 0;
		StringBuffer analysisOfSteal = new StringBuffer();
		int stealper36Minutes_rank = 1;
		if(stealper36Minutes==-1){
			stealper36Minutes_rank = -1;
		}
		else{
			for(PlayerInMatchFull p:playerList_36Minutes){
				if(stealper36Minutes<p.getAssist()){
					stealper36Minutes_rank++;
				}
			}
			if(stealper36Minutes_rank<=30){
				
				analysisOfSteal.append("抢断能力强。");
			}
		}
		for(PlayerInMatchFull p:playerList_36Minutes){
			if(p.getAssist()<0)
				continue;
			gamePlayed_league += p.getGamesPlayed();
			stealper36Minutes_league+=p.getAssist()*p.getGamesPlayed();
		}
		stealper36Minutes_league/=gamePlayed_league;
		
		
		//
		double blockRatio = Double.parseDouble(String.valueOf(map_adv.get("blockRatio")));//盖帽率：BLK%
		int blockRatio_rank = 1;//盖帽率联盟排名
		double blockperGame = Double.parseDouble(String.valueOf(map_avg.get("block")));//场均盖帽
		double blockperGame_league = 0;//联盟平均水平(场均盖帽)
		gamePlayed_league = 0;
		if(blockRatio==-1){
			blockRatio_rank = -1;
		}
		else{
			for(PlayerInMatchFull p:playerList_pergame){
				if(blockRatio<p.getBlockRatio()){
					blockRatio_rank++;
				}
			}
		}
		for(PlayerInMatchFull p:playerList_pergame){
			if(p.getBlock()<0)
				continue;
			gamePlayed_league += p.getGamesPlayed();
			blockperGame_league+=p.getBlock()*p.getGamesPlayed();
		}
		blockperGame_league/=gamePlayed_league;
		
		double blockper36Minutes = Double.parseDouble(String.valueOf(map_avg_36.get("block")));//每36分钟盖帽率
		double blockper36Minutes_league = 0;//联盟平均水平(每36分钟盖帽率）
		int blockper36Minutes_rank = 1;
		StringBuffer analysisOfBlock = new StringBuffer();
		gamePlayed_league = 0;
		if(blockper36Minutes==-1){
			blockper36Minutes_rank = -1;
		}
		else{
			for(PlayerInMatchFull p:playerList_36Minutes){
				if(stealper36Minutes<p.getAssist()){
					stealper36Minutes_rank++;
				}
			}
			if(blockper36Minutes_rank<=30){
				analysisOfBlock.append("善于篮下封盖。");
			}
		}
		for(PlayerInMatchFull p:playerList_36Minutes){
			if(p.getBlock()<0)
				continue;
			gamePlayed_league += p.getGamesPlayed();
			blockper36Minutes_league+=p.getBlock()*p.getGamesPlayed();
		}
		blockper36Minutes_league/=gamePlayed_league;
		
		
		
		//
		double DWS = Double.parseDouble(String.valueOf(map_adv.get("dws")));//防守贡献值
		int DWS_rank = 0;//防守贡献值联盟排名
		StringBuffer analysisOfDWS = new StringBuffer();
		if(DWS==-1){
			DWS_rank=-1;
		}
		else{
			for(PlayerInMatchFull p:playerList_pergame){
				if(DWS<p.getDws()){
					DWS_rank++;
				}
			}
			if(DWS_rank<=30){
				analysisOfDWS.append("防守水准联盟一流");
			}
		}
		
		
		DefendFactor playerDefend = new DefendFactor(
				playerID,season,position,DRB_ratio, DRBperGame_percentage
				, DRB_rank, analysisOfDRB.toString(), stealRatio, stealRatio_rank
				, stealperGame, stealperGame_league, stealper36Minutes, stealper36Minutes_league
				, analysisOfSteal.toString(), blockRatio, blockRatio_rank, blockperGame
				, blockperGame_league, blockper36Minutes, blockper36Minutes_league
				, analysisOfBlock.toString(), DWS, DWS_rank, analysisOfDWS.toString());
		return playerDefend;
	}
	
}
