package com.upmoon.alexanderbean.barcrawlr.networking.pretendworking;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;
import com.upmoon.alexanderbean.barcrawlr.networking.Connector;

/**
 * Created by Alex on 1/19/2017.
 */


/**
 * pretendworking.BarConnectorSimulator exists to pretend to be
 * A real connection for the sake of prototyping
 */
public class BarConnectorSimulator implements Connector {

    User[] pretendUsers = {new User("Bob", 24,24), new User("Joe", 26,26)};

    public BarConnectorSimulator(){

    }


    @Override
    public String sendPlan(Plan plan, User self) {
        if(plan.getName() != "" &&
                plan.getNumPlaces() > 0 &&
                self.getName() != ""){

            return "{\"status:\"success\",\"\"users\":[" + self.toJson() + "]}";
        }
        else{
            return "{\"error\":\"failure\"}";
        }
    }


    @Override
    public String sendCode(String code, User self) {
        return "{\"status\":\"success\"}";
    }


    @Override
    public String locationUpdate(String code, User self) {
        return "{\"status:\"success\",\"\"users\":[" + self.toJson() +
                "," + pretendUsers[0].toJson() + "," + pretendUsers[1].toJson() + "]}";
    }

    @Override
    public String disconnect(String code, User self) {
        return "{\"status\":\"success\"}";
    }
}
