package utils;

import java.math.BigDecimal;
import java.net.URL;

public class CurrencyConverter {
	
	private static String currencyCode = "USD";
	private static BigDecimal rate = new BigDecimal(1.00);
	private static String symbol = "$";
	
	public static BigDecimal getCurrentRate(String code) {
		updateCurrency(code);
		return rate;
	}
	
	public static String getSymbol() {
		return symbol;
	}
	
	public static void updateCurrency(String code) {
		if (currencyCode != code) {
			String response;
			try {			
				URL url = new URL(
						"http://currencies.apps.grandtrunk.net/getrate/2013-05-01/usd/"
								+ code);
				
				WebData data = new WebData(url);
				response = data.makeRequest();
				rate = new BigDecimal(response);
				
				currencyCode = code;
				
				if (code == "USD") {
					symbol = "$";
				} else if (code == "GBP") {
					symbol = "£";
				} else if (code == "EUR") {
					symbol = "€";
				} else if (code == "INR") {
					symbol = "र";
				}
			} catch (Exception e) {}
		}
	}
}
