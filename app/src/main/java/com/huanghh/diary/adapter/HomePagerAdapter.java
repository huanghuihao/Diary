package com.huanghh.diary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mListFragment;

    public HomePagerAdapter(FragmentManager fm, List<Fragment> mListFragment) {
        super(fm);
        this.mListFragment = mListFragment;
    }

    @Override
    public Fragment getItem(int i) {
        return mListFragment.get(i);
    }

    @Override
    public int getCount() {
        return mListFragment != null ? mListFragment.size() : 0;
    }
}
