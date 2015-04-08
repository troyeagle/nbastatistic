package njuse.ffff.presenter.matchController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.matchService.MatchsViewService;
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
	 * TODO 赛季？
	 * 显示所有比赛
	 */
	public void setMatchsViewPanel(String season) {
		Date dateStart = new Date();
		Date dateEnd = new Date();
		List<MatchPO> matchList = dataService.getMatchInPeriod(dateStart, dateEnd);
		
		for(MatchPO match:matchList){
			
		}
	}

}
