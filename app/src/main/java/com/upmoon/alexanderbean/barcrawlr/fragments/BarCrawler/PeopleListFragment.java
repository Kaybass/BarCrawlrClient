package com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.model.User;
import com.upmoon.alexanderbean.barcrawlr.networking.BarConnector;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentUsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;


public class PeopleListFragment extends Fragment {

    private static final int MINUTES_TO_MILLISECONDS = 10000;

    private static int mMinutesToUpdate = 3;

    private RecyclerView mPeopleRecyclerView;
    private PeopleAdapter mPeopleAdapter;

    private LocationManager mLM;
    private LocationListener mLL;

    private Thread mUpdateThread;

    private volatile boolean mRunning = true;

    private static int mSleepTime = mMinutesToUpdate * MINUTES_TO_MILLISECONDS;

    private double mLongitude;

    private double mLatitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateLocationToCached();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_people_list, container, false);

        mPeopleAdapter = new PeopleAdapter();
        mPeopleRecyclerView = (RecyclerView) v.findViewById(R.id.people_recycler_view_bc);
        mPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));
        mPeopleRecyclerView.setAdapter(mPeopleAdapter);

        return v;
    }

    public void update(){
        if(mPeopleAdapter != null)
            mPeopleAdapter.notifyDataSetChanged();
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
        PeopleListFragment.mSleepTime = mSleepTime;
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

    private void updateLocationToCached(){
        if(getActivity().checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
            mLM = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

            //gets last known location, low energy use, low effort
            Location location = mLM.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //handles mildy rare case where device has no known last location
            if(!(location == null)) {
                setLongitude(location.getLongitude());
                setLatitude(location.getLatitude());
            } else {
                mLongitude = 0;
                mLatitude = 0;
            }
        }
    }

    private class PeopleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView personName;

        private ArrayList<User> users;

        public PeopleHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            personName = (TextView) itemView.findViewById(R.id.people_holder_name);

            users = CurrentUsers.getInstance().getUsers();
        }

        public void bindPlace(int pos) {
            personName.setText(users.get(pos).getName());
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class PeopleAdapter extends RecyclerView.Adapter<PeopleHolder> {
        public PeopleAdapter() {

        }

        @Override
        public PeopleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater LI = LayoutInflater.from(getActivity());
            View view = LI.inflate(R.layout.people_holder,parent,false);
            return new PeopleHolder(view);
        }

        @Override
        public void onBindViewHolder(PeopleHolder holder, int pos) {
            holder.bindPlace(pos);
        }

        @Override
        public int getItemCount() {
            return CurrentUsers.getInstance().getUsers().size();
        }
    }
}
