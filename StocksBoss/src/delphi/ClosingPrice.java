package delphi;

public class ClosingPrice {

	/**
	 * @param args
	 */
	// If percentage change is less than 100, then it depicts that the price has fallen.
	// If percentage change is more than 100, then it depicts that the price has increased.
	// If percentage change is 100, then it depicts that the price has not changed in the last 2 days.
	public double closingPriceScore(double yesterday, double dayBeforeYesterday){
		double score =0.0;
		score = (yesterday * 100) / dayBeforeYesterday;		
		return score;
		
		// Have to calculate the score based on the percentage change.
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ClosingPrice cp=new ClosingPrice();
		double percentChange = cp.closingPriceScore(50, 50);
		System.out.println("Percentage Change=:" + percentChange);
		
	}

}
