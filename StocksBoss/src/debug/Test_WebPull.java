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
		String xmlBaseUrl = "http://finance.yahoo.com/d/quotes.csv?";
		String xmlsQuery = "s=" + stockSymbol;
		String xmlfQuery = "f=snxl1pt8w";
		/*
		 * s - symbol
		 * n - name
		 * x - stock exchange
		 * l1 - last trade price
		 * p - previous close price
		 * e7 - EPSE Current Year
		 * e8 - EPSE Next Year
		 * e9 - EPSE Next Quarter
		 * j - 52-week low
		 * k - 52-week high
		 * j6 - % change from 52-week low
		 * k5 - % change from 52-week high
		 * m3 - 50-day moving average
		 * m4 - 200-day moving average
		 * m6 - % change from 200-day moving average
		 * m8 - % change from 50-day moving average
		 * t8 - 1 year target price
		 * w - 52-week range [low-high]
		 */
		
		HttpURLConnection httpConnect = null;
		URL queryUrl = null;
		BufferedReader buffy = null;
		String line = null;
		StringBuilder builder = null;
		
		try {
			String xmlQueryUrl = xmlBaseUrl + xmlsQuery + "&" + xmlfQuery;
			queryUrl = new URL(xmlQueryUrl);
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
				String xmlText = builder.toString();
				System.out.printf("[+] %s\n\n", xmlText);
				String[] stockInfo = xmlText.replace("\"", "").split(",");
				
				for (int n = 0; n < stockInfo.length; n++) {
					System.out.printf("[%s] %s\n", n, stockInfo[n]);
				}
				
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