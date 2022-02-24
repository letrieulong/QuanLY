package com.example.appsp.model;

public class OrderList {
    int id;
    String pricesp;
    String namesp;
    String quantity;
    String image;
    String dateorder;

    public OrderList(){}

    public String getPricesp() {
        return pricesp;
    }

    public void setPricesp(String pricesp) {
        this.pricesp = pricesp;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamesp() {
        return namesp;
    }

    public void setNamesp(String namesp) {
        this.namesp = namesp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateorder() {
        return dateorder;
    }

    public void setDateorder(String dateorder) {
        this.dateorder = dateorder;
    }
}
