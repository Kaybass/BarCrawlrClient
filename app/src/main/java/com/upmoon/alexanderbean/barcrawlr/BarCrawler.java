package com.upmoon.alexanderbean.barcrawlr;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.upmoon.alexanderbean.barcrawlr.fragments.MapFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.PeopleListFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.PlaceListFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.OptionsFragment;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BarCrawler extends AppCompatActivity {
    private static final String EXTRA_PLAN_NAME = "com.upmoon.alexander.barcrawlr.plan_name";

    PlanLoader planLoader;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static Intent newIntent(Context packageContext, UUID planName) {
        Intent intent = new Intent(packageContext, BarCrawler.class);
        intent.putExtra(EXTRA_PLAN_NAME, planName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_crawler);

        mViewPager = (ViewPager) findViewById(R.id.container);

       if (mViewPager != null) { setUpViewPager(); }

        // Initialize the TabLayout.
        mTabLayout = (TabLayout) findViewById(R.id.tabs_BC);
        mViewPager.clearOnPageChangeListeners();
        mTabLayout.setupWithViewPager(mViewPager);

        setTitle("Bar Crawl : " + CurrentPlan.getInstance().getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar_crawler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private void setUpViewPager() {
        BC_Adapter adapter = new BC_Adapter(getSupportFragmentManager());
        adapter.addFragment(new MapFragment(), "Map");
        adapter.addFragment(new PlaceListFragment(), "Places");
        adapter.addFragment(new PeopleListFragment(), "People");
        adapter.addFragment(new OptionsFragment(), "Options");
        mViewPager.setAdapter(adapter);
    }

    static class BC_Adapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitles = new ArrayList<>();

        public BC_Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}

