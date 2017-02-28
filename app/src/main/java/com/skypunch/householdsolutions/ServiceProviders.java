package com.skypunch.householdsolutions;

/**
 * Created by taherPardawala on 22/02/17.
 */

public class ServiceProviders {
     public String firstName , lastName , middleName , email , password , mobileNumber , residentNumber , address , city , service , service_city;

    ServiceProviders(){

    }

    public ServiceProviders(String firstName, String lastName, String middleName, String email, String password, String mobileNumber, String residentNumber, String address, String city, String service , String service_city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.residentNumber = residentNumber;
        this.address = address;
        this.city = city;
        this.service = service;
        this.service_city = service_city;
    }
}
