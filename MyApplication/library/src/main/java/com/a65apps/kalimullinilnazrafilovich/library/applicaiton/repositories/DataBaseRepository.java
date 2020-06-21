package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;


import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.Location;

import java.util.List;

import entities.Contact;

public interface DataBaseRepository {

    Contact getContactById(String id);

    void insertContact(Location location);

    List<Location> getAllLocations();

}
