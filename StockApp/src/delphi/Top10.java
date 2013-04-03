package delphi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		String[] top10List = getTop10Regex();
		return Arrays.asList(top10List).contains(symbol);
	}
	
	private static boolean isInBottom10(String symbol) {
		String[] bottom10List = getBottom10Regex();
		return Arrays.asList(bottom10List).contains(symbol);
	}
	
	private static String getBottom10Listing() {
		String results = "";
		try {
			URL url = new URL("http://slant.investorplace.com/2012/12/10-worst-stocks-of-2012/");
			url = new URL("http://investing.money.msn.com/investments/stockscouter-top-rated-stocks?sco=1&col=7&asc=1");
			//url = new URL("http://investorplace.com/2012/06/10-worst-stocks-so-far-in-2012/view-all/");
			results = WebData.makeRequest(url);
		} catch (Exception ignored1) {}
		return results;
	}
	
	public static String[] getBottom10Regex() {
		String string = getBottom10Listing();
		Pattern pattern = Pattern.compile("(\\>)(.*?)(\\<a\\>)");
		pattern = Pattern.compile("(symbol\\=)([a-zA-Z]{1,4})");
		Matcher matcher = pattern.matcher(string);
		
		List<String> listMatches = new ArrayList<String>();
		
		while(matcher.find()) {
		    listMatches.add(matcher.group(2));
		}
		
		String[] bottom10List = listMatches.toArray(new String[listMatches.size()]);
		
		return bottom10List;
	}
	
	private static String getTop10Listing() {
		String results = "";
		try {
			URL url = new URL("http://beta.fool.com/brewcrewfool/2013/02/10/the-top-10-stocks-for-2013-and-beyond/24111/");
			url = new URL("http://investing.money.msn.com/investments/stockscouter-top-rated-stocks?sco=2&page=1&col=7");
			results = WebData.makeRequest(url);
		} catch (Exception ignored1) {}
		return results;
	}
	
	public static String[] getTop10Regex() {
		String string = getTop10Listing();
		Pattern pattern = Pattern.compile("(\\>)(.*?)(\\<a\\>)");
		pattern = Pattern.compile("(symbol\\=)([a-zA-Z]{1,4})");
		Matcher matcher = pattern.matcher(string);
		
		List<String> listMatches = new ArrayList<String>();
		
		while(matcher.find()) {
		    listMatches.add(matcher.group(2));
		}
		
		String[] top10List = listMatches.toArray(new String[listMatches.size()]);
		
		return top10List;
	}
	
	public static String[] getTop10() {
		String[] top10List = {
				"TCCO",	"SYMX", "CVM", "THLD", "TRNS",
				"CTRM", "BLIN", "ADNC", "RMKR", "ENMD"
			};
		return top10List;
	}
	
	public static String[] getBottom10() {
		String[] bottom10List = {
				"JRCC", "IDN", "KWK", "URRE", "AFFY",
				"FBN", "NBG", "AUMN", "JAG", "PRIS"
			};
		return bottom10List;
	}
}