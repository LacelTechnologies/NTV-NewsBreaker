package com.ntvui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//Adapter class extends with BaseAdapter and implements with OnClickListener 
public class WeatherAdapter extends BaseAdapter implements OnClickListener {

	private Activity activity;
	private ArrayList<String> days = new ArrayList<String>();
	private ArrayList<String> icons = new ArrayList<String>();
	private ArrayList<String> maxTemps = new ArrayList<String>();
	private ArrayList<String> minTemps = new ArrayList<String>();
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public WeatherAdapter(Activity a, ArrayList<String> day,
			ArrayList<String> icon, ArrayList<String> maxtemp,
			ArrayList<String> mintemp) {
		activity = a;
		days = day;
		icons = icon;
		maxTemps = maxtemp;
		minTemps = mintemp;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Create ImageLoader object to download and show image in list
		// Call ImageLoader constructor to initialize FileCache
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return days.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView weatherDesc;
		public TextView min_temp;
		public TextView day;
		public TextView max_temp;
		public ImageView weather_icon;

	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.weather_list_item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			// holder.weatherDesc = (TextView)
			// vi.findViewById(R.id.item_headline);
			holder.min_temp = (TextView) vi.findViewById(R.id.textminTemp);
			holder.day = (TextView) vi.findViewById(R.id.textDay);
			holder.max_temp = (TextView) vi.findViewById(R.id.textmaxTemp);
			holder.weather_icon = (ImageView) vi.findViewById(R.id.weathericon);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();
		/** converting temp to degrees before setting it to textview*/
		holder.day.setText(days.get(position));
		holder.max_temp.setText((int) (Double.parseDouble(maxTemps
				.get(position)) - 273) + "\u2103");
		holder.min_temp.setText((int) (Double.parseDouble(minTemps
				.get(position)) - 273) + "\u2103");
		ImageView image = holder.weather_icon;
		StringBuilder iconUrl = new StringBuilder();
		
		//generating icon url using string builder.
		iconUrl.append("http://openweathermap.org/img/w/")
				.append(icons.get(position)).append(".png");
		// DisplayImage function from ImageLoader Class
		// //http://openweathermap.org/img/w/10d.png
		imageLoader.DisplayImage(iconUrl.toString(), image);

		/******** Set Item Click Listner for LayoutInflater for each row ***********/

		return vi;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}