package com.ntvui;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	String TAG = "GCMMainActivity";
	Button btn_register;
	EditText Names, Usernames, Address;

	String message, names, usernames, address, regId;
	private SharedPreferences loginPreferences;
	SharedPreferences pref;
	private SharedPreferences.Editor loginPrefsEditor;
	private Boolean saveLogin;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MSG = "message";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.register);
		Names = (EditText) findViewById(R.id.txtnames);
		Usernames = (EditText) findViewById(R.id.txtEmail);
		Address = (EditText) findViewById(R.id.txtAddress);

		pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

		// Determine if an activity has already been executed
		if (pref.getBoolean("activity_executed", false)) {
			Intent t = new Intent(RegisterActivity.this, HomeActivity.class);
			startActivity(t);
			finish();
		}

		else {
			Editor ed = pref.edit();
			ed.putBoolean("activity_executed", true);
			ed.commit();
		}

		/** Gcm codes */
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		regId = GCMRegistrar.getRegistrationId(this);
		btn_register = (Button) findViewById(R.id.btnRegister);
		btn_register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// getting user Inputs
				names = Names.getText().toString();
				usernames = Usernames.getText().toString();
				address = Address.getText().toString();
				RegisterUser(names, usernames, address);
				Log.i(TAG, "Registering device");
				GCMRegistrar.register(RegisterActivity.this,
						GCMIntentService.SENDER_ID);

				/** registration done */

			}
		});
	}

	public void RegisterUser(final String names, final String username,
			final String address) {

		RequestParams params = new RequestParams();
		params.put("Names", names);
		params.put("Usernames", username);
		params.put("Address", address);

		RestClient.post("ntv/register.php", params,
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject json) {

						String msg;
						try {
							msg = json.getString(TAG_MSG);
							message = msg;
							SharedPreferences loginPreferences = getApplicationContext()
									.getSharedPreferences("loginPreferences",
											MODE_WORLD_READABLE);
							SharedPreferences.Editor loginPrefsEditor = loginPreferences
									.edit();
							loginPrefsEditor.putString("NAME", names);
							loginPrefsEditor.putString("USERNAME", username);
							loginPrefsEditor.putString("ADDRESS", address);
							// loginPrefsEditor.putBoolean("REMEMBER",
							// chk.isChecked());
							loginPrefsEditor.commit();

							Intent I = new Intent(RegisterActivity.this,
									AboutAppActivity.class);
							startActivity(I);
							finish();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Toast.makeText(getApplicationContext(), message,
								Toast.LENGTH_LONG).show();

					}

					@Override
					public void onFailure(Throwable arg0, JSONObject json) {

						Toast.makeText(getApplicationContext(),
								"Some went wrong", Toast.LENGTH_LONG).show();

					}

					@Override
					public void onFailure(Throwable error, String response) {

						// Toast.makeText(getApplicationContext(),response,
						// Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(),
								"Could not find Host ", Toast.LENGTH_LONG)
								.show();
					}
				});

	}
}
