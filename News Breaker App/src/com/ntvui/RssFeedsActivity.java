package com.ntvui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.app.SherlockListFragment;

import com.ntvui.RSSItem;

public class RssFeedsActivity extends SherlockListActivity {
	private ArrayList<RSSItem> itemlist = null;
	private RSSListAdaptor rssadaptor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		itemlist = new ArrayList<RSSItem>();

		/** Enabling home button **/
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		new RetrieveRSSFeeds().execute();
		setContentView(R.layout.rss_feeds_list);

		new RetrieveRSSFeeds().execute();

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		RSSItem data = itemlist.get(position);

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.link));

		startActivity(intent);
	}

	private void retrieveRSSFeed(String urlToRssFeed, ArrayList<RSSItem> list) {
		try {
			URL url = new URL(urlToRssFeed);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader xmlreader = parser.getXMLReader();
			RSSParser theRssHandler = new RSSParser(list);

			xmlreader.setContentHandler(theRssHandler);

			InputSource is = new InputSource(url.openStream());

			xmlreader.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class RetrieveRSSFeeds extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progress = null;

		@Override
		protected Void doInBackground(Void... params) {
			// RSS Link for EastAfrica Newspaper
			retrieveRSSFeed(
					"http://www.theeastafrican.co.ke/-/2456/2456/-/view/asFeed/-/13blr5d/-/index.xml",
					itemlist);

			// RSS link for monitor Ug
			retrieveRSSFeed(
					"http://www.monitor.co.ug/-/691150/691150/-/view/asFeed/-/11emxavz/-/index.xml",
					itemlist);

			// RSS link for Business Daily
			retrieveRSSFeed(
					"http://www.businessdailyafrica.com/Kenya-seeks-IMF-emergency-loan-as-shocks-loom/-/539546/2136660/-/view/asFeed/-/nvyvobz/-/index.xml",
					itemlist);

			retrieveRSSFeed("http://rss.cnn.com/rss/edition.rss", itemlist);
			// RSS Link for NewVision Education
			retrieveRSSFeed("http://www.newvision.co.ug/feed.aspx?cat_id=6",
					itemlist);

			// RSS Link for EastAfrica Newspaper
			retrieveRSSFeed(
					"http://www.theeastafrican.co.ke/-/2456/2456/-/view/asFeed/-/13blr5d/-/index.xml",
					itemlist);

			retrieveRSSFeed(
					"http://www.monitor.co.ug/News/National/Sudan-President--Omar-al-Bashir-in-Juba-for/-/688334/2136014/-/11wplyoz/-/index.html",
					itemlist);
			rssadaptor = new RSSListAdaptor(RssFeedsActivity.this,
					R.layout.activity_rss_reader, itemlist);

			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			progress.dismiss();
		}

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(RssFeedsActivity.this, null,
					"Loading more stories...");

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			setListAdapter(rssadaptor);

			progress.dismiss();

			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	}

	private class RSSListAdaptor extends ArrayAdapter<RSSItem> {
		private List<RSSItem> objects = null;

		public RSSListAdaptor(Context context, int textviewid,
				List<RSSItem> objects) {
			super(context, textviewid, objects);

			this.objects = objects;
		}

		@Override
		public int getCount() {
			return ((null != objects) ? objects.size() : 0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public RSSItem getItem(int position) {
			return ((null != objects) ? objects.get(position) : null);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;

			if (null == view) {
				LayoutInflater vi = (LayoutInflater) RssFeedsActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.activity_rss_reader, null);
			}

			RSSItem data = objects.get(position);

			if (null != data) {
				TextView title = (TextView) view.findViewById(R.id.txtTitle);
				TextView date = (TextView) view.findViewById(R.id.txtDate);
				TextView description = (TextView) view
						.findViewById(R.id.txtDescription);

				title.setText(data.title);
				date.setText("on " + data.date);
				description.setText(data.description);
			}

			return view;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.news_in_details,
				(com.actionbarsherlock.view.Menu) menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub		
		super.onResume();
	}
	
}