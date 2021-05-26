package com.example.helloworldnataliasapplication.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.helloworldnataliasapplication.SettingsDatabase;
import com.example.helloworldnataliasapplication.entity.Settings;

public class SettingsViewModel extends ViewModel {
    public LiveData<Settings> loadSettings(Context context) {
        SettingsDatabase db = SettingsDatabase.getDatabase(context);
        return db.settingsDao().loadSettings();
    }

    public LiveData<Settings> updateSettings(Context context, Settings settings) {
        SettingsDatabase db = SettingsDatabase.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.settingsDao().updateSettings(settings);
        });
        return db.settingsDao().loadSettings();
    }

    public LiveData<Settings> saveDefaultSettings(Context context) {
        SettingsDatabase db = SettingsDatabase.getDatabase(context);

        Settings settings = new Settings();
        settings.setPrivateAccount(true);
        settings.setAgeRangeStart(20);
        settings.setAgeRangeEnd(30);
        settings.setReminderTime("10:00");
        settings.setSearchRange(20);
        settings.setSettingsKey("uniqueKey");

        db.getTransactionExecutor().execute(() -> {
            db.settingsDao().insertSettings(settings);
        });

        return db.settingsDao().loadSettings();
    }

}
