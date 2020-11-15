package com.art.trolleybusinspection.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.art.trolleybusinspection.database.dao.Converter;

import java.time.LocalDate;

@Entity(tableName = "inspection_table")
//        foreignKeys = @ForeignKey(
//                entity = Trolley.class,
//                parentColumns = "id",
//                childColumns = "trolley_id"
//        ))
public class InspectionRecord implements Comparable<InspectionRecord> {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "date")
    @TypeConverters({Converter.class})
    private LocalDate date;
    @ColumnInfo(name = "mileage")
    private int mileage;
    @ColumnInfo(name = "resistance_1")
    private double resistance1;
    @ColumnInfo(name = "resistance2")
    private double resistance2;
    @ColumnInfo(name = "resistance3")
    private double resistance3;
    @ColumnInfo(name = "resistance4")
    private double resistance4;
    @ColumnInfo(name = "resistance5")
    private double resistance5;
    @ColumnInfo(name = "resistance6")
    private double resistance6;
    @ColumnInfo(name = "resistance7")
    private double resistance7;
    @ColumnInfo(name = "resistance8")
    private double resistance8;
    @ColumnInfo(name = "resistance9")
    private double resistance9;
    @ColumnInfo(name = "resistance10")
    private double resistance10;
    @ColumnInfo(name = "resistance11")
    private double resistance11;
    @ColumnInfo(name = "resistance12")
    private double resistance12;
    @ColumnInfo(name = "resistance13")
    private double resistance13;
    @ColumnInfo(name = "resistance14")
    private double resistance14;
    @ColumnInfo(name = "resistance15")
    private double resistance15;
    @ColumnInfo(name = "resistance16")
    private double resistance16;
    @ColumnInfo(name = "resistance17")
    private double resistance17;
    @ColumnInfo(name = "resistance18")
    private double resistance18;
    @ColumnInfo(name = "resistance19")
    private double resistance19;
    @ColumnInfo(name = "resistance20")
    private double resistance20;
    @ColumnInfo(name = "trolley_id")
    private int trolleyId;

    public InspectionRecord(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getResistance1() {
        return resistance1;
    }

    public void setResistance1(double resistance1) {
        this.resistance1 = resistance1;
    }

    public double getResistance2() {
        return resistance2;
    }

    public void setResistance2(double resistance2) {
        this.resistance2 = resistance2;
    }

    public double getResistance3() {
        return resistance3;
    }

    public void setResistance3(double resistance3) {
        this.resistance3 = resistance3;
    }

    public double getResistance4() {
        return resistance4;
    }

    public void setResistance4(double resistance4) {
        this.resistance4 = resistance4;
    }

    public double getResistance5() {
        return resistance5;
    }

    public void setResistance5(double resistance5) {
        this.resistance5 = resistance5;
    }

    public double getResistance6() {
        return resistance6;
    }

    public void setResistance6(double resistance6) {
        this.resistance6 = resistance6;
    }

    public double getResistance7() {
        return resistance7;
    }

    public void setResistance7(double resistance7) {
        this.resistance7 = resistance7;
    }

    public double getResistance8() {
        return resistance8;
    }

    public void setResistance8(double resistance8) {
        this.resistance8 = resistance8;
    }

    public double getResistance9() {
        return resistance9;
    }

    public void setResistance9(double resistance9) {
        this.resistance9 = resistance9;
    }

    public double getResistance10() {
        return resistance10;
    }

    public void setResistance10(double resistance10) {
        this.resistance10 = resistance10;
    }

    public double getResistance11() {
        return resistance11;
    }

    public void setResistance11(double resistance11) {
        this.resistance11 = resistance11;
    }

    public double getResistance12() {
        return resistance12;
    }

    public void setResistance12(double resistance12) {
        this.resistance12 = resistance12;
    }

    public double getResistance13() {
        return resistance13;
    }

    public void setResistance13(double resistance13) {
        this.resistance13 = resistance13;
    }

    public double getResistance14() {
        return resistance14;
    }

    public void setResistance14(double resistance14) {
        this.resistance14 = resistance14;
    }

    public double getResistance15() {
        return resistance15;
    }

    public void setResistance15(double resistance15) {
        this.resistance15 = resistance15;
    }

    public double getResistance16() {
        return resistance16;
    }

    public void setResistance16(double resistance16) {
        this.resistance16 = resistance16;
    }

    public double getResistance17() {
        return resistance17;
    }

    public void setResistance17(double resistance17) {
        this.resistance17 = resistance17;
    }

    public double getResistance18() {
        return resistance18;
    }

    public void setResistance18(double resistance18) {
        this.resistance18 = resistance18;
    }

    public double getResistance19() {
        return resistance19;
    }

    public void setResistance19(double resistance19) {
        this.resistance19 = resistance19;
    }

    public double getResistance20() {
        return resistance20;
    }

    public void setResistance20(double resistance20) {
        this.resistance20 = resistance20;
    }

    public int getTrolleyId() {
        return trolleyId;
    }

    public void setTrolleyId(int trolleyId) {
        this.trolleyId = trolleyId;
    }

    @Override
    public int compareTo(InspectionRecord o) {
        return date.compareTo(o.date);
    }
}
