package com.upmoon.alexanderbean.barcrawlr.model;

import java.io.Serializable;

/**
 * Created by Alex on 1/21/2017.
 */

public class Place implements Serializable{

    private String mName, mAddress;

    private float mLat, mLon;

    public Place(String name, String address, float lat, float lon){

        mName = name;
        mAddress = address;
        mLat = lat;
        mLon = lon;
    }

    public String jsonify(){

        return "";
    }
}
