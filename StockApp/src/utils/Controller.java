package utils;

import stocks.Stock;
import stocks.YahooFinance;

public class Controller {

	public static Stock search(String symbol) {
		Stock stock; String stockSymbol;
		String[] stockInfo;
		
		long startTime, endTime, runTime;
		
		stockSymbol = "";
			
		startTime = System.currentTimeMillis();
		stockInfo = YahooFinance.searchSymbol(stockSymbol);
		if (stockInfo != null) {
			stock = new Stock(stockInfo);
			stock.setScore(delphi.FinalScore.getScore(stock));
		} else {
			stock = new Stock();
		}
			
		endTime = System.currentTimeMillis();
		runTime = (endTime - startTime) / 1000;
		
		return stock;
	}
}