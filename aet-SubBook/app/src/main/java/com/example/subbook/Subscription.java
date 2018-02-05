/**
 * SubBook Application to keep track of users subscriptions
 *
 * Copyright 2018, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under the terms and conditions of the
 * Code of Student Behaviour at University of Alberta.
 */
package com.example.subbook;

/**
 * Representation of a "Subscription" in the app
 *
 * @author Alden Tan
 */
public class Subscription {
    private String name;
    private String date;

    /**
     * Creates a Subscription object
     *
     * @param name
     * @param date
     */
    public Subscription(String name, String date){
        this.name = " Date Started: " + date + " Name - Monthly Charge - and/or Comments: " + name;
    }

    /**
     * Converts Subscription data to string
     *
     * @return "name" as a string
     */
    @Override
    public String toString() {
        return name;
    }
}
