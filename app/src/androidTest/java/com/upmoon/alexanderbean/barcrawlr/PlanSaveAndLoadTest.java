package com.upmoon.alexanderbean.barcrawlr;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanLoader;
import com.upmoon.alexanderbean.barcrawlr.utilities.PlanSaver;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by AlexanderBean on 1/27/2017.
 */

@RunWith(AndroidJUnit4.class)
public class PlanSaveAndLoadTest {

    private final String planJSON = "{" +
            "\"name\":\"AlexPlan\"," +
            "\"places\":[" +
            "{" +
            "\"name\":\"Joe's Bar\"," +
            "\"address\":\"10 King's Street, Burlington, 05401 VT\"," +
            "\"lon\":0.0," +
            "\"lat\":0.0" +
            "}," +
            "{" +
            "\"name\":\"Bob's Bar\"," +
            "\"address\":\"11 King's Street, Burlington, 05401 VT\"," +
            "\"lon\":0.1," +
            "\"lat\":0.1" +
            "}" +
            "]" +
            "}";

    private final String SAVE_DIRECTORY_NAME = "plans";

    @Test
    public void planSaveTest() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();

        Plan myPlan = new Plan(planJSON);

        PlanSaver ps = new PlanSaver(appContext);

        String myFile = ps.savePlan(myPlan);


        File thingIJustSaved = new File(appContext.getFilesDir().getAbsolutePath() +
                File.separator + SAVE_DIRECTORY_NAME + File.separator + myFile);

        assertEquals(true,thingIJustSaved.exists());
    }

    @Test
    public void planLoadTest() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();

        Plan myPlan;

        PlanLoader pl = new PlanLoader(appContext);

        String[] s = pl.getPlans();

        assertEquals("AlexPlan.plan",s[0]);

        myPlan = pl.loadPlan(s[0]);

        assertEquals("AlexPlan",myPlan.getName());

        assertEquals(2,myPlan.getNumPlaces());
    }

}
