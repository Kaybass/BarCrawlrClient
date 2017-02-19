package com.upmoon.alexanderbean.barcrawlr.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by AlexanderBean on 12/24/2016.
 */


/**
 * Plan is the object that the client uses
 * to organize their bar crawling experience
 * "name" is the name of the plan (i.e "Bob's Birthday")
 * "places" are the locations that are in the plan
 * @see Place
 * @see com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan
 */
public class Plan implements Serializable{

    private String name;

    private ArrayList<Place> places;

    private int numPlaces = 0;

    public Plan(){
        name = "Plan not initialized properly";
        places = new ArrayList<>();
    }

    public Plan(String json){

        try{
            JSONObject plan = new JSONObject(json);

            name = plan.getString("name");

            JSONArray thePlaces = plan.getJSONArray("places");

            places = new ArrayList<>();

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

        json += "{\"name\":\"" + name + "\",\"places\":[";

        for(int i = 0; i < numPlaces; i++){

            json += places.get(i).toJson() + ",";
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

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void addPlace(Place place) {
        places.add(place);
        numPlaces++;
    }

    /**
     * setName
     * @param name
     *
     * This exists in the case that there is already a
     * plan saved under the plan's name
     */
    public void setName(String name) {
        this.name = name;
    }
}
