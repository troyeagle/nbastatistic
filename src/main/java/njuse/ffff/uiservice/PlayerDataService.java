package njuse.ffff.uiservice;

/**
 * 球员数据界面接口
 * 
 * @author Li
 *
 */
public interface PlayerDataService {

	void setAvgData(Object[][] data);

	void setTotalData(Object[][] data);

	void setAdvancedData(Object[][] data);

	void setShotData(Object[][] data);

	void setGameLog(Object[][] data, int[][] dirty);

	void setGameSeasons(String[] seasons);

	String getSelectedSeason();

	void setGameLog(Object[][] data);

	void updateData();
}
