package com.upmoon.alexanderbean.barcrawlr.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanLoader;
import com.upmoon.alexanderbean.barcrawlr.PlanCreator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanSelectorFragment extends Fragment {
    /**
     * Member variables
     */
    private ArrayList<File> mPlans;
    FloatingActionButton mLeftFAB, mRightFAB;

    private RecyclerView mRecyclerView;
    private PlanAdapter  mAdapter;


    public PlanSelectorFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
                CurrentPlan.getInstance().setPlan(new Plan());
                Intent intent = new Intent(getActivity(),PlanCreator.class);
                startActivity(intent);
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

        if(mPlans.size() > 0)
        {
            TextView sectionLabel = (TextView) getActivity().findViewById(R.id.section_label);
            TextView selectPlan = (TextView) getActivity().findViewById(R.id.select_plan);
            TextView noPlans = (TextView) getActivity().findViewById(R.id.no_plans_text_view);

            sectionLabel.setVisibility(GONE);
            selectPlan.setVisibility(GONE);
            noPlans.setVisibility(GONE);
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater mi){

        super.onCreateOptionsMenu(menu,mi);

        mi.inflate(R.menu.menu_plan_selector,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            //legal
            case R.id.plan_selector_legal:
                AlertDialog.Builder build = new AlertDialog.Builder(getActivity());

                build.setMessage(R.string.mit_license_message);

                AlertDialog diag = build.create();

                diag.show();

                return true;

            //ABOUT
            case R.id.plan_selector_about:
                return true;

            //HELP
            case R.id.plan_selector_help:
                return true;

            //SETTINGS
            case R.id.plan_selector_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * @TODO write onClickListener for holder
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

            planName.setText(plan.getName().replace(".plan",""));

            String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US).format(plan.lastModified());

            date = "Last Modified: " + date;

            planLastAccessDate.setText(date);

        }

        @Override
        public void onClick(View v){

            // Load the clicked plan.

            // Set the CurrentPlan to plan.

            // Load the PlanCreator Fragment.
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

    private class GetPlan extends AsyncTask<String,String,String> {

        public GetPlan(){
        }

        @Override
        protected String doInBackground(String... meme){

            return "";
        }

        @Override
        protected void onPostExecute(String str){
            Toast.makeText(getActivity(), "Disconnected",Toast.LENGTH_SHORT).show();
        }
    }
}
