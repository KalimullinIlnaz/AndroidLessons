package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;
import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.LocationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.mapper.YandexDTOMapper;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.LocationORM;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.services.YandexGeocodeService;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactLocationRepository implements LocationRepository {
    private final AppDatabase database;
    private final Context context;
    private final ContactDetailsContentResolverRepository contactDetailsContentResolverRepository;

    public ContactLocationRepository(AppDatabase database, Context context, ContactDetailsContentResolverRepository contactDetailsContentResolverRepository){
        this.database = database;
        this.context = context;
        this.contactDetailsContentResolverRepository = contactDetailsContentResolverRepository;
    }

    @Override
    public void insertContactLocation(Location location, Contact contact) {
        LocationORM locationORM = new LocationORM(contact,location);
        database.locationDao().insert(locationORM);
    }

    @Override
    public Single<List<Location>> getAllContactLocations() {
        return Single.fromCallable( () -> {
            List<LocationORM> locationORMList =  database.locationDao().getAll();
            List<Location> locations = new ArrayList<>();
            for (LocationORM locationORM:locationORMList) {
                contactDetailsContentResolverRepository.getDetailsContact(locationORM.getContactID())
                .subscribe(
                        ( (contactORM) -> {
                            Contact contact = contactORM;
                            locations.add(new Location(
                                    contact,
                                    locationORM.getAddress(),
                                    new Point(locationORM.getLatitude(), locationORM.getLongitude())
                                    )
                            );
                        })
                );

            }
            return locations;
        });
    }

    @Override
    public Single<Location> getContactLocation(Contact contact, double latitude, double longitude) {
        String coordinate = latitude + "," + longitude;

        return YandexGeocodeService.getInstance()
                .getJSONApi()
                .getLocation(
                        coordinate,
                        context.getResources().getString(R.string.yandex_maps_key))
                .subscribeOn(Schedulers.io())
                .map( (responseDTO) ->
                        new YandexDTOMapper().transform(contact, responseDTO, latitude, longitude));
    }
}
