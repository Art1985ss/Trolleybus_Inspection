package com.art.trolleybusinspection.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.art.trolleybusinspection.database.dao.relations.RelationTrolleyEngine;
import com.art.trolleybusinspection.entity.Engine;

import java.util.List;

@Dao
public interface EngineDao {
    @Insert
    void insert(Engine engine);

    @Update
    void update(Engine engine);

    @Query("SELECT * FROM engine_table WHERE id = :id")
    Engine findById(int id);

    @Query("SELECT * FROM engine_table WHERE trolley_id = :trolleyId")
    List<Engine> findByTrolleyId(int trolleyId);

    @Query("SELECT * FROM engine_table")
    List<RelationTrolleyEngine> getRelationsEngines();
}
