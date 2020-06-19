package Interactors.route;

import Entities.GoogleRouteResponseDTO;
import Interactors.db.DataBaseRepository;
import Interactors.geocode.GeocodeRepository;
import io.reactivex.rxjava3.core.Single;

public class MapRouteModel implements MapRouteInteractor {
    private final DataBaseRepository dataBaseRepository;
    private final GeocodeRepository geocodeRepository;

    public MapRouteModel(DataBaseRepository dataBaseRepository, GeocodeRepository geocodeRepository){
        this.dataBaseRepository = dataBaseRepository;
        this.geocodeRepository = geocodeRepository;
    }

    @Override
    public Single<GoogleRouteResponseDTO> loadRoutePoints(String from, String to) {
        return geocodeRepository.getRoutePoints(from, to);
    }
}
