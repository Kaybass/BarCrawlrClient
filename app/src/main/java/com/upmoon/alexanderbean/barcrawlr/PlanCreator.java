package com.upmoon.alexanderbean.barcrawlr;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.upmoon.alexanderbean.barcrawlr.fragments.MapFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator.OptionsFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator.PlaceListFragment;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanLoader;

import java.util.ArrayList;
import java.util.List;

public class PlanCreator extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, PlanCreator.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_creator);

        // Add Toolbar to Activity.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the ViewPager.
        mViewPager = (ViewPager) findViewById(R.id.container);

        if(mViewPager != null) {
            setUpViewPager();
        }

        // Initialize the TabLayout.
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.clearOnPageChangeListeners();
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan_creator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void setUpViewPager() {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new PlaceListFragment(), "Places");
        adapter.addFragment(new MapFragment(), "Map");
        adapter.addFragment(new OptionsFragment(), "Options");
        mViewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
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