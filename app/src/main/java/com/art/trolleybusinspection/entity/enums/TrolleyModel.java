package com.art.trolleybusinspection.entity.enums;

import com.art.trolleybusinspection.AppException;

import java.util.Arrays;
import java.util.function.Predicate;

public enum TrolleyModel {
    TR27("Škoda 27Tr Solaris"),
    TR24("Škoda 24Tr Irisbus"),
    GST_18("Solaris Trollino III 18 Ganz-Škoda");

    private final String text;

    TrolleyModel(String text) {
        this.text = text;
    }

    public static TrolleyModel getByText(String t) {
        Predicate<TrolleyModel> predicate = trolleyModel -> trolleyModel.text.equals(t);
        return Arrays.stream(values()).filter(predicate).findAny()
                .orElseThrow(() -> new AppException("No trolley model found from " + t));
    }

    public String getText() {
        return text;
    }
}
