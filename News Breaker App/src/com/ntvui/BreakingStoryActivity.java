package com.ntvui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BreakingStoryActivity extends Activity {
    String message;
    TextView txtmsg;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breaking_stories);
 
        // Retrive the data from GCMIntentService.java
        Intent i = getIntent();
 
        message = i.getStringExtra("message");
 
        // Locate the TextView
        txtmsg = (TextView) findViewById(R.id.txtBreaking);
 
        // Set the data into TextView
        txtmsg.setText(message);
    }
}