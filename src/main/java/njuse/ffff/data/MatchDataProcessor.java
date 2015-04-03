package njuse.ffff.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInMatch;
/**
 * Read Match info
 * 在迭代二需要进行增量读取的修改
 * 4.3修改完成
 * @author Mebleyev.G.Longinus
 *
 */
public class MatchDataProcessor {
	private static String path = "./CSEIII data/迭代一数据/matches";

	private static String saveLoadPath = "./CSEIII data2/迭代一数据/matches";
	static ArrayList<MatchPO> matches;

	public void readAndAnalysisMatch(){
		matches = new ArrayList<MatchPO>();
		//System.out.println("INFO:MatchPO Info Initializing");
		File file = new File(path);
		File[] files = file.listFiles();


		for (int i = 0; i < files.length; i++) {
			readAndAnalyzeNew(files[i].getName());
		}
	}

	public void saveAsSerial() throws IOException {
//		System.out.println("Info:Match Info Saving");
		FileOutputStream fo;
		ObjectOutputStream os;
		File file = new File(saveLoadPath);
		if (!file.exists() || !file.isDirectory()) {
			if (file.mkdirs()) {
				System.out.println("创建比赛存储文件夹" + saveLoadPath);
			} else {
				System.out.println("创建文件夹失败！存储失败。");
				return;
			}

		}
		for (MatchPO p : matches) {
			fo = new FileOutputStream(saveLoadPath + "/" + p.getName() + ".ser");
			os = new ObjectOutputStream(fo);
			os.writeObject(p);
			os.close();
		}
		System.out.println("Info:Match Info Saved successfully");
	}
	
	public void loadSerial() throws IOException, ClassNotFoundException{
//		System.out.println("INFO:Match Info Loading");
		if(matches!=null){
			matches.clear();
		}else{
			matches = new ArrayList<MatchPO>();
		}

		FileInputStream fi;
		ObjectInputStream is;
		File file = new File(saveLoadPath);
		File[] files = file.listFiles();
		for(int i = 0;i<files.length;i++){
			fi = new FileInputStream(files[i]);
			is = new ObjectInputStream(fi);
			matches.add((MatchPO)is.readObject());
		}
		System.out.println("INFO:Match Info loaded successfully");
	}

	public void processAll(){
		List<MatchPO> ma = matches;
		Collections.sort(ma);
		for(MatchPO m:matches){
			m.teamProcess();
		}
	}
	//Iteration 2
	public MatchPO readAndAnalyzeNew(String fileName){
		
		try {
			FileReader fr = new FileReader(path+"/"+fileName);
			Date date;
			String teamA, teamB;
			ArrayList<String> members = new ArrayList<String>();
			ArrayList<Integer> scoreA, scoreB;
			ArrayList<PlayerInMatch> playerInTeamA = new ArrayList<PlayerInMatch>();
			ArrayList<PlayerInMatch> playerInTeamB = new ArrayList<PlayerInMatch>();

			scoreA = new ArrayList<Integer>();
			scoreB = new ArrayList<Integer>();

			MatchPO match;
			//关于日期处理和总分处理
			BufferedReader br = new BufferedReader(fr);
			String[] total = br.readLine().split(";");
			String year[] = fileName.substring(0, 5).split("-");
			if(total[0].startsWith("1")||total[0].startsWith("09")){
				total[0]=year[0]+"-"+total[0];
			}else{
				total[0]=year[1]+"-"+total[0];
			}
			DateFormat df = new SimpleDateFormat("yy-MM-dd");
			date = df.parse(total[0]);
			teamA = total[1].split("-")[0];
			teamB = total[1].split("-")[1];
			scoreA.add(Integer.parseInt(total[2].split("-")[0]));
			scoreB.add(Integer.parseInt(total[2].split("-")[1]));

			// Each session score
			String[] subScore = br.readLine().split(";");
			for (int j = 0; j < subScore.length; j++) {
				scoreA.add(Integer.parseInt(subScore[j].split("-")[0]));
				scoreB.add(Integer.parseInt(subScore[j].split("-")[1]));
			}
			br.readLine();
			// TeamA
			String playerStat;
			int firstFive = 0;
			boolean firstOnMatch = true;
			while ((playerStat = br.readLine()).length() > 6) {
				String[] split = playerStat.split(";");
				firstFive++;
				if(firstFive <5){
					firstOnMatch = true;
				}else{
					firstOnMatch = false;
				}
				playerInTeamA.add(new PlayerInMatch(split,path+"/"+fileName,firstOnMatch));
				members.add(split[0]);
			}
			// TeamB
			firstFive = 0;
			while ((playerStat = br.readLine())!=null) {
				String[] split = playerStat.split(";");
				firstFive++;
				if(firstFive <5){
					firstOnMatch = true;
				}else{
					firstOnMatch = false;
				}
				playerInTeamB.add(new PlayerInMatch(split,path+"/"+fileName,firstOnMatch));
				members.add(split[0]);
			}

			match = new MatchPO(fileName, date, teamA, teamB,
					members, scoreA, scoreB, playerInTeamA, playerInTeamB);
			matches.add(match);
			br.close();
			return match;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("FileNotFound!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
