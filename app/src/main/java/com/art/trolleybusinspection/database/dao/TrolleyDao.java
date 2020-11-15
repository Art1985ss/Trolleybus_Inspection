package com.art.trolleybusinspection.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.art.trolleybusinspection.IOperations;
import com.art.trolleybusinspection.entity.Trolley;

import java.util.List;

@Dao
public interface TrolleyDao extends IOperations<Trolley> {
    @Insert
    void insert(Trolley trolley);

    @Update
    void update(Trolley trolley);

    @Delete
    void delete(Trolley trolley);

    @Query("SELECT * FROM trolley_table WHERE id = :id;")
    Trolley findById(int id);

    @Query("SELECT * FROM trolley_table ORDER BY id;")
    LiveData<List<Trolley>> getAll();


}
