package stocks;

import java.math.BigInteger;

/**
 * This class represents a stock object.
 * Stock attributes:
 * 	name, symbol, exchange, currentPrice, previousClosingPrice,
 * 	epseCYear, epseNYear, epseNQuarter, delphiRating
 * 
 * @author NNRooth
 *
 */
public class Stock {
	
	private String name; // Company's full name
	private String symbol; // Stock ticker symbol
	private String exchange; // Stock exchange
	
	private BigInteger currentPrice; // Most up to date price retrieved
	private BigInteger previousClosingPrice; // Value at the most recent closing of the market
	
	private BigInteger epseCYear; // Earnings per share estimate for current year
	private BigInteger epseNYear; // Earnings per share estimate for next year
	private BigInteger epseNQuarter; // Earnings per share estimate for next quarter
	
	private int delphiRating; // Value derived from Delphi calculation. Range [-10:10]
	
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
		delphiRating = 0;
	}
	
	// Begin block of get/set methods
	public String getName() { return name; }
	public void setName(String name) {	this.name = name; }
	
	public String getSymbol() { return symbol; }
	public void setSymbol(String symbol) {	this.symbol = symbol; }
	
	public String getExchange() { return exchange; }
	public void setExchange(String exchange) { this.exchange = exchange; }
	
	public BigInteger getCurrentPrice() { return currentPrice; }
	public void setCurrentPrice(BigInteger currentPrice) { this.currentPrice = currentPrice; }
	
	public BigInteger getPreviousClosingPrice() { return previousClosingPrice; }
	public void setPreviousClosingPrice(BigInteger previousClosingPrice) { this.previousClosingPrice = previousClosingPrice; }
	
	public int getDelphiRating() { return delphiRating; }
	public void setDelphiRating(int delphiRating) { this.delphiRating = delphiRating; }
	
	public BigInteger getEpseCYear() { return epseCYear; }
	public void setEpseCYear(BigInteger epseCYear) { this.epseCYear = epseCYear; }
	
	public BigInteger getEpseNYear() { return epseNYear; }
	public void setEpseNYear(BigInteger epseNYear) { this.epseNYear = epseNYear; }
	
	public BigInteger getEpseNQuarter() { return epseNQuarter; }
	public void setEpseNQuarter(BigInteger epseNQuarter) { this.epseNQuarter = epseNQuarter; }
	// End block of get/set methods
}