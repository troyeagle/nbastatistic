package functionTest;

import java.util.ArrayList;
import java.util.List;

import njuse.ffff.data.SeasonStatProcessor;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.util.Sort;
import test.data.PlayerHighInfo;
import test.data.PlayerHotInfo;
import test.data.PlayerKingInfo;
import test.data.PlayerNormalInfo;
import de.tototec.cmdoption.CmdOption;

public class PlayerCommand extends GameCommand{
	private int dataFormat = 1;//1表示平均数据，0表示总数据
	private int dataType = 1;//1表示基础数据，0表示高级数据
	private String hot_field = null;
	private String king_field = null;
	private int show_king_daily = 1;//1表示当天的，0表示赛季的
	private int number = 50;
	private String[] filter_field = null;
	private String[] sort_field = new String[]{"score.desc"};
	
	private DataReaderService readService = null;
	
	public PlayerCommand(DataReaderService service){
		readService = service;
	}

	@CmdOption(names = { "-avg" }, description = "show Avg", maxCount = 1, minCount = 0)
	public void setShowAvg() {
		dataFormat = 1;
	}
	
	@CmdOption(names = { "-total" }, description = "show total", maxCount = 1, minCount = 0)
	public void setShowTotal() {
		dataFormat = 0;
	}
	
	@CmdOption(names = { "-high" }, description = "show high", maxCount = 1, minCount = 0)
	public void setShowHigh() {
		dataType = 0;
		sort_field = new String[]{"realShot.desc"};
	}
	
	@CmdOption(names = { "-all" }, description = "show all", maxCount = 1, minCount = 0)
	public void setShowAll() {
		dataFormat = 1;
	}
	
	@CmdOption(names={"-hot"},args = {"field"},description="",maxCount=1,minCount=0)
	public void setShowHot(String field){
		hot_field = field;
		number = 5;
	}
	
	@CmdOption(names={"-king"},args = {"field"},description="",maxCount=1,minCount=0)
	public void setShowKing(String field){
		king_field = field;
		number = 5;
	}
	
	@CmdOption(names={"-daily"},description="",maxCount=1,minCount=0)
	public void setShowKingDaily(){
		show_king_daily = 1;
	}
	
	@CmdOption(names={"-season"},description="",maxCount=1,minCount=0)
	public void setShowKingSeasonly(){
		show_king_daily = 0;
	}
	
	@CmdOption(names={"-n"},args={"field"},description="",maxCount=1,minCount=0)
	public void setShowNumber(String field){
		number = Integer.parseInt(field);
	}
	
	@CmdOption(names={"-filter"},args={"field"},description="",maxCount=1,minCount=0)
	public void setShowFilter(String field){
		filter_field = field.split(",");
	}
	
	@CmdOption(names={"-sort"},args={"field"},description="",maxCount=1,minCount=0)
	public void setShowSort(String field){
		sort_field = field.split(",");
	}
	
