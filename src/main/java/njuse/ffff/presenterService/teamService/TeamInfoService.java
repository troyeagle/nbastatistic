package njuse.ffff.presenterService.teamService;

import njuse.ffff.uiservice.TeamDataService;
import njuse.ffff.uiservice.TeamProfileService;

public interface TeamInfoService {
	//设置球队简介界面
	public void setTeamProfilePanel(TeamProfileService panel,String teamName);
	//设置球队的球员
	public void setPlayerForTeam(TeamDataService panel,String teamName);
	//设置球队数据界面--总基础数据
	public void setTeamTotalData(TeamDataService panel,String teamName);
	//设置球队数据界面--平均基础数据
	public void setTeamAvgData(TeamDataService panel,String teamName);
	//设置球队数据界面--进阶基础数据
	public void setTeamAdvancedlData(TeamDataService panel,String teamName);
	//设置球队参加的比赛
	public void setTeamGameLog(TeamDataService panel,String teamName);
	public String[] getPresentTeam();
}
