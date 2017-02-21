package com.upmoon.alexanderbean.barcrawlr.fragments.BarCrawler;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.upmoon.alexanderbean.barcrawlr.R;

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
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    private class Disconnect extends AsyncTask<String,String,String> {

        private Context context;

        public Disconnect(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... meme){

            return "";
        }

        @Override
        protected void onPostExecute(String str){
            Toast.makeText(context, "Disconnected",Toast.LENGTH_SHORT).show();
        }
    }
}
