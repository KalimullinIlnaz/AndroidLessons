package Interactors.db;

import java.util.List;

import Entities.Contact;
import Entities.Location;

public interface DataBaseInteractor {
    Contact getContactById(String id);

    void insertContact(Location location);

    List<Location> getAllLocations();

}
