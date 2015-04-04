package njuse.ffff.presenterService.hotEventService;

import java.util.Date;

public interface HotEventService {
	//设置当天热点球员界面
	public void setBestPlayerForDayPanel(Date date);
	//设置赛季热点球员界面
	public void setBestPlayerForSeasonPanel(Date date);
	//设置赛季热点球队界面
	public void setBestTeamForSeasonPanel(Date date);
	//设置5佳进步球员界面
	public void setProgressedPlayerPanel(Date date);
}
