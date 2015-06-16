package njuse.ffff.vo;

public class PlayerSteady {
	private String attribute;//考量属性
	
	private double[] list_last;//上一赛季的数据
	private double[] list_current;//本赛季的数据
	
	private double var;//总体方差
	
	private double s2;//本赛季样本方差
	
	private double X2;//卡方检验统计量值
	
	private double X2_001_n;//拒绝域临界值
	
	private String result;//检验结果

	public PlayerSteady(String attribute,double[] list_last, double[] list_current, double var,
			double s2, double x2, double x2_001_n, String result) {
		super();
		this.attribute = attribute;
		this.list_last = list_last;
		this.list_current = list_current;
		this.var = var;
		this.s2 = s2;
		X2 = x2;
		X2_001_n = x2_001_n;
		this.result = result;
	}
	
	public String getAttribute() {
		return attribute;
	}

	public double[] getList_last() {
		return list_last;
	}

	public double[] getList_current() {
		return list_current;
	}

	public double getVar() {
		return var;
	}

	public double getS2() {
		return s2;
	}

	public double getX2() {
		return X2;
	}

	public double getX2_001_n() {
		return X2_001_n;
	}

	public String getResult() {
		return result;
	}
	
}
