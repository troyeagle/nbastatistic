package njuse.ffff.presenter.matchController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.matchService.MatchsViewService;
import njuse.ffff.uiservice.MatchesLogOverviewService;
import njuse.ffff.util.Filter;

public class MatchsViewController implements MatchsViewService{
	private DataReaderService dataService;
	private static MatchsViewController matchsViewController = null;
	private static TotalUIController totalController = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
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
	public void setMatchsViewPanel(MatchesLogOverviewService panel,String year,String month) {
		Date dateStart = new Date();
		Date dateEnd = new Date();
//		if(season.equals("12-13")){
//			dateStart.setYear(2012);
//		}
		
		List<MatchPO> matchList = dataService.getMatchInPeriod(dateStart, dateEnd);
		
		for(MatchPO match:matchList){
			
		}
	}

}
