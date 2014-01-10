package com.ntvui;



import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class NewsInDetailsActivity extends SherlockFragmentActivity {
	Bundle getcount;
	int position, count;
	ArrayList<String>  heads, fulls, times,images,captions;
	NewInDetailsPageAdapter newsAdapter;
	AppFragmentAdapter mAdapter;
	ViewPager mPager;

	PageIndicator mIndicator;

	/** Enabling home button **/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_in_details);

		/** Enabling home button **/
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getcount = getIntent().getExtras();

		/** getting currently clicked item position **/
		position = getcount.getInt("id");

		/** getting number of items **/
		count = getcount.getInt("count");

		/** getting news **/
        heads = getcount.getStringArrayList("headlines");
        times = getcount.getStringArrayList("time");
        fulls = getcount.getStringArrayList("fullStory");
        images = getcount.getStringArrayList("imageUrl");
        captions = getcount.getStringArrayList("captions");
		newsAdapter = new NewInDetailsPageAdapter(getSupportFragmentManager(),heads,fulls, times, images,captions,count);
		
        /** Locating pager layout*/
		mPager = (ViewPager) findViewById(R.id.pager_newsindetails);
		 
		/**Setting the adapter for the pager layout */
		mPager.setAdapter(newsAdapter);
         
		/**Locating page indicator */
		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator_newsindetails);
		mIndicator.setViewPager(mPager);
		mIndicator.setCurrentItem(position);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.news_in_details, (com.actionbarsherlock.view.Menu) menu);
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
}