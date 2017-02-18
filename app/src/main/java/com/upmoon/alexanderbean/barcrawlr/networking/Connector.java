package com.upmoon.alexanderbean.barcrawlr.networking;

/**
 * Created by Alex on 1/19/2017.
 */


import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;

/**
 * We want connection to
 *
 *  - start (plan or connection key gets sent)
 *  - send location updates
 *  - receive location updates
 *  - notify the server if user is going home
 */
public interface Connector {

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
    String sendPlan(Plan plan, User self);

    /**
     * sendCode
     * @param code
     * @param self
     * @return
     *
     * sendCode in the real world will send a code
     * to the server and if the code is real the server
     * will send status + user list like in sendPlan
     */
    String sendCode(String code, User self);

    /**
     * locationUpdate
     * @param code
     * @param self
     * @return
     *
     * locationUpdate sends the user's location info to
     * the server and in turn the server sends the status
     * of that action back + the information of other users
     */
    String locationUpdate(String code, User self);

    /**
     * disconnect
     * @param code
     * @param self
     * @return
     *
     * disconnect tells the server that the user
     * wishes to stop using the app.
     */
    String disconnect(String code, User self);
}
