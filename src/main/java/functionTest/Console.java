package functionTest;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import njuse.ffff.data.DataReadController;
import njuse.ffff.dataservice.DataReaderService;
import de.tototec.cmdoption.CmdlineParser;

public class Console {
	private DataReaderService service = null;
	
	public void execute(PrintStream out,String[] args){
		if(args[0].equals("--dataresource")){
			service = new DataReadController();

			try {
				service.advancedInitialize(args[1], 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
//			out.println("========Info initialized========");
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
				for(Object o:list){
					out.print(o);
				}
			}
			else if(team!=null){
				ArrayList<Object> list = team.getTeamInfo();
				for(Object o:list){
					out.print(o);
				}
			}
		}
	}
	
	public DataReaderService getServcie(){
		return service;
	}
	
	public static void main(String[] args) {
		Console con = new Console();
//		con.execute(System.out, args);
		String[] m=new String[2];
		m[0]="--dataresource";
		m[1]="C:/Users/Mebleyev.G.Longinus/Documents/GitHub/nbastatistic/CSEIII data/迭代一数据";
		con.execute(System.out, m);
		con.execute(System.out, "-player -high -n 10 -sort frequency".split("\\s"));
		con.execute(System.out, "-player -hot assist -n 5".split("\\s"));
		con.execute(System.out, "-player -king score -season".split("\\s"));
		con.execute(System.out, "-player -avg -n 5 -filter position.F".split("\\s"));
		con.execute(System.out, "-player -total -all -n 10 -sort shot".split("\\s"));
		con.execute(System.out, "-team -hot assist -n 4".split("\\s"));
		con.execute(System.out, "-team -total -all -n 4".split("\\s"));
	}
}
