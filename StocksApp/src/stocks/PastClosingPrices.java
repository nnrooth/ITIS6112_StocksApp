package stocks;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import utils.WebData;
/**
 * Fetches the previous closing prices for a specific stock
 * 
 * @author Team 3+4
 *
 */
public class PastClosingPrices {

	/**
	 * Returns an array of previous closing prices sorted most recent to least
	 * recent
	 * 
	 * @param symbol Stock ticker to fetch previous closing prices for
	 * @return An array of previous closing prices. First value is date, second price, etc
	 * @throws MalformedURLException Error thrown if URL give is invalid
	 * @throws InterruptedException Error thrown if fetch thread is interrupted prematurely
	 */
	public static String[] fetch(String symbol) 
			throws MalformedURLException, InterruptedException {
		
		String[] pastPrices = null;
		String response; long timeout = 2500;

		// This is the url for yahoo finances previous closing price search
		String queryUrl = String.format("http://finance.yahoo.com/q/hp?s=%s",
				symbol);
		URL url = new URL(queryUrl);

		WebData data = new WebData(url);
		Thread thread = new Thread(data);

		// Start the thread, and join its parent thread
		thread.start();
		thread.join(timeout);
		
		response = data.getResponse();

		List<String> listMatches = new ArrayList<String>();
		Document doc = Jsoup.parse(response);
		String parsedResponse = "";
		Elements values = doc.getElementsByClass("yfnc_tabledata1");
		
		/*
		 * Parse all past prices on first page of results.
		 * The amount of avaialble historical prices is rather large,
		 * but we only need the last 25 or so. 
		 */
		for (int n = 0; n < values.size() - 4; n += 7) {
			parsedResponse = values.get(n).toString();
			parsedResponse = parsedResponse.substring(
					parsedResponse.indexOf(">") + 1,
					parsedResponse.indexOf("</td>"));
			listMatches.add(parsedResponse);

			parsedResponse = values.get(n + 4).toString();
			parsedResponse = parsedResponse.substring(
					parsedResponse.indexOf(">") + 1,
					parsedResponse.indexOf("</td>"));
			listMatches.add(parsedResponse);
		}

		// Convert the List of Strings into an array
		pastPrices = listMatches.toArray(new String[listMatches.size()]);

		return pastPrices;
	}
}
