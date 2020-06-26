package com.a65apps.kalimullinilnazrafilovich.entities;

public class BirthdayNotification {
    private final String idContact;
    private final String nameContact;
    private final String textNotification;

    BirthdayNotification(String idContact, String nameContact, String textNotification){
        this.nameContact = nameContact;
        this.textNotification = textNotification;
        this.idContact = idContact;
    }

    public String getIdContact() {
        return idContact;
    }

    public String getNameContact() {
        return nameContact;
    }

    public String getTextNotification() {
        return textNotification;
    }
}
