package com.example.toney.trial51;


public class Settings {
    private String key, value;

    public Settings() {
    }

    public Settings(String key, String value) {
        this.key = key;
        this.value = value;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }

}

