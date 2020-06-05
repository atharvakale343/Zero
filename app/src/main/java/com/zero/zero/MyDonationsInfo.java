package com.zero.zero;

public class MyDonationsInfo {
    public MyDonationsInfo(String foodDescription, String status, String key, String date) {
        this.foodDescription = foodDescription;
        this.status = status;
        this.key = key;
        this.date = date;
    }
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String foodDescription;
    String status;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;
    public MyDonationsInfo() {

    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
