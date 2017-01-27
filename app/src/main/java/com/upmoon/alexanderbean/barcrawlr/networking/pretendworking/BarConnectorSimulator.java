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
     * return a json object that contains the status
     * of the plan and the list of current users (
     * which will only contain the user who sent the
     * plan)
     */
    @Override
    public String sendPlan(Plan plan, User self) {
        if(plan.getName() != "" &&
                plan.getNumPlaces() > 0 &&
                self.getName() != ""){

            return "{\"status:\"success\",\"\"users\":[" + self.toJson() + "]}";
        }
        else{
            return "{\"status\":\"failure\"}";
        }
    }

    /**
     * sendCode
     * @param code
     * @return
     *
     * sendCode in the real world will send a code
     * to the server and if the code is real the server
     * will send status + user list like in sendPlan
     */
    @Override
    public String sendCode(String code) {
        return null;
    }

    /**
     * locationUpdate
     * @param self
     * @return
     *
     * locationUpdate sends the user's location info to
     * the server and in turn the server sends the status
     * of that action back + the information of other users
     */
    @Override
    public String locationUpdate(User self) {
        return null;
    }

    /**
     * disconnect
     * @return
     *
     * disconnect tells the server that the user
     * wishes to stop using the app.
     */
    @Override
    public String disconnect() {
        return "{\"status\":\"success\"}";
    }
}
