package njuse.ffff.presenter.analysisController.playerAnalysis;

public class CorrelationAnalysis {
	
	
	//y = a+bx , r2 , Sy
	public double[] analyseCorrelation(double[] listX,double[] listY){
		double a = 0;
		double b = 0;
		double r2 = 0;
		double Sy = 0;
		int length = listX.length;
		double Lxy = 0;
		double Lxx = 0;
		double sumX = 0;
		double sumY = 0;
		double sumXY = 0;
		double sumXX = 0;
		for(int i=0;i<length;i++){
			sumX +=listX[i];
			sumY +=listY[i];
			sumXY +=listX[i]*listY[i];
			sumXX +=listX[i]*listX[i];
		}
		Lxy = sumXY-sumX*sumY/length;
		Lxx = sumXX - sumX*sumX/length;
		b = Lxy/Lxx;
		a = sumY/length - b*sumX/length;
		double[] list_Y = new double[length];
		double SR = 0;
		double ST = 0;
		double Se = 0;
		for(int j=0;j<length;j++){
			list_Y[j] = a+b*listX[j];
			SR += (list_Y[j]-sumY/length)*(list_Y[j]-sumY/length);
			ST += (listY[j]-sumY/length)*(listY[j]-sumY/length);
			Se += (listY[j]-list_Y[j])*(listY[j]-list_Y[j]);
		}
		r2 = SR/ST;
		Sy = Math.sqrt(Se/(length-2));
		return new double[]{a,b,r2,Sy};
	}
	
}
