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
import java.util.ArrayList;

import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInMatch;

public class MatchDataProcessor {
	private static String path = "C:/Users/Mebleyev.G.Longinus/Downloads/CSEIII data/迭代一数据/matches";

	private static String saveLoadPath = "C:/Users/Mebleyev.G.Longinus/Downloads/CSEIII data2/迭代一数据/matches";
	private static ArrayList<MatchPO> matches;

	public void readAndAnalysisMatch() {
		matches = new ArrayList<MatchPO>();
		System.out.println("INFO:MatchPO Info Initializing");
		File file = new File(path);
		File[] files = file.listFiles();
		FileReader fr;

		for (int i = 0; i < files.length; i++) {
			try {

				String date;
				String teamA, teamB;
				ArrayList<String> members = new ArrayList<String>();
				ArrayList<Integer> scoreA, scoreB;
				ArrayList<PlayerInMatch> playerInTeamA = new ArrayList<PlayerInMatch>();
				ArrayList<PlayerInMatch> playerInTeamB = new ArrayList<PlayerInMatch>();

				scoreA = new ArrayList<Integer>();
				scoreB = new ArrayList<Integer>();

				MatchPO match;
				fr = new FileReader(files[i]);
				BufferedReader br = new BufferedReader(fr);
				String[] total = br.readLine().split(";");

				date = total[0];
				teamA = total[1].split("-")[0];
				teamB = total[1].split("-")[1];
				scoreA.add(Integer.parseInt(total[2].split("-")[0]));
				scoreB.add(Integer.parseInt(total[2].split("-")[1]));

				// Each session score
				String[] subScore = br.readLine().split(";");
				for (int j = 0; i < subScore.length; i++) {
					scoreA.add(Integer.parseInt(subScore[j].split("-")[0]));
					scoreB.add(Integer.parseInt(subScore[j].split("-")[1]));
				}
				br.readLine();
				// TeamA
				String playerStat;
				while ((playerStat = br.readLine()).length() > 6) {
					String[] split = playerStat.split("");
					playerInTeamA.add(new PlayerInMatch(split));
					members.add(split[0]);
				}
				// TeamB
				while ((playerStat = br.readLine()).length() > 6) {
					String[] split = playerStat.split("");
					playerInTeamB.add(new PlayerInMatch(split));
					members.add(split[0]);
				}

				match = new MatchPO(files[i].getName(), date, teamA, teamB,
						members, scoreA, scoreB, playerInTeamA, playerInTeamB);
				matches.add(match);
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("FileNotFound!");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void saveAsSerial() throws IOException {
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

	}
	
	public void loadSerial() throws IOException, ClassNotFoundException{
		matches.clear();
		FileInputStream fi;
		ObjectInputStream is;
		File file = new File(saveLoadPath);
		File[] files = file.listFiles();
		for(int i = 0;i<files.length;i++){
			fi = new FileInputStream(files[i]);
			is = new ObjectInputStream(fi);
			matches.add((MatchPO)is.readObject());
		}
		
	}

}
