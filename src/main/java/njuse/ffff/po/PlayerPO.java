package njuse.ffff.po;

import java.io.Serializable;
/**
 * 球员基本信息，由<code>PlayersDataProcessor</code>构建
 * @author Mebleyev.G.Longinus
 * @see PlayersDataProcessor
 *
 */
public class PlayerPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String number;
	char[] position;
	String height;
	String weight;
	String birth;
	int age;
	String exp;
	String school;
	String pathOfPortrait;
	String pathOfAction;
	String birthCity;
	int salary;
	@Override
	public String toString() {
		return "PlayerPO [name=" + name + ", number=" + number + ", position=" + String.valueOf(position)
				+ ", height=" + height + ", weight=" + weight + ", birth=" + birth + ", age="
				+ age + ", exp=" + exp + ", school=" + school + "]";
	}

	public PlayerPO(String name, String number, char position[], String height, String weight,
			String birth, int age, String exp, String school, String playerPath) {
		super();
		this.name = name;
		this.number = number;
//		if(position[1]!='F'&&position[1]!='G'&&position[1]!='C'){
//			this.position = new char[1];
//			this.position[0]= position[0];
//		}else{
			this.position = position;
//		}
		
		this.height = height;
		this.weight = weight;
		this.birth = birth;
		this.age = age;
		this.exp = exp;
		this.school = school;
		this.pathOfAction = playerPath + "/action/" + name + ".png";
		this.pathOfPortrait = playerPath + "/portrait/" + name + ".png";
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public char[] getPosition() {
		return position;
	}

	public String getHeight() {
		return height;
	}

	public String getWeight() {
		return weight;
	}

	public String getBirth() {
		return birth;
	}

	public int getAge() {
		return age;
	}

	public String getExp() {
		return exp;
	}

	public String getSchool() {
		return school;
	}

	public String getPathOfPortrait() {
		return pathOfPortrait;
	}

	public String getPathOfAction() {
		return pathOfAction;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

}
