package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.LocationOrm;

import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM LocationOrm")
    @NonNull
    List<LocationOrm> getAll();

    @Query("SELECT * FROM LocationOrm WHERE contact_id = :id")
    @NonNull
    LocationOrm getById(@NonNull String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull LocationOrm location);

    @Query("SELECT EXISTS(SELECT * FROM LocationOrm WHERE contact_id = :id)")
    int isExists(@NonNull String id);
}
