package com.a65apps.kalimullinilnazrafilovich.entities;

import io.reactivex.rxjava3.annotations.Nullable;

public class Location {

    @Nullable
    private final Contact contact;

    @Nullable
    private final String address;

    @Nullable
    private final Point point;

    public Location(@Nullable Contact contact,
                    @Nullable String address,
                    @Nullable Point point) {
        this.contact = contact;
        this.address = address;
        this.point = point;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    @Nullable
    public Contact getContact() {
        return contact;
    }

    @Nullable
    public Point getPoint() {
        return point;
    }
}
