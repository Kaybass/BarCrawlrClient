package com.upmoon.alexanderbean.barcrawlr.model;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Alex on 1/21/2017.
 */


/**
 *
 */
public class Place implements Serializable{

    private String name, address;

    private double lon, lat;

    public Place(){

    }

    public Place(String name, String address, double lon, double lat){

        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    public String toJson(){

        return "{\"name\":\"" + name + "\",\"address\":\"" +
                address + "\",\"lon\":" + Double.toString(lon) +
                ",\"lat\":" + Double.toString(lat) + "}";
    }

    public String getName(){

        return name;
    }

    public String getAddress(){

        return address;
    }

    public double getLon(){

        return lon;
    }

    public double getLat(){

        return lat;
    }
}
