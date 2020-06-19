package Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Contact {
    @NonNull
    private final String id;

    @NonNull
    private final String name;

    @NonNull
    private final String telephoneNumber;

    @NonNull
    private final String telephoneNumber2;

    @NonNull
    private final String email;

    @NonNull
    private final String email2;

    @NonNull
    private final String description;

    @NonNull
    private final String dateOfBirth;

    @Nullable
    private String address;

    private double longitude = 0;
    private double latitude = 0;

    public Contact(@NonNull String id,@NonNull String name,@NonNull String telephoneNumber){
        this.id = id;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.email = " ";
        this.telephoneNumber2  = " ";
        this.email2 = " ";
        this.description = " ";
        this.dateOfBirth = " ";
    }

    public Contact(@NonNull String id,@NonNull String name,@NonNull String dateOfBirth,@NonNull  String telephoneNumber,
                   @NonNull String telephoneNumber2, @NonNull String email,@NonNull String email2,@NonNull String description){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.telephoneNumber2 = telephoneNumber2;
        this.email2 = email2;
        this.description = description;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @NonNull
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    @NonNull
    public String getTelephoneNumber2() {
        return telephoneNumber2;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getEmail2() {
        return email2;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getAddress() {
        return address;
    }
    
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }
}