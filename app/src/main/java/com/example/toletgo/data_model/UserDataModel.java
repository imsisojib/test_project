package com.example.toletgo.data_model;

public class UserDataModel {
    String userName,userMobile,userDivision,userDistrict,userPostalCode,userProfession,userAddress,
            userHolding,userNID,userProPicUrl,locationUrl;

    public UserDataModel() {
    }

    public UserDataModel(String userName, String userMobile, String userDivision, String userDistrict,
                         String userPostalCode, String userProfession, String userAddress, String userHolding,
                         String userNID, String userProPicUrl, String locationUrl) {
        this.userName = userName;
        this.userMobile = userMobile;
        this.userDivision = userDivision;
        this.userDistrict = userDistrict;
        this.userPostalCode = userPostalCode;
        this.userProfession = userProfession;
        this.userAddress = userAddress;
        this.userHolding = userHolding;
        this.userNID = userNID;
        this.userProPicUrl = userProPicUrl;
        this.locationUrl = locationUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserDivision() {
        return userDivision;
    }

    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }

    public String getUserDistrict() {
        return userDistrict;
    }

    public void setUserDistrict(String userDistrict) {
        this.userDistrict = userDistrict;
    }

    public String getUserPostalCode() {
        return userPostalCode;
    }

    public void setUserPostalCode(String userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

    public String getUserProfession() {
        return userProfession;
    }

    public void setUserProfession(String userProfession) {
        this.userProfession = userProfession;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserHolding() {
        return userHolding;
    }

    public void setUserHolding(String userHolding) {
        this.userHolding = userHolding;
    }

    public String getUserNID() {
        return userNID;
    }

    public void setUserNID(String userNID) {
        this.userNID = userNID;
    }

    public String getUserProPicUrl() {
        return userProPicUrl;
    }

    public void setUserProPicUrl(String userProPicUrl) {
        this.userProPicUrl = userProPicUrl;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }
}
