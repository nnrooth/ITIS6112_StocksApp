package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebData {

	public static void main(String[] args) {
		Document doc = getSoup(null);
		Elements a = doc.getAllElements();
	}
	
	public static Document getSoup(URL url) {
		String html = "<html><head><title>First parse</title></head>"
				  + "<body><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
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