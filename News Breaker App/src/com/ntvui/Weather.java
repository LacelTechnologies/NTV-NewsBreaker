package com.ntvui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockListActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Weather extends SherlockListActivity {

	// my arrays..
	ArrayList<String> day = new ArrayList<String>();
	ArrayList<String> minTemp = new ArrayList<String>();
	ArrayList<String> maxTemp = new ArrayList<String>();
	ArrayList<String> icons = new ArrayList<String>();
	ArrayList<String> desc = new ArrayList<String>();
	ArrayList<String> dayTemp = new ArrayList<String>();

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	private static String TAG_LIST = "list";
	private static String TAG_MSG = "message";
	private static String TAG_LIST_DT = "dt";
	private static String TAG_LIST_TEMP = "temp";
	private static String TAG_LIST_TEMP_DAY = "day";
	private static String TAG_LIST_TEMP_MIN = "min";
	private static String TAG_LIST_TEMP_MAX = "max";
	private static String TAG_LIST_TEMP_NIGHT = "night";
	private static String TAG_LIST_TEMP_EVE = "eve";
	private static String TAG_LIST_TEMP_MORN = "morn";

	private static String TAG_LIST_PRESSURE = "pressure";
	private static String TAG_LIST_HUMIDITY = "humidity";

	private static String TAG_LIST_WEATHER = "weather";
	private static String TAG_LIST_WEATHER_MAIN = "main";
	private static String TAG_LIST_WEATHER_ID = "id";
	private static String TAG_LIST_WEATHER_ICON = "icon";
	private static String TAG_LIST_WEATHER_DESCR = "description";

	private static String TAG_LIST_SPEED = "speed";
	private static String TAG_LIST_DEG = "deg";

	// data JSONArray
	JSONArray LIST = null;
	JSONArray TEMP = null;
	JSONArray WEATHER = null;

	ArrayList<HashMap<String, String>> forecast = null;

	long dt;
	String pressure;
	String humidity;
	String speed;
	String degree;

	String temp_day;
	String temp_night;
	String temp_min;
	String temp_max;
	String temp_eve;
	String temp_morn;

	String weather_icon;
	String weather_main;
	String weather_id;
	String weather_descr;

	String date;
	TextView max;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.weather_layout);
max = (TextView)findViewById(R.id.maximumTemp);

		/** Enabling home button **/
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		new WeatherTask().execute();
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				max.setText((int) (Double.parseDouble(dayTemp
						.get(position)) - 273) + "\u2103");
LinearLayout l = (LinearLayout)findViewById(R.id.layout);

			}

		});
	}

	private class WeatherTask extends
			AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {
		@Override
		public ArrayList<HashMap<String, String>> doInBackground(Void... urls) {

			forecast = new ArrayList<HashMap<String, String>>();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			JSONObject json = jParser
					.makeHttpRequest(
							"http://api.openweathermap.org/data/2.5/forecast/daily?lat=0.331648&lon=32.581222&APPID=17ccd0db91baad73b0cee060b4a01295&cnt=7&mode=json",
							"GET", params);
			if (json != null) {
				try {
					String success = json.getString(TAG_MSG);
					// info=success;
					// Getting Array of data
					LIST = json.getJSONArray(TAG_LIST);

					// looping through All data
					for (int i = 0; i < LIST.length(); i++) {
						JSONObject c = LIST.getJSONObject(i);

						// Storing each json item in variable
						dt = c.getLong(TAG_LIST_DT);

						// date = new
						// java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new
						// java.util.Date (dt*1000));
						date = new java.text.SimpleDateFormat("MM/dd/yyyy")
								.format(new java.util.Date(dt * 1000));
						pressure = c.getString(TAG_LIST_PRESSURE);
						humidity = c.getString(TAG_LIST_HUMIDITY);
						speed = c.getString(TAG_LIST_SPEED);
						degree = c.getString(TAG_LIST_DEG);

						JSONObject d = c.getJSONObject(TAG_LIST_TEMP);
						temp_day = d.getString(TAG_LIST_TEMP_DAY);
						temp_night = d.getString(TAG_LIST_TEMP_NIGHT);
						temp_min = d.getString(TAG_LIST_TEMP_MIN);
						temp_max = d.getString(TAG_LIST_TEMP_MAX);
						temp_eve = d.getString(TAG_LIST_TEMP_EVE);
						temp_morn = d.getString(TAG_LIST_TEMP_MORN);

						/** creating listArrays **/
						day.add(new String(date));
						dayTemp.add(new String(temp_day));
						minTemp.add(new String(temp_min));
						maxTemp.add(new String(temp_max));
						

						// // Getting Array of data
						WEATHER = c.getJSONArray(TAG_LIST_WEATHER);
						// looping through All data
						for (int b = 0; b < WEATHER.length(); b++) {
							JSONObject e = WEATHER.getJSONObject(b);
							weather_icon = e.getString(TAG_LIST_WEATHER_ICON);
							weather_main = e.getString(TAG_LIST_WEATHER_MAIN);
							weather_id = e.getString(TAG_LIST_WEATHER_ID);
							weather_descr = e.getString(TAG_LIST_WEATHER_DESCR);

							/** creating listArrays **/
							icons.add(new String(weather_icon));
							desc.add(new String(weather_descr));
						}

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value

						map.put(TAG_LIST_DT, date);
						map.put(TAG_LIST_PRESSURE, pressure);
						map.put(TAG_LIST_HUMIDITY, humidity);
						map.put(TAG_LIST_SPEED, speed);
						map.put(TAG_LIST_DEG, degree);

						map.put(TAG_LIST_TEMP_DAY, temp_day);
						map.put(TAG_LIST_TEMP_NIGHT, temp_night);
						map.put(TAG_LIST_TEMP_MIN, temp_min);
						map.put(TAG_LIST_TEMP_MAX, temp_max);
						map.put(TAG_LIST_TEMP_EVE, temp_eve);
						map.put(TAG_LIST_TEMP_MORN, temp_morn);

						map.put(TAG_LIST_WEATHER_ICON, weather_icon);
						map.put(TAG_LIST_WEATHER_MAIN, weather_main);
						map.put(TAG_LIST_WEATHER_ID, weather_id);
						map.put(TAG_LIST_WEATHER_DESCR, weather_descr);

						// adding HashList to ArrayList
						forecast.add(map);

					}

				} catch (JSONException e) {
					e.printStackTrace();

				}
			}

			return forecast;

		}

		@Override
		public void onPostExecute(ArrayList<HashMap<String, String>> theList) {

			// Toast.makeText(getApplicationContext(),info,
			// Toast.LENGTH_LONG).show();
			WeatherAdapter weatheradapter = new WeatherAdapter(Weather.this, day,icons, maxTemp, minTemp);
			// updating listview
			setListAdapter(weatheradapter);
			ProgressBar p = (ProgressBar) findViewById(R.id.pbar_weather);
			p.setVisibility(View.INVISIBLE);

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			ProgressBar p = (ProgressBar) findViewById(R.id.pbar_weather);
			p.setVisibility(View.VISIBLE);
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

}
