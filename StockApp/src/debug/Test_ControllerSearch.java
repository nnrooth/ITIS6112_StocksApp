package debug;
import stocks.Stock;
import utils.Controller;


public class Test_ControllerSearch {

	public static void main(String[] args) {
		Stock stock = Controller.getStock("goog");
		System.out.printf("Score: %s", stock.getScore());
	}
}