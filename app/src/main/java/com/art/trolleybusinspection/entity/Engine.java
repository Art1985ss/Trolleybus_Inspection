package com.art.trolleybusinspection.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.art.trolleybusinspection.database.dao.Converter;
import com.art.trolleybusinspection.entity.enums.EngineType;

@Entity(tableName = "engine_table")
//        foreignKeys = @ForeignKey(
//                entity = Trolley.class,
//                parentColumns = "id",
//                childColumns = "trolley_id"
//        )
//)
public class Engine {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "engine_type")
    @TypeConverters({Converter.class})
    private EngineType type = EngineType.ELECTRIC;
    @ColumnInfo(name = "trolley_id")
    private int trolleyId;

    public Engine(int id) {
        this.id = id;
    }

    public void setType(EngineType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public EngineType getType() {
        return type;
    }

    public int getTrolleyId() {
        return trolleyId;
    }

    public void setTrolleyId(int trolleyId) {
        this.trolleyId = trolleyId;
    }
}
