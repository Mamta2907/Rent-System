package com.example.rent;

public class ComplexData {

    String complexName;
    String complexAdd;
    String totalShop;

    public ComplexData() {
    }

    public ComplexData(String complexName, String complexAdd, String totalShop) {
        this.complexName = complexName;
        this.complexAdd = complexAdd;
        this.totalShop = totalShop;
    }

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public String getComplexAdd() {
        return complexAdd;
    }

    public void setComplexAdd(String complexAdd) {
        this.complexAdd = complexAdd;
    }

    public String getTotalShop() {
        return totalShop;
    }

    public void setTotalShop(String totalShop) {
        this.totalShop = totalShop;
    }
}
