package debug;

import java.util.Scanner;
import stocks.Stock;

public class Test_StockPopulate {

	public static void main(String[] args) {
		System.out.printf("[+] Search by symbol test\n\n");
		
		Stock stock = new Stock();	String stockSymbol;  
		Scanner scanIn = new Scanner(System.in);
		boolean search = true; String newSearch;
		
		while(search) {
			System.out.printf("[.] Sybmol: ");
			stockSymbol = scanIn.nextLine();
			
			if (stock.getSymbol() != null) {
				System.out.printf("[+] Name: %s\n", stock.getName());
				System.out.printf("[+] Value: %s\n", stock.getCurrentPrice());
				System.out.printf("[+] P Close: %s\n", stock.getPreviousClosingPrice());
				System.out.printf("[+] EPSE CY: %s\n", stock.getEpseCYear());
				System.out.printf("[+] EPSE NY: %s\n", stock.getEpseNYear());
				System.out.printf("[+] EPSE NQ: %s\n", stock.getEpseNQuarter());
			} else {
				System.out.printf("[-] No Results\n");
			}
			
			System.out.printf("[.] New Search [y, N]: ");
			newSearch = scanIn.nextLine();
			
			if (!newSearch.toLowerCase().equals("y")) {
				search = false;
			}
		}
		
		scanIn.close();
	}

}