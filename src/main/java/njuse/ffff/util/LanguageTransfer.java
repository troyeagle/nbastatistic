package njuse.ffff.util;

import java.util.HashMap;
import java.util.Map;

public class LanguageTransfer {
	public static LanguageTransfer transfer = null;
	
	private Map<String,String> mapCE = new HashMap<String,String>();//中，英
	private Map<String,String> mapEC = new HashMap<String,String>();//英，中
	
	private LanguageTransfer(){
		initChineseToEnglistMap();
		initEnglistToChineseMap();
	}
	
	public static LanguageTransfer getInstance(){
		if(transfer == null){
			transfer = new LanguageTransfer();
		}
		return transfer;
	}
	
	private void initChineseToEnglistMap(){
		mapCE.put("身高", "height");
		mapCE.put("体重", "weight");
		mapCE.put("生日", "birth");
		mapCE.put("年龄", "age");
		mapCE.put("联赛球龄", "experience");
		mapCE.put("编号", "number");
		mapCE.put("毕业学校", "school");
		mapCE.put("位置", "position");
		mapCE.put("联盟", "league");

		mapCE.put("球员得分", "points");
		mapCE.put("进攻篮板", "offensiveRebound");
		mapCE.put("防守篮板", "defensiveRebound");
		mapCE.put("篮板数", "rebound");
		mapCE.put("助攻数", "assist");
		mapCE.put("盖帽数", "block");
		mapCE.put("抢断数", "steal");
		mapCE.put("犯规数", "foul");
		mapCE.put("失误数", "turnover");
		mapCE.put("分钟", "minute");
		mapCE.put("效率", "playerEfficiencyRate");
		mapCE.put("投篮", "fieldGoal");
		mapCE.put("三分", "threePointer");
		mapCE.put("罚球", "freeThrow");
		mapCE.put("两双", "doubledouble");
		
		mapCE.put("投篮命中数", "fieldGoalMade");
		mapCE.put("投篮出手数", "fieldGoalAttempted");
		mapCE.put("三分命中数", "threePointerMade");
		mapCE.put("三分出手数", "threePointerAttempted");
		mapCE.put("罚球命中数", "freeThrowMade");
		mapCE.put("罚球出手数", "freeThrowAttempted");
		
		mapCE.put("投篮命中率", "fieldGoalRatio");
		mapCE.put("三分命中率", "threePointerRatio");
		mapCE.put("罚球命中率", "freeThrowRatio");
		mapCE.put("真实命中率", "trueShootingPercentage");
		
		mapCE.put("进攻篮板率", "offensiveReboundRatio");
		mapCE.put("防守篮板率", "defensiveReboundRatio");
		mapCE.put("篮板率", "reboundRatio");
		mapCE.put("助攻率", "assistRatio");
		mapCE.put("抢断率", "stealRatio");
		mapCE.put("盖帽率", "blockRatio");
		mapCE.put("失误率", "turnoverRatio");
		mapCE.put("使用率", "usingRatio");
		mapCE.put("GmSc效率值", "GmSc");
		
		mapCE.put("近5场得分提升率", "recent5ScoreAdv");
		mapCE.put("近5场篮板提升率", "recent5BlockAdv");
		mapCE.put("近5场助攻提升率", "recent5AssistAdv");
		
		mapCE.put("东部", "E");
		mapCE.put("西部", "W");
		mapCE.put("前锋", "F");
		mapCE.put("中锋", "C");
		mapCE.put("后卫", "G");
		
		mapCE.put("赛季", "season");
		mapCE.put("球队得分", "score");

		mapCE.put("进攻回合", "myRounds");
		mapCE.put("进攻效率", "offensiveEf");
		mapCE.put("防守效率", "defensiveEf");
		mapCE.put("进攻篮板效率", "offensiveReboundEf");
		mapCE.put("防守篮板效率", "defensiveReboundEf");
		mapCE.put("抢断效率", "stealEf");
		mapCE.put("助攻效率", "assistEf");
		
		mapCE.put("胜率", "winningRatio");
	}
	
