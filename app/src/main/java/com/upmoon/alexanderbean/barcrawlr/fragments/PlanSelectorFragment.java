package com.upmoon.alexanderbean.barcrawlr.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upmoon.alexanderbean.barcrawlr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanSelectorFragment extends Fragment {

    FloatingActionButton mLeftFAB;

    private final String planJSON = "{" +
            "\"name\":\"AlexPlan\"," +
            "\"places\":[" +
            "{" +
            "\"name\":\"Joe's Bar\"," +
            "\"address\":\"10 King's Street, Burlington, 05401 VT\"," +
            "\"lon\":0.0," +
            "\"lat\":0.0" +
            "}," +
            "{" +
            "\"name\":\"Bob's Bar\"," +
            "\"address\":\"11 King's Street, Burlington, 05401 VT\"," +
            "\"lon\":0.1," +
            "\"lat\":0.1" +
            "}," +
            "]" +
            "}";


    public PlanSelectorFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        return v;
    }

    private class PlanHolder extends RecyclerView.ViewHolder{

        public PlanHolder(View itemView){

            super(itemView);
        }

        public void bindPlan(int pos){

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
            return 0;
        }
    }

}