	public ArrayList<Object> getPlayInfo(){
		ArrayList<Object> result = new ArrayList<Object>();
		if(hot_field!=null){
			int loc = -1;
			switch(hot_field){
			case "score":
				loc = 21;
				break;
			case "rebound":
				loc = 22;
				break;
			case "assist":
				loc = 23;
				break;
			}
			List<PlayerInAverage> playerList = readService.getImprovePlayer(readService.getCurrentSeason(), loc);
			for(int i=0;i<Math.min(number,playerList.size());i++){
				result.add(formHotPlayer(playerList.get(i), loc));
			}
		}
		else if(king_field!=null){
			int loc = -1;
			switch(king_field){
			case "score":
				loc = 14;
				break;
			case "rebound":
				loc = 8;
				break;
			case "assist":
				loc = 9;
				break;
			}
			if(show_king_daily==1){
				List<PlayerInMatchExtended> playerList = readService.getLeadPlayerForDay(readService.getCurrentDate(), loc);
				for(int i=0;i<number;i++){
					result.add(formKingPlayerDaily(playerList.get(i), loc));
				}
			}
			else{
				List<PlayerInAverage> playerList = readService.getLeadPlayerForSeason(readService.getCurrentSeason(), loc);
				for(int i=0;i<Math.min(number,playerList.size());i++){
					result.add(formKingPlayerSeasonly(playerList.get(i), loc));
				}
			}
		}
		else if(dataType==0){
			int[] condition = new int[sort_field.length];
			boolean[] order = new boolean[sort_field.length];
			for(int i=0;i<sort_field.length;i++){
				String[] parts = sort_field[i].split("[.]");
				if(parts.length==2){//暂时默认升序
					if(parts[1].equals("desc")){
						order[i] = true;
					}
				}
				else{
					order[i] = false;
				}
				switch(parts[0]){
				case "realShot":
					condition[i] = 17;
					break;
				case "GmSc":
					condition[i] = 16;
					break;
				case "shotEfficent":
					condition[i] = 18;
					break;
				case "reboundEfficient":
					condition[i] = 19;
					break;
				case "offendReboundEfficient":
					condition[i] = 20;
					break;
				case "defendReboundEfficient":
					condition[i] = 21;
					break;
				case "assistEfficient":
					condition[i] = 22;
					break;
				case "stealEfficient":
					condition[i] = 23;
					break;
				case "blockShotEfficient":
					condition[i] = 24;
					break;
				case "faultEfficient":
					condition[i] = 25;
					break;
				case "frequency":
					condition[i] = 26;
					break;
				}
			}
			SeasonStatProcessor seasonProcessor = readService.getSeasonStatProcessor(readService.getCurrentSeason());
			ArrayList<PlayerInAverage> playerList = seasonProcessor.getPlayerInAverage();
			Sort sort = new Sort();
			sort.sortPlayer(playerList, condition, order);
			for(int i=0;i<Math.min(number,playerList.size());i++){
				result.add(formHighPlayer(playerList.get(i)));
			}
		}
		else{
			SeasonStatProcessor seasonProcessor = readService.getSeasonStatProcessor(readService.getCurrentSeason());
			ArrayList<PlayerInAverage> playerList = seasonProcessor.getPlayerInAverage();
			//筛选
			if(filter_field!=null){
				ArrayList<PlayerInAverage> players_filtered = new ArrayList<PlayerInAverage>();
				for(int i=0;i<filter_field.length;i++){
					String[] parts = filter_field[i].split("[.]");
					switch(parts[0]){
					case "position":
						if(!parts[1].equals("All")){
							for(PlayerInAverage player:playerList){
								if(formatChangePos(player.getPosition()).contains(parts[1])){
									players_filtered.add(player);
								}
							}
							playerList = players_filtered;
							players_filtered = new ArrayList<PlayerInAverage>();
						}
						break;
					case "league":
						if(!parts[1].equals("All")){
							for(PlayerInAverage player:playerList){
								if(player.getLeague().equals(parts[1])){
									players_filtered.add(player);
								}
							}
							playerList = players_filtered;
							players_filtered = new ArrayList<PlayerInAverage>();
						}
						break;
					case "age":
						if(!parts[1].equals("All")){
							for(PlayerInAverage player:playerList){
								PlayerPO p = readService.getPlayerInfo(player.getName(), null);
								if(p!=null){
									switch(parts[1]){
									case "<=22":
										if(p.getAge()<=22){
											players_filtered.add(player);
										}
										break;
									case "22<X<=25":
										if(p.getAge()>22&&p.getAge()<=25){
											players_filtered.add(player);
										}
										break;
									case "25<X<=30":
										if(p.getAge()>25&&p.getAge()<=30){
											players_filtered.add(player);
										}
										break;
									case ">30":
										if(p.getAge()>30){
											players_filtered.add(player);
										}
										break;
									}
								}
							}
							playerList = players_filtered;
							players_filtered = new ArrayList<PlayerInAverage>();
						}
						break;
					}
				}
			}
			//排序
			int[] condition = new int[sort_field.length];
			boolean[] order = new boolean[sort_field.length];
			for(int i=0;i<sort_field.length;i++){
				String[] parts = sort_field[i].split("[.]");
				if(parts.length>=2){//默认升序
					if(parts[1].equals("desc")){
						order[i] = true;
					}
				}
				
				else{
					order[i] = false;
				}
				switch(parts[0]){
				case "point":
					condition[i] = 14;
					break;
				case "rebound":
					condition[i] = 8;
					break;
				case "assist":
					condition[i] = 9;
					break;
				case "blockShot":
					condition[i] = 11;
					break;
				case "steal":
					condition[i] = 10;
					break;
				case "foul":
					condition[i] = 13;
					break;
				case "fault":
					condition[i] = 12;
					break;
				case "minute":
					condition[i] = 31;
					break;
				case "efficient":
					condition[i] = 15;
					break;
				case "shot":
					condition[i] = 29;
					break;
				case "three":
					condition[i] = 28;
					break;
				case "penalty":
					condition[i] = 27;
					break;
				case "doubleTwo":
					condition[i] = 36;
					break;
				}
			}
			Sort sort = new Sort();
			if(dataFormat==1){
				sort.sortPlayer(playerList, condition, order);
			}
			else{
				sort.sortPlayerTotal(playerList, condition, order);
			}
			for(int i=0;i<Math.min(number,playerList.size());i++){
				PlayerPO player = readService.getPlayerInfo(playerList.get(i).getName(), null);
				result.add(formNormalPlayer(playerList.get(i),player));
			}
		}
		return result;
	}
	
	private PlayerHotInfo formHotPlayer(PlayerInAverage player,int loc){
		PlayerHotInfo hotPlayer = new PlayerHotInfo();
		hotPlayer.setName(player.getName());
		hotPlayer.setPosition(formatChangePos(player.getPosition()));
		hotPlayer.setTeamName(player.getTeamName());
		hotPlayer.setField(hot_field);
		switch(loc){
		case 21:
			hotPlayer.setUpgradeRate(player.getRecent5ScoreAdv());
			hotPlayer.setValue(player.getStatsAverage()[14]);
			break;
		case 22:
			hotPlayer.setUpgradeRate(player.getRecent5BlockAdv());
			hotPlayer.setValue(player.getStatsAverage()[8]);
			break;
		case 23:
			hotPlayer.setUpgradeRate(player.getRecent5AssistAdv());
			hotPlayer.setValue(player.getStatsAverage()[9]);
			break;
		}
		return hotPlayer;
	}
	
