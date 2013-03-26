package core;

public class Stock {
	
	private String name; // Company's full name
	private String symbol; // Stock ticker symbol
	
	private Double currentPrice; // Most up to date price retrieved
	private Double previousClosingPrice; // Value at the most recent closing of the market
	
	private int delphiRating; // Value derived from Delphi calculation. Range [-10:10]
	
	public Stock() {
		
	}
	
	public void populate() {
		setName("Microsoft Co.");
		setSymbol("MSFT");
		setCurrentPrice(139.21);
		setPreviousClosingPrice(121.89);
		setDelphiRating(3);
	}
	
	public void update() {
		
	}
	
	public String getName() { return name; }
	private void setName(String name) {	this.name = name; }
	
	public String getSymbol() { return symbol; }
	private void setSymbol(String symbol) {	this.symbol = symbol; }
	
	public Double getCurrentPrice() { return currentPrice; }
	private void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }
	
	public Double getPreviousClosingPrice() { return previousClosingPrice; }
	private void setPreviousClosingPrice(Double previousClosingPrice) { this.previousClosingPrice = previousClosingPrice; }
	
	public int getDelphiRating() { return delphiRating; }
	private void setDelphiRating(int delphiRating) { this.delphiRating = delphiRating; }
	
}