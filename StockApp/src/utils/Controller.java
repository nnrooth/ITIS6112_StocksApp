package utils;

import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import delphi.FinalScore;
import stocks.Stock;

public class Controller {
	
	public static Stock getStock(String symbol) {
		Stock stock; String[] stockInfo;
		double finalScore = 0.00;
		
		if (HistoryStack.fetch(symbol) != null) {
			stock = HistoryStack.fetch(symbol);
		} else {
			try {
				stockInfo = stocks.YahooFinance.searchSymbol(symbol);
				stock = new Stock(stockInfo);
				finalScore = FinalScore.getScore(stock);
				stock.setScore(finalScore);
				HistoryStack.push(stock);
			} catch (MalformedURLException | InterruptedException e) {
				stock = null;
				e.printStackTrace();
			}
		}

		return stock;
	}
	
	private static class HistoryStack {
		private static final int STACK_SIZE = 20;
		private static Hashtable<String, Stock> stack = new Hashtable<String, Stock>(STACK_SIZE);
		private static Random rand = new Random();
		
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
		
		public static Stock fetch(String symbol) {
			Stock stock;
			if (stack.containsKey(symbol.toUpperCase())) {
				stock = stack.get(symbol.toUpperCase());
			} else {
				stock = null;
			}
			return stock;
		}
	}
}
