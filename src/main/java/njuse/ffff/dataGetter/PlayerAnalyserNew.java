package njuse.ffff.dataGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import njuse.ffff.sqlpo.PlayByPlay;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.sqlpo.PlayerInfo;
import njuse.ffff.sqlpo.PlayerNumber;
import njuse.ffff.sqlpo.PlayerSalary;
import njuse.ffff.sqlpo.PlayerShooting;
import njuse.ffff.util.DatabaseUtility;
import njuse.ffff.util.Mapper;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

/**
 * This class is used to analyzing a page of player in
 * www.basketball-reference.com
 * 
 * @author Mebleyev.G.Longinus
 *
 */
public class PlayerAnalyserNew {
	BufferedReader br;
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
	String draft;
	String hallOfFame;
	int experience;
	short plNumber;
	int plSalary;

	public void analyseSingle(BufferedReader br, String head)
			throws IOException, NullPointerException {
		DatabaseUtility.init();
		SqlSession sqlSession = DatabaseUtility.getSqlSession();
		this.br = br;
		head = head.replaceAll(".html", "");
		idPlayerInfo = head;
		String line = "", get = "";
		line = readLineUntil("<h1>");
		plName = matchPattern(line, "<h1>(.*)</h1>");
		// line = br.readLine();
		// get = matchPattern(line, "bold_text\">(.*)</span>");
		// plName = get;
		// line = readLineUntil("Position:");
		// plPosition = matchPattern(line, ">Position:</span> (.*)&nbsp").split(
		// "&")[0];
		// plPosition = positionConverter(plPosition);
		// shoot = matchPattern(line, "Shoots:</span> (.*)<br>").charAt(0);
		// plHeight = matchPattern(line,
		// "Height:</span> (.*)&nbsp").split("&")[0];
		// plWeight = Integer.parseInt(matchPattern(line,
		// "Weight:</span> (.*) lbs").trim());
		// line = readLineUntil("data-birth=");
		// plBirth = Date.valueOf(matchPattern(line,
		// "data-birth=\"(.*)\"><a href=").trim());
		// String alter = line.replaceAll("<(.+?)>", " ");
		// plBirthCity = alter.split("in")[1].trim();
		// line = readLineUntil("High School");
		// plHighSchool = matchPattern(line, "</span> (.*), <a href")
		// + matchPattern(line, "<a href=\"(.*)</a>").split(">")[1];
		// line = readLineUntil("<br><span class=");
		//
		// if (line.contains("College")) {
		// plUniv = matchPattern(line, "<a href=\"(.*)</a>").split(">")[1];
		// } else {
		// line = readLineUntil("<br><span class=\"bold_text\">NBA Debut");
		// }
		// nbadebut = matchPattern(line, "html\">(.*)</a>");
		// line = br.readLine();
		// if (line.contains("Experience")) {
		// experience = Short.parseShort(matchPattern(line,
		// ">Experience:</span> (.*) year").trim());
		// } else if (line.contains("Hall")) {
		// hallOfFame = matchPattern(line, "</span> (.*) (<");
		//
		// }
		try {
			while (!line.contains("margin_bottom_half margin_left_half")&&!line.contains("margin-bottom:32px")) {
				line = br.readLine();
				get += line;

				if (line.contains("data-birth=")) {
					plBirth = Date.valueOf(matchPattern(line,
							"data-birth=\"(.*)\"><a href=").trim());
				}

			}
			String[] split = get.split("<(.+?)>");
			ArrayList<String> processor = new ArrayList<String>();
			for (int i = 0; i < split.length; i++) {
				split[i] = split[i].trim();
				split[i] = split[i].replaceAll("&(.*);", "");
				if (split[i].length() > 2) {

					processor.add(split[i]);
				}
			}
			plFullName = processor.get(0);
			int t = processor.indexOf("Position:");
			if (t > 0) {
				plPosition = positionConverter(processor.get(t + 1));
			}
			t = processor.indexOf("Shoots:");
			if (t > 0) {
				shoot = processor.get(t + 1).charAt(0);
			}
			t = processor.indexOf("Height:");
			if (t > 0) {
				plHeight = processor.get(t + 1);
			}
			t = processor.indexOf("Weight:");
			if (t > 0) {
				plWeight = Integer.parseInt(processor.get(t + 1)
						.substring(0, 3));
			}
			t = processor.indexOf("Born:");
			if (t > 0&&processor.get(t+3).contains("in")) {
				try{
					plBirthCity = processor.get(t + 3).substring(2);
					plBirthCity+=processor.get(t + 4);
				}catch(Exception e){
					
				}
				
						
			}
			t = processor.indexOf("High School:");
			if (t > 0) {
				plHighSchool = processor.get(t + 1) + processor.get(t + 2);
			}
			t = processor.indexOf("College:");
			if (t > 0) {
				plUniv = processor.get(t + 1);
			}
			t = processor.indexOf("Draft:");
			if (t > 0) {
				try{
					draft = processor.get(t + 1) + processor.get(t + 2)
							+ processor.get(t + 3);
				}catch(Exception e){
					
				}
				
			}
			t = processor.indexOf("NBA Debut:");
			if (t > 0) {
				nbadebut = processor.get(t + 1);
				nbadebut = nbadebut.substring(0, nbadebut.length()-1);
			}
			t = processor.indexOf("Experience:");
			if (t > 0) {
				experience = Integer.parseInt(processor.get(t + 1)
						.substring(0, 2).trim());
			}
			t = processor.indexOf("Hall of Fame:");
			if (t > 0) {
				hallOfFame = processor.get(t + 1);
				hallOfFame = hallOfFame.substring(0, hallOfFame.length()-1);
			}
			
		}catch(NullPointerException e){
			System.out.println(head +" infomation error");
		}
		try{
			
			ArrayList<String> tips = new ArrayList<String>();
			ArrayList<Short> numbers = new ArrayList<Short>();
			// line = readLineUntil("<div class");
			while (true) {

				String tip = matchPattern(line, "tip=(.*)>");
				tips.add(tip);
				line = readLineUntil("<span style=");
				plNumber = Short.parseShort(line.replaceAll("<(.+?)>", "")
						.trim());
				numbers.add(plNumber);
				line = readLineUntil("<div class");
				if (!line.contains("poptip"))
					break;
			}
			List<ArrayList<String>> statusList = new ArrayList<ArrayList<String>>();
			/**
			 * 核心模块，决定每一块读出来的效果，注意这里的判断条件。
			 */
			String lastKey = "";
			while (true) {
				ArrayList<String> status = new ArrayList<String>();
				line = readLineUntil("<tr  class=\"");
				String key = "";
				if (line.contains("blank_table")) {
					continue;
				}
				if(line.contains("news")){
					continue;
				}
				if(line.contains("college")){
					continue;
				}
				if(line.contains("all_star")){
					break;
				}
				if (line.startsWith("<tr  class=\"\">")) {
					break;
				} else if (line.startsWith("<tr  class=\"full_table\"")) {
					key = matchPattern(line, "id=\"(.*)\"");
					lastKey = key.split("\\.")[0];
					status.add(key);

				} else if (line.contains("stat_total")) {// 包括total的，关键字不在一开始给出，要自动生成
					line = br.readLine();
					get = matchPattern(line, ">(.*)</td>");
					key = lastKey + " " + get;
					if (get == null || get.equals("")) {
						continue;
					}
					status.add(key);
					status.add(null);// 增加一个空项，对齐

				}
				if (key.length() < 2) {
					continue;
				}

				if (key.contains("shooting")) {

				} else if (key.contains("play-by-play")) {

				}
				do {
					line = br.readLine();
					get = line.replaceAll("<(.+?)>", "").trim();
					get = get.replaceAll("(&(.+?);)","").trim();
//					if (line.contains("left")) {
//						get = matchPattern(line, "<a href(.*)</a>");
//						if (get != null) {
//							get = matchPattern(line, "<a href(.*)</a>").split(
//									">")[1];
//						} else {
//							get = matchPattern(line, ">(.*)</td>");
//						}
//
//					} else {
//						get = matchPattern(line, ">(.*)</td>");
//					}
					status.add(get);
				} while (line.startsWith("   <td align="));
				statusList.add(status);
			}
			List<ArrayList<String>> salaries = new ArrayList<ArrayList<String>>();

			/**
			 * 工资问题
			 */
			boolean salary = true;
			try{
				readLineUntil("  <th data-stat=\"salary\"");
				line = readLineUntil("<tr  class=");

				while (line.startsWith("<tr  class=")) {
					ArrayList<String> items = new ArrayList<String>();
					line = br.readLine();
					items.add(matchPattern(line, ">(.*)</td>"));
					line = br.readLine();
					items.add(matchPattern(line, "html\">(.*)</a>"));
					line = br.readLine();
					items.add(matchPattern(line, "html\">(.*)</a>"));
					line = br.readLine();
					items.add(matchPattern(line, ">(.*)</td>"));
					line = br.readLine();
					line = br.readLine();
					salaries.add(items);
				}
			}catch(NullPointerException e){
				salary = false;
			}
			

			Iterator<ArrayList<String>> it = statusList.iterator();
			ArrayList<String> temp = null;
			/**
			 * 比赛数据表
			 */
			while (it.hasNext()) {
				String tableName = null;
				Map<String, Object> playerstatus = new HashMap<String, Object>();
				temp = it.next();
				if (temp.get(0).contains("pbp")) {
					PlayByPlay pbp = new PlayByPlay(plName, idPlayerInfo);
					pbp.setAttributes(temp);
					playerstatus = pbp.generateMap();
					tableName = "playbyplay";
				} else if (temp.get(0).contains("advanced")) {
					PlayerInMatchFull pf = new PlayerInMatchFull(plName,
							idPlayerInfo);
					pf.setAdvancedByArray(temp);
					playerstatus = pf.generateAdvancedMap();
					tableName = "playerstatadv";
				} else if (temp.get(0).contains("shooting")) {
					PlayerShooting ps = new PlayerShooting(plName, idPlayerInfo);
					ps.setAttributes(temp);
					playerstatus = ps.generateMap();
					tableName = "playershooting";
				} else {
					PlayerInMatchFull pf = new PlayerInMatchFull(plName,
							idPlayerInfo);
					pf.setBasicByArray(temp);
					playerstatus = pf.generateBasicMap();
					tableName = "playerstatinfo";
				}
				Mapper mapper = sqlSession.getMapper(Mapper.class);
				try {
					mapper.insert(tableName, playerstatus);
				} catch (PersistenceException e) {
					System.out.println(head);
					e.printStackTrace();
				}

				//sqlSession.commit();
			}
			/**
			 * 薪水表
			 */
			if(salary){
				for (ArrayList<String> i : salaries) {
					PlayerSalary ps = new PlayerSalary(plName, idPlayerInfo);
					ps.setAttributes(i);
					plSalary =Integer.parseInt(i.get(3).replaceAll(",", "").substring(1).trim());
					Mapper mapper = sqlSession.getMapper(Mapper.class);
					mapper.insert("playersalary", ps.generateMap());
					//sqlSession.commit();
				}
			}
		
			/**
			 * 球号表
			 */
			for (int i = 0; i < tips.size(); i++) {
				PlayerNumber pn = new PlayerNumber(plName, idPlayerInfo,
						numbers.get(i), tips.get(i));
				Mapper mapper = sqlSession.getMapper(Mapper.class);
				mapper.insert("playernumber", pn.generateMap());
				//sqlSession.commit();
			}
			sqlSession.commit();
		} catch (NullPointerException e) {
			//System.out.println(head + "do not fit the table");
		}

		br.close();
		/**
		 * Construct and insert into Database
		 * 如果不符合表，就只添加球员的基本信息
		 */
		PlayerInfo p = new PlayerInfo(idPlayerInfo, plName, plFullName,
				plPosition, shoot, plHeight, plWeight, plBirth, plBirthCity,
				plHighSchool, plUniv, nbadebut, hallOfFame, draft, experience,
				plNumber, plSalary);
		Map<String, Object> inputMap = p.generateHashMap();

		Mapper m = sqlSession.getMapper(Mapper.class);
		try {
			m.insert("playerinfo", inputMap);
			sqlSession.commit();
		} catch (PersistenceException e) {
			System.out.println(head);
			e.printStackTrace();
		}

		

		sqlSession.close();
	}

	private String positionConverter(String str) {
		str = str.replaceAll("Power Forward", "PF");
		str = str.replaceAll("Small Forward", "SF");
		str = str.replaceAll("Center", "C");
		str = str.replaceAll("Shooting Guard", "SG");
		str = str.replaceAll("Point Guard", "PG");
		str = str.replaceAll("Guard", "G");
		str = str.replaceAll("Forward", "F");
		str = str.replaceAll("and", "-");
		str = str.replaceAll(" ", "");
		return str;

	}

	public String readLineUntil(String flag) throws IOException {
		String line = "";
		while (!line.contains(flag)) {
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

	public static void main(String[] args) {
		HtmlReader hr = new HtmlReader();
		BufferedReader br = hr
				.execute("http://www.basketball-reference.com/players/w/walkebi01.html");
		PlayerAnalyserNew pa = new PlayerAnalyserNew();
		try {
			pa.analyseSingle(br, "walkebi01");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
