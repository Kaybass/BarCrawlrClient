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
    String sendPlan(Plan plan, User self);

    String sendCode(String code);

    String locationUpdate(User self);

    String disconnect();
}
