package com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.upmoon.alexanderbean.barcrawlr.BarCrawler;
import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.model.User;
import com.upmoon.alexanderbean.barcrawlr.networking.BarConnector;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentUsers;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanSaver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    private LocationManager mLM;

    private double mLongitude, mLatitude;

    private Button mSavePlan, mSharePlan;

    private volatile boolean mTaskRunning = false;


    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mSavePlan = (Button) v.findViewById(R.id.save_plan_button);
        mSavePlan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlanSaver planSaver = new PlanSaver(getActivity());

                planSaver.savePlan(CurrentPlan.getInstance().getPlan());

                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();

            }
        });

        mSharePlan = (Button) v.findViewById(R.id.add_people_button);
        mSharePlan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddPlan ap = new AddPlan();

                //TODO: add dialogue for user name

                ap.execute("alex");
            }
        });

        if ( ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            updateLocation();
        }

        return v;
    }

    public boolean isTaskRunning() {
        return mTaskRunning;
    }

    public void setTaskRunning(boolean mTaskRunning) {
        this.mTaskRunning = mTaskRunning;
    }

    //http://stackoverflow.com/questions/33865445/gps-location-provider-requires-access-fine-location-permission-for-android-6-0
    private void updateLocation(){
        if(getActivity().checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
            mLM = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

            //gets last known location, low energy use, low effort
            Location location = mLM.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //handles mildy rare case where device has no known last location
            if(!(location == null)) {
                mLongitude = location.getLongitude();
                mLatitude = location.getLatitude();
            } else {
                mLongitude = 0;
                mLatitude = 0;
            }
        }
    }

    private class AddPlan extends AsyncTask<String, Void,Boolean> {

        private String message;

        @Override
        protected Boolean doInBackground(String... planAndUserName){

            if(!isTaskRunning()){

                setTaskRunning(true);

                BarConnector bc = new BarConnector(getActivity().getString(R.string.bcsite),
                        getActivity().getString(R.string.bcserverapikey));

                updateLocation();

                String Result = bc.sendPlan(CurrentPlan.getInstance().getPlan(),new User(planAndUserName[0],mLongitude,mLatitude));

                if(Result == BarConnector.ERROR_MESSAGE){

                    message = BarConnector.ERROR_MESSAGE;

                    setTaskRunning(false);
                    return false;
                }
                else{

                    try{

                        JSONObject resultJSON = new JSONObject(Result);

                        if(resultJSON.has("error")){
                            message = resultJSON.getString("error");

                            setTaskRunning(false);
                            return false;
                        }
                        else{
                            CurrentPlan  curp = CurrentPlan.getInstance();
                            CurrentUsers curu = CurrentUsers.getInstance();

                            curp.setCode(resultJSON.getString("code"));

                            curu.loadUsers(resultJSON.getJSONObject("users"));

                            setTaskRunning(false);
                            return true;
                        }
                    }
                    catch(JSONException e){
                        message = "Server sent bad JSON";
                        setTaskRunning(false);
                        return false;
                    }
                }
            }
            else{
                message = "";
                setTaskRunning(false);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean str){

            if(str){
                Intent i = new Intent(getActivity(),BarCrawler.class);
                startActivity(i);
            }
            else{
                if(!message.equals("")){
                    Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}