package com.a65apps.kalimullinilnazrafilovich.entities;

public class Location {

    private Contact contact;

    private String address;

    private Point point;

    public Location(){

    }

    public Location(Contact contact, String address, Point point){
        this.contact = contact;
        this.address = address;
        this.point = point;
    }

    public String getAddress() {
        return address;
    }

    public Contact getContact() {
        return contact;
    }

    public Point getPoint() {
        return point;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
