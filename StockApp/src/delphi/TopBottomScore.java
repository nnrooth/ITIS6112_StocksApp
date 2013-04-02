package delphi;

public class TopBottomScore {

	/**
	 * @param args
	 */
	public double topBottomCheck(String[] top, String[] bottom, String company){
		boolean bTop = false;
		boolean bBottom = false;
		
		for(int i=0; i < top.length; i++){
			if(company.equalsIgnoreCase(top[i]))
				bTop = true;
		}
		
		for(int j=0; j < top.length; j++){
			if(company.equalsIgnoreCase(bottom[j]))
				bBottom = true;
		}		
		double topBotScore = 0.0;
		topBotScore=topBotScore(bTop, bBottom);
		return topBotScore;
		
	}
	
	public double topBotScore(boolean top, boolean bottom){
		double score = 0.0;
		if(top)
			score = 10.0;
		if(bottom)
			score = -10.0;
		return score;
	}
	
	public static void main(String[] args) {
		// TODO Remove Method
		TopBottomScore tbs = new TopBottomScore();
		String[] top={"Apple","Google", "Microsoft"};
		String[] bot={"Ducati","Food Lion","Reynolds"};
		
		System.out.println("Top bottom score: " + tbs.topBottomCheck(top, bot, "Apple"));
		

	}

}
