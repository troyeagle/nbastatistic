package njuse.ffff.sqlpo;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class PlayerInfo {
	String idPlayerInfo;
	String plName;
	String plFullName;
	String plPosition;
	char shoot;
	String plHeight;
	int plWeight;
	Date plBirth;
	String plBirthCity;
	String plHighSchool;
	String plUniv;
	String nbadebut;
	String hallOfFame;
	String draft;
	int experience;
	short plNumber;
	int plSalary;

	public PlayerInfo(String idPlayerInfo, String plName, String plFullName,
			String plPosition, char shoot, String plHeight, int plWeight,
			Date plBirth, String plBirthCity, String plHighSchool,
			String plUniv, String nbadebut, String hallOfFame, String draft,
			int experience, short plNumber, int plSalary) {
		super();
		this.idPlayerInfo = idPlayerInfo;
		this.plName = plName;
		this.plFullName = plFullName;
		this.plPosition = plPosition;
		this.shoot = shoot;
		this.plHeight = plHeight;
		this.plWeight = plWeight;
		this.plBirth = plBirth;
		this.plBirthCity = plBirthCity;
		this.plHighSchool = plHighSchool;
		this.plUniv = plUniv;
		this.nbadebut = nbadebut;
		this.experience = experience;
		this.hallOfFame = hallOfFame;
		this.draft = draft;
		this.plNumber = plNumber;
		this.plSalary = plSalary;
	}

	public PlayerInfo(Map<String, Object> map) {
		idPlayerInfo = (String) map.get("idPlayerInfo");
		plName = (String) map.get("plName");
		plFullName = (String) map.get("plFullName");
		plPosition = (String) map.get("plPosition");
		shoot = (char) map.get("shoot");
		plHeight = (String) map.get("plHeight");
		plWeight = (int) map.get("plWeight");
		plBirth = (Date) map.get("plBirth");
		plBirthCity = (String) map.get("plBirthCity");
		plHighSchool = (String) map.get("plHighSchool");
		plUniv = (String) map.get("plUniv");
		nbadebut = (String) map.get("nbadebut");
		hallOfFame = (String) map.get("hallOfFame");
		draft = (String) map.get("draft");
		experience = (int) map.get("experience");
		plNumber = (short) map.get("plNumber");
		plSalary = (int) map.get("plSalary");
	}

	public Map<String, Object> generateHashMap() {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("plBirthCity", plBirthCity);
		inputMap.put("idPlayerInfo", idPlayerInfo);
		inputMap.put("plName", plName);
		inputMap.put("plFullName", plFullName);
		inputMap.put("plPosition", plPosition);
		inputMap.put("shoot", shoot);
		inputMap.put("plHeight", plHeight);
		inputMap.put("plWeight", plWeight);
		inputMap.put("plBirth", plBirth);
		inputMap.put("plHighSchool", plHighSchool);
		inputMap.put("plUniv", plUniv);
		inputMap.put("hallOfFame", hallOfFame);
		inputMap.put("draft", draft);
		inputMap.put("nbadebut", nbadebut);
		inputMap.put("experience", experience);
		inputMap.put("plNumber", plNumber);
		inputMap.put("plSalary", plSalary);
		return inputMap;
	}

	public String getIdPlayerInfo() {
		return idPlayerInfo;
	}

	public String getPlName() {
		return plName;
	}

	public String getPlFullName() {
		return plFullName;
	}

	public String getPlPosition() {
		return plPosition;
	}

	public char getShoot() {
		return shoot;
	}

	public String getPlHeight() {
		return plHeight;
	}

	public int getPlWeight() {
		return plWeight;
	}

	public Date getPlBirth() {
		return plBirth;
	}

	public String getPlBirthCity() {
		return plBirthCity;
	}

	public String getPlHighSchool() {
		return plHighSchool;
	}

	public String getPlUniv() {
		return plUniv;
	}

	public String getNbadebut() {
		return nbadebut;
	}

	public String getHallOfFame() {
		return hallOfFame;
	}

	public String getDraft() {
		return draft;
	}

	public int getExperience() {
		return experience;
	}

	public short getPlNumber() {
		return plNumber;
	}

	public int getPlSalary() {
		return plSalary;
	}

}
