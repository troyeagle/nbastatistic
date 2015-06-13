package njuse.ffff.dataservice;

import java.util.ArrayList;

import njuse.ffff.sqlpo.*;

public interface NewDataReaderService {
	//根据球员获得他参加的所有赛季
	ArrayList<String> selectSeasonsByPlayer(String playerId);
	//获得某赛季所有球员数据,其中season可包含po，attribute可包含total,average,per36,advance
	ArrayList<PlayerInMatchFull> getPlayersStatsAll(String season,String attribute);
	//获得某赛季某个球员的数据。
	PlayerInMatchFull getPlayerStatsSingle(String idPlayer,String season,String attribute);
	//获得某赛季某个球员所有数据项
	ArrayList<PlayerInMatchFull> getPlayerStats(String idPlayer,String season);
	//获得某一球员信息
	PlayerInfo getPlayerInfo(String name);
	//获得所有球员信息,attribute表示是否服役中
	ArrayList<PlayerInfo> getPlayerInfoAll(String attribute);
	//获得投篮信息
	PlayerShooting getPlayerShooting(String name,String season);
	//获得某个赛季球队数据信息
	ArrayList<TeamAverage> getTeamAverages(String season);
	//获得某赛季某球队数据信息
	TeamAverage getTeamAverageSingle(String name,String season);
	//获得某球队某赛季比赛信息
	
}
