package com.art.trolleybusinspection.database.converters;

import androidx.room.TypeConverter;

import com.art.trolleybusinspection.entity.enums.TrolleyModel;

import java.time.LocalDate;


public class Converter {
    @TypeConverter
    public String fromTrolleyModel(TrolleyModel trolleyModel) {
        return trolleyModel.getText();
    }

    @TypeConverter
    public TrolleyModel toTrolleyModel(String string) {
        return TrolleyModel.getByText(string);
    }

    @TypeConverter
    public long fromDate(LocalDate date) {
        return date.toEpochDay();
    }

    @TypeConverter
    public LocalDate toDate(long epoch) {
        return LocalDate.ofEpochDay(epoch);
    }
}
