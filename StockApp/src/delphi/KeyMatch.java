package delphi;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import utils.WebData;

public class KeyMatch {

	public String[] getNews(String symbol) {
		String[] text = null;

		String url = String.format("https://www.google.com/finance/company_news?q=%s", symbol);
		Document d = WebData.getSoup(url);
		Elements links = d.getElementsByClass("name");

		int linkCount = links.size(); 
		text = new String[linkCount];
		String link;
		for (int i = 0; i < linkCount; i++) {
			link = links.get(i).getElementsByAttribute("href").attr("href").toString();
			Document story = WebData.getSoup(link);
			Elements paragraphs = story.select("p"); // FIXME - Throws intermittent nullpointers exceptions
			text[i] = paragraphs.text();
		}
		
	return text;
	}

	/*
	 * Strong 2 attractive target 3 positive momentum 4 still a buy 5 gain 6
	 * rise 7 relative strength 8 optimism 9 potential boost 10 bolster 11 safe
	 * haven 12 durable 13 profit 14 profitable 15 high 16 Increase
	 * 
	 * 1. harmful 2. complaints 3. sued by 4. patent infringement 5. decline 6.
	 * greater pessimism 7 don’t buy 8 low 9 Fall 10 Decrease 11 failure
	 */
	public ArrayList<String> getMatchingKeywords(String[] text) {
		// String[] matchingKeywords;
		ArrayList<String> matchedKeywords = new ArrayList<String>();
		String[] keywords = { "strong", "attractive traget",
				"positive momentum", "still a buy", "gain", "rise", "rising",
				"relative strength", "optimism", "potential boost", "bolster",
				"safe heaven", "durable", "profit", "profitable", "high",
				"increase", "increasing", "speculation", "sccessful", "reap",
				"harmful", "complaints", "sued by", "declining", "decline",
				"decreasing", "decrease", "going down", "greater pessimism",
				"don't but", "low", "fall", "failure", "fluctuales" };
		// matchingKeywords = new String[200];
		for (int i = 0; i < text.length; i++) {
			for (int j = 0; j < keywords.length; j++) {
				if (text[i].contains(keywords[j])) {
					matchedKeywords.add(keywords[j]);
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
}