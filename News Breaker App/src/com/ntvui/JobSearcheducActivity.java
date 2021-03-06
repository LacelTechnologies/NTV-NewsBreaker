package com.ntvui;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class JobSearcheducActivity extends SherlockActivity {
    
	Button btn_submit;
	EditText edt_names, edt_dob, edt_aor, edt_sex, edt_inst, edt_course,edt_personaldesc, edt_personalexp, edt_contact, edt_email;
	
	
	// JSON Node names	
    /**private static final String TAG_NAMES = "names";
	private static final String TAG_DOB= "dob";
    private static final String TAG_AOR = "aor";
    private static final String TAG_SEX = "sex";
    private static final String TAG_INSTITUTION = "institution";
    private static final String TAG_COURSE = "course_offered";
    private static final String TAG_PERSONALDESCR = "personaldescription";
    private static final String TAG_PERSONALEXP = "personalexperience";
    private static final String TAG_CONTACT = "contact";
    private static final String TAG_EMAIL = "email";*/
	
    private static final String TAG_SUCCESS = "success";
	private static final String TAG_MSG = "message";
	
		
	int x=0;
	
	
	HttpEntity resEntity;
	String message;
	
	//String targetUrl="http://192.168.43.77/ntv/selfemployed.php";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_search_educ);
        //enabling home button
        getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
        intialiseObjects();
        btn_submit =(Button)findViewById(R.id.btnsubmit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(emptyCheck()==false){
					
					String names = edt_names.getText().toString() ;
					String dob= edt_dob.getText().toString();
					String sex= edt_sex.getText().toString();
					String inst= edt_inst.getText().toString();
					String course= edt_course.getText().toString();
					String aor= edt_aor.getText().toString();
					String personaldesc= edt_personaldesc.getText().toString();
					String personalexp= edt_personalexp.getText().toString();
					String contact= edt_contact.getText().toString();
					String email= edt_email.getText().toString();
					
					try{
						
					}
						 catch (Exception e)
						{                       
				                    Toast.makeText(getApplicationContext(), "no plain text", Toast.LENGTH_LONG).show();
				            }
					
					submitStory(names,dob,aor,sex,inst,course,personalexp,personaldesc,contact,email);
				
				}else{
					
					Toast.makeText(getApplicationContext(),"Fill all fields", Toast.LENGTH_LONG).show();
				}
				
			}
		});
    }
    
       
    
    public void submitStory(String names,String dob,String aor,String sex, String inst, String course,String personaldesc, String personalexp, String contact, String email){
    	
    	RequestParams params = new RequestParams();
    	params.put("names", names);
    	params.put("dob", dob);
    	params.put("aor", aor);
    	params.put("sex", sex);
    	params.put("institution", inst);
    	params.put("course_offered", course);
    	params.put("personaldescription", personaldesc);
    	params.put("personalexperience", personalexp);
    	params.put("contact", contact);
    	params.put("email", email);
    	
    	RestClient.post("ntv/educemployment.php", params, new JsonHttpResponseHandler() {
            
    		@Override
            public void onSuccess(JSONObject json) {
    			
    			String msg;
				try {
					msg = json.getString(TAG_MSG);
					message=msg;
					emptyEdtTexts();
					Intent I = new Intent(JobSearcheducActivity.this,GetSelfEmployedActivity.class);
					startActivity(I);
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              	
				Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    			
    		}
    		
    		@Override
			public void onFailure(Throwable arg0, JSONObject json) {
				
				Toast.makeText(getApplicationContext(),"Some went wrong", Toast.LENGTH_LONG).show();
				
			}
			@Override
			public void onFailure(Throwable error, String response) {
				
			  //Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(),"Could not find Host ", Toast.LENGTH_LONG).show();
			} 
        });
     
    }
    
    public void intialiseObjects(){
    	
    	btn_submit=(Button) findViewById(R.id.btn_submit);
       	edt_names=(EditText) findViewById(R.id.jobSearch_names);
       	edt_dob=(EditText) findViewById(R.id.jobSearch_dob);
       	edt_aor=(EditText) findViewById(R.id.jobSearch_aor);
       	edt_sex=(EditText) findViewById(R.id.jobSearch_sex);
       	edt_inst=(EditText) findViewById(R.id.jobSearch_inst);
       	edt_course=(EditText) findViewById(R.id.jobSearch_course);
       	edt_personaldesc=(EditText) findViewById(R.id.et_personaldescr);
       	edt_personalexp=(EditText) findViewById(R.id.et_exp);
       	edt_contact=(EditText) findViewById(R.id.et_phone);
       	edt_email=(EditText) findViewById(R.id.et_email);
        
    	
    }
    
    public boolean emptyCheck(){
    	
		if(edt_names.getText().toString().contentEquals("")||
		   edt_dob.getText().toString().contentEquals("")||
		   edt_aor.getText().toString().contentEquals("")||
		   edt_sex.getText().toString().contentEquals("")||
		   edt_inst.getText().toString().contentEquals("")||
		   edt_course.getText().toString().contentEquals("")||
		   edt_personaldesc.getText().toString().contentEquals("")||
		   edt_personalexp.getText().toString().contentEquals("")||
		   edt_contact.getText().toString().contentEquals("")||
		   edt_email.getText().toString().contentEquals("")){
			return true;
		}else{
			return false;
		}
	}
    
    public void emptyEdtTexts(){
    	
    	edt_names.setText("");
    	edt_dob.setText("");
    	edt_aor.setText("");
    	edt_sex.setText("");
    	edt_inst.setText("");
    	edt_course.setText("");
    	edt_personaldesc.setText("");
    	edt_personalexp.setText("");
    	edt_contact.setText("");
    	edt_email.setText("");
			}
    
   
    @Override
    public boolean onCreateOptionsMenu( com.actionbarsherlock.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.job_search_menu, (com.actionbarsherlock.view.Menu) menu);
       
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        
        switch(item.getItemId()){
      
        case R.id.share_jobSearch:    	
        	//call the messaging apps
        	Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, "Download now");
			shareIntent.setType("text/plain");
			startActivity(/* shareIntent */Intent.createChooser(shareIntent,
					"Choose please"));
            break;
        case android.R.id.home:
        	super.onBackPressed();
        
        }
 
        return true;
    } 


    

}
