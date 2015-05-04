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
enum option{TOTAL,AVERAGE};
public class Sort {
	public void sortPlayer(List<PlayerInAverage> players, int[] attributes,boolean dec) {
		//Collections.sort(players, new PlayerComparator(attributes,dec));
		Collections.sort(players, new AttributeComparator(attributes,dec,true));
	}
	public void sortTeam(List<TeamInAverage> teams,int[] attributes,boolean dec){
		//Collections.sort(teams,new TeamComparator(attributes,dec));
		Collections.sort(teams,new AttributeComparator(attributes,dec,true));
	}
	public void sortPlayerTotal(List<PlayerInAverage> players,int[] attributes,boolean dec){
		//Collections.sort(players,new PlayerTotalComparator(attributes,dec));
		Collections.sort(players,new AttributeComparator(attributes,dec,true));
	}
	public void sortPlayerSingle(List<PlayerInMatchExtended> players,int[] attributes,boolean dec){
		//Collections.sort(players,new PlayerSingleComparator(attributes,dec));
		Collections.sort(players,new AttributeComparator(attributes,dec,true));
	}
	private class PlayerComparator implements Comparator<PlayerInAverage> {
		int[] attributes;
		boolean dec;
		PlayerComparator(int[] attributes,boolean dec) {
			this.attributes = attributes;
			this.dec = dec;
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
					if(!dec)return (int) result;
					return -(int) result;
				}
			}

			return -1;
		}

	}

	private class TeamComparator implements Comparator<TeamInAverage>{
		

		int[] attributes;
		boolean dec;
		TeamComparator(int[] attributes,boolean dec) {
			this.attributes = attributes;
			this.dec = dec;
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
		boolean dec;
		PlayerTotalComparator(int[] attributes,boolean dec) {
			this.attributes = attributes;
			this.dec = dec;
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

		boolean dec;
		PlayerSingleComparator(int[] attributes,boolean dec) {
			this.attributes = attributes;
			this.dec = dec;
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

	private class AttributeComparator implements Comparator<Object>{
		int[] attributes;
		boolean dec;
		double array1[]=new double[32];
		double array2[]= new double[32];
		boolean ave;
		public AttributeComparator(int[] attributes,boolean dec,boolean ave){
			this.attributes=attributes;
			this.dec=dec;
			this.ave=ave;
		}
		
		@Override
		public int compare(Object o1, Object o2) {
			double result = 0;
			if(o1 instanceof PlayerInMatchExtended){
				array1 = ((PlayerInMatchExtended)o1).getArray();
				array2 = ((PlayerInMatchExtended)o2).getArray();
			}
			else if(o1 instanceof PlayerInAverage){
				if(ave){
					array1 = ((PlayerInAverage)o1).getStatsAverage();
					array2 = ((PlayerInAverage)o2).getStatsAverage();
				}else{
					array1 = ((PlayerInAverage)o1).getStatsTotal();
					array2 = ((PlayerInAverage)o2).getStatsTotal();
				}
				
			}
			else if(o1 instanceof TeamInAverage){
				if(ave){
					array1 = ((TeamInAverage)o1).getStatsAverage();
					array2 = ((TeamInAverage)o2).getStatsAverage();
				}else{
					array1 = ((TeamInAverage)o1).getStatsTotal();
					array2 = ((TeamInAverage)o2).getStatsTotal();
				}
				
			}
			
			for (int i = 0; i < attributes.length; i++) {
				try {
					result = 1000*array1[attributes[i]]
							- 1000*array2[attributes[i]];
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				if ((int) result == 0) {
				} else {
					if(!dec){
						return (int)result;
					}
					return -(int) result;
				}
			
		}
			return 0;
		
	}
}
}