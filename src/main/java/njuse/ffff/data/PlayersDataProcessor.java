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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import njuse.ffff.po.PlayerPO;

public class PlayersDataProcessor {
	private static String path="C:/Users/Mebleyev.G.Longinus/Downloads/CSEIII data/迭代一数据/players";
	
	private static String saveLoadPath = "C:/Users/Mebleyev.G.Longinus/Downloads/CSEIII data2/迭代一数据/players/info";
	private static ArrayList<PlayerPO> players;
	
	public void readAndAnalysisPlayer() {
		players = new ArrayList<PlayerPO>();
		System.out.println("INFO:Player Info Initializing");
		File file = new File(path+"/info");
		File[] files = file.listFiles();
		FileReader fr;
		
		
		for (int i = 0; i <files.length; i++) {
			try {
				PlayerPO tempPlayerPO;
				System.out.println(files[i].getPath());
				fr = new FileReader(files[i]);
				BufferedReader br = new BufferedReader(fr);
				br.readLine();
				String name = matchPattern("Name		│(.*)║",br.readLine());
				br.readLine();
				String number = matchPattern("Number		│(.*)║",br.readLine());
				br.readLine();
				char position = matchPattern("Position	│(.*)║",br.readLine()).charAt(0);
				br.readLine();
				String height = matchPattern("Height		│(.*)║",br.readLine());
				br.readLine();
				String weight = matchPattern("Weight		│(.*)║",br.readLine());
				br.readLine();
				String birth = matchPattern("Birth		│(.*)║",br.readLine());
				br.readLine();
				int age = Integer.parseInt(matchPattern("Age		│(.*)║",br.readLine()));
				br.readLine();
				String exp = matchPattern("Exp		│(.*)║",br.readLine());
				br.readLine();
				String school = matchPattern("School		│(.*)║",br.readLine());
				br.close();
				tempPlayerPO = new PlayerPO(name,number,position,height,weight,birth,age,exp,school,path);
				System.out.println(tempPlayerPO.toString());
				
				players.add(tempPlayerPO);
				
				
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
		System.out.println("INFO:Player Info Initialized!");
	}

	
	public String matchPattern(String pattern,String field){
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(field);

		while(m.find()){
			return m.group(1).trim();
		}
		return null;
	}

	public void saveAsSerial() throws IOException{
		FileOutputStream fo;
		ObjectOutputStream os;
		File file = new File(saveLoadPath);
		if(!file.exists()||!file.isDirectory()){
			if(file.mkdirs()){
				System.out.println("创建队伍存储文件夹"+saveLoadPath);
			}else{
				System.out.println("创建文件夹失败！存储失败。");
				return;
			}
			
		}
		for(PlayerPO p:players){
			fo = new FileOutputStream(saveLoadPath+"/"+p.getName()+".ser");
			os = new ObjectOutputStream(fo);
			os.writeObject(p);
			os.close();
		}
		
	}
	
	public void loadSerial() throws IOException, ClassNotFoundException{
		players.clear();
		FileInputStream fi;
		ObjectInputStream is;
		File file = new File(saveLoadPath);
		File[] files = file.listFiles();
		for(int i = 0;i<files.length;i++){
			fi = new FileInputStream(files[i]);
			is = new ObjectInputStream(fi);
			players.add((PlayerPO)is.readObject());
		}
		
	}

}
