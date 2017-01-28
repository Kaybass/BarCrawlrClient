package com.upmoon.alexanderbean.barcrawlr.model;



/**
 * Created by Alex on 1/22/2017.
 */

/**
 * User is the object used to represent the
 * users using the current plan
 * "name" is a nick created by the user
 * "lon" and "lat" are the user's coords
 * @see com.upmoon.alexanderbean.barcrawlr.singletons.CurrentUsers
 */
public class User {

    private String name;

    double lon, lat;

    public User(){

    }

    public User(String nick, double lon, double lat){
        this.name = nick;
        this.lat = lat;
        this.lon = lon;
    }

    public String toJson(){

        return "{\"name\":\"" + name + "\",\"lon\":" +
                Double.toString(lon) + ",\"lat\":" +
                Double.toString(lat) + "}";
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
