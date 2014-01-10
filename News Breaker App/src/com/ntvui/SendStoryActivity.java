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

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.ntvui.MCrypt;
import com.ntvui.R.color;

import com.ntvui.RestClient;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import com.ntvui.MCrypt;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendStoryActivity extends SherlockActivity {
	
	
	/**Declaring Variables**/
	
	Button btn_submit;
	Button btn_browseImg, attach;
	Button btn_browseAud;
	Button btn_browseVid;
	EditText edt_title;
	EditText edt_details;
	EditText tv_attach, edt_where;
	TextView  notify;
	CheckBox check;
	private static int identifier = 1;
	private static double Lat = 0.0;
	private static  double Long = 0.0;

	// JSON Node names	
    //private static final String TAG_TITLE = "title";
	//private static final String TAG_DETAILS= "details";
    //private static final String TAG_WHERE = "location";
    //private static final String TAG_SUCCESS = "success";
	private static final String TAG_MSG = "message";
	
	private static final int SELECT_IMAGE = 1;
	private static final int SELECT_VIDEO=2;
	private static final int SELECT_AUDIO=3;
	
	
	int x=0;
	
	
	HttpEntity resEntity;
	String message;
	String selectedPathForImage = "";
	String selectedPathForAud_Vid = "";
	String imagedata;
	/**url for image upload**/
	String targetUrl="http://192.168.1.95/sVoice/scripts/upload_aud_vid.php";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sending_news);
        
    	/**Initializing objects for large screen**/
        if(findViewById(R.id.btn_choose_audio)!=null){
        intialiseObjects();
        
        }
    	/**Initializing objects for small screen**/
        else{
        	intialiseObjectsForSmallScreen();
        }
        
    	/**Enabling home button**/
        getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); 		
		
		
		/**setting browse buttons for large screen**/
        if(findViewById(R.id.btn_choose_audio)!=null){
       btn_browseImg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openGalleryForImage();
			}	
		});
       btn_browseAud.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openGalleryforAudio();
			}	
		});
       btn_browseVid.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openGalleryforVideo();
			}	
		});
        }
        
    	/**setting the submit button to send data to the server**/
       btn_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/**Encrypting users' inputs before sending**/
				if(identifier == 2){
				sendsecure();
			}
				/** sending without encryption**/
				else{
					sendnormally();
				}}
		});
       
       check.setOnClickListener(new View.OnClickListener() {
		
		@SuppressLint("ResourceAsColor")
		@Override
		public void onClick(View arg0) {
			// notifying users on when sending news.
			if(check.isChecked()){
				//user wants to reveal identity
				notify.setText(R.string.attentionChecked);
				notify.setTextColor(color.successcolor);
				//notify the sending code 
				identifier = 1;
			} 
			else {
				//user doesnt want to reveal identity
				notify.setText(R.string.attentionUnchecked);
				notify.setTextColor(color.warningcolor);
				identifier = 2;
			}
		}
	});
    }
   /**selecting file**/ 
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	 
        if (resultCode == RESULT_OK) {
 
            if (requestCode == SELECT_IMAGE)
            {
                Log.i("SELECT_IMAGE","Selecting image");
                Uri selectedImageUri = data.getData();
                selectedPathForImage = getPath(selectedImageUri);
                Log.i("SELECT_IMAGE Path : ", selectedPathForImage);
                tv_attach.setText("File: "+getFileName(selectedPathForImage)); 
                
            }
            if (requestCode == SELECT_AUDIO)
            {
                Log.i("SELECT_AUDIO","Selecting Audio");
                Uri selectedImageUri = data.getData();
                selectedPathForAud_Vid = getPath(selectedImageUri);
                Log.i("SELECT_AUDIO Path : ", selectedPathForAud_Vid);
                tv_attach.setText(getFileName(selectedPathForAud_Vid)); 
                
            }
            if (requestCode == SELECT_VIDEO)
            {
                Log.i("SELECT_VIDEO","Selecting Video");
                Uri selectedImageUri = data.getData();
                selectedPathForAud_Vid = getPath(selectedImageUri);
                Log.i("SELECT_VIDEO Path : ", selectedPathForAud_Vid);
                
                tv_attach.setText(getFileName(selectedPathForAud_Vid)); 
                
            }
 
        }
    }
    
    public boolean checkForImage(String path){
    	
    	if(path.contentEquals("")){
    	 
    		imagedata="";
    		return false;
    		
    	}else{
    		imagedata= ConvertImageToString(path);
    		return true;
    	}
    }
    
   public boolean checkForAudio_Video(String path){
    	
    	if(path.contentEquals("")){
    	 
    		
    		return false;
    		
    	}else{
    		
    		doFileUpload2(path,targetUrl );
    		
    		return true;
    	}
    }
   /**send normally**/
   public void sendnormally(){
	 
		if(emptyCheck()==false){
			
			String title = edt_title.getText().toString() ;
			String details= edt_details.getText().toString();
			String where= edt_where.getText().toString();		
			
			/**sending text and attachments**/
			checkForImage(selectedPathForImage);
			Log.i("image: ",imagedata);
			submitStory(title,details,where,imagedata);
			checkForAudio_Video(selectedPathForAud_Vid);
			
			
		}else{
			
			Toast.makeText(getApplicationContext(),"Fill all fields", Toast.LENGTH_LONG).show();
		}
		
   }
   
   /**sending secure**/
   public void sendsecure(){
	   MCrypt mcrypt=new MCrypt();
		if(emptyCheck()==false){
			
			String title = edt_title.getText().toString() ;
			String details= edt_details.getText().toString();
			String where= edt_where.getText().toString();
			
			try{
				title = mcrypt.bytesToHex(mcrypt.encrypt(title));
				details = mcrypt.bytesToHex(mcrypt.encrypt(details));
				where = mcrypt.bytesToHex(mcrypt.encrypt(where));} catch (Exception e)
				{                       
		                    Toast.makeText(getApplicationContext(), "error while encrypting data!", Toast.LENGTH_LONG).show();
		            }
			/**sending text and attachments**/
			checkForImage(selectedPathForImage);
			Log.i("image: ",imagedata);
			submitStory(title,details,where,imagedata);
			checkForAudio_Video(selectedPathForAud_Vid);
			
			
		}else{
			
			Toast.makeText(getApplicationContext(),"Fill all fields", Toast.LENGTH_LONG).show();
		}
		
   }
    public void submitStory(String title,String details,String where,String attachement){
    	
    	RequestParams params = new RequestParams();
    	SharedPreferences loginPreferences = getSharedPreferences("loginPreferences", MODE_PRIVATE);

    	String NAME = loginPreferences.getString("NAME"," ");
    	String USERNAME= loginPreferences.getString("USERNAME"," ");
    	//String ADDRESS= loginPreferences.getString("ADDRESS"," ");
    	params.put("title", title);
    	params.put("details", details);
    	params.put("location", where);
    	params.put("attachment", attachement);
    	params.put("username", USERNAME);
    	params.put("name", NAME);
    	params.put("longitude", getString((int) Long));
    	params.put("latitude", getString((int) Lat));
    	
    	RestClient.post("ntv/submitstory.php", params, new JsonHttpResponseHandler() {
            
    		@Override
            public void onSuccess(JSONObject json) {
    			
    			String msg;
				try {
					msg = json.getString(TAG_MSG);
					message=msg;
					emptyEdtTexts();
					Intent I = new Intent(SendStoryActivity.this,SentActivity.class);
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
    	btn_browseImg=(Button) findViewById(R.id.btn_choose_image);
    	btn_browseAud=(Button) findViewById(R.id.btn_choose_audio);
    	btn_browseVid=(Button) findViewById(R.id.btn_choose_video);
    	tv_attach=(EditText) findViewById(R.id.txt_attach);
        edt_title=(EditText) findViewById(R.id.txt_title);
        edt_details=(EditText) findViewById(R.id.txt_body);
        edt_where=(EditText) findViewById(R.id.txt_where);
       // attach =(Button)findViewById(R.id.attach);
        //indicator =(TextView)findViewById(R.id.indicator);
        
    	
    }
public void intialiseObjectsForSmallScreen(){
    	
    	btn_submit=(Button) findViewById(R.id.btn_submit);
    	//btn_browseImg=(Button) findViewById(R.id.btn_choose_image);
    	//btn_browseAud=(Button) findViewById(R.id.btn_choose_audio);
    	//btn_browseVid=(Button) findViewById(R.id.btn_choose_video);
    	tv_attach=(EditText) findViewById(R.id.txt_attach);
        edt_title=(EditText) findViewById(R.id.txt_title);
        edt_details=(EditText) findViewById(R.id.txt_body);
        edt_where=(EditText) findViewById(R.id.txt_where);
       // attach =(Button)findViewById(R.id.attach);
        notify =(TextView)findViewById(R.id.txtNotify);
        check = (CheckBox)findViewById(R.id.check_btn_normal);
    	
    }
    
    public boolean emptyCheck(){
    	
		if(edt_title.getText().toString().contentEquals("")||
		   edt_details.getText().toString().contentEquals("")||
		   edt_where.getText().toString().contentEquals("")){
			return true;
		}else{
			return false;
		}
	}
    
    public void emptyEdtTexts(){
    	
    	edt_title.setText("");
    	edt_details.setText("");
    	edt_where.setText("");
		tv_attach.setText("");
		selectedPathForAud_Vid="";
		selectedPathForImage="";
	}
    
    @SuppressWarnings("static-access")
    //open gallery to choose image
	public void openGalleryForImage(){
   	 
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent,"Select Image "), SELECT_IMAGE);
    }
    //open gallery to choose video
    public void openGalleryforVideo(){
      	 
        Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Video "), SELECT_VIDEO);
       }
    //open gallery to choose audio
    public void openGalleryforAudio(){
      	 
        Intent intent = new Intent();
            intent.setType("audio/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Audio "), SELECT_AUDIO);
       }
    //open gallery to choose image
    private void doFileUpload2(String selectedVideo,String urlString ){
		 
    	Log.i("PATH: ",selectedVideo);
    	
        File file1=null; 
       
        file1= new File(selectedVideo);
        
        try
        { //upload file to server
             HttpClient client = new DefaultHttpClient();
             HttpPost post = new HttpPost(urlString);
             FileBody bin1 = new FileBody(file1);
           
             MultipartEntity reqEntity = new MultipartEntity();
             reqEntity.addPart("uploadedfile", bin1);
         
             post.setEntity(reqEntity);
             HttpResponse response = client.execute(post);
             resEntity = response.getEntity();
             final String response_str = EntityUtils.toString(resEntity);
             if (resEntity != null) {
                 Log.i("RESPONSE",response_str);
                 runOnUiThread(new Runnable(){
                        public void run() {
                             try {
                               
                                Toast.makeText(getApplicationContext(),"Upload Complete. Check the server uploads directory.", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                           }
                    });
             }
        }
        catch (Exception ex){
             Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
      }
 
 
    //fetching media from phone storage
    @SuppressWarnings("deprecation")
	public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    
    public String getFileName(String Path){
    	
    	File f= new File(Path);
    	return f.getName();
    }
    
    @TargetApi(Build.VERSION_CODES.FROYO)
	public String ConvertImageToString(String path){
    	
	    FileInputStream in;
        BufferedInputStream buf;
     
    	 try {
			in = new FileInputStream(path);
		
         buf = new BufferedInputStream(in);
         Bitmap bMap = BitmapFactory.decodeStream(buf);
        //image.setImageBitmap(bMap);
         
         
         ByteArrayOutputStream baos = new ByteArrayOutputStream();  
         																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																							bMap.compress(Bitmap.CompressFormat.JPEG, 80, baos); // bm is the bitmap object   
         byte[] b = baos.toByteArray();  

       
       
         Bitmap bMap2= decodeToLowResImage(b,  50, 50);
         ByteArrayOutputStream baos2 = new ByteArrayOutputStream();  
         bMap2.compress(Bitmap.CompressFormat.JPEG, 80, baos2); // bm is the bitmap object   
         byte[] b2 = baos2.toByteArray(); 
       
    	
         return Base64.encodeToString(b2,Base64.DEFAULT);
       
    	 } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
	     }
    	 
    }
    
    private Bitmap decodeToLowResImage(byte [] b, int width, int height) {
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new ByteArrayInputStream(b), null, o);

            //The new size we want to scale to
            final int REQUIRED_SIZE_WIDTH=(int)(width*0.7);
            final int REQUIRED_SIZE_HEIGHT=(int)(height*0.7);

            //Find the correct scale value. It should be the power of 2.
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE_WIDTH || height_tmp/2<REQUIRED_SIZE_HEIGHT)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new ByteArrayInputStream(b), null, o2);
        } catch (OutOfMemoryError e) {
        }
        return null;
    }
  

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		if(findViewById(R.id.attach_audio)!=null){
		getSupportMenuInflater().inflate(R.menu.send_story_large,
				(com.actionbarsherlock.view.Menu) menu);}
		
		else{
			getSupportMenuInflater().inflate(R.menu.send_story,
					(com.actionbarsherlock.view.Menu) menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.attach_audio:
			openGalleryforAudio();
			break;
case R.id.attach_video:
	openGalleryforVideo();
			break;
case R.id.attach_image:
	openGalleryForImage();
	break;
		case android.R.id.home:
			finish();
		}
		return true;
	}

	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	/*

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(SendStoryActivity.this);
				dialog.setContentView(R.layout.custom_alert_dialog);
				dialog.setTitle("Sending...");
				TextView text = (TextView) dialog
						.findViewById(R.id.txt_alert_message);
				text.setText("You have't attached any file, Do you wish to send this Story without an attachement?");

				dialog.show();
			}
		});

	}
*/
	public class MyLocationListener implements LocationListener {
		int f=1;
			    @Override
			    public void onLocationChanged(Location loc)
			    
			    {
			    	
			    	  if(f==1){
			    		  //getting user coordinates
			       Lat = loc.getLatitude();
			        Long = loc.getLongitude();
			        
			        f=2;
			    	  }
			    	  //toast location for confirmation
			       String d = "My current location is: " + "Latitude = "
			            + loc.getLatitude() + "Longitude = " + loc.getLongitude();
			      
			       
			        Toast.makeText(getApplicationContext(), d, Toast.LENGTH_SHORT)
			                .show();
			    }

			    @Override
			    public void onProviderDisabled(String provider) {
			       Toast.makeText(getApplicationContext(), "Please turn on your GPS",
			    		   Toast.LENGTH_SHORT).show();
			        	Intent i = new Intent(android.provider.Settings.ACTION_SETTINGS);
			        startActivity(i);
			               
			    }

			    @Override
			    public void onProviderEnabled(String provider) {
			        Toast.makeText(getApplicationContext(), "Enable",
			  
			                Toast.LENGTH_SHORT).show();
			    }

			    @Override
			    public void onStatusChanged(String provider, int status, Bundle extras) {}

				
			}
			
			
}
