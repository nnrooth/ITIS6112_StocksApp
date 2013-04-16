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
		
	private static Map<String, Integer> getKeywordList() {
		Map<String, Integer> keyWordList = new HashMap<String, Integer>(20);
		keyWordList.put("strong", 10);
		keyWordList.put("attractive target", 7);
		keyWordList.put("still a buy", 4);
		keyWordList.put("positive momentum", 5);
		keyWordList.put("gain", 5);
		keyWordList.put("rise", 4);
		keyWordList.put("rising", 4);
		keyWordList.put("strength", 2);
		keyWordList.put("relative strength", 3);
		keyWordList.put("boost", 3);
		keyWordList.put("optimism", 4);
		keyWordList.put("potential boost", 3);
		keyWordList.put("bolster",2);
		keyWordList.put("safe heaven", 3);
		keyWordList.put("safe", 3);
		keyWordList.put("durable",5);
		keyWordList.put("profit", 4);
		keyWordList.put("profitable", 4);
		keyWordList.put("high",3);
		keyWordList.put("increase",3);
		keyWordList.put("speculation", 2);
		keyWordList.put("successful", 5);
		keyWordList.put("reap", 7);
		keyWordList.put("buy", 4);
		keyWordList.put("harmful", -5); 
		keyWordList.put("complaints", -2);
		keyWordList.put("sued by", -3);
		keyWordList.put("declining", -3);
		keyWordList.put("decline", -7);
		keyWordList.put("decreasing", -4);
		keyWordList.put("decrease ", -4);
		keyWordList.put("going down ", -4);
		keyWordList.put("greater pessimism", -7);
		keyWordList.put("don’t buy", -10);
		keyWordList.put("low",-2);
		keyWordList.put("fall", -3);
		keyWordList.put("failure", -3);
		keyWordList.put("fluctuates", -2);
		keyWordList.put("sell", -2);
		
		return keyWordList;
	}
	
	public String[] getNews(String symbol) throws IOException {
		String[] text = null;
		URL url; int timeout = 2500 /* MilliSeconds */;
		String response = null; // int strike = 0; int strikes = 1;
		
		//while (response == null && strike++ < strikes) {
			url = new URL(String.format("https://www.google.com/finance/company_news?q=%s", symbol));
			WebData request = new WebData(url, timeout);
			Thread requestThread = new Thread(request);
			requestThread.start();
			while(requestThread.isAlive()) {}
			response = request.getResponse();
		//}
		
		Document d = Jsoup.parse(response);
		Elements links = null; int linkCount;
		
		try {
			links = d.getElementsByClass("name"); // XXX - Quick and dirty fix
			linkCount = links.size();
			text = new String[linkCount];
		} catch (Exception e) {
			return null;
		}
		
		String link; WebData[] requests = new WebData[linkCount];
		for (int i = 0; i < linkCount; i++) {
			link = links.get(i).getElementsByAttribute("href").attr("href").toString();
			url = new URL( link.substring( ( link.indexOf("&url=") + 5 ), link.indexOf("&cid=") ) );
			requests[i] = new WebData(url, timeout);
		}
		ArrayList<Thread> newsThreads = new ArrayList<Thread>(10);
		for (int n = 0; n < requests.length; n++) {
			newsThreads.add(new Thread(requests[n]));
			newsThreads.get(n).run();
			try {
				newsThreads.get(n).join();
			} catch (InterruptedException e) {}
		}
		
		for (int n = 0; n < requests.length; n++) {
			response = requests[n].getResponse();
			try {
				Document story = Jsoup.parse(response);
				Elements paragraphs = story.select("p"); // XXX - Quick and dirty fix
				text[n] = paragraphs.text();
			} catch (Exception e) {
				return null;
			}
		}
		
	return text;
	}

	
	public ArrayList<String> getMatchingKeywords(String[] text) {
		Object[] keywordList = getKeywordList().keySet().toArray();
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

		/*for (int i = 0; i < matchedKeywords.size()
				&& matchedKeywords.get(i) != null; i++) {
			for (int j = i + 1; j < matchedKeywords.size()
					&& matchedKeywords.get(j) != null; j++) {
				if (matchedKeywords.get(i).equalsIgnoreCase(
						matchedKeywords.get(j)))
					matchedKeywords.remove(j);
			}
		}*/
		return matchedKeywords;
	}
	
	private double getScore(ArrayList<String> matchedKeywords) {
		
		Map<String, Integer> keywordList = getKeywordList();

		double score = 0.0;
		int matchCount = matchedKeywords.size();
		for (int i = 0; i < matchCount; i++) {
			if (keywordList.get(matchedKeywords.get(i)) != null)
				score = score + keywordList.get(matchedKeywords.get(i));
		}
		return (score / matchCount);
	}
}