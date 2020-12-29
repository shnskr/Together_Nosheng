package com.together.nosheng.model.plan;

import com.together.nosheng.model.pin.Pin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plan {
    private Date planDate = new Date(); // 날짜 ex) 1일차, 2일차
    private boolean open; // 공개여부
    private String planTheme = ""; // 썸네일
//    private Map<String, Pin> pins = new HashMap<>(); // Key : User id, Value : Pin class
    private List<Pin> pins = new ArrayList<>();
//    private Map<String, Location> locations = new HashMap<>(); // Key : 위치 이름, Value : Location class
    private String projectId = ""; // 프로젝트 id
    private Map<String, List<Pin>> route = new HashMap<>(); // Key : 기획인지 최종경로인지, Value : Pin class
    //추가된 항목
    private String planTitle = ""; // 플랜 제목
    private List<String> planLike = new ArrayList<>(); //플랜의 좋아요 개수

    public List<Pin> getPins() {
        return pins;
    }

    public void setPins(List<Pin> pins) {
        this.pins = pins;
    }

    public String getPlanTitle() { return planTitle; }

    public void setPlanTitle(String planTitle) { this.planTitle = planTitle; }

    public List<String> getPlanLike() { return planLike; }

    public void setPlanLike(List<String> planLike) { this.planLike = planLike; }

    public Date getPlanDate() { return planDate; }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getPlanTheme() {
        return planTheme;
    }

    public void setPlanTheme(String planTheme) {
        this.planTheme = planTheme;
    }

//    public Map<String, Pin> getPins() {
//        return pins;
//    }
//
//    public void setPins(Map<String, Pin> pins) {
//        this.pins = pins;
//    }

//    public Map<String, Location> getLocations() {
//        return locations;
//    }
//
//    public void setLocations(Map<String, Location> locations) {
//        this.locations = locations;
//    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Map<String, List<Pin>> getRoute() {
        return route;
    }

    public void setRoute(Map<String, List<Pin>> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "planDate=" + planDate +
                ", open=" + open +
                ", planTheme='" + planTheme + '\'' +
                ", pins=" + pins +
                ", projectId='" + projectId + '\'' +
                ", route=" + route +
                ", planTitle='" + planTitle + '\'' +
                ", planLike=" + planLike +
                '}';
    }
}
