package com.ntvui;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
 
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
 
import com.google.android.gcm.GCMRegistrar;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
 


 
public final class ServerUtilities {
   
    /**
     * Register this account/device pair within the server.
     *
     */
    static void register(final Context context,final String regId) {
        Log.i("", "registering device (regId = " + regId + ")");
        
        RequestParams params = new RequestParams();
    	params.put("registration_id", regId);
    
    	RestClient.post("ntv/registerGCMregid.php", params, new JsonHttpResponseHandler() {
            
    		@Override
            public void onSuccess(JSONObject json) {
    			
    			String msg;
              	
    			 Log.i("success", "registered device (regId = " + regId + ")");
    		}
    		
    		@Override
			public void onFailure(Throwable arg0, JSONObject json) {
				
    			Log.i("json failure", "registered device (regId = " + regId + ")");
			}
			@Override
			public void onFailure(Throwable error, String response) {
				
				Log.i("failed", "registered device (regId = " + regId + ")");
			} 
        });
    }
}