package com.example.bungee;

public class UserType {
    String email, requestType, currentType;

    public UserType(String email, String requestType, String currentType) {
        this.email = email;
        this.requestType = requestType;
        this.currentType = currentType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getCurrentType() {
        return currentType;
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }
}
