
package delphi;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.WebData;
import android.util.Log;
/**
 * @Author: Team 3+4
 * @Description: This class is used to get the Top Bottom 10 scores. The Top Bottom 10 list is extracted 
 * from the internet and provided to this class. This class then checks if the selected stock is present
 * in the extracted list and returns the score accordingly.
 * 
 */
public class Top10Expert {

	private static String[] top10List;
	private static String[] bottom10List;

	
	/**
	 * @Name getScore()
	 * @Input String
	 * @return type double
	 * @@description Calculates the score for Top Bottom 10 value
	 */
	
	public static double getScore(String symbol) {
		double score = 0;
		symbol = symbol.toUpperCase(Locale.US);

		if (isInTop10(symbol)) {
			score += 10;
		} else if (isInBottom10(symbol)) {
			score -= 10;
		}

		return score;
	}

	/**
	 * @Name isInTop10()
	 * @Input String
	 * @return type boolean
	 * @@description Checks if the input symbol is present in Top 10 list and returns true if it exists.
	 * 
	 */
	
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


	/**
	 * @Name isINBottom10()
	 * @Input String
	 * @return type boolean
	 * @@description Checks if the input symbol is present in Bottom 10 list and returns true if it exists.
	 * 
	 */
	private static boolean isInBottom10(String symbol) {
		String[] bottom10List;
		boolean found;

		try {
			bottom10List = getBottom10Regex();
			found = Arrays.asList(bottom10List).contains(symbol);
		} catch (Exception e) {
			found = false;
		}

		return found;
	}


	/**
	 * @Name getBottom10Listing()
	 * @Input null
	 * @return type String
	 * @@description Gets the List of Bottom 10 stocks from the internet.
	 */
	private static String getBottom10Listing() {
		String response = "";
		try {
			URL url = new URL(
				"http://investing.money.msn.com/investments/" +
				"stockscouter-top-rated-stocks?sco=1&col=6&asc=1"
			);

			WebData request = new WebData(url);			
			response = request.makeRequest();
		} catch (Exception ignored1) {
			Log.e("Bottom10", ignored1.getMessage());
		}
		return response;
	}

	/**
	 * @Name getBottom10Regex()
	 * @Input null
	 * @return type String[]
	 * @@description Gets the List of Bottom 10 stocks from the internet.
	 */
	public static String[] getBottom10Regex() {
		if (bottom10List == null) {
			String string = getBottom10Listing();
			Pattern pattern = Pattern.compile("(\\>)(.*?)(\\<a\\>)");
			pattern = Pattern.compile("(symbol\\=)([a-zA-Z]{1,4})");
			Matcher matcher = pattern.matcher(string);
			
			List<String> listMatches = new ArrayList<String>();
			
			while(matcher.find() /*&& listMatches.size()<10*/) {
			    listMatches.add(matcher.group(2).toUpperCase(Locale.US));
			}
			
			bottom10List = listMatches.toArray(new String[listMatches.size()]);
		}

		return bottom10List;
	}

	/**
	 * @Name getTop10Listing()
	 * @Input null
	 * @return type String
	 * @@description Gets the List of Top 10 stocks from the internet.
	 */
	private static String getTop10Listing() {
		String response = "";
		try {
			URL url = new URL(
				"http://investing.money.msn.com/investments/" +
				"stockscouter-top-rated-stocks?sco=10&col=6"
			);

			WebData request = new WebData(url);
			response = request.makeRequest();
		} catch (Exception e) {
			Log.e("Top10", e.getMessage());
		}
		return response;
	}

	/**
	 * @Name getTop10Regex()
	 * @Input 
	 * @return type String[]
	 * @@description Gets the List of Top 10 stocks from the internet.
	 */
	public static String[] getTop10Regex() {
		if (top10List == null) {
			String string = getTop10Listing();
			Pattern pattern = Pattern.compile("(\\>)(.*?)(\\<a\\>)");
			pattern = Pattern.compile("(symbol\\=)([a-zA-Z]{1,4})");
			Matcher matcher = pattern.matcher(string);
			
			List<String> listMatches = new ArrayList<String>();
			
			while(matcher.find() /*&& listMatches.size()<10*/) {
			    listMatches.add(matcher.group(2).toUpperCase(Locale.US));
			}
			
			top10List = listMatches.toArray(new String[listMatches.size()]);
		}

		return top10List;
	}

	/**
	 * @Name getTop10()
	 * @Input null
	 * @return type String[]
	 * @@description Hardcoded values for the Top!0 list. This method is not currently being used.
	 */
	public static String[] getTop10() {
		Log.i("Top10", "Defaulted to hard list");
		String[] top10List = {
				"GAZ", "GRID", "FAN", "CORN", "MUNI",
				"BIL", "WOOD", "CUT", "KOL", "JO"
			};
		return top10List;
	}

	/**
	 * @Name getBottom10()
	 * @Input null
	 * @return type String[]
	 * @@description Hardcoded values for the Bottom10 list. This method is not currently being used.
	 */
	public static String[] getBottom10() {
		Log.i("Bottom10", "Defaulted to hard list");
		String[] bottom10List = {
				"EAT", "DNA", "MOO", "BID", "HOG",
				"BUNZ", "LUV", "FB", "SEXY", "TAN"
			};
		return bottom10List;
	}
}