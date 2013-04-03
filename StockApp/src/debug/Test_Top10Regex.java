package debug;

import java.util.Random;
import java.util.Scanner;

import delphi.Top10;

public class Test_Top10Regex {

	public static void main(String[] args) {
		
		Scanner scanIn = new Scanner(System.in);
		
		long startTime, endTime, runTime;
		
		System.out.printf("[.] View Top/Bottom Stocks [y, N]: ");
		String viewTop = scanIn.nextLine();
		if (viewTop.toLowerCase().equals("y")) {
			System.out.printf("[.] View Count: ");
			int viewCount = Integer.parseInt(scanIn.nextLine());
			
			String[] top10List = Top10.getTop10Regex();
			String[] bottom10List = Top10.getBottom10Regex();
			
			if (viewCount > top10List.length) {
				viewCount = top10List.length;
			} else if (viewCount > bottom10List.length){
				viewCount = bottom10List.length;
			}
			
			Random randy = new Random();
			
			System.out.printf("[.] Randy Max: ");
			int maxRandy = Integer.parseInt(scanIn.nextLine());
			if (maxRandy > 2500) {
				maxRandy = 2500;
			}
			
			startTime = System.currentTimeMillis();
			System.out.printf("[+] Top Stocks\tBottom Stocks\n");
			for (int n = 0; n < viewCount; n++) {
				try { Thread.sleep(randy.nextInt(maxRandy)); } catch (InterruptedException e) {} finally {
				System.out.printf("[%s]\t%s\t%s\n", (n + 1), top10List[n], bottom10List[n]); }
			}
			
			endTime = System.currentTimeMillis();
			runTime = (endTime - startTime) / 1000;
			
			System.out.printf("[+] Runtime: %s seconds\n", runTime);
		}
		
		scanIn.close();
	}
}
