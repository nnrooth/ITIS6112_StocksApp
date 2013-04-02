package delphi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeywordMatching {

	public double keyScore(ArrayList<String> matchedKeywords) {
		
		Map<String, Integer> scoreRule = new HashMap<String, Integer>(20);
		scoreRule.put("strong", 10);
		scoreRule.put("attractive target", 7);
		scoreRule.put("still a buy", 4);
		scoreRule.put("positive momentum", 5);
		scoreRule.put("gain", 5);
		scoreRule.put("rise", 4);
		scoreRule.put("rising", 4);
		scoreRule.put("strength", 2);
		scoreRule.put("relative strength", 3);
		scoreRule.put("boost", 3);
		scoreRule.put("optimism", 4);
		scoreRule.put("potential boost", 3);
		scoreRule.put("bolster",2);
		scoreRule.put("safe heaven", 3);
		scoreRule.put("safe", 3);
		scoreRule.put("durable",5);
		scoreRule.put("profit", 4);
		scoreRule.put("profitable", 4);
		scoreRule.put("high",3);
		scoreRule.put("increase",3);
		scoreRule.put("speculation", 2);
		scoreRule.put("successful", 5);
		scoreRule.put("reap", 7);
		scoreRule.put("buy", 4);
		scoreRule.put("harmful", -5); 
		scoreRule.put("complaints", -2);
		scoreRule.put("sued by", -3);
		scoreRule.put("declining", -3);
		scoreRule.put("decline", -7);
		scoreRule.put("decreasing", -4);
		scoreRule.put("decrease ", -4);
		scoreRule.put("going down ", -4);
		scoreRule.put("greater pessimism", -7);
		scoreRule.put("don’t buy", -10);
		scoreRule.put("low",-2);
		scoreRule.put("fall", -3);
		scoreRule.put("failure", -3);
		scoreRule.put("fluctuates", -2);
		scoreRule.put("sell", -2);

		double score = 0.0;
		int matchCount = matchedKeywords.size();
		for (int i = 0; i < matchCount; i++) {
			if (scoreRule.get(matchedKeywords.get(i)) != null)
				score = score + scoreRule.get(matchedKeywords.get(i));
		}
		return (score / matchCount);
	}
}