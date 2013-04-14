package delphi;

import java.math.BigDecimal;

import stocks.Stock;

public class PreviousPriceExpert {
	
	/**
	 * Accepts the current price, previous closing price, and the previous previous closing price
	 * 
	 * @param cp - Current price
	 * @param pp1 - Previous closing price
	 * @param pp2 - Previous previous closing price
	 * @return Score calculated by change from previous two closing prices
	 */
	public static double getScore(Stock stock) {
		double percentChange, score = 0;
		BigDecimal cp = stock.getCurrentPrice();
		BigDecimal pp = stock.getPreviousClosingPrice();
		
		if (cp == null || pp == null) {
			return 0.00;
		}
		
		// percent change from yesterday's closing price
		percentChange = (cp.doubleValue() - pp.doubleValue()) / (pp.doubleValue());
		
		if (percentChange >= 1) { score += 10; } else
		if (percentChange <= -1) { score -= 10; } else
		{ score = percentChange * 1000.00; }
		
		return score;
	}
}