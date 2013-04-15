package debug;

import java.io.IOException;
import java.util.Random;

import delphi.KeyWordExpert;

public class Test_KeyWordExpert {
	public static void main(String[] args) {
		String[] news;
		long startTime, endTime, runTime, totalTime, testRuns, errors, empty;
		
		Random randy = new Random();
		for (int n = 0; n < 25; n++) {
			randy.nextInt();
		}
		
		String[] symbols = new String[] {"goog", "msft", "amzn", "csco", "aapl", "able", "nvda", "jpm", "lnkd", "uvxy"};
		
		testRuns = 2;
		errors = 0; empty = 0;
		totalTime = 0;
		
		System.out.printf("[*] Starting %s test runs\n", testRuns);
		for (int run = 0; run < testRuns; run++) { 
			startTime = System.currentTimeMillis() /* MilliSeconds */;
			KeyWordExpert expert = new KeyWordExpert();
			try {
				news = expert.getNews(symbols[randy.nextInt(symbols.length)]);
			} catch (IOException e) {
				news = null;
			}
			endTime = System.currentTimeMillis() /* MilliSeconds */;
			runTime = (endTime - startTime) /* MilliSeconds */;
			if (news == null) {
				errors++;
			} else if (news.equals("")) {
				empty++;
			} else {
				System.out.printf("[+]\t%s\t%s\t%s\n", news[0].contains("Strong"), news[1].contains("Strong"), news[2].contains("Strong"));				
			}
			totalTime += runTime /* MilliSeconds */;
		}
		
		System.out.printf("[+] Completed in %1$.2f seconds\n", (totalTime / 1000.00));
		System.out.printf("[+] Each run completed in an average of %1$.2f seconds\n", ((totalTime / 1000.00) / testRuns));
		System.out.printf("[+] %s errors caught\n", errors);
		System.out.printf("[+] %s empty resonses\n", empty);
	}
}
