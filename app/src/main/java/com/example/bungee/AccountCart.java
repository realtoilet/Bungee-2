package com.example.bungee;

public class AccountCart {
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    String productName, seller;
    float productPrice;
    int productQuantity;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    byte[] img;


    public AccountCart(String productName, String seller, float productPrice, int productQuantity, byte[] img) {
        this.productName = productName;
        this.seller = seller;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.img = img;
    }

}
