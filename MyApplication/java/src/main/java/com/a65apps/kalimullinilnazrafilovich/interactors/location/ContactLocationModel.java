package com.a65apps.kalimullinilnazrafilovich.interactors.location;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class ContactLocationModel implements ContactLocationInteractor {
    private final LocationRepository locationRepository;

    public ContactLocationModel(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    @Override
    public void saveContactLocation(Location location, Contact contact) {
        locationRepository.insertContactLocation(location, contact);
    }

    @Override
    public Single<List<Location>> loadAllContactLocations() {
        return locationRepository.getAllContactLocations();
    }

    @Override
    public Single<Location> createOrUpdateContactLocationByCoordinate(Contact contact, double latitude, double longitude) {
        return locationRepository.createOrUpdateContactLocationByCoordinate(contact,latitude, longitude);
    }
}
