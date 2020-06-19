package com.a65apps.kalimullinilnazrafilovich.myapplication.repositories;


import com.a65apps.kalimullinilnazrafilovich.myapplication.app.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import Entities.Contact;
import Entities.Location;
import Interactors.db.DataBaseRepository;
import Interactors.details.ContactDetailsRepository;

public class DataBaseRepositoryImpl implements DataBaseRepository {
    private final AppDatabase database;
    private final ContactDetailsRepository contactDetailsRepository;

    public DataBaseRepositoryImpl(AppDatabase database, ContactDetailsRepository contactDetailsRepository){
        this.database = database;
        this.contactDetailsRepository = contactDetailsRepository;
    }


    @Override
    public Contact getContactById(String id) {
        Contact contact = contactDetailsRepository.getDetailContact(id);

        if (database.locationDao().isExists(id) == 1){
            contact.setLatitude(database.locationDao().getById(contact.getId()).getLatitude());
            contact.setLongitude(database.locationDao().getById(contact.getId()).getLongitude());
            contact.setAddress(database.locationDao().getById(contact.getId()).getAddress());
        }else {
            contact.setAddress("");
        }

        return contact;
    }

    @Override
    public void insertContact(Location location) {
//        Location location = new Location(id);
//        location.setAddress(address);
//        location.setLatitude(latitude);
//        location.setLongitude(longitude);

         database.locationDao().insert(location);
    }

    @Override
    public List<Location> getAllLocations() {
        return database.locationDao().getAll();
    }


   /* @Override
    public void insertContact(String id, String address, double latitude, double longitude) {

    }*/

}
