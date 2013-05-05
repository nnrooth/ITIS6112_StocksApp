package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

public class CurrencyConverter {
	public static BigDecimal getCurrentRate(String code) {
		BigDecimal rate=null;
		try {
			URL convert = new URL(
					"http://currencies.apps.grandtrunk.net/getrate/2013-05-01/usd/"
							+ code);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					convert.openStream()));
			String answer = in.readLine();
			in.close();
			rate = new BigDecimal(answer);
			System.out.println(rate);			
		} catch (MalformedURLException mue) {
			System.exit(1);
		} catch (IOException ioe) {
			System.exit(1);
		}
		return rate;
	}
}
