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
	public PlayerInfo(String idPlayerInfo, String plName, String plFullName,String plPosition,
			char shoot, String plHeight, int plWeight, Date plBirth,
			String plBirthCity, String plHighSchool, String plUniv,
			String nbadebut,String hallOfFame,String draft, int experience, short plNumber, int plSalary) {
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
	
	public Map<String,Object> generateHashMap(){
		Map<String,Object> inputMap = new HashMap<String,Object>();
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
	
}
