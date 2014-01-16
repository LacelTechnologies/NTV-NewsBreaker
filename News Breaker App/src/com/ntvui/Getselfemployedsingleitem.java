package com.ntvui;

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

public class Getselfemployedsingleitem extends SherlockActivity {
	Bundle getcount;
	int position, count;
	String  name, dob, aor, sex, job_title,job_descr, personal_exp,contact, email;
	NewInDetailsPageAdapter newsAdapter;
	AppFragmentAdapter mAdapter;
	ViewPager mPager;

	TextView namet,dobt,aort,sext,job_titlet,job_descrt,personal_expt,contactt,emailt;
	
	/** Enabling home button **/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getselfemployedsingleitem);

		/** Enabling home button **/
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getcount = getIntent().getExtras();

		
		/** getting job details **/
        name = getcount.getString("name");
        dob = getcount.getString("dob");
        aor = getcount.getString("aor");
        sex = getcount.getString("sex");
        job_title = getcount.getString("title");
        job_descr = getcount.getString("desc");
        personal_exp = getcount.getString("exp");
        contact = getcount.getString("contact");
        email = getcount.getString("email");
    	/** getting textViews **/ 
		namet=(TextView)findViewById(R.id.textView10);
		dobt=(TextView)findViewById(R.id.textView11);
		aort=(TextView)findViewById(R.id.textView12);
		sext=(TextView)findViewById(R.id.textView13);
		job_titlet=(TextView)findViewById(R.id.textView14);
		job_descrt=(TextView)findViewById(R.id.textView15);
		personal_expt=(TextView)findViewById(R.id.textView16);
		contactt=(TextView)findViewById(R.id.textView17);
		emailt=(TextView)findViewById(R.id.textView18);
		
		namet.setText(name);
		dobt.setText(dob);
		aort.setText(aor);
		sext.setText(sex);
		job_titlet.setText(job_title);
		job_descrt.setText(job_descr);
		personal_expt.setText(personal_exp);
		contactt.setText(contact);
		emailt.setText(email);
        
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
