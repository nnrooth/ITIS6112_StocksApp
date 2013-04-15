package debug;

import java.util.Scanner;

import delphi.Top10Expert;

public class Test_Top10Regex {

	public static void main(String[] args) {
		
		Scanner scanIn = new Scanner(System.in);

		System.out.printf("[.] View Count: ");
		int viewCount = Integer.parseInt(scanIn.nextLine());
		
		Timer.start();
		String[] top10List = Top10Expert.getTop10Regex();
		Timer.stop();
		System.out.printf(Timer.getFormattedRunTime());
		Timer.start();
		String[] bottom10List = Top10Expert.getBottom10Regex();
		Timer.stop();
		System.out.printf(Timer.getFormattedRunTime());
		
		if (viewCount > top10List.length) {
			viewCount = top10List.length;
		} else if (viewCount > bottom10List.length){
			viewCount = bottom10List.length;
		}
		
		System.out.printf("[+] Top Stocks\tBottom Stocks\n");
		for (int n = 0; n < viewCount; n++) {
			System.out.printf("[%s]\t%s\t%s\n", (n + 1), top10List[n], bottom10List[n]);
		}
		
		scanIn.close();
	}
}
