package njuse.ffff.presenter.playerController;

import java.util.ArrayList;

import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.po.PlayerInAverage;
import njuse.ffff.po.PlayerPO;
import njuse.ffff.presenter.TotalUIController;
import njuse.ffff.presenterService.playerService.PlayerCompareService;
import njuse.ffff.ui.PlayerComparePanel;
import njuse.ffff.util.DealDecimal;
import njuse.ffff.util.Filter;

public class PlayerCompareController implements PlayerCompareService{
	private DataReaderService dataService;
	private static PlayerCompareController playerCompareController = null;
	private static TotalUIController totalController = null;
	
	private static final Filter emptyFilter;

	static {
		emptyFilter = new Filter(new ArrayList<String>(), new ArrayList<String>());
	}
	
	private PlayerCompareController() {
		totalController = TotalUIController.getInstance();
		dataService = totalController.getDataReadController();
	}

	public static PlayerCompareController getInstance() {
		if (playerCompareController == null) {
			playerCompareController = new PlayerCompareController();
		}
		return playerCompareController;
	}

	/**
	 * 设置球员信息一览界面
	 */
	public void setPlayerComparePanel() {
		// TODO 自动生成的方法存根
		//获取所有球员信息
		ArrayList<PlayerPO> data = dataService.getPlayerInfoAll(emptyFilter);

		String[] properties_total = { "球员名称","所属球队","参赛场数","先发场数"
				,"篮板数","助攻数"/**,"在场时间","进攻数","防守数"*/
				,"抢断数","盖帽数","失误数","犯规数","得分","效率"/**,"GmSc效率值""真实命中率",,"投篮效率" */};
//						,"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率"
		String[] properties_average = { "球员名称","所属球队"
				,"篮板数","助攻数","在场时间","投篮命中率","三分命中率","罚球命中率"/**,"进攻数","防守数"*/
				,"抢断数","盖帽数","失误数","犯规数","得分","效率","GmSc效率值","真实命中率","投篮效率"
				,"篮板率","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率" };
		ArrayList<PlayerInAverage> players = new ArrayList<PlayerInAverage>();
		Object[][] values_total = new Object[data.size()][];
		Object[][] values_average = new Object[data.size()][];
		for (int i = 0; i < data.size(); i++) {
			PlayerPO player = data.get(i);
			PlayerInAverage playerAvg = dataService.getPlayerAverage(player.getName(),emptyFilter);
			if(playerAvg==null){
				values_total[i] = new Object[] {player.getName(),"","","","","","","","","","","","","","","",""
						,"","","","","","","","","","","","","","","",""};
				values_average[i] = new Object[] {player.getName(),"","","","","","","","","","","","","","",""
						,"","","","","","","","","","",""};
				continue;
			}
			players.add(playerAvg);
			double[] total = playerAvg.getStatsTotal();
			double[] average = playerAvg.getStatsAverage();
			// TODO
			values_total[i] = new Object[] {
					playerAvg.getName(),playerAvg.getTeamName(),playerAvg.getEffective(),playerAvg.getFirstOnMatch()//球队
					,DealDecimal.formatChange(total[8], 3),DealDecimal.formatChange(total[9], 3)//,"playerAvg.getMinute()"
					/**,"",""*/,DealDecimal.formatChange(total[10], 3)//进攻数,防守数
					,DealDecimal.formatChange(total[11], 3),DealDecimal.formatChange(total[12], 3)
					,DealDecimal.formatChange(total[13], 3),DealDecimal.formatChange(total[14], 3)
					,DealDecimal.formatChange(total[15], 3)//,DealDecimal.formatChange(total[29], 3)
					//,DealDecimal.formatChange(total[19], 3),DealDecimal.formatChange(total[20], 3)
//							,DealDecimal.formatChange(total[21], 3),DealDecimal.formatChange(total[22], 3)
//							,DealDecimal.formatChange(total[23], 3),DealDecimal.formatChange(total[24], 3)
//							,DealDecimal.formatChange(total[25], 3),DealDecimal.formatChange(total[26], 3)
					//,total[27],total[28]
//							,DealDecimal.formatChange(total[27], 3),DealDecimal.formatChange(total[28], 3)
			};
			values_average[i] = new Object[]{
					playerAvg.getName(),playerAvg.getTeamName(),DealDecimal.formatChange(average[8], 3)//,average[8]//球队
					,DealDecimal.formatChange(average[9], 3)//,average[9]//
					,DealDecimal.formatChange(playerAvg.getSecond()/60, 3)//,playerAvg.getSecond()/playerAvg.getEffective()//
					,DealDecimal.formatChange(average[29], 3),DealDecimal.formatChange(average[28], 3)//,average[16],average[17]//
					,DealDecimal.formatChange(average[27], 3)/**,"",""*///,进攻数,防守数,average[18] TODO
					,DealDecimal.formatChange(average[10], 3),DealDecimal.formatChange(average[11], 3)//,average[10],average[11]//
					,DealDecimal.formatChange(average[12], 3),DealDecimal.formatChange(average[13], 3)//,average[12],average[13]//
					,DealDecimal.formatChange(average[14], 3),DealDecimal.formatChange(average[15], 3)//,average[14],average[15]//
					,DealDecimal.formatChange(average[16], 3),DealDecimal.formatChange(average[17], 3)//,average[29],average[20]//
					,DealDecimal.formatChange(average[18], 3),DealDecimal.formatChange(average[19], 3)//,average[19],average[21]//
					,DealDecimal.formatChange(average[20], 3),DealDecimal.formatChange(average[21], 3)//,average[22],average[23]//
					,DealDecimal.formatChange(average[22], 3),DealDecimal.formatChange(average[23], 3)//,average[24],average[25]//
					,DealDecimal.formatChange(average[24], 3),DealDecimal.formatChange(average[25], 3)//,average[26],average[27]//
							,DealDecimal.formatChange(average[26], 3)//,average[28]//
			};
		}

		PlayerComparePanel playerComparePanel = new PlayerComparePanel();
		playerComparePanel.setPlayersTotalInfo(properties_total, values_total,players);
		playerComparePanel.setPlayerAverageInfo( properties_average, values_average);

		totalController.addCurrentPanel(playerComparePanel);
		totalController.switchToPanel(playerComparePanel);
	}
}
