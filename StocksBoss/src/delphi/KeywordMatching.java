package delphi;
import java.util.HashMap;
import java.util.Map;

public class KeywordMatching {

	/**
	 * @param args
	 */

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
		scoreRule.put("Reap", 7);
		scoreRule.put("sued by", -3);
		scoreRule.put("patent infringement", -5);
		scoreRule.put("pessimism", -7);
		scoreRule.put("don’t buy", -10);
		scoreRule.put("low", -2);
		scoreRule.put("Fall", -3);
		scoreRule.put("Decrease ", -4);
		scoreRule.put("failure", -3);
		scoreRule.put("fluctuates", -2);
		
		//System.out.print("FDfwgwe");
		double score = 0.0;
		for (int i = 0; i < matchedKeywords.length; i++) {
			if (scoreRule.get(matchedKeywords[i]) != null)
				score = score + scoreRule.get(matchedKeywords[i]);
		}
		return score;
	}

	/*public static void main(String[] args) {
		//System.out.print("FDfwgwe main");
		// TODO Auto-generated method stub
		// Creating map for testing
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
		scoreRule.put("Reap", 7);
		scoreRule.put("sued by", -3);
		scoreRule.put("patent infringement", -5);
		scoreRule.put("pessimism", -7);
		scoreRule.put("don’t buy", -10);
		scoreRule.put("low", -2);
		scoreRule.put("Fall", -3);
		scoreRule.put("Decrease ", -4);
		scoreRule.put("failure", -3);
		scoreRule.put("fluctuates", -2);

		// Creating String array for testing
		KeyMatch s = new KeyMatch();
		ArrayList<String> abc = new ArrayList<String>();
		abc = s.getMatchingKeywords(s.getNews());
		String[] keyMatched = new String[abc.size()];
		keyMatched = abc.toArray(keyMatched);

		// String[] keyMatched = { "strong", "rise", "decline" };

		KeywordMatching km = new KeywordMatching();

		// Calling the main methode to calculate the score
		double score = km.keyScore(keyMatched, scoreRule);
		System.out.println("Score of the matched keywords:" + score);

	}*/

}