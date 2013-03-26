package debug;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Test_WebPull {

	/**
	 * Symbol to name and vice versa
	 * 
	 */
	public static void main(String[] args) {
		String stockSymbol = "goog";
		String yqlBaseUrl = "http://query.yahooapis.com/v1/public/yql";
		String yqlQuery = "SELECT * FROM yahoo.finance.quotes WHERE symbol in ('" + stockSymbol.toUpperCase() + "')";
		String yqlOptions = "&format=json";
		yqlOptions += "&env=" + "http://datatables.org/alltables.env";
		
		HttpURLConnection httpConnect = null;
		URL queryUrl = null;
		BufferedReader buffy = null;
		String line = null;
		StringBuilder builder = null;
		
		try {
			String yqlQueryUrl = yqlBaseUrl + "?q=" + URLEncoder.encode(yqlQuery, "UTF-8") + yqlOptions;
			queryUrl = new URL(yqlQueryUrl);
			httpConnect = (HttpURLConnection) queryUrl.openConnection();
			httpConnect.setRequestMethod("GET");
			httpConnect.setDoOutput(true);
			httpConnect.setReadTimeout(5000);
			httpConnect.connect();
		
			buffy = new BufferedReader(
				new InputStreamReader(
					httpConnect.getInputStream()
			));
			
			builder = new StringBuilder();
			
			while ((line = buffy.readLine()) != null) {
				builder.append(line + "\n");
			}
			
			if (builder.length() > 0) {
				String jsonText = builder.toString();
				System.out.printf("[+] %s\n\n", jsonText);
				String[] jsonParts = jsonText.split("\\{");
				String[] stockInfo = jsonParts[4].replace("}", "").split(",");
				
				String symbol = stockInfo[0].replace("\"", "");
				String price = stockInfo[1].replace("\"", "");
				String lastTrade = stockInfo[13].replace("\"", "");
				String name = stockInfo[52].replace("\"", "");
				String espeCurrentYear = stockInfo[17].replace("\"", "");
				String espeNextYear = stockInfo[18].replace("\"", "");
				String espeNextQuarter = stockInfo[19].replace("\"", "");
				
				System.out.printf("[+] %s\n", symbol);
				System.out.printf("[+] %s\n", price);
				System.out.printf("[+] %s\n", lastTrade);
				System.out.printf("[+] %s\n", name);
				System.out.printf("[+] %s\n", espeCurrentYear);
				System.out.printf("[+] %s\n", espeNextYear);
				System.out.printf("[+] %s\n", espeNextQuarter);
				
			} else {
				System.out.printf("[-] No Results");
			}
			
		} catch (Exception e) {
			System.out.printf("[!] Err: %s", e.toString());
		} finally {
			httpConnect.disconnect(); buffy = null; builder = null;
		}
		
	}

}