package com.example.stocksapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TopOrBottom10Activity extends Activity {
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top_or_bottom10);
		
		TabHost tabHost = (TabHost)findViewById(R.id.tabHost1);
		tabHost.setup();
		
		TabSpec priceSpec = tabHost.newTabSpec("Price");
		priceSpec.setIndicator("Price");
		priceSpec.setContent(R.id.tab1);
		TabSpec marketSpec = tabHost.newTabSpec("Market Cap");
		marketSpec.setIndicator("Market Cap");
		marketSpec.setContent(R.id.tab2);
		TabSpec volumeSpec = tabHost.newTabSpec("Volume");
		volumeSpec.setIndicator("Volume");
		volumeSpec.setContent(R.id.tab3);
		
		tabHost.addTab(priceSpec);
		tabHost.addTab(marketSpec);
		tabHost.addTab(volumeSpec);
		
		ListView myList = (ListView)findViewById(R.id.lvTopOrBottom);
		final String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
		myList.setAdapter(adapter);
		
		myList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				intent.putExtra("Company", values[position]);
				
			}
		});
		
		Button btnHome = (Button)findViewById(R.id.button1);
		btnHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent = new Intent(getBaseContext(), MainActivity.class);
				finish();
				startActivity(intent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_or_bottom10, menu);
		return true;
	}

}
