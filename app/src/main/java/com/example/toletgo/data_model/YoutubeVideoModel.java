package com.example.toletgo.data_model;

public class YoutubeVideoModel {
    private  String videoLink,location;

    public YoutubeVideoModel() {
    }

    public YoutubeVideoModel(String videoLink, String location) {
        this.videoLink = videoLink;
        this.location = location;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
