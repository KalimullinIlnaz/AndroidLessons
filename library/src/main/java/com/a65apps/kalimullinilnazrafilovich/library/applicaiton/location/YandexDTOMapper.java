package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.location;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;
import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.YandexAddressResponseDTO;

public class YandexDTOMapper {

    @NonNull
    public Location transform(@NonNull ContactDetailsInfo contactDetailsInfo,
                              @NonNull YandexAddressResponseDTO yandexAddressResponseDTO,
                              double latitude, double longitude) {
        return new Location(
                contactDetailsInfo,
                getFullAddress(yandexAddressResponseDTO),
                new Point(latitude, longitude)
        );
    }

    @NonNull
    private String getFullAddress(@NonNull YandexAddressResponseDTO yandexAddressResponseDTO) {
        try {
            return yandexAddressResponseDTO.getResponse()
                    .getGeoObjectCollection().getFeatureMember().get(0).getGeoObject()
                    .getMetaDataProperty().getGeocoderMetaData().getText();
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}
