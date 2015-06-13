package njuse.ffff.dataGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import njuse.ffff.sqlpo.TeamAverage;
import njuse.ffff.sqlpo.TeamAverageAdv;
import njuse.ffff.sqlpo.TeamInfo;
import njuse.ffff.util.DatabaseUtility;
import njuse.ffff.util.Mapper;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class TeamAnalyser {
	BufferedReader br;
	ArrayList<TeamAverage> myteam = new ArrayList<TeamAverage>();
	ArrayList<TeamAverage> opponent = new ArrayList<TeamAverage>();
	String name;
	public void urlAnalyser(String name){
		this.name = name;
		
		String basic = "http://www.basketball-reference.com/teams/"+name+"/";
		
		String urlTotal = "http://www.basketball-reference.com/teams/"+name+"/stats_totals.html";
		String urlPerGame = "http://www.basketball-reference.com/teams/"+name+"/stats_per_game.html";
		String urlOppoTotal = "http://www.basketball-reference.com/teams/"+name+"/opp_stats_totals.html";
		String urlOppoPerGame = "http://www.basketball-reference.com/teams/"+name+"/opp_stats_per_game.html";
		
		HtmlReader hr = new HtmlReader();
		try {
			System.out.println("Analysing basic:"+name);
			teamInfoAnalyse(hr.execute(basic));
			System.out.println("Analysing stats:"+name);
			teamStatAnalyse(hr.execute(urlTotal),name+" total");
			teamStatAnalyse(hr.execute(urlPerGame),name+" perGame");
			teamStatAnalyse(hr.execute(urlOppoTotal),name+" oppo_total");
			teamStatAnalyse(hr.execute(urlOppoPerGame),name+" oppo_perGame");
			teamAdvAnalyse();
			System.out.println("Analysing over."+name);
			hr.httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void teamInfoAnalyse(BufferedReader br) throws IOException{
		String location,teamNames,seasons,record;
		int playOffs,championships;
		this.br = br;
		String line;
		line=readLineUntil("<div class=\"stw\">");
		location=getWord(line).split(":")[1];
		line=br.readLine();
		teamNames = getWord(line).split(":")[1];
		line=br.readLine();
		seasons = getWord(line).split(":")[1];
		line=br.readLine();
		record = getWord(line).split(":")[1];
		line=br.readLine();
		playOffs = Integer.parseInt(getWord(line).split(":")[1].substring(0, 2).trim());
		line=br.readLine();
		championships = Integer.parseInt(getWord(line).split(":")[1].substring(0, 2).trim());
		TeamInfo ti = new TeamInfo(name,location,teamNames,seasons,record,playOffs,championships);
		DatabaseUtility.init();
		SqlSession sqlSession = DatabaseUtility.getSqlSession();
		Mapper mapper = sqlSession.getMapper(Mapper.class);
		try{
			mapper.insert("teaminfo", ti.generateMap());
		}catch(PersistenceException e){
			e.printStackTrace();
			sqlSession.close();
			return;
		}
	
		sqlSession.commit();
		sqlSession.close();
		
	}
	/**
	 * 包括存入数据库
	 * @param br
	 * @param attribute
	 * @throws IOException
	 */
	public void teamStatAnalyse(BufferedReader br,String attribute) throws IOException{
		this.br = br;
		String line;
		line=readLineUntil("<h2");
		line=getWord(line);
		line=line.split(" ")[0];
		int seasons=Integer.parseInt(line);
		
		ArrayList<TeamAverage> teamStatus = new ArrayList<TeamAverage>();
		for(int i = 0;i<seasons;i++){
			readLineUntil("<tr  class=");
			ArrayList<String> str = new ArrayList<String>();
			while(!(line=br.readLine()).contains("/tr")){
				str.add(getWord(line));
			}			
			teamStatus.add(new TeamAverage(attribute,str));
			
		}
		if(attribute.contains("oppo_perGame")){
			opponent.addAll(teamStatus);
		}else if(attribute.contains("perGame")){
			myteam.addAll(teamStatus);
		}
		DatabaseUtility.init();
		SqlSession sqlSession = DatabaseUtility.getSqlSession();
		Mapper mapper = sqlSession.getMapper(Mapper.class);
		for(TeamAverage t:teamStatus){
			Map<String,Object> map = t.generateMap();
			mapper.insert("teamaverage", map);
		}
		sqlSession.commit();
		sqlSession.close();
		
	}
	public void teamAdvAnalyse(){
		int size = myteam.size();
		SqlSession sqlSession = DatabaseUtility.getSqlSession();
		Mapper mapper = sqlSession.getMapper(Mapper.class);
		
		for(int i = 0;i<size;i++){
			TeamAverageAdv adv = new TeamAverageAdv(myteam.get(i),opponent.get(i));
			mapper.insert("teamaverageadv", adv.generateMap());
			sqlSession.commit();
		}
		sqlSession.close();
	}
	public String getWord(String line){
		return line.replaceAll("<(.+?)>", "");
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

	public static void main(String[] args){
		new TeamAnalyser().urlAnalyser("ATL");
	}

}
