package njuse.ffff.sqlpo;

import java.util.HashMap;
import java.util.Map;

public class TeamInfo {
	String name;
	String location,teamNames,seasons,record;
	int playOffs,championships;
	public TeamInfo(String name,String location, String teamNames, String seasons,
			String record, int playOffs, int championships) {
		super();
		this.name = name;
		this.location = location;
		this.teamNames = teamNames;
		this.seasons = seasons;
		this.record = record;
		this.playOffs = playOffs;
		this.championships = championships;
	}
	
	public Map<String,Object> generateMap(){
		Map<String,Object> inputMap = new HashMap<String,Object>();
		inputMap.put("name", name);
        inputMap.put("location",location);
        inputMap.put("teamNames",teamNames);
		inputMap.put("seasons",seasons);
		inputMap.put("record",record);
		inputMap.put("playOffs",playOffs);
		inputMap.put("championships",championships);
		return inputMap;
	}
}
