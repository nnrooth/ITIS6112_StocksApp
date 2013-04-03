package debug;

import java.util.Random;
import java.util.Scanner;

import delphi.Top10;
import stocks.Stock;
import stocks.YahooFinance;

public class Test_DelphiLogic {

	public static void main(String[] args) {
		System.out.printf("[+] Search by Symbol\n\n");
		
		Stock stock; String stockSymbol;
		String[] stockInfo;
		Scanner scanIn = new Scanner(System.in);
		boolean search = true; String newSearch;
		
		long startTime, endTime, runTime;
		
		System.out.printf("[.] View Top/Bottom Stocks [y, N]: ");
		String viewTop = scanIn.nextLine();
		if (viewTop.toLowerCase().equals("y")) {
			String[] top10List = Top10.getTop10Regex();
			String[] bottom10List = Top10.getBottom10Regex();
			
			Random randy = new Random();
			int maxRandy = 250; // TODO determine randymax
			int viewCount = 10; // TODO change to 10 for presentation
			
			System.out.printf("[+] Top Stocks\tBottom Stocks\n");
			
			if (viewCount > top10List.length) {
				viewCount = top10List.length;
			} else if (viewCount > bottom10List.length){
				viewCount = bottom10List.length;
			}
			for (int n = 0; n < viewCount; n++) {
				try { Thread.sleep(randy.nextInt(maxRandy)); } catch (InterruptedException e) {} finally {
				System.out.printf("[%s]\t%s\t%s\n", (n + 1), top10List[n], bottom10List[n]); }
			}
		}
		
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
