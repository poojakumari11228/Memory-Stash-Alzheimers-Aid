package com.example.expo2019.alzheimerapp.HealthReminders;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Reminders")
public class RemindersModel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "hour")
    private String hour;

    @ColumnInfo(name = "min")
    private String min;

    @ColumnInfo(name = "status")
    private String status;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getHour() {
        return hour;
    }

    public String getMin() {
        return min;
    }

    public String getStatus() {
        return status;
    }
}
