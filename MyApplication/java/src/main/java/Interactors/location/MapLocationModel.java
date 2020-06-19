package Interactors.location;

import Entities.Location;
import Entities.YandexAddressResponseDTO;
import Interactors.db.DataBaseRepository;
import Interactors.geocode.GeocodeRepository;
import io.reactivex.rxjava3.core.Single;

public class MapLocationModel implements MapLocationInteractor {
    private final DataBaseRepository dataBaseRepository;
    private final GeocodeRepository geocodeRepository;

    public MapLocationModel(DataBaseRepository dataBaseRepository, GeocodeRepository geocodeRepository){
        this.dataBaseRepository = dataBaseRepository;
        this.geocodeRepository = geocodeRepository;
    }


    @Override
    public Single<YandexAddressResponseDTO> loadAddress(String coordinate) {
        return geocodeRepository.getAddress(coordinate);
    }

    @Override
    public void saveAddressInDB(Location location) {
        dataBaseRepository.insertContact(location);
    }


}
