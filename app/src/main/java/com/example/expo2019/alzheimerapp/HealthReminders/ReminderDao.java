package com.example.expo2019.alzheimerapp.HealthReminders;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ReminderDao {

    @Query("select * from reminders")
    List<RemindersModel> getAll();

    @Insert
    void insert(RemindersModel remindersModel);

    @Delete
    public  void deletereminder(RemindersModel reminders);

    @Update
    public  void updateReminder(RemindersModel reminders);



}