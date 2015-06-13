package njuse.ffff.dataGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerList {
	ArrayList<String> allPlayers;
	int base = 0;

	public void playerByLetter(char letter) throws IOException {
		BufferedReader br;
		HtmlReader hr = new HtmlReader();
		do {
			br = hr.execute("http://www.basketball-reference.com/players/"
					+ letter + "/");
		} while (br == null);

		StringBuilder sb = new StringBuilder();
		String line = "";
		while (line != null) {
			line = br.readLine();
			sb.append(line);

		}
		ArrayList<String> players = matchPattern(sb.toString(),
				"<td align=\"left\" ><a href=\"(.+?)\">");
		allPlayers.addAll(players);
		// for(String s:players){
		// HtmlReader subhr = new HtmlReader();
		// System.out.println(s);
		// BufferedReader subbr =
		// subhr.execute("http://www.basketball-reference.com"+s);
		// System.out.println("Reading player "+s);
		// new PlayerAnalyserNew().analyseSingle(subbr,s.substring(11));
		// subhr.httpClient.close();
		// }
	}

	public synchronized String getPlayer() {
		if (base < allPlayers.size()) {
			base++;
			return allPlayers.get(base - 1);
		} else {
			return "End";
		}
	}

	public void playerStatProcess() {
		allPlayers = new ArrayList<String>();
		getAllPlayer();

		ExecutorService exe = Executors.newCachedThreadPool();
		PlayerThread[] pthreads = new PlayerThread[10];
		for (int i = 0; i <10; i++) {
			pthreads[i] = new PlayerThread(i);
			exe.execute(pthreads[i]);
		}
	}

	public static void main(String[] args) {
		new PlayerList().playerStatProcess();
	}

	public synchronized ArrayList<String> matchPattern(String origin,
			String normal) {
		Pattern p = Pattern.compile(normal);
		Matcher m = p.matcher(origin);
		ArrayList<String> matches = new ArrayList<String>();
		while (m.find()) {
			if (m.group(1).contains("player")) {
				matches.add(m.group(1));
			}

		}// 这里多了一个空格，请注意
		return matches;
	}

	public void getAllPlayer() {
		for (char m = 'a'; m <= 'b'; m++) {
			if (m == 'x') {
				continue;
			}
			try {
				playerByLetter(m);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class PlayerThread extends Thread {
		int i;

		PlayerThread(int i) {
			this.i = i;
		}

		public void run() {
			String s;

			
			s = PlayerList.this.getPlayer();
			while (true) {
				
				PlayerAnalyserNew pn = new PlayerAnalyserNew();
				if (s.equals("End")) {
					System.out.println("Thread " + i + "End");
					break;
				}
				HtmlReader subhr = new HtmlReader();
				//BufferedReader subbr = subhr
				//		.execute("http://www.basketball-reference.com" + s);
				System.out.println("Thread" + i + " Reading player " + s);

				try {
					pn.analyseSingle(subhr
							.execute("http://www.basketball-reference.com" + s), s.substring(11));
					subhr.httpClient.close();
					subhr.httpResponse.close();
					s = PlayerList.this.getPlayer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// catch (NullPointerException e) {
//					e.printStackTrace();s = PlayerList.this.getPlayer();
//
//				}

			}

		}
	}

}
