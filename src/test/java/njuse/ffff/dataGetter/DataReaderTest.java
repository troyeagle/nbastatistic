package njuse.ffff.dataGetter;

import java.sql.Date;
import java.util.List;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.sqlpo.MatchInfo;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.sqlpo.PlayerInfo;
import njuse.ffff.sqlpo.PlayerShooting;
import njuse.ffff.sqlpo.TeamAverage;
import njuse.ffff.sqlpo.TeamAverageAdv;
import njuse.ffff.sqlpo.TeamInfo;

public class DataReaderTest {
	public void test(){
		NewDataReaderService serv = new DataReader();
//		List<PlayerInMatchFull> leadPlayerForDay = serv.getLeadPlayerForDay(Date.valueOf("2014-11-11"),"points");
//		List<PlayerInMatchFull> leadPlayerForSeason = serv.getLeadPlayerForSeason("14-15", "points");
//		List<TeamAverage> leadTeamForSeason = serv.getLeadTeamForSeason("14-15", "points");
		List<MatchInfo> matchInPeriod = serv.getMatchInPeriod(Date.valueOf("2014-11-11"), Date.valueOf("2014-11-30"));
//		PlayerInfo playerInfo = serv.getPlayerInfo("Kobe Bryant");
//		List<PlayerInfo> playerInfoAll = serv.getPlayerInfoAll("playing");
//		List<PlayerInMatchFull> playerInMatch = serv.getPlayerInMatch("198806070LAL");
//		PlayerShooting playershooting = serv.getPlayerShooting("jamesle01", "2014-15");
//		List<PlayerInfo> playersInTeam = serv.getPlayersInTeam("LAL", "14-15");
//		List<PlayerInMatchFull> playerStatsAll = serv.getPlayersStatsAll("2014-15", "per_game");
//		List<PlayerInMatchFull> playerStats = serv.getPlayerStats("jamesle01", "14-15");
//		PlayerInMatchFull playerStatsSingle = serv.getPlayerStatsSingle("jamesle01", "2014-15", "per_game");
//		TeamAverageAdv teamAverageAdv= serv.getTeamAverageAdv("LAL", "2014-15");
//		TeamAverage teamAverage = serv.getTeamAverageSingle("LAL", "2014-15", "total");//不能加s，
//		List<TeamAverage> teamAverages = serv.getTeamAverages("2014-15");
//		List<TeamInfo> teamInfoAll = serv.getTeamInfoAll();
//		List<PlayerInMatchFull> teamStatBySeason = serv.getTeamStatBySeason("LAL", "14-15");
//		PlayerInMatchFull teamStatSingle = serv.getTeamStatSingle("LAL", Date.valueOf("2012-10-30"));
		System.out.println();
	}
	public static void main(String args[]){
		new DataReaderTest().test();
	}
}
