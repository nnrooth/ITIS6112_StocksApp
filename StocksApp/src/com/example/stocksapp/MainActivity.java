package com.example.stocksapp;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class MainActivity extends Activity {
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
					finish();
					startActivity(intent);
				} else if (position == 1) {
					intent = new Intent(getBaseContext(),
							TopPickoftheDayActivity.class);
					finish();
					startActivity(intent);
				} else if (position == 2) {
					// builderSearch.show();
					search();
				} else if (position == 3) {
					categories();
				} else if (position == 4) {
					intent = new Intent(getBaseContext(),
							SettingsActivity.class);
					startActivity(intent);
				} else if (position == 5) {
					history();
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

	private String[] getAutoSuggest(String word) {
		ArrayList<String> suggestions = new ArrayList<String>();
		String[] results;
		String[] stock_names = getResources().getStringArray(R.array.stock_names);
		
		for (int n = 0; n < stock_names.length; n++) {
			if (stock_names[n].toLowerCase(Locale.US).startsWith(word.toLowerCase(Locale.US))) {
				suggestions.add(stock_names[n]);
				Log.i("Suggestions", stock_names[n]);
			}
		}
		
		results = suggestions.toArray(new String[suggestions.size()]);
		return results;
	}
	
	protected void search() {
		//final EditText input = new EditText(this);
		final AutoCompleteTextView actv = new AutoCompleteTextView(this);
		new AlertDialog.Builder(this)
				.setTitle("Search")
				.setMessage(
						R.string.searchCaption)
				.setView(actv).setOnKeyListener(new DialogInterface.OnKeyListener() {
					
					String typeWord = "";
					
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
						if (KeyEvent.ACTION_UP == event.getAction()) {
							typeWord = actv.getText().toString();
							Log.i("TypeWord", typeWord);
						}
						
						String[] stockNames;
						if (typeWord.length() > -1) {
							stockNames = getAutoSuggest(typeWord);
							Log.i("StockNames", String.format("%s", stockNames.length));
							
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, stockNames);
							actv.setAdapter(adapter);
						}
						
						return false;
					}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
//						searchResults();
						//Removing the screen with search suggestions
						String companyName = actv.getText().toString();
						intent = new Intent(getBaseContext(),
								CompanyActivity.class);
						
						
						
						
						intent.putExtra("Company", companyName);
						
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

	protected void categories() {
		final String[] categories = { "Small Cap", "Mid Cap", "Energy", "Power", "Banking" };
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

	protected void history() {
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
