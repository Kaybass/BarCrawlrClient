package com.upmoon.alexanderbean.barcrawlr;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;
import com.upmoon.alexanderbean.barcrawlr.networking.pretendworking.BarConnectorSimulator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alex on 1/23/2017.
 */

public class PretendWorkingUnitTest {

    @Test
    public void sendPlanTest() throws Exception{

        Plan plan1 = new Plan("{" +
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
                "}");
        Plan plan2 = new Plan("{" +
                "\"name\":\"Alex's Plan\"," +
                "\"places\":[" +
                "]" +
                "}");

        BarConnectorSimulator BCS = new BarConnectorSimulator();

        User me = new User("Alex",0,0);

        assertEquals("{\"status:\"success\",\"\"users\":[" + me.toJson() + "]}",
                BCS.sendPlan(plan1,me)
        );
        assertEquals("{\"status\":\"failure\"}",
                BCS.sendPlan(plan2,me)
        );
    }

}
