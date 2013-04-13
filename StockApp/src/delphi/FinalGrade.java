package delphi;


import java.math.BigDecimal;
import java.util.Scanner;

import stocks.Stock;

public class FinalGrade {

	public double score(double kScore, double pcScore, double tbScore, double estimatedGrowthScore) {
		double score = 0.0;
		
		// Percentage distribution
		double keyPercent = 0.3;
		double pastClosingPercent = 0.1;
		double topBottomPercent = 0.1;
		double estimatedGrowthPercent = 0.5;

		// Checking if any keywords have matched
		if (kScore != 0.0) {
			if (pcScore != 100.0) {
				if (tbScore != 0.0) {
					if (estimatedGrowthScore != 0.0) {
						// Have received all inputs
						score = (keyPercent * kScore)
								+ (pastClosingPercent * pcScore)
								+ (topBottomPercent * tbScore)
								+ (estimatedGrowthPercent * estimatedGrowthScore);

					}
					else{
						score = (keyPercent * kScore)
								+ (pastClosingPercent * pcScore)
								+ (topBottomPercent * tbScore)
								+ (estimatedGrowthPercent * estimatedGrowthScore);
					}
				}
				else{ // In case the company is not listed in the Top or Bottom List
					score = (keyPercent * kScore)
							+ (pastClosingPercent * pcScore)
							+ (topBottomPercent * tbScore)
							+ (estimatedGrowthPercent * estimatedGrowthScore);
					
				}
			}
			else{/* Percentage Change Score is 0.0 */}

		} else{/* No keywords have matched.	*/}

		return score;
	}

	public static void main(String[] args) {
		// TODO Remove method
		System.out.printf("[+] Search by symbol test\n\n");
		
		Stock stock;	String stockSymbol;
		Scanner scanIn = new Scanner(System.in);
		
		System.out.printf("[.] Sybmol: ");
		stockSymbol = scanIn.nextLine();
		
		String[] stockInfo = stocks.YahooFinance.searchSymbol(stockSymbol);
		stock = new Stock(stockInfo);
			
		if (stock.getSymbol() != null) {
			System.out.printf("[+] Name: %s\n", stock.getName());
			System.out.printf("[+] Value: %s\n", stock.getCurrentPrice());
		} else {
			System.out.printf("[-] No Results\n");
		}
			
		scanIn.close();


		// Getting the key matching score
		KeyWordExpert s = new KeyWordExpert();
		
		KeywordMatching km = new KeywordMatching();
		double kScore = 0.0;
		kScore = km.keyScore(s.getMatchingKeywords(s.getNews(stock.getSymbol())));
		System.out.printf("[+] K Score: %s\n", kScore);
		
		// getting closing price score
		ClosingPrice cp = new ClosingPrice();
		double pcScore = 0.0;
		pcScore = cp.closingPriceScore(stock.getCurrentPrice().doubleValue(), stock.getPreviousClosingPrice().doubleValue());// 50(yesterday) and 100(dayBeforeYesterday) are test inputs
		/*int pcScore = PreviousPrice.getScore(stock.getCurrentPrice(), stock.getPreviousClosingPrice(), stock.getPriorPreviousClosingPrice());*/
		System.out.printf("[+] PC Score: %s\n", pcScore);
						
		double tbScore = Top10.getScore(stock.getSymbol());
		System.out.printf("[+] TB Score: %s\n", tbScore);
		BigDecimal[] epses = {stock.getEpseCYear(), stock.getEpseNYear(), stock.getEpseNQuarter()};
		
		double egScore = EstimatedGrowth.getScore(stock.getCurrentPrice(), epses);
		System.out.printf("[+] EG Score: %s\n", egScore);
		
		// Calling the method to calculate the final grade.
		FinalGrade fg = new FinalGrade();
		double finalScore = 0.0;
		
		finalScore = fg.score(kScore, pcScore, tbScore, egScore);
		System.out.println("Final Score: " + finalScore);
	}
}
