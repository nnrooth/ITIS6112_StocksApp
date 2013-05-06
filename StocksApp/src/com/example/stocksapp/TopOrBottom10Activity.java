package com.example.stocksapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * Displays the Top10 or Bottom10 list of stocks
 * 
 * @author Team 3+4
 * 
 */
public class TopOrBottom10Activity extends Activity {
	Intent intent;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top_or_bottom10);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Loading..");
		dialog.setCancelable(false);
		dialog.show();
		
		new AsyncGetToporBottom().execute();
		
		RadioGroup rg = (RadioGroup) findViewById(R.id.rgTopOrBottom);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				dialog.show();
				new AsyncGetToporBottom().execute();
				
			}
		});

		Button btnHome = (Button) findViewById(R.id.button1);
		btnHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(getBaseContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}
	
	public class AsyncGetToporBottom extends AsyncTask<Void, Void, String[]>{

		@Override
		protected String[] doInBackground(Void... params) {
			RadioGroup rg = (RadioGroup) findViewById(R.id.rgTopOrBottom);
			int id = rg.getCheckedRadioButtonId();

			RadioButton rbTop = (RadioButton) findViewById(R.id.radio0);

			if (findViewById(id) == rbTop) {
				String[] values = utils.Controller.getTop10();
				return values;
				
			} else {				
				String[] values = utils.Controller.getBottom10();
				return values;
			}
		}
		
		@Override
		protected void onPostExecute(String[] values) {
			final ArrayList<String> list = new ArrayList<String>();
			for(int i=0; i<10; i++){
				list.add(values[i]);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(TopOrBottom10Activity.this,
					android.R.layout.simple_list_item_1, android.R.id.text1,
					list);
			ListView myList = (ListView) findViewById(R.id.lvTopOrBottom);
			myList.setAdapter(adapter);

			myList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					intent = new Intent(getBaseContext(), CompanyActivity.class);
					intent.putExtra("Company", list.get(position));
					startActivity(intent);
				}
			});
			dialog.dismiss();
			super.onPostExecute(values);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_or_bottom10, menu);
		return true;
	}

}
