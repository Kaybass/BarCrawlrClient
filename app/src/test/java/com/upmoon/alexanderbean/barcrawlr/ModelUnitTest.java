package com.upmoon.alexanderbean.barcrawlr;

import com.upmoon.alexanderbean.barcrawlr.model.Place;
import com.upmoon.alexanderbean.barcrawlr.model.Plan;

import org.junit.Test;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alex on 1/26/2017.
 */

public class ModelUnitTest {


    @Test
    public void place_CreateCorrect() throws Exception{

        Place place = new Place("bingo","bango",1,2);

        assertEquals("{\"name\":\"bingo\",\"address\":\"bango\",\"lat\":1.0,\"lon\":2.0}",place.toJson());
    }


    @Test
    public void plan_CreateCorrect() throws Exception{

        String planJSON = "{" +
                "\"name\":\"Alex's Plan\"," +
                "\"places\":[" +
                "{" +
                "\"name\":\"Joe's Bar\"," +
                "\"address\":\"10 King's Street, Burlington, 05401 VT\"," +
                "\"lon\" : 0.0," +
                "\"lat\" : 0.0" +
                "}," +
                "{" +
                "\"name\":\"Bob's Bar\"," +
                "\"address\":\"11 King's Street, Burlington, 05401 VT\"," +
                "\"lon\" : 0.1," +
                "\"lat\" : 0.1" +
                "}," +
                "]" +
                "}";

        Plan plan = new Plan(planJSON);

        assertEquals("Alex's Plan",plan.getName());
        assertEquals(2,plan.getNumPlaces());
        assertEquals("Joe's Bar",plan.getPlace(0).getName());

        assertEquals(planJSON,plan.toJson());

    }

    @Test
    public void user_CreateCorrect() throws Exception{


    }
}
