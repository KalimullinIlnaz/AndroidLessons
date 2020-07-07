package com.a65apps.kalimullinilnazrafilovich.interactors.location;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LocationRepository {
    void insertContactLocation(Location location, ContactDetailsInfo contactDetailsInfo);

    Single<List<Location>> getAllContactLocations();

    Single<Location> createOrUpdateContactLocationByCoordinate(ContactDetailsInfo contactDetailsInfo,
                                                               double latitude, double longitude);
}
