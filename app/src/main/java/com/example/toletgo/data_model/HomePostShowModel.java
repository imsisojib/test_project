package com.example.toletgo.data_model;

public class HomePostShowModel {
    private String homePhoto1,homeBed,homeBath,homeKitchen,homeBalcony,postLocation,homePrice,homeDivision,homeArea,searchAddress;
    private boolean postLive,postSold;

    public HomePostShowModel() {
    }

    public HomePostShowModel(String homePhoto1, String homeBed, String homeBath, String homeKitchen,
                             String homeBalcony, String postLocation, String homePrice, String homeDivision,
                             String homeArea, String searchAddress, boolean postLive, boolean postSold) {
        this.homePhoto1 = homePhoto1;
        this.homeBed = homeBed;
        this.homeBath = homeBath;
        this.homeKitchen = homeKitchen;
        this.homeBalcony = homeBalcony;
        this.postLocation = postLocation;
        this.homePrice = homePrice;
        this.homeDivision = homeDivision;
        this.homeArea = homeArea;
        this.searchAddress = searchAddress;
        this.postLive = postLive;
        this.postSold = postSold;
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

    public String getHomeDivision() {
        return homeDivision;
    }

    public void setHomeDivision(String homeDivision) {
        this.homeDivision = homeDivision;
    }

    public String getHomeArea() {
        return homeArea;
    }

    public void setHomeArea(String homeArea) {
        this.homeArea = homeArea;
    }

    public String getSearchAddress() {
        return searchAddress;
    }

    public void setSearchAddress(String searchAddress) {
        this.searchAddress = searchAddress;
    }

    public boolean isPostLive() {
        return postLive;
    }

    public void setPostLive(boolean postLive) {
        this.postLive = postLive;
    }

    public boolean isPostSold() {
        return postSold;
    }

    public void setPostSold(boolean postSold) {
        this.postSold = postSold;
    }
}
