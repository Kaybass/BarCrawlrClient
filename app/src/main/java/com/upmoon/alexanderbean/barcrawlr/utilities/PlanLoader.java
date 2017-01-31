package com.upmoon.alexanderbean.barcrawlr.utilities;

import android.content.Context;

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
            return new Plan();
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
