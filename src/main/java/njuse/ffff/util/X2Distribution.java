package njuse.ffff.util;

public class X2Distribution {
	static double[] list_001 = {6.6349,9.2103,11.3449,13.2767,15.0863,
						 16.8119,18.4753,20.0902,21.6660,23.2093,
						 24.7250,26.2170,27.6882,29.1412,30.5779,
						 31.9999,33.4087,34.8053,36.1909,37.5662,
						 38.9322,40.2894,41.6384,42.9798,44.3141,
						 45.6417,46.9629,48.2782,49.5879,50.8922,
						 52.1914,53.4858,54.7755,56.0609,57.3421,
						 58.6192,59.8925,61.1621,62.4281,63.6907,
						 64.9501,66.2062,67.4593,68.7095,69.9568,
						 71.2014,72.4433,73.6826,74.9195,76.1539,
						 77.3860,78.6158,79.8433,81.0688,82.2921,
						 83.5134,84.7328,85.9502,87.1657,88.3794,
						 89.5913,90.8015,92.0100,93.2169,94.4221,
						 95.6257,96.8278,98.0284,99.2275,100.4252,
						 101.6214,102.8163,104.0098,105.2020,106.3929,
						 107.5825,108.7709,109.9581,111.1440,112.3288};
	static double[] list_099 = {0.0002,0.0201,0.1148,0.2971,0.5543,
						 0.8721,1.2390,1.6465,2.0879,2.5582,
						 3.0535,3.5706,4.1069,4.6604,5.2293,
						 5.8122,6.4078,7.0149,7.6327,8.2604,
						 8.8972,9.5425,10.1957,10.8564,11.5240,
						 12.1981,12.8785,13.5647,14.2565,14.9535,
						 15.6555,16.3622,17.0735,17.7891,18.5089,
						 19.2327,19.9602,20.6914,21.4262,22.1643,
						 22.9056,23.6501,24.3976,25.1480,25.9013,
						 26.6572,27.4158,28.1770,28.9406,29.7067,
						 30.4750,31.2457,32.0185,32.7934,33.5705,
						 34.3495,35.1305,35.9135,36.6982,37.4849,
						 38.2732,39.0633,39.8551,40.6486,41.4436,
						 42.2402,43.0384,43.8380,44.6392,45.4417,
						 46.2457,47.0510,47.8577,48.6657,49.4750,
						 50.2856,51.0974,51.9104,52.7247,53.5401};
	
	public static double getValue(int n,double a){
		if(n>0&&n<=80){
			if(a==0.01){
				return list_001[n-1];
			}
			else if(a==0.99){
				return list_099[n-1];
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