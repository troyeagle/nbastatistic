package njuse.ffff.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import njuse.ffff.po.TeamPO;
import njuse.ffff.presenter.TotalUIController;

public class TeamNameAndAbbr {
	private static Map<String,String> list_abbr = new HashMap<String,String>();
	private static Map<String,String> list_name = new HashMap<String,String>();
	
	private static TeamNameAndAbbr nameAbbrTransfer = null;
	
	private TeamNameAndAbbr() {
	}

	public static TeamNameAndAbbr getInstance() {
		if (nameAbbrTransfer == null) {
			nameAbbrTransfer = new TeamNameAndAbbr();
			init();
		}
		return nameAbbrTransfer;
	}
	
	private static void init(){//values[]{名称,缩写}
		ArrayList<TeamPO> teams = TotalUIController.getInstance()
				.getDataReadController().getTeamInfoAll(null);
		if(teams!=null){
			for(int i=0;i<teams.size();i++){
				if(teams.get(i)!=null){
					String name = teams.get(i).getName();
					String abbr = teams.get(i).getAbbr();
					if(name!=null&&abbr!=null){
						list_abbr.put(name, abbr);
						list_name.put(abbr, name);
					}
				}
			}
		}
	}
	
	public void updateTeams(){
		ArrayList<TeamPO> teams = TotalUIController.getInstance()
				.getDataReadController().getTeamInfoAll(null);
		if(teams!=null){
			for(int i=0;i<teams.size();i++){
				if(teams.get(i)!=null){
					String name = teams.get(i).getName();
					String abbr = teams.get(i).getAbbr();
					if(list_abbr.get(name)!=null&&list_name.get(abbr)!=null){
						list_abbr.put(name, abbr);
						list_name.put(abbr, name);
					}
				}
			}
		}
	}
	
	public String getName(String abbr){
		return list_name.get(abbr);
	}
	
	public String getAbbr(String name){
		return list_abbr.get(name);
	}
}
