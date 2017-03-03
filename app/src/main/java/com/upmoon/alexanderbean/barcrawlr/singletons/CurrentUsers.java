package com.upmoon.alexanderbean.barcrawlr.singletons;

import com.google.android.gms.maps.model.LatLng;
import com.upmoon.alexanderbean.barcrawlr.model.User;

import java.util.ArrayList;

/**
 * Created by AlexanderBean on 1/23/2017.
 */
public class CurrentUsers {
    private boolean BAR_CRAWLER = false;

    private static CurrentUsers ourInstance = new CurrentUsers();

    private ArrayList<User>  users = new ArrayList<>();

    public static CurrentUsers getInstance() {
        return ourInstance;
    }

    private CurrentUsers() {
    }

    public void setBarCrawler() {
        BAR_CRAWLER = true;
    }

    public int getNumUsers() {
        return users.size();
    }

    public String getUserName(int index) {
        return users.get(index).getName();
    }

    public LatLng getUserLocation(int index) {

        if(BAR_CRAWLER) {

            LatLng userLocation = new LatLng(users.get(index).getLat(), users.get(index).getLon());

            return userLocation;
        }

        return null;
    }

    public void updateUserLocations() {
        // TODO: update users ArrayList locations
    }
}
