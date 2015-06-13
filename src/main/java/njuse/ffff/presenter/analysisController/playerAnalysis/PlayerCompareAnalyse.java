package njuse.ffff.presenter.analysisController.playerAnalysis;

public class PlayerCompareAnalyse {
	
	public boolean compare(double[] listA,double listB[],double trustValue){
		boolean related = true;
		int length = listA.length;
		double[] listA_modify = new double[length];
		double[] listB_modify = new double[length];
		double average_A = calAverage(listA);
		double average_B = calAverage(listB);
		for(int i=0;i<length;i++){
			listA_modify[i] = listA[i]/average_A;
			listB_modify[i] = listB[i]/average_B;
		}
		
		int n_plus = 0;
		int n_minus = 0;
		double p = 0.0;
		for(int j=0;j<length;j++){
			if((listA_modify[j]>1&&listB_modify[j]<1)
					||(listA_modify[j]<1&&listB_modify[j]>1)){
				n_minus++;
			}
			else{
				n_plus++;
			}
//			if(listA_modify[j]<listB_modify[j]){
//				n_minus++;
//			}
//			else if(listA_modify[j]>listB_modify[j]){
//				n_plus++;
//			}
		}
		System.out.println(n_plus);
		System.out.println(n_minus);
		if(n_plus>n_minus){
			for(int i=n_plus;i<=length;i++){
				p = p+calCombinationNumber(i,length)*Math.pow(0.5, length);
			}
		}
		else{
			for(int i=0;i<=n_plus;i++){
				p = p+calCombinationNumber(i,length)*Math.pow(0.5, length);
			}
		}
		System.out.println(p);
		//判断是否相关
		if(p<trustValue){
			related = false;
		}
		else{
			related = true;
		}
		return related;
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
	
	public double calSampleVar(double[] list){
		double var = 0.0;
		double average = calAverage(list);
		for(double x:list){
			var = var + (x-average)*(x-average);
		}
		var = var/list.length;
		return var;
	}
	
	public long calCombinationNumber(int t,int n){
		long result = 0;
		long a = 1;
		long b = 1;
		if(t>n/2){
			t = n-t;
		}
		for(int i=n-t+1;i<=n;i++){
			b = b*i;
		}
		for(int j=1;j<=t;j++){
			a = a*j;
		}
		result = b/a;
		return result;
	}
	
	public static void main(String[] args) {
		PlayerCompareAnalyse temp = new PlayerCompareAnalyse();
		double[] l1 = {4,6,7,4,8,4,6,7,4,8,4,6,7,4,8};
		double[] l2 = {3,2,2,2,1,3,2,2,1,1,3,2,2,2,1};
//		System.out.println(temp.calCombinationNumber(12, 62));
		System.out.println(temp.compare(l1, l2, 0.025));
	}
}
