package delphi;

import java.math.BigDecimal;

import stocks.Stock;

public class EstimatedGrowth {
	
	/**
	 * Calculates the Estimate Growth Score based on EPSEstimates
	 * 
	 * @param currentPrice Price of the stock ATM
	 * @param epse Order : current year, next year, next quarter
	 * @return An average score based on all EPSEs
	 */
	private static BigDecimal getScore(BigDecimal currentPrice, BigDecimal[] epse) {
		double egScore = 0.00; double egScore2 = 0.00; double placeHolder = 0.00;
		for (int n = 0; n < epse.length; n++) {
			double change = epse[n].doubleValue() / currentPrice.doubleValue();
			
			// This is a placeholder. Need to evaluate scores according to the estimate period
			if (change >= .10) {
				egScore += 10;
			}
			else
			if (change >= .05) {
				egScore += 7.5;
			} else 
			if (change >= .02) {
				egScore += 5;
			} else
			if (change > 0) {
				egScore += 2;
			} else
			if (change < 0) {
				egScore -= 2;
			} else
			if (change <= .02) {
				egScore -= 5;
			} else
			if (change <= .05) {
				egScore -= 7.5;
			} else
			if (change <= .10) {
				egScore -= 10;
			}
			
			placeHolder = change * 100;
			if (placeHolder > 10) { placeHolder = 10; }
			
			if (placeHolder >= 5) { placeHolder -= 5; }
			else { placeHolder *= -1; }
			egScore2 += 2 * placeHolder;
		}
		
		System.out.printf("[+] Method 1: %s\n", (egScore / 3));
		System.out.printf("[+] Method 2: %s\n", (egScore2 / 3));
		
		return BigDecimal.valueOf(egScore / 3); // This is always returning positive... Nead to change scoring
		
		/*
		 * Ideas for new scoring method
		 * 
		 * 1. Calculate change
		 * 2. Multiply by 100
		 * 3. 10 is max, so scale back anything greater than
		 */
	}
}
