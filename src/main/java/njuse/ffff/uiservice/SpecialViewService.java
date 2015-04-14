package njuse.ffff.uiservice;

public interface SpecialViewService {

	public void setHotPlayerToday(String type, Object[][] data);

	public void setHotPlayerSeason(String type, Object[][] data);

	public void setHotTeamSeason(String type, Object[][] data);

	public void setProgressPlayer(String type, Object[][] data);
}
