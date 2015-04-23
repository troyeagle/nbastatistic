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
		if(!Double.valueOf(number).isInfinite()&&!Double.valueOf(number).isNaN()){
			String a = String.valueOf(number);
			int int1 = Integer.parseInt(a.split("[.]")[0]);
			return int1;
		}
		return (int) number;
	}
	
	/**
	 * 将double类型转换为百分数
	 * @param number
	 * @return
	 */
	public static String formatChangeToPercentage(double number){
		NumberFormat num = NumberFormat.getPercentInstance(); 
		num.setMaximumIntegerDigits(4); 
		num.setMaximumFractionDigits(1); 
		String res = num.format(number);
		return res;
	}
	
	public static void main(String[] args) {
//		Date date1 = new Date();
//		date1.setYear(1912);
//		date1.setMonth(11);
//		date1.setDate(4);
//		Calendar date = new GregorianCalendar();
//		date.setTime(date1);
//		int year = date.get(Calendar.YEAR)-1900;
//		int month = date.get(Calendar.MONTH)+1;
//		int day = date.get(Calendar.DAY_OF_MONTH);
//		StringBuffer dateBuffer = new StringBuffer(year+"-"+month+"-"+day);
//		System.out.println(dateBuffer.toString());
//		String w = "6-0";
//		String[] parts = w.split("-");
//		String weight = null;
//		if(parts[1].equals("0")){
//			weight = parts[0];
//		}
//		else{
//			weight = w;
//		}
//		System.out.println(weight);
	}
}
