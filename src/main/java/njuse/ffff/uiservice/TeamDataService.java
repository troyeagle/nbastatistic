package njuse.ffff.uiservice;

/**
 * 球队数据界面接口
 * 
 * @author Li
 *
 */
public interface TeamDataService {

	void setPlayers(Object[][] data);

	void setAvgDataTable(Object[][] data);

	void setTotalDataTable(Object[][] data);

	void setAdvancedDataTable(Object[][] data);

	void setGameLog(Object[][] data, int[][] dirty);

	void setGameSeasons(String[] seasons);

	String getSelectedSeason();

	void setGameLog(Object[][] data);
}
