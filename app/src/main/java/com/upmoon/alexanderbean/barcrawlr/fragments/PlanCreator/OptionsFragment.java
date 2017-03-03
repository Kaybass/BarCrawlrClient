package com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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


    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        final Button savePlan = (Button) v.findViewById(R.id.save_plan_button);
        savePlan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlanSaver planSaver = new PlanSaver(getActivity());

                planSaver.savePlan(CurrentPlan.getInstance().getPlan());

                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();

            }
        });

        if ( ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            updateLocation();
        }

        return v;
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
        protected Boolean doInBackground(String... codeAndUserName){

            BarConnector bc = new BarConnector(getActivity().getString(R.string.bcsite),
                    getActivity().getString(R.string.bcserverapikey));

            updateLocation();

            String Result = bc.sendCode(codeAndUserName[0],new User(codeAndUserName[1],mLongitude,mLatitude));

            if(Result == BarConnector.ERROR_MESSAGE){

                message = BarConnector.ERROR_MESSAGE;

                return false;
            }
            else{

                try{
                    JSONObject resultJSON = new JSONObject(Result);

                    if(resultJSON.has("error")){
                        message = resultJSON.getString("error");
                        return false;
                    }
                    else{
                        CurrentPlan  curp = CurrentPlan.getInstance();
                        CurrentUsers curu = CurrentUsers.getInstance();

                        curp.setCode(resultJSON.getString("code"));

                        curu.loadUsers(resultJSON.getJSONObject("users"));

                        return true;
                    }
                }
                catch(JSONException e){
                    message = "Server sent bad JSON";
                    return false;
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean str){
            if(str){
                //load barcrawlr
            }
            else{
                Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}