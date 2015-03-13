package njuse.ffff.po;

import java.io.Serializable;

public class TeamPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String abbr;
	String state;
	String league;
	String subLeague;
	String homeCourt;
	String timeOfFoundation;
	String pathOfLogo;
	public TeamPO(String name, String abbr, String state, String league,
			String subLeague, String homeCourt, String timeOfFoundation,String dirOfLogos) {
		super();
		this.name = name;
		this.abbr = abbr;
		this.state = state;
		this.league = league;
		this.subLeague = subLeague;
		this.homeCourt = homeCourt;
		this.timeOfFoundation = timeOfFoundation;
		this.pathOfLogo = dirOfLogos+"/"+abbr+".svg";
	}
	@Override
	public String toString() {
		return "TeamPO [name=" + name + ", abbr=" + abbr + ", state=" + state
				+ ", league=" + league + ", subLeague=" + subLeague
				+ ", homeCourt=" + homeCourt + ", timeOfFoundation="
				+ timeOfFoundation + "]";
	}
	public String getName() {
		return name;
	}
	
	
	
}
