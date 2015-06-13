package njuse.ffff.live;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveTest {
	static int eid=0;
	CloseableHttpClient httpClient = HttpClients.createDefault();
	public void getRequest() {
		
		HttpGet httpget = new HttpGet(
				"http://api.sports.sina.com.cn/pbp/?format=json&source=web&withhref=1&callback=mGetterGetReSautocast2015061105__"+eid+"&mid=2015061105&pid=&eid="+eid+"&dpc=1");
		
		try {
			HttpResponse httpResponse = httpClient.execute(httpget);
			
			
			HttpEntity entity = httpResponse.getEntity();
			InputStream instream = entity.getContent();
			InputStreamReader inreader = new InputStreamReader(instream,
					StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(inreader);
			String line;
			line=br.readLine();
			System.out.println(line);
			line=line.substring(line.indexOf("{"), line.length()-1);
			JSONObject jo = new JSONObject(line);
			
			JSONObject a = jo.getJSONObject("result").getJSONObject("data").getJSONObject("pbp_msgs");
			System.out.println(jo.toString());
			//System.out.println((String)jo.get("pbp_msgs"));
			
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
		
		
	}
	
	public static void main(String[] args){
		LiveTest l = new LiveTest();
		l.getRequest();
	}
	
}
