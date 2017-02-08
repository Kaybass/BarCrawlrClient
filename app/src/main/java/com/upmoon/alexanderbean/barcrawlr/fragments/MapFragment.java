package com.upmoon.alexanderbean.barcrawlr.fragments;


import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.model.Place;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment {
    private static final String TAG = "MapFragment";

    //private GoogleApiClient mClient;
    private GoogleMap mMap;
    private Location mCurrentLocation;
    private Place mPlace;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //mClient = new GoogleApiClient.Builder(getActivity()).build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
            }
        });
    }

    private class SearchTask extends AsyncTask<Location,Void,Void> {
        private Location mLocation;

        @Override
        protected Void doInBackground(Location... params) {
            mLocation = params[0];

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mCurrentLocation = mLocation;

            updateUI();
        }
    }

    private void updateUI() {
        if (mMap == null) {
            return;
        }

        LatLng pPlace = new LatLng(mPlace.getLat(), mPlace.getLon());
        LatLng myPlace = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

        LatLngBounds bounds = new LatLngBounds.Builder().include(pPlace).include(myPlace).build();

        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds,0);
        mMap.animateCamera(update);
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }*/
}
