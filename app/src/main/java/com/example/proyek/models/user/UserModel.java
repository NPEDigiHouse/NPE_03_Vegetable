package com.example.proyek.models.user;

public class UserModel {
    public  String username, email, phoneNumber, as;

    public UserModel(String username, String email, String phoneNumber, String as){
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.as = as;
    }

    public UserModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }
}
