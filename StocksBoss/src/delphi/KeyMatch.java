package delphi;

/*import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;*/
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

//import java.util.List;
//import org.json.JSONArray;
//import org.json.JSONException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*import org.jsoup.nodes.Element;
 //import org.jsoup.nodes.Node;*/

//import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

//import org.w3c.dom.Document;

public class KeyMatch {
	static int count = 0;

	public String[] getNews(String symbol) {
		String[] text = null;
		try {
			/*
			 * // get URL content //url = new
			 * URL("http://www.google.com/finance/market_news"); String
			 * stock_name = "AAPL"; //url = new
			 * URL("http://www.google.com/finance/company_news?q=NASDAQ%3" +
			 * stock_name + "&ei=ts9RUdj0Faa6lAODEA"); url = new
			 * URL("http://www.google.com/ig/api?stock=AAPL"); URLConnection
			 * conn = url.openConnection();
			 * 
			 * // open the stream and put it into BufferedReader BufferedReader
			 * br = new BufferedReader( new
			 * InputStreamReader(conn.getInputStream()));
			 * 
			 * String inputLine;
			 * 
			 * //save to this filename String fileName = "./Stockstest.xml";
			 * File file = new File(fileName);
			 * 
			 * 
			 * if (!file.exists()) { System.out.println("Hello");
			 * file.createNewFile(); }
			 * 
			 * //use FileWriter to write file FileWriter fw = new
			 * FileWriter(file.getAbsoluteFile()); BufferedWriter bw = new
			 * BufferedWriter(fw);
			 * 
			 * while ((inputLine = br.readLine()) != null) {
			 * bw.write(inputLine); }
			 * 
			 * bw.close(); br.close();
			 * 
			 * System.out.println("Done 1 ");
			 * 
			 * url1 = new
			 * URL("https://www.google.com/finance/company_news?q=AAPL");
			 * URLConnection conn1 = url1.openConnection();
			 * 
			 * // open the stream and put it into BufferedReader BufferedReader
			 * br1 = new BufferedReader( new
			 * InputStreamReader(conn1.getInputStream()));
			 * 
			 * String inputLine1;
			 * 
			 * //save to this filename String fileName1 = "./Stockstest1.html";
			 * File file1 = new File(fileName1);
			 * 
			 * 
			 * if (!file1.exists()) { System.out.println("Hello");
			 * file1.createNewFile(); }
			 * 
			 * //use FileWriter to write file FileWriter fw1 = new
			 * FileWriter(file1.getAbsoluteFile()); BufferedWriter bw1 = new
			 * BufferedWriter(fw1);
			 * 
			 * while ((inputLine1 = br1.readLine()) != null) {
			 * bw1.write(inputLine1); }
			 * 
			 * bw1.close(); br1.close();
			 * 
			 * System.out.println("Done 2 ");
			 */

			String url = String.format("https://www.google.com/finance/company_news?q=%s", symbol);
			
			Document doc = Jsoup.connect(url).get();

			// Document doc = Jsoup.connect("http://example.com/").get();
			// String title = doc.title();
			// Element class1 = doc.getElementById("news-main");
			// Elements[] abc = new Elements[1];
			Elements abc = doc.getElementsByClass("name");
			/*
			 * //class1.getAllElements(); //String[] news_links = new
			 * String[abc.size()]; //for(int i = 0;i < abc.size();i++)
			 * //news_links[i] =
			 * abc.get(i).getElementsByAttribute("href").attr("href"
			 * ).toString(); //String title_of_link =
			 * abc.get(0).getElementsByAttribute("href").text();
			 * //System.out.println(news_links[0]);
			 */

			text = new String[abc.size()];
			for (int i = 0; i < abc.size(); i++) {
				Document doc1 = Jsoup
						.connect(
								abc.get(i).getElementsByAttribute("href")
										.attr("href").toString()).timeout(4000)
						.get();
				Elements paragraphs = doc1.select("p");
				text[count] = paragraphs.text();
				count++;
			}

			/*
			 * Document doc1 = Jsoup.connect(news_links[1]).get();
			 * List<TextNode> abc1 = doc.body().textNodes(); Elements paragraphs
			 * = doc1.select("p"); String text = paragraphs.text();
			 * System.out.println("hello");
			 */
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		for (int h = 0; h < matchedKeywords.size(); h++)
			System.out.println(matchedKeywords.get(h));
		return matchedKeywords;
	}
}