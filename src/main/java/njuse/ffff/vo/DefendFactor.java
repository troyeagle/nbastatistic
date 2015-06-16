package njuse.ffff.vo;

public class DefendFactor {
	private String playerID;
	private String season;
	private String position;
	/**
	 * 1.防守篮板
	 */
	private double DRB_ratio = 0;//防守篮板率defensiveReboundRatio：DRB%
	private double DRBperGame_percentage = 0;//场均防守篮板占个人前后篮板的比例
	private int DRB_rank = 0;//防守篮板率排名
	private String analysisOfDRB = new String();
	//防守篮板率联盟前三十位：擅长保护后场篮板
	
	/**
	 * 2.抢断能力
	 */
	private double stealRatio = 0;//抢断率：STL%
	private int stealRatio_rank = 0;//抢断率联盟排名
	private double stealperGame = 0;//场均抢断
	private double stealperGame_league = 0;//联盟平均水平(场均抢断)
	private double stealper36Minutes = 0;//每36分钟助攻
	private double stealper36Minutes_league = 0;//联盟平均水平(每36分钟抢断)
	private String analysisOfSteal = new String();
	//每36分钟抢断联盟前三十：抢断能力强
	
	/**
	 * 3.封堵能力
	 */
	private double blockRatio = 0;//盖帽率：BLK%
	private int blockRatio_rank = 0;//盖帽率联盟排名
	private double blockperGame = 0;//场均盖帽
	private double blockperGame_league = 0;//联盟平均水平(场均盖帽)
	private double blockper36Minutes = 0;//每36分钟盖帽率
	private double blockper36Minutes_league = 0;//联盟平均水平(每36分钟盖帽率）
	private String analysisOfBlock = new String();
	//每36分钟封盖联盟前三十：善于篮下封盖
	
	/**
	 * 4.防守贡献
	 */
	private double DWS = 0;//防守贡献值
	private int DWS_rank = 0;//防守贡献值联盟排名
	private String analysisOfDWS = new String();
	//防守贡献值联盟前三十：防守水准联盟一流
	
	public DefendFactor(String playerID,String season,String position,
			double dRB_ratio, double dRBperGame_percentage,
			int dRB_rank, String analysisOfDRB, double stealRatio,
			int stealRatio_rank, double stealperGame,
			double stealperGame_league, double stealper36Minutes,
			double stealper36Minutes_league, String analysisOfSteal,
			double blockRatio, int blockRatio_rank, double blockperGame,
			double blockperGame_league, double blockper36Minutes,
			double blockper36Minutes_league, String analysisOfBlock,
			double dWS, int dWS_rank, String analysisOfDWS) {
		super();
		this.playerID = playerID;
		this.season = season;
		this.position = position;
		DRB_ratio = dRB_ratio;
		DRBperGame_percentage = dRBperGame_percentage;
		DRB_rank = dRB_rank;
		this.analysisOfDRB = analysisOfDRB;
		this.stealRatio = stealRatio;
		this.stealRatio_rank = stealRatio_rank;
		this.stealperGame = stealperGame;
		this.stealperGame_league = stealperGame_league;
		this.stealper36Minutes = stealper36Minutes;
		this.stealper36Minutes_league = stealper36Minutes_league;
		this.analysisOfSteal = analysisOfSteal;
		this.blockRatio = blockRatio;
		this.blockRatio_rank = blockRatio_rank;
		this.blockperGame = blockperGame;
		this.blockperGame_league = blockperGame_league;
		this.blockper36Minutes = blockper36Minutes;
		this.blockper36Minutes_league = blockper36Minutes_league;
		this.analysisOfBlock = analysisOfBlock;
		DWS = dWS;
		DWS_rank = dWS_rank;
		this.analysisOfDWS = analysisOfDWS;
	}
	
	public String getPlayerID() {
		return playerID;
	}
	public String getSeason() {
		return season;
	}
	public String getPosition() {
		return position;
	}
	public double getDRB_ratio() {
		return DRB_ratio;
	}
	public double getDRBperGame_percentage() {
		return DRBperGame_percentage;
	}
	public int getDRB_rank() {
		return DRB_rank;
	}
	public String getAnalysisOfDRB() {
		return analysisOfDRB;
	}
	public double getStealRatio() {
		return stealRatio;
	}
	public int getStealRatio_rank() {
		return stealRatio_rank;
	}
	public double getStealperGame() {
		return stealperGame;
	}
	public double getStealperGame_league() {
		return stealperGame_league;
	}
	public double getStealper36Minutes() {
		return stealper36Minutes;
	}
	public double getStealper36Minutes_league() {
		return stealper36Minutes_league;
	}
	public String getAnalysisOfSteal() {
		return analysisOfSteal;
	}
	public double getBlockRatio() {
		return blockRatio;
	}
	public int getBlockRatio_rank() {
		return blockRatio_rank;
	}
	public double getBlockperGame() {
		return blockperGame;
	}
	public double getBlockperGame_league() {
		return blockperGame_league;
	}
	public double getBlockper36Minutes() {
		return blockper36Minutes;
	}
	public double getBlockper36Minutes_league() {
		return blockper36Minutes_league;
	}
	public String getAnalysisOfBlock() {
		return analysisOfBlock;
	}
	public double getDWS() {
		return DWS;
	}
	public int getDWS_rank() {
		return DWS_rank;
	}
	public String getAnalysisOfDWS() {
		return analysisOfDWS;
	}
}
