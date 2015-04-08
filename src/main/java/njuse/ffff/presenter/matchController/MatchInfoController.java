package njuse.ffff.presenter.matchController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.MatchPO;
import njuse.ffff.po.PlayerInMatchExtended;
import njuse.ffff.po.TeamPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.matchService.MatchInfoService;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;
import njuse.ffff.util.SvgConverter;

import org.apache.batik.transcoder.TranscoderException;

public class MatchInfoController implements MatchInfoService{
	private DataReaderService dataService;
	private static MatchInfoController matchInfoController = null;
	private static TotalUIController totalController = null;
	
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
	public void setMatchInfoPanel(Date date, String team) {
		MatchPO match = dataService.getMatch(date, team);
		TeamPO teamA = dataService.getTeamInfo(match.getTeamA(), emptyFilter);
		TeamPO teamB = dataService.getTeamInfo(match.getTeamB(), emptyFilter);
		/**
		 * 球队A的队徽
		 */
		File flie_svg = new File(teamA.getPathOfLogo());
		String f = teamA.getPathOfLogo();
		String pathOfLogo = f.substring(0, f.length()-4).concat(".png");
		File flie_png = new File(pathOfLogo);
		try {
			SvgConverter.convertToPng(flie_svg, flie_png);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TranscoderException e) {
			e.printStackTrace();
		}
		ImageIcon icon_teamA = new ImageIcon(pathOfLogo);
		/**
		 * 球队B的队徽
		 */
		flie_svg = new File(teamB.getPathOfLogo());
		f = teamB.getPathOfLogo();
		pathOfLogo = f.substring(0, f.length()-4).concat(".png");
		flie_png = new File(pathOfLogo);
		try {
			SvgConverter.convertToPng(flie_svg, flie_png);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TranscoderException e) {
			e.printStackTrace();
		}
		ImageIcon icon_teamB = new ImageIcon(pathOfLogo);
		
		//球队A的球员的数据
		ArrayList<PlayerInMatchExtended> playerListForTeamA = match.getPlayerInAEx();
		String[] properties = {"球员","首发","时间","投篮","命中","出手","三分","命中","出手"
				,"罚球","命中","出手","真实命中","篮板","前场","后场","助攻","抢断","盖帽"
				,"失误","犯规","得分"};
		
		Object[][] values_teamA = new Object[playerListForTeamA.size()][];
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
					};
		}
		
		
		//球队B的球员的数据
		ArrayList<PlayerInMatchExtended> playerListForTeamB = match.getPlayerInAEx();
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
					};
		}
		
		
	}

}
