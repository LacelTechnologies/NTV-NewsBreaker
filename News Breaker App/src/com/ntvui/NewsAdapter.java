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
/** this class is the adapter for the news list activity **/
//Adapter class extends with BaseAdapter and implements with OnClickListener 
public class NewsAdapter extends BaseAdapter implements
		OnClickListener {
	/** arrays that hold the news list items **/
	private Activity activity;
	private ArrayList<String> images = new ArrayList<String>();
	private ArrayList<String> heads = new ArrayList<String>();
	private ArrayList<String> fulls = new ArrayList<String>();
	private ArrayList<String> times = new ArrayList<String>();
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	
	/** an instance of the adapter **/
	public NewsAdapter(Activity a, ArrayList<String> image, ArrayList<String> head, ArrayList<String> full ,ArrayList<String> time ) {
		activity = a;
		images = image;
		heads = head;
		fulls = full;
		times = time;		
		
		
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Create ImageLoader object to download and show image in list
		// Call ImageLoader constructor to initialize FileCache
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return images.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView headline;
		public TextView time;
		public TextView full;
		public ImageView image;

	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.news_list_item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.headline = (TextView) vi.findViewById(R.id.item_headline);
			holder.time = (TextView) vi.findViewById(R.id.item_time);
			holder.full = (TextView) vi.findViewById(R.id.item_full);
			holder.image = (ImageView) vi.findViewById(R.id.item_thumb);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		holder.headline.setText(heads.get(position));
		holder.full.setText(fulls.get(position));
		holder.time.setText(times.get(position));
		ImageView image = holder.image;

		// DisplayImage function from ImageLoader Class
		imageLoader.DisplayImage(images.get(position), image);

		/******** Set Item Click Listner for LayoutInflater for each row ***********/
		
		return vi;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}