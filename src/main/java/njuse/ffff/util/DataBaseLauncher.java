package njuse.ffff.util;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseLauncher {
	String url = "jdbc:mysql://localhost:3306/nbadb";
	String user = "root";
	String password = "87281898";
	Connection conn;
	public DataBaseLauncher(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ResultSet executeSQL(String str){
		Statement stat;
		ResultSet result=null;
		try {
			stat = conn.createStatement();
			result =stat.executeQuery(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return result;
		
	}
	public static void main(String[] args){
		for(int i = 0;i<10000;i++){
			ResultSet r = new DataBaseLauncher().executeSQL("SELECT * FROM matchinfo");
		}
		
		System.out.println();
	}


}
