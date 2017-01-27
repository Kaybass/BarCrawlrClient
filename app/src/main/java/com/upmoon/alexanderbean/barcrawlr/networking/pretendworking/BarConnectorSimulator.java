package com.upmoon.alexanderbean.barcrawlr.networking.pretendworking;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;
import com.upmoon.alexanderbean.barcrawlr.networking.Connector;

/**
 * Created by Alex on 1/19/2017.
 */

public class BarConnectorSimulator implements Connector {

    public BarConnectorSimulator(){

    }

    /**
     * sendPlan
     * @param plan
     * @param self
     * @return
     *
     * In the network simulator sendPlan will check
     * the validity of the plan it is given, in the
     * real world this will be done by the server.
     * If the plan is loosely valid this method will
     * return
     */
    @Override
    public String sendPlan(Plan plan, User self) {
        if(plan.getName() != "" &&
                plan.getNumPlaces() > 0 &&
                self.getName() != ""){

            return "{\"users\":[" + self.toJson() + "]}";
        }
        else{
            return "{\"status\":\"failure\"}";
        }
    }

    @Override
    public String locationUpdate(User self) {
        return null;
    }

    @Override
    public String disconnect() {
        return "{\"status\":\"success\"}";
    }
}
