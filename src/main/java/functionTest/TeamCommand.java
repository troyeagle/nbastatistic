package functionTest;

import java.util.ArrayList;
import java.util.List;

import njuse.ffff.data.SeasonStatProcessor;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.TeamInAverage;
import njuse.ffff.po.TeamPO;
import njuse.ffff.util.Sort;
import test.data.TeamHighInfo;
import test.data.TeamHotInfo;
import test.data.TeamNormalInfo;
import de.tototec.cmdoption.CmdOption;

public class TeamCommand extends GameCommand{
	private int dataFormat = 1;//1表示平均数据，0表示总数据
	private int dataType = 1;//1表示基础数据，0表示高级数据
	private String hot_field = null;
	private int number = 30;
	private String[] sort_field = new String[]{"score.desc"};
	
	private DataReaderService readService = null;
	
	public TeamCommand(DataReaderService service){
		readService = service;
	}
	
	@CmdOption(names = { "-total" }, description = "show total", maxCount = 1, minCount = 0)
	public void setShowTotal() {
		dataFormat = 0;
	}
	
	@CmdOption(names = { "-avg" }, description = "show avg", maxCount = 1, minCount = 0)
	public void setShowAvg() {
		dataFormat = 1;
	}
	
	@CmdOption(names = { "-high" }, description = "show high", maxCount = 1, minCount = 0)
	public void setShowHigh() {
		dataType = 0;
		sort_field = new String[]{"winRate.desc"};
	}
	
	@CmdOption(names = { "-all" }, description = "show all", maxCount = 1, minCount = 0)
	public void setShowAll() {
		dataFormat = 1;
	}
	
	@CmdOption(names = { "-hot" }, args={"field"}, description = "show high", maxCount = 1, minCount = 0)
	public void setShowHot(String field) {
		hot_field = field;
		number = 5;
	}
	
	@CmdOption(names={"-n"},args={"field"},description="",maxCount=1,minCount=0)
	public void setShowNumber(String field){
		number = Integer.parseInt(field);
	}
	
	@CmdOption(names={"-sort"},args={"field"},description="",maxCount=1,minCount=0)
	public void setShowSort(String field){
		sort_field = field.split(",");
	}
	
