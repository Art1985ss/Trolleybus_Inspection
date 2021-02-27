package com.art.trolleybusinspection.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.art.trolleybusinspection.database.dao.TrolleyDao;
import com.art.trolleybusinspection.entity.Trolley;

@Database(entities = {Trolley.class}, version = 2, exportSchema = false)
public abstract class TrolleyDatabase extends RoomDatabase {
    private static TrolleyDatabase instance;

    public abstract TrolleyDao trolleyDao();

    private static final Migration migration = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'trolley_table' ADD COLUMN 'notes' TEXT DEFAULT ''");
        }
    };

    public static synchronized TrolleyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context,
                    TrolleyDatabase.class,
                    "trolley_database")
                    .addMigrations(migration)
                    .build();
        }
        return instance;
    }


}
