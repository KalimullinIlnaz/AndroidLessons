package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;


@Entity
public class LocationOrm {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "contact_id")
    private final String contactID;
    @ColumnInfo(name = "latitude")
    private final double latitude;
    @ColumnInfo(name = "longitude")
    private final double longitude;
    @ColumnInfo(name = "address")
    private final String address;

    public LocationOrm(@NonNull ContactDetailsInfo contactDetailsInfo,
                       @NonNull Location location) {
        this.contactID = contactDetailsInfo.getId();
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

    @NonNull
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
