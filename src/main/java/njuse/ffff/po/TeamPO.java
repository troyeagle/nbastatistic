package njuse.ffff.po;

import java.io.Serializable;

public class TeamPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;//名称
	String abbr;//简称
	String state;//所在地
	String league;//联盟（东，西）
	String subLeague;//次级联盟
	String homeCourt;//主场
	String timeOfFoundation;//成立时间
	String pathOfLogo;//队徽的文件路径

	public TeamPO(String name, String abbr, String state, String league, String subLeague,
			String homeCourt, String timeOfFoundation, String dirOfLogos) {
		super();
		this.name = name;
		this.abbr = abbr;
		this.state = state;
		this.league = league;
		this.subLeague = subLeague;
		this.homeCourt = homeCourt;
		this.timeOfFoundation = timeOfFoundation;
		this.pathOfLogo = dirOfLogos + "/" + abbr + ".svg";
	}

	@Override
	public String toString() {
		return "TeamPO [name=" + name + ", abbr=" + abbr + ", state=" + state + ", league="
				+ league + ", subLeague=" + subLeague + ", homeCourt=" + homeCourt
				+ ", timeOfFoundation=" + timeOfFoundation + "]";
	}

	public String getName() {
		return name;
	}

	public String getAbbr() {
		return abbr;
	}

	public String getState() {
		return state;
	}

	public String getLeague() {
		return league;
	}

	public String getSubLeague() {
		return subLeague;
	}

	public String getHomeCourt() {
		return homeCourt;
	}

	public String getTimeOfFoundation() {
		return timeOfFoundation;
	}

	public String getPathOfLogo() {
		return pathOfLogo;
	}

}
