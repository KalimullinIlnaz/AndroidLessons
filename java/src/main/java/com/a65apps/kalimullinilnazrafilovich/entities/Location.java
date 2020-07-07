package com.a65apps.kalimullinilnazrafilovich.entities;

import io.reactivex.rxjava3.annotations.Nullable;

public class Location {

    @Nullable
    private final ContactDetailsInfo contactDetailsInfo;

    @Nullable
    private final String address;

    @Nullable
    private final Point point;

    public Location(@Nullable ContactDetailsInfo contactDetailsInfo,
                    @Nullable String address,
                    @Nullable Point point) {
        this.contactDetailsInfo = contactDetailsInfo;
        this.address = address;
        this.point = point;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    @Nullable
    public ContactDetailsInfo getContactDetailsInfo() {
        return contactDetailsInfo;
    }

    @Nullable
    public Point getPoint() {
        return point;
    }
}
