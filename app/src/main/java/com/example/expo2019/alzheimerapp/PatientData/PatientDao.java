package com.example.expo2019.alzheimerapp.PatientData;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PatientDao {

    @Query("select * from patient")
    List<Patient> getAll();

    @Insert
    void insert(Patient patient);

    @Update
    void update(Patient patient);


}