

package delphi;

import stocks.Stock;
/**
 * @Author: Team 3+4
 * @Description: This class is used to calculate the Final Grade based on all the different inputs gathered.
 * The Final Grade is calculated based on the Keyword Score, Estimated Growth Score, Top/Bottom 10 score,
 * and Percentage Change since the previous closing score. The differenct scores are then given different 
 * weightage and the Final Score is calculated.
 * 
 */
public class FinalScore {
	// Final weight adjustment
	private static final double KW_WEIGHT = 0.50;
	private static final double T10_WEIGHT = 0.50;
	private static final double EG_WEIGHT = 0.50;
	private static final double PC_WEIGHT = 0.10;

	public static double getScore(Stock stock) {
		String symbol = stock.getSymbol();
		
		double 	kwScore = 0, t10Score = 0, 
				egScore = 0, pcScore = 0;
		
		double finalScore = 0;

		kwScore = new KeyWordExpert().getScore(symbol);
		stock.setKwScore(round(kwScore));

		t10Score = Top10Expert.getScore(stock.getSymbol());
		stock.setT10Score(round(t10Score));

		egScore = EstimatedGrowthExpert.getScore(stock);
		stock.setEgScore(round(egScore));

		pcScore = PreviousCloseExpert.getScore(stock);
		stock.setPcScore(round(pcScore));
				
		finalScore = round(((
			(kwScore * KW_WEIGHT) + (t10Score * T10_WEIGHT) +
			(egScore * EG_WEIGHT) + (pcScore * PC_WEIGHT)
		)));
		
		if (finalScore > 10.0) { finalScore = 10.0; }
		
		else
			
		if (finalScore < -10.0) { finalScore = -10.0; }
		
		return finalScore;
	}

	/**
	 * This method provides a more aggressive rounding functionality
	 * than that provided by Math.round. 
	 * Negative values are rounded to the floor value
	 * (e.g. round(-0.01) = -1.0).
	 * Positive values are rounded to the ceiling value
	 * (e.g. round(0.01) = 1.0)
	 * 
	 * @param initialValue Value to be rounded
	 * @return The rounded value
	 */
	private static double round(double initialValue) {
		double roundedValue = 0.00;
		
		if (initialValue < 0.00) { roundedValue = Math.floor(initialValue); }
		if (initialValue > 0.00) { roundedValue = Math.ceil(initialValue); }
		
		return roundedValue;
	}
}