package njuse.ffff.po;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerInMatch implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	char position;
	String minute;
	int second;
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

	public PlayerInMatch(String name, char position, String minute,
			int fieldGoalMade, int fieldGoalAttempted, int threePointerMade,
			int threePointerAttempted, int freeThrowMade,
			int freeThrowAttempted, int offensiveRebound, int defensiveRebound,
			int rebound, int assist, int steal, int block, int turnover,
			int foul, int points,ArrayList<Integer> dirty) {
		super();
		this.name = name;
		this.position = position;
		this.minute = minute;
		second=Integer.parseInt(minute.split(":")[0])*60+Integer.parseInt(minute.split(":")[1]);
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
				p.steal, p.block, p.turnover, p.foul, p.points,p.dirty);
		second=Integer.parseInt(minute.split(":")[0])*60+Integer.parseInt(minute.split(":")[1]);

	}

	public PlayerInMatch(String[] split,String path) {
		// TODO Auto-generated method stub
		dirty = new ArrayList<Integer>();
		
		for(int i=0;i<split.length;i++){
			if(split[i].equals("null")||split[i].equals("None")){
				dirty.add(i);
				System.out.println("Dirty data:"+split.toString());
				System.out.println("in file "+path);
				switch(i){
				case 0:
				case 1:
				case 2:
					split[2]="0:0";
					break;
				default:
					split[i]="0";
					
				}
			}
		}
		
		
		
		name = split[0];
		if(!split[1].equals("")){
			position= split[1].charAt(0);
		}
		minute=	split[2];

		second=Integer.parseInt(minute.split(":")[0])*60+Integer.parseInt(minute.split(":")[1]);

		
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
	
	public PlayerInMatch(){
		
	}
}
