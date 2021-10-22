package com.example.maibank.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.maibank.adapters.PageAdapter;
import com.example.maibank.fragments.AccountsPageFragment;
import com.example.maibank.fragments.HomePageFragment;
import com.example.maibank.fragments.InfoPageFragment;
import com.example.maibank.fragments.PaymentsPageFragment;
import com.example.maibank.R;
import com.google.android.material.tabs.TabLayout;

/**
 * Class holder for the pageview
 */
public class MainActivity extends AppCompatActivity {

    private PageAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        viewPager = findViewById(R.id.pager);

        // setting up the adapter
        viewPagerAdapter = new PageAdapter(getSupportFragmentManager());

        // add the fragments
        viewPagerAdapter.addFragment(new HomePageFragment(), "Home");
        viewPagerAdapter.addFragment(new PaymentsPageFragment(), "Payments");
        viewPagerAdapter.addFragment(new AccountsPageFragment(), "Accounts");
        viewPagerAdapter.addFragment(new InfoPageFragment(), "Info");

        // Set the adapter
        viewPager.setAdapter(viewPagerAdapter);

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to  set the page viewer
        // we use the setupWithViewPager().
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_AUTO);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.darkGrey));
        tabLayout.setTabTextColors(getResources().getColor(R.color.white),
                getResources().getColor(R.color.white));
    }

    @Override
    public void onBackPressed() {

    }
}