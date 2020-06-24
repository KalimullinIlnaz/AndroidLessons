package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces.LocationDao;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.LocationOrm;

@Database(entities = {LocationOrm.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
