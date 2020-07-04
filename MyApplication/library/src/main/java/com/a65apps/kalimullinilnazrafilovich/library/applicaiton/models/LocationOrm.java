package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;


@Entity
public class LocationOrm {
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

    public LocationOrm(@NonNull Contact contact,
                       @NonNull Location location) {
        this.contactID = contact.getId();
        this.address = location.getAddress();
        this.latitude = location.getPoint().getLatitude();
        this.longitude = location.getPoint().getLongitude();
    }

    public LocationOrm(@NonNull String contactID,
                       double latitude,
                       double longitude,
                       @NonNull String address) {
        this.contactID = contactID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    @NonNull
    public String getContactID() {
        return contactID;
    }

    public void setContactID(@NonNull String contactID) {
        this.contactID = contactID;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
