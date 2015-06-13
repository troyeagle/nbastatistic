package njuse.ffff.presenter.analysisController.styleAnalysis;

import java.util.ArrayList;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.TeamInMatch;
import njuse.ffff.presenter.TotalUIController;

public class LeagueStyleAnalysis {
	private DataReaderService dataService;
	private static LeagueStyleAnalysis leagueStyleAnalysis = null;
	private static TotalUIController totalController = null;
	
	private LeagueStyleAnalysis() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}

	public static LeagueStyleAnalysis getInstance() {
		if (leagueStyleAnalysis == null) {
			leagueStyleAnalysis = new LeagueStyleAnalysis();
		}
		return leagueStyleAnalysis;
	}
	
	private String[] seasonPeriod = new String[]{"1981-1985","1986-1990","1991-1995"
			,"1996-2000","2001-2005","2006-2010","2011,2014"};
	
	/**
	 * 联盟进攻风格分析
	 */
	public void analyseLeagueStyle(){
		for(String seasons:seasonPeriod){
			double[] data = calculateCharactoristic(seasons);
			
		}
	}
	
	
	private double[] calculateCharactoristic(String seasons){
		int size = 5;
		double[] value_estimated = new double[10];	//一支球队的
		double[] FGMade = new double[size];			//总出手数，
		double[] FGAttempted = new double[size];		//总命中数，
		double[] FGRatio = new double[size];			//（命中率），
		double[] TPMade = new double[size];			//三分出手数，
		double[] TPAttempted = new double[size];		//三分出手数，
		double[] TPRatio = new double[size];			//（三分命中率），
		double[] FTMade = new double[size];			//罚球出手数，
		double[] FTAttempted = new double[size];		//罚球命中数，
		double[] FTRatio = new double[size];			//（罚球命中率），
		double[] score = new double[size];				//得分
		String[] period = seasons.split("[-]");
		int start = Integer.parseInt(period[0]);
		int end = Integer.parseInt(period[1]);
		for(int num=start;num<=end;num++){
			StringBuffer buffer = new StringBuffer();
			buffer.append(String.valueOf(num).substring(2, 4));
			buffer.append("-");
			buffer.append(String.valueOf(num+1).substring(2, 4));
			String season = buffer.toString();
			ArrayList<MatchPO> listMatch = sampling(season);
			//TODO
			int index = num-start+1;
			for(int i=0;i<listMatch.size();i++){
				MatchPO match = listMatch.get(i);
				TeamInMatch teamA = match.getTeamStatA();
				TeamInMatch teamB = match.getTeamStatB();
				FGMade[index] += 0;//teamA.get。。
				FGMade[index] += 0;//teamB.get。。
				FGAttempted[index] += 0;//teamA.get。。
				FGAttempted[index] += 0;//teamB.get。。
				FGRatio[index] += 0;//teamA.get。。
				FGRatio[index] += 0;//teamB.get。。
				TPMade[index] += 0;//teamA.get。。
				TPMade[index] += 0;//teamB.get。。
				TPAttempted[index] += 0;//teamA.get。。
				TPAttempted[index] += 0;//teamB.get。。
				TPRatio[index] += 0;//teamA.get。。
				TPRatio[index] += 0;//teamB.get。。
				FTMade[index] += 0;//teamA.get。。
				FTMade[index] += 0;//teamB.get。。
				FTAttempted[index] += 0;//teamA.get。。
				FTAttempted[index] += 0;//teamB.get。。
				FTRatio[index] += 0;//teamA.get。。
				FTRatio[index] += 0;//teamB.get。。
				score[index] += 0;//teamA.get。。
				score[index] += 0;//teamB.get。。
			}
			FGMade[index] = FGMade[index]/(2*listMatch.size());
			FGAttempted[index] = FGAttempted[index]/(2*listMatch.size());
			FGRatio[index] = FGRatio[index]/(2*listMatch.size());
			TPMade[index] = TPMade[index]/(2*listMatch.size());
			TPAttempted[index] = TPAttempted[index]/(2*listMatch.size());
			TPRatio[index] = TPRatio[index]/(2*listMatch.size());
			FTMade[index] = FTMade[index]/(2*listMatch.size());
			FTAttempted[index] = FTAttempted[index]/(2*listMatch.size());
			FTRatio[index] = FTRatio[index]/(2*listMatch.size());
			score[index] = score[index]/(2*listMatch.size());
		}
		for(int j=0;j<size;j++){
			value_estimated[0] += FGMade[j];
			value_estimated[1] += FGAttempted[j];
			value_estimated[2] += FGRatio[j];
			value_estimated[3] += TPMade[j];
			value_estimated[4] += TPAttempted[j];
			value_estimated[5] += TPRatio[j];
			value_estimated[6] += FTMade[j];
			value_estimated[7] += FTAttempted[j];
			value_estimated[8] += FTRatio[j];
			value_estimated[9] += score[j];
		}
		for(int k=0;k<10;k++){
			value_estimated[k] = value_estimated[k]/size;
		}
		return value_estimated;
	}
	
	/**
	 * 抽取样本
	 * 一组有5个赛季，在一个赛季中以周为单位进行随机抽样，每周随机抽取一天的比赛
	 */
	private ArrayList<MatchPO> sampling(String season){
		ArrayList<MatchPO> listMatch = new ArrayList<MatchPO>();
		//TODO
		
		return listMatch;
	}
}
