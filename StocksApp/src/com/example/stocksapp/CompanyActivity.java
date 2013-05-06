package com.example.stocksapp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import stocks.PastClosingPrices;
import stocks.Stock;
import utils.CurrencyConverter;
import utils.AsyncTaskEx;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

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
		if (stock != null) {
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						public void run() {
							TextView tv = (TextView) findViewById(R.id.textView2);
							tv.setText(stock.updatePrice() + "");
						}
					});
				}
			}, 0, 5000);
		}

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
		map1.put(10, "Very good choice. Go got it.");

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
									int which) {
								// TODO Auto-generated method stub

							}
						}).show();
	}

	protected void search() {
		// final EditText input = new EditText(this);
		final AutoCompleteTextView actv = new AutoCompleteTextView(this);
		new AlertDialog.Builder(this)
				.setTitle("Compare with " + companyName)
				.setMessage(R.string.searchCaption)
				.setView(actv)
				.setOnKeyListener(new DialogInterface.OnKeyListener() {

					@Override
					public boolean onKey(DialogInterface dialog, int keyCode,
							KeyEvent event) {
						// TODO Auto-generated method stub

						return false;
					}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// searchResults();
						// Removing the screen with search suggestions
						String company2 = actv.getText().toString();
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
									int which) {
								// TODO Auto-generated method stub

							}
						}).show();
	}

	protected void searchResults() {
		final String[] searchValues = { "Microsoft", "Google", "Yahoo" };
		new AlertDialog.Builder(this)
				.setTitle("Search Results")
				.setItems(searchValues, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						intent = new Intent(getBaseContext(),
								CompanyActivity.class);
						intent.putExtra("Company", searchValues[which]);
						startActivity(intent);
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						}).show();
	}

	public class AsyncPastPrices extends AsyncTaskEx<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... arg0) {
			String[] pastPrices = null;
			try {
				pastPrices = PastClosingPrices.fetch(companyName);

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pastPrices;
		}

		@Override
		protected void onPostExecute(String[] result) {
			ListView lv = (ListView) findViewById(R.id.listView1);
			ArrayList<String> pastPrices = new ArrayList<String>();
			for (int i = 0; i < result.length; i++) {
				pastPrices.add(result[i]);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					CompanyActivity.this, android.R.layout.simple_list_item_1,
					android.R.id.text1, pastPrices);
			lv.setAdapter(adapter);
			super.onPostExecute(result);
		}

	}

	public class AsyncGetCurrencyRate extends
			AsyncTaskEx<String, Void, BigDecimal> {

		@Override
		protected BigDecimal doInBackground(String... params) {
			BigDecimal currencyRate = CurrencyConverter
					.getCurrentRate(params[0]);
			;
			rate = currencyRate;
			stock = utils.Controller.getStock(companyName);
			return currencyRate;
		}

		@Override
		protected void onPostExecute(BigDecimal result) {
			try {

				TextView tv = (TextView) findViewById(R.id.tvCompanyName);
				
				TextView tvCompany = (TextView) findViewById(R.id.textView1);
				tvCompany.setText(stock.getName());
				score = (int) stock.getScore();
				tv.setText(randomPhrase(score));
				BigDecimal current = stock.getCurrentPrice();
				current = current.multiply(rate);
				BigDecimal previous = stock.getPreviousClosingPrice();
				previous = previous.multiply(rate);
				BigDecimal change = current.subtract(previous);
				Double chg = Double.valueOf(change.doubleValue());
				BigDecimal percent = BigDecimal.valueOf(chg * 100).divide(
						previous, 2, RoundingMode.CEILING);
				Double pc = Double.valueOf(percent.doubleValue());
				TextView tvCurrent = (TextView) findViewById(R.id.textView2);
				tvCurrent.setText("" + current);
				TextView tvPrevious1 = (TextView) findViewById(R.id.textView6);
				tvPrevious1.setText("" + previous);
				// TextView tvPrevious2 = (TextView)
				// findViewById(R.id.textView8);
				// tvPrevious2.setText("" + previous);
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
				dialog.dismiss();
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
