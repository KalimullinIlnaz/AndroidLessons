package com.a65apps.kalimullinilnazrafilovich.myapplication.models;

public class Contact {
    private final String id;
    private final String name;
    private final String telephoneNumber;
    private final String telephoneNumber2;
    private final String email;
    private final String email2;
    private final String description;
    private final String dateOfBirth;
    private double longitude = 0;
    private double latitude = 0;
    private String address;

    public Contact(String id,String name,String telephoneNumber){
        this.id = id;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.email = " ";
        this.telephoneNumber2  = " ";
        this.email2 = " ";
        this.description = " ";
        this.dateOfBirth = " ";
    }

    public Contact(String id,String name, String dateOfBirth,  String telephoneNumber, String telephoneNumber2, String email, String email2, String description){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.telephoneNumber2 = telephoneNumber2;
        this.email2 = email2;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getTelephoneNumber2() {
        return telephoneNumber2;
    }

    public String getEmail() {
        return email;
    }

    public String getEmail2() {
        return email2;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}