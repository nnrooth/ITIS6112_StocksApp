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
	CharSequence[] experts = { "Google Finance", "Yahoo Finance", "Expert 3",
			"Expert 4", "Expert 5" };
	ArrayList<String> selectedExperts = new ArrayList<String>();
	String[] currenyItems = { "Dollar", "Pound", "Euro", "Rupee", "Peso" };
	String currency = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		currency = settings.getString("Currency", null);
		int size = settings.getInt("Experts_Number", -1);
		if (size == -1) {
			restoreDefaults();
		} else {
			for (int i = 0; i < size; i++) {
				selectedExperts.add(settings.getString("Expert_" + i, null));
			}
		}
		ListView myListView = (ListView) findViewById(R.id.listView1);
		String[] menuItems = { "Currency Settings",
				"Select Experts", "About" };
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
					selectExperts();
				} else if (position == 2) {
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
		new AlertDialog.Builder(this).setTitle("About")
		.setMessage("The information goes here!")
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		}).show();
	}

	protected void currencySettings() {
		int location = 0;
		for(int i=0; i<currenyItems.length; i++){
			if(currency.equals(currenyItems[i])){
				location = i;
			}
		}
		new AlertDialog.Builder(this)
				.setTitle("Currency Settings")
				.setSingleChoiceItems(currenyItems, location,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								currency = currenyItems[which].toString();

							}
						})
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences settings = getSharedPreferences(
								PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("Currency", currency);
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
		selectedExperts.add("Google Finance");
		selectedExperts.add("Yahoo Finance");
		currency = "Dollar";
	}

	protected void selectExperts() {
		boolean[] checkedItems = new boolean[5];
		for (int i = 0; i < 5; i++) {
			checkedItems[i] = false;
			for (int j = 0; j < selectedExperts.size(); j++) {
				if (experts[i].equals(selectedExperts.get(j))) {
					checkedItems[i] = true;
				}
			}
		}
		new AlertDialog.Builder(this)
				.setTitle("Select Experts")
				.setMultiChoiceItems(experts, checkedItems,
						new DialogInterface.OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								if (isChecked) {
									selectedExperts.add(experts[which]
											.toString());
								} else {
									selectedExperts.remove(experts[which]
											.toString());
								}

							}
						})
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences settings = getSharedPreferences(
								PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putInt("Experts_Number", selectedExperts.size());
						for (int i = 0; i < selectedExperts.size(); i++) {
							editor.putString("Expert_" + i,
									selectedExperts.get(i));
						}
						editor.commit();
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								selectedExperts = new ArrayList<String>();
								for(int i=0; i<experts.length; i++){
									selectedExperts.add(experts[i].toString());
								}
							}
						}).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
