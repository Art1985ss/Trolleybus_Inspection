package com.art.trolleybusinspection.database.dao.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.art.trolleybusinspection.entity.Engine;
import com.art.trolleybusinspection.entity.Trolley;

import java.util.List;

public class RelationTrolleyEngine {
//    @Embedded
    public Trolley trolley;
//    @Relation(
//            parentColumn = "id",
//            entityColumn = "trolley_id"
//    )
    public List<Engine> engines;

}
