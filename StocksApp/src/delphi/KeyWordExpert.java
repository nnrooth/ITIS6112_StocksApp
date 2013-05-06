package delphi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import utils.WebData;

/**
 * @Author: Team 3+4
 * @Description: This class is used to grade the keywords that are extracted from the internet. 
 * These keywords have a pre-defined score and the average is returned to h=the calling function.
 * 
 */
public class KeyWordExpert {


	/*
	 * @Name getScore
	 * @Input Paramters String
	 * @return type double
	 * @@description returns the Keyword score
	 */
	public double getScore(String symbol) {
		double score = 0.00;
		try {
			String[] news = getNews(symbol);
			ArrayList<String> matches = getMatchingKeywords(news);
			score = getScore(matches);
		} catch (Exception e) {
			score = 0.01;
		}

		return score;
	}

	/**
	 * Use this list for original keyword weights
	 */
	/**
	 * @Name getKeywordList
	 * @Input null
	 * @return type Map<String, Integer>
	 * @@description Provides a Map with the pre-defined keyword score. This map is used to compare the keywords 
	 * extracted from the internet.
	 */
	
	private static Map<String, Integer> getKeywordList() {
		Map<String, Integer> keyWordList = new HashMap<String, Integer>(20);
		keyWordList.put("very strong", 10);
		keyWordList.put("attractive target", 7);
		keyWordList.put("positive momentum", 5);
		keyWordList.put("gain", 5);
		keyWordList.put("rise", 4);
		keyWordList.put("rising", 4);
		keyWordList.put("strength", 2);
		keyWordList.put("relatively strong", 3);
		keyWordList.put("boost", 3);
		keyWordList.put("optimism", 4);
		keyWordList.put("potential boost", 3);
		keyWordList.put("bolster", 2);
		keyWordList.put("safe", 3);
		keyWordList.put("durable", 5);
		keyWordList.put("profit", 4);
		keyWordList.put("profitable", 4);
		keyWordList.put("high", 3);
		keyWordList.put("increase", 3);
		keyWordList.put("speculation", 2);
		keyWordList.put("successful", 5);
		keyWordList.put("reap", 7);
		keyWordList.put("harmful", -5);
		keyWordList.put("complaints", -2);
		keyWordList.put("lawsuit", -3);
		keyWordList.put("declining", -3);
		keyWordList.put("decline", -7);
		keyWordList.put("decreasing", -4);
		keyWordList.put("decrease", -4);
		keyWordList.put("plummet", -7);
		keyWordList.put("pessimism", -7);
		keyWordList.put("don’t buy", -10);
		keyWordList.put("low", -2);
		keyWordList.put("fall", -3);
		keyWordList.put("failure", -3);
		keyWordList.put("fluctuates", -2);
		keyWordList.put("sell", -2);

		return keyWordList;
	}

	/**
	 * @Name getNews()
	 * @Input String
	 * @return type String[]
	 * @@description Extracts the news from the in internet related to the symbol provided as imput.
	 * The news is in the String[] format.
	 */
	
	public String[] getNews(String symbol) throws IOException {
		String[] text = null;
		URL url;

		String response = null;

		url = new URL(String.format(
				"https://www.google.com/finance/company_news?q=%s", symbol));

		WebData request = new WebData(url);
		response = request.makeRequest();
		url = null;
		request = null;

		Document d = Jsoup.parse(response);
		Elements links = null;
		int linkCount;

		try {
			links = d.getElementsByClass("name");
			linkCount = links.size();
			text = new String[linkCount];
		} catch (Exception e) {
			linkCount = 0;
		}

		String link;
		WebData[] requests = new WebData[linkCount];
		for (int i = 0; i < linkCount; i++) {
			link = links.get(i).getElementsByAttribute("href").attr("href")
					.toString();
			url = new URL(link.substring((link.indexOf("&url=") + 5),
					link.indexOf("&cid=")));

			requests[i] = new WebData(url);
		}

		for (int n = 0; n < requests.length; n++) {
			response = requests[n].makeRequest();
			try {
				Document story = Jsoup.parse(response);
				Elements paragraphs = story.select("p");
				text[n] = paragraphs.text();
			} catch (Exception e) {
				if (text[n] == null) {
					text[n] = "";
				}
			}
		}

		return text;
	}

	/**
	 * @Name getMatchingKeywords()
	 * @Input String[]
	 * @return type ArrayList<String>
	 * @@description Extracts the keywords from the news related to the symbol provided.
	 */
	
	public ArrayList<String> getMatchingKeywords(String[] text) {
		Object[] keywordList;

		// This is only used for matching so changes here have no affect.
		keywordList = getKeywordList().keySet().toArray();
		// keywordList = getKeywordListV2().keySet().toArray();
		ArrayList<String> matchedKeywords = new ArrayList<String>();
		String keyword;

		for (int i = 0; i < text.length; i++) {
			for (int j = 0; j < keywordList.length; j++) {
				keyword = keywordList[j].toString();
				if (text[i].contains(keyword)) {
					matchedKeywords.add(keyword);
				}
			}
		}

		return matchedKeywords;
	}

	/**
	 * @Name getScore()
	 * @Input ArrayList<String>
	 * @return type double
	 * @@description Calculates the score for all the keywords matched.
	 */
	
	private double getScore(ArrayList<String> matchedKeywords) {

		Map<String, Integer> keywordList;

		// Change here to use different scoring methods
		keywordList = getKeywordList();
		// keywordList = getKeywordListV2();

		double score = 0.0;
		int matchCount = matchedKeywords.size();
		for (int i = 0; i < matchCount; i++) {
			if (keywordList.get(matchedKeywords.get(i)) != null)
				score = score + keywordList.get(matchedKeywords.get(i));
		}
		return (score / matchCount);
	}
}