package com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceListFragment extends Fragment {
    private static final int PLACE_PICKER_REQUEST = 1;
    private int PLACES_EMPTY = 0;

    // Define variables for the places' ListView and its corresponding ArrayAdapter.
    private ListView placesListView;
    private ArrayAdapter<String> placesArrayAdapter;

    public PlaceListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View v = inflater.inflate(R.layout.fragment_place_list, container, false);

        // Initialize placesListView
        placesListView = (ListView) getActivity().findViewById(R.id.places_list_view);

        // If the CurrentPlan contains any places, load them into placesListView
        if (CurrentPlan.getInstance().getNumPlaces() > 0) {
            // Initialize placesArrayAdapter.
            placesArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
               getPlacesNames());
            // Set placesListView adapter to placesArrayAdapter.
            placesListView.setAdapter(placesArrayAdapter);

        } else {
            // If not, placesArrayAdapter must be initialized
            //  when the first Place is added to the CurrentPlan.
            // Set the flag to initialize placesArrayAdapter
            PLACES_EMPTY = 1;
        }

        // Initialize the addPlaceButton.
        final Button addPlaceButton = (Button) v.findViewById(R.id.add_place_button);
        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPickButtonClick(v);
            }
        });

        // Return the inflated layout for the fragment.
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(),data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();

                /** Add place to placeList **/
                // Convert Google Place to our Place.
                LatLng latLng = place.getLatLng();
                // Create ourPlace Place object
                com.upmoon.alexanderbean.barcrawlr.model.Place ourPlace =
                        new com.upmoon.alexanderbean.barcrawlr.model.Place(
                                place.getName().toString(),
                                place.getAddress().toString(),
                                latLng.longitude,
                                latLng.latitude);
                // Add ourPlace (new place) to current plan.
                CurrentPlan.getInstance().addPlaceToPlan(ourPlace);

                // If there were no places in the CurrentPlan, placesArrayAdapter must be initialized and set here
                if (PLACES_EMPTY == 1) {
                    // Initialize placesListView
                    placesListView = (ListView) getActivity().findViewById(R.id.places_list_view);
                    // Initialize placesArrayAdapter here.
                    placesArrayAdapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        getPlacesNames());

                    // Set placesListView adapter to placesArrayAdapter.
                    placesListView.setAdapter(placesArrayAdapter);
                    // The ListView is no longer empty, reset the flag.
                    PLACES_EMPTY = 0;
                } else {
                    // If not, update placesArrayAdapter.

                    /** Couldn't get ArrayAdapter.notifyDataSetChanged() to work **/
                    //placesArrayAdapter.notifyDataSetChanged();

                    /** Alt method that recreates the ArrayAdapter. It works, but with a lot of garbage and wastes resources **/
                    placesArrayAdapter = new ArrayAdapter<>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            getPlacesNames());

                    // Set placesListView adapter to placesArrayAdapter.
                    placesListView.setAdapter(placesArrayAdapter);
                }
            }
        }
    }

    public void onPickButtonClick(View v) {
        // Construct an intent for the place picker
        try {
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(getActivity());
            // Start the intent by requesting a result,
            // identified by a request code.
            startActivityForResult(intent, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            Toast.makeText(getActivity(), "Google Play Services Repairable Exception", Toast.LENGTH_LONG).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getActivity(), "Google Play Services Not Available", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<String> getPlacesNames() {
        ArrayList<String> placeNames = new ArrayList<>();
        for (int i = 0; i < CurrentPlan.getInstance().getNumPlaces(); i++) {
            placeNames.add(CurrentPlan.getInstance().getPlace(i).getName());
        }

        return placeNames;
    }
}