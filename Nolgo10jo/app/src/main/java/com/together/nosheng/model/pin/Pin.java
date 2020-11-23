package com.together.nosheng.model.pin;

import com.together.nosheng.model.user.User;

import java.util.List;
import java.util.Map;

public class Pin {
    private String latitude; // 위도
    private String longitude; // 경도
    private List<String> theme; // 테마
    private String pinName; // 이름
    private List<User> visitors; // 방문객 리스트

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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
