package com.art.trolleybusinspection.database.dao;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.art.trolleybusinspection.entity.enums.EngineType;
import com.art.trolleybusinspection.entity.enums.TrolleyModel;

import java.time.LocalDate;


public class Converter {
    @TypeConverter
    public String fromEngineType(EngineType engineType) {
        return engineType.toString();
    }

    @TypeConverter
    public EngineType toEngineType(String string) {
        return EngineType.getByText(string);
    }

    @TypeConverter
    public String fromTrolleyModel(TrolleyModel trolleyModel) {
        return trolleyModel.getText();
    }

    @TypeConverter
    public TrolleyModel toTrolleyModel(String string) {
        return TrolleyModel.getByText(string);
    }
    @TypeConverter
    public String fromDate(LocalDate date) {
        return date.toString();
    }

    @TypeConverter
    public LocalDate toDate(String string) {
        return LocalDate.parse(string);
    }
}
