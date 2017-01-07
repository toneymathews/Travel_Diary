package com.example.toney.trial51;


public class Entry {
    private String title, place, createdTime, modifiedTime, seconds, content;

    public Entry() {
    }

    public Entry(String title, String place, String createdTime, String modifiedTime, String seconds, String content) {
        this.title = title;
        this.place = place;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.seconds = seconds;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title;     }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}