package njuse.ffff.data;

import java.io.IOException;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamPO;
import njuse.ffff.util.Filter;

public class DataReadController implements DataReaderService{
	PlayersDataProcessor player=  new PlayersDataProcessor();
	MatchDataProcessor match = new MatchDataProcessor();
	TeamDataProcessor team = new TeamDataProcessor();
	public PlayerInAverage getPlayerAverage(String name, Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public PlayerInMatchExtended[] getPlayerStatistics(String name,
			Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public PlayerPO getPlayerInfo(String name, Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public TeamInAverage getTeamAverage(String name, Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public MatchPO[] getTeamStatistics(String name, Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public TeamPO getTeamInfo(String name, Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException{
		new DataReadController().load();
	}

	public void process() throws IOException{

		player.readAndAnalysisPlayer();
		player.saveAsSerial();
		
		team.readAndAnalysisTeam();
		team.saveAsSerial();
		
		match.readAndAnalysisMatch();
		match.saveAsSerial();
		
		match.processAll();
		
		
	}
	
	public void load() throws IOException,ClassNotFoundException{
		player.loadSerial();
		team.loadSerial();
		match.loadSerial();
		match.processAll();
	}

}