	public ArrayList<Object> getTeamInfo(){
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
			case "blockShot":
				loc = 21;
				break;
			case "steal":
				loc = 22;
				break;
			case "foul":
				loc = 23;
				break;
			case "shot":
				loc = 21;
				break;
			case "three":
				loc = 22;
				break;
			case "penalty":
				loc = 23;
				break;
			case "defendRebound":
				loc = 21;
				break;
			case "offendRebound":
				loc = 22;
				break;
			}
			List<TeamInAverage> teamList = readService.getLeadTeamForSeason(readService.getCurrentSeason(), loc);
			for(int i=0;i<Math.min(number, teamList.size());i++){
				result.add(formHotTeam(teamList.get(i), loc));
			}
		}
		else if(dataType==0){
			//排序
			int[] condition = new int[sort_field.length];
			boolean[] order = new boolean[sort_field.length];
			for(int i=0;i<sort_field.length;i++){
				String[] parts = sort_field[i].split("[.]");
				if(parts[1].equals("desc")){
					order[i] = true;
				}
				else{
					order[i] = false;
				}
				switch(parts[0]){
				case "winRate":
					condition[i] = 23;
					break;
				case "offendRound":
					condition[i] = 24;
					break;
				case "offendEfficient":
					condition[i] = 25;
					break;
				case "defendEfficient":
					condition[i] = 26;
					break;
				case "offendReboundEfficient":
					condition[i] = 27;
					break;
				case "defendReboundEfficient":
					condition[i] = 28;
					break;
				case "stealEfficient":
					condition[i] = 29;
					break;
				case "assistEfficient":
					condition[i] = 30;
					break;
				}
			}
			SeasonStatProcessor seasonProcessor = readService.getSeasonStatProcessor(readService.getCurrentSeason());
			ArrayList<TeamInAverage> teamList = seasonProcessor.getTeamInAverage();
			Sort sort = new Sort();
			sort.sortTeam(teamList, condition, order);//TODO
			for(int i=0;i<Math.min(number,teamList.size());i++){
				result.add(formHighTeam(teamList.get(i)));
			}
		}
		else{
			//排序
			int[] condition = new int[sort_field.length];
			boolean[] order = new boolean[sort_field.length];
			for(int i=0;i<sort_field.length;i++){
				String[] parts = sort_field[i].split("[.]");
				if(parts[1].equals("desc")){
					order[i] = true;
				}
				else{
					order[i] = false;
				}
				switch(parts[0]){
				case "point":
					condition[i] = 17;
					break;
				case "rebound":
					condition[i] = 16;
					break;
				case "assist":
					condition[i] = 18;
					break;
				case "blockShot":
					condition[i] = 19;
					break;
				case "steal":
					condition[i] = 20;
					break;
				case "foul":
					condition[i] = 21;
					break;
				case "fault":
					condition[i] = 22;
					break;
				case "shot":
					condition[i] = 25;
					break;
				case "three":
					condition[i] = 26;
					break;
				case "penalty":
					condition[i] = 26;
					break;
				case "defendRebound":
					condition[i] = 26;
					break;
				case "offendRebound":
					condition[i] = 26;
					break;
				}
			}
			SeasonStatProcessor seasonProcessor = readService.getSeasonStatProcessor(readService.getCurrentSeason());
			ArrayList<TeamInAverage> teamList = seasonProcessor.getTeamInAverage();
			Sort sort = new Sort();
			if(dataFormat==1){
				sort.sortTeam(teamList, condition, order);//TODO
			}
			else{
				//TODO sortTeamTotal
			}
			for(int i=0;i<Math.min(number, teamList.size());i++){
				result.add(formNormalTeam(teamList.get(i)));
			}
		}
		return result;
	}
	
	private TeamHotInfo formHotTeam(TeamInAverage team,int loc){
		TeamHotInfo hotTeam = new TeamHotInfo();
		hotTeam.setTeamName(team.getName());
		TeamPO t = readService.getTeamInfo(team.getName(), null);
		if(t!=null)
			hotTeam.setLeague(t.getLeague());
		hotTeam.setField(hot_field);
		double[] average = team.getStatsAverage();
		hotTeam.setValue(average[loc]);
		return hotTeam;
	}
	
	private TeamHighInfo formHighTeam(TeamInAverage team){
		TeamHighInfo highTeam = new TeamHighInfo();
		highTeam.setTeamName(team.getName());
		double[] average = team.getStatsAverage();
		highTeam.setWinRate(team.getWinningRatio());
		highTeam.setOffendRound(average[24]);
		highTeam.setOffendEfficient(average[25]);
		highTeam.setDefendEfficient(average[26]);
		highTeam.setOffendReboundEfficient(average[27]);
		highTeam.setDefendReboundEfficient(average[28]);
		highTeam.setStealEfficient(average[29]);
		highTeam.setAssistEfficient(average[30]);
		return highTeam;
	}
	
	private TeamNormalInfo formNormalTeam(TeamInAverage team){
		TeamNormalInfo normalTeam = new TeamNormalInfo();
		normalTeam.setTeamName(team.getName());
		double[] data = null;
		if(dataFormat==1){
			data = team.getStatsAverage();
			normalTeam.setShot(data[21]);
			normalTeam.setThree(data[22]);
			normalTeam.setPenalty(data[23]);
		}
		else{
			data = team.getStatsTotal();
		}
		normalTeam.setPoint(data[14]);
		normalTeam.setRebound(data[8]);
		normalTeam.setAssist(data[9]);
		normalTeam.setBlockShot(data[11]);
		normalTeam.setSteal(data[10]);
		normalTeam.setFoul(data[13]);
		normalTeam.setFault(data[12]);
		normalTeam.setDefendRebound(data[7]);
		normalTeam.setOffendRebound(data[6]);
		return normalTeam;
	}
}