	private void initEnglistToChineseMap(){
		mapEC.put("height", "身高");
		mapEC.put("weight", "体重");
		mapEC.put("birth", "生日");
		mapEC.put("age", "年龄");
		mapEC.put("experience", "联赛球龄");
		mapEC.put("number", "编号");
		mapEC.put("school", "毕业学校");
		mapEC.put("position", "位置");
		mapEC.put("league", "联盟");

		mapEC.put("points", "球员得分");
		mapEC.put("offensiveRebound", "进攻篮板");
		mapEC.put("defensiveRebound", "防守篮板");
		mapEC.put("rebound", "篮板数");
		mapEC.put("assist", "助攻数");
		mapEC.put("block", "盖帽数");
		mapEC.put("steal", "抢断数");
		mapEC.put("foul", "犯规数");
		mapEC.put("turnover", "失误数");
		mapEC.put("minute", "分钟");
		mapEC.put("playerEfficiencyRate", "效率");
		mapEC.put("fieldGoal", "投篮");
		mapEC.put("threePointer", "三分");
		mapEC.put("freeThree", "罚球");
		mapEC.put("doubledouble", "两双");
		
		mapEC.put("fieldGoalMade", "投篮命中数");
		mapEC.put("fieldGoalAttempted", "投篮出手数");
		mapEC.put("threePointerMade", "三分命中数");
		mapEC.put("threePointerAttempted", "三分出手数");
		mapEC.put("freeThrowMade", "罚球命中数");
		mapEC.put("freeThrowAttempted", "罚球出手数");
		
		mapEC.put("fieldGoalRatio", "投篮命中率");
		mapEC.put("threePointerRatio", "三分命中率");
		mapEC.put("freeThrowRatio", "罚球命中率");
		mapEC.put("efficiencyGoalPercentage", "投篮效率");
		mapEC.put("trueShootingPercentage", "真实命中率");
		
		mapEC.put("offensiveReboundRatio", "进攻篮板率");
		mapEC.put("defensiveReboundRatio", "防守篮板率");
		mapEC.put("reboundRatio", "篮板率");
		mapEC.put("assistRatio", "助攻率");
		mapEC.put("stealRatio", "抢断率");
		mapEC.put("blockRatio", "盖帽率");
		mapEC.put("turnoverRatio", "失误率");
		mapEC.put("usingRatio", "使用率");
		mapEC.put("GmSc", "GmSc效率值");
		
		mapEC.put("recent5ScoreAdv", "近5场得分提升率");
		mapEC.put("recent5BlockAdv", "近5场篮板提升率");
		mapEC.put("recent5AssistAdv", "近5场助攻提升率");
		
		mapEC.put("E", "东部");
		mapEC.put("W", "西部");
		mapEC.put("F", "前锋");
		mapEC.put("C", "中锋");
		mapEC.put("G", "后卫");
		
		mapEC.put("season", "赛季");
		mapEC.put("score", "球队得分");
		
		mapEC.put("myRounds", "进攻回合");
		mapEC.put("offensiveEf", "进攻效率");
		mapEC.put("defensiveEf", "防守效率");
		mapEC.put("offensiveReboundEf", "进攻篮板效率");
		mapEC.put("defensiveReboundEf", "防守篮板效率");
		mapEC.put("stealEf", "抢断效率");
		mapEC.put("assistEf", "助攻效率");
		
		mapEC.put("winningRatio", "胜率");
	}
	
	public String getEnglishForm(String chineseForm){
		return mapCE.get(chineseForm);
	}
	
	public String getChineseForm(String englishForm){
		return mapEC.get(englishForm);
	}
	
//	public static void main(String[] args) {
//		LanguageTransfer lt = LanguageTransfer.getInstance();
//		System.out.println(lt.getEnglishForm("联盟"));
//		System.out.println(lt.getChineseForm("E"));
//	}
}
