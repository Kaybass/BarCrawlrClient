package com.upmoon.alexanderbean.barcrawlr.utilities;


import android.content.Context;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * Created by Alex on 1/21/2017.
 */

public class PlanSaver {

    private Context mContext;

    private final String SAVE_DIRECTORY_NAME = "plans";

    public PlanSaver(Context context){

        mContext = context;
    }

    public String savePlan(Plan p){

        String savePath = mContext.getFilesDir().getAbsolutePath() + File.separator + SAVE_DIRECTORY_NAME;

        File saveFolder = new File(savePath);

        if(!saveFolder.exists()){
            saveFolder.mkdirs();
        }

        String[] otherSaves = saveFolder.list();

        String planName;

        if(Arrays.asList(otherSaves).contains(p.getName())){
            planName = p.getName() + "1" + ".plan";
        } else {
            planName = p.getName() + ".plan";
        }

        File saveFile = new File(savePath + File.separator + planName);

        try{
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(p);

            oos.close();
            fos.close();

        } catch(IOException e){
            e.printStackTrace();
        }

        return savePath + File.separator + planName;
    }
}
