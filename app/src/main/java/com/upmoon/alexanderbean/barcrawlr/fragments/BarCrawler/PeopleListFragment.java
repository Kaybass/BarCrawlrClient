package com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.upmoon.alexanderbean.barcrawlr.BarCrawler;
import com.upmoon.alexanderbean.barcrawlr.R;


public class PeopleListFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_people_list, container, false);

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

}
