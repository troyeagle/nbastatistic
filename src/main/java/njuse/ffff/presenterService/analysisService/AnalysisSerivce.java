package njuse.ffff.presenterService.analysisService;

public interface AnalysisSerivce {
	//设置联盟风格分析
	public void setDefaultLeagueAnalysis(/**panel1*/);
	//设置自定义联盟风格分析(连续5年内)
	public void setSelfLeagueAnalysis(/**panel1,*/String startSeason);
	
	//设置球员属性关联性分析
	public void setCorrelationAnalysis(/**panel2,*/String attribute1,String attribute2);
}
