package categoriesAndTicker;

import java.io.BufferedReader;
import java.io.FileInputStream;

public class Ticker {
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
	public static String stockTickerTickerStock(String name) {
		FileInputStream fs = null;
		BufferedReader reader = null;
		String stockDetail = null;
		for(int i=0; i<companies.length; i++){
			String line = companies[i];
			String[] tokens = line.split(",");

			// Store the Company Names in 1st column if StockName matches
			// 0th colum in the file
			if (name.equalsIgnoreCase(tokens[1])) {
				stockDetail = new String(tokens[0]);
				break;
			} else {
				stockDetail = name;
			}
		}
		return stockDetail;
	}
}
