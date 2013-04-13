package utils;

import java.io.BufferedReader; // Buffy!!!
import java.io.IOException;

import java.io.InputStreamReader; // Slayer!!!

import java.net.HttpURLConnection;
import java.net.URL;

// import android.util.Log; TODO - Uncomment when added to android project

public class ResourceRequestTask implements Runnable{
	private static final String TAG = "WebDataRequest";
	private final URL requestUrl;
	
	ResourceRequestTask(URL requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	public void run() {
		String results = makeRequest(requestUrl);
	}
	
	private String makeRequest(URL requestUrl) {
		HttpURLConnection connect = null;
		String result = "";
		
		try {
			if (Thread.interrupted())
				throw new InterruptedException();
			
			connect = (HttpURLConnection) requestUrl.openConnection();
			connect.setReadTimeout(10000 /* milliseconds */);
			connect.setConnectTimeout(15000 /* milliseconds */);
			connect.setRequestMethod("GET");
			connect.setDoInput(true);
			
			connect.connect();
			
			if (Thread.interrupted())
				throw new InterruptedException();
			
			BufferedReader buffy = new BufferedReader(
					new InputStreamReader(connect.getInputStream(), "UTF-8"));
			result = buffy.readLine();
			buffy.close();
			
			if (Thread.interrupted())
				throw new InterruptedException();
		} catch (IOException e) {
			// Log.e(TAG, "IOException", e); TODO Uncomment when added to android platform
		} catch (InterruptedException e) {
			// Log.d(TAG, "InterruptedException", e); TODO Uncomment when added to android platform
		} finally {
			if (connect != null) {
				connect.disconnect();
			}
		}
		
		// Log.d(TAG, "   --> returned " + result); TODO - Uncomment when added to android platform
		return result;
	}	
}