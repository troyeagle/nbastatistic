package njuse.ffff.presenterService.playerService;

import njuse.ffff.uiservice.PlayerDataService;
import njuse.ffff.uiservice.PlayerProfileService;

public interface PlayerInfoService {
	//设置球员简介界面
	public void setPlayerProfilePanel(PlayerProfileService panel,String playerName);
	//设置球员数据界面--总基础数据表格
	public void setPlayerTotalData(PlayerDataService panel,String playerName);
	//设置球员数据界面--平均基础数据表格
	public void setPlayerAvgData(PlayerDataService panel,String playerName);
	//设置球员数据界面--进阶数据表格
	public void setPlayerAdvancedData(PlayerDataService panel,String playerName);
	//设置球员参加的比赛
	public void setPlayerGameLog(PlayerDataService panel,String playerName);
	public String[] getPresentPlayer();
}
