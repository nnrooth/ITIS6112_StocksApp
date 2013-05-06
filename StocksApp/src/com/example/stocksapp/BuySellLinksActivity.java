package com.example.stocksapp;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Displays the links to buy and sell stocks.
 * 
 * @author Team 3+4
 * 
 */
public class BuySellLinksActivity extends Activity {
	public static final String PREFS_NAME = "MyLinksFile";
	Intent intent;
	ArrayList<String> links;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_sell_links);
		Button btnHome = (Button) findViewById(R.id.button1);
		links = new ArrayList<String>();
		SharedPreferences linksFile = getSharedPreferences(PREFS_NAME, 0);
		int size = linksFile.getInt("Links_Number", -1);
		if (size == -1) {
			restoreDefaults();
		} else {
			for (int i = 0; i < size; i++) {
				links.add(linksFile.getString("Link_" + i, null));
			}
		}

		loadListView();

		btnHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(getBaseContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		Button btnAddNewLink = (Button) findViewById(R.id.button2);
		btnAddNewLink.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addNewLink();
			}
		});

		Button btnRemoveLink = (Button) findViewById(R.id.button3);
		btnRemoveLink.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				removeLink();
			}
		});

		Button btnRestore = (Button) findViewById(R.id.button4);
		btnRestore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				restoreDefaults();
			}
		});
	}
	
	public void loadListView(){
		ListView listView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, links);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				intent = new Intent(Intent.ACTION_VIEW, Uri.parse(links
						.get(position)));
				startActivity(intent);

			}
		});
	}

	public void addNewLink() {
		final EditText input = new EditText(this);
		new AlertDialog.Builder(this)
				.setTitle("Add New Link")
				.setMessage("Please enter the new link below:")
				.setView(input)
				.setPositiveButton("Save",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String link = input.getText().toString();
								links.add(link);
								SharedPreferences linksFile = getSharedPreferences(
										PREFS_NAME, 0);
								SharedPreferences.Editor editor = linksFile
										.edit();
								editor.putInt("Links_Number", links.size());
								for (int i = 0; i < links.size(); i++) {
									editor.putString("Link_" + i, links.get(i));
								}
								editor.commit();
								loadListView();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {}
						}).show();
	}

	public void removeLink() {
		final CharSequence[] items = new CharSequence[links.size()];
		for (int i = 0; i < links.size(); i++) {
			items[i] = links.get(i);
		}
		new AlertDialog.Builder(this)
				.setTitle("Remove Links")
				.setMultiChoiceItems(items, null,
						new DialogInterface.OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								if (isChecked) {
									links.remove(items[which].toString());
								} else {
									links.add(items[which].toString());
								}

							}
						})
				.setPositiveButton("Remove",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								SharedPreferences linksFile = getSharedPreferences(
										PREFS_NAME, 0);
								SharedPreferences.Editor editor = linksFile
										.edit();
								editor.putInt("Links_Number", links.size());
								for (int i = 0; i < links.size(); i++) {
									editor.putString("Link_" + i, links.get(i));
								}
								editor.commit();
								loadListView();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								links = new ArrayList<String>();
								for (int i = 0; i < items.length; i++) {
									links.add(items[i].toString());
								}
							}
						}).show();
	}

	public void restoreDefaults() {
		links = new ArrayList<String>();
		links.add("http://finance.google.com");
		links.add("http://pfinance.yahoo.com");
		links.add("http://www.etrade.com");
		SharedPreferences linksFile = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = linksFile.edit();
		editor.putInt("Links_Number", links.size());
		for (int i = 0; i < links.size(); i++) {
			editor.putString("Link_" + i, links.get(i));
		}
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buy_sell_links, menu);
		return true;
	}

}
