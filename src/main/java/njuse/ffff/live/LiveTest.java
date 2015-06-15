package njuse.ffff.live;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * LiveTest说明
 * 1.新建一个对象
 * 2.设置里面的mid为测试的比赛日期，eid可以不用管
 * 3.调用AnalyseResauto和AnalyseSmatchData
 * 4.一直不断地调用。已通过间隔0.3秒静态测试。未通过动态测试
 * 5.关于返回的类是什么意思。。请用Chrome打开http://sports.sina.com.cn/nba/live.html?id=
 * 2015060905
 * 然后审查元素，过滤找到名字中带有match和autocast的包，用下面的Preview自己看。
 * 需要注意的是这边是手动解封装，有一些层次结构可能与原来的JSON略有不同
 * 
 * @author Mebleyev.G.Longinus
 *
 */
public class LiveTest {
	static int eid = 0;
	static String mid = "2015061409";
	CloseableHttpClient httpClient = HttpClients.createDefault();
	HttpGet getReSautoCast = new HttpGet(
			"http://api.sports.sina.com.cn/pbp/?format=json&source=web&withhref=1&callback=mGetterGetReSautocast"
					+ mid
					+ "__"
					+ eid
					+ "&mid="
					+ mid
					+ "&pid=&eid="
					+ eid
					+ "&dpc=1");
	// 投篮数据
	HttpGet getReSeventShoot = new HttpGet(
			"http://api.sports.sina.com.cn/pbp/?format=json&source=show&callback=mGetterGetReSeventShoot"
					+ mid
					+ "__"
					+ eid
					+ "&mid="
					+ mid
					+ "&pid=&eid="
					+ eid
					+ "&dpc=1");
	HttpGet getReSroster = new HttpGet(
			"http://api.sports.sina.com.cn/roster/?format=json&callback=mGetterGetReSroster"
					+ mid + "&mid=" + mid + "&dpc=1");
	HttpGet getReSmatchData = new HttpGet(
			"http://api.sports.sina.com.cn/stats/player/?format=json&callback=mGetterGetReSmatchData"
					+ mid + "&mid=" + mid + "&dpc=1");
	HttpGet getSrankInTeam = new HttpGet(
			"http://api.sports.sina.com.cn/score/rank/?format=json&callback=mGetterGetReSrankInTeam"
					+ mid + "&mid=" + mid + "&dpc=1");
	HttpGet getSmatchInfo = new HttpGet(
			"http://api.sports.sina.com.cn/?p=nba&s=live&a=matchInfo&format=json&callback=mGetterGetReSmatchInfo"
					+ mid + "&id=" + mid + "&dpc=1");
	HttpGet getReSschedule3Day = new HttpGet(
			"http://api.sports.sina.com.cn/?p=nba&s=live&a=dateSchedule&format=json&callback=mGetterGetReSschedule3Day"
					+ mid + "&id=" + mid + "&dpc=1");

	public JSONObject getRequest(HttpGet get) {
		try {
			HttpResponse httpResponse = httpClient.execute(get);

			HttpEntity entity = httpResponse.getEntity();
			InputStream instream = entity.getContent();
			InputStreamReader inreader = new InputStreamReader(instream,
					StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(inreader);
			String line;
			line = br.readLine();
			System.out.println(line);
			try {
				line = line.substring(line.indexOf("{"), line.length() - 1);
			} catch (Exception e) {}
			JSONObject jo = new JSONObject(line);
			return jo;

			// System.out.println((String)jo.get("pbp_msgs"));

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 这个方法提供所有实时信息
	 * 估计是一直刷新，而不是增量更新
	 * 
	 * @return
	 */
	public ArrayList<PlayByPlayMessages> AnalyseReSauto() {
		JSONObject jo = getRequest(getReSautoCast);

		try {
			JSONObject msgs = jo.getJSONObject("result").getJSONObject("data")
					.getJSONObject("pbp_msgs");
			int maxnum = jo.getJSONObject("result").getJSONObject("data")
					.getInt("last_eid");
			ArrayList<PlayByPlayMessages> allmessage = new ArrayList<PlayByPlayMessages>();
			for (int i = 0; i <= maxnum; i++) {
				JSONObject msg;
				try {
					msg = msgs.getJSONObject(String.valueOf(i));
				} catch (JSONException j) {
					continue;
				}
				PlayByPlayMessages p = new PlayByPlayMessages(msg);
				allmessage.add(p);

			}
			return allmessage;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 这个方法提供两支队伍的数据，其中包括了球员数据
	 * 
	 * @return
	 */
	public ArrayList<TeamLiveInfo> AnalyseSmatchData() {
		JSONObject jo = getRequest(getReSmatchData);
		try {
			JSONObject data = jo.getJSONObject("result").getJSONObject("data");
			JSONObject guest = data.getJSONObject("guest");
			TeamLiveInfo g = new TeamLiveInfo(guest);
			JSONObject host = data.getJSONObject("host");
			TeamLiveInfo h = new TeamLiveInfo(host);
			ArrayList<TeamLiveInfo> ret = new ArrayList<TeamLiveInfo>();
			ret.add(h);
			ret.add(g);
			return ret;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String[]> AnalyseSmatchInfo() {
		JSONObject jo = getRequest(getSmatchInfo);
		JSONObject score;
		try {
			score = jo.getJSONObject("result").getJSONObject("data").getJSONObject("score");
			String scorehost = score.getJSONObject("host").getString("scores");
			String[] a = scorehost.substring(2, scorehost.length() - 2).split("\",\"");
			String scoreguest = score.getJSONObject("guest").getString("scores");
			String[] b = scoreguest.substring(2, scoreguest.length() - 2).split("\",\"");
			String[] remain = { score.getJSONObject("remain").getString("status"),
					String.valueOf(score.getJSONObject("remain").getInt("period")) };
			ArrayList<String[]> ret = new ArrayList<String[]>();
			ret.add(a);
			ret.add(b);
			ret.add(remain);
			System.out.println();
			return ret;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {

		while (true) {
			LiveTest l = new LiveTest();
			//l.AnalyseReSauto();
			//l.AnalyseSmatchData();
			l.AnalyseSmatchInfo();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
