package com.example.appsp.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    int ID;
    String Image;
    String Name;
    String Price;
    String Mota;
    String ChatLieu;
    String Xuatxu;
    String Size;

    public Sanpham(){}

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getChatLieu() {
        return ChatLieu;
    }

    public void setChatLieu(String chatLieu) {
        ChatLieu = chatLieu;
    }

    public String getXuatxu() {
        return Xuatxu;
    }

    public void setXuatxu(String xuatxu) {
        Xuatxu = xuatxu;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
