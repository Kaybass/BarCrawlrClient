package com.upmoon.alexanderbean.barcrawlr;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;
import com.upmoon.alexanderbean.barcrawlr.networking.BarConnector;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;


//This would work if we weren't lazy and mocked Android.net.uri
public class NetworkingNonInstrumentedTest {

    private final String APIKEY = "ONLYTHEBIGGESTMEMES";
    private final String ADDRESS = "nickelp.ro:3033";
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
            "}" +
            "]" +
            "}";

    private String code;

    public NetworkingNonInstrumentedTest(){
        code = "";
    }

    @Test
    public void sendPlan_test() throws Exception{
        BarConnector BC = new BarConnector(ADDRESS,APIKEY);
        Plan plan = new Plan(planJSON);
        User user = new User("Alex", 0.0, 0.0);

        String realResultStr = BC.sendPlan(plan,user);

        JSONObject realResult = new JSONObject(realResultStr);

        code = realResult.getString("code");

        assertEquals(planJSON,realResult.getString("plan"));
    }

    @Test
    public void sendCode_test() throws Exception{
        BarConnector BC = new BarConnector(ADDRESS,APIKEY);
        User user = new User("Shmalex", 0.0, 0.0);

        String realResultStr = BC.sendCode(code,user);

        JSONObject realResult = new JSONObject(realResultStr);

        assertEquals(planJSON,realResult.getString("plan"));
    }

    @Test
    public void locationUpdate_test() throws Exception{
        BarConnector BC = new BarConnector(ADDRESS,APIKEY);
        User user = new User("Shmalex", 0.1, 0.1);

        String realResultStr = BC.locationUpdate(code,user);

        JSONObject realResult = new JSONObject(realResultStr);

        assertEquals(user.toJson(),realResult.getJSONObject("user").getJSONArray("user").get(1).toString());
    }

    @Test
    public void disconnect_test() throws Exception{
        BarConnector BC = new BarConnector(ADDRESS,APIKEY);
        User user = new User("Shmalex", 0.1, 0.1);

        String realResultStr = BC.disconnect(code,user);

        assertEquals("{'status': 'Success, user removed'}",realResultStr);
    }
}
