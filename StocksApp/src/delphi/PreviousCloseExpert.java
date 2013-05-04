
package delphi;

import java.math.BigDecimal;

import stocks.Stock;
/**
 * @Author: Team 3+4
 * @Description: This class calculates the Percentage Change of previous closing of the given stock.
 * 
 */
public class PreviousCloseExpert {

	/**
	 * Accepts the current price, previous closing price, and the previous
	 * previous closing price
	 * 
	 * @param cp
	 *            - Current price
	 * @param pp1
	 *            - Previous closing price
	 * @param pp2
	 *            - Previous previous closing price
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
		percentChange = (cp.doubleValue() - pp.doubleValue())
				/ (pp.doubleValue());

		score = percentChange * 100.00;

		if (score >  10) { score =  10; }
		if (score < -10) { score = -10; }

		return score;
	}
}