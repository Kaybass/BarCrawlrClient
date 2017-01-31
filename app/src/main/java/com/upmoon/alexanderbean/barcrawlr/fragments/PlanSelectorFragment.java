package com.upmoon.alexanderbean.barcrawlr.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanSelectorFragment extends Fragment {

    /**
     * Member variables
     */
    FloatingActionButton mLeftFAB, mRightFAB;

    private ArrayList<File> mPlans;

    private RecyclerView mRecyclerView;
    private PlanAdapter  mAdapter;


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

        mRightFAB = (FloatingActionButton) v.findViewById(R.id.connect_fab);
        mRightFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //RecyclerView
        mAdapter = new PlanAdapter();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.gol_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                                        LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(mAdapter);


        /**
         * Load in plan data
         */

        PlanLoader pl = new PlanLoader(getActivity());
        mPlans = new ArrayList<File>(Arrays.asList(pl.getPlans()));
        mAdapter.notifyDataSetChanged();

        return v;
    }


    /**
     * @TODO write onclicklistener for holder
     */
    private class PlanHolder extends RecyclerView.ViewHolder
                        implements View.OnClickListener{

        private TextView planName, planLastAccessDate;

        public PlanHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            planName = (TextView) itemView.findViewById(R.id.plan_holder_plan_name);

            planLastAccessDate = (TextView) itemView.findViewById(R.id.plan_holder_create_date);
        }

        public void bindPlan(int pos){

            File plan = mPlans.get(pos);

            planName.setText(plan.getName());
            planLastAccessDate.setText(plan.getAbsolutePath());

        }

        @Override
        public void onClick(View v){

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
            return mPlans.size();
        }
    }

}
