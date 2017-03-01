package com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceListFragment extends Fragment {

    private RecyclerView mPlacesRecyclerView;
    private PlaceAdapter mPlaceAdapter;

    public PlaceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_place_list_bc, container, false);

        mPlaceAdapter = new com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.PlaceListFragment.PlaceAdapter();
        mPlacesRecyclerView = (RecyclerView) v.findViewById(R.id.places_recycler_view_bc);
        mPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));
        mPlacesRecyclerView.setAdapter(mPlaceAdapter);
        return v;
    }



    // Begin : Accessory classes used in the RecyclerView

    private class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView placeName, placeAddress;

        public PlaceHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            placeName = (TextView) itemView.findViewById(R.id.place_holder_name);

            placeAddress = (TextView) itemView.findViewById(R.id.place_holder_address);
        }

        public void bindPlace(int pos) {
            placeName.setText(CurrentPlan.getInstance().getPlace(pos).getName());

            placeAddress.setText(CurrentPlan.getInstance().getPlace(pos).getAddress());
        }

        @Override
        public void onClick(View v) {

        }
    }i

    private class PlaceAdapter extends RecyclerView.Adapter<com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.PlaceListFragment.PlaceHolder> {
        public PlaceAdapter() {

        }

        @Override
        public com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.PlaceListFragment.PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater LI = LayoutInflater.from(getActivity());
            View view = LI.inflate(R.layout.place_holder,parent,false);
            return new com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.PlaceListFragment.PlaceHolder(view);
        }

        @Override
        public void onBindViewHolder(com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler.PlaceListFragment.PlaceHolder holder, int position) { holder.bindPlace(position); }

        @Override
        public int getItemCount() {
            return CurrentPlan.getInstance().getNumPlaces();
        }
    }

}
