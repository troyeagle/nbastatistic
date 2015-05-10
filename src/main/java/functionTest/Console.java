package functionTest;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import njuse.ffff.data.DataReadController;
import njuse.ffff.dataservice.DataReaderService;
import de.tototec.cmdoption.CmdlineParser;

public class Console {
	private static DataReaderService service = null;
	
	public void execute(PrintStream out,String[] args){
		if(args[0].equals("--datasource")){
			service = new DataReadController();

			try {
				service.advancedInitialize(args[1], 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//out.println("========Info initialized========");
		}
		else{
			GameCommand cmd = new GameCommand();
			CmdlineParser cp = new CmdlineParser(cmd);
			CmdlineParser cp2 = null;
			PlayerCommand player = null;
			TeamCommand team = null;
			cp.parse(args[0]);
			if(cmd.isPlayer()){
				player = new PlayerCommand(service);
				cp2 = new CmdlineParser(player);
			}
			else{
				team = new TeamCommand(service);
				cp2 = new CmdlineParser(team);
			}
			for(int i=1;i<args.length;i++){
				if(i==args.length-1||args[i+1].charAt(0)=='-'){
					cp2.parse(args[i]);
				}
				else{
					cp2.parse(new String[]{args[i],args[i+1]});
					i++;
				}
			}
			if(player!=null){
				ArrayList<Object> list = player.getPlayInfo();
				int i=1;
				for(Object o:list){
					out.println(i);
					out.println(o);
					i++;
				}
			}
			else if(team!=null){
				ArrayList<Object> list = team.getTeamInfo();
				int i=1;
				for(Object o:list){
					out.println(i);
					out.println(o);
					i++;
				}
			}
		}
	}
	
	public DataReaderService getServcie(){
		return service;
	}
	
	public static void main(String[] args) {
		Console con = new Console();
		con.execute(System.out, args);
	}
}
