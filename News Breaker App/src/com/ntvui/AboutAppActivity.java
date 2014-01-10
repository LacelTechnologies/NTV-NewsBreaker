package com.ntvui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
/** this class displays about application swipe view **/
public class AboutAppActivity extends SherlockFragmentActivity {
	/** the adapter class **/
	    AppFragmentAdapter mAdapter;
	  /** view pager frame which houses the swipy fragments **/
	    ViewPager mPager;
	    /** the page indicator  **/
	    PageIndicator mIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app_themed_circles);
        mAdapter = new AppFragmentAdapter(getSupportFragmentManager());
        /**locating the pager frame **/
        mPager = (ViewPager)findViewById(R.id.pager);
        /** a setting the adapter for the pager frame **/
        mPager.setAdapter(mAdapter);
        
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }
}