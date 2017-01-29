package com.upmoon.alexanderbean.barcrawlr;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;
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
            "}," +
            "]" +
            "}";

    private final String SAVE_DIRECTORY_NAME = "plans";

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.upmoon.alexanderbean.barcrawlr", appContext.getPackageName());
    }

    @Test
    public void planSaveTest() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();

        Plan myPlan = new Plan(planJSON);

        PlanSaver ps = new PlanSaver(appContext);

        String p = ps.savePlan(myPlan);

        assertEquals("AlexPlan",appContext.getFilesDir().getAbsolutePath() +
                File.separator + SAVE_DIRECTORY_NAME + File.separator + myPlan.getName());

        File thingIJustSaved = new File(appContext.getFilesDir().getAbsolutePath() +
                File.separator + SAVE_DIRECTORY_NAME + File.separator + myPlan.getName());

        assertEquals(true,thingIJustSaved.exists());
    }

    @Test
    public void planLoadTest() throws Exception{

    }

}
