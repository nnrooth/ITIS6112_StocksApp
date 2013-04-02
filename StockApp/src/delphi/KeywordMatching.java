package delphi;
import java.util.HashMap;
import java.util.Map;

public class KeywordMatching {

	public double keyScore(String[] matchedKeywords) {
		
		Map<String, Integer> scoreRule = new HashMap<String, Integer>(20);
		scoreRule.put("strong", 10);
		scoreRule.put("attractive", 7);
		scoreRule.put("rise", 4);
		scoreRule.put("harmful", -5);
		scoreRule.put("complaints", -2);
		scoreRule.put("decline", -7);
		scoreRule.put("gain", 5);
		scoreRule.put("strength", 2);
		scoreRule.put("optimism", 4);
		scoreRule.put("boost", 3);
		scoreRule.put("bolster", 2);
		scoreRule.put("safe", 3);
		scoreRule.put("durable", 5);
		scoreRule.put("profit", 4);
		scoreRule.put("profitable", 4);
		scoreRule.put("high", 3);
		scoreRule.put("increase", 3);
		scoreRule.put("speculation", 2);
		scoreRule.put("Successful", 5);
		scoreRule.put("reap", 7);
		scoreRule.put("sued by", -3);
		scoreRule.put("patent infringement", -5);
		scoreRule.put("pessimism", -7);
		scoreRule.put("don’t buy", -10);
		scoreRule.put("low", -2);
		scoreRule.put("fall", -3);
		scoreRule.put("decrease ", -4);
		scoreRule.put("failure", -3);
		scoreRule.put("fluctuates", -2);

		double score = 0.0;
		int matchCount = matchedKeywords.length;
		for (int i = 0; i < matchCount; i++) {
			if (scoreRule.get(matchedKeywords[i]) != null)
				score = score + scoreRule.get(matchedKeywords[i]);
		}
		return (score / matchCount);
	}
}