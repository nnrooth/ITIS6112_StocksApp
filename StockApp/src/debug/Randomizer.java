package debug;

import java.util.Random;

public class Randomizer {
	private static Random randy = null;
	private static String[] stockList = null;
	
	public static String nextStock() {
		if (randy == null) {
			randy = new Random();
			
			for (int n = 0; n < 25; n++) {
				randy.nextInt();
			}
		}
		
		if (stockList == null) {
			stockList = new String[] {
					"goog", "aapl",
					"amzn", "csco",
					"msft", "ibm",
					"nvda", "intc",
					"amd",  "fb",
					"dell", "rht",
					"logi", "atvi",
					"jpm",	"lnkd",
					"uvxy", "amd"
			};
		}
		
		return stockList[randy.nextInt(stockList.length)];
	}
}
