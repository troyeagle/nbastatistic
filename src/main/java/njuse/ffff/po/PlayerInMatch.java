package njuse.ffff.po;

public class PlayerInMatch {
	String name;
	char position;
	int minute;
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

	public PlayerInMatch(String name, char position, int minute,
			int fieldGoalMade, int fieldGoalAttempted, int threePointerMade,
			int threePointerAttempted, int freeThrowMade,
			int freeThrowAttempted, int offensiveRebound, int defensiveRebound,
			int rebound, int assist, int steal, int block, int turnover,
			int foul, int points) {
		super();
		this.name = name;
		this.position = position;
		this.minute = minute;
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
	}

	public PlayerInMatch(PlayerInMatch p) {
		this(p.name, p.position, p.minute, p.fieldGoalMade,
				p.fieldGoalAttempted, p.threePointerMade,
				p.threePointerAttempted, p.freeThrowMade, p.freeThrowAttempted,
				p.offensiveRebound, p.defensiveRebound, p.rebound, p.assist,
				p.steal, p.block, p.turnover, p.foul, p.points);
	}
	public PlayerInMatch(String[] split) {
		// TODO Auto-generated method stub
		name = split[0];
		if(!split[1].equals("")){
			position= split[1].charAt(0);
		}
		
		minute=Integer.parseInt(split[2]);
		fieldGoalMade=Integer.parseInt(split[3]);
		fieldGoalAttempted=Integer.parseInt(split[4]);
		threePointerMade=Integer.parseInt(split[5]);
		threePointerAttempted=Integer.parseInt(split[6]);
		freeThrowMade=Integer.parseInt(split[7]);
		freeThrowAttempted=Integer.parseInt(split[8]);
		offensiveRebound=Integer.parseInt(split[9]);
		defensiveRebound=Integer.parseInt(split[10]);
		rebound=Integer.parseInt(split[11]);
		assist=Integer.parseInt(split[12]);
		steal=Integer.parseInt(split[13]);
		block=Integer.parseInt(split[14]);
		turnover=Integer.parseInt(split[15]);
		foul=Integer.parseInt(split[16]);
		points=Integer.parseInt(split[17]);
	
	}
}
