package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces.LocationDao;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.LocationOrm;

import io.reactivex.rxjava3.annotations.NonNull;

@Database(entities = {LocationOrm.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    @NonNull
    public abstract LocationDao locationDao();
}
