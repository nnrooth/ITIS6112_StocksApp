package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;


public class WebData implements Runnable {

	String response;
	private int thread;
	private URL url;
	
    public WebData(int thread, URL url) {
    	this.thread = thread;
    	this.url = url;
    }
    
    public String getResponse() {
    	return response;
    }
    
    private void setResponse(String response) {
    	this.response = response;
    }
    
    private int getThread() {
    	return thread;
    }
    
    private URL getUrl() {
    	return url;
    }
	
	public void run() {
		try {
			setResponse(makeRequest(getUrl()));
		} catch (IOException e) {
			System.out.printf("[-] Err: thread-%s\n", getThread());
			setResponse(null);
		}
    }

    public static void main(String args[]) {
    	URL[] url = new URL[4];
    	try {
			url[0] = new URL("http://www.github.com");
			url[1] = new URL("http://www.google.com");
			url[2] = new URL("http://www.amazon.com");
			url[3] = new URL("http://www.msnbc.com");
			
			for (int n = 0; n < url.length; n++) {
	    		(new Thread(new WebData(n, url[n]))).start();
	    	}
		} catch (MalformedURLException e) {
			System.out.printf("[-] Err: A URL you provided is invalid\n");
		}
    }
    
    public static String makeRequest(URL url) throws IOException {
    	HttpURLConnection httpConnect = null;
		BufferedReader buffy = null; // lulzy reference to buffy the vampire slayer
		String line = null;
		StringBuilder builder = null;
					
		httpConnect = (HttpURLConnection) url.openConnection(); // Create a new connection object
		httpConnect.setRequestMethod("GET"); // Send query using url encoding
		httpConnect.setDoOutput(true); // Allow reading http response
		
		int timeout = 2500; // Set response timeout at 2.5 seconds
		try {
			httpConnect.setReadTimeout(timeout);
			httpConnect.connect();
		} catch (SocketTimeoutException to1) {
			try {
				httpConnect.setReadTimeout(timeout * 2);
				httpConnect.connect();
			} catch (SocketTimeoutException to2) {
				try {
					httpConnect.setReadTimeout(timeout * 3);
					httpConnect.connect();
				} catch (SocketTimeoutException to3) {
					try {
						httpConnect.setReadTimeout(timeout * 4);
						httpConnect.connect();
					} catch (SocketTimeoutException to4) {
						return null;
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
	
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