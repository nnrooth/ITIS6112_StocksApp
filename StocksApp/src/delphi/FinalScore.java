package delphi;

import stocks.Stock;

public class FinalScore {
	private static final double KW_WEIGHT = 0.50;
	private static final double T10_WEIGHT = 0.10;
	private static final double EG_WEIGHT = 0.30;
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
		
		finalScore = round(
					(((kwScore * KW_WEIGHT) + (t10Score * T10_WEIGHT) +
					(egScore * EG_WEIGHT) + (pcScore * PC_WEIGHT))));
		
		return finalScore;
	}

	private static double round(double a) {
		double b = 0.00;
		
		if (a < 0.00) { b = Math.floor(a); }
		if (a > 0.00) { b = Math.ceil(a); }
		
		return b;
	}
}
