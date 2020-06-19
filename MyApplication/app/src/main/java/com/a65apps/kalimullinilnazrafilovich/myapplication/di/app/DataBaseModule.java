package com.a65apps.kalimullinilnazrafilovich.myapplication.di.app;

import android.content.Context;

import androidx.room.Room;


import com.a65apps.kalimullinilnazrafilovich.myapplication.app.AppDatabase;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepositoryImpl;

import javax.inject.Singleton;

import Interactors.db.DataBaseModel;
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

    @Provides
    @Singleton
    public DataBaseModel provideDataBaseModel(DataBaseRepositoryImpl dataBaseRepository){
        return new DataBaseModel(dataBaseRepository);
    }

}
