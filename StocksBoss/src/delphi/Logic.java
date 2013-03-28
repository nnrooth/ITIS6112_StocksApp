package delphi;

import stocks.Stock;

public class Logic {
	/* Delphi Prediciton Method Weights */
	private Double kWeight = .30;	// k - Article Keywords
	private Double egWeight = .50;	// eg - Estimate Growth or EPSEstimate
	private Double t10Weight = .10;	// t10 - Top 10 Lists
	private Double p2pWeight = .10;	// p2p - Past 2 Closing Prices
	/* These are not set and we should test different values */
	
	// Score variables. All scores are in range [-10:10]
	private Double kScore;	// Score from keyword occurrences in news articles
	private Double egScore;	// Score from EPSEstimate from CurrentYear, NextYear, & NextQuarter
	private Double t10Score;// Score from inclusion in from top 10 lists
	private Double p2pScore;// Score from past 2 prices
	
	private Double delphiScore;	// The final score
	// (k * kW) + (eg * egW) + (t10 * t10W) + (p2p * p2pW) \\
	
	/**
	 * Load values into the Logic Machine!!!
	 * 
	 * @param stock Stock to score
	 */
	public void populate(Stock stock) {
		this.kScore = .0;
		this.t10Score = .0;
		this.p2pScore = .0;
		
		Double currentPrice = stock.getCurrentPrice();
		Double[] epse = new Double[3];
		epse[0] = stock.getEpseCYear();
		epse[1] = stock.getEpseNYear();
		epse[2] = stock.getEpseNQuarter();
		this.egScore = getEgScore(currentPrice, epse);
		epse = null;
	}
	
	private Double getEgScore(Double currentPrice, Double[] epse) {
		
		
		return 0.00;
	}
	
}
