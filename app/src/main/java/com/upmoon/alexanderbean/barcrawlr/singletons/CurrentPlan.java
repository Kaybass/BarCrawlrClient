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
    private String code;

    private boolean imported;

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

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    public void setPlan(Plan plan){
        this.plan = plan;
    }

    public String getName() {
        return plan.getName();
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

    public void removePlaceFromPlan(int index) {
        plan.removePlace(index);
    }

    public Plan getPlan() {
        return plan;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
