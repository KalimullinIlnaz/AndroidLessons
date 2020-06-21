package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;


import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.GoogleRouteResponseDTO;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.YandexAddressResponseDTO;

import io.reactivex.rxjava3.core.Single;

public interface GeocodeRepository {

    Single<YandexAddressResponseDTO> getAddress(String coordinate);

    Single<GoogleRouteResponseDTO> getRoutePoints(String from, String to);

}
