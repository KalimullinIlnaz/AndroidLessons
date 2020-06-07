package com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces;

import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Address;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexGeocodeApi {
    @GET("1.x?&format=json&")
    public Call<Address> getLocation(
            @Query("geocode") String geocode,
            @Query("apikey") String key);
}
