package njuse.ffff.po;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlayerInMatch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	char position;
	String minute;
	
	Date date;

	int fieldGoalMade;
	int fieldGoalAttempted;
	int threePointerMade;
	int threePointerAttempted;
	int freeThrowMade;
	int freeThrowAttempted;
	int offensiveRebound;
	int defensiveRebound;
	int rebound;
	int assist;
	int steal;
	int block;
	int turnover;
	int foul;
	int points;

	ArrayList<Integer> dirty;
	int second;// Calculate by Minute;

	public PlayerInMatch(String name, char position, String minute,
			int fieldGoalMade, int fieldGoalAttempted, int threePointerMade,
			int threePointerAttempted, int freeThrowMade,
			int freeThrowAttempted, int offensiveRebound, int defensiveRebound,
			int rebound, int assist, int steal, int block, int turnover,
			int foul, int points, ArrayList<Integer> dirty) {
		super();
		this.name = name;
		this.position = position;
		this.minute = minute;
		second = Integer.parseInt(minute.split(":")[0]) * 60
				+ Integer.parseInt(minute.split(":")[1]);
		this.fieldGoalMade = fieldGoalMade;
		this.fieldGoalAttempted = fieldGoalAttempted;
		this.threePointerMade = threePointerMade;
		this.threePointerAttempted = threePointerAttempted;
		this.freeThrowMade = freeThrowMade;
		this.freeThrowAttempted = freeThrowAttempted;
		this.offensiveRebound = offensiveRebound;
		this.defensiveRebound = defensiveRebound;
		this.rebound = rebound;
		this.assist = assist;
		this.steal = steal;
		this.block = block;
		this.turnover = turnover;
		this.foul = foul;
		this.points = points;
		this.dirty = dirty;//Debugged
	}

	public PlayerInMatch(PlayerInMatch p) {
		this(p.name, p.position, p.minute, p.fieldGoalMade,
				p.fieldGoalAttempted, p.threePointerMade,
				p.threePointerAttempted, p.freeThrowMade, p.freeThrowAttempted,
				p.offensiveRebound, p.defensiveRebound, p.rebound, p.assist,
				p.steal, p.block, p.turnover, p.foul, p.points, p.dirty);
		second = Integer.parseInt(minute.split(":")[0]) * 60
				+ Integer.parseInt(minute.split(":")[1]);

	}
	/**
	 * The very first initialization from files
	 * @see njuse.ffff.data.MatchDataProcessor
	 * @param split
	 * @param path
	 */
	public PlayerInMatch(String[] split, String path) {
		// TODO Auto-generated method stub

		emptyStatProcess(split, path);

		name = split[0];
		if (!split[1].equals("")) {
			position = split[1].charAt(0);
		}
		minute = split[2];

		second = Integer.parseInt(minute.split(":")[0]) * 60
				+ Integer.parseInt(minute.split(":")[1]);

		fieldGoalMade = Integer.parseInt(split[3]);
		fieldGoalAttempted = Integer.parseInt(split[4]);
		threePointerMade = Integer.parseInt(split[5]);
		threePointerAttempted = Integer.parseInt(split[6]);
		freeThrowMade = Integer.parseInt(split[7]);
		freeThrowAttempted = Integer.parseInt(split[8]);
		offensiveRebound = Integer.parseInt(split[9]);
		defensiveRebound = Integer.parseInt(split[10]);
		rebound = Integer.parseInt(split[11]);
		assist = Integer.parseInt(split[12]);
		steal = Integer.parseInt(split[13]);
		block = Integer.parseInt(split[14]);
		turnover = Integer.parseInt(split[15]);
		foul = Integer.parseInt(split[16]);
		points = Integer.parseInt(split[17]);

	}

	public PlayerInMatch() {

	}

	/**
	 * Process empty data while reading<br>
	 * 
	 * @param split
	 * @param path
	 */
	void emptyStatProcess(String[] split, String path) {
		dirty = new ArrayList<Integer>();

		for (int i = 0; i < split.length; i++) {
			if (split[i].equals("null") || split[i].equals("None")) {
				dirty.add(i);
				System.out.println("Empty data:" + split.toString());
				System.out.println("in file " + path);
				switch (i) {
				case 0:
				case 1:
				case 2:
					split[2] = "0:0";
					break;
				default:
					split[i] = "0";

				}
			}
		}
	}

	/**
	 * Process all dirty statistics which are able to be marked or corrected.<br>
	 * including:<br>
	 * 1.minute N/A,then using 48minute * 5 - all other team members' minute<br>
	 * 2.The relations between three-pointer,free-throw,field goal made and
	 * points <br>3.Offensive rebounds,defensive rebounds and total rebounds. m:The
	 * match which the player played in.<br>
	 * 
	 * @param 我是卖萌的t
	 */
	// Not Finished.
	void dirtyStatProcess(TeamInMatch t) {
		/*
		 * Minute
		 */
		if (dirty.contains(2)) {
			this.second = 48 * 60 * 5 - t.secondInTotal;
			int tempMinute = t.secondInTotal / 60;
			int tempSecond = t.secondInTotal % 60;
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMinimumIntegerDigits(2);
			this.minute = nf.format(tempMinute) + ":" + nf.format(tempSecond);
		}
		/*
		 * Not played,any statistic should be 0
		 */
		if (this.second == 0
				&& (fieldGoalMade + fieldGoalAttempted + threePointerMade
						+ threePointerAttempted + freeThrowMade
						+ freeThrowAttempted + offensiveRebound
						+ defensiveRebound + rebound + assist + steal + block
						+ turnover + foul + points != 0)){
			dirty.add(2);
		}
		/*
		 * Some common sense
		 */
			if (fieldGoalMade > fieldGoalAttempted) {
				dirty.add(4);
			}
		if (threePointerMade > threePointerAttempted) {
			dirty.add(6);
		}
		if (freeThrowMade > freeThrowAttempted) {
			dirty.add(8);
		}
		if (offensiveRebound + defensiveRebound != rebound) {
			dirty.add(11);
		}
		if(foul>6){
			dirty.add(16);
		}
		
		/*
		 * Points correction
		 */
		if (dirty.contains(3)) {
			fieldGoalMade = (points - freeThrowMade - threePointerMade * 3) / 2
					+ threePointerMade;
		} else if (dirty.contains(5)) {
			threePointerMade = points - freeThrowMade - fieldGoalMade * 2;
		} else if (dirty.contains(7)) {
			freeThrowMade = points - fieldGoalMade * 2 - threePointerMade;
		} else if (dirty.contains(17) && this.points == 0 && this.second != 0) {
			points = fieldGoalMade * 2 + threePointerMade + freeThrowMade;
		}
		

	}

	public String getName() {
		return name;
	}

	public ArrayList<Integer> getDirty() {
		return dirty;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

}
