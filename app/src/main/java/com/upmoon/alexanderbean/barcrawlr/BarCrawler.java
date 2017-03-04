package com.upmoon.alexanderbean.barcrawlr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
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

    private Thread mUpdateThread;

    private volatile boolean mRunning = true;

    private static int mSleepTime = 600000;

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

        mUpdateThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while(isRunning()) {


                    //Sleep
                    try {
                        Thread.sleep(getSleepTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        mUpdateThread.start();
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

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("BARCRAWLRACTIVITY", "onDestroy() called");
        setRunning(false);
    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Are you sure you want to leave the crawl?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface diag, int which) {

                        //TODO write function to clean up stuff from plan

                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface diag, int which) {
                        diag.cancel();
                    }
                }).setCancelable(true);

        final AlertDialog alertDialogConfirmation = builder.create();

        alertDialogConfirmation.show();
    }

    public boolean isRunning() {
        return mRunning;
    }

    public void setRunning(boolean mRunning) {
        this.mRunning = mRunning;
    }

    public static int getSleepTime() {
        return mSleepTime;
    }

    public static void setSleepTime(int mSleepTime) {
        BarCrawler.mSleepTime = mSleepTime;
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

