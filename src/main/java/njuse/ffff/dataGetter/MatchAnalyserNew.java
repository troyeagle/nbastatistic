package njuse.ffff.dataGetter;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import njuse.ffff.po.MatchPO;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.util.DatabaseUtility;
import njuse.ffff.util.Mapper;

import org.apache.ibatis.session.SqlSession;

/**
 * 
 * @author Mebleyev.G.Longinus
 *
 */
public class MatchAnalyserNew {
	BufferedReader br;
	MatchPO matchPO;
	/**
	 * Variables
	 */
	String head;// 比赛名，唯一标识
	String name;
	String season;
	Date date;
	String teamA, teamB;
	ArrayList<String> members;
	String hostTeam;
	String place;
	ArrayList<Integer> scoreA, scoreB;
	ArrayList<Double> fourFactorsA, fourFactorsB;
	ArrayList<PlayerInMatchFull> playerInTeamA = new ArrayList<PlayerInMatchFull>();
	ArrayList<PlayerInMatchFull> playerInTeamB = new ArrayList<PlayerInMatchFull>();
	ArrayList<PlayerInMatchFull> teamTotal = new ArrayList<PlayerInMatchFull>();//????

	public void matchAnalyse(BufferedReader br, String head) throws IOException {
		this.head = head;
		this.br = br;
		String line, get;
		/**
		 * Total Score A,B
		 */
		readLineUntil("<td class=\"align_center padding\">");
		line = br.readLine();
		get = matchPattern(line, "</a><br>(.*)</span><br>");
		scoreA = new ArrayList<Integer>();
		scoreA.add(Integer.parseInt(get.trim()));
		br.readLine();
		line = readLineUntil("<span class=\"bold_text large_text\">");
		get = matchPattern(line, "</a><br>(.*)</span><br>");
		scoreB = new ArrayList<Integer>();
		scoreB.add(Integer.parseInt(get.trim()));
		/**
		 * Place
		 */
		line = readLineUntil("<td class=\"align_center padding_bottom small_text\"");
		place = matchPattern(line, "<br>(.*)</td>");

		/**
		 * Name,scoresPerSection
		 */

		line = readLineUntil("<td><a href=");
		teamA = matchPattern(line, "html\">(.*)</a></td>").trim();
		do {
			line = br.readLine();
			get = matchPattern(line, ">(.*)</td>");
			scoreA.add(Integer.parseInt(get.trim()));

		} while (line.startsWith("<td class=\"align_right\">"));

		line = readLineUntil("<td><a href=");
		teamB = matchPattern(line, "html\">(.*)</a></td>").trim();
		do {
			line = br.readLine();
			get = matchPattern(line, ">(.*)</td>");
			scoreB.add(Integer.parseInt(get.trim()));

		} while (line.startsWith("<td class=\"align_right\">"));

		/**
		 * FourFactors
		 */
		while (true) {
			try {
				readLineUntil("   <td align=\"left\" ><a href=");
				fourFactorsA = new ArrayList<Double>();
				line = br.readLine();
				do {
					get = matchPattern(line, ">(.*)</td>");
					try {
						fourFactorsA.add(Double.parseDouble(get.trim()));
					} catch (NumberFormatException e) {
						fourFactorsA.add(Double.NaN);
					}

					line = br.readLine();
				} while (line.startsWith("   <td align=\"right\""));

				readLineUntil("   <td align=\"left\" ><a href=");
				fourFactorsB = new ArrayList<Double>();
				line = br.readLine();
				do {
					get = matchPattern(line, ">(.*)</td>");
					try {
						fourFactorsB.add(Double.parseDouble(get.trim()));
					} catch (NumberFormatException e) {
						fourFactorsB.add(Double.NaN);
					}
					line = br.readLine();
				} while (line.startsWith("   <td align=\"right\""));
				break;
			} catch (NullPointerException e) {
				System.out.println("NullPointer in four factors:" + head);
				break;
			}
		}

		/**
		 * Date,Host Team
		 */
		// line =
		// readLineUntil("<div class=\"border_gray_double float_left margin_right padding\">");
		// get = matchPattern(line, "/boxscores/pbp/(.*).html");
		get = head;
		line = get.substring(0, 4) + "-" + get.substring(4, 6) + "-"
				+ get.substring(6, 8);
		date = Date.valueOf(line);
		hostTeam = get.substring(9, 12);
		if (hostTeam.equals(teamA)) {
			hostTeam = "A";
		} else {
			hostTeam = "B";
		}
		name = get;

		processATeam(playerInTeamA, teamA);
		processATeam(playerInTeamB, teamB);
		br.close();
	}

