package njuse.ffff.live;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayByPlayMessages {
	String description;
	int event_num;
	String game_clock;
	int home_score;
	int if_pic;
	String last_name;
	String locationx;
	String locationy;
	int msg_type;
	int period;
	int person_id;
	int team_id;
	String team_name;
	int visitor_score;
	int wall_clock_as_int;
	
	public PlayByPlayMessages(JSONObject jo) throws JSONException{
		description = jo.getString("description");
		event_num = jo.getInt("event_num");
		game_clock = jo.getString("game_clock");
		home_score = jo.getInt("home_score");
		if_pic = jo.getInt("if_pic");
		last_name = jo.getString("last_name");
		locationx = jo.getString("locationx");
		locationy = jo.getString("locationy");
		msg_type = jo.getInt("msg_type");
		period = jo.getInt("period");
		person_id = jo.getInt("person_id");
		team_id = jo.getInt("team_id");
		team_name = jo.getString("team_name");
		visitor_score = jo.getInt("visitor_score");
		wall_clock_as_int = jo.getInt("wall_clock_as_int");
	}

	public String getDescription() {
		return description;
	}

	public int getEvent_num() {
		return event_num;
	}

	public String getGame_clock() {
		return game_clock;
	}

	public int getHome_score() {
		return home_score;
	}

	public int getIf_pic() {
		return if_pic;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getLocationx() {
		return locationx;
	}

	public String getLocationy() {
		return locationy;
	}

	public int getMsg_type() {
		return msg_type;
	}

	public int getPeriod() {
		return period;
	}

	public int getPerson_id() {
		return person_id;
	}

	public int getTeam_id() {
		return team_id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public int getVisitor_score() {
		return visitor_score;
	}

	public int getWall_clock_as_int() {
		return wall_clock_as_int;
	}
	
	
}
