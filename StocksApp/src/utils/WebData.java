package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
/**
 * This class is used to fetch data from the internet. All the web related queried are fired in this class.
 * 
 * 
 * @author NNRooth
 * 
 */
public class WebData implements Runnable {

	private static final String TAG = "WebData";
	
	// TODO If web requests are timing out, increase this value
	// Too many 0 values is most likely caused by timeouts
	private static final int DEFAULT_TIMEOUT = 5000 /* MilliSeconds */;

	String response; // This holds the response from our web request
	private URL url; // This is the address of the web resource we request
	private int timeout;	/* 	This is the maximum time to wait for a response
								before closing the connection */

	/**
	 * @Name WebData()
	 * @Input URL
	 * @return type null
	 * @description Sets the url parameter of this class with the url passed to this method
	 */
	public WebData(URL url) {
		this.url = url;
		this.timeout = DEFAULT_TIMEOUT;
	}

	/**
	 * @Name WebData()
	 * @Input URL,int
	 * @return type null
	 * @description Sets the url parameter  and the timeout parameter of this class with the url 
	 * and timeout value passed to this method
	 */
	
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

	
	public URL getUrl() {
		return url;
	}

	
	public int getTimeout() {
		return timeout;
	}

	public void run() {
		setResponse(makeRequest(getUrl(), getTimeout()));
	}

	private String makeRequest(URL url, int timeout) {
		
		HttpURLConnection httpConnect = null;
		BufferedReader buffRead = null; 
		String line = null; //
		StringBuilder builder = null; 
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
			buffRead = new BufferedReader( 
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
			while ((line = buffRead.readLine()) != null) { 
				builder.append(line + "\n");
			}
		} catch (IOException e) {
			try {
				/* Some characters cause read to throw an error,
				 * skip those characters and attempt to recover
				 */
				while ((line = buffRead.readLine()) != null) { 
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