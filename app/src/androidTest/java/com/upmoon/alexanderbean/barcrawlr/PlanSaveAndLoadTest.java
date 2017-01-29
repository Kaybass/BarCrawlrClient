package com.upmoon.alexanderbean.barcrawlr;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by AlexanderBean on 1/27/2017.
 */

@RunWith(AndroidJUnit4.class)
public class PlanSaveAndLoadTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.upmoon.alexanderbean.barcrawlr", appContext.getPackageName());
    }

    @Test
    public void planSaveTest() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();


    }
}
