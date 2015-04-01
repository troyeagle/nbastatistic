package njuse.ffff.util;

import java.math.BigDecimal;

public class DealDecimal {
	public static double formatChange(double number,int bit){
		if(!Double.valueOf(number).isInfinite()&&!Double.valueOf(number).isNaN()){
			BigDecimal b = new BigDecimal(number);
			double f1 = b.setScale(bit,BigDecimal.ROUND_HALF_UP).doubleValue();
			return f1;
		}
		return number;
//		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.000");  
//		return Double.valueOf(df.format(number));
	}
	
	public static int formatChange(double number){
		String a = String.valueOf(number);
		int int1 = Integer.parseInt(a.split("[.]")[0]);
		return int1;
	}
		
}
