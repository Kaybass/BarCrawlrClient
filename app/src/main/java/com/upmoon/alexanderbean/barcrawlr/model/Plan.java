package com.upmoon.alexanderbean.barcrawlr.model;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by AlexanderBean on 12/24/2016.
 */

public class Plan implements Serializable{

    private String name;

    private ArrayList<Place> places;

    private transient int numPlaces;

    public Plan(){

    }

    public Plan(String json){

        try{
            JSONObject plan = new JSONObject(json);

            name = plan.getString("name");

            JSONArray thePlaces = plan.getJSONArray("places");

            numPlaces = thePlaces.length();

            places = new ArrayList<Place>();

            Gson gson = new Gson();

            for(int i = 0; i < thePlaces.length(); i++){

                JSONObject placeJSON = thePlaces.getJSONObject(i);

                places.add(new Place(placeJSON.getString("name"),
                        placeJSON.getString("address"),
                        placeJSON.getDouble("lon"),
                        placeJSON.getDouble("lat")));
            }

        } catch(JSONException e){
        }
    }

    public String toJson(){

        String json = "";

        json += "{\"name\":\"" + name + "\",places:[";

        Gson gson = new Gson();

        for(int i = 0; i < numPlaces; i++){

            json += gson.toJson(places.get(i)) + ",";
        }

        json += "]}";

        return json;
    }

    public String getName() {
        return name;
    }

    public int getNumPlaces() {
        return numPlaces;
    }

    public Place getPlace(int i) {
        if (i < numPlaces){
            return places.get(i);
        }
        else{
            return null;
        }
    }
}
