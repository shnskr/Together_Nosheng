package com.together.nosheng.model.pin;

import com.together.nosheng.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pin {
    private double latitude; // 위도
    private double longitude; // 경도
    private List<String> theme = new ArrayList<>(); // 테마
    private String pinName = ""; // 이름
    private List<User> visitors = new ArrayList<>(); // 방문객 리스트

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getTheme() {
        return theme;
    }

    public void setTheme(List<String> theme) {
        this.theme = theme;
    }

    public String getPinName() {
        return pinName;
    }

    public void setPinName(String pinName) {
        this.pinName = pinName;
    }

    public List<User> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<User> visitors) {
        this.visitors = visitors;
    }
}
