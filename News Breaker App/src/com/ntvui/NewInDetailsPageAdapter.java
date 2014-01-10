package com.ntvui;

import java.util.ArrayList;

import com.actionbarsherlock.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/** this class acts as  *the page adapter for the vews in details page view*/
public class NewInDetailsPageAdapter extends FragmentPagerAdapter {
	MenuItem menuItem;

	// ArrayList<String> dataList;
	String message = "";
	int status = 0;

	/**array lists that hold all data for the view pager **/
	protected static  ArrayList<String> HEADLINE;
	protected static  ArrayList<String> FULL;
	protected static  ArrayList<String> TIME;
	protected static  ArrayList<String> IMAGEURLS;
	protected static  ArrayList<String> CAPTIONS;

	protected static final int[] IMAGE = { R.drawable.abs__ic_voice_search,
			R.drawable.ic_menu_compose, R.drawable.ntvlogos };

	private int mCount;
	/** an instance of the page adapter **/
	public NewInDetailsPageAdapter(FragmentManager fm,  ArrayList<String> newsHeadlines,ArrayList<String> newsFull, ArrayList<String> newsTime,ArrayList<String> newsImage,ArrayList<String> caption,int count) {
		super(fm);
		this.mCount = count;
		NewInDetailsPageAdapter.HEADLINE = newsHeadlines;
		NewInDetailsPageAdapter.FULL = newsFull;
		NewInDetailsPageAdapter.TIME = newsTime;
		NewInDetailsPageAdapter.IMAGEURLS = newsImage;
		NewInDetailsPageAdapter.CAPTIONS = caption;

	}
	/** Mapping  array list items onto corresponding page **/
	@Override
	public Fragment getItem(int position) {
		return NewsInDetailsFragment.newInstance(HEADLINE.get(position % mCount),FULL.get(position % mCount),TIME.get(position % mCount),IMAGEURLS.get(position % mCount),CAPTIONS.get(position % mCount), IMAGE[position % IMAGE.length]);
	}
	/** gets number of pages in the view pager **/
	@Override
	public int getCount() {
		return mCount;
	}
	/** fragment title  hidden **/
	@Override
	public CharSequence getPageTitle(int position) {
		return NewInDetailsPageAdapter.HEADLINE.get(position % mCount);
	}
	/**page counter **/
	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}
}
