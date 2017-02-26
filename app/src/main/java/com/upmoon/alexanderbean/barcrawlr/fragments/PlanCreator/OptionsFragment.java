package com.upmoon.alexanderbean.barcrawlr.fragments.PlanCreator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanSaver;
import com.upmoon.alexanderbean.barcrawlr.R;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends PreferenceFragmentCompat {


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

                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the Layout resource
        addPreferencesFromResource(R.xml.fragment_plan_creator_options);
    }

    private class AddPlan extends AsyncTask<JSONObject,String,String> {

        public AddPlan(){

        }

        @Override
        protected String doInBackground(JSONObject... meme){

            return "";
        }

        @Override
        protected void onPostExecute(String str){
            Toast.makeText(getActivity(), "Disconnected",Toast.LENGTH_SHORT).show();
        }
    }
}
