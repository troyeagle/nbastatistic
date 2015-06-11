package njuse.ffff.dataGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import njuse.ffff.po.PlayerPO;


public class PlayerAnalyser {
	public static void main(String[] args){
		new PlayerAnalyser().processAllPlayers();
	}
	public void processAllPlayers() {
		BufferedReader br = new HtmlReader()
				.execute("http://stat-nba.com/player/195.html");
		try {
			playerAnalyser(br);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void playerAnalyser(BufferedReader br) throws IOException {
		String name;
		String number;
		char[] position;
		String height;
		String weight;
		String birth;
		String birthCity;
		int age;
		String exp;
		String school;
		
		String line = "";
		String get = "";
		while( !line.trim().startsWith("<div class=\"detail\">")){
			line=br.readLine();
		}
		line = br.readLine();
		name = matchPattern(line, "全　　名:</div>(.*)</div>");
		line = br.readLine();
		String[] split = matchPattern(line,"位　　置:</div>(.*)</div>").split("-");
		position = new char[split.length];
		for(int i = 0;i<split.length;i++){
			switch(split[i].trim()){
			case "前锋":
				position[i]='F';
				break;
			case "中锋":
				position[i]='C';
				break;
			case "后卫":
				position[i]='G';
				break;
			}
		}
		line = br.readLine();
		height = matchPattern(line,"身　　高:</div>(.*)</div>");
		line=br.readLine();
		weight = matchPattern(line,"体　　重:</div>(.*)</div>");
		line=br.readLine();
		birth = matchPattern(line,"出生日期:</div>(.*)</div>");
		line=br.readLine();
		birthCity = matchPattern(line,"出生城市:</div>(.*)</div>");
		line=br.readLine();
		school = matchPattern(line,"</div>(.*)</div>");
		line = br.readLine();
		if(line.contains("大　　学")){
			school = matchPattern(line,"</div>(.*)</div>");
			line=br.readLine();
		}
		number = matchPattern(line,"</div>(.*)<a href=");
		age = Calendar.getInstance().get(Calendar.YEAR)-Integer.parseInt(birth.substring(0, 4));
		/**
		 * 建立对象
		 */
		PlayerPO info = new PlayerPO(name,number,position,height,weight,birth,age,"",school,"");
		while(!line.contains("选秀情况")){
			line=br.readLine();	
		}
		line=br.readLine();
		String chosen = matchPattern(line,"\t+(.*)<a href=\"../team")+matchPattern(line,">(.*)</a>选中<")+"选中";
		System.out.println(chosen);
		br.readLine();
		line=br.readLine();
		int salary =Integer.parseInt(matchPattern(line,"当前薪金:</div>(.*)万美元<").trim());
		info.setBirthCity(birthCity);
		info.setSalary(salary);
		
		while(!line.trim().startsWith("<td style=\"border:0px;\"></td>")){
			line=br.readLine();
		}
		
		String playerAverage="";
		while(line.trim().startsWith("<td style=\"border:0px;\"></td>")){
			line=br.readLine();
			get=matchPattern(line,"target=\"_blank\">(.*)</a></td>");
			playerAverage+=get.trim()+",";
			br.readLine();
			line=br.readLine();
			get=matchPattern(line,"rank=\"(.*)\"><a href");
			playerAverage+=get.trim()+",";
			br.readLine();
			br.readLine();
			for(int i = 0;i<23;i++){
				line=br.readLine();
				playerAverage+=matchPattern(line, "rank=\"(.*)\"").trim()+",";
			}
			System.out.println(playerAverage);
			br.readLine();
			br.readLine();
			line=br.readLine();
			playerAverage="";
		}
		while(!line.trim().startsWith("<td class =\"normal season\" colspan =")){
			line=br.readLine();
		}
		exp=matchPattern(line,"colspan =\"2\">(.*)</td>");
		info.setExp(exp);
		playerAverage="";
		for(int i =0;i<24;i++){
			line=br.readLine();
			playerAverage+=matchPattern(line,">(.*)</td>");
		}System.out.println(playerAverage);
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
}
