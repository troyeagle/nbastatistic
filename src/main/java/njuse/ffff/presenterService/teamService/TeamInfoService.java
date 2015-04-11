package njuse.ffff.presenterService.teamService;

public interface TeamInfoService {
	//设置界面为球队简介界面
	public void setTeamProfilePanel(String teamName);
	//球队简介界面切换为球队数据界面
	public void changeToTeamDataPanel(int number);
	//球队数据界面切换为球队简介界面
	public void changeToTeamProfilePanel();
	//球队参与的比赛
	public void arrangeMatchForTeam(String season,String teamName);
}
