package com.upmoon.alexanderbean.barcrawlr.singletons;

import com.upmoon.alexanderbean.barcrawlr.model.Place;
import com.upmoon.alexanderbean.barcrawlr.model.Plan;

/**
 * Created by AlexanderBean on 1/23/2017.
 */
public class CurrentPlan {
    private static CurrentPlan ourInstance = new CurrentPlan();
    public Plan plan;

    public static CurrentPlan getInstance() {
        return ourInstance;
    }

    private CurrentPlan() {
    }

    public void addPlaceToPlan(Place place) {
        plan.addPlace(place);
        return;
    }
}
