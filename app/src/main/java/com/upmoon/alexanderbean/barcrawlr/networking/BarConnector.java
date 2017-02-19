package com.upmoon.alexanderbean.barcrawlr.networking;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;

import java.io.IOException;

/**
 * Created by Alex on 1/19/2017.
 */

public class BarConnector implements Connector {

    private String Address, ApiKey;

    public BarConnector(String address, String key){

        Address = address;
        ApiKey = key;
    }

    @Override
    public String sendPlan(Plan plan, User self) {
        return null;
    }

    @Override
    public String sendCode(String code, User self) {
        return null;
    }

    @Override
    public String locationUpdate(String code, User self) {
        return null;
    }

    @Override
    public String disconnect(String code, User self) {
        return null;
    }

    public String barGet(String code, String user) throws IOException {
        return null;
    }

    public String barPost(String plan, String code, String user) throws IOException {
        return null;
    }
}
