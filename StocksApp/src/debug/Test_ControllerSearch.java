package debug;
import stocks.Stock;
import utils.Controller;


public class Test_ControllerSearch {

	public static void main(String[] args) {
		int testRuns = 20; int errors = 0;
		Timer bigTimer = new Timer();
		Timer littleTimer = new Timer();
		String symbol;
		
		System.out.printf("[*] Performing %s test runs\n\n", testRuns);
		bigTimer.start();
		for (int n = 0; n < testRuns; n++) {
			Stock stock = null;
			symbol = Randomizer.nextStock();

			System.out.printf("[*] Test run %s\t:\t%s\n", ( n + 1 ), symbol);
			
			littleTimer.start();
			try {
				stock = Controller.getStock(symbol);
			} catch (Exception e) {
				errors++;
				//e.printStackTrace();
			}
			littleTimer.stop();
			
			if (stock != null) {
				System.out.printf("[+] KW Score: %s\n", stock.getKwScore());
				System.out.printf("[+] T10 Score: %s\n", stock.getT10Score());
				System.out.printf("[+] EG Score: %s\n", stock.getEgScore());
				System.out.printf("[+] PC Score: %s\n", stock.getPcScore());
				
				System.out.printf("[+] Final Score: %s\n", stock.getScore());
				System.out.printf("[+] Current Price: %s\n", stock.getCurrentPrice());
			} else {
				System.out.printf("[-] No Results\n");
			}
					
			System.out.printf("%s\n", littleTimer.getFormattedRunTime());
		}
		bigTimer.stop();
		System.out.printf("[+] %s errors\n", errors);
		long totalMillis = bigTimer.getRunTime();
		long runTimeMillis = totalMillis % 1000;
		long runTimeSecs = totalMillis / 1000;
		System.out.printf("[+] Average runtime of %s seconds %s millis\n", (runTimeSecs / testRuns), (runTimeMillis / testRuns));
	}
}