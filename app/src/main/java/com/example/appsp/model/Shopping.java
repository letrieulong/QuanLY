package com.example.appsp.model;

public class Shopping {
    int idsp;
    String tensp;
    long giasp;
    String imgsp;
    int soluongsp;

    public Shopping(){}

    public Shopping(int idsp, String tensp, long giasp, String imgsp, int soluongsp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.imgsp = imgsp;
        this.soluongsp = soluongsp;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public String getImgsp() {
        return imgsp;
    }

    public void setImgsp(String imgsp) {
        this.imgsp = imgsp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }
}
