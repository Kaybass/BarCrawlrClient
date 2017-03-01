package com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.upmoon.alexanderbean.barcrawlr.BarCrawler;
import com.upmoon.alexanderbean.barcrawlr.R;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanSaver;


public class PeopleListFragment extends Fragment {

    private RecyclerView mPeopleRecyclerView;
    private PeopleAdapter mPeopleAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_people_list, container, false);

        mPeopleAdapter = new PeopleAdapter();
        mPeopleRecyclerView = (RecyclerView) v.findViewById(R.id.people_recycler_view_bc);
        mPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));
        mPeopleRecyclerView.setAdapter(mPeopleAdapter);

        return v;
    }

    private class PeopleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView personName;

        public PeopleHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            personName = (TextView) itemView.findViewById(R.id.people_holder_name);
        }

        public void bindPlace(int pos) {
            personName.setText(CurrentPlan.getInstance().getPerson(pos));
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class PeopleAdapter extends RecyclerView.Adapter<PeopleHolder> {
        public PeopleAdapter() {

        }

        @Override
        public PeopleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater LI = LayoutInflater.from(getActivity());
            View view = LI.inflate(R.layout.people_holder,parent,false);
            return new PeopleHolder(view);
        }

        @Override
        public void onBindViewHolder(PeopleHolder holder, int pos) {
            holder.bindPlace(pos);
        }

        @Override
        public int getItemCount() {
            return CurrentPlan.getInstance().getNumPeople();
        }
    }
}
