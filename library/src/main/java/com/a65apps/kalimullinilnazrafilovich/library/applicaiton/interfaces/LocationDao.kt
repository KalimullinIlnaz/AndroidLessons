package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.LocationOrm

@Dao
interface LocationDao {
    @Query("SELECT * FROM LocationOrm")
    fun getAll(): List<LocationOrm>

    @Query("SELECT * FROM LocationOrm WHERE contact_id = :id")
    fun getById(id: String): LocationOrm

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: LocationOrm)

    @Query("SELECT EXISTS(SELECT * FROM LocationOrm WHERE contact_id = :id)")
    fun isExists(id: String): Int
}