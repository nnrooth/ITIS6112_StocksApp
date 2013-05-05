package com.example.stocksapp;

import java.util.ArrayList;
import java.util.Locale;

import categoriesAndTicker.Stock;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Intent intent;
	public static final String PREFS_NAME = "MyHistoryFile";
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pDialog = new ProgressDialog(this);

		ListView myListView = (ListView) findViewById(R.id.listView1);
		String[] menuItems = { "Top/Bottom 10", "Top Pick of the Day",
				"Search", "Categories", "Settings", "History" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				menuItems);
		myListView.setAdapter(adapter);

		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					intent = new Intent(getBaseContext(),
							TopOrBottom10Activity.class);
					startActivity(intent);
				} else if (position == 1) {
					intent = new Intent(getBaseContext(),
							TopPickoftheDayActivity.class);
					startActivity(intent);
				} else if (position == 2) {
					search();
				} else if (position == 3) {
					categories();
				} else if (position == 4) {
					intent = new Intent(getBaseContext(),
							SettingsActivity.class);
					startActivity(intent);
				} else if (position == 5) {
					SharedPreferences historyFile = getSharedPreferences(
							PREFS_NAME, 0);
					int size = historyFile.getInt("History_Number", -1);
					if (size == -1) {
						Toast.makeText(MainActivity.this,
								"No history available!", Toast.LENGTH_SHORT)
								.show();
					} else {
						history();
					}
				}

			}
		});
		

		Button btnExit = (Button) findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);
			}
		});
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
				.setTitle("Search")
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
									MainActivity.this,
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
						// searchResults();
						// Removing the screen with search suggestions

//						pDialog.setMessage("Loading...");
//						pDialog.setCancelable(false);
//						pDialog.show();
						String companyName = actv.getText().toString();
						try {
							intent = new Intent(getBaseContext(),
									CompanyActivity.class);
							intent.putExtra("Company", companyName);
							startActivity(intent);
						} catch (Exception e) {
							Toast.makeText(MainActivity.this,
									"No information retrieved. Try again!",
									Toast.LENGTH_SHORT).show();
						}
						
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

	protected void categories() {
		final String[] oldCategories = Stock.getCategories();
		final String[] categories = new String[oldCategories.length];
		for(int i=0; i<oldCategories.length; i++){
			String line = oldCategories[i];
			String[] tokens = line.split(",");
			categories[i] = tokens[1];
		}
		new AlertDialog.Builder(this)
				.setTitle("Categories")
				.setItems(categories, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						categoryResults(categories[which]);
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

	protected void categoryResults(String category) {
		final String[] searchValues = Stock.getStocksForCategories(category);
		final String[] companies = new String[searchValues.length];
		final String[] stocks = new String[searchValues.length];
		for(int i=0; i<searchValues.length; i++){
			String line = searchValues[i];
			String[] tokens = line.split(",");
			companies[i] = tokens[1];
			stocks[i] = tokens[0];
		}
		new AlertDialog.Builder(this)
				.setTitle("Search Results")
				.setItems(companies, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						intent = new Intent(getBaseContext(),
								CompanyActivity.class);
						intent.putExtra("Company", stocks[which]);
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

	protected void history() {
		SharedPreferences historyFile = getSharedPreferences(PREFS_NAME, 0);
		int size = historyFile.getInt("History_Number", -1);
		final String[] historyValues = new String[size];
		for (int i = 0; i < size; i++) {
			historyValues[i] = historyFile.getString("History_" + i, null);
		}

		new AlertDialog.Builder(this)
				.setTitle("Search Results")
				.setItems(historyValues, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						intent = new Intent(getBaseContext(),
								CompanyActivity.class);
						intent.putExtra("Company", historyValues[which]);
						startActivity(intent);
					}
				})
				.setPositiveButton("Clear History",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								SharedPreferences historyFile = getSharedPreferences(
										PREFS_NAME, 0);
								SharedPreferences.Editor editor = historyFile
										.edit();
								editor.clear();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
