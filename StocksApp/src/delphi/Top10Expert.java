package delphi;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.WebData;

public class Top10Expert {

	private static String[] top10List;
	private static String[] bottom10List;
	private static int timeout = 3500; // TODO - Optimize for performance
	
	public static double getScore(String symbol) {
		double score = 0;
		
		// Use regex to validate query
		if (!symbol.matches("[a-zA-Z]{1,4}")) {
			return score;
		}
		
		if (isInTop10(symbol.toUpperCase())) { score += 10; } else
		if (isInBottom10(symbol.toUpperCase())) { score -= 10; }
		
		return score;
	}
	
	private static boolean isInTop10(String symbol) {
		String[] top10List;
		boolean found;
		
		try {
			top10List = getTop10Regex();
			found = Arrays.asList(top10List).contains(symbol);
		} catch (Exception e) {
			found = false;
		}
		
		return found;
	}
	
	private static boolean isInBottom10(String symbol) {
		String[] bottom10List;
		boolean found;
		
		try {
			bottom10List = getBottom10Regex();
			found =  Arrays.asList(bottom10List).contains(symbol);
		} catch (Exception e) {
			found = false;
		}
		
		return found;
	}
	
	private static String getBottom10Listing() {
		String response = "";
		try {
			URL url = new URL("http://investing.money.msn.com/investments/stockscouter-top-rated-stocks?sco=1&col=6&asc=1");
			
			WebData request = new WebData(url);
			
			Thread thread = new Thread(request);
			thread.start(); thread.join(timeout); // TODO Optimize for performance
			
			response = request.getResponse();
		} catch (Exception ignored1) {}
		return response;
	}
	
	public static String[] getBottom10Regex() {
		if (bottom10List == null) {
			String string = getBottom10Listing();
			Pattern pattern = Pattern.compile("(\\>)(.*?)(\\<a\\>)");
			pattern = Pattern.compile("(symbol\\=)([a-zA-Z]{1,4})");
			Matcher matcher = pattern.matcher(string);
			
			List<String> listMatches = new ArrayList<String>();
			
			while(matcher.find() /*&& listMatches.size()<10*/) {
			    listMatches.add(matcher.group(2).toUpperCase());
			}
			
			bottom10List = listMatches.toArray(new String[listMatches.size()]);
		}
		
		return bottom10List;
	}
	
	private static String getTop10Listing() {
		String response = "";
		try {
			URL url = new URL("http://investing.money.msn.com/investments/stockscouter-top-rated-stocks?sco=10&col=6");
			
			WebData request = new WebData(url);
			
			Thread thread = new Thread(request);
			thread.start(); thread.join(timeout); // TODO Optimize for performance
			
			response = request.getResponse();
		} catch (Exception ignored1) {}
		return response;
	}
	
	public static String[] getTop10Regex() {
		if (top10List == null) {	
			String string = getTop10Listing();
			Pattern pattern = Pattern.compile("(\\>)(.*?)(\\<a\\>)");
			pattern = Pattern.compile("(symbol\\=)([a-zA-Z]{1,4})");
			Matcher matcher = pattern.matcher(string);
			
			List<String> listMatches = new ArrayList<String>();
			
			while(matcher.find() /*&& listMatches.size()<10*/) {
			    listMatches.add(matcher.group(2).toUpperCase());
			}
			
			top10List = listMatches.toArray(new String[listMatches.size()]);
		}
		
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