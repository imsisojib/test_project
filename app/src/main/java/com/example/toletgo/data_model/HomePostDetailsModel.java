package com.example.toletgo.data_model;

public class HomePostDetailsModel {
    String homePhoto1,homePhoto2,homePhoto3,homePhoto4,homePhoto5,postOwner,homeWifi,homeCleaning,homeSecurity,homeGenerator,homeLift;

    public HomePostDetailsModel() {
    }

    public HomePostDetailsModel(String homePhoto1, String homePhoto2, String homePhoto3, String homePhoto4,
                                String homePhoto5, String postOwner, String homeWifi, String homeCleaning,
                                String homeSecurity, String homeGenerator, String homeLift) {
        this.homePhoto1 = homePhoto1;
        this.homePhoto2 = homePhoto2;
        this.homePhoto3 = homePhoto3;
        this.homePhoto4 = homePhoto4;
        this.homePhoto5 = homePhoto5;
        this.postOwner = postOwner;
        this.homeWifi = homeWifi;
        this.homeCleaning = homeCleaning;
        this.homeSecurity = homeSecurity;
        this.homeGenerator = homeGenerator;
        this.homeLift = homeLift;
    }

    public String getHomePhoto1() {
        return homePhoto1;
    }

    public void setHomePhoto1(String homePhoto1) {
        this.homePhoto1 = homePhoto1;
    }

    public String getHomePhoto2() {
        return homePhoto2;
    }

    public void setHomePhoto2(String homePhoto2) {
        this.homePhoto2 = homePhoto2;
    }

    public String getHomePhoto3() {
        return homePhoto3;
    }

    public void setHomePhoto3(String homePhoto3) {
        this.homePhoto3 = homePhoto3;
    }

    public String getHomePhoto4() {
        return homePhoto4;
    }

    public void setHomePhoto4(String homePhoto4) {
        this.homePhoto4 = homePhoto4;
    }

    public String getHomePhoto5() {
        return homePhoto5;
    }

    public void setHomePhoto5(String homePhoto5) {
        this.homePhoto5 = homePhoto5;
    }

    public String getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(String postOwner) {
        this.postOwner = postOwner;
    }

    public String getHomeWifi() {
        return homeWifi;
    }

    public void setHomeWifi(String homeWifi) {
        this.homeWifi = homeWifi;
    }

    public String getHomeCleaning() {
        return homeCleaning;
    }

    public void setHomeCleaning(String homeCleaning) {
        this.homeCleaning = homeCleaning;
    }

    public String getHomeSecurity() {
        return homeSecurity;
    }

    public void setHomeSecurity(String homeSecurity) {
        this.homeSecurity = homeSecurity;
    }

    public String getHomeGenerator() {
        return homeGenerator;
    }

    public void setHomeGenerator(String homeGenerator) {
        this.homeGenerator = homeGenerator;
    }

    public String getHomeLift() {
        return homeLift;
    }

    public void setHomeLift(String homeLift) {
        this.homeLift = homeLift;
    }
}
