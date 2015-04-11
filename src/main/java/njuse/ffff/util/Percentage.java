package njuse.ffff.util;

public class Percentage {
	private String number;				//百分数的数字部分
	private static String symbol = "%";	//百分数的符号部分
	
	public Percentage(String percentage){
		number = percentage.substring(0, percentage.length()-1);
	}
	
	/**
	 * 判断是否为百分数
	 * @param o
	 * @return
	 */
	public static boolean isPercentage(Object o){
		boolean result = false;
		char[] charArray = o.toString().toCharArray();
		String lastCharacter = o.toString().substring(o.toString().length()-1, o.toString().length());
		if(lastCharacter.equals(symbol)){
			int validDigit = 0;
			int validDot = 0;
			for(int i=0;i<charArray.length-1;i++){
				switch(charArray[i]){
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					validDigit++;
					break;
				case '.':
					validDot++;
				}
			}
			if((validDot==0&&validDigit==(charArray.length-1))
					||validDot==1&&validDigit==(charArray.length-2)){
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 百分数比较
	 * @param percent
	 * @return
	 */
	public int compareTo(Percentage percent){
		int result = 0;
		if(Double.parseDouble(number)<Double.parseDouble(percent.getNumber())){
			result = -1;
		}
		else if(Double.parseDouble(number)==Double.parseDouble(percent.getNumber())){
			result = 0;
		}
		else{
			result = 1;
		}
		return result;
	}
	
	private String getNumber(){
		return number;
	}
	
	public String getPercentage(){
		return number.concat(symbol);
	}
	
//	public static void main(String[] args) {
//		double a = 0.872;
////		double b = 0.33;
//		String a_percent = DealDecimal.formatChangeToPercentage(a);
////		String b_percent = DealDecimal.formatChangeToPercentage(b);
////		Percentage a_p = new Percentage(a_percent);
////		Percentage b_p = new Percentage(b_percent);
////		System.out.println(b_percent+"  "+b_p.getNumber()+" "+b_p.getPercentage());
////		System.out.println(b_p.getPercentage().equals(b_percent));
////		System.out.println(b_p.compareTo(a_p));
//		Object obj = a_percent;
//		System.out.println(Percentage.isPercentage(obj));
//		Object obj2 = "1.0.9%";
//		System.out.println(Percentage.isPercentage(obj2));
//	}
}
