package com.upmoon.alexanderbean.barcrawlr.singletons;

import com.upmoon.alexanderbean.barcrawlr.model.Place;
import com.upmoon.alexanderbean.barcrawlr.model.Plan;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by AlexanderBean on 1/23/2017.
 */
public class CurrentPlan {
    private static CurrentPlan ourInstance = new CurrentPlan();
    private Plan plan;

    public static CurrentPlan getInstance() {
        if (ourInstance == null) {
            ourInstance = new CurrentPlan();
            return ourInstance;
        } else {
            return ourInstance;
        }
    }

    private CurrentPlan() {

    }

    public void setPlan(Plan plan){
        this.plan = plan;
    }

    public String getName() {
        return plan.getName();
    }

    public ArrayList<Place> getPlaces() {
        return plan.getPlaces();
    }

    public int getNumPlaces() {
        return plan.getNumPlaces();
    }

    public Place getPlace(int index) {
        return plan.getPlace(index);
    }

    public void addPlaceToPlan(Place place) {
        plan.addPlace(place);
    }
}
