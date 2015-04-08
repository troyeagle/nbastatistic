package njuse.ffff.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class DealDecimal {
	/**
	 * double类型数保留若干位小数
	 * @param number
	 * @param bit
	 * @return
	 */
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
	
	/**
	 * 将double类型的数去除小数部分（转换为整数）
	 * @param number
	 * @return
	 */
	public static int formatChange(double number){
		String a = String.valueOf(number);
		int int1 = Integer.parseInt(a.split("[.]")[0]);
		return int1;
	}
	
	/**
	 * 将double类型转换为百分数
	 * @param number
	 * @return
	 */
	public static String formatChangeToPercentage(double number){
		NumberFormat num = NumberFormat.getPercentInstance(); 
		num.setMaximumIntegerDigits(3); 
		num.setMaximumFractionDigits(1); 
		String res = num.format(number);
		return res;
	}
		
}
