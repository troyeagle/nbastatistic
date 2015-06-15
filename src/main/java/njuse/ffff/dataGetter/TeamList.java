package njuse.ffff.dataGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamList {
	static ArrayList<String> teams;
	public void getAllTeams() throws IOException {
		BufferedReader br;
		HtmlReader hr = new HtmlReader();
		do {
			br = hr.execute("http://www.basketball-reference.com/teams/");
		} while (br == null);
		
		StringBuilder sb = new StringBuilder();
		String line = "";
		while (line != null) {
			line = br.readLine();
			sb.append(line);
			if(line.contains("Defunct Franchises")){
				break;
			}

		}
		teams=matchPattern(sb.toString(),"<td align=\"left\" ><a href=\"/teams/(.+?)/\"");
		System.out.println();
	}
	public static void main(String[] args){
		new TeamList().process();
	}
	public void process(){
		TeamList teamlist = new TeamList();
		try {
			teamlist.getAllTeams();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExecutorService exe = Executors.newCachedThreadPool();
		TeamThread tt[] = new TeamThread[30];
		for(int i = 0;i<30;i++){
			tt[i]=new TeamThread(i);
			exe.execute(tt[i]);
		}
	}
	
	public synchronized ArrayList<String> matchPattern(String origin,
			String normal) {
		Pattern p = Pattern.compile(normal);
		Matcher m = p.matcher(origin);
		ArrayList<String> matches = new ArrayList<String>();
		while (m.find()) {
			matches.add(m.group(1));
		}// 这里多了一个空格，请注意
		return matches;
	}
	class TeamThread extends Thread{
		int i;
		TeamThread(int i){
			this.i  = i;
		}
		public void run(){
			new TeamAnalyser().urlAnalyser(teams.get(i));
		}
		
	}
}

