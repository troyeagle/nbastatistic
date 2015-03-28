package njuse.ffff.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.TeamInAverage;

public class Sort {
	public void sortPlayer(ArrayList<PlayerInAverage> players, int[] attributes) {
		Collections.sort(players, new PlayerComparator(attributes));
	}
	public void sortTeam(ArrayList<TeamInAverage> teams,int[] attributes){
		Collections.sort(teams,new TeamComparator(attributes));
	}
	public void sortPlayerTotal(ArrayList<PlayerInAverage> players,int[] attributes){
		Collections.sort(players,new PlayerTotalComparator(attributes));
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
}
