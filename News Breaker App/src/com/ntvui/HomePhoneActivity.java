package com.ntvui;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;

public class HomePhoneActivity extends SherlockListActivity implements OnItemSelectedListener {

	MenuItem menuItem;
	Spinner mySpinner;
	
	String[] heads;
	ArrayList<HashMap<String, String>> dataList;

	ArrayList<String> HeadLines = new ArrayList<String>();
	ArrayList<String> captions = new ArrayList<String>();
	ArrayList<String> fulls = new ArrayList<String>();
	ArrayList<String> times = new ArrayList<String>();
	ArrayList<String> images = new ArrayList<String>();
	String message = "";
	int status = 0;

	// private static String url_FeedStores_data =
	// "http://192.168.43.77/ProjectAgric/feed.php";
	// private static String url_FeedStores_data =
	// "http://192.168.2.169/ProjectAgric/feed.php";
	// JSON Node names
	private static final String TAG_IMAGEURL = "image";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MSG = "message";
	private static final String TAG_NEWS = "news";
	private static final String TAG_HEADLINE = "headline";
	private static final String TAG_FULL = "full";
	private static final String TAG_TIME = "time";
	private static final String TAG_CAPTION = "Caption";

	// data JSONArray
	JSONArray news = null;
	
	String[] Urls= new String[]{"ntv/gettopstories.php","ntv/getsports.php","ntv/getbusiness.php"};
	CategoryListAdapter mMenuAdapter;
	private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);

		// Hashmap for ListView
		dataList = new ArrayList<HashMap<String, String>>();
		

		LoadNews_httpsync(Urls[0]);

		// Get listview
		ListView lv = getListView();		
	 
		String[] title = new String[] { "Top Stories", "Sports",
		"Business","Others..." };

/** Generate icons for category list **/
int[] icon = new int[] { R.drawable.top_stories, R.drawable.sports,
		R.drawable.business,R.drawable.others };

mMenuAdapter = new CategoryListAdapter(this, title, icon);
mySpinner = (Spinner)findViewById(R.id.spinner1);
mySpinner.setAdapter(mMenuAdapter);
mySpinner.setOnItemSelectedListener(this);
//0n Spinner clicked

		// on seleting single store
		// launching Map Screen
SharedPreferences loginPreferences = getSharedPreferences("loginPreferences", MODE_PRIVATE);

String NAME = loginPreferences.getString("NAME"," ");
String USERNAME= loginPreferences.getString("USERNAME"," ");
String ADDRESS= loginPreferences.getString("ADDRESS"," ");

//Toast.makeText(getApplicationContext(), NAME, Toast.LENGTH_SHORT).show();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Bundle myPosition = new Bundle();
				myPosition.putInt("id", position);
				myPosition.putInt("count", news.length());
				myPosition.putStringArrayList("headlines", HeadLines);
				myPosition.putStringArrayList("fullStory", fulls);
				myPosition.putStringArrayList("time", times);
				myPosition.putStringArrayList("imageUrl", images);
				myPosition.putStringArrayList("captions", captions);
				Intent i = new Intent(HomePhoneActivity.this,
						NewsInDetailsActivity.class);
				i.putExtras(myPosition);
				startActivity(i);

			}

		});
	}

	public void LoadNews_httpsync(String url) {

		RestClient.get(url, null, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject json) {

				try {
					 TextView t = (TextView)findViewById(R.id.txtnetwork);
			          t.setText("");
						
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);
					status = 1;
					if (success == 1) {
						// data found
						// Getting Array of data
						news = json.getJSONArray(TAG_NEWS);

						// looping through All data
						for (int i = 0; i < news.length(); i++) {
							JSONObject c = news.getJSONObject(i);

							// Storing each json item in variable
							String headline = c.getString(TAG_HEADLINE);
							
							String full = c.getString(TAG_FULL);
							String time = c.getString(TAG_TIME);
							String imageUrl = c.getString(TAG_IMAGEURL);
							String caption = c.getString(TAG_CAPTION);


							/** Loading list arrays */
							HeadLines.add(new String(headline));
							fulls.add(new String(full));
							images.add(new String(imageUrl));
							times.add(new String(time));
							captions.add(new String(caption));

						}
					} else {

						String msg = json.getString(TAG_MSG);
						message = msg;

					}
				} catch (JSONException e) {
					e.printStackTrace();

				}
				if (message.contentEquals("")) {

					NewsAdapter adapter = new NewsAdapter(
							HomePhoneActivity.this, images, HeadLines, fulls,
							times);
					// updating listview
					setListAdapter(adapter);

				} else {

					Toast.makeText(getApplicationContext(), message,
							Toast.LENGTH_LONG).show();

				}

			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.loopj.android.http.JsonHttpResponseHandler#onFailure(java
			 * .lang.Throwable, org.json.JSONObject)
			 */

			@Override
			public void onFailure(Throwable arg0, JSONObject json) {
          TextView t = (TextView)findViewById(R.id.txtnetwork);
          t.setText("Ooops!. Check Internet connectivity");
			
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.loopj.android.http.AsyncHttpResponseHandler#onFailure(java
			 * .lang.Throwable, java.lang.String)
			 */
			@Override
			public void onFailure(Throwable error, String response) {
				// TODO Auto-generated method stub
				super.onFailure(error, response);
				 TextView t = (TextView)findViewById(R.id.txtnetwork);
		          t.setText("Ooops!. Check Internet connectivity");
					
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// inflating menu
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.compose_quick:

			Intent i = new Intent(HomePhoneActivity.this,
					SendingNewsQuickActivity.class);
			startActivity(i);
			break;

		case R.id.compose_normal:

			Intent j = new Intent(HomePhoneActivity.this,
					SendStoryActivity.class);
			startActivity(j);
			break;
		case R.id.action_weather:

			Intent l = new Intent(HomePhoneActivity.this, Weather.class);
			startActivity(l);
			break;
		case R.id.action_ranking:

			Intent m = new Intent(HomePhoneActivity.this, RankingActivity.class);
			startActivity(m);
			break;
		case R.id.action_refresh:
			menuItem = item;
			// replace the refresh icon with a progress bar
			menuItem.setActionView(R.layout.progressbar);
			images.clear();
			HeadLines.clear();
			fulls.clear();
		    captions.clear();
			LoadNews_httpsync("ntv/getnews.php");

			break;
		case R.id.action_jobs:
			Intent k = new Intent(HomePhoneActivity.this,
					GetSelfEmployedActivity.class);
			startActivity(k);
			break;
		case R.id.action_share:
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, "Download now");
			shareIntent.setType("text/plain");
			startActivity(/* shareIntent */Intent.createChooser(shareIntent,
					"Choose please"));
		
			break;
		case R.id.action_rss:
			Intent n = new Intent(HomePhoneActivity.this,
					RssFeedsActivity.class);
			startActivity(n);
			break;
		}

		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> position, View arg1,
			int arg2, long arg3) {

		if(arg2==3){
			Intent i = new Intent(HomePhoneActivity.this, RssFeedsActivity.class);
			startActivity(i);
			mySpinner.setAdapter(mMenuAdapter);
		}
		else{
			LoadNews_httpsync(Urls[arg2]);
			//clear arrays on reload
			images.clear();
			HeadLines.clear();
			fulls.clear();
			times.clear();
			captions.clear();
		}
		//getSelectionIndex();
			
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}