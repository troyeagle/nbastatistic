package njuse.ffff.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.TeamInAverage;
/**
 * 根据属性对球员/球队类的排序方法
 * @lastUpdate 4.7
 * @author Mebleyev.G.Longinus
 *
 */
public class Sort {
	public void sortPlayer(List<PlayerInAverage> players, int[] attributes) {
		Collections.sort(players, new PlayerComparator(attributes));
	}
	public void sortTeam(List<TeamInAverage> teams,int[] attributes){
		Collections.sort(teams,new TeamComparator(attributes));
	}
	public void sortPlayerTotal(List<PlayerInAverage> players,int[] attributes){
		Collections.sort(players,new PlayerTotalComparator(attributes));
	}
	public void sortPlayerSingle(List<PlayerInMatchExtended> players,int[] attributes){
		Collections.sort(players,new PlayerSingleComparator(attributes));
	}
	private class PlayerComparator implements Comparator<PlayerInAverage> {
		int[] attributes;

		PlayerComparator(int[] attributes) {
			this.attributes = attributes;
		}

		public int compare(PlayerInAverage o1, PlayerInAverage o2) {
			// TODO Auto-generated method stub

			double result = 0;
			for (int i = 0; i < attributes.length; i++) {
				try {
					result = 1000*o1.getStatsAverage()[attributes[i]]
							- 1000*o2.getStatsAverage()[attributes[i]];
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				if ((int) result == 0) {

				} else {
					return -(int) result;
				}
			}

			return -1;
		}

	}

	private class TeamComparator implements Comparator<TeamInAverage>{
		int[] attributes;

		TeamComparator(int[] attributes) {
			this.attributes = attributes;
		}

		public int compare(TeamInAverage o1, TeamInAverage o2) {
			// TODO Auto-generated method stub

			double result = 0;
			for (int i = 0; i < attributes.length; i++) {
				try {
					result = 1000*o1.getStatsAverage()[attributes[i]]
							- 1000*o2.getStatsAverage()[attributes[i]];
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if ((int) result == 0) {

				} else {
					return -(int) result;
				}
			}

			return -1;
		}
	}

	private class PlayerTotalComparator implements Comparator<PlayerInAverage>{
		int[] attributes;

		PlayerTotalComparator(int[] attributes) {
			this.attributes = attributes;
		}

		public int compare(PlayerInAverage o1, PlayerInAverage o2) {
			// TODO Auto-generated method stub

			double result = 0;
			for (int i = 0; i < attributes.length; i++) {
				try {
					result = 1000*o1.getStatsTotal()[attributes[i]]
							- 1000*o2.getStatsTotal()[attributes[i]];
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				if ((int) result == 0) {

				} else {
					return -(int) result;
				}
			}

			return -1;
		}
	}
	
	private class PlayerSingleComparator implements Comparator<PlayerInMatchExtended>{
		int[] attributes;
		
		PlayerSingleComparator(int[] attributes) {
			this.attributes = attributes;
		}
		@Override
		public int compare(PlayerInMatchExtended o1, PlayerInMatchExtended o2) {
			double result = 0;
			for (int i = 0; i < attributes.length; i++) {
				try {
					result = 1000*o1.getArray()[attributes[i]]
							- 1000*o2.getArray()[attributes[i]];
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				if ((int) result == 0) {

				} else {
					return -(int) result;
				}
			}

			return -1;
		}
		
	}
}
