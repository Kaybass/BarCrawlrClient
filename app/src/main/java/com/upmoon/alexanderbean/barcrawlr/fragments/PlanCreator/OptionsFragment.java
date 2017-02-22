package com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanSaver;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {


    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        final Button savePlan = (Button) v.findViewById(R.id.save_plan_button);
        savePlan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlanSaver planSaver = new PlanSaver(getActivity());

                planSaver.savePlan(CurrentPlan.getInstance().getPlan());

            }
        });

        final Button addPeopleButton = (Button) v.findViewById(R.id.add_people_button);
        addPeopleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: Add users to plan.
                Toast.makeText(getActivity(), "Feature not yet implemented", Toast.LENGTH_LONG).show();
            }
        });

        final Button publishButton = (Button) v.findViewById(R.id.publish_button);
        publishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: Publish the plan.
                Toast.makeText(getActivity(), "Feature not yet implemented", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private class AddPlan extends AsyncTask<String,String,String> {

        public AddPlan(){

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
