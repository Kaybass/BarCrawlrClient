package com.upmoon.alexanderbean.barcrawlr.model;

/**
 * Created by Alex on 1/22/2017.
 */

public class User {

    private String mNick;

    float mLat, mLon;

    public User(String nick, float lat, float lon){
        mNick = nick;
        mLat = lat;
        mLon = lon;
    }

    public String getNick(){
        return mNick;
    }

    public float getLat(){
        return mLat;
    }

    public float getLon(){
        return mLon;
    }
}
