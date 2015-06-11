package njuse.ffff.dataGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInMatch;

public class MatchAnalyser {
	MatchPO m;
	int currentMatchNumber;
	static int currentCount = 0;
	ArrayList<Integer> lost = new ArrayList<Integer>();
	/**
	 * 解析核心方法 解析所有数据
	 * 
	 * @param br
	 * @throws IOException
	 */
	public static void main(String[] args) {
		new MatchAnalyser().processAllMatchCache();
	}

	public void processAllMatch() {
		BufferedReader br = new HtmlReader()
				.execute("http://stat-nba.com/game/39.html");
		try {
			matchAnalyser(br);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processAllMatchCache() {
		currentMatchNumber = 30000;
		ExecutorService exe = Executors.newCachedThreadPool();
		MyThread th[] = new MyThread[70];

		for (int i = 0; i < 70; i++) {
			th[i] = new MyThread();
			exe.execute(th[i]);
		}

	}

	public synchronized void matchAnalyser(BufferedReader br)
			throws IOException {
		String name;
		Date date;
		String teamA, teamB;
		// ArrayList<String> members;
		ArrayList<Integer> scoreA, scoreB;
		ArrayList<PlayerInMatch> playerInTeamA;
		ArrayList<PlayerInMatch> playerInTeamB;
		String line = null;
		String get = null;
		for (int i = 0; i < 132; i++) {
			br.readLine();
		}
		line = br.readLine().trim() + br.readLine().trim();
		// String season = line;
		//System.out.println(line.trim());

		br.readLine();
		line = br.readLine();
		get = matchPattern(line, ">(.*)<font style=");
		date = Date.valueOf(get.trim());
		System.out.println(get.trim());
		for (int i = 0; i < 6; i++) {
			br.readLine();
		}
		// 队伍名1
		line = br.readLine();
		get = matchPattern(
				line,
				"<div><a style=\"font-size:14px;\" href=\"/team/(.*).html\" target=\"_blank\">(.*)</a><br/>");
		teamA = get.substring(0, 3);

		//System.out.println(get);
		// 赛前战绩（可选）
		line = br.readLine();
		// get = matchPattern(line, ">(.*)</a><br/>");
		// System.out.println(get);
		for (int i = 0; i < 8; i++) {
			br.readLine();// 152
		}
		// 队伍1总得分和主客场
		line = br.readLine();
		get = matchPattern(line, "<div class=\"score\">(.*)</div>");
		int scoreAtotal = Integer.parseInt(get.trim());
		//System.out.println(get);
		line = br.readLine();
		get = matchPattern(line,
				"style=\"float: left;margin-left: 20px;\">(.*)</div>");
		boolean host = get.equals("主场");
		//System.out.println(get);
		for (int i = 0; i < 6; i++) {
			br.readLine();// 160
		}
		line = br.readLine();
		scoreA = new ArrayList<Integer>();
		scoreA.add(scoreAtotal);
		while (line.trim().startsWith("<td")) {
			get = matchPattern(line, "<td class=\"number\">(.*)</td>");
			//System.out.print(get);
			scoreA.add(Integer.parseInt(get.trim()));
			br.readLine();
			br.readLine();
			br.readLine();
			line = br.readLine();
		}
		//System.out.println();
		for (int i = 0; i < 4; i++) {
			br.readLine();// 182
		}
		line = br.readLine();
		scoreB = new ArrayList<Integer>();
		while (line.trim().startsWith("<td")) {
			get = matchPattern(line, "<td class=\"number\">(.*)</td>");
			scoreB.add(Integer.parseInt(get.trim()));
			//System.out.print(get);
			br.readLine();
			br.readLine();
			br.readLine();
			line = br.readLine();
		}
		//System.out.println();
		br.readLine();
		br.readLine();// 200
		// 队伍2总得分和主客场
		line = br.readLine();
		get = matchPattern(line, "<div class=\"score\">(.*)</div>");
		scoreB.add(0, Integer.parseInt(get.trim()));
		//System.out.println(get);
		line = br.readLine();
		get = matchPattern(line,
				"style=\"float: right;margin-right: 20px;\">(.*)</div>");
		//System.out.println(get);
		for (int i = 0; i < 5; i++) {
			br.readLine();// 206
		}
		line = br.readLine();
		get = matchPattern(
				line,
				"<div><a style=\"font-size:14px;\" href=\"/team/(.*).html\" target=\"_blank\">(.*)</a><br/>");
		// 队伍2队名
		teamB = get.substring(0, 3);
		//System.out.println(get);

		while (!line.trim().startsWith("<td style=\"border:0px;\"></td>")) {
			line = br.readLine();
		}
		// 队伍1每个人的得分
		line = br.readLine();
		playerInTeamA = new ArrayList<PlayerInMatch>();
		while (line.trim().startsWith("<td class =\"normal player_name_out")) {
			String playerMatchLine = "";
			get = matchPattern(line,
					"<a href=\"/player/(.*).html\" target=\"_blank\">(.*)</a></td>");
			if (get.split(" ").length > 2) {
				String[] temp = get.split(" ");
				get = temp[0] + " ";
				for (int i = 1; i < temp.length; i++) {
					get += temp[i] + "_";
				}
			}
			playerMatchLine += get.trim() + " ";
			for (int i = 0; i < 21; i++) {
				line = br.readLine();
				get = matchPattern(line, "rank=\"(.*)\">");
				playerMatchLine += get.trim() + " ";
			}
			br.readLine();
			br.readLine();
			br.readLine();
			line = br.readLine();
		//	System.out.println(playerMatchLine);
			PlayerInMatch p = new PlayerInMatch(playerMatchLine.split(" "));
			playerInTeamA.add(p);
		}
		// 472
		for (int i = 0; i < 23; i++) {
			br.readLine();// 495
		}
		for (int i = 0; i < 19; i++) {
			line = br.readLine();
			get = matchPattern(line, ">(.*)</td>");
			//System.out.print(get);
		}
		//System.out.println();
		for (int i = 0; i < 4; i++) {
			br.readLine();
		}
		line = br.readLine();
		get = matchPattern(line,
				"<a target=\"_blank\" href=\"/coach/(.*).html\">(.*)</a>");
		//System.out.println(get);

		while (!line.trim().startsWith("<td style=\"border:0px;\"></td>")) {
			line = br.readLine();
		}
		/**
		 * B!
		 */
		line = br.readLine();
		playerInTeamB = new ArrayList<PlayerInMatch>();
		while (line.trim().startsWith("<td class =\"normal player_name_out")) {
			String playerMatchLine = "";
			get = matchPattern(line,
					"<a href=\"/player/(.*).html\" target=\"_blank\">(.*)</a></td>");
			if (get.split(" ").length > 2) {
				String[] temp = get.split(" ");
				get = temp[0] + " ";
				for (int i = 1; i < temp.length; i++) {
					get += temp[i] + "_";
				}
			}
			playerMatchLine += get.trim() + " ";
			for (int i = 0; i < 21; i++) {

				line = br.readLine();
				get = matchPattern(line, "rank=\"(.*)\">");
				playerMatchLine += get.trim() + " ";

			}
			br.readLine();
			br.readLine();
			br.readLine();
			line = br.readLine();
		//	System.out.println(playerMatchLine);
			PlayerInMatch p = new PlayerInMatch(playerMatchLine.split(" "));
			playerInTeamB.add(p);
		}
		// 472
		for (int i = 0; i < 23; i++) {
			br.readLine();// 495
		}
		for (int i = 0; i < 19; i++) {
			line = br.readLine();
			get = matchPattern(line, ">(.*)</td>");
			//System.out.print(get);
		}
		//System.out.println();
		for (int i = 0; i < 4; i++) {
			br.readLine();
		}
		line = br.readLine();
		get = matchPattern(line,
				"<a target=\"_blank\" href=\"/coach/(.*).html\">(.*)</a>");
		//System.out.println(get);

		name = date.toString() + " " + teamA + " vs " + teamB;
		m = new MatchPO(name, date, teamA, teamB, null, scoreA, scoreB,
				playerInTeamA, playerInTeamB);
		if (host) {
			m.setHostTeam("A");
		} else {
			m.setHostTeam("B");
		}
		m.teamProcess();
	}

	/**
	 * 匹配的核心方法，将所有匹配项输出，用空格隔开
	 * 
	 * @param origin
	 * @param normal
	 * @return
	 */
	public synchronized String matchPattern(String origin, String normal) {
		Pattern p = Pattern.compile(normal);
		Matcher m = p.matcher(origin);
		while (m.find()) {
			String result = "";
			for (int i = 1; i <= m.groupCount(); i++) {
				result += m.group(i) + " ";
			}// 这里多了一个空格，请注意
			return result;
		}
		return null;
	}

	class MyThread extends Thread {
		public void run() {
			int i;
			while ((i = getCount()) > 0) {
				BufferedReader br = new HtmlReader()
						.execute("http://stat-nba.com/game/" + i + ".html");
				try {
					System.out.println("Analysing Match" +i);
					matchAnalyser(br);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
						lost.add(i);
										
					continue;
				}
			}
		}
	}

	public synchronized int getCount() {
		if(!lost.isEmpty()){
			int a = lost.get(0);
			lost.remove(0);
			return a;
		}
		if (currentCount > currentMatchNumber) {
			return 0;
		}
		currentCount++;
		return currentCount;
	}
}
