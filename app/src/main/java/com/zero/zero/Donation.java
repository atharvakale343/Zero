package com.zero.zero;

public class Donation {
    public String uid;
    public String status;

    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String key;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String foodDescription;
    public String pickupDate;
    public String photoDownloadURL;

    public String getPhotoDownloadURL() {
        return photoDownloadURL;
    }

    public void setPhotoDownloadURL(String photoDownloadURL) {
        this.photoDownloadURL = photoDownloadURL;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String fromPickupTime;
    public String toPickupTime;

    public String getFromPickupTime() {
        return fromPickupTime;
    }

    public void setFromPickupTime(String fromPickupTime) {
        this.fromPickupTime = fromPickupTime;
    }

    public String getToPickupTime() {
        return toPickupTime;
    }

    public void setToPickupTime(String toPickupTime) {
        this.toPickupTime = toPickupTime;
    }

    public String expiration;
    public String special;

    @Override
    public String toString() {
        return "Donation{" +
                "uid='" + uid + '\'' +
                "status='" + status + '\'' +
                "foodDescription='" + foodDescription + '\'' +
                ", photoDownloadURL='" + photoDownloadURL + '\'' +
                ", pickupDate='" + pickupDate + '\'' +
                ", fromPickupTime='" + fromPickupTime + '\'' +
                ", toPickupTime='" + toPickupTime + '\'' +
                ", expiration='" + expiration + '\'' +
                ", special='" + special + '\'' +
                '}';
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }



    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public Donation() {
    }

    public Donation(String uid, String key, String status, String foodDescription, String photoDownloadURL, String pickupDate, String fromPickupTime,String toPickupTime, String expiration, String special) {
        this.uid = uid;
        this.key = key;
        this.status = status;
        this.foodDescription = foodDescription;
        this.photoDownloadURL = photoDownloadURL;
        this.pickupDate = pickupDate;
        this.fromPickupTime = fromPickupTime;
        this.toPickupTime = toPickupTime;
        this.expiration = expiration;
        this.special = special;
    }


}
