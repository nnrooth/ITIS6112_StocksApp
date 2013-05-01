package com.example.stocksapp;

import java.math.BigDecimal;
import java.math.RoundingMode;

import stocks.Stock;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TopPickoftheDayActivity extends Activity {
	Intent intent;
	String companyName = utils.Controller.getTop10()[0];
	Stock stock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top_pickofthe_day);

		// update the company name with the top stock of the day
		new AsyncCompanyName().execute();
		

		Button btnHome = (Button) findViewById(R.id.button1);
		btnHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(getBaseContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		Button btnCompany = (Button) findViewById(R.id.button2);
		btnCompany.setText("View " + companyName);
		btnCompany.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(getBaseContext(), CompanyActivity.class);
				intent.putExtra("Company", companyName);
				startActivity(intent);
			}
		});

	}
	
	public class AsyncCompanyName extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... arg0) {
			String[] values = delphi.Top10Expert.getTop10Regex();
			Log.d("demo", "1");
//			companyName= values[0];
//			Log.d("demo", companyName);
			return values[0];
		}
		
		@Override
		protected void onPostExecute(String result) {
			Log.d("demo", result);
			companyName = result;
			TextView tvCompany = (TextView) findViewById(R.id.textView2);
			tvCompany.setText(companyName);
			stockDetails();
			super.onPostExecute(result);
		}
		
	}

	public void stockDetails() {
		stock = utils.Controller.getStock(companyName);
		BigDecimal current = stock.getCurrentPrice();
		BigDecimal previous = stock.getPreviousClosingPrice();
		BigDecimal change = current.subtract(previous);
		Double chg = Double.valueOf(change.doubleValue());
		BigDecimal percent = BigDecimal.valueOf(chg * 100).divide(previous, 2,
				RoundingMode.CEILING);
		Double pc = Double.valueOf(percent.doubleValue());
		TextView tvCurrent = (TextView) findViewById(R.id.textView3);
		tvCurrent.setText("" + current);
		TextView tvPrevious1 = (TextView) findViewById(R.id.textView7);
		tvPrevious1.setText("" + previous);
		TextView tvChange = (TextView) findViewById(R.id.textView4);
		if (chg > 0) {
			tvChange.setText("+" + change);
			tvChange.setTextColor(Color.GREEN);
		} else {
			tvChange.setText("" + change);
			tvChange.setTextColor(Color.RED);
		}
		TextView tvPercent = (TextView) findViewById(R.id.textView5);
		if (pc > 0) {
			tvPercent.setText("+" + percent + "%");
			tvPercent.setTextColor(Color.GREEN);
		} else {
			tvPercent.setText("" + percent + "%");
			tvPercent.setTextColor(Color.RED);
		}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_pickofthe_day, menu);
		return true;
	}

}
