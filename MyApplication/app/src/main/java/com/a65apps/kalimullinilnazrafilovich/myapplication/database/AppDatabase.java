package com.a65apps.kalimullinilnazrafilovich.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces.LocationDao;
import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Location;


@Database(entities = {Location.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
