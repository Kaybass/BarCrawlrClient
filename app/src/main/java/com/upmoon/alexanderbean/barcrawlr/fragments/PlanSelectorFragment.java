package com.upmoon.alexanderbean.barcrawlr.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.location.LocationListener;

import com.google.android.gms.location.LocationServices;
import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;
import com.upmoon.alexanderbean.barcrawlr.networking.BarConnector;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanLoader;
import com.upmoon.alexanderbean.barcrawlr.PlanCreator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanSelectorFragment extends Fragment {
    /**
     * Member variables
     */
    private ArrayList<File> mPlans;
    FloatingActionButton mLeftFAB, mRightFAB;

    private RecyclerView mRecyclerView;
    private PlanAdapter  mAdapter;

    private double mLongitude, mLatitude;

    private LocationManager locationManager;
    private LocationListener locationListener;

    public PlanSelectorFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan_selector,container,false);

        /**
         * Widgets here
         */
        mLeftFAB = (FloatingActionButton) v.findViewById(R.id.connect_fab);
        mLeftFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mRightFAB = (FloatingActionButton) v.findViewById(R.id.connect_fab);
        mRightFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder buildo = new AlertDialog.Builder(getActivity());

                final EditText inp = new EditText(getActivity());

                inp.setInputType(InputType.TYPE_CLASS_TEXT);

                buildo.setTitle("Create New Plan");

                buildo.setView(inp);

                buildo.setPositiveButton("Create",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface diag, int which){

                        PlanLoader pl = new PlanLoader(getActivity());

                        String planName = inp.getText().toString();

                        if(planName != "" && pl.planNameExists(planName)){
                            Toast.makeText(getActivity(),"Invalid Name or name exists",Toast.LENGTH_SHORT).show();
                        }
                        else{

                            Plan plan = new Plan();

                            plan.setName(inp.getText().toString());

                            CurrentPlan.getInstance().setPlan(plan);

                            Intent intent = new Intent(getActivity(),PlanCreator.class);
                            startActivity(intent);
                        }
                    }
                });
                buildo.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface diag, int which){

                    }
                });

                buildo.show();
            }
        });

        //RecyclerView
        mAdapter = new PlanAdapter();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.gol_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                                        LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(mAdapter);


        /**
         * Load in plan data
         */

        PlanListChanged();

        /**
         * location
         */

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                updateLocation(location.getLongitude(),location.getLatitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        return v;
    }

    public void updateLocation(double lon, double lat){
        mLongitude = lon;
        mLatitude = lat;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater mi){

        super.onCreateOptionsMenu(menu,mi);

        mi.inflate(R.menu.menu_plan_selector,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            //legal
            case R.id.plan_selector_legal:
                AlertDialog.Builder build = new AlertDialog.Builder(getActivity());

                build.setMessage(R.string.mit_license_message);

                AlertDialog diag = build.create();

                diag.show();

                return true;

            //ABOUT
            case R.id.plan_selector_about:
                return true;

            //HELP
            case R.id.plan_selector_help:
                return true;

            //SETTINGS
            case R.id.plan_selector_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            PlanListChanged();
        }
    }

    private void PlanListChanged(){
        PlanLoader pl = new PlanLoader(getActivity());
        mPlans = new ArrayList<>(Arrays.asList(pl.getPlans()));
        mAdapter.notifyDataSetChanged();

        TextView sectionLabel = (TextView) getActivity().findViewById(R.id.section_label);
        TextView selectPlan = (TextView) getActivity().findViewById(R.id.select_plan);
        TextView noPlans = (TextView) getActivity().findViewById(R.id.no_plans_text_view);
        if(mPlans.size() > 0)
        {
            sectionLabel.setVisibility(GONE);
            selectPlan.setVisibility(GONE);
            noPlans.setVisibility(GONE);
        }
        else{
            sectionLabel.setVisibility(View.VISIBLE);
            selectPlan.setVisibility(View.VISIBLE);
            noPlans.setVisibility(View.VISIBLE);
        }
    }

    private class PlanHolder extends RecyclerView.ViewHolder
                        implements View.OnClickListener{

        private TextView planName, planLastAccessDate;

        public PlanHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            planName = (TextView) itemView.findViewById(R.id.plan_holder_plan_name);

            planLastAccessDate = (TextView) itemView.findViewById(R.id.plan_holder_create_date);
        }

        public void bindPlan(int pos){

            File plan = mPlans.get(pos);

            planName.setText(plan.getName().replace(".plan",""));

            String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US).format(plan.lastModified());

            date = "Last Modified: " + date;

            planLastAccessDate.setText(date);
        }


        /*
            TODO: ADD DELETE OPTION
         */
        @Override
        public void onClick(View v){

            PlanLoader pl = new PlanLoader(getActivity());

            Log.d("PLAN CHOSEN",planName.getText().toString());

            CurrentPlan.getInstance().setPlan(pl.loadPlan(planName.getText().toString() + ".plan"));

            Intent intent = new Intent(getActivity(),PlanCreator.class);
            startActivity(intent);
        }
    }

    private class PlanAdapter extends RecyclerView.Adapter<PlanHolder>{

        public PlanAdapter(){

        }

        @Override
        public PlanHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater LI = LayoutInflater.from(getActivity());
            View view = LI.inflate(R.layout.plan_holder,parent,false);
            return new PlanHolder(view);
        }

        @Override
        public void onBindViewHolder(PlanHolder holder, int position){ holder.bindPlan(position); }

        @Override
        public int getItemCount(){
            return mPlans.size();
        }
    }

    private class GetPlan extends AsyncTask<String, Void,String> {

        public GetPlan(){
        }

        @Override
        protected String doInBackground(String... codeAndUserName){

            BarConnector bc = new BarConnector(getActivity().getString(R.string.bcsite),
                                            getActivity().getString(R.string.bcserverapikey));

            if ( ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                        LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION );
            }

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

            bc.sendCode(codeAndUserName[0],new User(codeAndUserName[1],mLongitude,mLatitude));

            return "";
        }

        @Override
        protected void onPostExecute(String str){
            Toast.makeText(getActivity(), "Disconnected",Toast.LENGTH_SHORT).show();
        }
    }
}
