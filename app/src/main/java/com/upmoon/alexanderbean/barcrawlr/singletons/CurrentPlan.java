package com.upmoon.alexanderbean.barcrawlr.singletons;

import com.upmoon.alexanderbean.barcrawlr.model.Place;
import com.upmoon.alexanderbean.barcrawlr.model.Plan;

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
        plan = new Plan();
    }

    public String getName() {
        return plan.getName();
    }

    public ArrayList<String> getPlacesNames() {
        //return plan.getPlaces();
        if (plan.getNumPlaces() > 0) {
            ArrayList<String> places = new ArrayList<>();
            for (int i = 0; i < this.getNumPlaces(); i++) {
                places.add(this.getPlace(i).getName());
            }
            return places;
        } else {
            return null;
        }
    }

    public int getNumPlaces() {
        return plan.getNumPlaces();
    }

    public Place getPlace(int index) {
        return plan.getPlace(index);
    }

    public void addPlaceToPlan(Place place) {
        plan.addPlace(place);
        return;
    }
}
