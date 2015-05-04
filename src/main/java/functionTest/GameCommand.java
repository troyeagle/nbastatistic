package functionTest;

import de.tototec.cmdoption.CmdOption;

public class GameCommand {
	private boolean isPlayer = false;
	
	@CmdOption(names={"-player","-p"},description="Show Player Info")
	public void setPlayer(){
		isPlayer = true;
	}
	
	@CmdOption(names={"-team","-t"},description="Show Player Info")
	public void setTeam(){
	}
	
	public boolean isPlayer(){
		return this.isPlayer;
	}

}
