package delphi;

import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.nodes.Document;

import utils.WebData;

public class Top10 {

	public static double getScore(String symbol) {
		double score = 0;
		
		// Use regex to validate query
		if (!symbol.matches("[a-zA-Z]{1,4}")) {
			return score;
		}
		
		if (isInTop10(symbol)) { score += 10; } else
		if (isInBottom10(symbol)) { score -= 10; }
		
		
		return score;
	}
	
	private static boolean isInTop10(String symbol) {
		boolean found = false;
		
		try {
			URL url = new URL("http://beta.fool.com/brewcrewfool/2013/02/10/the-top-10-stocks-for-2013-and-beyond/24111/");
			url = new URL("http://investing.money.msn.com/investments/stockscouter-top-rated-stocks?sco=2&page=1&col=7");
			
			Document d = WebData.getSoup(url);
			
			String query = String.format("%s</a>", symbol);
			
			found = d.toString().toUpperCase().contains(query);
			
		} catch (MalformedURLException ignored) {}
		
		return found;
	}
	
	private static boolean isInBottom10(String symbol) {
		boolean found = false;
		try {
			URL url = new URL("http://slant.investorplace.com/2012/12/10-worst-stocks-of-2012/");
			url = new URL("http://investing.money.msn.com/investments/stockscouter-top-rated-stocks?sco=1&page=1&col=7");
			url = new URL("http://investorplace.com/2012/06/10-worst-stocks-so-far-in-2012/view-all/");
			Document d = WebData.getSoup(url);
			
			String query = String.format(">%s<", symbol);
			
			found = d.toString().toUpperCase().contains(query);
			
		} catch (MalformedURLException ignored) {}
		
		return found;
	}
}