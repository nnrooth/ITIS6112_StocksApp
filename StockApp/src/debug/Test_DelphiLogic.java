package debug;

import java.util.Scanner;
import stocks.Stock;
import stocks.YahooFinance;
import utils.Controller;

public class Test_DelphiLogic {

	public static void main(String[] args) {
		System.out.printf("[+] Search by symbol test\n\n");
		
		Stock stock; String stockSymbol;
		String[] stockInfo;
		Scanner scanIn = new Scanner(System.in);
		boolean search = true; String newSearch;
		
		long startTime, endTime, runTime;
		
		while(search) {
			System.out.printf("[.] Sybmol: ");
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
				System.out.printf("[+] Score: %s\n", stock.getScore());
				System.out.printf("[+] Current Price: %s\n", stock.getCurrentPrice());
			} else {
				System.out.printf("[-] No Results\n");
			}
			
			endTime = System.currentTimeMillis();
			runTime = (endTime - startTime) / 1000;
			System.out.printf("[+] Completed in %s seconds\n", runTime);
			
			System.out.printf("[.] New Search [y, N]: ");
			newSearch = scanIn.nextLine();
			
			if (!newSearch.toLowerCase().equals("y")) {
				search = false;
			}
		}
		
		scanIn.close();
	}
}
