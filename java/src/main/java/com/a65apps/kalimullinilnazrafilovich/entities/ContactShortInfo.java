package com.a65apps.kalimullinilnazrafilovich.entities;

import io.reactivex.rxjava3.annotations.NonNull;

public class ContactShortInfo {
    @NonNull
    private final String id;

    @NonNull
    private final String name;

    @NonNull
    private final String telephoneNumber;

    public ContactShortInfo(@NonNull String id,
                            @NonNull String name,
                            @NonNull String telephoneNumber) {
        this.id = id;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
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
    public String getTelephoneNumber() {
        return telephoneNumber;
    }
}
