package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebData implements Runnable {

	private static final int DEFAULT_TIMEOUT = 3500 /* MilliSeconds */; 
	
	String response;
	private URL url;
	private int timeout;
	
    public WebData(URL url) {
    	this.url = url;
    	this.timeout = DEFAULT_TIMEOUT;
    }
    
    public WebData(URL url, int timeout) {
    	this.url = url;
    	this.timeout = timeout;
    }
    
    public String getResponse() {
    	return response;
    }
    
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
		BufferedReader buffy = null; // lulzy reference to buffy the vampire slayer
		String line = null;
		StringBuilder builder = null;
		
		try {
			httpConnect = (HttpURLConnection) url.openConnection(); // Create a new connection object
			httpConnect.setRequestMethod("GET"); // Send query using url encoding
			httpConnect.setDoOutput(true); // Allow reading http response
		} catch (Exception e) {
			return null;
		}
		
		try {
			httpConnect.setReadTimeout(timeout);
			httpConnect.connect();
			buffy = new BufferedReader(
					new InputStreamReader(
						httpConnect.getInputStream()
				));
		} catch (Exception e) {
			return null;
		}
	
		// Read the response into a StringBuilder object
		builder = new StringBuilder();
		try {
			while ((line = buffy.readLine()) != null) {
				builder.append(line + "\n");
			}
		} catch (IOException e) {
			try {
				while ((line = buffy.readLine()) != null) {
					builder.append(line + "\n");
				}
			} catch (IOException e1) {
				return builder.toString();
			}
		}
		return builder.toString();
	}
}