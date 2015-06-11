package njuse.ffff.sqlpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerSalary {
	String name;
	String idPlayerInfo;
	String season;
	String team;
	String league;
	String salary;
	
	public PlayerSalary(String name,String idPlayerInfo){
		this.name = name;
		this.idPlayerInfo = idPlayerInfo;
	}
	public void setAttributes(ArrayList<String> arr){	
		season = arr.get(0);
		team = arr.get(1);		
		league = arr.get(2);
		salary = arr.get(3);	
	}
	public Map<String,Object> generateMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("idPlayerInfo", idPlayerInfo);
		map.put("season", season);
		map.put("team", team);
		map.put("league", league);
		map.put("salary", salary);
		return map;
	}
}
