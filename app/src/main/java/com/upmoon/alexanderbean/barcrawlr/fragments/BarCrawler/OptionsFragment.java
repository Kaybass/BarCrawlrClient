package com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler;


import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    private LocationManager mLM;

    private Button mDisconnectButton;

    private volatile boolean mTaskRunning = false;

    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_options, container, false);

        // Initialize Disconnect Button
        mDisconnectButton = (Button) v.findViewById(R.id.disconnect);

        mDisconnectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Disconnect dc = new Disconnect();

                dc.execute(CurrentUsers.getInstance().getSelf());
            }
        });

        return v;
    }

    private Location getCurrentLocation() {
        if(getActivity().checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
            mLM = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

            //gets last known location, low energy use, low effort
            return mLM.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        return null;
    }

    public boolean isTaskRunning() {
        return mTaskRunning;
    }

    public void setTaskRunning(boolean mTaskRunning) {
        this.mTaskRunning = mTaskRunning;
    }

    private class Disconnect extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... userName){

            if(!isTaskRunning()) {
                setTaskRunning(true);

                Location currentLocation = getCurrentLocation();

                if(currentLocation != null) {

                    User selfUserObject = new User(userName.toString(), currentLocation.getLongitude(), currentLocation.getLatitude());

                    BarConnector bc = new BarConnector(getActivity().getString(R.string.bcsite),
                            getActivity().getString(R.string.bcserverapikey));

                    String result = bc.disconnect(CurrentPlan.getInstance().getCode(), selfUserObject);

                    if (result == BarConnector.ERROR_MESSAGE) {
                        setTaskRunning(false);
                        return false;
                    }

                    setTaskRunning(false);
                    return true;

                } else {
                    setTaskRunning(false);
                    return false;
                }

            } else {
                setTaskRunning(false);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean str){
            Toast.makeText(getActivity(), "Disconnected",Toast.LENGTH_SHORT).show();

            // Quit the activity.
            getActivity().finish();
        }
    }
}
