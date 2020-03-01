package com.example.toletgo.data_model;

public class HomePostShowModel {
    private String homePhoto1,homeBed,homeBath,homeKitchen,homeBalcony,postLocation,homePrice;

    public HomePostShowModel() {
    }

    public HomePostShowModel(String homePhoto1, String homeBed, String homeBath, String homeKitchen, String homeBalcony,
                             String postLocation, String homePrice) {
        this.homePhoto1 = homePhoto1;
        this.homeBed = homeBed;
        this.homeBath = homeBath;
        this.homeKitchen = homeKitchen;
        this.homeBalcony = homeBalcony;
        this.postLocation = postLocation;
        this.homePrice = homePrice;
    }

    public String getHomePhoto1() {
        return homePhoto1;
    }

    public void setHomePhoto1(String homePhoto1) {
        this.homePhoto1 = homePhoto1;
    }

    public String getHomeBed() {
        return homeBed;
    }

    public void setHomeBed(String homeBed) {
        this.homeBed = homeBed;
    }

    public String getHomeBath() {
        return homeBath;
    }

    public void setHomeBath(String homeBath) {
        this.homeBath = homeBath;
    }

    public String getHomeKitchen() {
        return homeKitchen;
    }

    public void setHomeKitchen(String homeKitchen) {
        this.homeKitchen = homeKitchen;
    }

    public String getHomeBalcony() {
        return homeBalcony;
    }

    public void setHomeBalcony(String homeBalcony) {
        this.homeBalcony = homeBalcony;
    }

    public String getPostLocation() {
        return postLocation;
    }

    public void setPostLocation(String postLocation) {
        this.postLocation = postLocation;
    }

    public String getHomePrice() {
        return homePrice;
    }

    public void setHomePrice(String homePrice) {
        this.homePrice = homePrice;
    }
}
