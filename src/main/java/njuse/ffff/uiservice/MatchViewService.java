package njuse.ffff.uiservice;

public interface MatchViewService {

	void setGuestTeamInfo(String name, int[] score, Object[][] data, int[][] dirty);

	void setHostTeamInfo(String name, int[] score, Object[][] data, int[][] dirty);
}
