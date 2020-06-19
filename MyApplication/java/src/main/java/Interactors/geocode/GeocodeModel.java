package Interactors.geocode;

import Entities.GoogleRouteResponseDTO;
import Entities.YandexAddressResponseDTO;
import io.reactivex.rxjava3.core.Single;

public class GeocodeModel implements GeocodeInteractor{
    private final GeocodeRepository geocodeRepository;

    public GeocodeModel(GeocodeRepository geocodeRepository){
        this.geocodeRepository = geocodeRepository;
    }

    @Override
    public Single<YandexAddressResponseDTO> getAddress(String coordinate) {
        return geocodeRepository.getAddress(coordinate);
    }

    @Override
    public Single<GoogleRouteResponseDTO> getRoutePoints(String from, String to) {
        return geocodeRepository.getRoutePoints(from, to);
    }
}
