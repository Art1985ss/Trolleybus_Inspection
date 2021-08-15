package com.art.trolleybusinspection.database.converters;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class DateConverter {
    @TypeConverter
    public Long dateToEpoch(LocalDate date) {
        return date.toEpochDay();
    }

    @TypeConverter
    public LocalDate epochToDate(Long days) {
        return LocalDate.ofEpochDay(days);
    }
}
