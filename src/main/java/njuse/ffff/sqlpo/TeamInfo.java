package njuse.ffff.sqlpo;

import java.util.HashMap;
import java.util.Map;

public class TeamInfo {
	String name;
	String location;
	String teamNames;
	String seasons;
	String record;
	int playOffs;
	int championships;

	public TeamInfo(String name, String location, String teamNames,
			String seasons, String record, int playOffs, int championships) {
		super();
		this.name = name;
		this.location = location;
		this.teamNames = teamNames;
		this.seasons = seasons;
		this.record = record;
		this.playOffs = playOffs;
		this.championships = championships;
	}

	public TeamInfo(Map<String, Object> map) {
		name = (String) map.get("name");
		location = (String) map.get("location");
		teamNames = (String) map.get("teamNames");
		seasons = (String) map.get("seasons");
		record = (String) map.get("record");
		playOffs = (int) map.get("playOffs");
		championships = (int) map.get("championships");
	}

	public Map<String, Object> generateMap() {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("name", name);
		inputMap.put("location", location);
		inputMap.put("teamNames", teamNames);
		inputMap.put("seasons", seasons);
		inputMap.put("record", record);
		inputMap.put("playOffs", playOffs);
		inputMap.put("championships", championships);
		return inputMap;
	}
}
