package delphi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import web.WebData;

public class KeyWordExpert {

	// TODO Timeout is working, but needs to be refined for better speeds.
	
	public static void main(String[] args) {
		String[] news;
		long startTime, endTime, runTime, totalTime, testRuns, errors, empty;
		
		Random randy = new Random();
		for (int n = 0; n < 25; n++) {
			randy.nextInt();
		}
		
		String[] symbols = new String[] {"goog", "msft", "amzn", "csco", "aapl", "able", "nvda", "jpm", "lnkd", "uvxy"};
		
		testRuns = 2;
		errors = 0; empty = 0;
		totalTime = 0;
		
		System.out.printf("[*] Starting %s test runs\n", testRuns);
		for (int run = 0; run < testRuns; run++) { 
			startTime = System.currentTimeMillis() /* MilliSeconds */;
			KeyWordExpert expert = new KeyWordExpert();
			try {
				news = expert.getNews(symbols[randy.nextInt(symbols.length)]);
			} catch (IOException e) {
				news = null;
			}
			endTime = System.currentTimeMillis() /* MilliSeconds */;
			runTime = (endTime - startTime) /* MilliSeconds */;
			if (news == null) {
				errors++;
			} else if (news.equals("")) {
				empty++;
			} else {
				System.out.printf("[+]\t%s\t%s\t%s\n", news[0].contains("Strong"), news[1].contains("Strong"), news[2].contains("Strong"));				
			}
			totalTime += runTime /* MilliSeconds */;
		}
		
		System.out.printf("[+] Completed in %1$.2f seconds\n", (totalTime / 1000.00));
		System.out.printf("[+] Each run completed in an average of %1$.2f seconds\n", ((totalTime / 1000.00) / testRuns));
		System.out.printf("[+] %s errors caught\n", errors);
		System.out.printf("[+] %s empty resonses\n", empty);
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
		String response = null; int strike = 0; int strikes = 1;
		
		while (response == null || strike++ < strikes) {
			url = new URL(String.format("https://www.google.com/finance/company_news?q=%s", symbol));
			WebData request = new WebData(url, timeout);
			(new Thread(request)).start();
			while(Thread.activeCount() > 1){}
			response = request.getResponse();
		}
		
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
		
		for (int n = 0; n < requests.length; n++) {
			(new Thread(requests[n])).start();
		}
		
		while(Thread.activeCount() > 1){}
		
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
		ArrayList<String> matchedKeywords = new ArrayList<String>();
		
		Map<String, Integer> keywordList = getKeywordList();
		ArrayList<String> keywords = new ArrayList<String>();
		
		for (int i = 0; i < text.length; i++) {
			for (int j = 0; j < keywordList.size(); j++) {
				if (keywords.contains(text[i])) {
					matchedKeywords.add(keywords.get(j));
				}
			}
		}

		for (int i = 0; i < matchedKeywords.size()
				&& matchedKeywords.get(i) != null; i++) {
			for (int j = i + 1; j < matchedKeywords.size()
					&& matchedKeywords.get(j) != null; j++) {
				if (matchedKeywords.get(i).equalsIgnoreCase(
						matchedKeywords.get(j)))
					matchedKeywords.remove(j);
			}
		}
		return matchedKeywords;
	}
	
	public double getScore(ArrayList<String> matchedKeywords) {
		
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