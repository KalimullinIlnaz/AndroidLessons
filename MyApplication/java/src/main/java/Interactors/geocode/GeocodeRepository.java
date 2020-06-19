package Interactors.geocode;

import Entities.GoogleRouteResponseDTO;
import Entities.YandexAddressResponseDTO;
import io.reactivex.rxjava3.core.Single;

public interface GeocodeRepository {

    Single<YandexAddressResponseDTO> getAddress(String coordinate);

    Single<GoogleRouteResponseDTO> getRoutePoints(String from, String to);

}
