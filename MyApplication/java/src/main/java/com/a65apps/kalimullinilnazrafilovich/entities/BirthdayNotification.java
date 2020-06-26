package com.a65apps.kalimullinilnazrafilovich.entities;

import io.reactivex.rxjava3.annotations.NonNull;

public class BirthdayNotification {
    @NonNull
    private final String idContact;

    @NonNull
    private final String nameContact;

    @NonNull
    private final String dateOfBithday;

    private final boolean status;

    public BirthdayNotification(@NonNull String idContact,@NonNull String nameContact,@NonNull String dateOfBithday, boolean status){
        this.idContact = idContact;
        this.nameContact = nameContact;
        this.dateOfBithday = dateOfBithday;
        this.status = status;
    }

    @NonNull
    public String getIdContact() {
        return idContact;
    }

    @NonNull
    public String getNameContact() {
        return nameContact;
    }

    @NonNull
    public String getDateOfBithday() {
        return dateOfBithday;
    }

    public boolean isStatus() {
        return status;
    }
}
