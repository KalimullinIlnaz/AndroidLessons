package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.LocationOrm;

import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM LocationOrm")
    List<LocationOrm> getAll();

    @Query("SELECT * FROM LocationOrm WHERE contact_id = :id")
    LocationOrm getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationOrm location);

    @Query("SELECT EXISTS(SELECT * FROM LocationOrm WHERE contact_id = :id)")
    int isExists(String id);
}