	public void processATeam(ArrayList<PlayerInMatchFull> playerInTeamA,
			String teamA) throws IOException {
		String line = "";
		String get = "";
		/**
		 * A,Basic,Starters
		 */
		readLineUntil("<tbody>");
		line = br.readLine();
		while (line.startsWith("<tr  class=")) {
			line = br.readLine();
			get = line.replaceAll("<(.+?)>", "").trim();
			PlayerInMatchFull player = new PlayerInMatchFull(get.trim(), null);
			String basic = "";
			for (int i = 0; i < 19; i++) {
				line = br.readLine();
				get = matchPattern(line, ">(.*)</td>");
				if (get.equals(" ")) {
					get = "-1 ";
				}
				basic += get;
			}
			player.setFirstOnMatch(1);
			player.setBasic(basic.split(" "));
			// System.out.println(basic);
			readLineUntil("</tr>");
			playerInTeamA.add(player);
			line = br.readLine();
		}
		boolean flag = false;
		if (playerInTeamA.size() == 5) {
			flag = true;
		}
		if (flag) {
			/**
			 * A,Basic,Reserve
			 */
			line = readLineUntil("<tr  class=");

			while (line.startsWith("<tr  class=")) {
				line = br.readLine();
				get = line.replaceAll("<(.+?)>", "").trim();
				PlayerInMatchFull player = null;
				try {
					player = new PlayerInMatchFull(get.trim(), null);
				} catch (NullPointerException e) {
					System.out.println(get);
					System.out.println(line);
					System.out.println(teamA);
					System.out.println();
				}

				String basic = "";
				boolean play = true;
				for (int i = 0; i < 19; i++) {
					line = br.readLine();
					if (line.contains("italic_text gray_text")) {
						play = false;
						break;
					}
					get = matchPattern(line, ">(.*)</td>");
					if (get.equals(" ")) {
						get = "-1 ";
					}
					basic += get;
				}
				if (play) {
					player.setBasic(basic.split(" "));
					playerInTeamA.add(player);
				}
				// System.out.println(basic);
				readLineUntil("</tr>");

				line = br.readLine();
			}
		}

		/**
		 * TeamTotalsA,Basic
		 */
		line = readLineUntil("<tr  class=");

		line = br.readLine();
		get = matchPattern(line, ">(.*)</td>");
		PlayerInMatchFull total = new PlayerInMatchFull(get.trim() + " "
				+ teamA, null);
		String basic = "";
		for (int i = 0; i < 19; i++) {
			line = br.readLine();
			get = matchPattern(line, ">(.*)</td>");
			if (get.equals(" ")) {
				get = "-1 ";
			}
			basic += get;
		}
		total.setBasic(basic.split(" "));
		// System.out.println(basic);
		readLineUntil("</tr>");
		// playerInTeamA.add(total);
		line = br.readLine();
		if (!flag) {
			return;
		}
		/**
		 * A,Advanced,Starters
		 */
		line = readLineUntil("<tr  class=");

		while (line.startsWith("<tr  class=")) {
			line = br.readLine();
			get = line.replaceAll("<(.+?)>", "").trim();
			PlayerInMatchFull player = null;
			for (PlayerInMatchFull p : playerInTeamA) {
				if (p.getName().equals(get.trim())) {
					player = p;
					break;
				}
			}

			String advanced = "";
			br.readLine();
			for (int i = 0; i < 14; i++) {
				line = br.readLine();
				get = matchPattern(line, ">(.*)</td>");
				if (get.equals(" ") || get.equals("") || get == null) {
					get = "-1 ";
				}
				advanced += get;
			}
			player.setAdvanced(advanced.split(" "));
			// System.out.println(advanced);
			readLineUntil("</tr>");
			playerInTeamA.add(player);
			line = br.readLine();
		}
		if (flag) {
			/**
			 * A.Advanced.reserve
			 */
			line = readLineUntil("<tr  class=");

			while (line.startsWith("<tr  class=")) {
				line = br.readLine();
				get = matchPattern(line, ">(.*)</a></td>");
				PlayerInMatchFull player = null;
				for (PlayerInMatchFull p : playerInTeamA) {
					if (p.getName().equals(get.trim())) {
						player = p;
						break;
					}
				}

				if (player == null) {
					readLineUntil("</tr>");
					line = br.readLine();
					continue;
				}
				String advanced = "";
				br.readLine();
				boolean play = true;
				for (int i = 0; i < 14; i++) {

					line = br.readLine();
					if (line.contains("italic_text gray_text")) {
						play = false;
						break;
					}
					get = matchPattern(line, ">(.*)</td>");
					if (get.equals(" ")) {
						get = "-1 ";
					}
					advanced += get;
				}
				if (play) {
					player.setAdvanced(advanced.split(" "));
					playerInTeamA.add(player);
				}

				// System.out.println(advanced);
				readLineUntil("</tr>");

				line = br.readLine();
			}
		}

		/**
		 * TeamTotal.Advanced
		 */
		readLineUntil("   <td align=");
		br.readLine();
		String advanced = "";
		for (int i = 0; i < 14; i++) {
			line = br.readLine();
			get = matchPattern(line, ">(.*)</td>");
			if (get.equals(" ")) {
				get = "-1 ";
			}
			advanced += get;
		}
		total.setAdvanced(advanced.split(" "));
		// System.out.println(advanced);
		playerInTeamA.add(total);
	}

