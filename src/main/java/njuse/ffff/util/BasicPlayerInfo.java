package njuse.ffff.util;

public class BasicPlayerInfo implements Comparable<BasicPlayerInfo> {
	private String lastName;
	private String rest;
	private String id;

	public BasicPlayerInfo(String name, String id) {
		this.id = id;
		String[] parts = name.split(" ");
		int len = parts.length - 1;
		lastName = parts[len];
		if (len > 0) {
			rest = parts[0];
			for (int i = 1; i < len; i++) {
				rest += " " + parts[i];
			}
		} else {
			rest = "";
		}
	}

	@Override
	public String toString() {
		String res;
		if (rest.isEmpty())
			res = lastName;
		else
			res = rest + " " + lastName;
		return res;
	}

	public String getID() {
		return id;
	}

	@Override
	public int compareTo(BasicPlayerInfo name) {
		int res = lastName.compareTo(name.lastName);
		if (res == 0)
			res = rest.compareTo(name.rest);
		return res;
	}
}
