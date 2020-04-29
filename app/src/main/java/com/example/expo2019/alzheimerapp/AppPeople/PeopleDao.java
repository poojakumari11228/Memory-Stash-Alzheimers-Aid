package com.example.expo2019.alzheimerapp.AppPeople;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PeopleDao {

    @Query("select * from people")
    List<People> getAll();

    @Query("SELECT * FROM people WHERe name IN (:name)")
    List<People> getByName(String name);

    @Insert
    void insert(People people);


}
