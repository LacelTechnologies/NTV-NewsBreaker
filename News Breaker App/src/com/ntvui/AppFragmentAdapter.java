package com.ntvui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.viewpagerindicator.IconPagerAdapter;

class AppFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] { "Recieve hot Stories Daily in Business, sport, local & international", "Send in  news Stories to ntv any time any Where", "Updates of latest job Opportuniies.." };
    protected static final int[] ICONS = new int[] {
           
    };
   protected static final int[] IMAGE = {R.drawable.abs__ic_voice_search,R.drawable.ic_menu_compose,R.drawable.ntvlogos};

    private int mCount = CONTENT.length;

    public AppFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return AppFragment.newInstance(CONTENT[position % CONTENT.length], IMAGE[position%IMAGE.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return AppFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}