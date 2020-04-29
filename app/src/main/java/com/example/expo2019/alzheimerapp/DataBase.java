package com.example.expo2019.alzheimerapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.expo2019.alzheimerapp.AppPeople.People;
import com.example.expo2019.alzheimerapp.AppPeople.PeopleDao;
import com.example.expo2019.alzheimerapp.HealthReminders.ReminderDao;
import com.example.expo2019.alzheimerapp.HealthReminders.RemindersModel;
import com.example.expo2019.alzheimerapp.PatientData.Patient;
import com.example.expo2019.alzheimerapp.PatientData.PatientDao;

import java.net.PortUnreachableException;

@Database(entities = {People.class, RemindersModel.class, Patient.class}, version = 8)
public abstract class DataBase extends RoomDatabase {

    public abstract PeopleDao peopleDao();
    public abstract ReminderDao reminderDao();
    public abstract PatientDao patientDao();

    private static DataBase INSTANCE;
    public static DataBase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                     DataBase.class,
                    "people-db")
                    .fallbackToDestructiveMigration() //database to be cleared when you upgrade the version
                    .build();
        }
        return INSTANCE;
    }

}

