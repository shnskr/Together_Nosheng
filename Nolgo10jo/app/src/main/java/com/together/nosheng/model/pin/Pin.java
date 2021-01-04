package com.together.nosheng.model.pin;

import com.together.nosheng.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pin {
    private double latitude; // 위도
    private double longitude; // 경도
    private String pinName = ""; // 이름
    private String address = "";

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getPinName() {
        return pinName;
    }

    public void setPinName(String pinName) {
        this.pinName = pinName;
    }

    @Override
    public String toString() {
        return "Pin{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", pinName='" + pinName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
