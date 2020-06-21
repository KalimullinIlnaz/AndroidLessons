package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;


import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase;

import java.util.List;

import entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.Location;

import interactors.details.ContactDetailsRepository;

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
         database.locationDao().insert(location);
    }

    @Override
    public List<Location> getAllLocations() {
        return database.locationDao().getAll();
    }

}
