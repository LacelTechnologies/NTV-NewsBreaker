package com.ntvui;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.view.MenuItem;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class GetSelfEmployedActivity extends SherlockListActivity implements OnItemSelectedListener{

	// Progress Dialog
	// private ProgressDialog pDialog;

	ArrayList<HashMap<String, String>> dataList;
	ArrayList<String> arr_name = new ArrayList();
	ArrayList<String> arr_dob = new ArrayList();
	ArrayList<String> arr_aor = new ArrayList();
	ArrayList<String> arr_sex = new ArrayList();
	ArrayList<String> arr_title = new ArrayList();
	ArrayList<String> arr_experience = new ArrayList();
	ArrayList<String> arr_desc = new ArrayList();
	ArrayList<String> arr_contact = new ArrayList();
	ArrayList<String> arr_email = new ArrayList();
	String message = "";
	int status = 0;

	// private static String url_FeedStores_data =
	// "http://192.168.43.77/ProjectAgric/feed.php";
	// private static String url_FeedStores_data =
	// "http://192.168.2.169/ProjectAgric/feed.php";
	// JSON Node names

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MSG = "message";
	private static final String TAG_SELFEMPLOYED = "selfemployed";
	private static final String TAG_NAME = "name";
	private static final String TAG_DOB = "dob";
	private static final String TAG_AOR = "aor";
	private static final String TAG_SEX = "sex";
	private static final String TAG_JOBTITLE = "job_title";
	private static final String TAG_JOBDESCR = "job_descr";
	private static final String TAG_PERSONALEXP = "personal_exp";
	private static final String TAG_CONTACT = "contact";
	private static final String TAG_EMAIL = "email";

	// data JSONArray
	JSONArray selfemployed = null;
String[] urls = new String[]{"ntv/getselfemployed.php", "ntv/getcompanyjobs.php"};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_home);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Spinner
		
		Spinner spinner = (Spinner)findViewById(R.id.spinnerJobs);
	spinner.setOnItemSelectedListener(this);
		// Hashmap for ListView
		dataList = new ArrayList<HashMap<String, String>>();

		LoadSelfemployee_httpsync("ntv/getselfemployed.php");

		// Get listview
		ListView lv = getListView();

		// on seleting single store
		// launching Map Screen
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Bundle Job = new Bundle();
				Job.putString("name", arr_name.get(position));
				Job.putString("dob", arr_dob.get(position));
				Job.putString("aor", arr_aor.get(position));
				Job.putString("sex", arr_sex.get(position));
				Job.putString("title", arr_title.get(position));
				Job.putString("desc", arr_desc.get(position));
				Job.putString("exp", arr_experience.get(position));
				Job.putString("contact", arr_contact.get(position));
				Job.putString("email", arr_email.get(position));
				Intent i = new Intent(GetSelfEmployedActivity.this,
						Getselfemployedsingleitem.class);
				i.putExtras(Job);
				startActivity(i);

			}

		});
	}

	public void LoadSelfemployee_httpsync(String Url) {

		RestClient.get(Url, null,
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject json) {

						try {
							// Checking for SUCCESS TAG
							int success = json.getInt(TAG_SUCCESS);
							status = 1;
							if (success == 1) {
								// data found
								// Getting Array of data
								selfemployed = json
										.getJSONArray(TAG_SELFEMPLOYED);

								// looping through All data
								for (int i = 0; i < selfemployed.length(); i++) {
									JSONObject c = selfemployed
											.getJSONObject(i);

									// Storing each json item in variable
									String name = c.getString(TAG_NAME);
									String dateofbirth = c.getString(TAG_DOB);
									String areaofres = c.getString(TAG_AOR);
									String sex = c.getString(TAG_SEX);
									String jobtitle = c.getString(TAG_JOBTITLE);
									String jobdesc = c.getString(TAG_JOBDESCR);
									String exp = c.getString(TAG_PERSONALEXP);
									String contact = c.getString(TAG_CONTACT);
									String email = c.getString(TAG_EMAIL);
									
									//creating arrayLists
									arr_name.add(new String(name));
									arr_dob.add(new String(dateofbirth));
									arr_aor.add(new String(areaofres));
									arr_sex.add(new String(sex));
									arr_title.add(new String(jobtitle));
									arr_desc.add(new String(jobdesc));
									arr_experience.add(new String(exp));
									arr_contact.add(new String(contact));
									arr_email.add(new String(email));
									// creating new HashMap
									HashMap<String, String> map = new HashMap<String, String>();

									// adding each child node to HashMap key =>
									// value
									map.put(TAG_NAME, name);
									map.put(TAG_DOB, dateofbirth);
									map.put(TAG_AOR, areaofres);
									map.put(TAG_SEX, sex);
									map.put(TAG_JOBTITLE, jobtitle);
									map.put(TAG_JOBDESCR, jobdesc);
									map.put(TAG_PERSONALEXP, exp);
									map.put(TAG_CONTACT, contact);
									map.put(TAG_EMAIL, email);

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

							ListAdapter adapter = new SimpleAdapter(
									GetSelfEmployedActivity.this, dataList,
									R.layout.selfemployedsingleitem,
									new String[] { TAG_JOBTITLE, TAG_JOBDESCR,
											TAG_CONTACT }, new int[] {
											R.id.txtSelfEmployedTitle,
											R.id.txtSelfEmployedShortDesc,
											R.id.txtSelfEmployedContact });
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
					 * com.loopj.android.http.JsonHttpResponseHandler#onFailure
					 * (java.lang.Throwable, org.json.JSONObject)
					 */

					@Override
					public void onFailure(Throwable arg0, JSONObject json) {

						Toast.makeText(getApplicationContext(),
								"Some went wrong", Toast.LENGTH_LONG).show();

					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * com.loopj.android.http.AsyncHttpResponseHandler#onFailure
					 * (java.lang.Throwable, java.lang.String)
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

	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.job_menu,
				(com.actionbarsherlock.view.Menu) menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case android.R.id.home:
			finish();
			break;
		case R.id.compose_self:
			Intent i = new Intent(GetSelfEmployedActivity.this,
					JobSearchActivity.class);
			startActivity(i);
			break;
		case R.id.compose_educ:
			Intent q = new Intent(GetSelfEmployedActivity.this,
					JobSearcheducActivity.class);
			startActivity(q);
			break;

		}

		return true;
	}

	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		// when item is selected
				arr_name.clear();
				arr_dob.clear();
				arr_aor.clear();
				arr_sex.clear();
				arr_title.clear();
				arr_desc.clear();
				arr_experience.clear();
				arr_contact.clear();
				arr_email.clear();
				LoadSelfemployee_httpsync(urls[arg2]);
				
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
