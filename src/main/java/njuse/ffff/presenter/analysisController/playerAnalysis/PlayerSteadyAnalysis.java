package njuse.ffff.presenter.analysisController.playerAnalysis;

import java.util.ArrayList;
import java.util.List;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.util.X2Distribution;
import njuse.ffff.vo.PlayerSteady;

public class PlayerSteadyAnalysis {
	private static PlayerSteadyAnalysis playerSteadyAnalysis = null;
	private static TotalUIController totalController = null;
	private NewDataReaderService dataReader;
	
	private String[] attributes = {"points","rebound","assist","steal","block"};
	
	private PlayerSteadyAnalysis(){
		totalController = TotalUIController.getInstance();
		dataReader = totalController.getDataReader();
	}
	
	public static PlayerSteadyAnalysis getInstance(){
		if(playerSteadyAnalysis==null){
			playerSteadyAnalysis = new PlayerSteadyAnalysis();
		}
		return playerSteadyAnalysis;
	}
	
	public ArrayList<PlayerSteady> playerSteady(String playerID){
		ArrayList<PlayerSteady> playerSteadys = new ArrayList<PlayerSteady>();
		String currentSeason = totalController.getCurrentSeason();
		String[] years = currentSeason.split("-");
		int year = Integer.parseInt(years[0])-1;
		StringBuffer bf = new StringBuffer(year+"-"+String.valueOf(year+1).substring(2,4));
		String lastSeason = bf.toString();
		List<PlayerInMatchFull> playerStats_last = dataReader.getPlayerStats(playerID, lastSeason);
		if(playerStats_last==null||playerStats_last.size()==0){
			return playerSteadys;
		}
		List<PlayerInMatchFull> playerStats_current = dataReader.getPlayerStats(playerID, currentSeason);
		if(playerStats_current==null||playerStats_current.size()==0){
			return playerSteadys;
		}
		int games_l = playerStats_last.size();
		double[] points_l = new double[games_l];
		double[] rebound_l = new double[games_l];
		double[] assist_l = new double[games_l];
		double[] steal_l = new double[games_l];
		double[] block_l = new double[games_l];
		int games_c = playerStats_current.size();
		double[] points_c = new double[games_c];
		double[] rebound_c = new double[games_c];
		double[] assist_c = new double[games_c];
		double[] steal_c = new double[games_c];
		double[] block_c = new double[games_c];
		for(int i=0;i<games_l;i++){
			PlayerInMatchFull p = playerStats_last.get(i);
			points_l[i] = p.getPoints();
			rebound_l[i] = p.getRebound();
			assist_l[i] = p.getAssist();
			steal_l[i] = p.getSteal();
			block_l[i] = p.getBlock();
 		}
		for(int i=0;i<games_c;i++){
			PlayerInMatchFull p = playerStats_current.get(i);
			points_c[i] = p.getPoints();
			rebound_c[i] = p.getRebound();
			assist_c[i] = p.getAssist();
			steal_c[i] = p.getSteal();
			block_c[i] = p.getBlock();
		}
		double[][] list_last = {points_l,rebound_l,assist_l,steal_l,block_l};
		double[][] list_current = {points_c,rebound_c,assist_c,steal_c,block_c};
		for(int i=0;i<attributes.length;i++){
			playerSteadys.add(X2Check(attributes[i], list_last[i], list_current[i]));
		}
		return playerSteadys;
	}
	
	public double calAverage(double[] listA){
		double average = 0.0;
		double total = 0.0;
		for(double p:listA){
			total = total+p;
		}
		average = total/listA.length;
		return average;
	}
	
	/**
	 * n阶中心矩
	 * @param list
	 * @param n
	 * @return
	 */
	public double calCentralMoment(double[] list,int n){
		double Bn = 0.0;
		double average = calAverage(list);
		for(double x:list){
			Bn = Bn + Math.pow((x-average), n);
		}
		Bn = Bn/list.length;
		return Bn;
	}
	
	public ArrayList<String> pvCheck(double[] list){
		int num = list.length;
		double var1 = Math.sqrt(6*(num-2)/((num+1)*(num+3)));
		double var2 = Math.sqrt(24*num*(num-2)*(num-3)/((num+1)*(num+1)*(num+3)*(num+5)));
		double mu2 = 3-6/(num+1);
		double B2 = calCentralMoment(list, 2);
		double B3 = calCentralMoment(list, 3);
		double B4 = calCentralMoment(list, 4);
		double U1 = (B3/Math.pow(B2, 1.5))/var1;
		double U2 = (B4/Math.pow(B2, 2)-mu2)/var2;
		double Za_4 = 1.96;//a=0.1
		String res = null;
		if(Math.abs(U1)>=Za_4||Math.abs(U2)>=Za_4){
			res = "样本的分布于正态分布差异显著";
		}
		else{
			res = "样本的分布与正态分布的差异不显著";
		}
		ArrayList<String> result = new ArrayList<String>();
		result.add(String.valueOf(calAverage(list)));//均值
		result.add(String.valueOf(B2));				//方差
		result.add(String.valueOf(U1));				
		result.add(String.valueOf(U2));
		result.add(String.valueOf(Za_4));
		result.add(String.valueOf(U1));
		result.add(res);
		return result;
	}
	
	public PlayerSteady X2Check(String attribute,double[] list_standard,double[] list_sample){
		String result = null;
		double var_standard = calCentralMoment(list_standard, 2);
		double S_sample = calCentralMoment(list_sample, 2)*list_sample.length/(list_sample.length-1);
		double X2 = (list_sample.length-1)*S_sample/var_standard;
		double X2_001_n = X2Distribution.getValue(list_sample.length-1, 0.01);
//		double X2_099_n = X2Distribution.getValue(list_sample.length-1, 0.99);
		if(X2_001_n>0){
			if(X2<=X2_001_n){
				result = "球员与上赛季稳定性相当,甚至发挥更稳定";
			}
			else{
				result = "球员比上赛季发挥波动性更大";
			}
		}
		PlayerSteady steady = new PlayerSteady(attribute,list_standard, list_sample,
				calAverage(list_standard),calAverage(list_sample), var_standard, S_sample, X2, X2_001_n, result);
		return steady;
	}
	
}
