package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.a65apps.kalimullinilnazrafilovich.entities.Location;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.LocationORM;

import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM LocationORM")
    List<LocationORM> getAll();

    @Query("SELECT * FROM LocationORM WHERE contact_id = :id")
    LocationORM getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationORM location);

    @Query("SELECT EXISTS(SELECT * FROM LocationORM WHERE contact_id = :id)")
    int isExists(String id);
}
