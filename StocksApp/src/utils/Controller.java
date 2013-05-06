package utils;

import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;

import stocks.Stock;
import stocks.YahooFinance;
import delphi.FinalScore;
import delphi.Top10Expert;

/**
 * Used to fetch a Stock object based on the symbol parameter. Symbol parameter
 * is check for pattern resembling stock tickers Stock returns null for errors
 * and for invalid stock symbols
 * 
 * @author Team 3+4
 * 
 */
public class Controller {

	/**
	 * @Name getTop10()
	 * @Input null
	 * @return type String[]
	 * @description Returns the top10 list of stocks
	 */
	public static String[] getTop10() {
		String[] top10 = Top10Expert.getTop10Regex();

		// Attempting to keep app from crashing list isn't fetched
		if (top10 == null) {
			top10 = new String[] {"List Unavailable"};
		}
			
		return top10;
	}
	
	/**
	 * @Name getBottom10()
	 * @Input null
	 * @return type String[]
	 * @description Returns the Bottom 10 list of stocks
	 */
	public static String[] getBottom10() {
		String[] bottom10 = Top10Expert.getBottom10Regex();

		// Attempting to keep app from crashing list isn't fetched
		if (bottom10 == null)
			bottom10 = new String[] {"List Unavailable"};
		
		return bottom10;
	}

	/**
	 * @Name getCompanyName()
	 * @Input String
	 * @return type String
	 * @description Returns company name of the stock.
	 */
	public static String getComanyName(String symbol) {
		String companyName = YahooFinance.getCompanyName(symbol);

		return companyName;

	}

	/**
	 * @Name getStock()
	 * @Input String
	 * @return type Stock
	 * @description Returns stock object for the given input stock name.
	 */
	public static Stock getStock(String symbol) {
		symbol = symbol.toUpperCase(Locale.US);
		Stock stock;
		String[] stockInfo;
		double finalScore = 0.00;

		if (HistoryStack.fetch(symbol) != null) {
			stock = HistoryStack.fetch(symbol);
		} else {
			try {
				stockInfo = stocks.YahooFinance.searchSymbol(symbol);
				stock = new Stock(stockInfo);
				stock.setPastPrices(stocks.PastClosingPrices.fetch(symbol));
				finalScore = FinalScore.getScore(stock);
				stock.setScore(finalScore);
				HistoryStack.push(stock);
			} catch (MalformedURLException e) {
				stock = null;
			} catch (InterruptedException e) {
				stock = null;
			}
		}

		return stock;
	}

	/**
	 * @Name HistoryStack()
	 * @Input 
	 * @return type 
	 * @description This method is used to store the History of the Past 25 stocks that are searched so that 
	 * fetching the same stock info in future would fasten the process of displaying stock info to the user.
	 */
	private static class HistoryStack {
		private static final int STACK_SIZE = 15;
		private static Hashtable<String, Stock> stack = new Hashtable<String, Stock>(
				STACK_SIZE);
		private static Random rand = new Random();

		/**
		 * @Name push()
		 * @Input Stock
		 * @return type 
		 * @description This method pushes the stock object onto the stack for faster retrieval of the 
		 * same stock in future. 
		 */
		public static void push(Stock stock) {
			String symbol = stock.getSymbol();
			int delKey;
			Enumeration<String> keys;
			String key = null;
			if (!stack.containsKey(symbol)) {
				if (stack.size() >= STACK_SIZE) {
					keys = stack.keys();
					delKey = rand.nextInt(stack.size());
					for (int n = 0; n <= delKey; n++) {
						key = keys.nextElement();
					}
					if (key != null) {
						stack.remove(key);
					}
				}

				stack.put(symbol, stock);
			}
		}


		/**
		 * @Name fetch()
		 * @Input String
		 * @return type Stock 
		 * @description This method is used to check and fetch the Stock object from the stack.
		 */
		public static Stock fetch(String symbol) {
			Stock stock;
			if (stack.containsKey(symbol)) {
				stock = stack.get(symbol);
			} else {
				stock = null;
			}
			return stock;
		}
	}
}