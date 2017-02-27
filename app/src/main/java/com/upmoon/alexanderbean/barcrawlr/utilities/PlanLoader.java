package com.upmoon.alexanderbean.barcrawlr.utilities;

import android.content.Context;
import android.util.Log;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

/**
 * Created by Alex on 1/21/2017.
 */

public class PlanLoader {

    private Context mContext;

    private final String SAVE_DIRECTORY_NAME = "plans";

    public PlanLoader(Context context){

        mContext = context;
    }

    public boolean planNameExists(String name){

        String savePath = mContext.getFilesDir().getAbsolutePath() + File.separator + SAVE_DIRECTORY_NAME;

        File saveFolder = new File(savePath);

        name = name + ".plan";

        for(String each : saveFolder.list()){
            Log.d("kek", each + " " + name);
            if(name == each){
                return true;
            }
        }
        return false;
    }

    public File[] getPlans(){

        String savePath = mContext.getFilesDir().getAbsolutePath() + File.separator + SAVE_DIRECTORY_NAME;

        File saveFolder = new File(savePath);

        if(!saveFolder.exists()){
            saveFolder.mkdirs();
            return new File[] {};
        }else{
            return saveFolder.listFiles();
        }
    }

    public Plan loadPlan(String planName){

        String savePath = mContext.getFilesDir().getAbsolutePath() + File.separator + SAVE_DIRECTORY_NAME;

        File saveFolder = new File(savePath);

        if(!saveFolder.exists()){
            return null;
        }

        String[] saves = saveFolder.list();

        if(!Arrays.asList(saves).contains(planName)){
            return null;
        }

        File loadFile = new File(savePath + File.separator + planName);

        try{
            FileInputStream fis = new FileInputStream(loadFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Plan plan = (Plan) ois.readObject();

            ois.close();
            fis.close();

            return plan;

        } catch(IOException | ClassNotFoundException e){
            return null;
        }
    }
}
