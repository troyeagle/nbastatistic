package functionTest;

public class TestConsole {
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
