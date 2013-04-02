package delphi;

import java.math.BigDecimal;

public class EstimatedGrowth {
	
	public static void main(String[] args) {
		BigDecimal[] epses = {BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.12), BigDecimal.valueOf(0.02)};
		System.out.printf("[+] Score: %s\n", getScore(BigDecimal.valueOf(10.00), epses));
	}
	
	/**
	 * Calculates the Estimate Growth Score based on EPSEstimates
	 * 
	 * @param currentPrice Price of the stock ATM
	 * @param epse Order : current year, next year, next quarter
	 * @return An average score based on all EPSEs
	 */
	public static int getScore(BigDecimal currentPrice, BigDecimal[] epse) {
		if (currentPrice == null || epse == null) {
			return 0;
		} else if (epse[0] == null || epse[1] == null || epse[2] == null) {
			return 0;
		}
		
		double egScore = 0.00, change;
		for (int n = 0; n < epse.length; n++) {
			if (epse[n] == null) { change = 0.00; }
			else {			
				change = epse[n].doubleValue() / currentPrice.doubleValue();
			}
			
			// 
			if (change >= .10) { egScore += 10; } else
			if (change >= .09) { egScore += 9; } else
			if (change >= .08) { egScore += 6; } else
			if (change >= .07) { egScore += 5; } else
			if (change >= .06) { egScore += 2; } else
			if (change >= .05) { egScore -= 1; } else
			if (change >= .04) { egScore -= 2; } else
			if (change >= .03) { egScore -= 4; } else
			if (change >= .02) { egScore -= 6; } else
			if (change >= .01) { egScore -= 8; } else
			if (change > .00) { egScore -= 10; }
			
		} return (int) Math.round(egScore / 3.00); // Rounding down, need to round up
	}
}