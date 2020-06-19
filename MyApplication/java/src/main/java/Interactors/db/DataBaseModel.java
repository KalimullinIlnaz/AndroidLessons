package Interactors.db;

import java.util.List;

import Entities.Contact;
import Entities.Location;

public class DataBaseModel implements DataBaseInteractor {
    private final DataBaseRepository dataBaseRepository;

    public DataBaseModel(DataBaseRepository dataBaseRepository){
        this.dataBaseRepository = dataBaseRepository;
    }

    @Override
    public Contact getContactById(String id) {
        return dataBaseRepository.getContactById(id);
    }

    @Override
    public void insertContact(Location location) {
        dataBaseRepository.insertContact(location);
    }

    @Override
    public List<Location> getAllLocations() {
        return dataBaseRepository.getAllLocations();
    }
}
