package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.GoogleRouteResponseDTO;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

@SuppressWarnings("PMD")
public interface GoogleRouteApi {
    @NonNull
    @GET("json?")
    Single<GoogleRouteResponseDTO> getRoute(
            @Query("origin") @NonNull String position,
            @Query("destination") @NonNull String destination,
            @Query("mode") @NonNull String mode,
            @Query("key") @NonNull String key);
}
