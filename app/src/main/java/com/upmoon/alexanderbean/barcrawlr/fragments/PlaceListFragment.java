package com.upmoon.alexanderbean.barcrawlr.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceListFragment extends Fragment {
    private static final int PLACE_PICKER_REQUEST = 1;
    private int PLACES_EMPTY = 0;

    // Define the RecyclerView to hold the list of Places.
    private RecyclerView mPlacesRecyclerView;

    
    public PlaceListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View v = inflater.inflate(R.layout.fragment_place_list, container, false);

        // Initialize placesListView
        mPlacesRecyclerView = (RecyclerView) getActivity().findViewById(R.id.places_recycler_view);

        if (CurrentPlan.getInstance().getNumPlaces() > 0) {
            // If the CurrentPlan contains any places, load them into placesListView
            loadPlaces();
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

                // If there were no places in the currentPlan, the RecyclerView.Adapter must be initialized and set here.
                if (PLACES_EMPTY == 1) {
                    loadPlaces();
                    // The ListView is no longer empty, reset the flag.
                    PLACES_EMPTY = 0;
                } else {
                    // If not, update mPlacesAdapter.
                    // TODO: Upadate this code for RecyclerView
                    /** Couldn't get ArrayAdapter.notifyDataSetChanged() to work **/
                    //placesArrayAdapter.notifyDataSetChanged();

                    /** Alt method that recreates the ArrayAdapter. It works, but with a lot of garbage and wastes resources **/
                    //loadPlaces();
                }
            }
        }
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

    /**
     * loadPlaces()
     *  - The purpose of this function is to create and set the RecyclerView.Adapter for places_recycler_view
     *     with the Places from the CurrentPlan.
     */
    public void loadPlaces() {
        PlaceExpandableAdapter mPlaceExpandableAdapter = new PlaceExpandableAdapter(getActivity(), generatePlacesList());
        mPlaceExpandableAdapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        mPlaceExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        mPlaceExpandableAdapter.setParentAndIconExpandOnClick(true);

        mPlacesRecyclerView = (RecyclerView) getActivity().findViewById(R.id.places_recycler_view);
        mPlacesRecyclerView.setAdapter(mPlaceExpandableAdapter);
    }

    private ArrayList<ParentObject> generatePlacesList() {
        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        ArrayList<com.upmoon.alexanderbean.barcrawlr.model.Place> places = CurrentPlan.getInstance().getPlaces();
        for(com.upmoon.alexanderbean.barcrawlr.model.Place place : places) {
            ArrayList<Object> childList = new ArrayList<>();
            childList.add(new PlaceChildObject(place.getAddress()));
            place.setChildObjectList(childList);
            parentObjects.add(place);
        }

        return parentObjects;
    }

    // Begin : Accessory classes used in the RecyclerView

    /**
     * The purpose of this class is to be a custom RecyclerView.Adapter that will expand when a user presses the expand_arrow.
     */
    private class PlaceExpandableAdapter extends ExpandableRecyclerAdapter<PlaceParentViewHolder, PlaceChildViewHolder> {

        LayoutInflater mInflater;

        public PlaceExpandableAdapter(Context context, List<ParentObject> parentList) {
            super(context, parentList);

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public PlaceParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
            View v = mInflater.inflate(R.layout.list_item_place_parent, viewGroup, false);
            return new PlaceParentViewHolder(v);
        }

        @Override
        public PlaceChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
            View v = mInflater.inflate(R.layout.list_item_place_child, viewGroup, false);
            return new PlaceChildViewHolder(v);
        }

        @Override
        public void onBindParentViewHolder(PlaceParentViewHolder placeParentViewHolder, int i, Object parentObject) {
            com.upmoon.alexanderbean.barcrawlr.model.Place place = (com.upmoon.alexanderbean.barcrawlr.model.Place) parentObject;
            placeParentViewHolder.mPlaceNameTextView.setText(place.getName());
        }

        @Override
        public void onBindChildViewHolder(PlaceChildViewHolder placeChildViewHolder, int i, Object childObject) {
            PlaceChildObject placeChildObject = (PlaceChildObject) childObject;
            placeChildViewHolder.mPlaceAddressTextView.setText(placeChildObject.getAddress());
            placeChildViewHolder.mPlaceDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "TODO : Call a delete place function here", Toast.LENGTH_SHORT);
                }
            });
        }

        @Override
        public int getItemCount(){ return CurrentPlan.getInstance().getNumPlaces(); }
    }

    private class PlaceParentViewHolder extends ParentViewHolder {
        TextView mPlaceNameTextView;
        ImageButton mParentDropDownArrow;

        PlaceParentViewHolder(View itemView) {
            super(itemView);

            mPlaceNameTextView = (TextView) itemView.findViewById(R.id.parent_list_item_place_name_text_view);
            mParentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);
        }
    }

    private class PlaceChildViewHolder extends ChildViewHolder {
        TextView mPlaceAddressTextView;
        Button mPlaceDeleteButton;

        PlaceChildViewHolder(View itemView) {
            super(itemView);

            mPlaceAddressTextView = (TextView) itemView.findViewById(R.id.child_list_item_place_address_text_view);
            mPlaceDeleteButton = (Button) itemView.findViewById(R.id.child_list_item_delete_place_button);
        }
    }


    private class PlaceChildObject {
        private String mAddress;

        public PlaceChildObject(String address) {
            mAddress = address;
        }

        public String getAddress() {
            return mAddress;
        }
    }
}