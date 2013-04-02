package delphi;

import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.nodes.Document;

import utils.WebData;

public class Top10 {

	public static void main(String[] args) {
		System.out.printf("[+] Score: %s", getScore("msft"));
	}
	
	public static int getScore(String symbol) {
		int score = 0;
		
		if (isInBottom10(symbol)) { score -= 10; } else
		if (isInTop10(symbol)) { score += 10; }
		
		
		return score;
	}
	
	private static boolean isInTop10(String symbol) {
		boolean found = false;
		try {
			URL url = new URL("http://slant.investorplace.com/2012/12/10-worst-stocks-of-2012/");
			Document d = WebData.getSoup(url);
			
			String query = String.format(">%s<", symbol);
			
			found = d.toString().toUpperCase().contains(query);
			
		} catch (MalformedURLException ignored) {}
		
		return found;
	}
	
	private static boolean isInBottom10(String symbol) {
		boolean found = false;
		try {
			URL url = new URL("http://slant.investorplace.com/2012/12/10-worst-stocks-of-2012/");
			Document d = WebData.getSoup(url);
			
			String query = String.format(">%s<", symbol);
			
			found = d.toString().toUpperCase().contains(query);
			
		} catch (MalformedURLException ignored) {}
		
		return found;
	}
}