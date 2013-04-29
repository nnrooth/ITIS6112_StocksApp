package com.example.stocksapp;

import stocks.Stock;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class CompanyActivity extends Activity {
	Intent intent;
	String companyName;
	Stock stock;
	String[] scorecardItems = { "Item1: 1", "Item2: 2", "Item3: 3" };
	public static final String PREFS_NAME = "MyHistoryFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);
		tabHost.setup();

		companyName = getIntent().getExtras().getString("Company");
		saveToHistory();
		TextView tvCompany = (TextView) findViewById(R.id.textView1);
		tvCompany.setText(companyName);
		TextView tv = (TextView) findViewById(R.id.tvCompanyName);
		int score;
		stock = utils.Controller.getStock(companyName);
		score = (int) stock.getScore();

		tv.setText(Integer.toString(score));

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
				viewScorecard();
			}
		});

	}

	public void saveToHistory() {
		SharedPreferences historyFile = getSharedPreferences(PREFS_NAME, 0);
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
				editor.putString("History_" + (size - 2), companyName);
				editor.commit();
			}
		}
	}

	public void viewScorecard() {
		new AlertDialog.Builder(this)
				.setTitle("Scorecard")
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
		final EditText input = new EditText(this);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.company, menu);
		return true;
	}

}
