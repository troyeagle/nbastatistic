package njuse.ffff.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import njuse.ffff.po.PlayerInAverage;

public class Sort {
	public void sortPlayer(ArrayList<PlayerInAverage> players, int[] attributes) {
		Collections.sort(players, new PlayerComparator(attributes));
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
					result = o1.getStatsAverage()[attributes[i]]
							- o2.getStatsAverage()[attributes[i]];
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if ((int) result == 0) {

				} else {
					return -(int) result;
				}
			}

			return 0;
		}

	}
}
