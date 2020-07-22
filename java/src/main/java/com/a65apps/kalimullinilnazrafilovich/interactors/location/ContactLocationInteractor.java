package com.a65apps.kalimullinilnazrafilovich.interactors.location;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import kotlinx.coroutines.flow.Flow;

public interface ContactLocationInteractor {
    void saveContactLocation(Location location, ContactDetailsInfo contactDetailsInfo);

    Flow<List<Location>> loadAllContactLocations();

    Single<Location> createOrUpdateContactLocationByCoordinate(ContactDetailsInfo contactDetailsInfo,
                                                               double latitude, double longitude);
}
