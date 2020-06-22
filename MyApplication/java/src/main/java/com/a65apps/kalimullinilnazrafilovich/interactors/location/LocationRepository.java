package com.a65apps.kalimullinilnazrafilovich.interactors.location;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LocationRepository {

    void insertContactLocation(Location location, Contact contact);

    Single<List<Location>> getAllContactLocations();

    Single<Location> getContactLocation(Contact contact, double latitude, double longitude);
}
