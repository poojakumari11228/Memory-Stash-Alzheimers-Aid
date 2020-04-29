package com.example.expo2019.alzheimerapp.PatientData;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Patient {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "p_name")
    private String p_name;

    @ColumnInfo(name = "p_phone")
    private String p_phone;

    @ColumnInfo(name = "emergency_pno1")
    private String emg_pno1;

    @ColumnInfo(name = "emergency_pno2")
    private String emg_pno2;

    @ColumnInfo(name = "p_address")
    private String p_addresss;

    public void setId(int id) {
        this.id = id;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public void setP_phone(String p_phone) {
        this.p_phone = p_phone;
    }

    public void setEmg_pno1(String emg_pno1) {
        this.emg_pno1 = emg_pno1;
    }

    public void setEmg_pno2(String emg_pno2) {
        this.emg_pno2 = emg_pno2;
    }

    public void setP_addresss(String p_addresss) {
        this.p_addresss = p_addresss;
    }

    public int getId() {
        return id;
    }

    public String getP_name() {
        return p_name;
    }

    public String getP_phone() {
        return p_phone;
    }

    public String getEmg_pno1() {
        return emg_pno1;
    }

    public String getEmg_pno2() {
        return emg_pno2;
    }

    public String getP_addresss() {
        return p_addresss;
    }
}

