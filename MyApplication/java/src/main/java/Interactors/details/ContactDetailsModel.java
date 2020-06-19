package Interactors.details;

import Entities.Contact;
import Interactors.db.DataBaseRepository;

public class ContactDetailsModel implements ContactDetailsInteractor {
    private final DataBaseRepository dataBaseRepository;

    public ContactDetailsModel(DataBaseRepository dataBaseRepository){
        this.dataBaseRepository = dataBaseRepository;
    }

    @Override
    public Contact loadDetailsContact(String id) {
        return dataBaseRepository.getContactById(id);
    }
}
