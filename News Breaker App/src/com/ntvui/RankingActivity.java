package com.ntvui;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RankingActivity extends ListActivity{
	
	// Progress Dialog
    //private ProgressDialog pDialog;
    
    ArrayList<HashMap<String, String>> rankList;
    
    
    String message=""; 
	
	
	
	TextView usr1,usr2;
	
	String usrname, usrrank;
	
    // JSON Node names	
    private static final String TAG_NAME = "name";
    private static final String TAG_RANK = "rank";
    private static final String TAG_USRRANK = "usrrank";
    private static final String TAG_SUCCESS = "success";
	private static final String TAG_MSG = "message";
	
    JSONArray rank = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rankings);
		
		usr1=(TextView)findViewById(R.id.textView1);
		usr2=(TextView)findViewById(R.id.textView2);
		
		rankList = new ArrayList<HashMap<String, String>>();
		
        //new getting_comments().execute();
        getting_ranks_httpsync();
        
        Button back =(Button)findViewById(R.id.btn_cancel_rank);
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
	}
    
	
	public void getting_ranks_httpsync(){
		
       RestClient.post("ntv/Ranking.php", null, new JsonHttpResponseHandler() {
            
    		@Override
            public void onSuccess(JSONObject json) {
    			int suc=0;
    			try {
    	               
    				// Checking for SUCCESS TAG
                   int success = json.getInt(TAG_SUCCESS);
                   suc = success;
                   if (success == 1) {
                       // data found
    					
    					rank = json.getJSONArray(TAG_USRRANK);

                       // looping through All data
                       for (int i = 0; i < rank.length(); i++) {
                           JSONObject rank_extract = rank.getJSONObject(i);

                           // Storing each json item in variable
                           String name_ = rank_extract.getString(TAG_NAME);
                           String rank_ = rank_extract.getString(TAG_RANK);
                           
                           
                           usrname= name_;
                           usrrank=rank_;
                          
                           // creating new HashMap
                           HashMap<String, String> mylist = new HashMap<String, String>();

                           // adding each child node to HashMap key => value                           
                           
                           
                           mylist.put(TAG_NAME, usrname);   					   
    					  
                           mylist.put(TAG_RANK, usrrank);
                           
                           // adding HashList to ArrayList                           
                           rankList.add(mylist);
                           
                           //setting List adapter
                           
                       }
                       
                       if(message.contentEquals("")){
                   		
                       	ListAdapter adapter = new SimpleAdapter(
                                   RankingActivity.this, rankList,
                                   R.layout.ranking_list_item, new String[] { TAG_NAME,TAG_RANK},
                                   new int[] { R.id.name_rank, R.id.rank_rank });
                           // updating listview
                           setListAdapter(adapter);
                   } else {
                       
                   	
                   	String msg = json.getString(TAG_MSG);
                   	message=msg;
                       
                   }
               } }catch (JSONException e) {
                   e.printStackTrace();
               }
    			if(suc==1){
    				
    				
    			} else{
    				
    				Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    			}
    		}
    		@Override
    		public void onFailure(Throwable arg0, JSONObject json) {
    				
    				Toast.makeText(getApplicationContext(),"Some went wrong", Toast.LENGTH_LONG).show();
    				
    		}

    		/* (non-Javadoc)
    		* @see com.loopj.android.http.AsyncHttpResponseHandler#onFailure(java.lang.Throwable, java.lang.String)
    		*/
    		@Override
    		public void onFailure(Throwable error, String response) {
    			// TODO Auto-generated method stub
    			super.onFailure(error, response);
    			
    			Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    		} 
    			
         });	
		
		
	}
	
	
}
