package com.ntvui;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeActivity extends SherlockListActivity {
	CategoryListAdapter mMenuAdapter;
	ListView mDrawerList;
	DrawerLayout mDrawerLayout;
	 ActionBarDrawerToggle mDrawerToggle;
	String[] title;
	String[]categories=new String[]{"Top Stories","Business"};
	int[] icon;
	String[] subtitle;
	MenuItem menuItem;

	ArrayList<HashMap<String, String>> dataList;
	String message = "";
	int status = 0;

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MSG = "message";
	private static final String TAG_NEWS = "news";
	private static final String TAG_HEADLINE = "headline";
	private static final String TAG_FULL = "full";
	private static final String TAG_TIME = "time";
ListView newsList;
	// data JSONArray
	JSONArray news = null;
	
	
  
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		// Generate title
		title = new String[] { "Top Stories", "Sports",
				"Business" };

		/** Generate icons for category list **/
		icon = new int[] { R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher };
		
		//if we are on a large Screen tablet.
		if(findViewById(R.id.cat_list) != null){
		
		mMenuAdapter = new CategoryListAdapter(this, title, icon);

		ListView catList = (ListView) findViewById(R.id.cat_list);
		catList.setAdapter(mMenuAdapter);
		LoadNews_httpsync();
		
		}
		
		//if we are on a phone view 
	
		else{
			Intent i = new Intent(this, HomePhoneActivity.class);
			startActivity(i);
			finish();

		}
		
	}
	
	/** fetching news from server **/
	
	public void LoadNews_httpsync() {

		RestClient.get("ntv/getnews.php", null, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject json) {

				try {
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
							String  full = c.getString(TAG_FULL);
							String time  = c.getString(TAG_TIME);
							
							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							map.put(TAG_HEADLINE, headline);
							map.put(TAG_FULL, full);
							map.put(TAG_TIME, time);
							
							// adding HashList to ArrayList
							dataList.add(map);
						}
					} else {

						String msg = json.getString(TAG_MSG);
						message = msg;

					}
				} catch (JSONException e) {
					e.printStackTrace();

				}
				if (message.contentEquals("")) {
                    //newsList =(ListView)findViewById(R.id.big_news_list);
					ListAdapter adapter = new SimpleAdapter(
							HomeActivity.this, dataList,
							R.layout.news_list_item,
							new String[] { TAG_HEADLINE , TAG_TIME,TAG_FULL},
							new int[] { R.id.item_headline, R.id.item_time ,R.id.item_full });
					
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

				Toast.makeText(getApplicationContext(), "Some went wrong",
						Toast.LENGTH_LONG).show();

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
				Toast.makeText(getApplicationContext(), response,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	//inflating menu
	    	   getSupportMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	 
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	 
	        if (item.getItemId() == android.R.id.home) {
	 
	            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
	                mDrawerLayout.closeDrawer(mDrawerList);
	            } else {
	                mDrawerLayout.openDrawer(mDrawerList);
	            }
	        }
	        switch(item.getItemId()){
	        case R.id.compose_quick:
	        	
	        	Intent i = new Intent(HomeActivity.this,SendingNewsQuickActivity.class);
	        	startActivity(i);
	        	break;
	        	
	  case R.id.compose_normal:
	        	
	        	Intent j = new Intent(HomeActivity.this,SendStoryActivity.class);
	        	startActivity(j);
	        	break;
	        case R.id.action_refresh:        	
	        	MenuItem menuItem = item;
	        	//replace the refresh icon with a progress bar
	            menuItem.setActionView(R.layout.progressbar);
	            menuItem.expandActionView();
	           
	            break;
	       
	        
	        }
	 
	        return true;
	    }
	    
	   
	   }


	

 

