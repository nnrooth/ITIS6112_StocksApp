package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class WebData implements Runnable {

	private static final String TAG = "WebData";
	
	// TODO If web requests are timing out, increase this value
	// Too many 0 values is most likely caused by timeouts
	private static final int DEFAULT_TIMEOUT = 5000 /* MilliSeconds */;

	String response; // This holds the response from our web request
	private URL url; // This is the address of the web resource we request
	private int timeout;	/* 	This is the maximum time to wait for a response
								before closing the connection */

	public WebData(URL url) {
		this.url = url;
		this.timeout = DEFAULT_TIMEOUT;
	}

	public WebData(URL url, int timeout) {
		this.url = url;
		this.timeout = timeout;
	}

	/**
	 * This method is used to retrieve the response for our http request
	 * 
	 * @return Full unadulterated response, minus headers
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Used internally to set the response object
	 * 
	 * @param response The response to store
	 */
	private void setResponse(String response) {
		this.response = response;
	}

	// TODO - I doubt this method is used. Remove it?
	public URL getUrl() {
		return url;
	}

	// TODO - I doubt this method is used. Remove it?
	public int getTimeout() {
		return timeout;
	}

	public void run() {
		setResponse(makeRequest(getUrl(), getTimeout()));
	}

	private String makeRequest(URL url, int timeout) {
		// FIXME - Rename these variables to whatever is deemed appropriate
		HttpURLConnection httpConnect = null;
		BufferedReader buffy = null; // <- Change
		String line = null; // <- Keep
		StringBuilder builder = null; // <- Change? Keep?

		try {
			/* Create a new connection object */
			httpConnect = (HttpURLConnection) url.openConnection();
			/* Send query using url encoding */
			httpConnect.setRequestMethod("GET");
			httpConnect.setDoOutput(true); // Allow reading http response
			/* This is where the connection timeout is set */
			httpConnect.setReadTimeout(timeout);
			httpConnect.connect(); // Establish the connection
			
			/* Read from the connection as a buffered reader */			
			buffy = new BufferedReader( // <- TODO Buffy Here
					new InputStreamReader(
					httpConnect.getInputStream()
				));
		} catch (Exception e) {
			Log.e(TAG, "Internets All Used Up", e);
			// Looks like the internets are all used up
		}

		// Read the response and store in a StringBuilder object
		builder = new StringBuilder();
		try {
			// Begin reading and storing the response
			while ((line = buffy.readLine()) != null) { // <- FIXME - Buffy here
				builder.append(line + "\n");
			}
		} catch (IOException e) {
			try {
				/* Some characters cause read to throw an error,
				 * skip those characters and attempt to recover
				 */
				while ((line = buffy.readLine()) != null) { // <- FIXME - Buffy here
					builder.append(line + "\n");
				}
			} catch (IOException e1) {
				Log.e(TAG, "Error reading response", e);
				// Final attempt to recover, just ignore and dump all retrieved data
			}
		}
		
		return builder.toString();
	}
}