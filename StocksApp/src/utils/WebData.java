package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;
/**
 * This class is used to fetch data from the internet. All the web related queried are fired in this class.
 * 
 * 
 * @author NNRooth
 * 
 */
public class WebData {

	private static final String TAG = "WebData";
	
	// TODO If web requests are timing out, increase this value
	// Too many 0 values is most likely caused by timeouts
	private static final int DEFAULT_TIMEOUT = 2500 /* MilliSeconds */;

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

	public String makeRequest() {
		URLConnection urlConnect = null;
		BufferedReader buffRead = null; 
		String line = null;
		StringBuilder builder = null;
		String response = null;
		Log.d(TAG, "Making Connection: " + url);
		try {
			/* Create a new connection object */
			urlConnect = url.openConnection();
			/* This is where the connection timeout is set */
			urlConnect.setDoInput(true);
			urlConnect.setConnectTimeout(timeout);
//			urlConnect.setReadTimeout(timeout);
			
			/* Read from the connection as a buffered reader */			
			buffRead = new BufferedReader(
					new InputStreamReader(
					urlConnect.getInputStream()
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
			Log.e(TAG, "Error reading response", e);
			// Final attempt to recover, just ignore and dump all retrieved data
		}
		
		response = builder.toString();
		Log.d(TAG, "Returning Response");
		return response;
	}
}