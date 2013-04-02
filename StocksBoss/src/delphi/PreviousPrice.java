package delphi;

import java.math.BigDecimal;

public class PreviousPrice {

	public static void main(String[] args) {
		int startPrice = 10, endPrice = 0;
		
		for (int n=startPrice; n >= endPrice; n--) {
			System.out.printf("[+] Score: %s\n", getScore(BigDecimal.valueOf(startPrice - n), BigDecimal.valueOf(startPrice)));
		}
		for (int n=endPrice; n <= startPrice; n++) {
			System.out.printf("[+] Score: %s\n", getScore(BigDecimal.valueOf(startPrice + n), BigDecimal.valueOf(startPrice)));
		}
	}
	
	/**
	 * Accepts the current price, previous closing price, and the previous previous closing price
	 * 
	 * @param cp - Current price
	 * @param pp1 - Previous closing price
	 * @param pp2 - Previous previous closing price
	 * @return Score calculated by change from previous two closing prices
	 */
	public static double getScore(BigDecimal cp, BigDecimal pp) {
		double percentChange, score = 0;
		
		if (cp == null || pp == null) {
			return 0;
		}
		
		// percent change from yesterday's closing price
		percentChange = (cp.doubleValue() - pp.doubleValue()) / (pp.doubleValue());
		
		if (percentChange >= 1) { score += 10; } else
		if (percentChange <= -1) { score -= 10; } else
		{ score = percentChange * 10.00; }
		
		return score;
	}
}