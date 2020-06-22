package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;

@Entity
public class LocationORM {
    public LocationORM(){

    }

    public LocationORM(Contact contact, Location location){
        this.contactID = contact.getId();
        this.address = location.getAddress();
        this.latitude = location.getPoint().getLatitude();
        this.longitude = location.getPoint().getLongitude();
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "contact_id")
    private String contactID;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "address")
    private String address;

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NonNull
    public String getContactID() {
        return contactID;
    }

    public String getAddress() {
        return address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
