package com.a65apps.kalimullinilnazrafilovich.myapplication.di.app;

import android.content.Context;

import androidx.room.Room;

import com.a65apps.kalimullinilnazrafilovich.myapplication.app.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    @Provides
    @Singleton
    public AppDatabase provideDataBase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class,"user_location")
                .build();
    }
}
