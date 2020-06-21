package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.Location;

import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM location")
    List<Location> getAll();

    @Query("SELECT * FROM location WHERE contact_id = :id")
    Location getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location location);

    @Query("SELECT EXISTS(SELECT * FROM location WHERE contact_id = :id)")
    int isExists(String id);
}
