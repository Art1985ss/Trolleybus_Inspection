package com.art.trolleybusinspection.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.art.trolleybusinspection.config.ValueConstants;
import com.art.trolleybusinspection.database.converters.Converter;
import com.art.trolleybusinspection.entity.enums.TrolleyModel;

import java.time.LocalDate;
import java.util.Locale;


@Entity(tableName = "trolley_table")
public class Trolley implements Comparable<Trolley> {
    @PrimaryKey
    private final int id;
    @ColumnInfo(name = "date")
    @TypeConverters({Converter.class})
    private LocalDate date;
    @ColumnInfo(name = "model")
    @TypeConverters({Converter.class})
    private TrolleyModel model;
    @ColumnInfo(name = "traction_motor_number")
    private int tractionMotorNumber;
    @ColumnInfo(name = "diesel_generator_number")
    private int dieselGeneratorNumber;
    @ColumnInfo(name = "diesel_engine_number")
    private int dieselEngineNumber;
    @ColumnInfo(name = "akb_1")
    private int akb1Id;
    @ColumnInfo(name = "akb_2")
    private int akb2Id;
    @ColumnInfo(name = "mileage")
    private int mileage;
    @ColumnInfo(name = "notes")
    private String note;


    public Trolley(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TrolleyModel getModel() {
        return model;
    }

    public void setModel(TrolleyModel model) {
        this.model = model;
    }

    public int getTractionMotorNumber() {
        return tractionMotorNumber;
    }

    public void setTractionMotorNumber(int tractionMotorNumber) {
        this.tractionMotorNumber = tractionMotorNumber;
    }

    public int getDieselGeneratorNumber() {
        return dieselGeneratorNumber;
    }

    public void setDieselGeneratorNumber(int dieselGeneratorNumber) {
        this.dieselGeneratorNumber = dieselGeneratorNumber;
    }

    public int getDieselEngineNumber() {
        return dieselEngineNumber;
    }

    public void setDieselEngineNumber(int dieselEngineNumber) {
        this.dieselEngineNumber = dieselEngineNumber;
    }

    public int getAkb1Id() {
        return akb1Id;
    }

    public void setAkb1Id(int akb1Id) {
        this.akb1Id = akb1Id;
    }

    public int getAkb2Id() {
        return akb2Id;
    }

    public void setAkb2Id(int akb2Id) {
        this.akb2Id = akb2Id;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int compareTo(Trolley o) {
        return id - o.id;
    }

    @Override
    public String toString() {
        return "ID " + id + " Model " + model + "\n" +
                "Date " + date.format(ValueConstants.DATE_FORMAT) + "\n" +
                "Vilcejs " + tractionMotorNumber + "\n" +
                "Dizelgenerators " + dieselGeneratorNumber + "\n" +
                "Dizeldzinejs " + dieselEngineNumber + "\n" +
                "AKB " + akb1Id + " " + akb2Id + "\n" +
                "Nobraukums " + mileage + "\n" +
                "Piezimes : " + note + "\n";
    }

    public String toCSV() {
        return String.format(Locale.ENGLISH, "%d;%s;%s;%d;%d;%d;%d;%d;%d;%s;",
                id, model, date.format(ValueConstants.DATE_FORMAT), tractionMotorNumber, akb1Id, akb2Id, dieselGeneratorNumber, dieselEngineNumber, mileage, note);
    }
}
