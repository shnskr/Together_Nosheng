package com.together.nosheng.model.plan;

import com.together.nosheng.model.pin.Pin;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Plan {
    private Date date; // 날짜 ex) 1일차, 2일차
    private boolean open; // 공개여부
    private String planTheme; // 썸네일
    private Map<String, Pin> pins; // Key : User id, Value : Pin class
    private Map<String, Location> locations; // Key : 위치 이름, Value : Location class
    private String projectId; // 프로젝트 id
    private Map<String, List<Pin>> route; // Key : 기획인지 최종경로인지, Value : Pin class
    //추가된 항목
    private String planTitle; // 플랜 제목
    private int planLike; //플랜의 좋아요 개수


    public String getPlanTitle() { return planTitle; }

    public void setPlanTitle(String planTitle) { this.planTitle = planTitle; }

    public int getPlanLike() { return planLike; }

    public void setPlanLike(int planLike) { this.planLike = planLike; }

    public Date getPlanDate() { return date; }

    public void setPlanDate(Date date) {
        this.date = date;
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

    public Map<String, Pin> getPins() {
        return pins;
    }

    public void setPins(Map<String, Pin> pins) {
        this.pins = pins;
    }

    public Map<String, Location> getLocations() {
        return locations;
    }

    public void setLocations(Map<String, Location> locations) {
        this.locations = locations;
    }

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
}
