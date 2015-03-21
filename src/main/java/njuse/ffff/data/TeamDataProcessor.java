package njuse.ffff.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import njuse.ffff.po.TeamPO;
/**
 * 读取所有队伍数据（在一个文件中
 * @author Mebleyev.G.Longinus
 *
 */
public class TeamDataProcessor {
	private static String path;
	static ArrayList<TeamPO> teams;
	private final int numberOfTeams = 30;
	private static String saveLoadPath = "./CSEIII data2/迭代一数据/team";
	public void readAndAnalysisTeam() throws IOException{
		System.out.println("INFO:Team Info Initializing");
		path = "./CSEIII data/迭代一数据/teams";
		FileReader fr = new FileReader(path+"/teams");
		BufferedReader br = new BufferedReader(fr);
		
		teams = new ArrayList<TeamPO>();
		br.readLine();
		for(int i = 0;i<numberOfTeams;i++){
			
			String info = br.readLine();
			TeamPO tempTeamPO;
//			String name =null;
//			String abbr= null;
//			String league = null;
//			String state = null;
//			String subLeague = null;
//			String homeCourt = null;
//			String timeOfFoundation = null;
			
			String[] split = info.split("│");
			split[0]=split[0].substring(1);
			split[6]=split[6].substring(0, 3);
			
			for(int j = 0;j<6;j++){
				split[j]=split[j].trim();
			}
			
/*			Pattern p = Pattern.compile("║(.*)	│");
			Matcher m = p.matcher(info);
			
			while(m.find()){
				name = m.group(1).trim();
			}
			p = Pattern.compile("│(.*)│");
			
			while(m.find()){
				abbr = m.group(1);
				league = m.group(2);
			}
			p = Pattern.compile("│(.*)	│");
			
			
			while(m.find()){
				state = m.group(1).trim();
				subLeague = m.group(2).trim();
				homeCourt = m.group(3).trim();
				
			}
			
			p = Pattern.compile("│(.*)║");
			while(m.find()){
				timeOfFoundation = m.group(1);
			}*/
			
//			tempTeamPO = new TeamPO(name,abbr,state,league,subLeague,homeCourt,timeOfFoundation);
			
			
			tempTeamPO = new TeamPO(split[0],split[1],split[2],split[3],split[4],split[5],split[6],path);
			teams.add(tempTeamPO);
		}
		System.out.println("INFO:Team Info Initialized");
		br.close();
	}
	
	public void saveAsSerial() throws IOException{
		System.out.println("INFO:Team Info Saving");
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
		for(TeamPO p:teams){
			fo = new FileOutputStream(saveLoadPath+"/"+p.getName()+".ser");
			os = new ObjectOutputStream(fo);
			os.writeObject(p);
			os.close();
		}
		System.out.println("INFO:Team Info Saved Successfully");
	}
	
	public void loadSerial() throws IOException, ClassNotFoundException{
		System.out.println("INFO:Team Info Loading");
		if(teams!=null){
			teams.clear();
		}else{
			teams = new ArrayList<TeamPO>();
		}
		
		FileInputStream fi;
		ObjectInputStream is;
		File file = new File(saveLoadPath);
		File[] files = file.listFiles();
		for(int i = 0;i<files.length;i++){
			fi = new FileInputStream(files[i]);
			is = new ObjectInputStream(fi);
			teams.add((TeamPO)is.readObject());
		}
		System.out.println("INFO:Team Info Loaded Successfully");
	}
}
