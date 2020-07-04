package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.services;


import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces.YandexGeocodeApi;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class YandexGeocodeService {
    private static final String BASE_URL = "https://geocode-maps.yandex.ru/";
    private static YandexGeocodeService mInstance;
    private Retrofit mRetrofit;

    private YandexGeocodeService() {
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
    public static YandexGeocodeService getInstance() {
        if (mInstance == null) {
            mInstance = new YandexGeocodeService();
        }
        return mInstance;
    }

    @NonNull
    public YandexGeocodeApi getJSONApi() {
        return mRetrofit.create(YandexGeocodeApi.class);
    }
}
