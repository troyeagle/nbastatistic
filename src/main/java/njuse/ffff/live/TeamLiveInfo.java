package njuse.ffff.live;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeamLiveInfo {
	ArrayList<PlayerLiveInfo> offPlayers;
	ArrayList<PlayerLiveInfo> onPlayers;
	int team_id;
	String team_name;
	int ass;
	String biggest;
	int blo;
	int def;
	String disqualifications;
	String ejections;
	String fast_points;
	String field;
	int field_att;
	int field_made;
	String flag;
	int fouls;
	String free;
	int free_att;
	int free_made;
	int minutes;
	int off;
	String off_turnovers;
	int points;
	String points_paint;
	int ste;
	String tec_fouls;
	String three;
	int three_att;
	int three_made;
	int turn;
	
	TeamLiveInfo(JSONObject jo) throws JSONException{
		team_id = jo.getJSONObject("team_info").getInt("team_id");
		team_name = jo.getJSONObject("team_info").getString("team_name");
		JSONObject total = jo.getJSONObject("total");
		ass =total.getInt("ass");
		biggest =total.getString("biggest");
		blo =total.getInt("blo");
		def =total.getInt("def");
		disqualifications =total.getString("disqualifications");
		ejections =total.getString("ejections");
		fast_points =total.getString("fast_points");
		field =total.getString("field");
		field_att =total.getInt("field_att");
		field_made =total.getInt("field_made");
		flag =total.getString("flag");
		fouls =total.getInt("fouls");
		free =total.getString("free");
		free_att =total.getInt("free_att");
		free_made =total.getInt("free_made");
		minutes =total.getInt("minutes");
		off =total.getInt("off");
		off_turnovers =total.getString("off_turnovers");
		points =total.getInt("points");
		points_paint =total.getString("points_paint");
		ste =total.getInt("ste");
		tec_fouls =total.getString("tec_fouls");
		three =total.getString("three");
		three_att =total.getInt("three_att");
		three_made =total.getInt("three_made");
		turn =total.getInt("turn");
		
		JSONArray ons = jo.getJSONArray("on");
		onPlayers = new ArrayList<PlayerLiveInfo>();
		for(int i = 0;i<ons.length();i++){
			JSONObject player = ons.getJSONObject(i);
			onPlayers.add(new PlayerLiveInfo(player,true));
		}
		
		offPlayers = new ArrayList<PlayerLiveInfo>();
		try {
		JSONArray offs = jo.getJSONArray("off");
		for(int i = 0;i<offs.length();i++){
			JSONObject player = offs.getJSONObject(i);
			offPlayers.add(new PlayerLiveInfo(player,false));
		}
		} catch (Exception e) {
			
		}
	}

	public ArrayList<PlayerLiveInfo> getOffPlayers() {
		return offPlayers;
	}

	public ArrayList<PlayerLiveInfo> getOnPlayers() {
		return onPlayers;
	}

	public int getTeam_id() {
		return team_id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public int getAss() {
		return ass;
	}

	public String getBiggest() {
		return biggest;
	}

	public int getBlo() {
		return blo;
	}

	public int getDef() {
		return def;
	}

	public String getDisqualifications() {
		return disqualifications;
	}

	public String getEjections() {
		return ejections;
	}

	public String getFast_points() {
		return fast_points;
	}

	public String getField() {
		return field;
	}

	public int getField_att() {
		return field_att;
	}

	public int getField_made() {
		return field_made;
	}

	public String getFlag() {
		return flag;
	}

	public int getFouls() {
		return fouls;
	}

	public String getFree() {
		return free;
	}

	public int getFree_att() {
		return free_att;
	}

	public int getFree_made() {
		return free_made;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getOff() {
		return off;
	}

	public String getOff_turnovers() {
		return off_turnovers;
	}

	public int getPoints() {
		return points;
	}

	public String getPoints_paint() {
		return points_paint;
	}

	public int getSte() {
		return ste;
	}

	public String getTec_fouls() {
		return tec_fouls;
	}

	public String getThree() {
		return three;
	}

	public int getThree_att() {
		return three_att;
	}

	public int getThree_made() {
		return three_made;
	}

	public int getTurn() {
		return turn;
	}



}
