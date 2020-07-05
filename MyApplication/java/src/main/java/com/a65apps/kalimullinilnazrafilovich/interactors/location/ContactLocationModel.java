package com.a65apps.kalimullinilnazrafilovich.interactors.location;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class ContactLocationModel implements ContactLocationInteractor {
    private final transient LocationRepository locationRepository;

    public ContactLocationModel(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void saveContactLocation(Location location, ContactDetailsInfo contactDetailsInfo) {
        locationRepository.insertContactLocation(location, contactDetailsInfo);
    }

    @Override
    public Single<List<Location>> loadAllContactLocations() {
        return locationRepository.getAllContactLocations();
    }

    @Override
    public Single<Location> createOrUpdateContactLocationByCoordinate(ContactDetailsInfo contactDetailsInfo,
                                                                      double latitude,
                                                                      double longitude) {
        return locationRepository.createOrUpdateContactLocationByCoordinate(
                contactDetailsInfo,
                latitude,
                longitude);
    }
}
