package stocks;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import utils.WebData;

/**
 * This class performs all data retrieval from YahooFinance's "hidden" API
 * 
 * @author Team 3+4
 * 
 */
public class YahooFinance {

	private static String queryBaseUrl = "http://finance.yahoo.com/d/quote?";
	private static String queryParamsDefault = "sxl1pe7e8e9ry";

	public static String[] searchSymbol(String queryValue)
			throws MalformedURLException, InterruptedException {
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
	 * | y - dividend yield                        | *
	 * | r - p/e ratio                             | *
	 * +-------------------------------------------+ *
	 * * * * * * * * * * * * * * * * * * * * * * * * */
	public static String[] searchSymbol(String queryValue, String queryParams)
			throws MalformedURLException, InterruptedException {
		URL queryUrl = null, queryUrl2 = null;
		String xmlText;
		String[] splitText;
		String companyName;
		ArrayList<String> stockInfo = null;
		String[] info = null;

		// Use regex to validate query
		if (!queryValue.matches("[a-zA-Z]{1,4}")) {
			return null;
		}

		String querySValue = "s=" + queryValue;
		String queryFValue = "f=" + queryParams;
		String queryFValue2 = "f=" + "n"; // Query for company name

		int timeout = 1500;

		queryUrl = new URL(queryBaseUrl + querySValue + "&" + queryFValue);
		queryUrl2 = new URL(queryBaseUrl + querySValue + "&" + queryFValue2);
		WebData web1 = new WebData(queryUrl);
		WebData web2 = new WebData(queryUrl2);

		Thread[] threads = new Thread[2];
		threads[0] = new Thread(web1);
		threads[1] = new Thread(web2);

		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			thread.join(timeout);
		}

		xmlText = web1.getResponse().replace("\"", "").trim();
		companyName = web2.getResponse().replace("\"", "").trim();
		splitText = xmlText.split(",");
		stockInfo = new ArrayList<String>();
		stockInfo.add(companyName);
		for (int n = 0; n < splitText.length; n++) {
			stockInfo.add(splitText[n].trim());
		}

		info = new String[stockInfo.size()];
		info = stockInfo.toArray(info);

		for (int n = 0; n < info.length; n++) {
			if (info[n].equals("N/A")) {
				info[n] = "0.00";
			}
		}

		return info;
	}

	/**
	 * Gets companies name based on queryValue parameter
	 * 
	 * @param queryValue
	 * @return
	 */
	public static String getCompanyName(String symbol) {
		String companyName = null;
		String querySValue = "s=" + symbol;
		String queryFValue = "f=" + "n"; // Query for company name
		WebData web = null;
		Thread thread = null;
		try {
			URL queryUrl = new URL(queryBaseUrl + querySValue + "&"
					+ queryFValue);
			web = new WebData(queryUrl);
			thread = new Thread(web);
			thread.start();
			companyName = web.getResponse().replace("\"", "").trim();
		} catch (MalformedURLException e) {
			companyName = "";
		}

		return companyName;
	}
}