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

public class KeyWordExpert {

	// TODO Timeout is working, but needs to be refined for better speeds.

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
	 * Use this list for 10/-10 scores only
	 */
	@SuppressWarnings("unused")
	private static Map<String, Integer> getKeywordListV2() {
		Map<String, Integer> keyWordList = new HashMap<String, Integer>(20);
		keyWordList.put("very strong", 10);
		keyWordList.put("attractive target", 10);
		keyWordList.put("positive momentum", 10);
		keyWordList.put("gain", 10);
		keyWordList.put("rise", 10);
		keyWordList.put("rising", 10);
		keyWordList.put("strength", 10);
		keyWordList.put("relatively strong", 10);
		keyWordList.put("boost", 10);
		keyWordList.put("optimism", 10);
		keyWordList.put("potential boost", 10);
		keyWordList.put("bolster", 10);
		keyWordList.put("safe", 10);
		keyWordList.put("durable", 10);
		keyWordList.put("profit", 10);
		keyWordList.put("profitable", 10);
		keyWordList.put("high", 10);
		keyWordList.put("increase", 10);
		keyWordList.put("speculation", 10);
		keyWordList.put("successful", 10);
		keyWordList.put("reap", 10);
		keyWordList.put("harmful", -10); 
		keyWordList.put("complaints", -10);
		keyWordList.put("lawsuit", -10);
		keyWordList.put("declining", -10);
		keyWordList.put("decline", -10);
		keyWordList.put("decreasing", -10);
		keyWordList.put("decrease", -10);
		keyWordList.put("plummet", -10);
		keyWordList.put("pessimism", -10);
		keyWordList.put("don’t buy", -10);
		keyWordList.put("low", -10);
		keyWordList.put("fall", -10);
		keyWordList.put("failure", -10);
		keyWordList.put("fluctuates", -10);
		keyWordList.put("sell", -10);
		
		return keyWordList;
	}
	
	/**
	 * Use this list for original keyword weights
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

	public String[] getNews(String symbol) throws IOException {
		String[] text = null;
		URL url;
		
		int timeout = 1500 /* MilliSeconds */; // TODO Optimize for performance
		
		String response = null;

		url = new URL(String.format(
				"https://www.google.com/finance/company_news?q=%s", symbol));

		WebData request = new WebData(url); // TODO - Optimize for performance

		Thread requestThread = new Thread(request);
		requestThread.start();

		try {
			requestThread.join(timeout / 2); // TODO - Optimize for performance
		} catch (InterruptedException e) {
		}
		
		response = request.getResponse();
		url = null;
		request = null;
		requestThread = null;

		Document d = Jsoup.parse(response);
		Elements links = null;
		int linkCount;

		try {
			links = d.getElementsByClass("name"); // XXX - Quick and dirty fix
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
		ArrayList<Thread> newsThreads = new ArrayList<Thread>(linkCount);
		for (WebData data : requests) {
			newsThreads.add(new Thread(data));
		}
		
		for (Thread thread : newsThreads) {
			thread.start();
		}
		
		for (Thread thread : newsThreads) {
			try {
				
				thread.join(timeout); // TODO - Optimize for performance
				
			} catch (InterruptedException e) {}
		}
		
		for (int n = 0; n < requests.length; n++) {
			response = requests[n].getResponse();
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

	public ArrayList<String> getMatchingKeywords(String[] text) {
		Object[] keywordList;
		
		// TODO - Decide on final keyword weights. List v2 uses 10/-10
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

	private double getScore(ArrayList<String> matchedKeywords) {

		Map<String, Integer> keywordList;
		
		// TODO - Decide on final keyword weights. List v2 uses 10/-10
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