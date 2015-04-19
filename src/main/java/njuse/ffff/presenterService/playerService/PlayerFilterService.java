package njuse.ffff.presenterService.playerService;

import njuse.ffff.uiservice.PlayerFilterViewService;

public interface PlayerFilterService {
	//设置球员筛选界面
	public void setPlayerFilterPanel();
	//传送球员筛选条件
	public void setPlayerFilterResult(PlayerFilterViewService panel);
}
