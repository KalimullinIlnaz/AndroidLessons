package Interactors.location;

import Entities.YandexAddressResponseDTO;
import io.reactivex.rxjava3.core.Single;

public interface MapLocationInteractor {
    Single<YandexAddressResponseDTO> loadAddress(String coordinate);
}
