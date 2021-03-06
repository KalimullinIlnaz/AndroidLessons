package com.a65apps.kalimullinilnazrafilovich.application.app;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    @Provides
    @Singleton
    @NonNull
    public AppDatabase provideDataBase(@NonNull Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "user_location")
                .build();
    }

}
