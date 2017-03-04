package com.upmoon.alexanderbean.barcrawlr.singletons;

import com.upmoon.alexanderbean.barcrawlr.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AlexanderBean on 1/23/2017.
 */
public class CurrentUsers {

    private boolean planIsActive;

    private static CurrentUsers ourInstance = new CurrentUsers();

    private ArrayList<User> usersList;

    private String self;

    public static CurrentUsers getInstance() {
        return ourInstance;
    }

    private CurrentUsers() {
        planIsActive = false;
    }

    public boolean isActive(){
        return planIsActive;
    }

    public void setPlanIsActive(boolean planIsActive) {
        this.planIsActive = planIsActive;
    }

    public boolean loadUsers(JSONObject users){
        try{
            JSONArray theUsers = users.getJSONArray("users");

            usersList = new ArrayList<>();

            for (int i = 0; i < theUsers.length(); i++){
                JSONObject user = theUsers.getJSONObject(i);

                usersList.add(new User(user.getString("name"),
                        user.getDouble("lon"),user.getDouble("lat")));
            }
            return true;
        }
        catch(JSONException e) {
            return false;
        }
    }

    public ArrayList<User> getUsers() {
        return usersList;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }
}
