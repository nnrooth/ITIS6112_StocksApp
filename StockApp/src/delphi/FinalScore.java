package delphi;

import java.math.BigDecimal;

import stocks.Stock;

public class FinalScore {
	private static final double KW_WEIGHT = 0.30;
	private static final double T10_WEIGHT = 0.10;
	private static final double EG_WEIGHT = 0.50;
	private static final double PC_WEIGHT = 0.10;
	
	public static BigDecimal getScore(Stock stock) {
		String symbol = stock.getSymbol();
		
		double 	kwScore = 0, t10Score = 0, 
				egScore = 0, pcScore = 0;
		
		double finalScore = 0;
		
		KeyWordExpert kwExpert = new KeyWordExpert();
		kwScore = kwExpert.getScore(symbol);
		stock.setKwScore(kwScore);
		
		t10Score = Top10Expert.getScore(stock.getSymbol());
		stock.setT10Score(t10Score);
		
		egScore = EstimatedGrowthExpert.getScore(stock);
		stock.setEgScore(egScore);
		
		pcScore = PreviousPriceExpert.getScore(stock);
		stock.setPcScore(pcScore);
		
		finalScore = Math.round(
					(((kwScore * KW_WEIGHT) + (t10Score * T10_WEIGHT) +
					(egScore * EG_WEIGHT) + (pcScore * PC_WEIGHT))));
		
		return BigDecimal.valueOf(finalScore);
	}
}
