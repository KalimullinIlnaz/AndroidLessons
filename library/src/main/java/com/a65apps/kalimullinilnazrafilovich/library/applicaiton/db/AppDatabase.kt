package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces.LocationDao
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.LocationOrm

@Database(entities = [LocationOrm::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
