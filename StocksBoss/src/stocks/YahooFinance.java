package stocks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class performs all data retrieval from YahooFinance
 * 
 * @author NNRooth
 *
 */
public class YahooFinance {

	private static String queryBaseUrl = "http://finance.yahoo.com/d/quote?";
	private static String queryParamsDefault = "nsxl1pe7e8e9";
	
	public static String[] searchSymbol(String queryValue) {
		return searchSymbol(queryValue, queryParamsDefault);
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * *
	 *             Relevant QueryParams              *
	 * +-------------------------------------------+ *
	 * | n - name                                  | *
	 * | s - symbol                                | *
	 * | x - stock exchange                        | *
	 * | l1 - last trade price                     | *
	 * | p - previous close price                  | *
	 * | e7 - EPSE Current Year                    | *
	 * | e8 - EPSE Next Year                       | *
	 * | e9 - EPSE Next Quarter                    | *
	 * | j - 52-week low                           | *
	 * | k - 52-week high                          | *
	 * | j6 - % change from 52-week low            | *
	 * | k5 - % change from 52-week high           | *
	 * | m3 - 50-day moving average                | *
	 * | m4 - 200-day moving average               | *
	 * | m6 - % change from 200-day moving average | *
	 * | m8 - % change from 50-day moving average  | *
	 * | t8 - 1 year target price                  | *
	 * | w - 52-week range [low-high]              | *
	 * +-------------------------------------------+ *
	 * * * * * * * * * * * * * * * * * * * * * * * * */
	public static String[] searchSymbol(String queryValue, String queryParams) {
		HttpURLConnection httpConnect = null;
		URL queryUrl = null;
		BufferedReader buffy = null; // lulzy reference to buffy the vampire slayer
		String line = null;
		StringBuilder builder = null;
		String[] stockInfo = null;
		
		// Use regex to validate query
		if (!queryValue.matches("[a-zA-Z]{1,4}")) {
			return stockInfo;
		}
		
		String querySValue = "s=" + queryValue;
		String queryFValue = "f=" + queryParams;
		
		try {
			String queryFullUrl = queryBaseUrl + querySValue + "&" + queryFValue;
			
			queryUrl = new URL(queryFullUrl); // Create a new url object
			httpConnect = (HttpURLConnection) queryUrl.openConnection(); // Create a new connection object
			httpConnect.setRequestMethod("GET"); // Send query using url encoding
			httpConnect.setDoOutput(true); // Allow reading http response
			httpConnect.setReadTimeout(2500); // Set response timeout at 2.5 seconds
			httpConnect.connect(); // Open connection with server
		
			// Create a BufferedReader from the InputStream for the server response
			buffy = new BufferedReader(
				new InputStreamReader(
					httpConnect.getInputStream()
			));
			
			// Read the response into a StringBuilder object
			builder = new StringBuilder();
			while ((line = buffy.readLine()) != null) {
				builder.append(line + "\n");
			}
			
			// Clean and parse the resulting string into a String array
			if (builder.length() > 0) {
				String xmlText = builder.toString();

				if (!xmlText.contains("N/A")) {
					// Does not properly split when company name has a comma (e.g. csco)
					stockInfo = xmlText.replace("\"", "").split(",");
				}
			}
			
		} catch (Exception e) {
			/* Need to implement proper error try catches */
		}

		// Close connection and return the String array of stock info
		httpConnect.disconnect(); queryUrl = null; buffy = null; builder = null;
		return stockInfo;
	}

}
