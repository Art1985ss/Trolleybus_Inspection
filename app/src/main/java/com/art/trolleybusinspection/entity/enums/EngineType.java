package com.art.trolleybusinspection.entity.enums;

import androidx.room.Entity;

import com.art.trolleybusinspection.AppException;

import java.util.Arrays;
import java.util.function.Predicate;

public enum EngineType {
    ELECTRIC("Electric"),
    DIESEL("Diesel"),
    HYDROGEN("Hydrogen"),
    PETROLEUM("Petroleum");

    private final String text;

    EngineType(String text) {
        this.text = text;
    }

    public static EngineType getByText(String t) {
        Predicate<EngineType> predicate = engineType -> engineType.text.equals(t);
        return Arrays.stream(values()).filter(predicate).findAny()
                .orElseThrow(() -> new AppException("No enum with text " + t));
    }

    @Override
    public String toString() {
        return text;
    }
}
