package njuse.ffff.presenterService.playerService;

import njuse.ffff.uiservice.PlayerDataService;
import njuse.ffff.uiservice.PlayerProfileService;

public interface PlayerInfoService {
	//设置球员简介界面
	public void setPlayerProfilePanel(PlayerProfileService panel,String playerID);
	//设置球员数据界面--总基础数据表格
	public void setPlayerTotalData(PlayerDataService panel,String playerID);
	//设置球员数据界面--平均基础数据表格
	public void setPlayerAvgData(PlayerDataService panel,String playerID);
	//设置球员数据界面--进阶数据表格
	public void setPlayerAdvancedData(PlayerDataService panel,String playerID);
	//设置球员参加的比赛
	public void setPlayerGameLog(PlayerDataService panel,String season ,String playerID);
	public String[] getPresentPlayer();
	
	//设置季后赛球员数据界面--总基础数据表格
	public void setPlayOffTotalData(PlayerDataService panel,String playerID);
	//设置季后赛球员数据界面--平均基础数据表格
	public void setPlayOffAvgData(PlayerDataService panel,String playerID);
	//设置季后赛球员数据界面--进阶数据表格
	public void setPlayOffAdvancedData(PlayerDataService panel,String playerID);
	
	//获得球员参与的赛季
	public String[] getInvolvedSeason(String playerID);
	//球员和联盟平均水平对比
	public void playerByLeagueCompare(PlayerDataService panel,String season,String playerID);
	//设置球员进攻分析界面
	public void setPlayerOffendAnalysis(PlayerDataService panel,String playerID,String season);
	//设置球员防守分析界面
	public void setPlayerDefendAnalysis(PlayerDataService panel,String playerID,String season);
}
