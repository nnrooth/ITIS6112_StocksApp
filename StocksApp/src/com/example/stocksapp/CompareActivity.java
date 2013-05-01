package com.example.stocksapp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Random;

import stocks.Stock;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class CompareActivity extends Activity {
	String company1;
	String company2;
	Intent intent;
	Stock stock1;
	Stock stock2;
	int score;
	String[] scorecardItems = new String[4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);
		tabHost.setup();

		company1 = getIntent().getExtras().getString("Company1");
		company2 = getIntent().getExtras().getString("Company2");

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

	public void viewScorecard() {
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

	public void stockDetails(Stock stock) {
		score = (int) stock.getScore();
		TextView tvScore = (TextView) findViewById(R.id.tvCompanyName);
		tvScore.setText(randomPhrase(score));
		BigDecimal current = stock.getCurrentPrice();
		BigDecimal previous = stock.getPreviousClosingPrice();
		BigDecimal change = current.subtract(previous);
		Double chg = Double.valueOf(change.doubleValue());
		BigDecimal percent = BigDecimal.valueOf(chg * 100).divide(previous, 2,
				RoundingMode.CEILING);
		Double pc = Double.valueOf(percent.doubleValue());
		TextView tvCurrent = (TextView) findViewById(R.id.textView2);
		tvCurrent.setText("" + current);
		TextView tvPrevious1 = (TextView) findViewById(R.id.textView6);
		tvPrevious1.setText("" + previous);
		TextView tvPrevious2 = (TextView) findViewById(R.id.textView8);
		tvPrevious2.setText("" + previous);
		TextView tvChange = (TextView) findViewById(R.id.textView3);
		if (chg > 0) {
			tvChange.setText("+" + change);
			tvChange.setTextColor(Color.GREEN);
		} else {
			tvChange.setText("" + change);
			tvChange.setTextColor(Color.RED);
		}
		TextView tvPercent = (TextView) findViewById(R.id.textView4);
		if (pc > 0) {
			tvPercent.setText("+" + percent + "%");
			tvPercent.setTextColor(Color.GREEN);
		} else {
			tvPercent.setText("" + percent + "%");
			tvPercent.setTextColor(Color.RED);
		}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compare, menu);
		return true;
	}

}
