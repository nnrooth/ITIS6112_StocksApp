package categoriesAndTicker;

import java.util.ArrayList;

public class Stock {
	static String[] categories = { "1,Energy", "2,Small Cap", "3,Mid Cap", "4,Power",
			"5,Finance", "6,Information Technology" };
	static String[] companies = { "DUK,Duke Energy Corp.,1,Energy",
			"ATO,Atmos Energy Corp.,1,Energy",
			"ENERQ,Energy Conversion Devices Inc.,1,Energy",
			"CHK,Chesapeake Energy Corp.,1,Energy",
			"SD,SandRidge Energy Inc.,1,Energy",
			"RVT,Royce Value Trust Inc.,2,Small Cap",
			"GRT,Glimcher Realty Trust,2,Small Cap",
			"JMP,JMP Group Inc.,2,Small Cap",
			"RMT,Royce Micro-Cap Trust Inc.,2,Small Cap",
			"DEJ,Dejour Enterprises Ltd.,2,Small Cap",
			"MDC,MDC Holdings,3,Mid Cap", "CYT,Cytec,3,Mid Cap",
			"CEB,Corp. Ex Board,3,Mid Cap", "WGL,WGL Holdings,3,Mid Cap",
			"HSH,Hilshire,3,Mid Cap", "PWER,Power One Inc.,4,Power",
			"DBA,PowerShares DB Agriculture Fund,4,Power",
			"STP,Suntech Power Holdings Co. Ltd.,4,Power",
			"AEP,American Electric Power Company Inc.,4,Power",
			"PLUG,Plug Power Inc.,4,Power",
			"FSC,Fifth Street Finance Corp.,5,Finance",
			"NRF,Northstar Realty Finance Corp.,5,Finance",
			"SFL,Ship Finance International Limited,5,Finance",
			"HIG,Hartford Financial Services Group Inc.,5,Finance",
			"WBS,Webster Financial Corporation,5,Finance",
			"MSFT,Microsoft Corporation,6,Information Technology",
			"AAPL,Apple Inc.,6,Information Technology",
			"GOOG,Google Inc.,6,Information Technology",
			"YHOO,Yahoo! Inc.,6,Information Technology",
			"IBM,International Business Machines Corp.,6,Information Technology" };

	public static String[] getCategories() {
		return categories;
	}

	// Function to get the Stocks for a particular catgegory
	// This pools the file Stocks.txt to list the present stocks in a particular
	// category
	public static String[] getStocksForCategories(String categoryName) {
		ArrayList<String> stocksList = new ArrayList<String>();
		for(int i=0; i<companies.length; i++){
			String line = companies[i];
			String[] tokens = line.split(",");

			// Store the Company Names in 1st column if categoryName matches
			// 3rd column in the file
			if (categoryName.equalsIgnoreCase(tokens[3])) {
				stocksList.add(line);
			}
		}
		String[] stocks = new String[stocksList.size()];
		int i=0;
		for(String line: stocksList){
			stocks[i] = line;
			i++;
		}
		return stocks;
	}
}
