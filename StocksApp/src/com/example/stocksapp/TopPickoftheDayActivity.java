package com.example.stocksapp;

import java.math.BigDecimal;
import java.math.RoundingMode;

import stocks.Stock;
import utils.CurrencyConverter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Displays the Top Stock of the day
 * 
 * @author Team 3+4
 * 
 */
public class TopPickoftheDayActivity extends Activity {
	private static final String TAG = "TopPick";
	
	Intent intent;
	String companyName = utils.Controller.getTop10()[0];
	Stock stock;
	public BigDecimal rate;
	public String symbol;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top_pickofthe_day);
		
		dialog = new ProgressDialog(this);
		dialog.setCancelable(false);
		dialog.setMessage("Loading...");
		dialog.show();

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
			String[] values = utils.Controller.getTop10();
			Log.d("demo", "1");
			return values[0];
		}
		
		@Override
		protected void onPostExecute(String result) {
			Log.d(TAG, result);
			companyName = result;
			TextView tvCompany = (TextView) findViewById(R.id.textView2);
			tvCompany.setText(companyName);
			stockDetails();
			super.onPostExecute(result);
		}
		
	}

	public void stockDetails() {
		stock = utils.Controller.getStock(companyName);
		new AsyncCurrencyRate().execute();
	}
	
	public class AsyncCurrencyRate extends AsyncTask<Void, Void, BigDecimal>{

		@Override
		protected BigDecimal doInBackground(Void... params) {
			BigDecimal currencyRate = CurrencyConverter.getCurrentRate(stock
					.getSymbol());
			rate = currencyRate;
			symbol = CurrencyConverter.getSymbol();
			return currencyRate;
		}

		@Override
		protected void onPostExecute(BigDecimal result) {
			BigDecimal current = stock.getCurrentPrice();
			current = current.multiply(rate);
			current = current.divide(BigDecimal.valueOf(1.00), 2, RoundingMode.CEILING);
			BigDecimal previous = stock.getPreviousClosingPrice();
			previous = previous.multiply(rate);
			previous = previous.divide(BigDecimal.valueOf(1.00), 2, RoundingMode.CEILING);
			BigDecimal change = current.subtract(previous);
			Double chg = Double.valueOf(change.doubleValue());
			BigDecimal percent = BigDecimal.valueOf(chg * 100).divide(previous, 2,
					RoundingMode.CEILING);
			Double pc = Double.valueOf(percent.doubleValue());
			TextView tvCurrent = (TextView) findViewById(R.id.textView3);
			tvCurrent.setText(symbol + current);
			TextView tvPrevious1 = (TextView) findViewById(R.id.textView7);
			tvPrevious1.setText(symbol + previous);
			TextView tvChange = (TextView) findViewById(R.id.textView4);
			if (chg > 0) {
				tvChange.setText("+" + change);
				tvChange.setTextColor(Color.rgb(0, 120, 0));
			} else {
				tvChange.setText("" + change);
				tvChange.setTextColor(Color.rgb(0, 120, 0));
			}
			TextView tvPercent = (TextView) findViewById(R.id.textView5);
			if (pc > 0) {
				tvPercent.setText("+" + percent + "%");
				tvPercent.setTextColor(Color.GREEN);
			} else {
				tvPercent.setText("" + percent + "%");
				tvPercent.setTextColor(Color.RED);
			}
			dialog.dismiss();
			super.onPostExecute(result);
		}
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_pickofthe_day, menu);
		return true;
	}
}
