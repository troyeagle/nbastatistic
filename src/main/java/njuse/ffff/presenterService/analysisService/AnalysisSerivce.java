package njuse.ffff.presenterService.analysisService;

import java.util.ArrayList;

import njuse.ffff.util.BasicPlayerInfo;
import njuse.ffff.vo.DefendFactor;
import njuse.ffff.vo.OffendFactor;
import njuse.ffff.vo.PlayerSteady;


public interface AnalysisSerivce {
	//设置球员进攻分析参与的赛季
	public String[] getOffendInvolvedSeason(String playerID);
	//设置球员进攻分析界面
	public OffendFactor getPlayerOffendAnalysis(String playerID,String season);
	//设置球员防守分析参与的赛季
	public String[] getDefendInvolvedSeason(String playerID);
	//设置球员防守分析界面
	public DefendFactor getPlayerDefendAnalysis(String playerID,String season);
	
	//设置联盟风格分析
	public String[][] getDefaultLeagueAnalysis();
	//设置自定义联盟风格分析(连续5年内)
	public String[] getSelfLeagueAnalysis(String startSeason);
	
	//获取球员稳定性分析
	public ArrayList<PlayerSteady> getPlayerSteadyAnalysis(String playerID);
	
	//设置球员属性关联性分析  暂时木有用
	public void getCorrelationAnalysis(String attribute1,String attribute2);
	
	//搜索在1980赛季以后打过篮球、并且包含关键字的球员
	public BasicPlayerInfo[] searchAnalysisPlayer(String input);
}
