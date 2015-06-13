package njuse.ffff.presenter.analysisController;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenter.analysisController.playerAnalysis.PlayerDefendController;
import njuse.ffff.presenter.analysisController.playerAnalysis.PlayerOffendController;
import njuse.ffff.presenterService.analysisService.AnalysisSerivce;
import njuse.ffff.uiservice.PlayerDataService;

public class AnalysisController implements AnalysisSerivce{
	private DataReaderService dataService;
	private static AnalysisController analysisController = null;
	private static TotalUIController totalController = null;
	
	private AnalysisController() {
		playerOffendController = PlayerOffendController.getInstance();
		playerDefendController = PlayerDefendController.getInstance();
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
		}

	public static AnalysisController getInstance() {
		if (analysisController == null) {
			analysisController = new AnalysisController();
		}
		return analysisController;
	}

	private static PlayerOffendController playerOffendController = null;
	private static PlayerDefendController playerDefendController = null;

	public void setDefaultLeagueAnalysis() {
		// TODO 自动生成的方法存根
		
	}

	public void setSelfLeagueAnalysis(String startSeason) {
		// TODO 自动生成的方法存根
		
	}

	public void setCorrelationAnalysis(String attribute1, String attribute2) {
		// TODO 自动生成的方法存根
		
	}
	
	/**
	 * 设置球员进攻分析界面
	 */
	public void setPlayerOffendAnalysis(String playerID,String season){
		PlayerDataService panel = null;
		playerOffendController.analyseOffend(panel, playerID, season);
	}
	
	/**
	 * 设置球员防守分析界面
	 */
	public void setPlayerDefendAnalysis(String playerID,String season){
		PlayerDataService panel = null;
		playerDefendController.analyseDefend(panel, playerID, season);
	}

}
