package com.example.helloworldnataliasapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.helloworldnataliasapplication.dao.SettingsDao;
import com.example.helloworldnataliasapplication.entity.Settings;

@Database(entities = {Settings.class}, version = 1)
public abstract class SettingsDatabase extends RoomDatabase {
    public abstract SettingsDao settingsDao();

    private static SettingsDatabase db;

    public static SettingsDatabase getDatabase(Context context) {
        if (db != null) {
            return db;
        }
        db = Room.databaseBuilder(context,
                SettingsDatabase.class, "settingsdb")
                .build();
        return db;
    }
}
