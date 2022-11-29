package com.example.rent;

public class UserInfo {

     String shopName;
     String oneMonthRent;
     String complexName;
     String paidAmount;
     String defaultMonth;
     String pendingAmount;

 // Empty Constructor
    public UserInfo(){

    }

    public UserInfo(  String shopName, String oneMonthRent, String complexName, String paidAmount, String defaultMonth, String pendingAmount) {

        this.shopName = shopName;
        this.oneMonthRent = oneMonthRent;
        this.complexName = complexName;
        this.paidAmount = paidAmount;
        this.defaultMonth = defaultMonth;
        this.pendingAmount = pendingAmount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOneMonthRent() {
        return oneMonthRent;
    }

    public void setOneMonthRent(String oneMonthRent) {
        this.oneMonthRent = oneMonthRent;
    }

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDefaultMonth() {
        return defaultMonth;
    }

    public void setDefaultMonth(String defaultMonth) {
        this.defaultMonth = defaultMonth;
    }

    public String getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(String pendingAmount) {
        this.pendingAmount = pendingAmount;
    }
}