	public String readLineUntil(String flag) throws IOException {
		String line = "";
		while (!line.startsWith(flag)) {
			try {
				line = br.readLine();
			} catch (SocketException e) {
				System.out.println("SocketReset");
			}

		}
		return line;
	}

	public String matchPattern(String origin, String normal) {
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

	public boolean execute(BufferedReader br, String season, String head) {
		this.season = season;
		while (true) {
			try {
				matchAnalyse(br, head);
				break;
			} catch (EOFException e1) {
				System.out.println("EOFException:" + head);
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		}
		try {
			insertMatch();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		}

	}

	public void insertMatch() throws Exception {
		DatabaseUtility.init();
		HashMap<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("idmatchinfo", name);
		inputMap.put("season", season);
		inputMap.put("date", date.toString());
		inputMap.put("teamA", teamA);
		inputMap.put("teamB", teamB);
		inputMap.put("host", hostTeam);
		inputMap.put("scoreInSectionA", scoreA.toString());
		inputMap.put("scoreInSectionB", scoreB.toString());
		inputMap.put("fourFactorsA", fourFactorsA.toString());
		inputMap.put("fourFactorsB", fourFactorsB.toString());
		SqlSession sqlSession = DatabaseUtility.getSqlSession();
		try{
			sqlSession.insert("insertAMatch", inputMap);
			sqlSession.commit();
		}catch(Exception e){
			
		}
		
		// sqlSession.close();
		playerInTeamA.addAll(playerInTeamB);
		for (PlayerInMatchFull pf : playerInTeamA) {
			Map<String, Object> playerInMatch = new HashMap<String, Object>();
			pf.setIdMatchInfo(head);
			
			Mapper mapper = sqlSession.getMapper(Mapper.class);
			
			Map<String, Object> selector = new HashMap<String, Object>();
			selector.put("plName", pf.getName());
			if(!pf.getName().contains("total")){
				Map<String, Object> receive = mapper.selectOne("playerinfo", null,
						selector);
				pf.setPlayerId((String) receive.get("idPlayerInfo"));
			}
			
			playerInMatch = pf.generateBasicMap();
			mapper.insert("playermatchinfo", playerInMatch);
			playerInMatch = pf.generateAdvancedMap();
			mapper.insert("playermatchinfoadv", playerInMatch);
			sqlSession.commit();
		}

	}

	public static void main(String[] args) {
		new MatchAnalyserNew()
				.execute(
						new HtmlReader()
								.execute("http://www.basketball-reference.com/boxscores/201010260BOS.html"),
						"10-11常规赛", "201010260BOS");

	}
}
