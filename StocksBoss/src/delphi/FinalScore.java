package delphi;

import java.math.BigDecimal;
import java.util.ArrayList;

import stocks.Stock;

public class FinalScore {
	private static final double KW_WEIGHT = 0.30;
	private static final double T10_WEIGHT = 0.10;
	private static final double EG_WEIGHT = 0.50;
	private static final double PC_WEIGHT = 0.10;
	
	public static int getScore(Stock stock) {
		double kwScore = 0, t10Score = 0, egScore = 0,
			   pcScore = 0, pcScore1 = 0, pcScore2 = 0;
			   
		int finalScore = 0;
		
		KeyMatch s = new KeyMatch();
		ArrayList<String> abc = new ArrayList<String>();
		abc = s.getMatchingKeywords(s.getNews(stock.getSymbol()));
		String[] keyMatched = new String[abc.size()];
		keyMatched = abc.toArray(keyMatched);
		
		KeywordMatching km = new KeywordMatching();
		kwScore = km.keyScore(keyMatched);
		
		t10Score = Top10.getScore(stock.getSymbol());
		
		BigDecimal[] epses = {stock.getEpseCYear(), stock.getEpseNYear(), stock.getEpseNQuarter()};
		egScore = EstimatedGrowth.getScore(stock.getCurrentPrice(), epses);
		
		pcScore1 = ClosingPrice.closingPriceScore(stock.getCurrentPrice().doubleValue(), stock.getPreviousClosingPrice().doubleValue());
		pcScore2 = PreviousPrice.getScore(stock.getCurrentPrice(), stock.getPreviousClosingPrice());
		
		pcScore = (pcScore1 + pcScore2) / 2.00;
		
		finalScore = (int) Math.round(
					(kwScore * KW_WEIGHT) + (t10Score * T10_WEIGHT) +
					(egScore * EG_WEIGHT) + (pcScore * PC_WEIGHT));
		
		return finalScore;
	}
}
