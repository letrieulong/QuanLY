package com.example.appsp.model;

public class Loaisp {
    int ID;
    String Loaisp;
    String HinhLoai;

    public Loaisp(int ID, String loaisp, String hinhLoai) {
        this.ID = ID;
        Loaisp = loaisp;
        HinhLoai = hinhLoai;
    }

    public Loaisp(){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLoaisp() {
        return Loaisp;
    }

    public void setLoaisp(String loaisp) {
        Loaisp = loaisp;
    }

    public String getHinhLoai() {
        return HinhLoai;
    }

    public void setHinhLoai(String hinhLoai) {
        HinhLoai = hinhLoai;
    }
}
