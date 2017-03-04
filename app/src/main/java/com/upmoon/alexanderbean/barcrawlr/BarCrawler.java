package com.upmoon.alexanderbean.barcrawlr;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Process;
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
import android.widget.Toast;

import com.upmoon.alexanderbean.barcrawlr.fragments.MapFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.PeopleListFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.PlaceListFragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.OptionsFragment;
import com.upmoon.alexanderbean.barcrawlr.model.User;
import com.upmoon.alexanderbean.barcrawlr.networking.BarConnector;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentUsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class BarCrawler extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private LocationManager mLM;
    private LocationListener mLL;

    private Thread mUpdateThread;

    private volatile boolean mRunning = true;

    private static int mSleepTime = 300000;

    private double mLongitude;

    private double mLatitude;

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

        mLM = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);

        mLL = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLongitude = location.getLongitude();
                mLatitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        mUpdateThread = new Thread(new Runnable() {

            @Override
            public void run() {

                BarConnector bc = new BarConnector(getString(R.string.bcsite),
                                            getString(R.string.bcserverapikey));

                while(isRunning()) {

                    // Update Client Location
                    if(checkPermission("android.permission.ACCESS_FINE_LOCATION", Process.myPid(), Process.myUid()) == PERMISSION_GRANTED) {
                        mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLL);

                        String usersResponse = bc.locationUpdate(CurrentPlan.getInstance().getCode()
                                , new User(CurrentUsers.getInstance().getSelf(), getLongitude(), getLatitude()));

                        Log.d("BarCrawlrThread", "Server sent: " + usersResponse);

                        parseUserResponse(usersResponse);

                        //Sleep
                        try {
                            Thread.sleep(getSleepTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public void parseUserResponse(String users){
        try{
            JSONObject usersJson = new JSONObject(users);

            if(usersJson.has("error")){
                Log.d("BarCrawlrActivity", "Server sent:" + usersJson.getString("error"));
                String errorMessage = "error: " + usersJson.getString("error");
                Toast.makeText(this, errorMessage ,Toast.LENGTH_SHORT ).show();
            }
            else{
                CurrentUsers.getInstance().loadUsers(usersJson);
            }

        }
        catch (JSONException e){
            Log.d("BarCrawlrActivity", "Server sent bad response to users");
        }
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

