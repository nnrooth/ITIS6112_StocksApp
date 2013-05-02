package categoriesAndTicker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Stock {
	public String[] getCategories() {
		FileInputStream fs = null;
		BufferedReader reader = null;
		String[] categories = null;
		try {
			// Intializing the FileInputStream to read from Categories.txt
			fs = new FileInputStream(
					"C:\\Users\\admin\\workspace\\Stock\\src\\Categories.txt");

			// Intializaing the BufferedReader to read a line
			reader = new BufferedReader(new InputStreamReader(fs));
			String line;
			categories = new String[20];
			int i = 0;

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

				// Store the categories which are listed in the 2nd column in
				// the file
				categories[i - 1] = new String(tokens[1]);
				i++;
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
		return categories;
	}

	// Function to get the Stocks for a particular catgegory
	// This pools the file Stocks.txt to list the present stocks in a particular
	// category
	public String[] getStocksForCategories(String categoryName) {
		FileInputStream fs = null;
		BufferedReader reader = null;
		String[] stocks = null;
		try {
			// Intializing the FileInputStream to read from Stocks.txt
			fs = new FileInputStream(
					"C:\\Users\\admin\\workspace\\Stock\\src\\Stocks.txt");

			// Intializaing the BufferedReader to read a line
			reader = new BufferedReader(new InputStreamReader(fs));
			String line;
			stocks = new String[20];
			int i = 0;

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

				// Store the Company Names in 1st column if categoryName matches
				// 3rd column in the file
				if (categoryName.equalsIgnoreCase(tokens[3])) {
					stocks[i - 1] = new String(tokens[1]);
					i++;
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
		return stocks;
	}
}
