package com.art.trolleybusinspection.database.dao.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.art.trolleybusinspection.entity.InspectionRecord;
import com.art.trolleybusinspection.entity.Trolley;

import java.util.List;

public class RelationTrolleyInspection {
//    @Embedded
    public Trolley trolley;
//    @Relation(
//            parentColumn = "id",
//            entityColumn = "trolley_id"
//    )
    public List<InspectionRecord> inspections;
}
