package njuse.ffff.dataGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HtmlReader {
	CloseableHttpResponse httpResponse;
	CloseableHttpClient httpClient;
	public BufferedReader execute(String url) {
		httpClient = HttpClients.createSystem();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			InputStream instream = entity.getContent();
			InputStreamReader inreader = new InputStreamReader(instream,
					StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(inreader);
			// String line = null;
			// while ((line = br.readLine()) != null) {
			// System.out.println(line);
			// }
			
			return br;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(url + " TimeOut!!!!!!!");

		}return null;

	}

}
