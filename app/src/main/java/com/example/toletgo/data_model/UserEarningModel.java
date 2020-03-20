package com.example.toletgo.data_model;

public class UserEarningModel {
    String locationUrl,totalRefer,totalPost,totalView,totalPaid,myRefCode,yourRefCode,userUID;

    public UserEarningModel() {
    }

    public UserEarningModel(String locationUrl, String totalRefer, String totalPost, String totalView, String totalPaid, String myRefCode, String yourRefCode, String userUID) {
        this.locationUrl = locationUrl;
        this.totalRefer = totalRefer;
        this.totalPost = totalPost;
        this.totalView = totalView;
        this.totalPaid = totalPaid;
        this.myRefCode = myRefCode;
        this.yourRefCode = yourRefCode;
        this.userUID = userUID;
    }


    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public String getTotalRefer() {
        return totalRefer;
    }

    public void setTotalRefer(String totalRefer) {
        this.totalRefer = totalRefer;
    }

    public String getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(String totalPost) {
        this.totalPost = totalPost;
    }

    public String getTotalView() {
        return totalView;
    }

    public void setTotalView(String totalView) {
        this.totalView = totalView;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getMyRefCode() {
        return myRefCode;
    }

    public void setMyRefCode(String myRefCode) {
        this.myRefCode = myRefCode;
    }

    public String getYourRefCode() {
        return yourRefCode;
    }

    public void setYourRefCode(String yourRefCode) {
        this.yourRefCode = yourRefCode;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
