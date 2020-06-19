package Interactors.location;

import Entities.YandexAddressResponseDTO;
import Interactors.geocode.GeocodeRepository;
import io.reactivex.rxjava3.core.Single;

public class MapLocationModel implements MapLocationInteractor {
    private final GeocodeRepository geocodeRepository;

    public MapLocationModel(GeocodeRepository geocodeRepository){
        this.geocodeRepository = geocodeRepository;
    }


    @Override
    public Single<YandexAddressResponseDTO> loadAddress(String coordinate) {
        return geocodeRepository.getAddress(coordinate);
    }

}
