package njuse.ffff.presenterService.teamService;

import njuse.ffff.uiservice.TeamDataService;
import njuse.ffff.uiservice.TeamProfileService;

public interface TeamInfoService {
	//设置界面为球队简介界面
	public void setTeamProfilePanel(TeamProfileService panel,String teamName);
	//设置球队数据界面
	public void changeToTeamDataPanel(TeamDataService panel);
}
