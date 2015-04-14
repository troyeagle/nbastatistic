package njuse.ffff.presenterService.playerService;

import njuse.ffff.uiservice.PlayerDataService;
import njuse.ffff.uiservice.PlayerProfileService;

public interface PlayerInfoService {
	//设置球员简介界面
	public void setPlayerProfilePanel(PlayerProfileService playerProfilePanel,String playerName);
	//设置球员数据界面
	public void setPlayerDataPanel(PlayerDataService playerDataPanel);
}
