package com.example.stocksapp;

import stocks.Stock;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);
		tabHost.setup();

		companyName = getIntent().getExtras().getString("Company");
		TextView tvCompany = (TextView) findViewById(R.id.textView1);
		tvCompany.setText(companyName);
		TextView tv = (TextView) findViewById(R.id.tvCompanyName);
		int score;
		stock = utils.Controller.getStock(companyName);
		score = (int) stock.getScore();
		// Log.d("demo", ""+score);
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
				finish();
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

	}
	
	protected void search() {
		final EditText input = new EditText(this);
		final AutoCompleteTextView actv = new AutoCompleteTextView(this);
		new AlertDialog.Builder(this)
				.setTitle("Compare with " + companyName)
				.setMessage(R.string.searchCaption)
				.setView(actv).setOnKeyListener(new DialogInterface.OnKeyListener() {
					
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
						// TODO Auto-generated method stub
						
						return false;
					}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// searchResults();
						// Removing the screen with search suggestions
						String company2 = input.getText().toString();
						intent = new Intent(getBaseContext(), CompareActivity.class);
						intent.putExtra("Company1", companyName);
						intent.putExtra("Company2", company2);
						finish();
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
