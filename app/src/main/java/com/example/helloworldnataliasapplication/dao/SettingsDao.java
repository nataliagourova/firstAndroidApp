package com.example.helloworldnataliasapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.helloworldnataliasapplication.entity.Settings;

@Dao
public interface SettingsDao {
    @Query("SELECT * FROM settings LIMIT 1")
    LiveData<Settings> loadSettings();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSettings(Settings settings);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSettings(Settings settings);

    @Delete
    void delete(Settings settings);
}
