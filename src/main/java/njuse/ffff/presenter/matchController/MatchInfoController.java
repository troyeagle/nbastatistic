package njuse.ffff.presenter.matchController;

import java.util.ArrayList;
import java.util.Date;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.matchService.MatchInfoService;
import njuse.ffff.uiservice.MatchViewService;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;

public class MatchInfoController implements MatchInfoService{
	private DataReaderService dataService;
	private static MatchInfoController matchInfoController = null;
	private static TotalUIController totalController = null;
	
	private Date presentDate = null;
	private String presentTeam = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private MatchInfoController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}

	public static MatchInfoController getInstance() {
		if (matchInfoController == null) {
			matchInfoController = new MatchInfoController();
		}
		return matchInfoController;
	}
	/**
	 * 生成一场比赛的界面
	 */
	public void setMatchInfoPanel(MatchViewService panel,Date date,String team) {
		MatchPO match = dataService.getMatch(date, team);
		TeamPO teamA = dataService.getTeamInfo(match.getTeamA(), emptyFilter);
		TeamPO teamB = dataService.getTeamInfo(match.getTeamB(), emptyFilter);
		
//		球队A的队徽
//		File flie_svg = new File(teamA.getPathOfLogo());
//		String f = teamA.getPathOfLogo();
//		String pathOfLogo = f.substring(0, f.length()-4).concat(".png");
//		File flie_png = new File(pathOfLogo);
//		try {
//			SvgConverter.convertToPng(flie_svg, flie_png);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (TranscoderException e) {
//			e.printStackTrace();
//		}
//		ImageIcon icon_teamA = new ImageIcon(pathOfLogo);
//		球队B的队徽		
//		flie_svg = new File(teamB.getPathOfLogo());
//		f = teamB.getPathOfLogo();
//		pathOfLogo = f.substring(0, f.length()-4).concat(".png");
//		flie_png = new File(pathOfLogo);
//		try {
//			SvgConverter.convertToPng(flie_svg, flie_png);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (TranscoderException e) {
//			e.printStackTrace();
//		}
//		ImageIcon icon_teamB = new ImageIcon(pathOfLogo);
		
		//球队A、B的分数
		ArrayList<Integer> score_A = match.getScoreA();
		ArrayList<Integer> score_B = match.getScoreB();
		int[] score_teamA = new int[5];
		int[] score_teamB = new int[5];
		for(int i=0;i<5;i++){
			score_teamA[i] = score_A.get(i);
			score_teamB[i] = score_B.get(i);
		}
		
//		String[] properties = {"球员","首发","时间","投篮","命中","出手","三分","命中","出手"
//				,"罚球","命中","出手","真实命中","篮板","前场","后场","助攻","抢断","盖帽"
//				,"失误","犯规","得分"};
		
		//球队A的球员的数据
		ArrayList<PlayerInMatchExtended> playerListForTeamA = match.getPlayerInAEx();
		Object[][] values_teamA = new Object[playerListForTeamA.size()][];
		ArrayList<int[]> dirty_teamA = new ArrayList<int[]>(); //脏数据位置
		for(int i=0;i<playerListForTeamA.size();i++){
			PlayerInMatchExtended player = playerListForTeamA.get(i);
			int firstOnMatch = 0;//1代表是首发，0代表不是首发
			if(player.isFirstOnMatch()){
				firstOnMatch = 1;
			}
			values_teamA[i] = new Object[]{
					player.getName(),firstOnMatch,player.getMinute()					//球员，首发，上场时间
					,DealDecimal.formatChangeToPercentage(player.getFieldGoalRatio())	//投篮命中率
					,DealDecimal.formatChange(player.getFieldGoalMade())				//投篮命中数
					,DealDecimal.formatChange(player.getFieldGoalAttempted())			//投篮出手数
					,DealDecimal.formatChangeToPercentage(player.getThreePointerRatio())//三分命中率
					,DealDecimal.formatChange(player.getThreePointerMade())				//三分命中数
					,DealDecimal.formatChange(player.getThreePointerAttempted())		//三分出手数
					,DealDecimal.formatChangeToPercentage(player.getFreeThrowRatio())	//罚球命中率
					,DealDecimal.formatChange(player.getFreeThrowMade())				//罚球命中数
					,DealDecimal.formatChange(player.getFreeThrowAttempted())			//罚球出手数
					,DealDecimal.formatChangeToPercentage(player.getTrueShootingPercentage())//真实命中率
					,DealDecimal.formatChange(player.getRebound())						//篮板数
					,DealDecimal.formatChange(player.getOffensiveRebound())				//进攻篮板数
					,DealDecimal.formatChange(player.getDefensiveRebound())				//防守篮板数
					,DealDecimal.formatChange(player.getAssist())						//助攻数
					,DealDecimal.formatChange(player.getSteal())						//抢断数
					,DealDecimal.formatChange(player.getBlock())						//盖帽数
					,DealDecimal.formatChange(player.getTurnover())						//失误数
					,DealDecimal.formatChange(player.getFoul())							//犯规数
					,DealDecimal.formatChange(player.getPoints())						//该场得分
					//playerID
			};
			
			ArrayList<Integer> dirty_playerInfo = player.getDirty();
			for(Integer loc:dirty_playerInfo){
				int dirtyLoc = transferDirtyLoc(loc);
				if(dirtyLoc>=0){
					dirty_teamA.add(new int[]{i,dirtyLoc});
				}
			}
		}
		
		//球队B的球员的数据
		ArrayList<PlayerInMatchExtended> playerListForTeamB = match.getPlayerInAEx();
		ArrayList<int[]> dirty_teamB = new ArrayList<int[]>();  //脏数据位置
		Object[][] values_teamB = new Object[playerListForTeamB.size()][];
		for(int i=0;i<playerListForTeamB.size();i++){
			PlayerInMatchExtended player = playerListForTeamB.get(i);
			int firstOnMatch = 0;//1代表是首发，0代表不是首发
			if(player.isFirstOnMatch()){
				firstOnMatch = 1;
			}
			values_teamB[i] = new Object[]{
					player.getName(),firstOnMatch,player.getMinute()					//球员，首发，上场时间
					,DealDecimal.formatChangeToPercentage(player.getFieldGoalRatio())	//投篮命中率
					,DealDecimal.formatChange(player.getFieldGoalMade())				//投篮命中数
					,DealDecimal.formatChange(player.getFieldGoalAttempted())			//投篮出手数
					,DealDecimal.formatChangeToPercentage(player.getThreePointerRatio())//三分命中率
					,DealDecimal.formatChange(player.getThreePointerMade())				//三分命中数
					,DealDecimal.formatChange(player.getThreePointerAttempted())		//三分出手数
					,DealDecimal.formatChangeToPercentage(player.getFreeThrowRatio())	//罚球命中率
					,DealDecimal.formatChange(player.getFreeThrowMade())				//罚球命中数
					,DealDecimal.formatChange(player.getFreeThrowAttempted())			//罚球出手数
					,DealDecimal.formatChangeToPercentage(player.getTrueShootingPercentage())//真实命中率
					,DealDecimal.formatChange(player.getRebound())						//篮板数
					,DealDecimal.formatChange(player.getOffensiveRebound())				//进攻篮板数
					,DealDecimal.formatChange(player.getDefensiveRebound())				//防守篮板数
					,DealDecimal.formatChange(player.getAssist())						//助攻数
					,DealDecimal.formatChange(player.getSteal())						//抢断数
					,DealDecimal.formatChange(player.getBlock())						//盖帽数
					,DealDecimal.formatChange(player.getTurnover())						//失误数
					,DealDecimal.formatChange(player.getFoul())							//犯规数
					,DealDecimal.formatChange(player.getPoints())						//该场得分
					//playerID
			};
			
			ArrayList<Integer> dirty_playerInfo = player.getDirty();
			for(Integer loc:dirty_playerInfo){
				int dirtyLoc = transferDirtyLoc(loc);
				if(dirtyLoc>=0){
					dirty_teamB.add(new int[]{i,dirtyLoc});
				}
			}
		}
		
		int[][] dirty_A = new int[dirty_teamA.size()][];
		dirty_A = dirty_teamA.toArray(dirty_A);
		int[][] dirty_B = new int[dirty_teamB.size()][];
		dirty_A = dirty_teamB.toArray(dirty_B);
		
		panel.setHostTeamInfo(teamA.getName(), score_teamA, values_teamA, dirty_A);
		panel.setGuestTeamInfo(teamB.getName(), score_teamB, values_teamB, dirty_B);
		
		presentDate = date;
		presentTeam = team;
		totalController.setMatchViewService(panel);
	}
	
	/**
	 * 判断脏数据的位置
	 * @param loc
	 * @return
	 */
	private int transferDirtyLoc(int loc){
		int dirtyLoc = -1;
		switch(loc){
		case 0:
			dirtyLoc = 0;
			break;
		case 1:
			break;
		case 2:
			dirtyLoc = 2;
			break;
		case 3:
			dirtyLoc = 4;
			break;
		case 4:
			dirtyLoc = 5;
			break;
		case 5:
			dirtyLoc = 7;
			break;
		case 6:
			dirtyLoc = 8;
			break;
		case 7:
			dirtyLoc = 10;
			break;
		case 8:
			dirtyLoc = 11;
			break;
		case 9:
			dirtyLoc = 14;
			break;
		case 10:
			dirtyLoc = 15;
			break;
		case 11:
			dirtyLoc = 13;
			break;
		case 12:
			dirtyLoc = 16;
			break;
		case 13:
			dirtyLoc = 17;
			break;
		case 14:
			dirtyLoc = 18;
			break;
		case 15:
			dirtyLoc = 19;
			break;
		case 16:
			dirtyLoc = 20;
			break;
		case 17:
			dirtyLoc = 21;
			break;
		}
		return dirtyLoc;
	}

	public Date getPresentDate() {
		return presentDate;
	}

	public String getPresentTeam() {
		return presentTeam;
	}
	
}
