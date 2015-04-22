package njuse.ffff.presenter.matchController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.matchService.MatchsViewService;
import njuse.ffff.uiservice.MatchesLogOverviewService;

public class MatchsViewController implements MatchsViewService{
	private DataReaderService dataService;
	private static MatchsViewController matchsViewController = null;
	private static TotalUIController totalController = null;
	
	private String presentYear = null;
	private String presentMonth = null;
	
//	private static final Filter emptyFilter;
//
//	static {
//		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
//	}
	
	private MatchsViewController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}

	public static MatchsViewController getInstance() {
		if (matchsViewController == null) {
			matchsViewController = new MatchsViewController();
		}
		return matchsViewController;
	}

	/**
	 * 赛季	12-13；13-14；14-15
	 * 显示所有比赛 TODO
	 */
	@SuppressWarnings("deprecation")
	public void setMatchsViewPanel(MatchesLogOverviewService panel,String year,String month) {
		Map<Integer, Object[][]> data = new HashMap<Integer, Object[][]>();
		//起始时间
		Date dateStart = new Date();
		dateStart.setYear(Integer.parseInt(year));
		dateStart.setMonth(Integer.parseInt(month));
		//终止日期
		Date dateEnd = new Date();
		dateEnd.setYear(Integer.parseInt(year));
		dateEnd.setMonth(Integer.parseInt(month));
		//获得比赛信息
		int dayOfMonth = getDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
		for(int day=0;day<dayOfMonth;day++){
			dateStart.setDate(day);
			dateEnd.setDate(day);
			List<MatchPO> matchList = dataService.getMatchInPeriod(dateStart, dateEnd);
			Object[][] matchData = new Object[matchList.size()][];
			for(int i=0;i<matchList.size();i++){
				MatchPO match = matchList.get(i);
				matchData[i] = new Object[]{match.getTeamA(),match.getTeamB()
						,match.getScoreA().get(0),match.getScoreB().get(0)};
			}
			data.put(day, matchData);
		}
		//在界面设置比赛信息
		panel.setGameLog(data);
		presentYear = year;
		presentMonth = month;
		totalController.setMatchsLogService(panel);
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

	public String getPresentYear() {
		return presentYear;
	}

	public String getPresentMonth() {
		return presentMonth;
	}
	
//	public static void main(String[] args) {
//		Date dateStart = new Date();
//		dateStart.setYear(2015);
//		dateStart.setMonth(2);
//		dateStart.setDate(25);
//		Date dateEnd = new Date();
//		dateEnd.setYear(2015);
//		dateEnd.setMonth(2);
//		dateEnd.setDate(25);
//		Date dateM = new Date();
//		dateM.setYear(2015);
//		dateM.setMonth(2);
//		dateM.setDate(24);
////		System.out.println(dateM.compareTo(dateEnd)<=0);
////		System.out.println(dateM.compareTo(dateStart)>=0);
//		System.out.println(dateStart.compareTo(dateM));
//		System.out.println(dateEnd.compareTo(dateM));
//	}

}
