package stocks;

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
	
	private Double currentPrice; // Most up to date price retrieved
	private Double previousClosingPrice; // Value at the most recent closing of the market
	
	private Double epseCYear; // Earnings per share estimate for current year
	private Double epseNYear; // Earnings per share estimate for next year
	private Double epseNQuarter; // Earnings per share estimate for next quarter
	
	private int delphiRating; // Value derived from Delphi calculation. Range [-10:10]
	
	/**
	 * Default Constructor
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
	}
	
	/**
	 * Updates a stock object with results from a query to yahoo finance based on symbol parameter
	 * 
	 * @param symbol 1 - 4 character long alphanumeric string
	 */
	public void populate(String symbol) {
		String[] info = YahooFinance.searchSymbol(symbol);
		if (info != null) {
			int n = 0;
			setName(info[n++]);
			setSymbol(info[n++]);
			setExchange(info[n++]);
			setCurrentPrice(info[n++]);
			setPreviousClosingPrice(info[n++]);
			setEpseCYear(info[n++]);
			setEpseNYear(info[n++]);
			setEpseNQuarter(info[n++]);
		} else {
			this.name = null;
			this.symbol = null;
			this.exchange = null;
			this.currentPrice = null;
			this.previousClosingPrice = null;
			this.epseCYear = null;
			this.epseNYear = null;
			this.epseNQuarter = null;
		}
		
		setDelphiRating(3); // Placeholder until Delphi Logic is implemented
	}
	
	/**
	 * Updates volatile stock information
	 * 
	 * Currently only changes the current price
	 */
	public void update() {
		String[] info = YahooFinance.searchSymbol(symbol, "l1");
		if (info != null) {
			setCurrentPrice(info[0]);
		}
	}
	
	// Begin block of get/set methods
	public String getName() { return name; }
	private void setName(String name) {	this.name = name; }
	
	public String getSymbol() { return symbol; }
	private void setSymbol(String symbol) {	this.symbol = symbol; }
	
	public String getExchange() { return exchange; }
	private void setExchange(String exchange) { this.exchange = exchange; }
	
	public Double getCurrentPrice() { return currentPrice; }
	private void setCurrentPrice(String currentPrice) { this.currentPrice = Double.parseDouble(currentPrice); }
	
	public Double getPreviousClosingPrice() { return previousClosingPrice; }
	private void setPreviousClosingPrice(String previousClosingPrice) { this.previousClosingPrice = Double.parseDouble(previousClosingPrice); }
	
	public int getDelphiRating() { return delphiRating; }
	private void setDelphiRating(int delphiRating) { this.delphiRating = delphiRating; }
	
	public Double getEpseCYear() { return epseCYear; }
	private void setEpseCYear(String epseCYear) { this.epseCYear = Double.parseDouble(epseCYear); }
	
	public Double getEpseNYear() { return epseNYear; }
	private void setEpseNYear(String epseNYear) { this.epseNYear = Double.parseDouble(epseNYear); }
	
	public Double getEpseNQuarter() { return epseNQuarter; }
	private void setEpseNQuarter(String epseNQuarter) { this.epseNQuarter = Double.parseDouble(epseNQuarter); }
	// End block of get/set methods
}