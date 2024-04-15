package com.example.bungee;

import android.graphics.Bitmap;
import android.net.Uri;

public class ProductData {
    String name, seller;
    Float price, rating;
    byte[] image;
    int quantity, sold, bought;

    public ProductData(String seller, String name, Float price, int quantity, int sold, int bought, byte[] image, float rating) {
        this.name = name;
        this.seller = seller;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.sold = sold;
        this.bought = bought;
        this.rating = rating;
    }
    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }
}
