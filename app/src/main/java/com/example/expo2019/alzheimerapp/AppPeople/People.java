package com.example.expo2019.alzheimerapp.AppPeople;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class People {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "relation")
    private String relation;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "addresss")
    private String addresss;

    @ColumnInfo (typeAffinity = ColumnInfo.BLOB, name = "image")
    private byte[] image;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setPhone(String  phone) {
        this.phone = phone;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {

        return id;
    }
    public byte[] getImage() {
        return image;
    }
    public String getName() {
        return name;
    }

    public String getRelation() {
        return relation;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddresss() {
        return addresss;
    }

}
