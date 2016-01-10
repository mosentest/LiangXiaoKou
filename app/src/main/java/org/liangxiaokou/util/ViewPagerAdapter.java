package org.liangxiaokou.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by moziqi on 2015/12/15 0015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mListFragments;
    private String[] mTitles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mListFragments, String[] mTitles) {
        super(fm);
        this.mListFragments = mListFragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragments.get(position);
    }

    @Override
    public int getCount() {
        return mListFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
