package com.ntvui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsInDetailsFragment extends Fragment {
	/** fragment holder news in details  **/
	private static final String KEY_HEADLINE = "TestFragment:Content";

	private static final String KEY_FULL = "TestFragment:Content";

	private static final String KEY_TIME = "TestFragment:Content";

	private static final String KEY_IMAGEURL = "TestFragment:Content";
	private static final String KEY_CAPTION = "TestFragment:Content";
	
	private static final int IMAGE_CONTENT = 1;
	TextView headline, photoCaption, time, details;
	ImageView photo;
	/**creating an instance for the fragment adapter  **/
	public static NewsInDetailsFragment newInstance(String headlines,String fullStory,String time,String imageUrl,String caption ,int image) {
		NewsInDetailsFragment fragment = new NewsInDetailsFragment();
        fragment.mCaption = caption;
	    fragment.mHeadlines = headlines;
	    fragment.mFull = fullStory;
	    fragment.mTime = time;
	    fragment.mImageUrl = imageUrl;
		fragment.images = image;

		return fragment;
	}
	/** fragment declaring main strings for the  class **/
	private String mHeadlines = "???";
	private String mFull = "???";
	private String mTime = "???";
	private String mImageUrl = "???";
	private String mCaption = "???";
	private int images = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**getting saved data from activity bundle if it exists**/
		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_HEADLINE)) {
			mHeadlines = savedInstanceState.getString(KEY_HEADLINE);
			mFull = savedInstanceState.getString(KEY_FULL);
			mTime = savedInstanceState.getString(KEY_TIME);
			mImageUrl = savedInstanceState.getString(KEY_IMAGEURL);
			mCaption = savedInstanceState.getString(KEY_CAPTION);
			// image = savedInstanceState.getInt(IMAGE_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/** setting fragment layout and views  **/
		View layout = inflater.inflate(R.layout.news_in_details_fragment, null);
        headline = (TextView)layout.findViewById(R.id.headline_newsInDetails);
        photoCaption = (TextView)layout.findViewById(R.id.photo_caption_newsInDetails);
        time = (TextView)layout.findViewById(R.id.time_newsInDetails);
        details = (TextView)layout.findViewById(R.id.details_newsInDetails);
        photo =(ImageView)layout.findViewById(R.id.photo_newsInDetails);
        
        /** setting data  to corresponding views  **/
        headline.setText(mHeadlines);
        photoCaption.setText(mCaption);
        time.setText(mTime);
        details.setText(mFull);       
        
        ImageLoader loader = new ImageLoader(getActivity());
        loader.DisplayImage(mImageUrl,photo);
		return layout;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {	
		
		super.onSaveInstanceState(outState);
		/**saving current instance into activity bundle **/
		outState.putString(KEY_HEADLINE, mHeadlines);
		outState.putString(KEY_FULL,mFull);
		outState.putString(KEY_TIME, mTime);
		outState.putString(KEY_IMAGEURL,mImageUrl);
		outState.putString(KEY_CAPTION,mCaption);
		// outState.putInt(IMAGE_CONTENT, images);

	}
	
	

}
