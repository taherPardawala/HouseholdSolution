package com.skypunch.householdsolutions;

/**
 * Created by taherPardawala on 25/02/17.
 */

public class ServiceSeeker {

    public String firstName , lastName , middleName , email , password , mobileNumber , residentNumber , address , city;
    public static String userCity;

    public ServiceSeeker() {
    }

    public ServiceSeeker(String firstName, String lastName, String middleName, String email, String password, String mobileNumber, String residentNumber, String address, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.residentNumber = residentNumber;
        this.address = address;
        this.city = city;
    }
}
