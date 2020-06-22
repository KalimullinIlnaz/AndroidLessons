package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactDataBaseLocationRepository implements ContactDataBaseRepository {
    private final AppDatabase database;

    public ContactDataBaseLocationRepository(AppDatabase database){
        this.database = database;
    }

    @Override
    public Single<Contact> getContactLocation(Single<Contact> contactEntitySingle) {
        return contactEntitySingle.map( (contact) -> getDataFromDB(contact))
                .subscribeOn(Schedulers.io());
    }

    private Contact getDataFromDB(Contact contact){
        if (database.locationDao().isExists(contact.getId()) == 1){
            Point point = new Point(
                    database.locationDao().getById(contact.getId()).getLatitude(),
                    database.locationDao().getById(contact.getId()).getLongitude());

            contact.getLocation().setPoint(point);

            contact.getLocation().setAddress(
                    database.locationDao().getById(contact.getId()).getAddress()
            );

        }else {
            contact.getLocation().setAddress("");
        }
        return contact;
    }

}
