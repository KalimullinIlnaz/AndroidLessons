package com.a65apps.kalimullinilnazrafilovich.myapplication.repositories;

import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Address;
import retrofit2.Response;


public class GeocodeRepository {
    public String getAddress(Response<Address> response) {
        Address fullAddress = response.body();
        return fullAddress.getResponse()
                .getGeoObjectCollection().getFeatureMember().get(0).getGeoObject()
                .getMetaDataProperty().getGeocoderMetaData().getText();
    }

}
