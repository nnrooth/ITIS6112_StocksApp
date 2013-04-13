package utils;

import java.math.BigDecimal;

import delphi.ClosingPrice;
import delphi.EstimatedGrowth;
import delphi.FinalGrade;
import delphi.KeyWordExpert;
import delphi.KeywordMatching;
import delphi.Top10;
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

		KeyWordExpert s = new KeyWordExpert();
		KeywordMatching km = new KeywordMatching();
		double kScore = 0.0;
		kScore = km.keyScore(s.getMatchingKeywords(s.getNews(stock.getSymbol())));
		
		// getting closing price score
		double pcScore = 0.0;
		pcScore = ClosingPrice.closingPriceScore(stock.getCurrentPrice().doubleValue(), stock.getPreviousClosingPrice().doubleValue());// 50(yesterday) and 100(dayBeforeYesterday) are test inputs
		/*int pcScore = PreviousPrice.getScore(stock.getCurrentPrice(), stock.getPreviousClosingPrice(), stock.getPriorPreviousClosingPrice());*/
						
		double tbScore = Top10.getScore(stock.getSymbol());
		BigDecimal[] epses = {stock.getEpseCYear(), stock.getEpseNYear(), stock.getEpseNQuarter()};
		
		double egScore = EstimatedGrowth.getScore(stock.getCurrentPrice(), epses);
		
		// Calling the method to calculate the final grade.
		FinalGrade fg = new FinalGrade();
		double finalScore = 0.0;
		
		finalScore = fg.score(kScore, pcScore, tbScore, egScore);
		stock.setScore((int) Math.round(finalScore));
		
		return stock;
	}
}