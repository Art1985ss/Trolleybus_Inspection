package com.art.trolleybusinspection.database.dao;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.art.trolleybusinspection.database.dao.relations.RelationTrolleyInspection;
import com.art.trolleybusinspection.entity.InspectionRecord;

import java.util.List;

public interface InspectionRecordDao {
    @Insert
    void insert(InspectionRecord inspectionRecord);

    @Update
    void update(InspectionRecord inspectionRecord);

    @Query("SELECT * FROM inspection_table WHERE id = :id")
    InspectionRecord findById(int id);

    @Query("SELECT * FROM inspection_table WHERE trolley_id = :trolleyId")
    List<InspectionRecord> findByTrolleyId(int trolleyId);

    @Query("SELECT * FROM inspection_table")
    List<RelationTrolleyInspection> getInspectionRelations();
}
