package njuse.ffff.po;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * 储存一场比赛中某一球员的基础数据
 * 由MatchDataProcessor构建
 * @author Mebleyev.G.Longinus
 *
 */
public class PlayerInMatch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	char position;
	String minute;
	int playerId;
	Date date;

	protected int fieldGoalMade;
	protected int fieldGoalAttempted;
	protected int threePointerMade;
	protected int threePointerAttempted;
	protected int freeThrowMade;
	protected int freeThrowAttempted;
	protected int offensiveRebound;
	protected int defensiveRebound;
	protected int rebound;
	protected int assist;
	protected int steal;
	protected int block;
	protected int turnover;
	protected int foul;
	protected int points;

	boolean firstOnMatch;
	ArrayList<Integer> dirty;
	int second;// Calculate by Minute;

	public PlayerInMatch(String name, char position, String minute,
			int fieldGoalMade, int fieldGoalAttempted, int threePointerMade,
			int threePointerAttempted, int freeThrowMade,
			int freeThrowAttempted, int offensiveRebound, int defensiveRebound,
			int rebound, int assist, int steal, int block, int turnover,
			int foul, int points, ArrayList<Integer> dirty,boolean firstOnMatch) {
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
		this.firstOnMatch = firstOnMatch;
	}

	public PlayerInMatch(PlayerInMatch p) {
		this(p.name, p.position, p.minute, p.fieldGoalMade,
				p.fieldGoalAttempted, p.threePointerMade,
				p.threePointerAttempted, p.freeThrowMade, p.freeThrowAttempted,
				p.offensiveRebound, p.defensiveRebound, p.rebound, p.assist,
				p.steal, p.block, p.turnover, p.foul, p.points, p.dirty,p.firstOnMatch);
		second = Integer.parseInt(minute.split(":")[0]) * 60
				+ Integer.parseInt(minute.split(":")[1]);

	}
	/**
	 * The very first initialization from files
	 * @see njuse.ffff.data.MatchDataProcessor
	 * @param split
	 * @param path
	 */
	public PlayerInMatch(String[] split, String path,boolean firstOnMatch) {
		// TODO Auto-generated method stub
		this.firstOnMatch = firstOnMatch;
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
	public PlayerInMatch(String[] split){
		this.playerId = Integer.parseInt(split[0]);
		this.name=split[1].replace("_", " ");
		this.firstOnMatch = split[2].equals("1");
		this.minute=split[3]+":00";
		this.fieldGoalMade=Integer.parseInt(split[5]);
		this.fieldGoalAttempted=Integer.parseInt(split[6]);
		this.threePointerMade = Integer.parseInt(split[8]);
		this.threePointerAttempted = Integer.parseInt(split[9]);
		this.freeThrowMade= Integer.parseInt(split[11]);
		this.freeThrowAttempted=Integer.parseInt(split[12]);
		this.rebound = Integer.parseInt(split[14]);
		this.offensiveRebound = Integer.parseInt(split[15]);
		this.defensiveRebound = Integer.parseInt(split[16]);
		this.assist = Integer.parseInt(split[17]);
		this.steal = Integer.parseInt(split[18]);
		this.block = Integer.parseInt(split[19]);
		this.turnover = Integer.parseInt(split[20]);
		this.foul = Integer.parseInt(split[21]);
		this.points = Integer.parseInt(split[22]);
		
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
//FIXME				System.out.println("Empty data:" + split.toString());
//				System.out.println("in file " + path);
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

	public char getPosition() {
		return position;
	}

	public String getMinute() {
		return minute;
	}

	public Date getDate() {
		return date;
	}

	public int getFieldGoalMade() {
		return fieldGoalMade;
	}

	public int getFieldGoalAttempted() {
		return fieldGoalAttempted;
	}

	public int getThreePointerMade() {
		return threePointerMade;
	}

	public int getThreePointerAttempted() {
		return threePointerAttempted;
	}

	public int getFreeThrowMade() {
		return freeThrowMade;
	}

	public int getFreeThrowAttempted() {
		return freeThrowAttempted;
	}

	public int getOffensiveRebound() {
		return offensiveRebound;
	}

	public int getDefensiveRebound() {
		return defensiveRebound;
	}

	public int getRebound() {
		return rebound;
	}

	public int getAssist() {
		return assist;
	}

	public int getSteal() {
		return steal;
	}

	public int getBlock() {
		return block;
	}

	public int getTurnover() {
		return turnover;
	}

	public int getFoul() {
		return foul;
	}

	public boolean isFirstOnMatch() {
		return firstOnMatch;
	}

	public int getSecond() {
		return second;
	}
	@Override
	public String toString(){
		return name;
	}
}
