package njuse.ffff.util;

public class TDistribution {
	static double[] list_0005 = new double[]{63.6567,9.9248,5.8409,4.6041,4.0321,
											  3.7074,3.4995,3.3554,3.2498,3.1693,
											  3.1058,3.0545,3.0123,2.9768,2.9467,
											  2.9208,2.8982,2.8784,2.8609,2.8453,
											  2.8314,2.8188,2.8073,2.7969,2.7874,
											  2.7787,2.7707,2.7633,2.7564,2.7500,
											  2.7440,2.7385,2.7333,2.7284,2.7238,
											  2.7195,2.7154,2.7116,2.7079,2.7045,
											  2.7012,2.6981,2.6951,2.6923,2.6896,
											  2.6870,2.6846,2.6822,2.6800,2.6778,
											  2.6757,2.6737,2.6718,2.6700,2.6682,
											  2.6665,2.6649,2.6633,2.6618,2.6603,
											  2.6589,2.6575,2.6561,2.6549,2.6536,
											  2.6524,2.6512,2.6501,2.6490,2.6479,
											  2.6469,2.6459,2.6449,2.6439,2.6430,
											  2.6421,2.6412,2.6403,2.6395,2.6387};
	
	public static double getValue(int n,double a){
		if(n>0&&n<=80){
			if(a==0.01){
				return list_0005[n-1];
			}
			else{
				return -1;
			}
		}
		else{
			return -1;
		}
	}
}