	private PlayerKingInfo formKingPlayerDaily(PlayerInMatchExtended player_day,int loc){
		PlayerKingInfo kingPlayer = new PlayerKingInfo();
		if(player_day!=null){
			kingPlayer.setName(player_day.getName());
			kingPlayer.setPosition(String.valueOf(player_day.getPosition()));
			kingPlayer.setTeamName(player_day.getTeam().getNameAbbr());
			kingPlayer.setField(king_field);
			switch(loc){
			case 14:
				kingPlayer.setValue(player_day.getPoints());
				break;
			case 8:
				kingPlayer.setValue(player_day.getRebound());
				break;
			case 9:
				kingPlayer.setValue(player_day.getAssist());
				break;
			}
		}
		return kingPlayer;
	}
	
	private PlayerKingInfo formKingPlayerSeasonly(PlayerInAverage player_season,int loc){
		PlayerKingInfo kingPlayer = new PlayerKingInfo();
		if(player_season!=null){
			kingPlayer.setName(player_season.getName());
			kingPlayer.setPosition(formatChangePos(player_season.getPosition()));
			kingPlayer.setTeamName(player_season.getTeamName());
			kingPlayer.setField(king_field);
			switch(loc){
			case 14:
				kingPlayer.setValue(player_season.getStatsAverage()[14]);
				break;
			case 8:
				kingPlayer.setValue(player_season.getStatsAverage()[8]);
				break;
			case 9:
				kingPlayer.setValue(player_season.getStatsAverage()[9]);
				break;
			}
		}
		return kingPlayer;
	}
	
	private PlayerHighInfo formHighPlayer(PlayerInAverage player){
		PlayerHighInfo highPlayer = new PlayerHighInfo();
		highPlayer.setName(player.getName());
		highPlayer.setPosition(formatChangePos(player.getPosition()));
		highPlayer.setTeamName(player.getTeamName());
		highPlayer.setLeague(player.getLeague());
		double[] average = player.getStatsAverage();
		highPlayer.setAssistEfficient(average[22]);
		highPlayer.setBlockShotEfficient(average[24]);
		highPlayer.setDefendReboundEfficient(average[21]);
		highPlayer.setFaultEfficient(average[25]);
		highPlayer.setFrequency(average[26]);
		highPlayer.setGmSc(average[16]);
		highPlayer.setOffendReboundEfficient(average[20]);
		highPlayer.setRealShot(average[17]);
		highPlayer.setReboundEfficient(average[19]);
		highPlayer.setShotEfficient(average[18]);
		highPlayer.setStealEfficient(average[23]);
		return highPlayer;
	}
	
	private PlayerNormalInfo formNormalPlayer(PlayerInAverage playerInfo,PlayerPO player){
		PlayerNormalInfo normalPlayer = new PlayerNormalInfo();
		normalPlayer.setName(playerInfo.getName());
		if(player!=null)
			normalPlayer.setAge(player.getAge());
		normalPlayer.setTeamName(playerInfo.getTeamName());
		normalPlayer.setNumOfGame(playerInfo.getEffective());
		double[] data = null;
		if(dataFormat==1){
			data = playerInfo.getStatsAverage();
			normalPlayer.setPenalty(data[27]);
			normalPlayer.setShot(data[29]);
			normalPlayer.setThree(data[28]);
		}
		else{
			double[] data_average = playerInfo.getStatsAverage();
			normalPlayer.setPenalty(data_average[27]);
			normalPlayer.setShot(data_average[29]);
			normalPlayer.setThree(data_average[28]);
			data = playerInfo.getStatsTotal();
		}
		normalPlayer.setStart(playerInfo.getFirstOnMatch());
		normalPlayer.setDefend(data[7]);
		normalPlayer.setOffend(data[6]);
		normalPlayer.setAssist(data[9]);
		normalPlayer.setBlockShot(data[11]);
		normalPlayer.setEfficiency(data[15]);
		normalPlayer.setFault(data[12]);
		normalPlayer.setFoul(data[13]);
		normalPlayer.setMinute(data[31]/60);
		normalPlayer.setPoint(data[14]);
		normalPlayer.setRebound(data[8]);
		normalPlayer.setSteal(data[10]);
		return normalPlayer;
	}
	
	private String formatChangePos(char[] pos){
		StringBuffer resultBuf = new StringBuffer();
		for(int i=0;i<pos.length;i++){
			resultBuf.append(pos[i]);
			if(i!=pos.length-1){
				resultBuf.append("-");
			}
		}
		return resultBuf.toString();
	}
}
