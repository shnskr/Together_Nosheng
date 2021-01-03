package com.together.nosheng.model.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {
    private String title = "";
    private Date regDate = new Date();
    private Date startDate = new Date();
    private Date endDate = new Date();
    private Map<String, Budget> budgets = new HashMap<>(); // Key : 식비같은 큰 항목 이름, Value : Budget class(total 예산과 세부 항목이 필드)
    private Map<String, List<String>> userTags = new HashMap<>(); // Key : 유저 id, Value : Tag name List
    private List<Post> posts = new ArrayList<>(); // 게시글 목록
    private List<String> tags = new ArrayList<>(); //테그 목록
    private Map<String, Map<String, Boolean>> checkLists = new HashMap<>(); // Key : 유저 ID,  Value : CheckList class(체크리스트 항목)
    private List<String> members = new ArrayList<>();
    private List<String> plans = new ArrayList<>();

    public Project() {
        budgets.put("식비", new Budget());
        budgets.put("숙박비", new Budget());
        budgets.put("교통비", new Budget());
        budgets.put("비상금", new Budget());
        budgets.put("기타", new Budget());

        tags.add("기사");
        tags.add("총무");
        tags.add("기획");
        tags.add("사진작가");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Map<String, Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(Map<String, Budget> budgets) {
        this.budgets = budgets;
    }

    public Map<String, List<String>> getUserTags() {
        return userTags;
    }

    public void setUserTags(Map<String, List<String>> userTags) {
        this.userTags = userTags;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, Map<String, Boolean>> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(Map<String, Map<String, Boolean>> checkLists) {
        this.checkLists = checkLists;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getPlans() {
        return plans;
    }

    public void setPlans(List<String> plans) {
        this.plans = plans;
    }

    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", regDate=" + regDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", budgets=" + budgets +
                ", userTags=" + userTags +
                ", posts=" + posts +
                ", tags=" + tags +
                ", checkLists=" + checkLists +
                ", members=" + members +
                ", plans=" + plans +
                '}';
    }

}