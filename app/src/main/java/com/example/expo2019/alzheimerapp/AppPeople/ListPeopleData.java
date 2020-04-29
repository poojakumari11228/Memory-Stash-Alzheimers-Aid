package com.example.expo2019.alzheimerapp.AppPeople;

public class ListPeopleData {
    private String name;
    private int phone;
    private byte[] image;
    private String addresss;

    public ListPeopleData(String name, String s, String addresss, byte[] image){
    }

    public ListPeopleData(String name, int phone,String addresss, byte[] image) {
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.addresss = addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public byte[] getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
