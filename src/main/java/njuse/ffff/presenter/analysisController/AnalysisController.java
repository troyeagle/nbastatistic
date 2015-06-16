package njuse.ffff.presenter.analysisController;

import java.util.ArrayList;
import java.util.List;

import njuse.ffff.dataservice.NewDataReaderService;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenter.analysisController.playerAnalysis.PlayerDefendController;
import njuse.ffff.presenter.analysisController.playerAnalysis.PlayerOffendController;
import njuse.ffff.presenter.analysisController.playerAnalysis.PlayerSteadyAnalysis;
import njuse.ffff.presenter.analysisController.styleAnalysis.LeagueStyleAnalysis;
import njuse.ffff.presenterService.analysisService.AnalysisSerivce;
import njuse.ffff.sqlpo.PlayerInfo;
import njuse.ffff.util.BasicPlayerInfo;
import njuse.ffff.vo.DefendFactor;
import njuse.ffff.vo.OffendFactor;
import njuse.ffff.vo.PlayerSteady;

public class AnalysisController implements AnalysisSerivce{
	private NewDataReaderService dataReader;
	private static AnalysisController analysisController = null;
	private static PlayerOffendController playerOffendController = null;
	private static PlayerDefendController playerDefendController = null;
	private static LeagueStyleAnalysis leagueStyleAnalysis = null;
	private static PlayerSteadyAnalysis playerSteadyAnalysis = null;
	private static TotalUIController totalController = null;
	
	private AnalysisController() {
		playerOffendController = PlayerOffendController.getInstance();
		playerDefendController = PlayerDefendController.getInstance();
		leagueStyleAnalysis = LeagueStyleAnalysis.getInstance();
		playerSteadyAnalysis = PlayerSteadyAnalysis.getInstance();
		totalController = TotalUIController.getInstance();
		dataReader = totalController.getDataReader();
		}

	public static AnalysisController getInstance() {
		if (analysisController == null) {
			analysisController = new AnalysisController();
		}
		return analysisController;
	}

	/**
	 * 设置默认联盟分析界面
	 */
	@Override
	public String[][] getDefaultLeagueAnalysis() {
		String[][] value = leagueStyleAnalysis.analyseLeagueStyle();
		return value;
	}

	/**
	 * 设置自定义联盟分析界面
	 */
	@Override
	public String[] getSelfLeagueAnalysis(String startSeason) {
		String[] temp = startSeason.split("-");
		int end = Math.min(Integer.parseInt(temp[0])+4, 2014);
		StringBuffer seasons = new StringBuffer(startSeason+"-"+end);
		String[] value = leagueStyleAnalysis.calculateCharactoristic2(seasons.toString());
		return value;
	}

	@Override
	public void getCorrelationAnalysis(String attribute1, String attribute2) {
		// TODO 自动生成的方法存根
		
	}
	
	/**
	 * 设置球员进攻分析界面
	 */
	@Override
	public OffendFactor getPlayerOffendAnalysis(String playerID,String season){
		return playerOffendController.analyseOffend(playerID, season);
	}
	
	/**
	 * 设置球员防守分析界面
	 */
	@Override
	public DefendFactor getPlayerDefendAnalysis(String playerID,String season){
		return playerDefendController.analyseDefend(playerID, season);
	}

	@Override
	public String[] getOffendInvolvedSeason(String playerID) {
		List<String> seasons = dataReader.selectSeasonsByPlayer(playerID);
		List<String> seasonList = new ArrayList<String>();
		for(String s:seasons){
			String[] season = s.split("[-]");
			if(Integer.parseInt(season[0])>=1980){
				seasonList.add(s);
			}
		}
		String[] seasonss = new String[seasonList.size()];
		for(int i=0;i<seasonList.size();i++){
			seasonss[i] = seasonList.get(i);
		}
		return seasonss;
	}
	
	@Override
	public String[] getDefendInvolvedSeason(String playerID) {
		List<String> seasons = dataReader.selectSeasonsByPlayer(playerID);
		List<String> seasonList = new ArrayList<String>();
		for(String s:seasons){
			String[] season = s.split("[-]");
			if(Integer.parseInt(season[0])>=1980){
				seasonList.add(s);
			}
		}
		String[] seasonss = new String[seasonList.size()];
		for(int i=0;i<seasonList.size();i++){
			seasonss[i] = seasonList.get(i);
		}
		return seasonss;
	}

	@Override
	public ArrayList<PlayerSteady> getPlayerSteadyAnalysis(String playerID) {
		return playerSteadyAnalysis.playerSteady(playerID);
	}
	
	public BasicPlayerInfo[] searchAnalysisPlayer(String input){
		ArrayList<BasicPlayerInfo> listPlayers = new ArrayList<>();
		input = input.toUpperCase();
		List<PlayerInfo> data_player = dataReader.getPlayerInfoAll("");
		for (PlayerInfo player : data_player) {
			if (player.getPlName().toUpperCase().contains(input)) {
				List<String> seasons = dataReader.selectSeasonsByPlayer(player.getIdPlayerInfo());
				boolean isValid = false;
				for(String s:seasons){
					String[] temp = s.split("-");
					if(Integer.parseInt(temp[0])>=1980){
						isValid = true;
					}
				}
				if(isValid)
					listPlayers.add(new BasicPlayerInfo(player.getPlName(),player.getIdPlayerInfo()));
			}
		}
		return listPlayers.toArray(new BasicPlayerInfo[0]);
	}

}
