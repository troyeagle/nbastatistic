package njuse.ffff.vo;

public class OffendFactor {
	private String playerID;
	private String season;
	
	/**
	 * 一、命中率
	 */
	private String[] titleOfFG_Ratio = new String[]{"2P","0-3","3-10","10-16","16 <3","3P"};
	private double[] FG_RatioByDistance = new double[6];//各个部分命中率
	private String analysisOfFG_Ratio = new String();
	//篮下命中率高的（60%），finish能力较强
	//三分命中率高（40%）：三分较准

	/**
	 * 二、出手分布
	 */
	private String[] titleOfFGA_Percentage = new String[]{"0-3","3-10","10-16","16 <3","3P"};
	private double[] FGA_PercentageByDistance = new double[5];//出手分布的命中率
	private String analysisOfFGA_Percentage = new String();
	//内线出手比例高的外线球员（50%）：偏爱突破
	//内线出手比例低的内线球员（30%以下）：大个子投手

	/**
	 * 三、前场篮板
	 */
	private double ORB_Ratio = 0;//进攻篮板率 offensiveReboundRatio:ORB%
	private double ORBperGame_Percentage = 0;//场均进攻篮板占个人前后篮板的比例
	private int ORB_rank = 0;//进攻篮板率排名
	private String analysisOfORB = new String();
	//rank<=30,擅长拼抢前场篮板
	
	/**
	 * 四、传球意识
	 */
	private double assistRatio = 0;//助攻率:AST%
	private int assistRatio_rank = 0;//助攻率联盟排名
	private double assistperGame = 0;//场均助攻
	private double assistperGame_league = 0;//联盟平均水平(场均助攻)
	private double assistper36Minutes = 0;//每36分钟助攻
	private double assistper36Minutes_league = 0;//联盟平均水平(每36分钟助攻)
	private String analysisOfAssist = new String();
	//每36分钟助攻联盟前三十：传球意识较佳
	
	/**
	 * 五、投篮选择
	 */
	private double trueShootingPercentage = 0;//真实投篮命中率:TS%
	private int TSPercentage_rank = 0;//联盟排名(真实投篮命中率)
//	private double efficiencyGoalPercentage = 0;//投篮效率:
//	private int EFG_rank = 0;//联盟排名(投篮效率)
	private double OWS = 0;//进攻贡献值
	private int OWS_rank = 0;//联盟排名(进攻贡献值)
	private String analysisOfFG_Choice= new String();
	//三项都在联盟前三十——进攻高效

	public OffendFactor(String playerID,String season,
			double[] fG_RatioByDistance, String analysisOfFG_Ratio,
			double[] fGA_PercentageByDistance, String analysisOfFGA_Percentage,
			double oRB_Ratio, double oRBperGame_Percentage, int oRB_rank,
			String analysisOfORB, double assistRatio, int assistRatio_rank,
			double assistperGame, double assistperGame_league,
			double assistper36Minutes, double assistper36Minutes_league,
			String analysisOfAssist, double trueShootingPercentage,
			int tSPercentage_rank, double oWS, int oWS_rank,
			String analysisOfFG_Choice) {
		super();
		this.playerID = playerID;
		this.season = season;
		FG_RatioByDistance = fG_RatioByDistance;
		this.analysisOfFG_Ratio = analysisOfFG_Ratio;
		FGA_PercentageByDistance = fGA_PercentageByDistance;
		this.analysisOfFGA_Percentage = analysisOfFGA_Percentage;
		ORB_Ratio = oRB_Ratio;
		ORBperGame_Percentage = oRBperGame_Percentage;
		ORB_rank = oRB_rank;
		this.analysisOfORB = analysisOfORB;
		this.assistRatio = assistRatio;
		this.assistRatio_rank = assistRatio_rank;
		this.assistperGame = assistperGame;
		this.assistperGame_league = assistperGame_league;
		this.assistper36Minutes = assistper36Minutes;
		this.assistper36Minutes_league = assistper36Minutes_league;
		this.analysisOfAssist = analysisOfAssist;
		this.trueShootingPercentage = trueShootingPercentage;
		TSPercentage_rank = tSPercentage_rank;
		OWS = oWS;
		OWS_rank = oWS_rank;
		this.analysisOfFG_Choice = analysisOfFG_Choice;
	}

	public String[] getTitleOfFG_Ratio() {
		return titleOfFG_Ratio;
	}

	public double[] getFG_RatioByDistance() {
		return FG_RatioByDistance;
	}

	public String getAnalysisOfFG_Ratio() {
		return analysisOfFG_Ratio;
	}

	public String[] getTitleOfFGA_Percentage() {
		return titleOfFGA_Percentage;
	}

	public double[] getFGA_PercentageByDistance() {
		return FGA_PercentageByDistance;
	}

	public String getAnalysisOfFGA_Percentage() {
		return analysisOfFGA_Percentage;
	}

	public double getORB_Ratio() {
		return ORB_Ratio;
	}

	public double getORBperGame_Percentage() {
		return ORBperGame_Percentage;
	}

	public int getORB_rank() {
		return ORB_rank;
	}

	public String getAnalysisOfORB() {
		return analysisOfORB;
	}

	public double getAssistRatio() {
		return assistRatio;
	}

	public int getAssistRatio_rank() {
		return assistRatio_rank;
	}

	public double getAssistperGame() {
		return assistperGame;
	}

	public double getAssistperGame_league() {
		return assistperGame_league;
	}

	public double getAssistper36Minutes() {
		return assistper36Minutes;
	}

	public double getAssistper36Minutes_league() {
		return assistper36Minutes_league;
	}

	public String getAnalysisOfAssist() {
		return analysisOfAssist;
	}

	public double getTrueShootingPercentage() {
		return trueShootingPercentage;
	}

	public int getTSPercentage_rank() {
		return TSPercentage_rank;
	}

	public double getOWS() {
		return OWS;
	}

	public int getOWS_rank() {
		return OWS_rank;
	}

	public String getAnalysisOfFG_Choice() {
		return analysisOfFG_Choice;
	}
}
