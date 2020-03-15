package com.example.toletgo.data_model;

public class HomePostShowModel {
    private String homePhoto1, homeBed, homeBath, homeKitchen, homeBalcony, postLocation, homePrice, homeDivision,
            homeArea, searchAddress, homeRentMonth, postID,homeFloor;
    private boolean postLive, postSold, earningPost, homeWifi, homeCleaning, homeSecurity, homeGenerator, homeLift;
    private long postTime;

    public HomePostShowModel() {
    }

    public HomePostShowModel(String homePhoto1, String homeBed, String homeBath, String homeKitchen, String homeBalcony, String postLocation, String homePrice, String homeDivision, String homeArea, String searchAddress, String homeRentMonth, String postID, String homeFloor, boolean postLive, boolean postSold, boolean earningPost, boolean homeWifi, boolean homeCleaning, boolean homeSecurity, boolean homeGenerator, boolean homeLift, long postTime) {
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
        this.homeRentMonth = homeRentMonth;
        this.postID = postID;
        this.homeFloor = homeFloor;
        this.postLive = postLive;
        this.postSold = postSold;
        this.earningPost = earningPost;
        this.homeWifi = homeWifi;
        this.homeCleaning = homeCleaning;
        this.homeSecurity = homeSecurity;
        this.homeGenerator = homeGenerator;
        this.homeLift = homeLift;
        this.postTime = postTime;
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

    public String getHomeRentMonth() {
        return homeRentMonth;
    }

    public void setHomeRentMonth(String homeRentMonth) {
        this.homeRentMonth = homeRentMonth;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getHomeFloor() {
        return homeFloor;
    }

    public void setHomeFloor(String homeFloor) {
        this.homeFloor = homeFloor;
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

    public boolean isEarningPost() {
        return earningPost;
    }

    public void setEarningPost(boolean earningPost) {
        this.earningPost = earningPost;
    }

    public boolean isHomeWifi() {
        return homeWifi;
    }

    public void setHomeWifi(boolean homeWifi) {
        this.homeWifi = homeWifi;
    }

    public boolean isHomeCleaning() {
        return homeCleaning;
    }

    public void setHomeCleaning(boolean homeCleaning) {
        this.homeCleaning = homeCleaning;
    }

    public boolean isHomeSecurity() {
        return homeSecurity;
    }

    public void setHomeSecurity(boolean homeSecurity) {
        this.homeSecurity = homeSecurity;
    }

    public boolean isHomeGenerator() {
        return homeGenerator;
    }

    public void setHomeGenerator(boolean homeGenerator) {
        this.homeGenerator = homeGenerator;
    }

    public boolean isHomeLift() {
        return homeLift;
    }

    public void setHomeLift(boolean homeLift) {
        this.homeLift = homeLift;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }
}