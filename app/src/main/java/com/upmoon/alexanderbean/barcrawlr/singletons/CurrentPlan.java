package com.upmoon.alexanderbean.barcrawlr.singletons;

/**
 * Created by AlexanderBean on 1/23/2017.
 */
public class CurrentPlan {
    private static CurrentPlan ourInstance = new CurrentPlan();

    public static CurrentPlan getInstance() {
        return ourInstance;
    }

    private CurrentPlan() {
    }
}
