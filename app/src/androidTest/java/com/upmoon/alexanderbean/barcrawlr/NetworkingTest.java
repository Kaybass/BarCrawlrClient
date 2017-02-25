package com.upmoon.alexanderbean.barcrawlr;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;
import com.upmoon.alexanderbean.barcrawlr.networking.BarConnector;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by AlexanderBean on 1/27/2017.
 */

@RunWith(AndroidJUnit4.class)
public class NetworkingTest {

    private final String APIKEY = "ONLYTHEBIGGESTMEMES";
    private final String ADDRESS = "http://www.nickelp.ro:3033";
    private final String planJSON = "{" +
            "\"name\":\"Alex's Plan\"," +
            "\"places\":[" +
            "{" +
            "\"name\":\"Joe's Bar\"," +
            "\"address\":\"10 King's Street, Burlington, 05401 VT\"," +
            "\"lon\":0.0," +
            "\"lat\":0.0" +
            "}," +
            "{" +
            "\"name\":\"Bob's Bar\"," +
            "\"address\":\"11 King's Street, Burlington, 05401 VT\"," +
            "\"lon\":0.1," +
            "\"lat\":0.1" +
            "}," +
            "]" +
            "}";

    private String code;

    public NetworkingTest(){
        code = "";
    }

    @Test
    public void sendPlan_test() throws Exception{
        BarConnector BC = new BarConnector(ADDRESS,APIKEY);
        Plan plan = new Plan(planJSON);
        User user = new User("Alex", 0.0, 0.0);

        String realResultStr = BC.sendPlan(plan,user);

        assertEquals("keke",realResultStr);

        JSONObject realResult = new JSONObject(realResultStr);

        code = realResult.getString("code");

        assertEquals(planJSON,realResult.getString("plan"));
    }

    @Test
    public void sendCode_test() throws Exception{
        BarConnector BC = new BarConnector(ADDRESS,APIKEY);
        User user = new User("Shmalex", 0.0, 0.0);

        String realResultStr = BC.sendCode("jCAC",user);

        JSONObject realResult = new JSONObject(realResultStr);

        assertEquals(planJSON,realResult.getString("plan"));
    }

    @Test
    public void locationUpdate_test() throws Exception{
        BarConnector BC = new BarConnector(ADDRESS,APIKEY);
        User user = new User("Shmalex", 0.1, 0.1);

        String realResultStr = BC.locationUpdate("jCAC",user);

        JSONObject realResult = new JSONObject(realResultStr);

        assertEquals(user.toJson(),realResult.getJSONArray("users").get(2).toString());
    }

    @Test
    public void disconnect_test() throws Exception{
        BarConnector BC = new BarConnector(ADDRESS,APIKEY);
        User user = new User("Shmalex", 0.1, 0.1);
        User user2 = new User("Alex", 0.0, 0.0);
        User user3 = new User("beanMan", 0.0, 0.0);
        User user4 = new User("beonMan", 0.0, 0.0);

        String realResultStr = BC.disconnect("jCAC",user);

        BC.disconnect(code,user2);
        BC.disconnect(code,user3);
        BC.disconnect(code,user4);

        assertEquals("{\"status\": \"Success, user removed\"}",realResultStr);
    }
}