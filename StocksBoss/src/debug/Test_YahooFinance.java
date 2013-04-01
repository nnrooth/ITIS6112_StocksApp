package debug;

import java.util.Scanner;
import stocks.YahooFinance;

public class Test_YahooFinance {

	/**
	 * Symbol to name and vice versa
	 * 
	 */
	public static void main(String[] args) {
		System.out.printf("[+] Search by symbol test\n\n");
		
		String stockSymbol; String[] stockInfo;
		Scanner scanIn = new Scanner(System.in);
		boolean search = true; String newSearch;
		
		while(search) {
			System.out.printf("[.] Sybmol: ");
			stockSymbol = scanIn.nextLine();
			
			stockInfo = YahooFinance.searchSymbol(stockSymbol);
			
			if (stockInfo != null) {
				for (int n = 0; n < stockInfo.length; n++) {
					System.out.printf("[%s] %s\n", n, stockInfo[n]);
				}
			} else {
				System.out.printf("[-] No Results\n");
			}
			
			System.out.printf("[.] New Search [y, N]: ");
			newSearch = scanIn.nextLine();
			
			if (!newSearch.toLowerCase().equals("y")) {
				search = false;
			}
			
		} scanIn.close();
	
	}

}