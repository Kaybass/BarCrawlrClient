package com.upmoon.alexanderbean.barcrawlr;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.upmoon.alexanderbean.barcrawlr.fragments.MapFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.PeopleListFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.PlaceListFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.PlanSelectorFragment;
import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class PlanCreator extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static final String EXTRA_PLAN_NAME = "com.upmoon.alexanderbean.barcrawlr.plan_name";

    private ViewPager mViewPager;
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

        FragmentManager fragmentmanager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentmanager) {

            @Override
            public Fragment getItem(int position) {
                switch(position) {
                    case 0:
                        return new MapFragment();
                    case 1:
                        return new PlaceListFragment();
                    case 2:
                        return new PeopleListFragment();
                }
                return new MapFragment();
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
