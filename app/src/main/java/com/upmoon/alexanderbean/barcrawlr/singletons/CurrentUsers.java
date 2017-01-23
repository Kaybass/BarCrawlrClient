package com.upmoon.alexanderbean.barcrawlr.singletons;

/**
 * Created by AlexanderBean on 1/23/2017.
 */
public class CurrentUsers {
    private static CurrentUsers ourInstance = new CurrentUsers();

    public static CurrentUsers getInstance() {
        return ourInstance;
    }

    private CurrentUsers() {
    }
}
