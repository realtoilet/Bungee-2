package com.example.bungee;

public class OrderData {
    String email;
    String name;
    String date;
    String seller;
    float price;
    int quantity;
    String id;
    String deliveryRider;
    byte[] img;

    public OrderData(String email, String name, String date, String seller, float price, int quantity, byte[] img, String id, String deliveryRider) {
        this.email = email;
        this.name = name;
        this.date = date;
        this.seller = seller;
        this.price = price;
        this.quantity = quantity;
        this.img = img;
        this.id = id;
        this.deliveryRider = deliveryRider;
    }
    public String getDeliveryRider() {
        return deliveryRider;
    }

    public void setDeliveryRider(String deliveryRider) {
        this.deliveryRider = deliveryRider;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
