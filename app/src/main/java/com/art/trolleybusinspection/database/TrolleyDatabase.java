package com.art.trolleybusinspection.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.art.trolleybusinspection.database.dao.TrolleyDao;
import com.art.trolleybusinspection.entity.Trolley;

@Database(entities = {Trolley.class}, version = 1)
public abstract class TrolleyDatabase extends RoomDatabase {
    private static TrolleyDatabase instance;

    public abstract TrolleyDao trolleyDao();

    public static synchronized TrolleyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context,
                    TrolleyDatabase.class,
                    "trolley_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }


}
