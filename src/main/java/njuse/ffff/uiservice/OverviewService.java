package njuse.ffff.uiservice;

public interface OverviewService {
	void setSeasons(String[] seasons);

	String getSelectedSeason();

	void setAvgInfo(Object[][] data);

	void setTotalInfo(Object[][] data);
}
