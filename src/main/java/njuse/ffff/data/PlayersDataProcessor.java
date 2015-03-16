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
	private static String path="./CSEIII data/迭代一数据/players";
	
	private static String saveLoadPath = "./CSEIII data2/迭代一数据/players/info";
	static ArrayList<PlayerPO> players;
	
	public void readAndAnalysisPlayer() {
		players = new ArrayList<PlayerPO>();
		System.out.println("INFO:Player Info Initializing");
		long a = System.currentTimeMillis();
		File file = new File(path+"/info");
		File[] files = file.listFiles();
		FileReader fr;
		
		
		for (int i = 0; i <files.length; i++) {
			try {
				PlayerPO tempPlayerPO;
				
				fr = new FileReader(files[i]);
				BufferedReader br = new BufferedReader(fr);
				Pattern p = Pattern.compile("│(.*)║");
				br.readLine();
				String name = matchPattern(p,br.readLine());
				br.readLine();
				String number = matchPattern(p,br.readLine());
				br.readLine();
				char position = matchPattern(p,br.readLine()).charAt(0);
				br.readLine();
				String height = matchPattern(p,br.readLine());
				br.readLine();
				String weight = matchPattern(p,br.readLine());
				br.readLine();
				String birth = matchPattern(p,br.readLine());
				br.readLine();
				int age = Integer.parseInt(matchPattern(p,br.readLine()));
				br.readLine();
				String exp = matchPattern(p,br.readLine());
				br.readLine();
				String school = matchPattern(p,br.readLine());
				br.close();
				tempPlayerPO = new PlayerPO(name,number,position,height,weight,birth,age,exp,school,path);
				
				
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
		long b = System.currentTimeMillis();
		System.out.println(b-a);
	}

	
	public String matchPattern(Pattern p,String field){
		
		Matcher m = p.matcher(field);

		while(m.find()){
			return m.group(1).trim();
		}
		return null;
	}

	public void saveAsSerial() throws IOException{
		FileOutputStream fo;
		ObjectOutputStream os;
		System.out.println("INFO:Player Info Saving");
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
		System.out.println("INFO:Player Info Saved Successfully");
	}
	
	public void loadSerial() throws IOException, ClassNotFoundException{
		System.out.println("INFO:Player Info Loading");
		if(players!=null){
			players.clear();
		}else{
			players = new ArrayList<PlayerPO>();
		}
		FileInputStream fi;
		ObjectInputStream is;
		File file = new File(saveLoadPath);
		File[] files = file.listFiles();
		for(int i = 0;i<files.length;i++){
			fi = new FileInputStream(files[i]);
			is = new ObjectInputStream(fi);
			players.add((PlayerPO)is.readObject());
		}
		System.out.println("INFO:Player Info Loaded Successfully");
	}

}
