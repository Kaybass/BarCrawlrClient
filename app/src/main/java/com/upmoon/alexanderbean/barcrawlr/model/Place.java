package com.upmoon.alexanderbean.barcrawlr.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Alex on 1/21/2017.
 */


/**
 * Place is an object that represents the places
 * users plan to go during a bar crawl.
 * "name" is the name of the location
 * "address" is the street address of the location
 * "lon" and "lat" are the coords
 */
public class Place implements Serializable, ParentObject {

    private String name, address;

    private double lon, lat;

    // A list for use with the RecyclerView in PlaceListFragment
    private List<Object> mPlaceChildrenList;

    public Place(){

    }

    public Place(String name, String address, double lon, double lat){

        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    // A constructor for use with the RecyclerView in PlaceListFragment
    public Place(List<Object> placeChildrenList) {
        mPlaceChildrenList = placeChildrenList;
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

    @Override
    public List<Object> getChildObjectList() {
        return mPlaceChildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mPlaceChildrenList = list;
    }
}
