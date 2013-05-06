package com.example.stocksapp;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import stocks.Stock;
import utils.CurrencyConverter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import categoriesAndTicker.Ticker;

/**
 * Displays the details for a particular stock.
 * 
 * @author Team 3+4
 * 
 */
public class CompanyActivity extends Activity {
	Intent intent;
	String companyName;
	Stock stock;
	String[] scorecardItems = new String[4];
	public static final String PREFS_NAME_HISTORY = "MyHistoryFile";
	public static final String PREFS_NAME_SETTINGS = "MySettingsFile";
	int score;
	ProgressDialog dialog;
	BigDecimal rate;
	String symbol;

	Timer updateTimer;
		
	@Override
	protected void onStop() {
		super.onStop();
		
		// Kill updateTimer
		if (updateTimer != null) {
			updateTimer.cancel();
			updateTimer.purge();
		}
		updateTimer = null;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// Start updateTimer
		if (updateTimer == null) {
			updateTimer = new Timer();
			updateTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						public void run() {
							if (stock != null) {
								TextView tv = (TextView) findViewById(R.id.textView2);
								BigDecimal update = stock.updatePrice();
								// Check for currency settings change
								stockDetails();
								update = update.multiply(rate);
								update = update.divide(BigDecimal.valueOf(1.00), 2, RoundingMode.CEILING);
								
								tv.setText(symbol + update);
								Log.d("PriceUpdate", "Updated: " + update);
							}
						}
					});
				}
			}, 0, 5000);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);
		tabHost.setup();

		dialog = new ProgressDialog(this);
		dialog.setMessage("Loading...");
		dialog.setCancelable(false);
		dialog.show();

		companyName = getIntent().getExtras().getString("Company");
		saveToHistory();
		stockDetails();

		TabSpec predictionSpec = tabHost.newTabSpec("Prediction");
		predictionSpec.setIndicator("Prediction");
		predictionSpec.setContent(R.id.tab1);
		TabSpec currentSpec = tabHost.newTabSpec("Current");
		currentSpec.setIndicator("Current");
		currentSpec.setContent(R.id.tab2);
		TabSpec pastSpec = tabHost.newTabSpec("Past");
		pastSpec.setIndicator("Past");
		pastSpec.setContent(R.id.tab3);

		tabHost.addTab(predictionSpec);
		tabHost.addTab(currentSpec);
		tabHost.addTab(pastSpec);

		Button btnHome = (Button) findViewById(R.id.button1);
		btnHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(getBaseContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});

		Button btnSettings = (Button) findViewById(R.id.button2);
		btnSettings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(getBaseContext(), SettingsActivity.class);
				startActivity(intent);
			}
		});

		Button btnCompare = (Button) findViewById(R.id.button4);
		btnCompare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				search();
			}
		});

		Button btnNews = (Button) findViewById(R.id.button3);
		btnNews.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(getBaseContext(), NewsActivity.class);
				intent.putExtra("Company", companyName);
				intent.putExtra("FullName", stock.getName());
				startActivity(intent);
			}
		});

		Button btnBuy = (Button) findViewById(R.id.button5);
		btnBuy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(getBaseContext(),
						BuySellLinksActivity.class);
				startActivity(intent);
			}
		});

		Button btnScorecard = (Button) findViewById(R.id.button6);
		btnScorecard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				scorecardItems[0] = "Keyword Matching: " + stock.getKwScore();
				scorecardItems[1] = "Estimated Growth: " + stock.getEgScore();
				scorecardItems[2] = "Top/Bottom 10: " + stock.getT10Score();
				scorecardItems[3] = "% change since prev. closing: "
						+ stock.getPcScore();
				viewScorecard(score);
			}
		});
	}

	public void stockDetails() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME_SETTINGS,
				0);
		String currencyCode = settings.getString("CurrencyCode", null);
		new AsyncGetCurrencyRate().execute(currencyCode);

	}

	@SuppressLint("UseSparseArrays")
	public String randomPhrase(int score) {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(10);
		randomInt++;
		HashMap<Integer, String> map1 = new HashMap<Integer, String>();
		map1.put(1, "The prediction seems very good. Go for it.");
		map1.put(2, "High Performance. Just buy");
		map1.put(3, "Grab the deal.");
		map1.put(4, "Stop thinking. Start buying.");
		map1.put(5, "Go ahead and buy.");
		map1.put(6, "Stock of the day.");
		map1.put(7, "Business benefits are very high");
		map1.put(8, "Its time to buy!");
		map1.put(9, "Must Buy.");
		map1.put(10, "Very good choice. Go get it.");

		HashMap<Integer, String> map2 = new HashMap<Integer, String>();
		map2.put(1, "Seeming good. Should wait and watch.");
		map2.put(2, "Do not rush. Keep watching.");
		map2.put(3, "Getting better. Might be a good buy.");
		map2.put(4, "Nothing to loose.");
		map2.put(5, "Uncertain. But, not bad.");
		map2.put(6, "Try your luck.");
		map2.put(7,
				"Think before buying. Check for company news to get updates.");
		map2.put(8, "Not bad, you can think of it");
		map2.put(9, "Average performance, you can take a chance");
		map2.put(10, "Wait for some more time");

		HashMap<Integer, String> map3 = new HashMap<Integer, String>();
		map3.put(1, "Not good.");
		map3.put(2, "Wait for some more time.");
		map3.put(3, "Risky. Spend only if crazy.");
		map3.put(4, "Company not doing great.");
		map3.put(5, "Not recommended.");
		map3.put(6, "Stay away from this stock for a while now.");
		map3.put(7, "Poor performance.");
		map3.put(8, "Market is falling.");
		map3.put(9, "Wise to stay away from this completely");
		map3.put(10, "Dont make a mistake. Dont buy!");

		String phrase = "";
		if (score < -3) {
			phrase = map3.get(randomInt);
		} else if (score >= -3 && score <= 3) {
			phrase = map2.get(randomInt);
		} else if (score > 3) {
			phrase = map1.get(randomInt);
		}
		return phrase;

	}

	public void saveToHistory() {
		SharedPreferences historyFile = getSharedPreferences(
				PREFS_NAME_HISTORY, 0);
		SharedPreferences.Editor editor = historyFile.edit();
		int size = historyFile.getInt("History_Number", -1);
		if (size == -1) {
			editor.putInt("History_Number", 1);
			editor.putString("History_0", companyName);
			editor.commit();
		} else {
			int flag = 0;
			for (int i = 0; i < size; i++) {
				if (companyName.equals(historyFile.getString("History_" + i,
						null))) {
					flag = 1;
				}
			}
			if (flag == 0) {
				size++;
				editor.putInt("History_Number", size);
				editor.putString("History_" + (size - 1), companyName);
				editor.commit();
			}
		}
	}

	public void viewScorecard(int score) {
		new AlertDialog.Builder(this)
				.setTitle("Scorecard" + "\n" + "(Final Score: " + score + ")")
				.setItems(scorecardItems, null)
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {}
						}).show();
	}
	
	// Returns an array of company name's matching the word parameter
		private String[] getAutoSuggest(String word) {
			ArrayList<String> suggestions = new ArrayList<String>();
			String[] results;
			String[] stock_names = getResources().getStringArray(
					R.array.stock_names);

			for (int n = 0; n < stock_names.length; n++) {
				if (stock_names[n].toLowerCase(Locale.US).startsWith(
						word.toLowerCase(Locale.US))) {
					suggestions.add(stock_names[n]);
				}
			}

			results = suggestions.toArray(new String[suggestions.size()]);
			return results;
		}

	protected void search() {
		final AutoCompleteTextView actv = new AutoCompleteTextView(this);
		new AlertDialog.Builder(this)
				.setTitle("Compare with " + companyName)
				.setMessage(R.string.searchCaption)
				.setView(actv)
				.setOnKeyListener(new DialogInterface.OnKeyListener() {
					String typeWord = "";
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode,
							KeyEvent event) {
						String[] stockNames = null;
						if (KeyEvent.ACTION_UP == event.getAction()) {
							typeWord = actv.getText().toString();
							Log.i("TypeWord", typeWord); // Log user input as
															// info

							stockNames = getAutoSuggest(typeWord);
							Log.i("StockNames",
									String.format("%s", stockNames.length)); // Log
																				// length
																				// of
																				// matched
																				// stock
																				// name
																				// array

							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									CompanyActivity.this,
									android.R.layout.simple_dropdown_item_1line,
									stockNames);
							actv.setAdapter(adapter);
						}

						return false;
					}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String companyName = actv.getText().toString();
						String company2 = Ticker.stockTickerTickerStock(companyName);
						intent = new Intent(getBaseContext(),
								CompareActivity.class);
						intent.putExtra("Company1", companyName);
						intent.putExtra("Company2", company2);
						startActivity(intent);
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {}
						}).show();
	}

	public class AsyncPastPrices extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... arg0) {
			String[] pastPrices = null;
			List<String> listMatches = new ArrayList<String>();
			String queryUrl = String.format("http://finance.yahoo.com/q/hp?s=%s",
					companyName);
			Document doc;
			try {
				doc = Jsoup.connect(queryUrl).get();
				String parsedResponse = "";
				Elements values = doc.getElementsByAttributeValue("class", "yfnc_tabledata1");
				
				/*
				 * Parse all past prices on first page of results.
				 * The amount of avaialble historical prices is rather large,
				 * but we only need the last 25 or so. 
				 */
				for (int n = 0; n < values.size() - 4; n += 7) {
					parsedResponse = values.get(n).toString();
					parsedResponse = parsedResponse.substring(
							parsedResponse.indexOf(">") + 1,
							parsedResponse.indexOf("</td>"));
					listMatches.add(parsedResponse);

					parsedResponse = values.get(n + 4).toString();
					parsedResponse = parsedResponse.substring(
							parsedResponse.indexOf(">") + 1,
							parsedResponse.indexOf("</td>"));
					listMatches.add(parsedResponse);
				}

				// Convert the List of Strings into an array
				pastPrices = listMatches.toArray(new String[listMatches.size()]);
			} catch (IOException e) {}
			return pastPrices;
		}

		@Override
		protected void onPostExecute(String[] result) {
			ListView lv = (ListView) findViewById(R.id.listView1);
			ArrayList<String> pastPrices = new ArrayList<String>();
			for (int i = 0; i < result.length; i+=2) {
				BigDecimal past = BigDecimal.valueOf(Double.parseDouble(result[i+1]));
				past = past.multiply(rate);
				past = past.divide(BigDecimal.valueOf(1.00), 2, RoundingMode.CEILING);
				pastPrices.add(result[i]+"    "+symbol +past);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					CompanyActivity.this, android.R.layout.simple_list_item_1,
					android.R.id.text1, pastPrices);
			lv.setAdapter(adapter);
			super.onPostExecute(result);
			dialog.dismiss();
		}

	}

	public class AsyncGetCurrencyRate extends
			AsyncTask<String, Void, BigDecimal> {

		@Override
		protected BigDecimal doInBackground(String... params) {
			BigDecimal currencyRate = CurrencyConverter
					.getCurrentRate(params[0]);
			rate = currencyRate;
			symbol = CurrencyConverter.getSymbol();
			return currencyRate;
		}

		@Override
		protected void onPostExecute(BigDecimal result) {
			try {

				TextView tv = (TextView) findViewById(R.id.tvCompanyName);
				// Update stock info only if null
				if (stock == null) {
					stock = utils.Controller.getStock(companyName);
					Log.d("PriceUpdate", "Stock is no longer null");
				
					TextView tvCompany = (TextView) findViewById(R.id.textView1);
					tvCompany.setText(stock.getName());
					score = (int) stock.getScore();
					tv.setText(randomPhrase(score));
				}
				
				BigDecimal current = stock.getCurrentPrice();
				current = current.multiply(rate);
				current = current.divide(BigDecimal.valueOf(1.00), 2, RoundingMode.CEILING);
				BigDecimal previous = stock.getPreviousClosingPrice();
				previous = previous.multiply(rate);
				previous = previous.divide(BigDecimal.valueOf(1.00), 2, RoundingMode.CEILING);
				BigDecimal change = current.subtract(previous);
				Double chg = Double.valueOf(change.doubleValue());
				BigDecimal percent = BigDecimal.valueOf(chg * 100).divide(
					previous, 2, RoundingMode.CEILING);
				Double pc = Double.valueOf(percent.doubleValue());
				TextView tvCurrent = (TextView) findViewById(R.id.textView2);
				tvCurrent.setText(symbol + current);
				TextView tvPrevious1 = (TextView) findViewById(R.id.textView6);
				tvPrevious1.setText(symbol + previous);
			
				new AsyncPastPrices().execute();
				TextView tvChange = (TextView) findViewById(R.id.textView3);
				if (chg >= 0) {
					tvChange.setText("+" + change);
					tvChange.setTextColor(Color.rgb(0, 120, 0));
				} else {
					tvChange.setText("" + change);
					tvChange.setTextColor(Color.RED);
				}
				TextView tvPercent = (TextView) findViewById(R.id.textView4);
				if (pc >= 0) {
					tvPercent.setText("+" + percent + "%");
					tvPercent.setTextColor(Color.rgb(0, 120, 0));
				} else {
					tvPercent.setText("" + percent + "%");
					tvPercent.setTextColor(Color.RED);
				}
			} catch (Exception e) {
				Toast.makeText(CompanyActivity.this,
						"No information retrieved. Try again!",
						Toast.LENGTH_SHORT).show();
				finish();
			}
			super.onPostExecute(result);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.company, menu);
		return true;
	}

}
