package com.example.stocksapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class NewsActivity extends Activity {
	Intent intent;
	ArrayList<String> news_title;
	ArrayList<String> news_link;
	String companyName;
	ProgressDialog progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		
		progress = new ProgressDialog(this);
		progress.setMessage("Loading News...");
		progress.setCancelable(false);
		progress.show();
		
		companyName = getIntent().getExtras().getString("Company");
		TextView tvCompany = (TextView)findViewById(R.id.textView1);
		tvCompany.setText(companyName);
		
		new AsyncGetNews().execute();

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

//	public void getNews() {
//		// ArrayList<String> text = new ArrayList<String>();
//		try {
//			Connection.Response response = Jsoup.connect(
//					"https://www.google.com/finance/company_news?q=GOOG")
//					.execute();
//			if (response.statusCode() == 200) {
//				Document doc = Jsoup.connect(
//						"https://www.google.com/finance/company_news?q=CSCO")
//						.get();
//				Elements abc = doc.getElementsByClass("name");
//				Elements content = doc.getElementsByAttributeValueStarting(
//						"id", "Article");
//				news_title = new ArrayList<String>();
//				news_link = new ArrayList<String>();
//				ArrayList<String> news_content = new ArrayList<String>();
//				for (int i = 0; i < abc.size(); i++) {
//					news_title.add(abc.get(i).text());
//					news_link.add(abc.get(i).getElementsByAttribute("href")
//							.attr("href").toString());
//					news_content.add(content.get(i + 1).text());
//					System.out.println(news_title.get(i) + " "
//							+ news_link.get(i) + " " + news_content.get(i)
//							+ "\n\n\n");
//				}
//			} else {
//				getNews();
//			}
//		}
//
//		catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public class AsyncGetNews extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				Connection.Response response = Jsoup.connect(
						"https://www.google.com/finance/company_news?q="+companyName)
						.execute();
				if (response.statusCode() == 200) {
					Document doc = Jsoup.connect(
							"https://www.google.com/finance/company_news?q="+companyName)
							.get();
					Elements abc = doc.getElementsByClass("name");
					Elements content = doc.getElementsByAttributeValueStarting(
							"id", "Article");
					news_title = new ArrayList<String>();
					news_link = new ArrayList<String>();
					ArrayList<String> news_content = new ArrayList<String>();
					for (int i = 0; i < abc.size(); i++) {
						news_title.add(abc.get(i).text());
						news_link.add(abc.get(i).getElementsByAttribute("href")
								.attr("href").toString());
						news_content.add(content.get(i + 1).text());
						System.out.println(news_title.get(i) + " "
								+ news_link.get(i) + " " + news_content.get(i)
								+ "\n\n\n");
					}
				}
			}

			catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			ListView listView = (ListView) findViewById(R.id.listView1);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewsActivity.this,
					android.R.layout.simple_list_item_1, android.R.id.text1,
					news_title);
			listView.setAdapter(adapter);
			progress.dismiss();
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news_link
							.get(position)));
					startActivity(intent);
				}
			});
			super.onPostExecute(result);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}

}
