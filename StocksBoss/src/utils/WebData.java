package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebData {

	public static Document getSoup(URL url) {
		Document doc = null;
		Connection connect = Jsoup.connect(url.toString());
		int timeout = 2500;
		try {
			connect.timeout(timeout);
			doc = connect.get();
		} catch (IOException e1) {
			try {
				connect.timeout(timeout * 2);
				doc = connect.get();
			} catch (IOException e2) {
				try {
					connect.timeout(timeout * 3);
					doc = connect.get();
				} catch (IOException ignored) {}
			}
		}
		return doc;
	}
	
	public static String makeRequest(URL url) throws IOException {
		HttpURLConnection httpConnect = null;
		BufferedReader buffy = null; // lulzy reference to buffy the vampire slayer
		String line = null;
		StringBuilder builder = null;
					
		httpConnect = (HttpURLConnection) url.openConnection(); // Create a new connection object
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
		return builder.toString();
	}	
}