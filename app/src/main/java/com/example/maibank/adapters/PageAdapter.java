package com.example.maibank.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Page view adapter
 */
public class PageAdapter extends FragmentStatePagerAdapter {
    /**
     * List containing the fragments to show in the pageview
     */
    private final List<Fragment> mFragmentList = new ArrayList<>();
    /**
     * Titles for the fragments
     */
    private final List<String> mFragmentTitleList = new ArrayList<>();

    /**
     * Parametrized constructor
     * @param fm The fragment manager
     */
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * Adds a fragment to the pageviewer
     * @param fragment The fragment to add
     * @param title The title of the fragment(page)
     */
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
}
