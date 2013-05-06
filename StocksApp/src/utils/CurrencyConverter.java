package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;

import android.util.Log;

/**
 * Used to fetch the currency conversion rates.
 * 
 * @author Team 3+4
 * 
 */

public class CurrencyConverter {
	
	private static final String TAG = "CurrencyConverter";
	
	private static String currencyCode = "USD";
	private static BigDecimal rate = new BigDecimal(1.00);
	private static String symbol = "$";
	
	/**
	 * @Name getCurrentRate()
	 * @Input String
	 * @return type BigDecimal
	 * @description Returns currency conversion rate between the USD and the input.
	 */
	public static BigDecimal getCurrentRate(String code) {
		updateCurrency(code);
		return rate;
	}
	
	/**
	 * @Name getSymbol()
	 * @Input None
	 * @return type String
	 * @description Returns the symbol of the currency.
	 */
	public static String getSymbol() {
		return symbol;
	}
	
	/**
	 * @Name updateCurrency()
	 * @Input String
	 * @return type None
	 * @description updates the currency conversion rate based on the input currency code.
	 */
	public static void updateCurrency(String code) {
		if (currencyCode != code) {
			try {			
				URL url = new URL(
						"http://currencies.apps.grandtrunk.net/getrate/2013-05-01/usd/"
								+ code);
				
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String answer = in.readLine();
				in.close();
				rate = new BigDecimal(answer);
				
				currencyCode = code;
				
				if (code.equals("USD")) {
					symbol = "$";
				} else if (code.equals("GBP")) {
					symbol = "£";
				} else if (code.equals("EUR")) {
					symbol = "€";
				} else if (code.equals("INR")) {
					symbol = "र";
				}
			} catch (Exception e) {
				Log.e(TAG, "Error", e);
			}
		}
	}
}
