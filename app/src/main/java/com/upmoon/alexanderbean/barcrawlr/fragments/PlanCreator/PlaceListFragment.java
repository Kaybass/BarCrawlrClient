package com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceListFragment extends Fragment {
    private static final int PLACE_PICKER_REQUEST = 1;

    // Define variables for the places' ListView and its corresponding ArrayAdapter.
    private RecyclerView mPlacesRecyclerView;
    private PlaceAdapter mPlaceAdapter;

    public PlaceListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View v = inflater.inflate(R.layout.fragment_place_list, container, false);

        mPlaceAdapter = new PlaceAdapter();
        mPlacesRecyclerView = (RecyclerView) v.findViewById(R.id.places_recycler_view);
        mPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                                                LinearLayoutManager.VERTICAL,false));
        mPlacesRecyclerView.setAdapter(mPlaceAdapter);

        // Initialize the addPlaceButton.
        final Button addPlaceButton = (Button) v.findViewById(R.id.add_place_button);
        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPickButtonClick();
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

                placeListChanged();

            }
        }
    }

    private void placeListChanged() {
        mPlaceAdapter.notifyDataSetChanged();
    }

    public void onPickButtonClick() {
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

    // Begin : Accessory classes used in the RecyclerView

    /**
     * The purpose of this class is to be a custom RecyclerView.Adapter that will expand when a user presses the expand_arrow.
     */

    private class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView placeName, placeAddress;

        private int placeIndex;

        public PlaceHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            placeName = (TextView) itemView.findViewById(R.id.place_holder_name);

            placeAddress = (TextView) itemView.findViewById(R.id.place_holder_address);
        }

        public void bindPlace(int pos) {
            placeName.setText(CurrentPlan.getInstance().getPlace(pos).getName());

            placeAddress.setText(CurrentPlan.getInstance().getPlace(pos).getAddress());

            placeIndex = pos;
        }

        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Remove Place")
                    .setMessage("Would you like to remove this place from the plan?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface diag, int which) {
                            CurrentPlan.getInstance().removePlaceFromPlan(placeIndex);

                            placeListChanged();
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
    }

    private class PlaceAdapter extends RecyclerView.Adapter<PlaceHolder> {
        public PlaceAdapter() {

        }

        @Override
        public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater LI = LayoutInflater.from(getActivity());
            View view = LI.inflate(R.layout.place_holder,parent,false);
            return new PlaceHolder(view);
        }

        @Override
        public void onBindViewHolder(PlaceHolder holder, int position) { holder.bindPlace(position); }

        @Override
        public int getItemCount() {
            return CurrentPlan.getInstance().getNumPlaces();
        }
    }

}