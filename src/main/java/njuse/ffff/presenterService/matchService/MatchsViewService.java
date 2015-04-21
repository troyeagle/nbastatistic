package njuse.ffff.presenterService.matchService;

import njuse.ffff.uiservice.MatchesLogOverviewService;

public interface MatchsViewService {
	//设置一个月的所有比赛的界面
	public void setMatchsViewPanel(MatchesLogOverviewService panel,String year,String month);
	public String getPresentYear();
	public String getPresentMonth();
}
