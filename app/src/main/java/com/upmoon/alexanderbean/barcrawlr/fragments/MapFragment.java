package com.upmoon.alexanderbean.barcrawlr.fragments;


import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;

import java.util.ArrayList;

import static com.google.android.gms.location.LocationServices.API;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment {
    private static final String TAG = "MapFragment";

    private GoogleApiClient mClient;
    private GoogleMap mMap;
    private Polyline mLine;
    private Location mCurrentLocation;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mClient = new GoogleApiClient.Builder(getActivity()).addApi(API).build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI(mMap);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            updateUI(mMap);
        }
    }

    private void updateUI(GoogleMap map) {
        if (mMap == null) {
            return;
        }

        try {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mClient);
        } catch(SecurityException e) {
            System.out.print(TAG + " : Unable to retrieve CurrentLocation.");
        }

        if((mCurrentLocation != null) || (CurrentPlan.getInstance().getNumPlaces() != 0)) {
            // Create an ArrayList to hold the Markers
            ArrayList<Marker> markers = new ArrayList<>();

            if (mCurrentLocation != null) {
                if(mLine != null) {
                    mMap.clear();
                }

                // Add a marker to the map at the client's Current Location
                markers.add(map.addMarker(new MarkerOptions()
                        .position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()))
                        .title("Current Location")));
                markers.get(0).setTag(0);
            }

            // Add a marker to the map at each Place in the Current Plan.
            for (int i = 0; i < CurrentPlan.getInstance().getNumPlaces(); i++) {
                markers.add(map.addMarker(new MarkerOptions()
                        .position(new LatLng(CurrentPlan.getInstance().getPlace(i).getLat(), CurrentPlan.getInstance().getPlace(i).getLon()))
                        .title(CurrentPlan.getInstance().getPlace(i).getName())));
                markers.get(i).setTag(0);
            }

            // Add PolyLines in between each Place.
            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            for (int i = 0; i < CurrentPlan.getInstance().getNumPlaces(); i++) {
                LatLng point = new LatLng(CurrentPlan.getInstance().getPlace(i).getLat(), CurrentPlan.getInstance().getPlace(i).getLon());
                options.add(point);
            }
            mLine = mMap.addPolyline(options);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();

            int padding = 25;    // Offset from the edge in pixels
            CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(update);
        }
    }
}
