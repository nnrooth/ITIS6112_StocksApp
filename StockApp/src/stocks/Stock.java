package stocks;

import java.math.BigDecimal;

/**
 * This class represents a stock object.
 * Stock attributes:
 * 	name, symbol, exchange, currentPrice, previousClosingPrice,
 * 	epseCYear, epseNYear, epseNQuarter, score
 * 
 * @author NNRooth
 *
 */
public class Stock {
	
	private String name; // Company's full name
	private String symbol; // Stock ticker symbol
	private String exchange; // Stock exchange
	
	private BigDecimal currentPrice; // Most up to date price retrieved
	private BigDecimal previousClosingPrice; // Value at the most recent closing of the market
	
	private BigDecimal epseCYear; // Earnings per share estimate for current year
	private BigDecimal epseNYear; // Earnings per share estimate for next year
	private BigDecimal epseNQuarter; // Earnings per share estimate for next quarter
	
	private double kwScore, t10Score, egScore, pcScore; // kw - Keyword, t10 - top/bottom 10, eg - Estimated Growth, pc - Previous Close
	private BigDecimal score; // Value derived from Delphi calculation. Range [-10:10]
		
	/**
	 * Null Constructor
	 */
	public Stock() {
		this.name = null;
		this.symbol = null;
		this.exchange = null;
		this.currentPrice = null;
		this.previousClosingPrice = null;
		this.epseCYear = null;
		this.epseNYear = null;
		this.epseNQuarter = null;
		this.kwScore = 0.00;
		this.t10Score = 0.00;
		this.egScore = 0.00;
		this.pcScore = 0.00;
		this.score = BigDecimal.valueOf(Double.valueOf(0));
	}
	
	public Stock(String[] stockInfo) {
		int count = 0;
		setName(stockInfo[count++]);
		setSymbol(stockInfo[count++]);
		setExchange(stockInfo[count++]);
		setCurrentPrice(BigDecimal.valueOf(Double.valueOf(stockInfo[count++])));
		setPreviousClosingPrice(BigDecimal.valueOf(Double.valueOf(stockInfo[count++])));
		setEpseCYear(BigDecimal.valueOf(Double.valueOf(stockInfo[count++])));
		setEpseNYear(BigDecimal.valueOf(Double.valueOf(stockInfo[count++])));
		setEpseNQuarter(BigDecimal.valueOf(Double.valueOf(stockInfo[count++])));
		setKwScore(0.00); setT10Score(0.00); setEgScore(0.00); setPcScore(0.00);
		setScore(BigDecimal.valueOf(Double.valueOf(0)));
	}
	
	// Begin block of get/set methods
	public String getName() { return name; }
	public void setName(String name) {	this.name = name; }
	
	public String getSymbol() { return symbol; }
	public void setSymbol(String symbol) {	this.symbol = symbol; }
	
	public String getExchange() { return exchange; }
	public void setExchange(String exchange) { this.exchange = exchange; }
	
	public BigDecimal getCurrentPrice() { return currentPrice; }
	public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
	
	public BigDecimal getPreviousClosingPrice() { return previousClosingPrice; }
	public void setPreviousClosingPrice(BigDecimal previousClosingPrice) { this.previousClosingPrice = previousClosingPrice; }
	
	public BigDecimal getScore() { return score; }
	public void setScore(BigDecimal score) { this.score = score; }
	
	public BigDecimal getEpseCYear() { return epseCYear; }
	public void setEpseCYear(BigDecimal epseCYear) { this.epseCYear = epseCYear; }
	
	public BigDecimal getEpseNYear() { return epseNYear; }
	public void setEpseNYear(BigDecimal epseNYear) { this.epseNYear = epseNYear; }
	
	public BigDecimal getEpseNQuarter() { return epseNQuarter; }
	public void setEpseNQuarter(BigDecimal epseNQuarter) { this.epseNQuarter = epseNQuarter; }
	
	public double getKwScore() { return kwScore; }
	public void setKwScore(double kwScore) { this.kwScore = kwScore; }

	public double getT10Score() { return t10Score; }
	public void setT10Score(double t10Score) { this.t10Score = t10Score; }

	public double getEgScore() { return egScore; }
	public void setEgScore(double egScore) { this.egScore = egScore; }

	public double getPcScore() { return pcScore; }
	public void setPcScore(double pcScore) { this.pcScore = pcScore; }
	// End block of get/set methods
}