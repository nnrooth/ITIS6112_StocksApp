package delphi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class KeyWord_v2 {

	public ArrayList<String> getNews(String symbol) {
		ArrayList<String> text = new ArrayList<String>();
		String url = String.format("https://www.google.com/finance/company_news?q=%s", symbol);
		try {
			Connection.Response response = Jsoup.connect(url).execute();
			if(response.statusCode()==200) {
				Document doc = Jsoup.connect(url).get();
				Elements abc = doc.getElementsByClass("name");
				for(int i = 0;i < abc.size();i++) {
					Document doc1 = Jsoup.connect(abc.get(i).getElementsByAttribute("href").attr("href").toString()).timeout(4000).get();
					Elements paragraphs = doc1.select("p");
					text.add(paragraphs.text());
				}
			}
			else {
				getNews(symbol);
			}
		}
		catch (MalformedURLException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (Exception ignored) {
			text.clear();
			text.add("");
		}
		return text;
	}
	public ArrayList<String> getMatchingKeywords(ArrayList<String> arrayList) {
		ArrayList<String> matchedKeywords = new ArrayList<String>();
		String[] keywords = { "strong","attractive target","positive momentum","still a buy","gain","rise","rising","strength","relative strength","boost","optimism","potential boost","bolster","safe   heaven","durable","profit",
				"profitable","high","increase","increasing","speculation","successful","reap","harmful","complaints",
				"sued by","declining","decline","decreasing","decrease","going down","greater pessimism",
				"don't but","low","fall","failure","fluctuates","buy","sell" };
		
		String[] articles = new String[arrayList.size()];
	    articles = arrayList.toArray(articles);
		
		for (int i = 0; i < articles.length; i++) {
			for (int j = 0; j < keywords.length; j++) {
				if (articles[i].contains(keywords[j])) {
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