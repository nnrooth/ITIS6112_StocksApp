package com.example.stocksapp;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import stocks.Stock;
import utils.CurrencyConverter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * Displays compare page for two stocks.
 * 
 * @author Team 3+4
 * 
 */
public class CompareActivity extends Activity {
	String company1;
	String company2;
	Intent intent;
	Stock stock1;
	Stock stock2;
	int score;
	String[] scorecardItems = new String[4];
	public static final String PREFS_NAME_HISTORY = "MyHistoryFile";
	public static final String PREFS_NAME_SETTINGS = "MySettingsFile";
	ProgressDialog dialog;
	BigDecimal rate;
	public String symbol;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);
		tabHost.setup();

		dialog = new ProgressDialog(this);
		dialog.setMessage("Loading...");
		dialog.setCancelable(false);
		dialog.show();

		company1 = getIntent().getExtras().getString("Company1");
		company2 = getIntent().getExtras().getString("Company2");

		saveToHistory();

		final RadioGroup rg = (RadioGroup) findViewById(R.id.rgTwoCompanies);
		final RadioButton rbCompany1 = (RadioButton) findViewById(R.id.rbCompany1);
		RadioButton rbCompany2 = (RadioButton) findViewById(R.id.rbCompany2);
		rbCompany1.setText(company1);
		rbCompany2.setText(company2);

		stock1 = utils.Controller.getStock(company1);
		stock2 = utils.Controller.getStock(company2);
		stockDetails(stock1);

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				int id = rg.getCheckedRadioButtonId();
				if (findViewById(id) == rbCompany1) {
					stockDetails(stock1);
				} else {
					stockDetails(stock2);
				}
			}
		});

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

		Button btnBuy = (Button) findViewById(R.id.button5);
		btnBuy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent = new Intent(getBaseContext(),
						BuySellLinksActivity.class);
				startActivity(intent);
			}
		});

		Button btnNews = (Button) findViewById(R.id.button3);
		btnNews.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int id = rg.getCheckedRadioButtonId();
				if (findViewById(id) == rbCompany1) {
					intent = new Intent(getBaseContext(), NewsActivity.class);
					intent.putExtra("Company", company1);
					startActivity(intent);
				} else {
					intent = new Intent(getBaseContext(), NewsActivity.class);
					intent.putExtra("Company", company2);
					startActivity(intent);
				}
			}
		});

		Button btnViewScoreCard = (Button) findViewById(R.id.button6);
		btnViewScoreCard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int id = rg.getCheckedRadioButtonId();
				if (findViewById(id) == rbCompany1) {
					scorecardItems[0] = "Keyword Matching: "
							+ stock1.getKwScore();
					scorecardItems[1] = "Estimated Growth: "
							+ stock1.getEgScore();
					scorecardItems[2] = "Top/Bottom 10: "
							+ stock1.getT10Score();
					scorecardItems[3] = "% change since prev. closing: "
							+ stock1.getPcScore();
					viewScorecard();
				} else {
					scorecardItems[0] = "Keyword Matching: "
							+ stock2.getKwScore();
					scorecardItems[1] = "Estimated Growth: "
							+ stock2.getEgScore();
					scorecardItems[2] = "Top/Bottom 10: "
							+ stock2.getT10Score();
					scorecardItems[3] = "% change since prev. closing: "
							+ stock2.getPcScore();
					viewScorecard();
				}
			}
		});

	}

	public void saveToHistory() {
		SharedPreferences historyFile = getSharedPreferences(
				PREFS_NAME_HISTORY, 0);
		SharedPreferences.Editor editor = historyFile.edit();
		int size = historyFile.getInt("History_Number", -1);
		if (size == -1) {
			editor.putInt("History_Number", 1);
			editor.putString("History_0", company2);
			editor.commit();
		} else {
			int flag = 0;
			for (int i = 0; i < size; i++) {
				if (company2
						.equals(historyFile.getString("History_" + i, null))) {
					flag = 1;
				}
			}
			if (flag == 0) {
				size++;
				editor.putInt("History_Number", size);
				editor.putString("History_" + (size - 1), company2);
				editor.commit();
			}
		}
	}

	public void viewScorecard() {
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

	public void stockDetails(Stock stock) {
		new AsyncGetCurrencyRate().execute(stock);
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
	
	public class AsyncPastPrices extends AsyncTask<Stock, Void, String[]> {

		@Override
		protected String[] doInBackground(Stock... arg0) {
			String[] pastPrices = null;
			List<String> listMatches = new ArrayList<String>();
			String queryUrl = String.format("http://finance.yahoo.com/q/hp?s=%s",
					arg0[0].getSymbol());
			Document doc;
			try {
				doc = Jsoup.connect(queryUrl).get();
				String parsedResponse = "";
				Elements values = doc.getElementsByAttributeValue("class", "yfnc_tabledata1");
				
				/**
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
				pastPrices.add(result[i]+"    "+ symbol +past);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					CompareActivity.this, android.R.layout.simple_list_item_1,
					android.R.id.text1, pastPrices);
			lv.setAdapter(adapter);
			super.onPostExecute(result);
			dialog.dismiss();
		}

	}

	public class AsyncGetCurrencyRate extends
			AsyncTask<Stock, Void, BigDecimal> {
		Stock stock;

		@Override
		protected BigDecimal doInBackground(Stock... params) {
			stock = params[0];
			BigDecimal currencyRate = CurrencyConverter.getCurrentRate(stock
					.getSymbol());
			rate = currencyRate;
			symbol = CurrencyConverter.getSymbol();
			return currencyRate;
		}

		@Override
		protected void onPostExecute(BigDecimal result) {
			try {
				score = (int) stock.getScore();
				TextView tvScore = (TextView) findViewById(R.id.tvCompanyName);
				tvScore.setText(randomPhrase(score));
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
				TextView tvChange = (TextView) findViewById(R.id.textView3);
				if (chg > 0) {
					tvChange.setText("+" + change);
					tvChange.setTextColor(Color.rgb(0, 120, 0));
				} else {
					tvChange.setText("" + change);
					tvChange.setTextColor(Color.RED);
				}
				TextView tvPercent = (TextView) findViewById(R.id.textView4);
				if (pc > 0) {
					tvPercent.setText("+" + percent + "%");
					tvPercent.setTextColor(Color.rgb(0, 120, 0));
				} else {
					tvPercent.setText("" + percent + "%");
					tvPercent.setTextColor(Color.RED);
				}
			} catch (Exception e) {
				Toast.makeText(CompareActivity.this,
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
		getMenuInflater().inflate(R.menu.compare, menu);
		return true;
	}

}
