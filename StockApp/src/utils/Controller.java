package utils;

import java.net.MalformedURLException;

import delphi.FinalScore;
import stocks.Stock;

public class Controller {
	
	public static Stock getStock(String symbol) {
		Stock stock; String[] stockInfo;
		double finalScore = 0.00;
		
		try {
			stockInfo = stocks.YahooFinance.searchSymbol(symbol);
			stock = new Stock(stockInfo);
			finalScore = FinalScore.getScore(stock);
			stock.setScore(finalScore);
		} catch (MalformedURLException | InterruptedException e) {
			stock = null;
			e.printStackTrace();
		}

		return stock;
	}
}