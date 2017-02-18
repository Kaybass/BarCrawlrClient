package com.upmoon.alexanderbean.barcrawlr.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Alex on 2/18/2017.
 */

public class Update extends AsyncTask<String,String,String> {

    private Context context;

    public Update(Context context){
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
