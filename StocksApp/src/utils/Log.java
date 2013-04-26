package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

public class Log extends Activity {

	private String FILE_NAME = "log.txt";

	// private final String FILE_NAME= "C:\\Users\\NNrooth\\Desktop\\Log.txt";
	// Checking if Log file exists
//	public boolean CheckFileExists() {
//		File file = new File(FILE_NAME);
//		if (file.exists())
//			return true;
//		return false;
//	}

	// Creating a new Log.txt file
	// public void createFile() {
	// File newfile = new File(FILE_NAME);
	// android.util.Log.e("Log File", FILE_NAME);
	// try {
	// newfile.createNewFile();
	// android.util.Log.i("Log File", "Log.txt created.");
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// //e.printStackTrace();
	// android.util.Log.i("Log File", "Could not create a new file.");
	// System.exit(1);
	// }
	// }

	// Getting History from the Log.txt file

	public ArrayList<String> getHistoryFromLog() {

		ArrayList<String> historyList = new ArrayList<String>();

		String[] history = new String[10]; // Contains History along with the
											// tag 'History'
		BufferedReader br = null;
		String content = null;

		Log log = new Log();
		// if (!log.CheckFileExists())
		// log.createFile();
		try {
			br = new BufferedReader(new FileReader(FILE_NAME));
			while ((content = br.readLine()) != null) {
				if (content.subSequence(0, 7).equals("History")) {
					history = content.split("\\|");
				}
				for (int i = 1; i < history.length; i++) {
					historyList.add(history[i]);
					// top10History[i - 1] = history[i];
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		// return top10History;
		return historyList;
	}

	// Updating the History String array
	public ArrayList<String> updateHistory(String stockName,
			ArrayList<String> history) {
		ArrayList<String> updatedHistory = new ArrayList<String>();
		int indexOfMatchedStockName = 100;
		for (int i = 0; i < history.size(); i++) {
			try {
				if (history != null
						&& history.get(i).equalsIgnoreCase(stockName)) {
					updatedHistory.add(0, history.get(i));
					indexOfMatchedStockName = i;
					break;
				}
			} catch (NullPointerException noe) {
				System.out
						.println("File has been modified. Unable to extract History details.");
				System.exit(1);
			}
		}

		// Stock Name is not in the history. A new entry has to be added and the
		// last entry has to be deleted.
		int newSize = history.size();
		if (indexOfMatchedStockName == 100) {
			updatedHistory.add(0, stockName);
			if (history.size() < 10)
				newSize = history.size() + 1;
			else
				newSize = 10;
			for (int i = 1; i < newSize; i++)
				updatedHistory.add(i, history.get(i - 1));
		}

		// This is WORKING FINE.
		// Stock Name already exists in the list, and the list should now be
		// rearranged.
		else if (indexOfMatchedStockName != 0) {
			int j = 0;
			for (int i = 1; i < history.size(); i++, j++)
				if ((i - 1) != indexOfMatchedStockName)
					updatedHistory.add(i, history.get(j));
				else {
					j++;
					updatedHistory.add(i, history.get(j));
				}
		}
		// Stock Name is in the list, and is already at the top of the list
		else {
			updatedHistory = history;
		}
		return updatedHistory;
	}

	// Updating the log file with the new History
	public void updatingLog(ArrayList<String> history) {
		Log lg = new Log();
		// StringBuffer sb = new StringBuffer("History");
		// FileWriter fw = null;
		// BufferedWriter bw = null;
		String text = "Hi";
		FileOutputStream out = null;
		try {
			android.util.Log.i("Log File", "File Creation Start");
			new File(FILE_NAME).createNewFile();
			android.util.Log.i("Log File", "File Created");
			out = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			android.util.Log.i("Log File", "Output Stream Created");
			OutputStreamWriter writer = new OutputStreamWriter(out);
			android.util.Log.i("Log File", "Output Writer Created");

			// try {
			// fw = new FileWriter(FILE_NAME);
			// bw = new BufferedWriter(fw);
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			// If file already exists

			// if (lg.CheckFileExists()) {

			for (int i = 0; i < history.size(); i++) {
				android.util.Log.i("Log File", "Writing: " + history.get(i));
				writer.write("|" + history.get(i));
				android.util.Log.i("Log File", "Written: " + history.get(i));
			}

			writer.flush();
			android.util.Log.i("Log File", "Flushed");
			writer.close();
			android.util.Log.i("Log File", "Write Successful");

			// try {
			// bw.write(sb.toString());
			// System.out.println(FILE_NAME + " updated.");
			// bw.close();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
		} catch (Exception e) {
			android.util.Log.e("Log File", "Error Writing Log");
			android.util.Log.e("Log File", e.getLocalizedMessage());
		}
		// If no Log.txt exists, create one and update it.
		// else {
		// lg.createFile();
		// for (int i = 0; i < history.size(); i++)
		// sb.append("|" + history.get(i));
		// try {
		// bw.write(sb.toString());
		// System.out.println(FILE_NAME + " updated.");
		// bw.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

	// This method makes calls to all other methods.
	public void logger(String stockName) {
		Log l = new Log();
//		if (!l.CheckFileExists())
			// l.createFile();
		l.updatingLog(l.updateHistory(stockName, l.getHistoryFromLog()));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Log l = new Log();
		// String FILE_NAME = "C:\\Users\\Sachin\\Desktop\\Log.txt";

		/*
		 * ************************* For testing purposes
		 * *************************** ArrayList<String> his =
		 * l.getHistoryFromLog(FILE_NAME); for (int i = 0; i < his.size(); i++)
		 * System.out.println(his.get(i)); his = l.updateHistory("Lenevo", his);
		 * System.out.println("\n"); for (int i = 0; i < his.size(); i++)
		 * System.out.println(his.get(i)); l.updatingLog(his, FILE_NAME);
		 * *********
		 * *******************************************************************
		 */

		// Call this function to execute the whole History features after
		// viewing a stock
		l.logger("Charlotte Inc.");

		// Call this function when the Application is initially started to
		// extract the existing history from the log file.
		// ArrayList<String> dummy = new ArrayList<String>();
		// dummy = l.getHistoryFromLog(FILE_NAME);
		// if (dummy.size() ==0)
		// System.out.println("No records in the history.");
		// else{
		// for(int i=0; i<dummy.size();i++)
		// System.out.println(dummy.get(i));
		// }

	}

}
