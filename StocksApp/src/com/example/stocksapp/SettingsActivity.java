package com.example.stocksapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SettingsActivity extends Activity {
	public static final String PREFS_NAME = "MySettingsFile";
	ArrayList<String> selectedExperts = new ArrayList<String>();
	String[] currenyItems = { "Dollar", "Pound", "Euro", "Rupee" };
	String[] currencyCodes = { "USD", "GBP", "EUR", "INR" };
	String currency = "";
	String currencyCode = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		currency = settings.getString("Currency", null);
		currencyCode = settings.getString("CurrencyCode", null);
		if (currency == null) {
			restoreDefaults();
		}
		ListView myListView = (ListView) findViewById(R.id.listView1);
		String[] menuItems = { "Currency Settings", "About" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				menuItems);
		myListView.setAdapter(adapter);

		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					currencySettings();
				} else if (position == 1) {
					about();
				}

			}
		});

		Button btnHome = (Button) findViewById(R.id.button1);
		btnHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Button btnRestore = (Button) findViewById(R.id.button2);
		btnRestore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				restoreDefaults();

			}
		});

	}

	protected void about() {
		new AlertDialog.Builder(this)
				.setTitle("About")
				.setMessage(
						"This application is purely for home/personal use. The Stock Market Prediction application uses the well-proven Delphi Logic to predict the future of stocks. The Delphi Logic used in this application is based on News Articles related to the company, the Past Stock prices, the estimated growth of the company as well as the Top and Bottom List of stocks. We expect this application to be used by the user as a guide in predicting stocks. Any loss or profit incurred as a result of using this application, is not to be blamed on the application developers."
								+ "\n\n"
								+ "Stock Market Prediction application has been developed as a part of Software System Design and Implementaion course-project."
								+ "\n\n"
								+ "Developers:-"
								+ "\n"
								+ "Nathaneal Rooth"
								+ "\n"
								+ "Nikitha Gottam"
								+ "\n"
								+ "Vishnu Payyavula"
								+ "\n"
								+ "Apoorva Katti" + "\n" + "Sachin Hadalgi")
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub

							}
						}).show();
	}

	protected void currencySettings() {
		int location = 0;
		for (int i = 0; i < currenyItems.length; i++) {
			if (currency.equals(currenyItems[i])) {
				location = i;
			}
		}
		new AlertDialog.Builder(this)
				.setTitle("Currency Settings")
				.setSingleChoiceItems(currenyItems, location,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								currency = currenyItems[which].toString();
								currencyCode = currencyCodes[which];
							}
						})
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences settings = getSharedPreferences(
								PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("Currency", currency);
						editor.putString("CurrencyCode", currencyCode);
						editor.commit();
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

	protected void restoreDefaults() {
		currency = "Dollar";
		currencyCode = "USD";
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("Currency", currency);
		editor.putString("CurrencyCode", "USD");
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
