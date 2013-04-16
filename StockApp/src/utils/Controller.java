package utils;

import delphi.FinalScore;
import stocks.Stock;
import stocks.YahooFinance;

public class Controller {
	
	public static Stock search(String symbol) {
		Stock stock; String stockSymbol;
		String[] stockInfo;
		
		stockSymbol = "";
			
		stockInfo = YahooFinance.searchSymbol(stockSymbol);
		if (stockInfo != null) {
			stock = new Stock(stockInfo);
			stock.setScore(delphi.FinalScore.getScore(stock));
		} else {
			stock = new Stock();
		}
			
		return stock;
	}
	
	public static Stock getStock(String symbol) {
		Stock stock;
		
		String[] stockInfo = stocks.YahooFinance.searchSymbol(symbol);
		stock = new Stock(stockInfo);

		double finalScore = 0.00;
		
		finalScore = FinalScore.getScore(stock);
		stock.setScore(finalScore);
		
		return stock;
	}
}