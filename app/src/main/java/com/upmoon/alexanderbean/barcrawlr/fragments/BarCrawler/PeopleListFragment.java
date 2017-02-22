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
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanSaver;


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



        return v;
    }

}
