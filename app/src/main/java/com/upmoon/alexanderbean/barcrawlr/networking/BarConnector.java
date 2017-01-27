package com.upmoon.alexanderbean.barcrawlr.networking;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;

/**
 * Created by Alex on 1/19/2017.
 */

public class BarConnector implements Connector {

    public BarConnector(){

    }

    /**
     * public BarConnector(IP ADDRESS)
     */

    @Override
    public String sendPlan(Plan plan, User self) {
        return null;
    }

    @Override
    public String locationUpdate(User self) {
        return null;
    }

    @Override
    public String disconnect() {
        return null;
    }
}
