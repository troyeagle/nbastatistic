package njuse.ffff.presenter.analysisController.playerAnalysis;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.uiservice.PlayerDataService;
import njuse.ffff.vo.DefendFactor;

public class PlayerDefendController {
	private static PlayerDefendController playerDefendController = null;
	private static TotalUIController totalController = null;
	private DataReaderService dataService;
	
	private PlayerDefendController(){
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}
	
	public static PlayerDefendController getInstance(){
		if(playerDefendController==null){
			playerDefendController = new PlayerDefendController();
		}
		return playerDefendController;
	}
	
	public void analyseDefend(PlayerDataService panel,String playerID,String season){
		PlayerInAverage player = dataService.getPlayerAverage(playerID, null);
		//
		double DRB_ratio = player.getStatsAverage()[21];
		double DRBperGame_percentage = player.getStatsAverage()[7]/player.getStatsAverage()[8];
		int DRB_rank = 0;
		StringBuffer analysisOfDRB = new StringBuffer();
		if(DRB_rank<=30){
			analysisOfDRB.append("擅长保护后场篮板。");
		}
		
		//
		double stealRatio = 0;//抢断率：STL%
		int stealRatio_rank = 0;//抢断率联盟排名
		double stealperGame = 0;//场均抢断
		double stealperGame_league = 0;//联盟平均水平(场均抢断)
		double stealper36Minutes = 0;//每36分钟助攻
		double stealper36Minutes_league = 0;//联盟平均水平(每36分钟抢断)
		int stealper36Minutes_rank = 0;
		StringBuffer analysisOfSteal = new StringBuffer();
		if(stealper36Minutes_rank<=30){
			analysisOfSteal.append("抢断能力强。");
		}
		
		//
		double blockRatio = 0;//盖帽率：BLK%
		int blockRatio_rank = 0;//盖帽率联盟排名
		double blockperGame = 0;//场均盖帽
		double blockperGame_league = 0;//联盟平均水平(场均盖帽)
		double blockper36Minutes = 0;//每36分钟盖帽率
		double blockper36Minutes_league = 0;//联盟平均水平(每36分钟盖帽率）
		int blockper36Minutes_rank = 0;
		StringBuffer analysisOfBlock = new StringBuffer();
		if(blockper36Minutes_rank<=30){
			analysisOfBlock.append("善于篮下封盖。");
		}
		
		//
		double DWS = 0;//防守贡献值
		int DWS_rank = 0;//防守贡献值联盟排名
		StringBuffer analysisOfDWS = new StringBuffer();
		if(DWS_rank<=30){
			analysisOfDWS.append("防守水准联盟一流");
		}
		
		DefendFactor playerDefend = new DefendFactor(
				playerID,season,DRB_ratio, DRBperGame_percentage
				, DRB_rank, analysisOfDRB.toString(), stealRatio, stealRatio_rank
				, stealperGame, stealperGame_league, stealper36Minutes, stealper36Minutes_league
				, analysisOfSteal.toString(), blockRatio, blockRatio_rank, blockperGame
				, blockperGame_league, blockper36Minutes, blockper36Minutes_league
				, analysisOfBlock.toString(), DWS, DWS_rank, analysisOfDWS.toString());
		//TODO
	}
	
}
