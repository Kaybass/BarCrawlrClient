package com.upmoon.alexanderbean.barcrawlr;

import com.upmoon.alexanderbean.barcrawlr.model.Place;
import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;
import com.upmoon.alexanderbean.barcrawlr.singletons.CurrentPlan;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alex on 1/26/2017.
 */

public class ModelUnitTest {


    @Test
    public void place_CreateCorrect() throws Exception{

        Place place = new Place("bingo","bango",1,2);

        assertEquals("{\"name\":\"bingo\",\"address\":\"bango\",\"lon\":1.0,\"lat\":2.0}",place.toJson());
    }


    @Test
    public void plan_CreateCorrect() throws Exception{

        String planJSON = "{" +
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

        Plan plan = new Plan(planJSON);

        assertEquals("Alex's Plan",plan.getName());
        assertEquals(2,plan.getNumPlaces());
        assertEquals("Joe's Bar",plan.getPlace(0).getName());
        assertEquals(planJSON,plan.toJson());

    }

    @Test
    public void user_CreateCorrect() throws Exception{

        User user = new User("Alex",0.0,0.0);

        assertEquals("Alex",user.getName());

        assertEquals("{\"name\":\"Alex\",\"lon\":0.0,\"lat\":0.0}",user.toJson());
    }

    @Test
    public void plan_AddCorrect() throws Exception{

        Plan plan = new Plan();

        Place place = new Place("bingo","bango",1,2);

        plan.addPlace(place);

        assertEquals("bingo",plan.getPlace(0).getName());

        CurrentPlan.getInstance().setPlan(plan);

        assertEquals("bingo",CurrentPlan.getInstance().getPlace(0).getName());
    }
}
