package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.mapper;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;
import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.YandexAddressResponseDTO;

public class YandexDTOMapper {

    public Location transform(Contact contact,
                              YandexAddressResponseDTO yandexAddressResponseDTO,
                              double latitude, double longitude) {
        return new Location(
                contact,
                getFullAddress(yandexAddressResponseDTO),
                new Point(latitude, longitude)
        );
    }

    private String getFullAddress(YandexAddressResponseDTO yandexAddressResponseDTO) {
        try {
            return yandexAddressResponseDTO.getResponse()
                    .getGeoObjectCollection().getFeatureMember().get(0).getGeoObject()
                    .getMetaDataProperty().getGeocoderMetaData().getText();
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}
