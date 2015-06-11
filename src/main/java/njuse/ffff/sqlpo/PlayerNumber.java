package njuse.ffff.sqlpo;

import java.util.HashMap;
import java.util.Map;

public class PlayerNumber {
	String name;
	String idPlayerInfo;
	int number;
	String tip;
	public PlayerNumber(String name, String idPlayerInfo, int number, String tip) {
		super();
		this.name = name;
		this.idPlayerInfo = idPlayerInfo;
		this.number = number;
		this.tip = tip;
	}
	
	public Map<String,Object> generateMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("idPlayerInfo", idPlayerInfo);
		map.put("number", number);
		map.put("tip", tip);
		return map;
	}
}
