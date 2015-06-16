package njuse.ffff.presenter.analysisController.styleAnalysis;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.sqlpo.MatchInfo;
import njuse.ffff.sqlpo.PlayerInMatchFull;
import njuse.ffff.util.TDistribution;

public class LeagueStyleAnalysis {
	private NewDataReaderService dataReader;
	private static LeagueStyleAnalysis leagueStyleAnalysis = null;
	private static TotalUIController totalController = null;
	
	private LeagueStyleAnalysis() {
		totalController = TotalUIController.getInstance();
		dataReader = totalController.getDataReader();
	}

	public static LeagueStyleAnalysis getInstance() {
		if (leagueStyleAnalysis == null) {
			leagueStyleAnalysis = new LeagueStyleAnalysis();
		}
		return leagueStyleAnalysis;
	}
	
	private String[] seasonPeriod = new String[]{"1981-1985","1986-1990","1991-1995"
			,"1996-2000","2001-2005","2006-2010","2011-2014"};
	
	/**
	 * 联盟进攻风格分析
	 */
	public String[][] analyseLeagueStyle(){
		String[][] leagueStyle_value = new String[7][];
		int index = 0;
		for(String seasons:seasonPeriod){
			String[] data = calculateCharactoristic2(seasons);
			leagueStyle_value[index] = data;
			index++;
		}
		return leagueStyle_value;
	}
	
	
	public double[] calculateCharactoristic(String seasons){				
		String[] period = seasons.split("[-]");
		int start = Integer.parseInt(period[0]);
		int end = Integer.parseInt(period[1]);
		int size = end-start+1;
		double[] value_estimated = new double[10];	//一支球队的
		double[] FGAttempted = new double[size];			//总出手数，
		double[] FGMade = new double[size];		//总命中数，
		double[] FGRatio = new double[size];			//（命中率），
		double[] TPAttempted = new double[size];			//三分出手数，
		double[] TPMade = new double[size];		//三分命中数，
		double[] TPRatio = new double[size];			//（三分命中率），
		double[] FTAttempted = new double[size];			//罚球出手数，
		double[] FTMade = new double[size];		//罚球命中数，
		double[] FTRatio = new double[size];			//（罚球命中率），
		double[] score = new double[size];			//得分
		for(int num=start;num<=end;num++){
			ArrayList<PlayerInMatchFull> listMatch = sampling(start,end);
			int index = num-start+1;
			for(int i=0;i<listMatch.size();i++){
				PlayerInMatchFull match = listMatch.get(i);
				Map<String, Object> map = match.generateBasicMap();
				FGMade[index] += Double.parseDouble(String.valueOf(map.get("fieldGoalMade")));
				FGAttempted[index] += Double.parseDouble(String.valueOf(map.get("fieldGoalAttempted")));
				FGRatio[index] += Double.parseDouble(String.valueOf(map.get("fieldGoalPercentage")));
				TPMade[index] += Double.parseDouble(String.valueOf(map.get("threePointerMade")));
				TPAttempted[index] += Double.parseDouble(String.valueOf(map.get("threePointerAttempted")));
				TPRatio[index] += Double.parseDouble(String.valueOf(map.get("threePointerPercentage")));
				FTMade[index] += Double.parseDouble(String.valueOf(map.get("freeThrowMade")));
				FTAttempted[index] += Double.parseDouble(String.valueOf(map.get("freeThrowAttempted")));
				FTRatio[index] += Double.parseDouble(String.valueOf(map.get("freeThrowRate")));
				score[index] += Double.parseDouble(String.valueOf(map.get("points")));
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
			value_estimated[0] += FGAttempted[j];
			value_estimated[1] += FGMade[j];
			value_estimated[2] += FGRatio[j];
			value_estimated[3] += TPAttempted[j];
			value_estimated[4] += TPMade[j];
			value_estimated[5] += TPRatio[j];
			value_estimated[6] += FTAttempted[j];
			value_estimated[7] += FTMade[j];
			value_estimated[8] += FTRatio[j];
			value_estimated[9] += score[j];
		}
		for(int k=0;k<10;k++){
			value_estimated[k] = value_estimated[k]/size;
		}
		return value_estimated;
	}
	
	public String[] calculateCharactoristic2(String seasons){
		int sampleAmount = 50;
		String[] period = seasons.split("[-]");
		int start = Integer.parseInt(period[0]);
		int end = Integer.parseInt(period[1]);
		double[] value_avg = new double[10];	//一支球队的
		double[] value_var = new double[10];
		double[] FGAttempted = new double[sampleAmount];			//总出手数，
//		ArrayList<Double> FGAttempted_sample = new ArrayList<Double>();
		double[] FGMade = new double[sampleAmount];		//总命中数，
//		ArrayList<Double> FGMade_sample = new ArrayList<Double>();
		double[] FGRatio = new double[sampleAmount];			//（命中率），
//		ArrayList<Double> FGRatio_sample = new ArrayList<Double>();
		double[] TPAttempted = new double[sampleAmount];			//三分出手数，
//		ArrayList<Double> TPAttempted_sample = new ArrayList<Double>();
		double[] TPMade = new double[sampleAmount];		//三分命中数，
//		ArrayList<Double> TPMade_sample = new ArrayList<Double>();
		double[] TPRatio = new double[sampleAmount];			//（三分命中率），
//		ArrayList<Double> TPRatio_sample = new ArrayList<Double>();
		double[] FTAttempted = new double[sampleAmount];			//罚球出手数，
//		ArrayList<Double> FTAttempted_sample = new ArrayList<Double>();
		double[] FTMade = new double[sampleAmount];		//罚球命中数，
//		ArrayList<Double> FTMade_sample = new ArrayList<Double>();
		double[] FTRatio = new double[sampleAmount];			//（罚球命中率），
//		ArrayList<Double> FTRatio_sample = new ArrayList<Double>();
		double[] score = new double[sampleAmount];			//得分
//		ArrayList<Double> score_sample = new ArrayList<Double>();
		int[] sample_size = new int[sampleAmount];
		for(int num=start;num<=end;num++){
			Map<Integer,List<PlayerInMatchFull>> sample_season = sampling2(num,num+1,sampleAmount);
			for(int i=0;i<sampleAmount;i++){
				List<PlayerInMatchFull> matches = sample_season.get(i+1);
				sample_size[i] += matches.size();
				for(PlayerInMatchFull match:matches){
					Map<String, Object> map = match.generateBasicMap();
					double temp = Double.parseDouble(String.valueOf(map.get("fieldGoalMade")));
					FGMade[i] += temp;
//					FGMade_sample.add(temp);
					temp = Double.parseDouble(String.valueOf(map.get("fieldGoalAttempted")));
					FGAttempted[i] += temp;
//					FGAttempted_sample.add(temp);
					temp = Double.parseDouble(String.valueOf(map.get("fieldGoalPercentage")));
					FGRatio[i] += temp;
//					FGRatio_sample.add(temp);
					temp = Double.parseDouble(String.valueOf(map.get("threePointerMade")));
					TPMade[i] += temp;
//					TPMade_sample.add(temp);
					temp = Double.parseDouble(String.valueOf(map.get("threePointerAttempted")));
					TPAttempted[i] += temp;
//					TPAttempted_sample.add(temp);
					temp = Double.parseDouble(String.valueOf(map.get("threePointerPercentage")));
					TPRatio[i] += temp;
//					TPRatio_sample.add(temp);
					temp = Double.parseDouble(String.valueOf(map.get("freeThrowMade")));
					FTMade[i] += temp;
//					FTMade_sample.add(temp);
					temp = Double.parseDouble(String.valueOf(map.get("freeThrowAttempted")));
					FTAttempted[i] += temp;
//					FTAttempted_sample.add(temp);
					temp = Double.parseDouble(String.valueOf(map.get("freeThrowRate")));
					FTRatio[i] += temp;
//					FTRatio_sample.add(temp);
					temp = Double.parseDouble(String.valueOf(map.get("points")));
					score[i] += temp;
//					score_sample.add(temp);
				}
			}
		}
		for(int index=0;index<sampleAmount;index++){
			FGMade[index] = FGMade[index]/sample_size[index];
			FGAttempted[index] = FGAttempted[index]/sample_size[index];
			FGRatio[index] = FGRatio[index]/sample_size[index];
			TPMade[index] = TPMade[index]/sample_size[index];
			TPAttempted[index] = TPAttempted[index]/sample_size[index];
			TPRatio[index] = TPRatio[index]/sample_size[index];
			FTMade[index] = FTMade[index]/sample_size[index];
			FTAttempted[index] = FTAttempted[index]/sample_size[index];
			FTRatio[index] = FTRatio[index]/sample_size[index];
			score[index] = score[index]/sample_size[index];
		}
		value_avg[0] = calAverage(FGAttempted);
		value_avg[1] = calAverage(FGMade);
		value_avg[2] = calAverage(FGRatio);
		value_avg[3] = calAverage(TPAttempted);
		value_avg[4] = calAverage(TPMade);
		value_avg[5] = calAverage(TPRatio);
		value_avg[6] = calAverage(FTAttempted);
		value_avg[7] = calAverage(FTMade);
		value_avg[8] = calAverage(FTRatio);
		value_avg[9] = calAverage(score);
		
		value_var[0] = calCentralMoment(FGAttempted, 2);
		value_var[1] = calCentralMoment(FGMade, 2);
		value_var[2] = calCentralMoment(FGRatio, 2);
		value_var[3] = calCentralMoment(TPAttempted, 2);
		value_var[4] = calCentralMoment(TPMade, 2);
		value_var[5] = calCentralMoment(TPRatio, 2);
		value_var[6] = calCentralMoment(FTAttempted, 2);
		value_var[7] = calCentralMoment(FTMade, 2);
		value_var[8] = calCentralMoment(FTRatio, 2);
		value_var[9] = calCentralMoment(score, 2);
		
		double[] segment = new double[10];
		double[] trust_up = new double[10];
		double[] trust_down = new double[10];
		String[] interval = new String[10];
		
		for(int i=0;i<10;i++){
			segment[i] = ((value_var[i]*sampleAmount/(sampleAmount-1))/Math.sqrt(sampleAmount))
													*TDistribution.getValue(sampleAmount-1, 0.005);
			trust_up[i] = value_avg[i]+segment[i];
			trust_down[i] = value_avg[i]-segment[i];
			StringBuffer bf = new StringBuffer("( ");
			bf.append(trust_up[i]);
			bf.append(" , ");
			bf.append(trust_down[i]);
			bf.append(" )");
			interval[i] = bf.toString();
		}
		return interval;
	}
	
	/**
	 * 抽取样本
	 * 在一个赛季中以月为单位进行随机抽样，每月随机抽取6天的比赛
	 * 其中10月份抽取1天，4月份抽取4天
	 */
	private ArrayList<PlayerInMatchFull> sampling(int start,int end){
		ArrayList<PlayerInMatchFull> listMatch = new ArrayList<PlayerInMatchFull>();
		int startDay = getDayOfMonth(start, 10);
		int endDay = getDayOfMonth(end, 4);
		//计算最初一场比赛和最后一场比赛的时间
		ArrayList<PlayerInMatchFull> temp = new ArrayList<PlayerInMatchFull>();
		//dataService.getMatchInPeriod(formDate(start, 10, 20), formDate(start,10,startDay));
		for(PlayerInMatchFull match:temp){
			Calendar date = new GregorianCalendar();
			date.setTime(match.getDate());
			int day = date.get(Calendar.DAY_OF_MONTH);
			if(day<startDay)
				startDay = day;
		}
		ArrayList<PlayerInMatchFull> temp2 = new ArrayList<PlayerInMatchFull>();
		//dataService.getMatchInPeriod(formDate(start, 10, 20), formDate(start,10,endDay));
		endDay = 0;
		for(PlayerInMatchFull match:temp2){
			Calendar date = new GregorianCalendar();
			date.setTime(match.getDate());
			int day = date.get(Calendar.DAY_OF_MONTH);
			if(day>endDay)
				endDay = day;
		}
		//抽取比赛
		int[] months = new int[]{10,11,12,1,2,3,4};
		for(int i=0;i<months.length;i++){
			int subSampleSize = 0;
			if(months[i]==10){
				subSampleSize = 1;
			}
			else if(months[i]==4){
				subSampleSize = 4;
			}
			else{
				subSampleSize = 6;
			}
			int[] sampleDays = new int[subSampleSize];
			for(int j=0;j<subSampleSize;j++){
				int year = 0;
				int day = 0;
				if(i<3){
					year = start;
					boolean selected = true;
					while(selected){
						if(i==0){
							day = (int)Math.random()*(getDayOfMonth(start, months[i])-startDay+1)+startDay;
						}
						else{
							day = (int)Math.random()*getDayOfMonth(start, months[i])+1;
						}
						for(int k=0;k<j;k++){
							if(sampleDays[k]!=day){
								selected = false;
								break;
							}
						}
					}
					sampleDays[j] = day;
				}
				else{
					year = end;
					boolean selected = true;
					while(selected){
						if(i==6){
							day = (int)Math.random()*endDay+1;
						}
						else{
							day = (int)Math.random()*getDayOfMonth(end, months[i])+1;
						}
						for(int k=0;k<j;k++){
							if(sampleDays[k]!=day){
								selected = false;
								break;
							}
						}
					}
					sampleDays[j] = day;
				}
				Date sample = formDate(year, months[i], day);
				List<MatchInfo> matches = dataReader.getMatchInPeriod(sample, sample);
				for(MatchInfo match:matches){
					listMatch.add(dataReader.getTeamStatSingle(match.getTeamA(), sample));
					listMatch.add(dataReader.getTeamStatSingle(match.getTeamB(), sample));
				}
			}
		}
		return listMatch;
	}
	
	/**
	 * 抽样：在赛季中随机抽取一天的比赛,抽取50次
	 * @param start
	 * @param end
	 */
	public Map<Integer,List<PlayerInMatchFull>> sampling2(int start,int end,int sampleAmount){
		int startDay = getDayOfMonth(start, 10);
		int endDay = getDayOfMonth(end, 4);
		//计算最初一场比赛和最后一场比赛的时间
		List<MatchInfo> temp = dataReader.getMatchInPeriod(formDate(start,10,20), formDate(start,10,startDay));
		for(MatchInfo match:temp){
			Calendar date = new GregorianCalendar();
			date.setTime(match.getDate());
			int day = date.get(Calendar.DAY_OF_MONTH);
			if(day<startDay)
				startDay = day;
		}
		List<MatchInfo> temp2 = dataReader.getMatchInPeriod(formDate(end,4,20), formDate(end,4,endDay));
		endDay = 0;
		for(MatchInfo match:temp2){
			Calendar date = new GregorianCalendar();
			date.setTime(match.getDate());
			int day = date.get(Calendar.DAY_OF_MONTH);
			if(day>endDay)
				endDay = day;
		}
		//抽取比赛
		Map<Integer,List<PlayerInMatchFull>> sample_matches = new HashMap<Integer,List<PlayerInMatchFull>>();
		int[] months = new int[]{10,11,12,1,2,3,4};
		int[] days = new int[months.length];
		for(int i=0;i<months.length;i++){
			int month = months[i];
			if(month==10){
				days[i] = getDayOfMonth(start, month);
			}
			else if(month>10){
				days[i] = days[i-1]+getDayOfMonth(start, month);
			}
			else{
				days[i] = days[i-1]+getDayOfMonth(end, month);
			}
		}
		for(int i=0;i<sampleAmount;i++){
			int random = (int)Math.random()*days[days.length-1]+1;
			int month = 0;
			int day = 0;
			int year = start;
			for(int j=0;j<days.length;j++){
				if(random>days[j]){
					continue;
				}
				month = months[j];
				day = random-months[j-1];
			}
			if(month<7)
				year = end;
			Date date = formDate(year, month, day);
			List<MatchInfo> matchs = dataReader.getMatchInPeriod(date, date);
			if(matchs==null||matchs.size()==0){
				i--;
				continue;
			}
			List<PlayerInMatchFull> matchList = new ArrayList<PlayerInMatchFull>();
			for(MatchInfo m:matchs){
				matchList.add(dataReader.getTeamStatSingle(m.getTeamA(), m.getDate()));
				matchList.add(dataReader.getTeamStatSingle(m.getTeamB(), m.getDate()));
			}
			sample_matches.put(i+1, matchList);
		}
		return sample_matches;
	}
	
	
	/**
	 * 获得某年某月有几天
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDayOfMonth(int year,int month){
		int day = 0;
		if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
			day = 31;
		}
		else if(month==4||month==6||month==9||month==11){
			day = 30;
		}
		else if(month==2){
			//闰年
			if(year%4==0||year%400==0){
				day = 29;
			}
			//平年
			else{
				day = 28;
			}
		}
		return day;
	}
	
	public Date formDate(int year,int month,int day){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer date = new StringBuffer();
		date.append(year+"-"+month+"-"+day);
		Date sample = null;
        try {
			sample = new Date(format.parse(date.toString()).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sample;
	}
	
	/**
	 * 均值
	 * @param listA
	 * @return
	 */
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
}
