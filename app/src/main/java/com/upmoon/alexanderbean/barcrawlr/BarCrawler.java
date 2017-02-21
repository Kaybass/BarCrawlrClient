package com.upmoon.alexanderbean.barcrawlr;

import android.content.Context;
import android.content.Intent;
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
import com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator.PlaceListFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator.SettingsFragment;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanLoader;

import java.util.UUID;

public class BarCrawler extends AppCompatActivity {
    private static final String EXTRA_PLAN_NAME = "com.upmoon.alexander.barcrawlr.plan_name";


    PlanLoader planLoader;

    private ViewPager mViewPager;

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

        planLoader = new PlanLoader(getApplicationContext());
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                switch(position) {
                    case 0:
                        return new MapFragment();
                    case 1:
                        return new PlaceListFragment();
                    case 2:
                        return new PeopleListFragment();
                    case 3:
                        return new SettingsFragment();
                }
                return new MapFragment();
            }

            @Override
            public int getCount() {
                // There are four fragments(/pages) in the PlanCreator ViewPager
                return 4;
            }
        });
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


}

