package debug;

import java.util.Scanner;

import stocks.Stock;
import stocks.YahooFinance;

public class Test_DelphiLogic {

	public static void main(String[] args) {
		System.out.printf("[+] Search by Symbol\n\n");
		
		Stock stock; String stockSymbol;
		String[] stockInfo;
		Scanner scanIn = new Scanner(System.in);
		boolean search = true;
		
		long startTime, endTime, runTime;
		
		while(search) {
			System.out.printf("\n[.] Sybmol: ");
			stockSymbol = scanIn.nextLine();
			
			startTime = System.currentTimeMillis();
			stockInfo = YahooFinance.searchSymbol(stockSymbol);
			if (stockInfo != null) {
				stock = new Stock(stockInfo);
				stock.setScore(delphi.FinalScore.getScore(stock));
			} else {
				stock = new Stock();
			}
			
			if (stock.getSymbol() != null) {
				System.out.printf("[+] KW Score: %s\n", stock.getKwScore());
				System.out.printf("[+] T10 Score: %s\n", stock.getT10Score());
				System.out.printf("[+] EG Score: %s\n", stock.getEgScore());
				System.out.printf("[+] PC Score: %s\n", stock.getPcScore());
				
				System.out.printf("[+] Final Score: %s\n", stock.getScore());
				System.out.printf("[+] Current Price: %s\n", stock.getCurrentPrice());
			} else {
				System.out.printf("[-] No Results\n");
			}
			
			endTime = System.currentTimeMillis();
			runTime = (endTime - startTime) / 1000;
			System.out.printf("[+] Completed in %s seconds\n", runTime);
			
		}
		
		scanIn.close();
	}
}
