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
	
	private int score; // Value derived from Delphi calculation. Range [-10:10]
	
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
		score = 0;
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
		setScore(0);
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
	
	public int getScore() { return score; }
	public void setScore(int score) { this.score = score; }
	
	public BigDecimal getEpseCYear() { return epseCYear; }
	public void setEpseCYear(BigDecimal epseCYear) { this.epseCYear = epseCYear; }
	
	public BigDecimal getEpseNYear() { return epseNYear; }
	public void setEpseNYear(BigDecimal epseNYear) { this.epseNYear = epseNYear; }
	
	public BigDecimal getEpseNQuarter() { return epseNQuarter; }
	public void setEpseNQuarter(BigDecimal epseNQuarter) { this.epseNQuarter = epseNQuarter; }
	// End block of get/set methods
}