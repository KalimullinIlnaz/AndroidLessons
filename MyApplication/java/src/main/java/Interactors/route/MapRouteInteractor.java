package Interactors.route;

import Entities.GoogleRouteResponseDTO;
import io.reactivex.rxjava3.core.Single;

public interface MapRouteInteractor {
    Single<GoogleRouteResponseDTO> loadRoutePoints(String from, String to);
}
