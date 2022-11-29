package com.example.rent;

public class User {

    String uid;
    String name;
    String Email;
    String Phone;

    public User() {
    }

    public User(String uid, String name, String email, String phone) {
        this.uid = uid;
        this.name = name;
        Email = email;
        Phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
