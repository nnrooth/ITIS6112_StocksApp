package stocks;

import java.math.BigDecimal;
import java.net.URL;

import utils.WebData;

/**
 * This class represents a stock object.
 * Stock attributes:
 * 	name, symbol, exchange, currentPrice, previousClosingPrice,
 * 	epseCYear, epseNYear, epseNQuarter, score, pastPrices
 * 
 * Note on the use of BigDecimal. Due to some peculiarities of
 * the double type, prices are handled by BigDecimal types
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
	
	private double peRatio; // P/E Ratio
	private double divYield; // Dividend Yield
	
	private double kwScore, t10Score, egScore, pcScore; // kw - Keyword, t10 - top/bottom 10, eg - Estimated Growth, pc - Previous Close
	private double score; // Value derived from Delphi calculation. Range [-10:10]
	
	private String[] pastPrices; // Added for larger range of past prices. ~25 stored
		
	/**
	 * Custom constructor. I use this to automatically distribute the
	 * results from a search using the YahooFinance API
	 * 
	 * @param stockInfo Parsed and sorted response from YahooFinance
	 */
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
		setPeRatio(Double.valueOf(stockInfo[count++]));
		setDivYield(Double.valueOf(stockInfo[count++]));
		setKwScore(0.00); setT10Score(0.00); setEgScore(0.00); setPcScore(0.00);
		setScore(0.00);
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
	
	public double getScore() { return score; }
	public void setScore(double score) { this.score = score; }
	
	public BigDecimal getEpseCYear() { return epseCYear; }
	public void setEpseCYear(BigDecimal epseCYear) { this.epseCYear = epseCYear; }
	
	public BigDecimal getEpseNYear() { return epseNYear; }
	public void setEpseNYear(BigDecimal epseNYear) { this.epseNYear = epseNYear; }
	
	public BigDecimal getEpseNQuarter() { return epseNQuarter; }
	public void setEpseNQuarter(BigDecimal epseNQuarter) { this.epseNQuarter = epseNQuarter; }
	
	public double getPeRatio() { return peRatio; }
	public void setPeRatio(double peRatio) { this.peRatio = peRatio; }
	
	public double getDivYield() { return divYield; }
	public void setDivYield(double divYield) { this.divYield = divYield; }
	
	public double getKwScore() { return kwScore; }
	public void setKwScore(double kwScore) { this.kwScore = kwScore; }

	public double getT10Score() { return t10Score; }
	public void setT10Score(double t10Score) { this.t10Score = t10Score; }

	public double getEgScore() { return egScore; }
	public void setEgScore(double egScore) { this.egScore = egScore; }

	public double getPcScore() { return pcScore; }
	public void setPcScore(double pcScore) { this.pcScore = pcScore; }

	public String[] getPastPrices() { return pastPrices; }
	public void setPastPrices(String[] pastPrices) { this.pastPrices = pastPrices; }
	
	// End block of get/set methods
	
	/**
	 * This method simply fetches the most recent price from YahooFinance,
	 * stores it, and returns it to the caller.
	 * 
	 * Keep in mind that this "updated" price is still on a 30min delay 
	 * 
	 * @return The updated price
	 */
	public BigDecimal updatePrice() {
		BigDecimal currentPrice = null; // BigDecimal for currency handling
		String queryBaseUrl = "http://finance.yahoo.com/d/quote?";
		String querySValue = "s=" + symbol;
		String queryFValue = "f=" + "l1"; // Query for company name
		WebData web = null;
		Thread thread = null;
		
		// Attempt to fetch an updated price
		try {
		URL queryUrl = new URL(queryBaseUrl + querySValue + "&" + queryFValue);
		web = new WebData(queryUrl);
		thread = new Thread(web);
		thread.start();
		currentPrice = BigDecimal.valueOf(Double.valueOf(web.getResponse().replace("\"",  "").trim()));
		setCurrentPrice(currentPrice);
		} catch (Exception e) {
			// An error... return the previously fetched price
			currentPrice = getCurrentPrice();
		}
		
		// Return "updated" price to the caller
		return currentPrice;
	}
}