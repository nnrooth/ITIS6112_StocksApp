package delphi;

public class ClosingPrice {
	
	public static double closingPriceScore(double today, double yesterday){
		return (today * 10.0) / yesterday;
	}
	
}
