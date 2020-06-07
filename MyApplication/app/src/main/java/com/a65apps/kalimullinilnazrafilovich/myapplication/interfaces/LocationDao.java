package com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Location;

import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM location")
    List<Location> getAll();

    @Query("SELECT * FROM location WHERE contact_id = :id")
    Location getById (String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location location);
}
