package com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Entities.Location;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM location")
    List<Location> getAll();

    @Query("SELECT * FROM location WHERE contact_id = :id")
    Location getById (String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location location);

    @Query("SELECT EXISTS(SELECT * FROM location WHERE contact_id = :id)")
    int isExists(String id);
}
