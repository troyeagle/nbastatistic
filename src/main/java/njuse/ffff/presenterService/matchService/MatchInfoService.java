package njuse.ffff.presenterService.matchService;

import java.util.Date;

import njuse.ffff.uiservice.MatchViewService;

public interface MatchInfoService {
	//生成一场比赛的界面
	public void setMatchInfoPanel(MatchViewService panel,Date date,String team);
	public Date getPresentDate();
	public String getPresentTeam();
}
