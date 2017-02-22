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
import com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator.PlaceListFragment;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanLoader;

import java.util.UUID;

public class PlanCreator extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static final String EXTRA_PLAN_NAME = "com.upmoon.alexanderbean.barcrawlr.plan_name";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private PlanLoader planLoader;

    public static Intent newIntent(Context packageContext, UUID planName) {
        Intent intent = new Intent(packageContext, PlanCreator.class);
        intent.putExtra(EXTRA_PLAN_NAME, planName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_creator);

        UUID planName = (UUID) getIntent().getSerializableExtra(EXTRA_PLAN_NAME);

        mViewPager = (ViewPager) findViewById(R.id.container);

        planLoader = new PlanLoader(getApplicationContext());

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("Places"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Map"));
        mTabLayout.addTab(mTabLayout.newTab().setText("People"));

        FragmentManager fragmentmanager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentmanager) {

            @Override
            public Fragment getItem(int position) {
                switch(position) {
                    case 0:
                        return new PlaceListFragment();
                    case 1:
                        return new MapFragment();
                    case 2:
                        return new PeopleListFragment();
                }
                return new PlaceListFragment();
            }

            @Override
            public int getCount() {
                // There are three fragments(/pages) in the PlanCreator ViewPager
                return 3;
            }

        });
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

}
