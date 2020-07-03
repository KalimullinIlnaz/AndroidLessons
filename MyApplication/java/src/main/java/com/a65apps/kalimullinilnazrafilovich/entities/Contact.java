package com.a65apps.kalimullinilnazrafilovich.entities;

import java.util.GregorianCalendar;
import java.util.Objects;

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
    private final GregorianCalendar dateOfBirth;

    @Nullable
    private final Location location;

    public Contact(@NonNull String id,
                   @NonNull String name,
                   @NonNull String telephoneNumber) {
        this.id = id;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.email = " ";
        this.telephoneNumber2 = " ";
        this.email2 = " ";
        this.description = " ";
        dateOfBirth = null;
        this.location = null;
    }

    public Contact(@NonNull Contact contact,
                   @NonNull GregorianCalendar dateOfBirth,
                   @NonNull String telephoneNumber2,
                   @NonNull String email,
                   @NonNull String email2,
                   @NonNull String description,
                   @Nullable Location location) {
        this.id = contact.getId();
        this.name = contact.getName();
        this.dateOfBirth = dateOfBirth;
        this.telephoneNumber = contact.getTelephoneNumber();
        this.email = email;
        this.telephoneNumber2 = telephoneNumber2;
        this.email2 = email2;
        this.description = description;
        this.location = location;
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
    public GregorianCalendar getDateOfBirth() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return id.equals(contact.id)
                && name.equals(contact.name)
                && telephoneNumber.equals(contact.telephoneNumber)
                && telephoneNumber2.equals(contact.telephoneNumber2)
                && email.equals(contact.email)
                && email2.equals(contact.email2)
                && description.equals(contact.description)
                && Objects.equals(dateOfBirth, contact.dateOfBirth)
                && Objects.equals(location, contact.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                telephoneNumber,
                telephoneNumber2,
                email,
                email2,
                description,
                dateOfBirth,
                location);
    }
}

