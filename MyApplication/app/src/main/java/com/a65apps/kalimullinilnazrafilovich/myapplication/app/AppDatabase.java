package com.a65apps.kalimullinilnazrafilovich.myapplication.app;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces.LocationDao;

import Entities.Location;

@Database(entities = {Location.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
