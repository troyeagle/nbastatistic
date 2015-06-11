package njuse.ffff.dataGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import njuse.ffff.util.DatabaseUtility;

public class MatchReaderByYear {
	public void YearTest(int year) throws IOException {
		BufferedReader br;
		HtmlReader hr = new HtmlReader();
		do {
			
			br = hr.execute("http://www.basketball-reference.com/leagues/NBA_"
					+ year + "_games.html");
		} while (br == null);
		StringBuilder sb = new StringBuilder();
		String line = "";
		while (line != null) {

			line = br.readLine();
			sb.append(line);
			if (line.trim().startsWith(
					"<h2 data-mobile-header=\"\" style=\"\">Playoffs</h2>")) {
				break;
			}
		}

		StringBuilder sbPlayoff = new StringBuilder();
		while (line != null) {

			line = br.readLine();
			sbPlayoff.append(line);
		}
		ArrayList<String> matches = matchPattern(sb.toString(),
				"<td align=\"center\" ><a href=\"/boxscores/(.+?).html\">Box Score</a></td>");

		ArrayList<String> playoffs = matchPattern(sbPlayoff.toString(),
				"<td align=\"center\" ><a href=\"/boxscores/(.+?).html\">Box Score</a></td>");
		String str1 = String.valueOf(year % 100);
		if (str1.length() == 1)
			str1 = "0" + str1;
		String str2 = String.valueOf((year + 1) % 100);
		if (str2.length() == 1)
			str2 = "0" + str2;
		str1 = str1 + "-" + str2;
		for (String i : matches) {
			System.out.println("ProcessingMatch " + i + " " + str1 + "常规赛");
			boolean flag = false;
			while (!flag) {
				HtmlReader ht = new HtmlReader();
				flag = new MatchAnalyserNew()
						.execute(
								ht.execute("http://www.basketball-reference.com/boxscores/"
										+ i + ".html"), str1 + "", i);
				ht.httpResponse.close();
				ht.httpClient.close();
			}

		}

		for (String i : playoffs) {
			System.out.println("ProcessingMatch " + i + " " + str1 + "季后赛");
			boolean flag = false;
			while (!flag) {
				HtmlReader ht = new HtmlReader();
				flag = new MatchAnalyserNew()
						.execute(
								ht.execute("http://www.basketball-reference.com/boxscores/"
										+ i + ".html"), str1 + "po", i);
				ht.httpResponse.close();
				ht.httpClient.close();
			}

			
		}
		// System.out.println(playoffs.get(1));
		hr.httpClient.close();
	}

	public synchronized ArrayList<String> matchPattern(String origin,
			String normal) {
		Pattern p = Pattern.compile(normal);
		Matcher m = p.matcher(origin);
		ArrayList<String> matches = new ArrayList<String>();
		while (m.find()) {
			matches.add(m.group(1));
		}// 这里多了一个空格，请注意
		return matches;
	}

	public static void main(String[] args) {
		ExecutorService exe = Executors.newCachedThreadPool();
		YearThread th[] = new YearThread[70];
		DatabaseUtility.init();
		for (int i = 0; i < 30; i++) {
			th[i] = new YearThread(i + 1984);
			exe.execute(th[i]);
		}

		// exe.execute(new YearThread(2014));
	}

}

class YearThread extends Thread {
	int i;

	public YearThread(int i) {
		this.i = i;
	}

	public void run() {
		boolean flag = false;
		while (!flag) {
			try {
				new MatchReaderByYear().YearTest(i);
				flag = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Redo " + i);
				e.printStackTrace();

			}
		}

	}
}
