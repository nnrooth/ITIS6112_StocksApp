package categoriesAndTicker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ticker {
	public String stockTickerTickerStock(String name) {
		FileInputStream fs = null;
		BufferedReader reader = null;
		String stockDetail = null;
		try {
			// Intializing the FileInputStream to read from Stocks.txt
			fs = new FileInputStream(
					"C:\\Users\\admin\\workspace\\Stock\\src\\Stocks.txt");

			// Intializaing the BufferedReader to read a line
			reader = new BufferedReader(new InputStreamReader(fs));
			String line;
			int i = 0;
			// boolean foundStock = false;

			// Read a line from the file opened by the FileInputStream using
			// the BufferedReader till the end of file
			while ((line = reader.readLine()) != null) {
				// skip the first line read as it contains only headers for
				// columns
				if (i == 0) {
					i++;
					continue;
				}
				// Tokenize the read line with "," as the delimiter
				String[] tokens = line.split(",");

				// Store the Company Names in 1st column if StockName matches
				// 0th column in the file
				if (name.equalsIgnoreCase(tokens[0])) {
					stockDetail = new String(tokens[1]);
					break;
				}

				// Store the Company Names in 1st column if StockName matches
				// 0th colum in the file
				if (name.equalsIgnoreCase(tokens[1])) {
					stockDetail = new String(tokens[0]);
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return stockDetail;
	}
}
