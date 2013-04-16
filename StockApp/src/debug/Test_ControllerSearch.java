package debug;
import stocks.Stock;
import utils.Controller;


public class Test_ControllerSearch {

	public static void main(String[] args) {
		int testRuns = 10; int errors = 0;
		
		System.out.printf("[*] Performing %s test runs\n\n", testRuns);
		for (int n = 0; n < testRuns; n++) {
			Stock stock = null;
			
			System.out.printf("[*] Test run %s\n", ( n + 1 ));
			
			Timer.start();
			try {
				stock = Controller.getStock(Randomizer.nextStock());
			} catch (Exception e) {
				errors++;
				e.printStackTrace();
			}
			Timer.stop();
			
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
					
			System.out.printf("%s\n", Timer.getFormattedRunTime());
		}
		System.out.printf("[+] %s errors\n", errors);
	}
}