package com.example.rent;

public class TenantsData {

    String uid;
    String name;
    String Email;
    String Phone;
    String ShopName;
    String ComplexName;

    public TenantsData() {

    }

    public TenantsData(String uid, String name, String email, String phone, String shopName, String complexName) {
        this.uid = uid;
        this.name = name;
        this.Email = email;
        this.Phone = phone;
        this.ShopName = shopName;
        this.ComplexName = complexName;
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

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getComplexName() {
        return ComplexName;
    }

    public void setComplexName(String complexName) {
        ComplexName = complexName;
    }
}
