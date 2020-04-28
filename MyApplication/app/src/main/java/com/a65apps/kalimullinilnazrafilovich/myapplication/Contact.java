package com.a65apps.kalimullinilnazrafilovich.myapplication;

public class Contact {
    private String name;
    private String telephoneNumber;
    private String telephoneNumber2;
    private String email;
    private String email2;
    private String description;

    Contact(String name, String telephoneNumber, String email){
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setTelephoneNumber2(String telephoneNumber2) {
        this.telephoneNumber2 = telephoneNumber2;
    }
}