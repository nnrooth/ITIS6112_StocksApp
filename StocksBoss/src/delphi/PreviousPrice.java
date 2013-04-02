package delphi;

import java.math.BigDecimal;

public class PreviousPrice {

	public static void main(String[] args) {
		System.out.printf("[+] Score: %s\n", getScore(BigDecimal.valueOf(10.00), BigDecimal.valueOf(10.00), BigDecimal.valueOf(10.00)));
		System.out.printf("[+] Score: %s", getScore(BigDecimal.valueOf(10.00), BigDecimal.valueOf(11.00), BigDecimal.valueOf(10.50)));
	}
	
	/**
	 * Accepts the current price, previous closing price, and the previous previous closing price
	 * @param cp - Current price
	 * @param pp1 - Previous closing price
	 * @param pp2 - Previous previous closing price
	 * @return Score calculated by change from previous two closing prices
	 */
	public static int getScore(BigDecimal cp, BigDecimal pp1, BigDecimal pp2) {
		double percentChange1, percentChange2, score;
		int pp1Score = 0, pp2Score = 0, finalScore = 0;
		
		if (cp == null || pp1 == null /*|| pp2 == null*/) {
			return 0;
		}
		
		// percent change from yesterday's closing price
		percentChange1 = (cp.doubleValue() - pp1.doubleValue()) / (pp1.doubleValue());
		// percent change from the day before yersterday's closing price
		/*percentChange2 = (cp.doubleValue() - pp2.doubleValue()) / (pp2.doubleValue());*/
		
		if (percentChange1 >= .10) { pp1Score += 10; } else
		if (percentChange1 >= .05) { pp1Score += 7.5; } else 
		if (percentChange1 >= .02) { pp1Score += 5; } else
		if (percentChange1 >= 0) { pp1Score += 2; } else
		if (percentChange1 < 0) { pp1Score -= 2; } else
		if (percentChange1 <= -.02) { pp1Score -= 5; } else
		if (percentChange1 <= -.05) { pp1Score -= 7.5; } else
		if (percentChange1 <= -.10) { pp1Score -= 10; }
		
		/*if (percentChange2 >= .10) { pp2Score += 10; } else
		if (percentChange2 >= .05) { pp2Score += 7.5; } else 
		if (percentChange2 >= .02) { pp2Score += 5; } else
		if (percentChange2 >= 0) { pp2Score += 2; } else
		if (percentChange2 < 0) { pp2Score -= 2; } else
		if (percentChange2 <= -.02) { pp2Score -= 5; } else
		if (percentChange2 <= -.05) { pp2Score -= 7.5; } else
		if (percentChange2 <= -.10) { pp2Score -= 10; }*/
		
		System.out.printf("[+] pc1: %s", percentChange1);
		/*System.out.printf("[+] pc2: %s", percentChange2);*/
		
		/*score = ((pp1Score * .60) + (pp2Score * .40));*/
		score = pp1Score;
		finalScore = (int) Math.round(score);
		return finalScore;
	}
}