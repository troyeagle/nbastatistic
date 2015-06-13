package njuse.ffff.live;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayerLiveInfo {
	int ass;
	int blo;
	int def;
	String field;
	int fouls;
	String free;
	String minutes;
	int off;
	int player_id;
	String player_name;
	int points;
	String position;
	int ste;
	String three;
	int turn;

	PlayerLiveInfo(JSONObject player, boolean on) throws JSONException {

		player_id = player.getInt("player_id");
		player_name = player.getString("player_name");
		position = player.getString("position");
		if (on) {
			ass = player.getInt("ass");
			blo = player.getInt("blo");
			def = player.getInt("def");
			field = player.getString("field");
			fouls = player.getInt("fouls");
			free = player.getString("free");
			minutes = player.getString("minutes");
			off = player.getInt("off");
			points = player.getInt("points");
			ste = player.getInt("ste");
			three = player.getString("three");
			turn = player.getInt("turn");
		}

	}

	public int getAss() {
		return ass;
	}

	public int getBlo() {
		return blo;
	}

	public int getDef() {
		return def;
	}

	public String getField() {
		return field;
	}

	public int getFouls() {
		return fouls;
	}

	public String getFree() {
		return free;
	}

	public String getMinutes() {
		return minutes;
	}

	public int getOff() {
		return off;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public int getPoints() {
		return points;
	}

	public String getPosition() {
		return position;
	}

	public int getSte() {
		return ste;
	}

	public String getThree() {
		return three;
	}

	public int getTurn() {
		return turn;
	}
}
