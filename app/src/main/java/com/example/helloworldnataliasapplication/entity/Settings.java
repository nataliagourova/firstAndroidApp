package com.example.helloworldnataliasapplication.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Settings {
    @PrimaryKey
    @NonNull
    public String settingsKey;

    @ColumnInfo(name = "reminder_time")
    private String reminderTime;

    @ColumnInfo(name = "search_range")
    private Integer searchRange;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "private_account")
    private Boolean privateAccount;

    @ColumnInfo(name = "age_range_start")
    private Integer ageRangeStart;

    @ColumnInfo(name = "age_range_end")
    private Integer ageRangeEnd;

    @NonNull
    public String getSettingsKey() {
        return settingsKey;
    }

    public void setSettingsKey(@NonNull String settingsKey) {
        this.settingsKey = settingsKey;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public Integer getSearchRange() {
        return searchRange;
    }

    public void setSearchRange(Integer searchRange) {
        this.searchRange = searchRange;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getPrivateAccount() {
        return privateAccount;
    }

    public void setPrivateAccount(Boolean privateAccount) {
        this.privateAccount = privateAccount;
    }

    public Integer getAgeRangeStart() {
        return ageRangeStart;
    }

    public void setAgeRangeStart(Integer ageRangeStart) {
        this.ageRangeStart = ageRangeStart;
    }

    public Integer getAgeRangeEnd() {
        return ageRangeEnd;
    }

    public void setAgeRangeEnd(Integer ageRangeEnd) {
        this.ageRangeEnd = ageRangeEnd;
    }
}
