package com.skypunch.householdsolutions;

/**
 * Created by taherPardawala on 25/02/17.
 */

public class LastUser {
    public String userType;
    public static String userProfile;

    public LastUser(){

    }

    public LastUser(String userType) {
        this.userType = userType;
        userProfile = userType;
    }
}