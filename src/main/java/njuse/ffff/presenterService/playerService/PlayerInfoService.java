package njuse.ffff.presenterService.playerService;

public interface PlayerInfoService {
	//设置界面为球员简介界面
	public void setPlayerProfilePanel(String playerName);
	//球员简介界面切换为球员数据界面
	public void changeToPlayerDataPanel(int number);
	//球员数据界面切换为球员简介界面
	public void changeToPlayerProfilePanel();
}
