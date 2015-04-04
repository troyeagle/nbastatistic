package njuse.ffff.presenterService.playerService;

import njuse.ffff.ui.PlayerFilterPanel;

public interface PlayerFilterService {
	//设置界面为球员筛选界面
	public void setPlayerFilterPanel();
	//传送球员筛选条件
	public void setPlayerFilterResult(PlayerFilterPanel panel,String position,String league,String sort);
}
