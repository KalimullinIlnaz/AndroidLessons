package com.a65apps.kalimullinilnazrafilovich.entities;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

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
    private Location location;

    public Contact(@NonNull String id, @NonNull String name, @NonNull String telephoneNumber){
        this.id = id;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.email = " ";
        this.telephoneNumber2  = " ";
        this.email2 = " ";
        this.description = " ";
        this.dateOfBirth = " ";
    }

    public Contact(@NonNull String id, @NonNull String name, @NonNull String dateOfBirth, @NonNull String telephoneNumber,
                   @NonNull String telephoneNumber2, @NonNull String email, @NonNull String email2, @NonNull String description){
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
    public Location getLocation() {
        return location;
    }

    public void setLocation(@Nullable Location location) {
        this.location = location;
    }
}