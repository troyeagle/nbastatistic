package njuse.ffff.util;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Filter,to judge what statistic should be returned <br>
 * The content in filters are defined as follows: <br>
 * The Attributes needed to be compared is set in ArrayList&lt;String>
 * attributes; <br>
 * Orderly, the condition(range,equal,etc) is set in ArrayList&lt;String>
 * condition;<br>
 * 
 * The condition is defined as follows;<br>
 * 1.If the attributes is string or character, directly input it.The method will
 * judge if it equals.<br>
 * 
 * 2.If the attributes is number(int or double),the conditions are split by ","
 * and each condition should start with a comparer, include !,=,<=,>=,<,>.<br>
 * 
 * eg: <br>
 * attributes:["fieldsGoalMade","freeThrowMade","position"] <br>
 * condition:[">12,<14","!7","G"]<br>
 * 
 */
public class Filter {

	ArrayList<String> attributes;
	ArrayList<String> condition;

	public Filter(ArrayList<String> attributes, ArrayList<String> condition) {
		super();
		this.attributes = attributes;
		this.condition = condition;
	}
	public Filter(){
	}

	public <T> boolean filt(T p) {
		if(attributes==null||condition==null){
			return true;
		}
		for (int i = 0; i < attributes.size(); i++) {
			Field field;
			try {
				field = p.getClass().getDeclaredField(attributes.get(i));
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				return false;
			} catch (SecurityException e) {
				e.printStackTrace();
				return false;
			}

			try {
				field.get(p);
				field.setAccessible(true);
				Class<?> typeOfPara = field.getType();

				if (typeOfPara.getName().equals("String")) {
					String result = (String) field.get(p);
					if (!result.equals(condition.get(i))) {
						return false;
					}

				}

				if (typeOfPara.getName().equals("char")) {
					char result = field.getChar(p);
					if (!(result == condition.get(i).charAt(0))) {
						return false;
					}
				}

				if (typeOfPara.getName().equals("double")
						|| typeOfPara.getName().equals("int")) {
					double result = field.getDouble(p);
					String[] split = condition.get(i).split(",");
					for (int k = 0; k < split.length; k++) {

						int flag = -1;
						for (int j = 0; j < split.length; j++) {
							if (split[j].startsWith(">=")) {
								flag = 1;
								split[j] = split[j].substring(2);
							} else if (split[j].startsWith("<=")) {
								flag = 2;
								split[j] = split[j].substring(2);
							} else if (split[j].startsWith(">")) {
								flag = 3;
								split[j] = split[j].substring(1);
							} else if (split[j].startsWith("<")) {
								flag = 4;
								split[j] = split[j].substring(1);
							} else if (split[j].startsWith("!")) {
								flag = 5;
								split[j] = split[j].substring(1);
							} else if (split[j].startsWith("=")) {
								flag = 6;
								split[j] = split[j].substring(1);
							}
						}
						double com = Double.parseDouble(split[k]);
						switch (flag) {
						case 1:
							if (result < com) {
								return false;
							}
						case 2:
							if (result > com) {
								return false;
							}
						case 3:
							if (result <= com) {
								return false;
							}
						case 4:
							if (result >= com) {
								return false;
							}
						case 5:
							if (result == com) {
								return false;
							}
						case 6:
							if (result != com) {
								return false;
							}
						}
					}

				}

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

}
