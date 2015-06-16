package njuse.ffff.presenterService.teamService;

import njuse.ffff.uiservice.TeamsOverviewService;

public interface TeamCompareService {
	//设置某一赛季的球队信息横向比较界面
	public void setTeamCompareInfoForSeason(TeamsOverviewService panel,String season);
	//获得所有的赛季
	public String[] getAllSeasons();
}
