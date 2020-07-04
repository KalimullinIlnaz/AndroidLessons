package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.content.Context;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;
import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.LocationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.mapper.YandexDTOMapper;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.LocationOrm;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.services.YandexGeocodeService;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class ContactLocationRepository implements LocationRepository {
    private final AppDatabase database;
    private final Context context;
    private final ContactDetailsRepository contactDetailsContentResolverRepository;

    public ContactLocationRepository(@NonNull AppDatabase database,
                                     @NonNull Context context,
                                     @NonNull ContactDetailsRepository contactDetailsContentResolverRepository) {
        this.database = database;
        this.context = context;
        this.contactDetailsContentResolverRepository = contactDetailsContentResolverRepository;
    }

    @Override
    public void insertContactLocation(@NonNull Location location, @NonNull Contact contact) {
        LocationOrm locationORM = new LocationOrm(contact, location);
        database.locationDao().insert(locationORM);
    }

    @Override
    @NonNull
    public Single<List<Location>> getAllContactLocations() {
        return Single.fromCallable(() -> database.locationDao().getAll())
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(item ->
                        contactDetailsContentResolverRepository.getDetailsContact(item.getContactID())
                                .map(contact -> new Pair<LocationOrm, Contact>(item, contact)))
                .map(pair -> new Location(
                        pair.second,
                        pair.first.getAddress(),
                        new Point(pair.first.getLatitude(), pair.first.getLongitude())
                )).toList();
    }

    @Override
    @NonNull
    public Single<Location> createOrUpdateContactLocationByCoordinate(@NonNull Contact contact,
                                                                      double latitude,
                                                                      double longitude) {
        String coordinate = latitude + "," + longitude;

        Single<Location> locationSingle = YandexGeocodeService.getInstance()
                .getJSONApi()
                .getLocation(
                        coordinate,
                        context.getResources().getString(R.string.yandex_maps_key))
                .map((responseDTO) -> new YandexDTOMapper().transform(
                        contact,
                        responseDTO,
                        latitude,
                        longitude))
                .doOnSuccess(location -> insertContactLocation(location, contact));

        return locationSingle;
    }
}
