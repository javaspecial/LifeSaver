package com.peoples.shield.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.peoples.shield.entity.CurrentLocation;

import java.util.List;

@Dao
public interface CurrentLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentLocation loc);

    @Update
    void update(CurrentLocation loc);

    @Delete
    void delete(CurrentLocation loc);

    @Query("SELECT * FROM current_location LIMIT 1")
    CurrentLocation getOne();

    @Query("SELECT * FROM current_location WHERE id = :id LIMIT 1")
    CurrentLocation getOneById(Long id);
    @Query(("SELECT * FROM current_location"))
    List<CurrentLocation> getAll();
}
