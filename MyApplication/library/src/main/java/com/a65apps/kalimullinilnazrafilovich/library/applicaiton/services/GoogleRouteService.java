package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.services;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces.GoogleRouteApi;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class GoogleRouteService {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/directions/";
    private static GoogleRouteService mInstance;
    private final transient Retrofit mRetrofit;

    private GoogleRouteService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client.build())
                .build();
    }

    @NonNull
    public static GoogleRouteService getInstance() {
        if (mInstance == null) {
            mInstance = new GoogleRouteService();
        }
        return mInstance;
    }

    @NonNull
    public GoogleRouteApi getJSONApi() {
        return mRetrofit.create(GoogleRouteApi.class);
    }
}
