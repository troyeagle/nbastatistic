package njuse.ffff.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayersDataProcessor {
	private String path;
	
	public void fastRead() {
		String path = "C:/Users/Mebleyev.G.Longinus/Downloads/CSEIII data/迭代一数据/players/info";
		File file = new File(path);
		File[] files = file.listFiles();
		FileReader fr;
		int j = 0;
		for (int i = 0; i <files.length; i++) {
			try {
				System.out.println(files[i].getPath());
				fr = new FileReader(files[i]);
				BufferedReader br = new BufferedReader(fr);
				System.out.println(br.readLine());
				Pattern p = Pattern.compile("Name		│(.*)║");
				Matcher m = p.matcher(br.readLine());
				while(m.find()){
					System.out.println(m.group(1).trim());
					j++;
				}
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
		System.out.println(j);
	}
	public static void main(String[] args){
		new PlayersDataProcessor().fastRead();
	}
}
